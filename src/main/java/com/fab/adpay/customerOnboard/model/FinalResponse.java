package com.fab.adpay.customerOnboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalResponse {
    private String status;
    private String errorDescription;
    private String applicationRefNo;
    private String applicationCreatedDate;
    private String applicationExpiryDate;
    private String applicationRemarks;
    private String applicationStatus;
}
