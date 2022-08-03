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

    private String requestId;
    private String requestTimeStamp;
    private String channelId;
    private ResponseStatus responseStatus;
    private String websitePageIdentifier;
    private ResponseData responseData;

}
