package com.fab.adpay.kycUpload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DMSResponse {

    private int errorCode;
    private String errorText;
    private List<DMSConfigurationElpResponse> dmsConfigurationElpResponsesList;
}
