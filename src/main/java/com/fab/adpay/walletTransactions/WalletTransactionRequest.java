package com.fab.adpay.walletTransactions;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class WalletTransactionRequest {

    private String cardId;
    private int transactionSource;
    private String sourceMakerId;
    private String sourcePosId;
    private String sourceTxnRef;
    private String onlHostRefNo;
    private String txnRefNo;
    private String desc1;
    private int feeType;
    private BigDecimal feeAmount;
    private BigDecimal txnAmount;
    private BigDecimal billAmount;
    private String txnCurrCode;
    private String billCurrCode;
    private int txnRate;
    private String authCode;
    private String merchantlocation;
    private String expiryDate;
    private int checkExpiryFlag;
    private String retrievalRefNo;
    private String passCode;
    private String freezeExpiryDate;
    private int begintranflag;
    private String desc2;
    private int txnType;
    private String merchantcategorycode;
    private String requestRcvTime;
    private String adgeId;
    private String serviceId;
    private String feeDesc;
}
