package com.fab.adpay.redemptionRequest;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class RedemptionReqRequest {

    private String walletId;
    private int transactionSource;
    private int transactionType;
    private String sourceMakerId;
    private String sourcePosId;
    private String sourceTransactionRef;
    private int transactionAmount;
    private int billAmount;
    private String transactionCurrencyCode;
    private String billCurrencyCode;
    private int transactionRate;
    private String paymentCurrencyCode;
    private String desc1;
    private String desc2;
    private String senderName;
    private String senderAddress1;
    private String senderAddress2;
    private String senderAddress3;
    private String senderAddress4;
    private int entityCode;
    private String beneficiaryBankSwiftCode;
    private String beneficiaryIBAN;
    private String benefiniciaryAccNo;
    private String beneficiaryName;
    private String beneficiaryNickName;
    private String beneficiaryAddress1;
    private String beneficiaryAddress2;
    private String beneficiaryAddress3;
    private String beneficiaryAddress4;
    private String beneficiaryPhone;
    private String beneficiaryEmail;
    private String beneficiaryBankName;
    private String beneficiaryBankAddress1;
    private String beneficiaryBankAddress2;
    private String beneficiaryBankAddress3;
    private String beneficiaryBankBic;

}

