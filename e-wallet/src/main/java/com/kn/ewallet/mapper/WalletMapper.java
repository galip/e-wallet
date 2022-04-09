package com.kn.ewallet.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.kn.ewallet.dto.WalletDto;
import com.kn.ewallet.model.Wallet;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WalletMapper {

    public static List<WalletDto> convertToDto(List<Wallet> wallets) {
        if (wallets.isEmpty()) {
            return null;
        }
        WalletDto walletDto = new WalletDto();
        return wallets.stream()
                .map(w -> walletDto.setId(w.getId())
                        .setCustomerId(w.getCustomerId())
                        .setStatus(w.getStatus())
                        .setBalance(w.getBalance()))
                .collect(Collectors.toList());
    }
}