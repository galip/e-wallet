package com.kn.ewallet.validator;

import com.kn.ewallet.constant.ErrorCode;
import com.kn.ewallet.exception.WalletRequestException;
import com.kn.ewallet.request.W2WBySenderRequest;
import com.kn.ewallet.request.W2WRequest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class W2WTransferValidator {

	public static void validateW2WTransferBySenderRequest(W2WBySenderRequest request) throws WalletRequestException {
		if (request == null) {
			throw new WalletRequestException(ErrorCode.REQUEST_NULL);
		}
		if (request.getSenderId() == null || request.getSenderId() == 0L) {
			throw new WalletRequestException(ErrorCode.CUSTOMER_ID_NULL_OR_ZERO);
		}
	}
	
	public static void validateW2WTransferBySenderRequest(W2WRequest request) throws WalletRequestException {
            if (request == null) {
                    throw new WalletRequestException(ErrorCode.REQUEST_NULL);
            }
            if (request.getSenderId() == null || request.getSenderId() == 0L) {
                    throw new WalletRequestException(ErrorCode.CUSTOMER_ID_NULL_OR_ZERO);
            }
            if (request.getReceiverId() == null || request.getReceiverId() == 0L) {
                throw new WalletRequestException(ErrorCode.CUSTOMER_ID_NULL_OR_ZERO);
            }
    }
}