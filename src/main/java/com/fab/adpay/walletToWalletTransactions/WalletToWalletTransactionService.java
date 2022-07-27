package com.fab.adpay.walletToWalletTransactions;

import com.fab.adpay.Datasource;
import com.fab.adpay.voidService.VoidServiceRequest;
import com.fab.adpay.voidService.VoidServiceResponse;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Map;

@Service
public class WalletToWalletTransactionService {

    public static WalletToWalletTransactionResponse walletToWalletTxnService(Map<String, String> headers, WalletToWalletTransactionRequest req) throws SQLException {
        try (Connection connection = Datasource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(
                     "{call  proc_mml_i_wallettowallet_trans(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)}")) {
            callableStatement.registerOutParameter("@po_vc_wallet_id_01", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_de_availableBalance_01", Types.DECIMAL);
            callableStatement.registerOutParameter("@po_de_currentBalance_01", Types.DECIMAL);
            callableStatement.registerOutParameter("@po_vc_wallet_id_02", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_de_availableBalance_02", Types.DECIMAL);
            callableStatement.registerOutParameter("@po_de_currentBalance_02", Types.DECIMAL);
            callableStatement.registerOutParameter("@po_i_txnId", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errcode", Types.INTEGER);


            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("countryOfOrgin"));
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    Timestamp.valueOf(headers.get("transactiondatetime")));
            callableStatement.setString("@Pi_vc_clientIdentifer", headers.get("channelId"));


            callableStatement.setString("@pi_vc_wallet_id_01", req.getWalletId_01());
            callableStatement.setString("@pi_vc_wallet_id_02", req.getWalletId_02());
            callableStatement.setString("@pi_vc_rrn", req.getRrn());
            callableStatement.setBigDecimal("@pi_de_transactionamount", req.getTransactionAmount());
            callableStatement.setString("@pi_c_transactioncurrency", req.getTransactionCurrency());
            callableStatement.setBigDecimal("@pi_vc_equivalentamount", req.getEquivalentAmount());
            callableStatement.setString("@pi_c_equivalentcurrency",  req.getEquivalentCurrency());
            callableStatement.setString("@pi_vc_authorizationcode", req.getAuthorizationCode());
            callableStatement.setBigDecimal("@pi_de_transactionrate", req.getTransactionRate());
            callableStatement.setString("@pi_vc_remarks", req.getRemarks());
            callableStatement.setString("@pi_dt_transactiondatetime", req.getTransactionDateTime());
            callableStatement.setBigDecimal("@pi_de_feeamount", req.getFeeAmount());
            callableStatement.setString("@pi_vc_feedescription", req.getFeeDescription());

            callableStatement.execute();
            if (!(callableStatement.getInt("@po_i_errcode") == 0)) {
                throw new com.fab.adpay.exception.ElpasoException(callableStatement.getInt("@po_i_errcode"),
                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
            }
            WalletToWalletTransactionResponse res = new WalletToWalletTransactionResponse();
            res.setErrorCode(callableStatement.getInt("@po_i_errcode"));
            res.setErrorText(callableStatement.getString("@po_vc_errortext"));
            res.setWalletId_01(callableStatement.getString("@po_vc_wallet id_01"));
            res.setWalletId_02(callableStatement.getString("@po_vc_wallet id_02"));
            res.setAvailableBalance_01(callableStatement.getBigDecimal("@po_de_availableBalance_01"));
            res.setAvailableBalance_02(callableStatement.getBigDecimal("@po_de_availableBalance_02"));
            res.setCurrentBalance_01(callableStatement.getBigDecimal("@po_de_currentBalance_01"));
            res.setCurrentBalance_02(callableStatement.getBigDecimal("@po_de_currentBalance_02"));
            res.setTxnId(callableStatement.getInt("@po_i_txnId"));

            return res;
        }
    }
}
