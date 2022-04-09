package com.kn.ewallet.constant;

import java.io.Serializable;

public class ErrorCode implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String REQUEST_NULL = "REQUEST_NULL";
    public static final String CUSTOMER_ID_NULL_OR_ZERO = "CUSTOMER_ID_NULL_OR_ZERO";
    public static final String WALLET_ALREADY_EXIST = "WALLET_ALREADY_EXIST";
    public static final String WALLET_NOT_FOUND = "WALLET_NOT_FOUND";
    public static final String AMOUNT_MUST_BE_MORE_THAN_ZERO = "AMOUNT_MUST_BE_MORE_THAN_ZERO";
    public static final String INSUFFICIENT_WALLET_BALANCE = "INSUFFICIENT_WALLET_BALANCE";

}