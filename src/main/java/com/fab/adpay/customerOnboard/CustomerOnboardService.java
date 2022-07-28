/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.customerOnboard;

import java.util.Arrays;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class CustomerOnboardService {

    @Autowired
    RestTemplate restTemplate;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(CustomerOnboardService.class);

    public CustomerOnboardResponse customerOnboard(Map<String, String> headers, CustomerOnboardRequest request) throws Exception {

        String URL = System.getenv("INITIATE_BPMS");
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<CustomerOnboardRequest> entity = new HttpEntity<CustomerOnboardRequest>(request, header);
        CustomerOnboardResponse response=new CustomerOnboardResponse();
       try {
           logger.info("Transaction Id : {}",URL);
           ResponseEntity<String> responseEntity =restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
           logger.info("Transaction Id : {} BPMS Status Code: {}",request.getRequestID(),responseEntity.getStatusCode());
           if (responseEntity.getStatusCode() == HttpStatus.OK) {
             String bpmsResponse = responseEntity.getBody();
             logger.info("Transaction Id : {} Response body: {}",request.getRequestID(),bpmsResponse);
             response = OBJECT_MAPPER.readValue(bpmsResponse,CustomerOnboardResponse.class);
        }else {
            throw new Exception("BPMS Service Response status fails");
        }
       }catch(Exception e){
           logger.info("Transaction Id : {}",e);
           throw new Exception("Error while connecting to BPMS service");
       }
return response;
    }

}
