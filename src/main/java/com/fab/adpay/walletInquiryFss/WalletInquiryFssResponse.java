package com.fab.adpay.walletInquiryFss;

import com.fab.adpay.walletInquiry.WalletInquiryData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletInquiryFssResponse {
    private String statusText;
    private int statusCode;
    private List<WalletInquiryDataFss> walletInquiryDataList;
}
