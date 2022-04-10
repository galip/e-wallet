package com.kn.ewallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kn.ewallet.core.ErrorCodes;
import com.kn.ewallet.exception.WalletBusinessException;
import com.kn.ewallet.exception.WalletException;
import com.kn.ewallet.mapper.TransactionMapper;
import com.kn.ewallet.model.Transaction;
import com.kn.ewallet.request.W2WBySenderRequest;
import com.kn.ewallet.request.W2WRequest;
import com.kn.ewallet.response.TransactionResponse;
import com.kn.ewallet.service.TransactionService;
import com.kn.ewallet.service.W2WTransferService;
import com.kn.ewallet.validator.W2WTransferValidator;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/w2w")
@RequiredArgsConstructor
public class W2WTransferController {

    @Autowired
    TransactionService transactionService;
    
    @Autowired
    W2WTransferService w2wTransferService;

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<TransactionResponse> findAll() {

        TransactionResponse response = new TransactionResponse();
        List<Transaction> transactions = w2wTransferService.findAllW2W();
        response.setTransactions(TransactionMapper.convertToDto(transactions));
        response.setResult(ErrorCodes.success());

        return ResponseEntity.ok(response);
    }

    /**
     * @param request
     * @return
     * @throws WalletException
     */
    @PostMapping("/bySenderId")
    @ResponseBody
    public ResponseEntity<TransactionResponse> findBySenderId(@RequestBody W2WBySenderRequest request)
            throws WalletBusinessException {
        W2WTransferValidator.validateW2WTransferBySenderRequest(request);

        TransactionResponse response = new TransactionResponse();
        List<Transaction> transactions = w2wTransferService.findBySenderId(request);
        response.setTransactions(TransactionMapper.convertToDto(transactions));
        response.setResult(ErrorCodes.success());

        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/transfer")
    @ResponseBody
    public ResponseEntity<TransactionResponse> transfer(@RequestBody W2WRequest request)
            throws WalletBusinessException {
        W2WTransferValidator.validateW2WTransferBySenderRequest(request);

        TransactionResponse response = new TransactionResponse();
        w2wTransferService.w2wTransfer(request);
        response.setResult(ErrorCodes.success());

        return ResponseEntity.ok(response);
    }
}