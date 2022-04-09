package com.kn.ewallet.request;

import java.math.BigDecimal;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class WithdrawRequest {
    
    private Long customerId;
    private BigDecimal amount;
}
