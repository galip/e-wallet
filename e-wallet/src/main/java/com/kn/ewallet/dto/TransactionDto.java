package com.kn.ewallet.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransactionDto {

    private Long id;
    private Long senderId;
    private Long rceiverId;
    private BigDecimal senderBalance;
    private BigDecimal senderPreviousBalance;
    private BigDecimal receiverBalance;
    private BigDecimal receiverPreviousBalance;
    private String type;
    private String token;
    private Date createdDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
}