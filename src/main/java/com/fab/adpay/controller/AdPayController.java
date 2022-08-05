package com.fab.adpay.controller;

import com.fab.adpay.addWallet.AddWalletRequest;
import com.fab.adpay.addWallet.AddWalletResponse;
import com.fab.adpay.addWallet.AddWalletService;
import com.fab.adpay.customerOnboard.BPMSResponse;
import com.fab.adpay.customerOnboard.CustomerOnboardRequest;
import com.fab.adpay.customerOnboard.CustomerOnboardResponse;
import com.fab.adpay.customerOnboard.CustomerOnboardService;
import com.fab.adpay.fetchCustomerOnbordingDetails.FetchDetailsRequest;
import com.fab.adpay.fetchCustomerOnbordingDetails.FetchDetailsResponse;
import com.fab.adpay.fetchCustomerOnbordingDetails.FetchDetailsService;
import com.fab.adpay.kycUpload.DMSConfiguration;
import com.fab.adpay.kycUpload.KycUploadRequest;
import com.fab.adpay.kycUpload.KycUploadResponse;
import com.fab.adpay.kycUpload.KycUploadService;
import com.fab.adpay.preApproval.PreApprovalRequest;
import com.fab.adpay.preApproval.PreApprovalResponse;
import com.fab.adpay.preApproval.PreApprovalService;
import com.fab.adpay.redemptionCallback.RedemptionCallbackRequest;
import com.fab.adpay.redemptionCallback.RedemptionCallbackResponse;
import com.fab.adpay.redemptionCallback.RedemptionCallbackService;
import com.fab.adpay.redemptionInquiry.RedemptionInquiryRequest;
import com.fab.adpay.redemptionInquiry.RedemptionInquiryResponse;
import com.fab.adpay.redemptionInquiry.RedemptionInquiryService;
import com.fab.adpay.transactionHistory.TransactionHistoryRequest;
import com.fab.adpay.transactionHistory.TransactionHistoryResponse;
import com.fab.adpay.transactionHistory.TransactionHistoryService;
import com.fab.adpay.updateCustomer.UpdateCustomerRequest;
import com.fab.adpay.updateCustomer.UpdateCustomerResponse;
import com.fab.adpay.updateCustomer.UpdateCustomerService;
import com.fab.adpay.voidService.VoidService;
import com.fab.adpay.voidService.VoidServiceRequest;
import com.fab.adpay.voidService.VoidServiceResponse;
import com.fab.adpay.walletInquiry.WalletInquiryRequest;
import com.fab.adpay.walletInquiry.WalletInquiryResponse;
import com.fab.adpay.walletInquiry.WalletInquiryService;
import com.fab.adpay.walletLimit.UpdateWalletLimitRequest;
import com.fab.adpay.walletLimit.UpdateWalletLimitResponse;
import com.fab.adpay.walletLimit.UpdateWalletLimitService;
import com.fab.adpay.walletStatusUpdate.WalletStatusUpdateRequest;
import com.fab.adpay.walletStatusUpdate.WalletStatusUpdateResponse;
import com.fab.adpay.walletStatusUpdate.WalletStatusUpdateService;
import com.fab.adpay.walletToWalletTransactions.WalletToWalletTransactionRequest;
import com.fab.adpay.walletToWalletTransactions.WalletToWalletTransactionResponse;
import com.fab.adpay.walletToWalletTransactions.WalletToWalletTransactionService;
import com.fab.adpay.walletTopUp.WalletTopUpRequest;
import com.fab.adpay.walletTopUp.WalletTopUpResponse;
import com.fab.adpay.walletTopUp.WalletTopUpService;
import com.fab.adpay.walletTransactions.WalletTransactionRequest;
import com.fab.adpay.walletTransactions.WalletTransactionResponse;
import com.fab.adpay.walletTransactions.WalletTransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Map;
import javax.validation.Valid;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AdPayController {

    @Autowired
    TransactionHistoryService transactionHistoryService;

    @Autowired
    VoidService voidService;

    @Autowired
    WalletTransactionService walletTransactionService;

    @Autowired
    WalletStatusUpdateService walletStatusUpdateService;

    @Autowired
    WalletTopUpService walletTopUpService;

    @Autowired
    UpdateCustomerService updateCustomerService;

    @Autowired
    PreApprovalService preApprovalService;

    @Autowired
    WalletInquiryService walletInquiryService;

    @Autowired
    AddWalletService addWalletService;

    @Autowired
    WalletToWalletTransactionService walletToWalletTransactionService;

    @Autowired
    UpdateWalletLimitService updateWalletLimitService;

    @Autowired
    CustomerOnboardService customerOnboardService;

    @Autowired
    RedemptionCallbackService redemptionCallbackService;

    @Autowired
    RedemptionInquiryService redemptionInquiryService;

    @Autowired
    FetchDetailsService fetchDetailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdPayController.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @PostMapping("/transactionHistory")
    TransactionHistoryResponse transactionHistory(@RequestHeader Map<String, String> headers,
                                                  @Valid @RequestBody TransactionHistoryRequest request)
            throws SQLException, IOException {
        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));
        TransactionHistoryResponse response = transactionHistoryService.getTransactionHistory(headers,
                request);
        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));
        return response;
    }

    @PostMapping("/voidService")
    VoidServiceResponse voidService(@RequestHeader Map<String, String> headers,
                                    @Valid @RequestBody VoidServiceRequest request)
            throws SQLException, IOException {
        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));
        VoidServiceResponse response = voidService.voidTxnRvs(headers,
                request);
        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));
        return response;
    }

    @PostMapping("/walletTransaction")
    WalletTransactionResponse walletTransaction(@RequestHeader Map<String, String> headers,
                                                @Valid @RequestBody WalletTransactionRequest request)
            throws SQLException, IOException {
        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));
        WalletTransactionResponse response = walletTransactionService.walletTransaction(headers, request);
        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));
        return response;
    }

    @PostMapping("/walletStatusUpdate")
    WalletStatusUpdateResponse walletStatusUpdate(@RequestHeader Map<String, String> headers,
                                                  @Valid @RequestBody WalletStatusUpdateRequest request)
            throws SQLException, IOException {
        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));
        WalletStatusUpdateResponse response = walletStatusUpdateService.walletStatusUpdate(headers, request);
        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));
        return response;
    }

    @PostMapping("/walletTopUp")
    WalletTopUpResponse walletTopUp(@RequestHeader Map<String, String> headers,
                                    @Valid @RequestBody WalletTopUpRequest request)
            throws SQLException, IOException {
        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));
        WalletTopUpResponse response = walletTopUpService.WalletTopUpService(headers, request);
        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));
        return response;
    }

    @PostMapping("/updateCustomer")
    UpdateCustomerResponse updateCustomer(@RequestHeader Map<String, String> headers,
                                          @Valid @RequestBody UpdateCustomerRequest request)
            throws SQLException, IOException {
        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));
        UpdateCustomerResponse response = updateCustomerService.updateCustomerData(headers, request);
        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));
        return response;
    }

    @PostMapping("/preApproval")
    PreApprovalResponse preApproval(@RequestHeader Map<String, String> headers,
                                    @Valid @RequestBody PreApprovalRequest request)
            throws SQLException, IOException {
        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));
        PreApprovalResponse response = preApprovalService.preApprovalService(headers, request);
        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));
        return response;
    }

    @PostMapping("/addWallet")
    AddWalletResponse addWallet(@RequestHeader Map<String, String> headers,
                                @Valid @RequestBody AddWalletRequest request)
            throws SQLException, IOException {

        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));
        AddWalletResponse response = addWalletService.addWalletService(headers, request);
        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));
        return response;
    }

    @PostMapping("/walletInquiry")
    WalletInquiryResponse walletInquiry(@RequestHeader Map<String, String> headers,
                                        @Valid @RequestBody WalletInquiryRequest request)
            throws SQLException, IOException {

        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));

        WalletInquiryResponse response = walletInquiryService.walletInquiry(headers, request);

        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));
        return response;
    }

    @PostMapping("/walletToWalletTransaction")
    WalletToWalletTransactionResponse walletToWalletTransaction(@RequestHeader Map<String, String> headers,
                                                                @Valid @RequestBody WalletToWalletTransactionRequest request)
            throws SQLException, IOException {

        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));

        WalletToWalletTransactionResponse response = walletToWalletTransactionService.walletToWalletTxnService(headers, request);

        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));
        return response;
    }

    @PostMapping("/walletLimitUpdate")
    UpdateWalletLimitResponse walletLimitUpdate(@RequestHeader Map<String, String> headers,
                                                @Valid @RequestBody UpdateWalletLimitRequest request)
            throws SQLException, IOException {

        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));

        UpdateWalletLimitResponse response = updateWalletLimitService.udpateWalletLimit(headers, request);

        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));
        return response;
    }

    @PostMapping("/customerOnboarding")
    Object customerOnboard(@RequestHeader Map<String, String> headers,
                                            @Valid @RequestBody CustomerOnboardRequest request)
            throws Exception {

        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));
        if(request.getActionName().equals("CREATE")&&request.getActionName()!=null){
            CustomerOnboardResponse elpresponse = customerOnboardService.customerOnboarding(headers, request);
            LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                    OBJECT_MAPPER.writeValueAsString(elpresponse));
            BPMSResponse bpmsResponse = customerOnboardService.initiateBPMS(elpresponse.getApplicationId(),headers,request);
            LOGGER.info("BPMS Response" , bpmsResponse);
            return elpresponse;
        }else if(request.getActionName().equals("UPDATE_DETAILS")&&request.getWalletId()!=null){
            BPMSResponse bpmsResponse = customerOnboardService.initiateBPMS(request.getWalletId(),headers,request);
            LOGGER.info("BPMS Response" , bpmsResponse);
            return bpmsResponse;
        }else{
            throw new Exception("actionName will be either Create/update"+request.getActionName()+"walletId:"+request.getWalletId());
        }




    }

    @PostMapping("/fetchOnboardingDetails")
    FetchDetailsResponse customerOnboard(@RequestHeader Map<String, String> headers,
                                         @Valid @RequestBody FetchDetailsRequest request)
            throws Exception {

        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));

        FetchDetailsResponse response = fetchDetailsService.fetchCustomerOnboardingDetails(headers, request);

        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));

        return response;
    }

    @PostMapping("/redemptionInquiry")
    RedemptionInquiryResponse redemptionInquiry(@RequestHeader Map<String, String> headers,
                                                @Valid @RequestBody RedemptionInquiryRequest request)
            throws SQLException, IOException {

        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));

        RedemptionInquiryResponse response = redemptionInquiryService.redemptionInquiry(headers, request);

        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));

        return response;
    }

    @PostMapping("/redemptionCallback")
    public RedemptionCallbackResponse redemptionCallback(@RequestHeader Map<String, String> headers, @RequestBody RedemptionCallbackRequest request) throws SQLException, JsonProcessingException {
        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(request));
        RedemptionCallbackResponse response = redemptionCallbackService.fundsTransferStatusUpdate(headers, request);
        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(response));

        return response;
    }
    @PostMapping("/kycUpload")
    KycUploadResponse kycUpload(@RequestHeader Map<String, String> headers, @RequestBody KycUploadRequest kycUploadRequest)
            throws Exception {
        LOGGER.info("Transaction id: {} Request data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(kycUploadRequest));
        DMSConfiguration dmsConfiguration = KycUploadService.getDMSConfiguration(headers, kycUploadRequest);
        LOGGER.debug("Elpaso response:\n{}", OBJECT_MAPPER.writeValueAsString(dmsConfiguration));
        String dmsResponse = KycUploadService.uploadDocument(dmsConfiguration, kycUploadRequest, headers);
        LOGGER.debug("DMS response:\n{}", dmsResponse);
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode node = xmlMapper.readTree(dmsResponse.getBytes(StandardCharsets.UTF_8));
        KycUploadResponse kycUploadResponse = KycUploadService.updateDocumentUploadStatus(headers,node);
        LOGGER.info("Transaction id: {} Response data: {}", headers.get("transactionid"),
                OBJECT_MAPPER.writeValueAsString(kycUploadResponse));
        return kycUploadResponse;
    }
}
