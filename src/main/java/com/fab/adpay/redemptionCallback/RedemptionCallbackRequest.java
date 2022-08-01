package com.fab.adpay.redemptionCallback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedemptionCallbackRequest {

    private String cardId;
    private int transactionSource;
    private int transactionType;
    private String sourceMakerId;
    private String sourcePosId;
    private String sourceTxnRef;
    private String redeemAckRef;
    private int redeemStatus;
    private String redeemDateTime;
    private String redeemDeclCode;
    private String redeemDeclDesc;
}
