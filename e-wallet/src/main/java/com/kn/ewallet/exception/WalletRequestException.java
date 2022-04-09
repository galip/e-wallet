package com.kn.ewallet.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WalletRequestException extends RuntimeException {

	public WalletRequestException(String message) {
		super(message);
	}

	public WalletRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}