package com.fab.adpay.controller;

import com.fab.adpay.preApproval.PreApprovalRequest;
import com.fab.adpay.preApproval.PreApprovalResponse;
import com.fab.adpay.preApproval.PreApprovalService;
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
import com.fab.adpay.walletStatusUpdate.WalletStatusUpdateRequest;
import com.fab.adpay.walletStatusUpdate.WalletStatusUpdateResponse;
import com.fab.adpay.walletStatusUpdate.WalletStatusUpdateService;
import com.fab.adpay.walletTopUp.WalletTopUpRequest;
import com.fab.adpay.walletTopUp.WalletTopUpResponse;
import com.fab.adpay.walletTopUp.WalletTopUpService;
import com.fab.adpay.walletTransactions.WalletTransactionRequest;
import com.fab.adpay.walletTransactions.WalletTransactionResponse;
import com.fab.adpay.walletTransactions.WalletTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(AdPayController.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @PostMapping("/transactionHistory")
    TransactionHistoryResponse checkScreeningStatus(@RequestHeader Map<String, String> headers,
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

}
