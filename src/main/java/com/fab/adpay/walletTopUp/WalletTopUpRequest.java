package com.fab.adpay.walletTopUp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WalletTopUpRequest {

    private String walletId;
    private int txntype;
    private int transactionSource;
    private String txnIdentifier;
    private String sourceMakerId;
    private String sourcePosId;
    private String sourceTxnReferenceNo;
    private String onlHostReferenceNo;
    private String txnReferenceNo;
    private String remarks;
    private int feeType;
    private int feeAmount ;
    private String feeAc;
    private String feeDescription;
    private BigDecimal transactionAmount;
    private BigDecimal equivalentAmount;
    private BigDecimal transactionCurrency;
    private BigDecimal equivalentCurrency;
    private int transactionRate;
    private String authorizationCode;
    private String merchantLocation;
    private int checkExpiryFlag;
    private String merchantName;
    private String rrn;
    private String sourceMakerDateTime;
    private String sourceAuthId;
    private String sourceAuthDateTime;
    private String authComments;
    private int surrenderFlag;
    private int closeCardFlag;
    private String loadId;
    private String transactionDateTime;
    private String mcc;

}

