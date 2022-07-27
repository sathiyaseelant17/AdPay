package com.fab.adpay.fetchCustomerOnbordingDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDetails {

    private String applicationRefNo;
    private String applicationStatus;
    private String applicationRemarks;
    private String applicationCreatedDate;
    private String applicationExpiryDate;
    private String applicationRejectedDate;
    private String applicationClosedDate;
    private String applicationSource;

}
