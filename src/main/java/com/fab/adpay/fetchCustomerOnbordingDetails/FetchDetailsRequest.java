package com.fab.adpay.fetchCustomerOnbordingDetails;

import com.fab.adpay.fetchCustomerOnbordingDetails.model.RequestData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchDetailsRequest {
    private String requestID;
    private String requestTimeStamp;
    private String channelID;
    private RequestData requestData;
}
