package com.fab.adpay.walletInquiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletInquiryData {
    private String walletId;
    private String walletLabel;
    private String defaultWallet;
    private String walletType;
    private String walletStatus;
    private String createDate;
    private String lastTransactionDate;
    private String lastTopupAmount;
    private String lastTopupDate;
    private BigDecimal walletSpendLimitPerTransaction;
    private BigDecimal availableBalance;
    private BigDecimal currentBalance;
    private List<PreApproved> preApproved=new ArrayList<PreApproved>();
    private String kycFlag;

}
