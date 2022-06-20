package com.fab.adpay.walletTransactions;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class WalletTransactionRequest {

    private String cardNo;
    private String cardId;
    private String txnIdentifier;
    private int txnSource;
    private String sourceMakerId;
    private String sourcePosId;
    private String sourceTxnRef;
    private String onlHostRefNo;
    private String txnRefNo;
    private String desc1;
    private int feeType;
    private int feeAmount;
    private int txnAmount;
    private int billAmount;
    private String txnCurrCode;
    private String billCurrCode;
    private int txnRate;
    private int donationAmount;
    private String retrievalRefNo;
    private String transitAc;
    private String merchantLocation;
    private int checkExpiryFlag;
    private String expiryDate;
    private int txtType;
    private String loanId;








}
