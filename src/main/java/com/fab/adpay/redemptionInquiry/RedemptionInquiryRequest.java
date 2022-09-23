package com.fab.adpay.redemptionInquiry;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class RedemptionInquiryRequest {

    private String walletId;
    private int transactionType;
    private int transactionSource;
    private String sourceMakerId;
    private String sourcePosId;
    private String sourceTransactionRef;
    private String redeemAcknowledgementRef;

}
