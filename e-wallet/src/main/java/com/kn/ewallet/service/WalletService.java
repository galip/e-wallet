package com.kn.ewallet.service;

import java.util.List;

import com.kn.ewallet.exception.WalletBusinessException;
import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.request.CreateWalletRequest;
import com.kn.ewallet.request.TopUpRequest;
import com.kn.ewallet.request.WithdrawRequest;

public interface WalletService {

    List<Wallet> findAll();
    
    Wallet create(CreateWalletRequest request) throws WalletBusinessException;
    
    Wallet topUp(TopUpRequest request) throws WalletBusinessException;
    
    Wallet withdraw(WithdrawRequest request) throws WalletBusinessException;

}
