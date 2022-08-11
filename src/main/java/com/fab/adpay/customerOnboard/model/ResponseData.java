package com.fab.adpay.customerOnboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {

    private String applicationRefNo;
    private String applicationCreatedDate;
    private String applicationExpiryDate;
    private String applicationSource;
    private String applicationRemarks;
    private String applicationStatus;

}
