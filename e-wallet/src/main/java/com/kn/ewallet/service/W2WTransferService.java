package com.kn.ewallet.service;

import com.kn.ewallet.exception.WalletBusinessException;
import com.kn.ewallet.request.W2WRequest;

public interface W2WTransferService {

    public void w2wTransfer(W2WRequest request) throws WalletBusinessException;

}
