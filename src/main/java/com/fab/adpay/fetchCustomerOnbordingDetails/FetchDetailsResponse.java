package com.fab.adpay.fetchCustomerOnbordingDetails;

import com.fab.adpay.customerOnboard.model.ResponseStatus;
import com.fab.adpay.fetchCustomerOnbordingDetails.model.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchDetailsResponse {
    private ResponseStatus responseStatus;
    private ResponseData responseData;
}
