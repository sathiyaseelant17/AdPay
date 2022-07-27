package com.fab.adpay.fetchCustomerOnbordingDetails.model;

import com.fab.adpay.customerOnboard.model.CustomerDetails;
import com.fab.adpay.customerOnboard.model.FatcaCRSDetails;
import com.fab.adpay.fetchCustomerOnbordingDetails.ApplicationDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private ApplicationDetails applicationDetails;
    private String product;
    private String UAEPassResult;
    private String TandCAgreed;
    private String websitePageIdentifier;
    private List<String> documentDetails;
    private String actionName;
    private CustomerDetails customerDetails;
    private FatcaCRSDetails fatcaCRSDetails;
}