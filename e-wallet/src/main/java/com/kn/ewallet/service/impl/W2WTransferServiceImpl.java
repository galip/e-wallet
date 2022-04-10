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
import com.kn.ewallet.request.W2WBySenderRequest;
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
        BigDecimal senderPreviousBalance = senderWallet.getBalance();

        BigDecimal receiverNewBalance = receiverWallet.getBalance().add(request.getAmount());
        BigDecimal receiverPreviousBalance = receiverWallet.getBalance();

        senderWallet.setPreviousBalance(senderWallet.getBalance());
        senderWallet.setBalance(senderPreviousBalance);
        senderWallet.setModifiedBy("modified user");
        senderWallet.setModifiedDate(new Date());

        receiverWallet.setPreviousBalance(receiverWallet.getBalance());
        receiverWallet.setBalance(receiverPreviousBalance);
        receiverWallet.setModifiedBy("modified user");
        receiverWallet.setModifiedDate(new Date());

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        Transaction w2wTransaction = new Transaction();
        w2wTransaction.setSenderId(request.getSenderId());
        w2wTransaction.setAmount(request.getAmount());
        w2wTransaction.setSenderBalance(senderNewBalance);
        w2wTransaction.setSenderPreviousBalance(senderWallet.getBalance());
        w2wTransaction.setReceiverId(request.getReceiverId());
        w2wTransaction.setReceiverBalance(receiverNewBalance);
        w2wTransaction.setReceiverPreviousBalance(receiverWallet.getBalance());
        w2wTransaction.setToken(UUID.randomUUID().toString());
        w2wTransaction.setType(TransactionType.W2WTRANSFER.name());
        w2wTransaction.setCreatedBy("created user");
        w2wTransaction.setCreatedDate(new Date());

        transactionRepository.save(w2wTransaction);

    }

    private Wallet checkIfWalletExist(Long customerId) throws WalletBusinessException {
        Wallet wallet = walletRepository.findByCustomerIdAndStatus(customerId, Status.ACTIVE.name());
        if (wallet == null)
            throw new WalletBusinessException(ErrorCode.WALLET_NOT_FOUND);
        return wallet;
    }

    @Override
    public List<Transaction> findBySenderId(W2WBySenderRequest request) throws WalletBusinessException {
        return transactionRepository.findBySenderIdAndType(request.getSenderId(), TransactionType.W2WTRANSFER.name());
    }

    @Override
    public List<Transaction> findAllW2W() {
        return transactionRepository.findAllByType(TransactionType.W2WTRANSFER.name());
    }
}