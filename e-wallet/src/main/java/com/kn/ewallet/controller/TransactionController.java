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
import com.kn.ewallet.request.TransactionByCustomerRequest;
import com.kn.ewallet.response.TransactionResponse;
import com.kn.ewallet.service.TransactionService;
import com.kn.ewallet.validator.TransactionValidator;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<TransactionResponse> findAll() {

        TransactionResponse response = new TransactionResponse();
        List<Transaction> transactions = transactionService.findAll();
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
    public ResponseEntity<TransactionResponse> findBySenderId(@RequestBody TransactionByCustomerRequest request)
            throws WalletBusinessException {
        TransactionValidator.validateByCustomerRequest(request);

        TransactionResponse response = new TransactionResponse();
        List<Transaction> transactions = transactionService.findBySenderId(request);
        response.setTransactions(TransactionMapper.convertToDto(transactions));
        response.setResult(ErrorCodes.success());

        return ResponseEntity.ok(response);
    }
}