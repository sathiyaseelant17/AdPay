package com.fab.adpay.voidService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class VoidServiceRequest {

    private String walletId;
    private String txnIdentifier;
    private int transactionSource;
    private String orgTxnIdentifier;
    private String sourceMakerId;
    private String sourcePosId;
    private int technicalRevFlag;
    private String requestRcvTime;
    private String URN;
    private String org_URN;
    private String loanId;
    private String sourceTxnReferenceNo;
    private String mcc;
    private String adgeId;
    private String serviceId;


}
