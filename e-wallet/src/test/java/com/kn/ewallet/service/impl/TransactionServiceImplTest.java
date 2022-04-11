package com.kn.ewallet.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import com.kn.ewallet.request.TransactionBySenderRequest;
import com.kn.ewallet.request.WithdrawRequest;
import com.kn.ewallet.service.TransactionService;
import com.kn.ewallet.service.WalletService;

@SpringBootTest
class TransactionServiceImplTest {

    @Autowired
    TransactionRepository transactionRepository;
    
    @Autowired
    TransactionService transactionService;

    Transaction firstTransaction = new Transaction();
    Transaction secondTransaction = new Transaction();

    @BeforeEach
    void beforeEach() {
        transactionRepository.deleteAll();

        firstTransaction.setSenderId(1L);
        firstTransaction.setAmount(BigDecimal.TEN);
        firstTransaction.setSenderBalance(BigDecimal.TEN);
        firstTransaction.setSenderPreviousBalance(BigDecimal.TEN);
        firstTransaction.setToken(UUID.randomUUID().toString());
        firstTransaction.setType(TransactionType.TOPUP.name());
        firstTransaction.setCreatedBy("created user");
        firstTransaction.setCreatedDate(new Date());
        transactionRepository.save(firstTransaction);

        secondTransaction.setSenderId(2L);
        secondTransaction.setAmount(BigDecimal.TEN);
        secondTransaction.setSenderBalance(BigDecimal.TEN);
        secondTransaction.setSenderPreviousBalance(BigDecimal.TEN);
        secondTransaction.setToken(UUID.randomUUID().toString());
        secondTransaction.setType(TransactionType.TOPUP.name());
        secondTransaction.setCreatedBy("created user");
        secondTransaction.setCreatedDate(new Date());
        transactionRepository.save(secondTransaction);

    }

    @AfterEach
    void afterEach() {
        transactionRepository.deleteAll();
    }

    @Test
    void shouldReturnWallets_whenFindAll() {
        // when
        List<Transaction> transactions = transactionService.findAll();

        // then
        assertNotNull(transactions);
        assertEquals(2, transactions.size());
    }
    
    @Test
    void shouldReturnList_whenFindBySenderId_withValidTransactionRequest() throws WalletBusinessException {
        
        //given
        TransactionBySenderRequest request = new TransactionBySenderRequest();
        request.setSenderId(1L);
        
        // when
        List<Transaction> transactions = transactionService.findBySenderId(request);

        // then
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
    }
}
