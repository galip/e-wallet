package com.kn.ewallet.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @Override
    public Wallet create(CreateWalletRequest request) throws WalletBusinessException {
        Wallet wallet = walletRepository.findByCustomerIdAndStatus(request.getCustomerId(), Status.ACTIVE.name());
        if (wallet != null) { // one customer can have only 1 wallet.
        	log.warn("Wallet already exists. Customer id: {}, wallet id: {}", request.getCustomerId(), wallet.getId());
        	throw new WalletBusinessException(ErrorCode.WALLET_ALREADY_EXIST);
        }

        wallet = new Wallet();
        wallet.setCustomerId(request.getCustomerId());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setPreviousBalance(BigDecimal.ZERO);
        wallet.setStatus(Status.ACTIVE.name());
        wallet.setCreatedBy("create user");
        wallet.setCreatedDate(new Date());

        return walletRepository.save(wallet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Wallet topUp(TopUpRequest request) throws WalletBusinessException {
        Wallet wallet = checkIfWalletExist(request.getCustomerId());

        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(request.getAmount());
        wallet.setBalance(newBalance);
        wallet.setPreviousBalance(balance);
        wallet.setModifiedBy("modified user");
        wallet.setModifiedDate(new Date());
        walletRepository.save(wallet);

        Transaction transaction = new Transaction();
        transaction.setSenderId(request.getCustomerId());
        transaction.setAmount(request.getAmount());
        transaction.setSenderBalance(newBalance);
        transaction.setSenderPreviousBalance(balance);
        transaction.setToken(UUID.randomUUID().toString());
        transaction.setType(TransactionType.TOPUP.name());
        transaction.setCreatedBy("created user");
        transaction.setCreatedDate(new Date());
        transactionRepository.save(transaction);

        return wallet;

    }

    private Wallet checkIfWalletExist(Long customerId) throws WalletBusinessException {
        Wallet wallet = walletRepository.findByCustomerIdAndStatus(customerId, Status.ACTIVE.name());
        if (wallet == null) {
        	log.error("Wallet not found. Customer id: ", customerId);
        	throw new WalletBusinessException(ErrorCode.WALLET_NOT_FOUND);
        }
            
        return wallet;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Wallet withdraw(WithdrawRequest request) throws WalletBusinessException {
        Wallet wallet = checkIfWalletExist(request.getCustomerId());

        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.subtract(request.getAmount());

        if (BigDecimal.ZERO.compareTo(newBalance) >= 0) {
        	log.warn("Insufficient wallet balance. Current balance: {}, withdraw request balance: {}", balance, request.getAmount());
        	throw new WalletBusinessException(ErrorCode.INSUFFICIENT_WALLET_BALANCE);
        }
            
        wallet.setBalance(newBalance);
        wallet.setPreviousBalance(balance);
        wallet.setModifiedBy("modified user");
        wallet.setModifiedDate(new Date());
        walletRepository.save(wallet);

        Transaction transaction = new Transaction();
        transaction.setSenderId(request.getCustomerId());
        transaction.setAmount(request.getAmount());
        transaction.setSenderBalance(newBalance);
        transaction.setSenderPreviousBalance(balance);
        transaction.setToken(UUID.randomUUID().toString());
        transaction.setType(TransactionType.WITHDRAW.name());
        transaction.setCreatedBy("created user");
        transaction.setCreatedDate(new Date());
        transactionRepository.save(transaction);

        return wallet;
    }
}