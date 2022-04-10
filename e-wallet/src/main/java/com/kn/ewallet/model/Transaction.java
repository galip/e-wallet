package com.kn.ewallet.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kn.ewallet.base.BaseEntity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "TRANSACTION")
@Accessors(chain = true)
public class Transaction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "transaction_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "transaction_seq_generator", sequenceName = "transaction_seq")
    private Long id;

    @Column(name = "SENDER_ID")
    private Long senderId;
    
    @Column(name = "RECEIVER_ID")
    private Long receiverId;
    
    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "SENDER_BALANCE")
    private BigDecimal senderBalance;

    @Column(name = "SENDER_PREVIOUS_BALANCE")
    private BigDecimal senderPreviousBalance;
    
    @Column(name = "RECEIVER_BALANCE")
    private BigDecimal receiverBalance;

    @Column(name = "RECEIVER_PREVIOUS_BALANCE")
    private BigDecimal receiverPreviousBalance;

    @Column(name = "TYPE")
    private String type; // TOPUP, WITHDRAW
    
    @Column(name = "TOKEN")
    private String token;

}