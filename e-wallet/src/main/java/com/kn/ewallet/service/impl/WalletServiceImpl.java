package com.kn.ewallet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.repository.WalletRepository;
import com.kn.ewallet.service.WalletService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Override
    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }
}