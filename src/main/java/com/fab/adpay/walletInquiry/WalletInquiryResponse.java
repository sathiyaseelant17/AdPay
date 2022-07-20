/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.walletInquiry;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WalletInquiryResponse {

    private int errorCode;
    private String errorText;
    private List<WalletInquiryData> walletInquiryDataList;
}
