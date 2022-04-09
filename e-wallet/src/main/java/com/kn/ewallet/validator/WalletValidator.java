package com.kn.ewallet.validator;

import java.math.BigDecimal;

import com.kn.ewallet.constant.ErrorCode;
import com.kn.ewallet.exception.WalletException;
import com.kn.ewallet.exception.WalletRequestException;
import com.kn.ewallet.request.CreateWalletRequest;
import com.kn.ewallet.request.TopUpRequest;
import com.kn.ewallet.request.WithdrawRequest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WalletValidator {

	public static void validateCreateWalletRequest(CreateWalletRequest request) throws WalletRequestException {
		if (request == null) {
			throw new WalletRequestException(ErrorCode.REQUEST_NULL);
		}
		if (request.getCustomerId() == null || request.getCustomerId() == 0L) {
			throw new WalletRequestException(ErrorCode.CUSTOMER_ID_NULL_OR_ZERO);
		}
	}
	
	public static void validateTopUpRequest(TopUpRequest request) throws WalletRequestException {
            if (request == null) {
                    throw new WalletRequestException(ErrorCode.REQUEST_NULL);
            }
            if (request.getCustomerId() == null || request.getCustomerId() == 0L) {
                    throw new WalletRequestException(ErrorCode.CUSTOMER_ID_NULL_OR_ZERO);
            }
            if (request.getAmount() == null || BigDecimal.ZERO.compareTo(request.getAmount()) >= 0) {
                throw new WalletRequestException(ErrorCode.AMOUNT_MUST_BE_MORE_THAN_ZERO);
            }
	}
	
	public static void validateWithdrawRequest(WithdrawRequest request) throws WalletRequestException {
            if (request == null) {
                    throw new WalletRequestException(ErrorCode.REQUEST_NULL);
            }
            if (request.getCustomerId() == null || request.getCustomerId() == 0L) {
                    throw new WalletRequestException(ErrorCode.CUSTOMER_ID_NULL_OR_ZERO);
            }
            if (request.getAmount() == null || BigDecimal.ZERO.compareTo(request.getAmount()) >= 0) {
                throw new WalletRequestException(ErrorCode.AMOUNT_MUST_BE_MORE_THAN_ZERO);
            }
        }
}