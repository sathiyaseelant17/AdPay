package com.fab.adpay.customerOnboard;

import com.fab.adpay.customerOnboard.model.ResponseData;
import com.fab.adpay.customerOnboard.model.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BPMSResponse {

    private String requestID;
    private String responseTimeStamp;
    private String channelID;
    private ResponseStatus responseStatus;
    private String websitePageIdentifier;
    private ResponseData responseData;

}
