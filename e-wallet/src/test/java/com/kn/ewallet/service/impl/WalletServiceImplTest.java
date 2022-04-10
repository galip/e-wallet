package com.kn.ewallet.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kn.ewallet.constant.ErrorCode;
import com.kn.ewallet.core.Status;
import com.kn.ewallet.core.TransactionType;
import com.kn.ewallet.exception.WalletBusinessException;
import com.kn.ewallet.model.Transaction;
import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.repository.TransactionRepository;
import com.kn.ewallet.repository.WalletRepository;
import com.kn.ewallet.request.CreateWalletRequest;
import com.kn.ewallet.request.TopUpRequest;
import com.kn.ewallet.request.WithdrawRequest;
import com.kn.ewallet.service.WalletService;

@SpringBootTest
class WalletServiceImplTest {

    @Autowired
    WalletService walletService;

    @Autowired
    WalletRepository walletRepository;
    
    @Autowired
    TransactionRepository transactionRepository;

    Wallet firstWallet = new Wallet();
    Wallet secondWallet = new Wallet();

    @BeforeEach
    void beforeEach() {

        firstWallet.setCustomerId(1L);
        firstWallet.setBalance(BigDecimal.ZERO);
        firstWallet.setPreviousBalance(BigDecimal.ZERO);
        firstWallet.setStatus(Status.ACTIVE.name());
        firstWallet.setCreatedBy("create user 1");
        firstWallet.setCreatedDate(new Date());
        walletRepository.save(firstWallet);

        secondWallet = new Wallet();
        secondWallet.setCustomerId(2L);
        secondWallet.setBalance(BigDecimal.ZERO);
        secondWallet.setPreviousBalance(BigDecimal.ZERO);
        secondWallet.setStatus(Status.PASSIVE.name());
        secondWallet.setCreatedBy("create user 2");
        secondWallet.setCreatedDate(new Date());
        walletRepository.save(secondWallet);

    }

    @AfterEach
    void afterEach() {
        walletRepository.deleteAll();
    }

    @Test
    void shouldReturnWallets_whenFindAll() {
        // when
        List<Wallet> wallets = walletService.findAll();

        // then
        assertNotNull(wallets);
        assertEquals(2, wallets.size());
    }

    @Test
    void shouldThrowWalletAlreadyExist_whenCreate_withAlreadyExistedWallet() {
        // given
        CreateWalletRequest request = new CreateWalletRequest();
        request.setCustomerId(firstWallet.getCustomerId()); // we use the customerId which already have wallet.

        // when
        WalletBusinessException exception = assertThrows(WalletBusinessException.class, () -> {
            walletService.create(request);
        });

        String expectedMessage = ErrorCode.WALLET_ALREADY_EXIST;
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateWallet_whenCreate_withValidWalletRequest() throws WalletBusinessException {
        // given
        CreateWalletRequest request = new CreateWalletRequest();
        request.setCustomerId(3L); // we use the customerId which already have wallet.

        // when
        Wallet wallet = walletService.create(request);

        // then
        assertNotNull(wallet);
        assertEquals(BigDecimal.ZERO, wallet.getBalance());
        assertEquals(BigDecimal.ZERO, wallet.getPreviousBalance());
        assertEquals(Status.ACTIVE.name(), wallet.getStatus());
    }
    
    // try topUp to a passive wallet and get exception
    @Test
    void shouldThrowWalletNotFound_whenTopUp_withNonExistWalletRequest() throws WalletBusinessException {
        // given
        TopUpRequest request = new TopUpRequest();
        request.setCustomerId(3L);
        request.setAmount(BigDecimal.TEN);

        // when
        WalletBusinessException exception = assertThrows(WalletBusinessException.class, () -> {
            walletService.topUp(request);
        });

        // then
        String expectedMessage = ErrorCode.WALLET_NOT_FOUND;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    void shouldCreateWallet_whenTopUp_withValidWalletRequest() throws WalletBusinessException {
        // given
        TopUpRequest request = new TopUpRequest();
        request.setCustomerId(1L);
        request.setAmount(BigDecimal.TEN);

        // when
        Wallet wallet = walletService.topUp(request);

        // then
        List<Transaction> transactions = transactionRepository.findBySenderIdAndType(request.getCustomerId(), TransactionType.TOPUP.name());
        
        assertNotNull(wallet);
        assertEquals(request.getCustomerId(), wallet.getCustomerId());
        assertTrue(BigDecimal.TEN.compareTo(wallet.getBalance()) == 0);
        assertTrue(BigDecimal.ZERO.compareTo(wallet.getPreviousBalance()) == 0);
        assertEquals(Status.ACTIVE.name(), wallet.getStatus());
        
        assertNotNull(transactions);
        assertEquals(request.getCustomerId(), transactions.get(0).getSenderId());
        assertTrue(BigDecimal.TEN.compareTo(transactions.get(0).getAmount()) == 0);
        assertTrue(BigDecimal.TEN.compareTo(transactions.get(0).getSenderBalance()) == 0);
        assertTrue(BigDecimal.ZERO.compareTo(transactions.get(0).getSenderPreviousBalance()) == 0);
        assertNull(transactions.get(0).getReceiverId());
        assertNull(transactions.get(0).getReceiverBalance());
        assertNull(transactions.get(0).getReceiverPreviousBalance());
    }
    
    // wallet balance is 0 and we try to withdraw 10.
    // and get INSUFFICIENT_WALLET_BALANCE exception.
    @Test
    void shouldThrowInsufficientWalletBalance_whenWithdraw_withInsufficientBalanceRequest() throws WalletBusinessException {
        // given
        WithdrawRequest request = new WithdrawRequest();
        request.setCustomerId(1L);
        request.setAmount(BigDecimal.TEN);

        // when
        WalletBusinessException exception = assertThrows(WalletBusinessException.class, () -> {
            walletService.withdraw(request);
        });

        // then
        String expectedMessage = ErrorCode.INSUFFICIENT_WALLET_BALANCE;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    void shouldWithdraw_whenWithdraw_withValidWithdrawRequest() throws WalletBusinessException {
        // given
        firstWallet.setCustomerId(1L);
        firstWallet.setBalance(BigDecimal.TEN);
        firstWallet.setPreviousBalance(BigDecimal.TEN);
        firstWallet.setStatus(Status.ACTIVE.name());
        firstWallet.setCreatedBy("create user 3");
        firstWallet.setCreatedDate(new Date());
        walletRepository.save(firstWallet);
        
        // when
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setCustomerId(1L);
        withdrawRequest.setAmount(BigDecimal.valueOf(4));
        Wallet wallet = walletService.withdraw(withdrawRequest);

        // then
        List<Transaction> transactions = transactionRepository.findBySenderIdAndType(withdrawRequest.getCustomerId(), TransactionType.WITHDRAW.name());
        
        assertNotNull(wallet);
        assertEquals(withdrawRequest.getCustomerId(), wallet.getCustomerId());
        assertTrue(BigDecimal.valueOf(6).compareTo(wallet.getBalance()) == 0);
        assertTrue(BigDecimal.TEN.compareTo(wallet.getPreviousBalance()) == 0);
        assertEquals(Status.ACTIVE.name(), wallet.getStatus());
        
        assertNotNull(transactions);
        assertEquals(withdrawRequest.getCustomerId(), transactions.get(0).getSenderId());
        assertTrue(BigDecimal.valueOf(4).compareTo(transactions.get(0).getAmount()) == 0);
        assertTrue(BigDecimal.valueOf(6).compareTo(transactions.get(0).getSenderBalance()) == 0);
        assertTrue(BigDecimal.TEN.compareTo(transactions.get(0).getSenderPreviousBalance()) == 0);
        assertNull(transactions.get(0).getReceiverId());
        assertNull(transactions.get(0).getReceiverBalance());
        assertNull(transactions.get(0).getReceiverPreviousBalance());
    }

}
