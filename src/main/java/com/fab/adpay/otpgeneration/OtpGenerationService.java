package com.fab.adpay.otpgeneration;

import com.fab.adpay.utils.HttpUtils;
import com.fab.adpay.walletInquiry.WalletInquiryRequest;
import com.fab.adpay.walletInquiry.WalletInquiryResponse;
import com.fab.adpay.walletInquiry.WalletInquiryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class OtpGenerationService {

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(OtpGenerationService.class);

    @Autowired
    WalletInquiryService walletInquiryService;

    public GenerateOtpServiceRequest buildGenerateOtpServiceRequest(String mobileNumber, String prefLanguage) {
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
        LOGGER.info("language : " + prefLanguage);

        if(prefLanguage.contentEquals("1")){
            applicationArea.setLanguage("EN");
        } else if (prefLanguage.contentEquals("2")) {
            applicationArea.setLanguage("AR");
        }else{
            applicationArea.setLanguage("EN");
        }

//        applicationArea.setCreationDateTime(UUID.randomUUID().toString());
//        applicationArea.setRequiredExecutionDate(UUID.randomUUID().toString());

        RequestDataArea requestDataArea = new RequestDataArea();

        requestDataArea.setMobileNumber(mobileNumber);
        requestDataArea.setEmailID(null);
        requestDataArea.setTransactionCode("ADP_TXN_OTP");
        requestDataArea.setDigitsInOTP(6);
        requestDataArea.setSplitOTP("N");
        requestDataArea.setExpiryTime(180);
        requestDataArea.setAlertType("SMS");
        requestDataArea.setMaxFailedAttempt(3);
        requestDataArea.setSendAsAttachement(null);
        requestDataArea.setTemplateName(null);

        requestDataArea.setIdentityDetails(Collections.EMPTY_LIST);
        requestDataArea.setTemplateAttributes(Collections.EMPTY_LIST);


        GenerateOtpServiceRequest generateOtpServiceRequest = new GenerateOtpServiceRequest();
        generateOtpServiceRequest.setApplicationArea(applicationArea);
        generateOtpServiceRequest.setDataArea(requestDataArea);
        return generateOtpServiceRequest;
    }

    public OtpGenerationResponse createOtp(Map<String, String> headers, OtpGenerationRequest request) throws Exception {
        OtpGenerationResponse otpGenerationResponse = new OtpGenerationResponse();
        String mobileNumber = fetchMobileNumberByCardId(headers, request);
        String prefLanguage = fetchLanguageByCardId(headers, request);
        GenerateOtpServiceRequest generateOtpServiceRequest = buildGenerateOtpServiceRequest(mobileNumber,prefLanguage);
        String referenceNumber = fetchReferenceNumberFromExternalOTPGenerationApi(generateOtpServiceRequest);
        otpGenerationResponse.setReferenceNumber(referenceNumber);
        otpGenerationResponse.setWalletId(request.getValue());
        return otpGenerationResponse;
    }
    public String fetchMobileNumberByCardId(Map<String, String> headers, OtpGenerationRequest request) throws Exception {
        String cardId = request.getValue();
        String mobileNumber = "";
        WalletInquiryRequest walletInquiryRequest = new WalletInquiryRequest();
        walletInquiryRequest.setIdentityType(0);
        walletInquiryRequest.setIdentityNumber("");
        walletInquiryRequest.setWalletId(request.getValue());
        WalletInquiryResponse walletInquiryResponse = walletInquiryService.walletInquiry(headers, walletInquiryRequest);
        LOGGER.info("WalletInquiryResponse : " + objectMapper.writeValueAsString(walletInquiryResponse));
        System.out.println(walletInquiryResponse.getMobile());

        String convertedMobileNumber = walletInquiryResponse.getMobile().replaceAll("[-]", "");
        System.out.println(convertedMobileNumber);



        return convertedMobileNumber;
    }


    public String fetchLanguageByCardId(Map<String, String> headers, OtpGenerationRequest request) throws Exception {
        String cardId = request.getValue();
        String mobileNumber = "";
        WalletInquiryRequest walletInquiryRequest = new WalletInquiryRequest();
        walletInquiryRequest.setIdentityType(0);
        walletInquiryRequest.setIdentityNumber("");
        walletInquiryRequest.setWalletId(request.getValue());
        WalletInquiryResponse walletInquiryResponse = walletInquiryService.walletInquiry(headers, walletInquiryRequest);
        LOGGER.info("WalletInquiryResponse : " + objectMapper.writeValueAsString(walletInquiryResponse));
//        System.out.println(walletInquiryResponse.getWalletInquiryDataList().get(0).getMobile());
        String prefLanguage = walletInquiryResponse.getPreferredLanguage();
//        String convertedMobileNumber = walletInquiryResponse.getWalletInquiryDataList().get(0).getMobile().replaceAll("[-]", "");
//        System.out.println(convertedMobileNumber);



        return prefLanguage;
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
