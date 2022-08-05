package com.fab.adpay.customerOnboard;

import com.fab.adpay.customerOnboard.model.RequestData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BPMSRequest {
    private String requestID;
    private String requestTimeStamp;
    private String channelID;
    private RequestData requestData;
}
