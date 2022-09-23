package com.fab.adpay.fetchCustomerOnbordingDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@Service
public class FetchDetailsService {
    @Autowired
    RestTemplate restTemplate;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(FetchDetailsService.class);

    public JSONObject fetchCustomerOnboardingDetails(Map<String, String> headers, FetchDetailsRequest request) throws Exception {

        String URL = System.getenv("BPMS_ENQUIRY");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FetchDetailsRequest> entity = new HttpEntity<FetchDetailsRequest>(request, header);
        logger.info("Transaction Id : {} BPMS request entity: {}",headers.get("transactionid"),entity);
        JSONObject response=new JSONObject();
            logger.info("Transaction Id : {}",URL);
            ResponseEntity<String> responseEntity =restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
            logger.info("Transaction Id : {} BPMS Status Code: {}",headers.get("transactionid"),responseEntity.getStatusCode());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String bpmsFetchResponse = responseEntity.getBody();
                logger.info("Transaction Id : {} Response body fetch: {}",headers.get("transactionid"),bpmsFetchResponse);
//                response = OBJECT_MAPPER.readValue(bpmsFetchResponse,FetchDetailsResponse.class);
                    response = new JSONObject(bpmsFetchResponse);
                logger.info("Transaction Id : {} Response body fetch: {}",headers.get("transactionid"),response);

            }else {
                logger.info("Transaction Id : {} BPMS Status Code: {}",headers.get("transactionid"),responseEntity.getStatusCode());
                throw new Exception("BPMS Service Response status fails");
            }
        logger.info("Transaction Id : {} BPMS Status Code: {}",headers.get("transactionid"), response);

        return response;
    }

}
