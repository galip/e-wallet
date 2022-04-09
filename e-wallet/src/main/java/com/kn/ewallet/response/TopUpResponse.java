package com.kn.ewallet.response;

import java.io.Serializable;

import com.kn.ewallet.dto.WalletDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author galip
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopUpResponse extends BaseResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private WalletDto walletDto;

}