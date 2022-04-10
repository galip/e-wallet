package com.kn.ewallet.validator;

import com.kn.ewallet.constant.ErrorCode;
import com.kn.ewallet.exception.WalletRequestException;
import com.kn.ewallet.request.TransactionBySenderRequest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionValidator {

	public static void validateByCustomerRequest(TransactionBySenderRequest request) throws WalletRequestException {
		if (request == null) {
			throw new WalletRequestException(ErrorCode.REQUEST_NULL);
		}
		if (request.getSenderId() == null || request.getSenderId() == 0L) {
			throw new WalletRequestException(ErrorCode.CUSTOMER_ID_NULL_OR_ZERO);
		}
	}
}