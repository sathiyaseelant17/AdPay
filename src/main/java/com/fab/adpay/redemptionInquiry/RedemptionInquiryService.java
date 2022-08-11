package com.fab.adpay.redemptionInquiry;

import com.fab.adpay.Datasource;
import com.fab.adpay.exception.ElpasoException;
import com.fab.adpay.walletLimit.UpdateWalletLimitRequest;
import com.fab.adpay.walletLimit.UpdateWalletLimitResponse;
import com.fab.adpay.walletLimit.UpdateWalletLimitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Date;
import java.util.Map;

@Service
public class RedemptionInquiryService {

    private static final Logger logger = LoggerFactory.getLogger(RedemptionInquiryService.class);

    public RedemptionInquiryResponse redemptionInquiry(Map<String, String> headers, RedemptionInquiryRequest request)
            throws SQLException {
        try (Connection connection = Datasource.getConnection(); CallableStatement callableStatement = connection.prepareCall(
                "{call proc_mml_FundsTransfer_Inquiry(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?)}")) {

            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_si_errcode", Types.INTEGER);
            callableStatement.registerOutParameter("@po_de_avlbal", Types.DECIMAL);
            callableStatement.registerOutParameter("@po_de_curbal", Types.DECIMAL);

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
            callableStatement.setString("@pi_vc_RedeemAckRef", request.getRedeemAcknowledgementRef());
            callableStatement.setInt("@pi_ti_RedeemStatus", request.getRedeemStatus());
            callableStatement.execute();
            if (!(callableStatement.getInt("@po_si_errcode") == 0)) {
                throw new ElpasoException(callableStatement.getInt("@po_si_errcode"),
                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
            }
            RedemptionInquiryResponse response = new RedemptionInquiryResponse();
            response.setStatusCode(callableStatement.getInt("@po_si_errcode"));
            response.setStatusText(callableStatement.getString("@po_vc_errortext"));
            response.setAvailableBalance(callableStatement.getBigDecimal("@po_de_avlbal"));
            response.setCurrentBalance(callableStatement.getBigDecimal("@po_de_curbal"));

            logger.debug("TRANSACTION ID: {} Redemption Inquiry RESPONSE:{}", headers.get("transactionid"), response);

            return response;
        }
    }

}
