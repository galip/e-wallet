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
@Table(name = "WALLET")
@Accessors(chain = true)
public class Wallet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "wallet_seq", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "wallet_seq_generator", sequenceName = "wallet_seq")
    private Long id;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @Column(name = "PREVIOUS_BALANCE")
    private BigDecimal previousBalance;

    @Column(name = "STATUS")
    private String status;

}