package com.fab.adpay.kycUpload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DMSResponse {

    private int statusCode;
    private String statusText;
    private List<DMSConfigurationElpResponse> dmsConfigurationElpResponsesList;
}
