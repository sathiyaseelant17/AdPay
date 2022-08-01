package com.fab.adpay.fetchCustomerOnbordingDetails;


import com.fab.adpay.fetchCustomerOnbordingDetails.model.ResponseData;
import com.fab.adpay.fetchCustomerOnbordingDetails.model.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchDetailsResponse {
    private String requestID;
    private String responseTimeStamp;
    private String channelID;
    private ResponseStatus responseStatus;
    private ResponseData responseData;
}
