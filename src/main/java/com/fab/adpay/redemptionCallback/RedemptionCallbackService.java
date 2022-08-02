package com.fab.adpay.redemptionCallback;

import com.fab.adpay.Datasource;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Date;
import java.util.Map;

@Service
public class RedemptionCallbackService {
    public RedemptionCallbackResponse fundsTransferStatusUpdate(Map<String, String> headers, RedemptionCallbackRequest request) throws SQLException {
        try (Connection connection = Datasource.getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call proc_mml_FundsTransfer_StatusUpdate(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")
        ) {
            callableStatement.registerOutParameter("@pi_vc_RedeemAckRef", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_si_errcode", Types.INTEGER);
            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.setString("@pi_vc_cardId", request.getCardId());
            callableStatement.setInt("@pi_si_txntype#", request.getTransactionType());
            callableStatement.setString("@pi_vc_clientIdentifier", headers.get("channelid"));
            callableStatement.setString("@pi_vc_transactionTimezone", "GST");
            callableStatement.setString("@pi_vc_countryOforgin", "AE");
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    new Timestamp(new Date().getTime()));
            callableStatement.setInt("@pi_ti_txnsource", request.getTransactionSource());
            callableStatement.setString("@pi_vc_sourcemakerid", request.getSourceMakerId());
            callableStatement.setString("@pi_vc_sourceposid", request.getSourcePosId());
            callableStatement.setString("@pi_vc_sourcetxnref", request.getSourceTxnRef());
            callableStatement.setString("@pi_vc_RedeemAckRef", request.getRedeemAckRef());
            callableStatement.setInt("@pi_ti_RedeemStatus", request.getRedeemStatus());
            callableStatement.setTimestamp("@pi_dt_RedeemDateTime", Timestamp.valueOf(request.getRedeemDateTime()));
            callableStatement.setString("@pi_vc_RedeemDeclCode", request.getRedeemDeclCode());
            callableStatement.setString("@pi_vc_RedeemDeclDesc", request.getRedeemDeclDesc());
            callableStatement.execute();

            RedemptionCallbackResponse response = new RedemptionCallbackResponse();
            response.setErrorCode(callableStatement.getInt("@po_si_errcode"));
            response.setErrorText(callableStatement.getString("@po_vc_errortext"));
            response.setRedeemAckRef(callableStatement.getString("@pi_vc_RedeemAckRef"));
            return response;

        }
        
    }
}
