/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.customerOnboard;

import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class CustomerOnboardService {

    @Autowired
    RestTemplate restTemplate;

    public Object customerOnboard(Map<String, String> headers, CustomerOnboardRequest request) {

        String URL = "";
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        header.set("requestID", headers.get("requestId"));
        header.set("requestTimeStamp", headers.get("requestTimeStamp"));
        header.set("channelID", headers.get("channelID"));
        HttpEntity<CustomerOnboardRequest> entity = new HttpEntity<CustomerOnboardRequest>(request, header);

        return restTemplate.exchange(URL, HttpMethod.POST, entity, Object.class).getBody();
    }

}
