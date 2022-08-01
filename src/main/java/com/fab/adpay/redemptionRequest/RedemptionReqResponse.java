package com.fab.adpay.redemptionRequest;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@ToString
public class RedemptionReqResponse {
    private String cardId;
    private int errorCode;
    private String errorText;
    private BigDecimal availableBalance;
    private BigDecimal currentBalance;
    private String redeemAcknowledgementRef;


}
