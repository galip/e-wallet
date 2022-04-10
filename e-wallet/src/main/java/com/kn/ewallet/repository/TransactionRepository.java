package com.kn.ewallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kn.ewallet.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findAllByType(String transactionType);
    
    public List<Transaction> findBySenderId(Long senderId);
    
    public List<Transaction> findBySenderIdAndType(Long senderId, String transactionType);
    
}