package com.kn.ewallet.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WalletBusinessException extends Exception {

	public WalletBusinessException(String message) {
		super(message);
	}
}