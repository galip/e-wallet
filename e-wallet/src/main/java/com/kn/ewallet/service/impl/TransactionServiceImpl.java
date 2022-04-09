package com.kn.ewallet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kn.ewallet.exception.WalletBusinessException;
import com.kn.ewallet.model.Transaction;
import com.kn.ewallet.repository.TransactionRepository;
import com.kn.ewallet.request.TransactionByCustomerRequest;
import com.kn.ewallet.service.TransactionService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    
    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findBySenderId(TransactionByCustomerRequest request) throws WalletBusinessException {
        return transactionRepository.findBySenderId(request.getCustomerId());
    }
}