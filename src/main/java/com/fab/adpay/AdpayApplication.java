package com.fab.adpay;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.boot.SpringApplication;
import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.io.*;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AdpayApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, 4);
		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
		System.out.println(timestamp);

		System.out.println("tds"+new Timestamp(new Date().getTime()));

		/* Start of Fix */
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		   public void checkClientTrusted(X509Certificate[] certs, String authType) {
		   }

		   public void checkServerTrusted(X509Certificate[] certs, String authType) {
		   }

		   public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		      return null;
		   }

		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
		   public boolean verify(String hostname, SSLSession session) {
		      return true;
		   }
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		/* End of the fix */
		SpringApplication.run(AdpayApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
