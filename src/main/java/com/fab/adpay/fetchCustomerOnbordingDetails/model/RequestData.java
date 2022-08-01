package com.fab.adpay.fetchCustomerOnbordingDetails.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestData {

    private String applicationRefNo;
    private String emiratesID;
    private String customerID;
    private String mobileNumber;
}
