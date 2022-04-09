package com.kn.ewallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WalletExceptionHandler {

	@ExceptionHandler(WalletRequestException.class)
	public ResponseEntity<?> handleRequestException(WalletRequestException e) {

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ApiException apiException = new ApiException(e.getMessage(), httpStatus);
		return new ResponseEntity<>(apiException, httpStatus);
	}
	
	@ExceptionHandler(WalletBusinessException.class)
	public ResponseEntity<?> handleBusinessException(WalletBusinessException e) {

		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ApiException apiException = new ApiException(e.getMessage(), httpStatus);
		return new ResponseEntity<>(apiException, httpStatus);
	}
}
