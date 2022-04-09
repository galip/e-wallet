package com.kn.ewallet.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApiException {

	private final String message;
	private final HttpStatus httpStatus;
}