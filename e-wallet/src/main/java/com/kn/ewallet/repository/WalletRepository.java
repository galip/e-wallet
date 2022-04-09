package com.kn.ewallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kn.ewallet.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    
    public Wallet findByCustomerIdAndStatus(Long customerId, String status);

}