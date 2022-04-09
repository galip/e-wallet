package com.kn.ewallet.service;

import java.util.List;

import com.kn.ewallet.model.Wallet;

public interface WalletService {

	List<Wallet> findAll();
}
