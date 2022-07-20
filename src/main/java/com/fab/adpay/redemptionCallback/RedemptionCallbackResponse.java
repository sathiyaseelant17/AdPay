package com.fab.adpay.redemptionCallback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedemptionCallbackResponse {
    private String redeemAckRef;
    private int errorCode;
    private String errorText;

}
