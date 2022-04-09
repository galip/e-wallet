package com.kn.ewallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kn.ewallet.core.ErrorCodes;
import com.kn.ewallet.mapper.WalletMapper;
import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.response.WalletsResponse;
import com.kn.ewallet.service.WalletService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<WalletsResponse> findAll() {

        WalletsResponse response = new WalletsResponse();
        List<Wallet> wallets = walletService.findAll();
        response.setWallets(WalletMapper.convertToDto(wallets));
        response.setResult(ErrorCodes.success());

        return ResponseEntity.ok(response);
    }

}
