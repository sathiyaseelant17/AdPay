package com.fab.adpay.otpgeneration;

import com.fab.adpay.walletInquiry.WalletInquiryRequest;
import com.fab.adpay.walletInquiry.WalletInquiryResponse;
import com.fab.adpay.walletInquiry.WalletInquiryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.fab.adpay.utils.HttpUtils;

@Service
public class OtpGenerationService {

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(OtpGenerationService.class);

    @Autowired
    WalletInquiryService walletInquiryService;

    public GenerateOtpServiceRequest buildGenerateOtpServiceRequest(String mobileNumber) {
        ApplicationArea applicationArea = new ApplicationArea();
        applicationArea.setCountryOfOrigin("AE");
        applicationArea.setSenderId("STL");
        applicationArea.setTransactionId(UUID.randomUUID().toString());
        String currentTimestamp = new Timestamp(new Date().getTime()).toString();
        applicationArea.setTransactionDateTime(currentTimestamp);
        applicationArea.setLanguage("EN");
        RequestDataArea requestDataArea = new RequestDataArea();
        requestDataArea.setMobileNumber(mobileNumber);
        requestDataArea.setTransactionCode("TXN_CARD_DET");
        requestDataArea.setDigitsInOTP(6);
        requestDataArea.setSplitOTP("N");
        requestDataArea.setExpiryTime(180);
        requestDataArea.setAlertType("SMS");
        requestDataArea.setMaxFailedAttempt(3);
        GenerateOtpServiceRequest generateOtpServiceRequest = new GenerateOtpServiceRequest();
        generateOtpServiceRequest.setApplicationArea(applicationArea);
        generateOtpServiceRequest.setDataArea(requestDataArea);
        return generateOtpServiceRequest;
    }

    public OtpGenerationResponse createOtp(Map<String, String> headers, OtpGenerationRequest request) throws Exception {
        OtpGenerationResponse otpGenerationResponse = new OtpGenerationResponse();
        String mobileNumber = fetchMobileNumberByCardId(headers, request);
        GenerateOtpServiceRequest generateOtpServiceRequest = buildGenerateOtpServiceRequest(mobileNumber);
        String referenceNumber = fetchReferenceNumberFromExternalOTPGenerationApi(generateOtpServiceRequest);
        otpGenerationResponse.setReferenceNumber(referenceNumber);
        otpGenerationResponse.setCardId(request.getValue());
        return otpGenerationResponse;
    }

    public String fetchMobileNumberByCardId(Map<String, String> headers, OtpGenerationRequest request) throws Exception {
        String cardId = request.getValue();
        String mobileNumber = "";
        WalletInquiryRequest walletInquiryRequest = new WalletInquiryRequest();
        walletInquiryRequest.setIdentityType(request.getRequestMode());
        walletInquiryRequest.setIdentityNumber(request.getValue());
        WalletInquiryResponse walletInquiryResponse = walletInquiryService.walletInquiry(headers, walletInquiryRequest);
        LOGGER.info("WalletInquiryResponse : " + objectMapper.writeValueAsString(walletInquiryResponse));
        return "";
    }

    public String fetchReferenceNumberFromExternalOTPGenerationApi(GenerateOtpServiceRequest generateOtpServiceRequest)
            throws Exception {
        String url = System.getenv("OTP_GENERATION_URL");
        String referenceNumber = "";

        OkHttpClient client = HttpUtils.getUnsafeOkHttpClient();
        RequestBody body = RequestBody.create(objectMapper.writeValueAsString(generateOtpServiceRequest),
                MEDIA_TYPE_JSON);
        LOGGER.debug("Generate Otp Request Body : " + objectMapper.writeValueAsString(generateOtpServiceRequest));
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody;
        if (response.isSuccessful()) {
            responseBody = response.body().string();
            LOGGER.debug("Successful Generate Otp Response : " + responseBody);
            GenerateOtpServiceResponse generateOtpServiceResponse = objectMapper.readValue(responseBody,
                    GenerateOtpServiceResponse.class);
            referenceNumber = generateOtpServiceResponse.getDataArea().getReferenceNumber();
        } else {
            responseBody = response.body().string();
            LOGGER.debug("Failure Generate Otp Response : " + responseBody);
            throw new Exception("An error occured while fetching mobileNumber from Elpaso");
        }
        return referenceNumber;
    }


}
