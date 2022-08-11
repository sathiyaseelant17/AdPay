package com.fab.adpay.redemptionRequest;

import com.fab.adpay.Datasource;
import com.fab.adpay.exception.ElpasoException;
import com.fab.adpay.redemptionInquiry.RedemptionInquiryRequest;
import com.fab.adpay.redemptionInquiry.RedemptionInquiryResponse;
import com.fab.adpay.redemptionInquiry.RedemptionInquiryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Date;
import java.util.Map;

@Service
public class RedemptionReqService {

    private static final Logger logger = LoggerFactory.getLogger(RedemptionReqService.class);

    public RedemptionReqResponse redemptionRequest(Map<String, String> headers, RedemptionReqRequest request)
            throws SQLException {
        try (Connection connection = Datasource.getConnection(); CallableStatement callableStatement = connection.prepareCall(
                "{call proc_mml_FundsTransfer_request( ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?,?,?)}")) {

            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_si_errcode", Types.INTEGER);
            callableStatement.registerOutParameter("@po_de_avlbal", Types.DECIMAL);
            callableStatement.registerOutParameter("@po_de_curbal", Types.DECIMAL);
            callableStatement.registerOutParameter("@pi_vc_RedeemAckRef", Types.VARCHAR);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
            callableStatement.setString("@Pi_vc_clientidentifier", headers.get("channelid"));
            callableStatement.setString("@pi_vc_transactionTimezone", "GST");
            callableStatement.setString("@pi_vc_countryOforgin", "AE");
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    new Timestamp(new Date().getTime()));
            callableStatement.setInt("@pi_ti_txnsource", request.getTransactionSource());
            callableStatement.setString("@pi_vc_cardId", request.getWalletId());
            callableStatement.setInt("@pi_si_txntype#", request.getTransactionType());
            callableStatement.setString("@pi_vc_sourcemakerid", request.getSourceMakerId());
            callableStatement.setString("@pi_vc_sourceposid", request.getSourcePosId());
            callableStatement.setString("@pi_vc_sourcetxnref", request.getSourceTransactionRef());
            callableStatement.setInt("@pi_nm_txnamount", request.getTransactionAmount());
            callableStatement.setInt("@pi_nm_billamount", request.getBillAmount());
            callableStatement.setString("@pi_vc_txncurrcode", request.getTransactionCurrencyCode());
            callableStatement.setString("@pi_vc_billcurrcode", request.getBillCurrencyCode());
            callableStatement.setInt("@pi_nm_txnrate", request.getTransactionRate());
            callableStatement.setString("@pi_vc_PaymntCurrencyDate", request.getPaymentCurrencyCode());
            callableStatement.setString("@pi_vc_desc1", request.getDesc1());
            callableStatement.setString("@pi_vc_desc2", request.getDesc2());
            callableStatement.setString("@pi_vc_SenderName", request.getSenderName());
            callableStatement.setString("@pi_vc_SenderAddr1", request.getSenderAddress1());
            callableStatement.setString("@pi_vc_SenderAddr2", request.getSenderAddress2());
            callableStatement.setString("@pi_vc_SenderAddr3", request.getSenderAddress3());
            callableStatement.setString("@pi_vc_SenderAddr4", request.getSenderAddress4());
            callableStatement.setInt("@pi_vc_EntityCode", request.getEntityCode());
            callableStatement.setString("@pi_vc_beneficiarybankswiftcode", request.getBeneficiaryBankSwiftCode());
            callableStatement.setString("@pi_vc_beneficiaryIBAN", request.getBeneficiaryIBAN());
            callableStatement.setString("@pi_vc_benefiniciaryaccno", request.getBenefiniciaryAccNo());
            callableStatement.setString("@pi_vc_beneficiaryname", request.getBeneficiaryName());
            callableStatement.setString("@pi_vc_beneficiaryNickName", request.getBeneficiaryNickName());
            callableStatement.setString("@pi_vc_beneficiaryaddress1", request.getBeneficiaryAddress1());
            callableStatement.setString("@pi_vc_beneficiaryaddress2", request.getBeneficiaryAddress2());
            callableStatement.setString("@pi_vc_beneficiaryaddress3", request.getBeneficiaryAddress3());
            callableStatement.setString("@pi_vc_beneficiaryaddress4", request.getBeneficiaryAddress4());
            callableStatement.setString("@pi_vc_beneficiaryPhone", request.getBeneficiaryPhone());
            callableStatement.setString("@pi_vc_beneficiaryEmail", request.getBeneficiaryEmail());
            callableStatement.setString("@pi_vc_beneficiarybankname", request.getBeneficiaryBankName());
            callableStatement.setString("@pi_vc_beneficiarybankaddress1", request.getBeneficiaryBankAddress1());
            callableStatement.setString("@pi_vc_beneficiarybankaddress2", request.getBeneficiaryBankAddress2());
            callableStatement.setString("@pi_vc_beneficiarybankaddress3", request.getBeneficiaryBankAddress3());
            callableStatement.setString("@pi_vc_beneficiarybankbic", request.getBeneficiaryBankBic());

            callableStatement.execute();
            if (!(callableStatement.getInt("@po_si_errcode") == 0)) {
                throw new ElpasoException(callableStatement.getInt("@po_si_errcode"),
                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
            }
            RedemptionReqResponse response = new RedemptionReqResponse();
            response.setStatusCode(callableStatement.getInt("@po_si_errcode"));
            response.setStatusText(callableStatement.getString("@po_vc_errortext"));
            response.setAvailableBalance(callableStatement.getBigDecimal("@po_de_avlbal"));
            response.setCurrentBalance(callableStatement.getBigDecimal("@po_de_curbal"));
            response.setRedeemAcknowledgementRef(callableStatement.getString("@pi_vc_RedeemAckRef"));


            logger.debug("TRANSACTION ID: {} Redemption Inquiry RESPONSE:{}", headers.get("transactionid"), response);

            return response;
        }
    }

}
