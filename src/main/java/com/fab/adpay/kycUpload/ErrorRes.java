package com.fab.adpay.kycUpload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorRes {
	private Integer statusCode;
	private String statusMsg;
}