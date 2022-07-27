package com.fab.adpay.fetchCustomerOnbordingDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public FetchDetailsResponse fetchCustomerOnboardingDetails(Map<String, String> headers, FetchDetailsRequest request) throws JsonProcessingException {

        String URL = System.getenv("BPMS_ENQUIRY");
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<FetchDetailsRequest> entity = new HttpEntity<FetchDetailsRequest>(request, header);
        FetchDetailsResponse response=new FetchDetailsResponse();
        try {
            ResponseEntity<String> responseEntity =restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
            logger.info("Transaction Id : {} BPMS Status Code: {}",headers.get("transactionid"),responseEntity.getStatusCode());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String bpmsResponse = responseEntity.getBody();
                logger.info("Transaction Id : {} Response body: {}",headers.get("transactionid"),bpmsResponse);
                response = OBJECT_MAPPER.readValue(bpmsResponse,FetchDetailsResponse.class);
            }else {
                throw new RuntimeException("BPMS Service Response status fails");
            }
        }catch(Exception e){
            throw new RuntimeException("Error while connecting to BPMS service");
        }
        return response;
    }

}
