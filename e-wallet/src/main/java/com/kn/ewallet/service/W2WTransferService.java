package com.kn.ewallet.service;

import java.util.List;

import com.kn.ewallet.exception.WalletBusinessException;
import com.kn.ewallet.model.Transaction;
import com.kn.ewallet.request.W2WBySenderRequest;
import com.kn.ewallet.request.W2WRequest;

public interface W2WTransferService {
    
    List<Transaction> findAllW2W();

    public void w2wTransfer(W2WRequest request) throws WalletBusinessException;
    
    List<Transaction> findBySenderId(W2WBySenderRequest request) throws WalletBusinessException;

}
