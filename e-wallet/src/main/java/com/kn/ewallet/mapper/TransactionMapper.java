package com.kn.ewallet.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.kn.ewallet.dto.TransactionDto;
import com.kn.ewallet.model.Transaction;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionMapper {

    public static List<TransactionDto> convertToDto(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return null;
        }
        return transactions.stream()
                .map(t -> new TransactionDto().setId(t.getId())
                        .setSenderId(t.getSenderId())
                        .setSenderBalance(t.getSenderBalance())
                        .setSenderPreviousBalance(t.getSenderPreviousBalance())
                        .setType(t.getType())
                        .setToken(t.getToken()))
                .collect(Collectors.toList());
    }

}