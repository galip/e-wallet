package com.kn.ewallet.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TransactionBySenderRequest {
    
    private Long senderId;
}
