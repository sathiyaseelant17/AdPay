package com.fab.adpay.addWallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddWalletResponse {

    private String newWalletId;
    private String statusText;
    private int statusCode;
}
