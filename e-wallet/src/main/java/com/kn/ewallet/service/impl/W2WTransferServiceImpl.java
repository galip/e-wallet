package com.kn.ewallet.service.impl;

import java.math.BigDecimal;
import java.util.Date;
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
import com.kn.ewallet.request.W2WRequest;
import com.kn.ewallet.service.W2WTransferService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class W2WTransferServiceImpl implements W2WTransferService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void w2wTransfer(W2WRequest request) throws WalletBusinessException {
        Wallet senderWallet = checkIfWalletExist(request.getSenderId());
        Wallet receiverWallet = checkIfWalletExist(request.getReceiverId());

        BigDecimal senderNewBalance = senderWallet.getBalance().subtract(request.getAmount());
        if (BigDecimal.ZERO.compareTo(senderNewBalance) >= 0) {
            throw new WalletBusinessException(ErrorCode.INSUFFICIENT_WALLET_BALANCE);
        }

        BigDecimal receiverNewBalance = receiverWallet.getBalance().add(request.getAmount());

        senderWallet.setPreviousBalance(senderWallet.getBalance());
        senderWallet.setBalance(senderNewBalance);
        senderWallet.setModifiedBy("modified user");
        senderWallet.setModifiedDate(new Date());

        receiverWallet.setPreviousBalance(receiverWallet.getBalance());
        receiverWallet.setBalance(receiverNewBalance);
        receiverWallet.setModifiedBy("modified user");
        receiverWallet.setModifiedDate(new Date());

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        Transaction senderTransaction = new Transaction();
        senderTransaction.setSenderId(request.getSenderId());
        senderTransaction.setSenderBalance(senderNewBalance);
        senderTransaction.setSenderPreviousBalance(senderWallet.getBalance());
        senderTransaction.setReceiverId(request.getReceiverId());
        senderTransaction.setReceiverBalance(receiverNewBalance);
        senderTransaction.setToken(UUID.randomUUID().toString());
        senderTransaction.setType(TransactionType.W2WTRANSFER.name());
        senderTransaction.setCreatedBy("created user");
        senderTransaction.setCreatedDate(new Date());

        transactionRepository.save(senderTransaction);

    }

    private Wallet checkIfWalletExist(Long customerId) throws WalletBusinessException {
        Wallet wallet = walletRepository.findByCustomerIdAndStatus(customerId, Status.ACTIVE.name());
        if (wallet == null)
            throw new WalletBusinessException(ErrorCode.WALLET_NOT_FOUND);
        return wallet;
    }
}