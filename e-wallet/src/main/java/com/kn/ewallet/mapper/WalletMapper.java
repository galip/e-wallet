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
        return wallets.stream()
                .map(w -> new WalletDto().setId(w.getId())
                        .setCustomerId(w.getCustomerId())
                        .setStatus(w.getStatus())
                        .setBalance(w.getBalance()))
                .collect(Collectors.toList());
    }
    
    public static WalletDto convertToDto(Wallet wallet) {
        if (wallet == null) {
                return null;
        }
        WalletDto orderDto = new WalletDto();
        orderDto.setId(wallet.getId());
        orderDto.setCustomerId(wallet.getCustomerId());
        orderDto.setBalance(wallet.getBalance());
        orderDto.setPreviousBalance(wallet.getPreviousBalance());
        orderDto.setStatus(wallet.getStatus());
        orderDto.setCreatedBy(wallet.getCreatedBy());
        orderDto.setCreatedDate(wallet.getCreatedDate());
        orderDto.setModifiedDate(wallet.getModifiedDate());
        orderDto.setModifiedBy(wallet.getModifiedBy());
        return orderDto;

}
}