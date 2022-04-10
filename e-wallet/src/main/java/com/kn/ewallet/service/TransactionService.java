package com.kn.ewallet.service;

import java.util.List;

import com.kn.ewallet.exception.WalletBusinessException;
import com.kn.ewallet.model.Transaction;
import com.kn.ewallet.request.TransactionBySenderRequest;

public interface TransactionService {

    List<Transaction> findAll();

    List<Transaction> findBySenderId(TransactionBySenderRequest request) throws WalletBusinessException;

}
