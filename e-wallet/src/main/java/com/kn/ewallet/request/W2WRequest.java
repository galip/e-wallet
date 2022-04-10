package com.kn.ewallet.request;

import java.math.BigDecimal;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class W2WRequest {
    
    private Long senderId;
    private Long receiverId;
    private BigDecimal amount;
}
