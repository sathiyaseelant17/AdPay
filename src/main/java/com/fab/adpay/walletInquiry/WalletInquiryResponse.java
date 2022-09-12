/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.walletInquiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletInquiryResponse {

    private String statusText;
    private int statusCode;
    private String firstNameEnglish;
    private String middleNameEnglish;
    private String lastNameEnglish;
    private String firstNameArabic;
    private String middleNameArabic;
    private String lastNameArabic;
    private String mobile;
    private String email;
    private BigDecimal customerLimit;
    private String preferredLanguage;
    private List<WalletInquiryData> walletInquiryDataList;
    private String applicationRefNo;
    private String applicationCreatedDate;
    private String applicationExpiryDate;
    private String applicationRemarks;
    private String applicationStatus;


}
