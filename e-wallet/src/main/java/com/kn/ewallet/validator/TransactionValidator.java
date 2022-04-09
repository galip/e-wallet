package com.kn.ewallet.validator;

import com.kn.ewallet.constant.ErrorCode;
import com.kn.ewallet.exception.WalletRequestException;
import com.kn.ewallet.request.TransactionByCustomerRequest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionValidator {

	public static void validateByCustomerRequest(TransactionByCustomerRequest request) throws WalletRequestException {
		if (request == null) {
			throw new WalletRequestException(ErrorCode.REQUEST_NULL);
		}
		if (request.getCustomerId() == null || request.getCustomerId() == 0L) {
			throw new WalletRequestException(ErrorCode.CUSTOMER_ID_NULL_OR_ZERO);
		}
	}
}