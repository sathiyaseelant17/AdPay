package com.fab.adpay.voidService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class VoidServiceRequest {

    private String cardId;
    private String txnIdentifier;
    private String orgTxnIdentifier;
    private String sourceMakerId;
    private String sourcePosId;
    private int technicalRevFlag;
    private String requestRcvTime;
    private String URN;
    private String org_URN;
    private String loanId   ;
    private String sourceTxnReferenceNo;
    private String nmc;
    private String ageId;
    private String serviceId;




}
