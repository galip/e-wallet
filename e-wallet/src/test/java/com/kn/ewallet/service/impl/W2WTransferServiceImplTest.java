package com.kn.ewallet.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import com.kn.ewallet.request.W2WRequest;
import com.kn.ewallet.service.W2WTransferService;
import com.kn.ewallet.service.WalletService;

@SpringBootTest
class W2WTransferServiceImplTest {
    
   @Autowired
   W2WTransferService w2WTransferService;

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
        secondWallet.setBalance(BigDecimal.valueOf(50));
        secondWallet.setPreviousBalance(BigDecimal.valueOf(50));
        secondWallet.setStatus(Status.ACTIVE.name());
        secondWallet.setCreatedBy("create user 2");
        secondWallet.setCreatedDate(new Date());
        walletRepository.save(secondWallet);

    }

    @AfterEach
    void afterEach() {
        walletRepository.deleteAll();
    }

    @Test
    void shouldTransfer_whenwW2wTransfer_withValidWalletRequest() throws WalletBusinessException {
        // given
        W2WRequest request = new W2WRequest();
        request.setSenderId(2L);
        request.setReceiverId(1L);
        request.setAmount(BigDecimal.TEN);

        // when
        w2WTransferService.w2wTransfer(request);
        
        Wallet firstCheckWallet = walletRepository.findByCustomerIdAndStatus(firstWallet.getCustomerId(), Status.ACTIVE.name());
        Wallet secondCheckWallet = walletRepository.findByCustomerIdAndStatus(secondWallet.getCustomerId(), Status.ACTIVE.name());
        
        List<Transaction> w2wTransaction = transactionRepository.findBySenderIdAndType(secondWallet.getCustomerId(), TransactionType.W2WTRANSFER.name());

        // then
        assertNotNull(firstCheckWallet);
        assertNotNull(secondCheckWallet);
        assertThat(BigDecimal.TEN, Matchers.comparesEqualTo(firstCheckWallet.getBalance()));
        assertThat(BigDecimal.valueOf(40), Matchers.comparesEqualTo(secondCheckWallet.getBalance()));
        
        assertEquals(request.getSenderId(), w2wTransaction.get(0).getSenderId());
        assertEquals(request.getReceiverId(), w2wTransaction.get(0).getReceiverId());
        assertThat(request.getAmount(), Matchers.comparesEqualTo(w2wTransaction.get(0).getAmount()));
    }
    
    @Test
    void shouldThrowInsufficientWalletBalance_whenTopUp_withInSufficientBalanceRequest() throws WalletBusinessException {
        // given
        W2WRequest request = new W2WRequest();
        request.setSenderId(2L);
        request.setReceiverId(1L);
        request.setAmount(BigDecimal.valueOf(100));

        // when
        WalletBusinessException exception = assertThrows(WalletBusinessException.class, () -> {
            w2WTransferService.w2wTransfer(request);
        });

        // then
        String expectedMessage = ErrorCode.INSUFFICIENT_WALLET_BALANCE;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}