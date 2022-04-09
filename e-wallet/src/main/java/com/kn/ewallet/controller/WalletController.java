package com.kn.ewallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kn.ewallet.core.ErrorCodes;
import com.kn.ewallet.exception.WalletBusinessException;
import com.kn.ewallet.exception.WalletException;
import com.kn.ewallet.mapper.WalletMapper;
import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.request.CreateWalletRequest;
import com.kn.ewallet.request.TopUpRequest;
import com.kn.ewallet.request.WithdrawRequest;
import com.kn.ewallet.response.CreateWalletResponse;
import com.kn.ewallet.response.TopUpResponse;
import com.kn.ewallet.response.WalletsResponse;
import com.kn.ewallet.service.WalletService;
import com.kn.ewallet.validator.WalletValidator;

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
    
    /**
     * A customer can have only 1 wallet.
     * @param request
     * @return
     * @throws WalletException
     */
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<CreateWalletResponse> create(@RequestBody CreateWalletRequest request) throws WalletBusinessException {
        WalletValidator.validateCreateWalletRequest(request);

        CreateWalletResponse response = new CreateWalletResponse();
        Wallet wallet = walletService.create(request);
        response.setWalletDto(WalletMapper.convertToDto(wallet));
        response.setResult(ErrorCodes.success());

        return ResponseEntity.ok(response);
    }
    
    /**
     * @param request
     * @return
     * @throws WalletException
     */
    @PostMapping("/topup")
    @ResponseBody
    public ResponseEntity<TopUpResponse> topUp(@RequestBody TopUpRequest request) throws WalletBusinessException {
        WalletValidator.validateTopUpRequest(request);

        TopUpResponse response = new TopUpResponse();
        Wallet wallet = walletService.topUp(request);
        response.setWalletDto(WalletMapper.convertToDto(wallet));
        response.setResult(ErrorCodes.success());

        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/withdraw")
    @ResponseBody
    public ResponseEntity<TopUpResponse> withdraw(@RequestBody WithdrawRequest request) throws WalletBusinessException {
        WalletValidator.validateWithdrawRequest(request);

        TopUpResponse response = new TopUpResponse();
        Wallet wallet = walletService.withdraw(request);
        response.setWalletDto(WalletMapper.convertToDto(wallet));
        response.setResult(ErrorCodes.success());

        return ResponseEntity.ok(response);
    }

}
