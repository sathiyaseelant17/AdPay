/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.walletStatusUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Hariharasudhan
 */
@Data
@NoArgsConstructor
public class WalletStatusUpdateResponse {

	private int errorCode;
	private String errorText;
}
