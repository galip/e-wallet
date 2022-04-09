package com.kn.ewallet.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WalletDto {

    private Long id;
    private Long customerId;
    private BigDecimal balance;
    private BigDecimal previousBalance;
    private String status;
    private Date createdDate;
    private String createdUser;
    private Date updatedDate;
    private String updatedUser;
}