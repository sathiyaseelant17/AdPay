package com.fab.adpay.redemptionInquiry;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@ToString
public class RedemptionInquiryResponse {

    private int statusCode;
    private String statusText;
    private BigDecimal availableBalance;
    private BigDecimal currentBalance;

}
