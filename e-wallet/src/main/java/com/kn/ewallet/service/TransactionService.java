package com.kn.ewallet.service;

import java.util.List;

import com.kn.ewallet.exception.WalletBusinessException;
import com.kn.ewallet.model.Transaction;
import com.kn.ewallet.request.TransactionByCustomerRequest;

public interface TransactionService {

    List<Transaction> findAll();

    List<Transaction> findBySenderId(TransactionByCustomerRequest request) throws WalletBusinessException;

}
