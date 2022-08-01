package com.fab.adpay.customerOnboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestData {

    private String applicationRefNo;
    private String applicationStatus;
    private String applicationRemarks;
    private String product;
    private String UAEPassResult;
    private String TandCAgreed;
    private String websitePageIdentifier;
    private List<String> documentDetails;
    private String actionName;
    private CustomerDetails customerDetails;
    private FatcaCRSDetails fatcaCRSDetails;

}
