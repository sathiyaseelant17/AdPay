package com.fab.adpay.otpvalidation;

import com.fab.adpay.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class OtpValidationService {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");
    public static final Logger LOGGER = LoggerFactory.getLogger(OtpValidationService.class);
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public OtpValidationApiPayload buildOtpValidationApiPayload(OtpValidationRequest request) {
        ApplicationArea applicationArea = new ApplicationArea();
        applicationArea.setCorrelationId(UUID.randomUUID().toString());
        applicationArea.setInterfaceID(null);
        applicationArea.setCountryOfOrigin("AE");
        applicationArea.setSenderId("STL");
        applicationArea.setSenderUserId(null);
        applicationArea.setSenderBranchId(null);
        applicationArea.setSenderAuthorizationId(null);
        applicationArea.setSenderReferenceId(null);
        applicationArea.setTransactionId(UUID.randomUUID().toString());
        DateTime now = new DateTime();
        DateTime currentTimestamp =  now.toDateTime(DateTimeZone.UTC);

        applicationArea.setTransactionDateTime(currentTimestamp.toString());
        applicationArea.setTransactionTimeZone(null);
        applicationArea.setSenderAuthorizationComments(null);
        applicationArea.setLanguage("EN");
//        applicationArea.setCreationDateTime(UUID.randomUUID().toString());
//        applicationArea.setRequiredExecutionDate(UUID.randomUUID().toString());
        OtpValidationApiPayload otpValidationApiPayload = new OtpValidationApiPayload();
        otpValidationApiPayload.setApplicationArea(applicationArea);
        otpValidationApiPayload.setDataArea(request);
        return otpValidationApiPayload;
    }

    public OtpValidationResponse validateOtp(Map<String, String> headers, OtpValidationRequest request)
            throws Exception {
        OtpValidationApiPayload otpValidationApiPayload = buildOtpValidationApiPayload(request);
        OtpValidationResponse response = doOtpValidationByExternalApi(otpValidationApiPayload);
        return response;
    }

    public OtpValidationResponse doOtpValidationByExternalApi(OtpValidationApiPayload otpValidationApiPayload)
            throws Exception {
        String url = System.getenv("OTP_VALIDATION_URL");
        LOGGER.debug("OTP_VALIDATION_URL : " + url);
        OkHttpClient client = HttpUtils.getUnsafeOkHttpClient();
        LOGGER.debug("Validate Otp Request Body : " + OBJECT_MAPPER.writeValueAsString(otpValidationApiPayload));
        RequestBody body = RequestBody.create(OBJECT_MAPPER.writeValueAsString(otpValidationApiPayload),
                MEDIA_TYPE_JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        OtpValidationApiResponse otpValidationApiResponse;
        String responseBody;
        OtpValidationResponse otpValidationResponse = new OtpValidationResponse();
        if (response.isSuccessful()) {
            responseBody = response.body().string();
            LOGGER.debug("Successful Otp Validation Response : " + responseBody);
            otpValidationApiResponse = objectMapper.readValue(responseBody, OtpValidationApiResponse.class);
            if (otpValidationApiResponse.getResponseStatus().getStatus().equals("SUCCESS")
                    && otpValidationApiResponse.getDataArea().isOtpMatched()) {
                otpValidationResponse.setSuccess(true);
                otpValidationResponse.setStatusCode("0");
                otpValidationResponse.setStatusText("");
                return otpValidationResponse;
            } else if (otpValidationApiResponse.getResponseStatus().getStatus().equals("ERROR")) {
                otpValidationResponse.setSuccess(false);
                otpValidationResponse.setStatusCode(
                        otpValidationApiResponse.getResponseStatus().getErrorDetails().get(0).getErrorCode());
                otpValidationResponse.setStatusText(
                        otpValidationApiResponse.getResponseStatus().getErrorDetails().get(0).getErrorDesc());
                return otpValidationResponse;
            } else {
                throw new Exception("Error occurred while validating OTP");
            }
        } else {
            responseBody = response.body().string();
            LOGGER.debug("Failure Otp Validation Response : " + responseBody);
            otpValidationApiResponse = objectMapper.readValue(responseBody, OtpValidationApiResponse.class);
            if (otpValidationApiResponse.getResponseStatus().getStatus().equals("ERROR")) {
                otpValidationResponse.setSuccess(false);
                otpValidationResponse.setStatusCode(
                        otpValidationApiResponse.getResponseStatus().getErrorDetails().get(0).getErrorCode());
                otpValidationResponse.setStatusText(
                        otpValidationApiResponse.getResponseStatus().getErrorDetails().get(0).getErrorDesc());
                return otpValidationResponse;
            } else {
                throw new Exception("Error occurred while validating OTP");
            }
        }
    }

}
