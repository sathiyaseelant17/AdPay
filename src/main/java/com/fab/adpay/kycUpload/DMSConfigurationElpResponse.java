package com.fab.adpay.kycUpload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DMSConfigurationElpResponse {

    private String documentType;
    private String docDtlAttAclName;
    private String docDtlAttAclDomain;
    private String objectFolder;
    private String dirDtlAttAclName;
    private String dirDtlAttAclDomain;
    private String docDtlAttAclDocType;
    private String docDtlAttAclObjName;
    private String docDtlAttAclCardId;
    private String fileName;

}
