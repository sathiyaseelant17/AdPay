package com.fab.adpay.walletToWalletTransactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WalletToWalletTransactionRequest {

    private String walletId_01;
    private String walletId_02;
    private String rrn;
    private BigDecimal transactionAmount;
    private String transactionCurrency;
    private BigDecimal equivalentAmount;
    private String equivalentCurrency;
    private String authorizationCode;
    private BigDecimal transactionRate;
    private String remarks;
    private String transactionDateTime;
    private BigDecimal feeAmount;
    private String feeDescription;


}
