package com.fab.adpay.redemptionInquiry;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class RedemptionInquiryRequest {

    private String cardId;
    private int transactionType;
    private String sourceMakerId;
    private String sourcePosId;
    private String sourceTransactionRef;
    private String redeemAcknowledgementRef;
    private int redeemStatus;

}
