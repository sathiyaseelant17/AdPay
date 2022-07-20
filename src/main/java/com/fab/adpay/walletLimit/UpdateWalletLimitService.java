/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.walletLimit;

import com.fab.adpay.Datasource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UpdateWalletLimitService {

    private static final Logger logger = LoggerFactory.getLogger(UpdateWalletLimitService.class);

    public UpdateWalletLimitResponse udpateWalletLimit(Map<String, String> headers, UpdateWalletLimitRequest request)
            throws SQLException {
        try ( Connection connection = Datasource.getConnection();  CallableStatement callableStatement = connection.prepareCall(
                "{call proc_mml_u_limitandlabel(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}")) {

            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_i_errcode", Types.INTEGER);

            callableStatement.setString("@Pi_vc_clientIdentifier", headers.get("channelId"));
            callableStatement.setTimestamp("@pi_dt_transactiondate", Timestamp.valueOf(headers.get("transactionDateTime")));
            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("countryOfOrgin"));

            callableStatement.setString("@pi_vc_wallet id", request.getWalletId());
            callableStatement.setString("@pi_vc_default wallet", request.getDefaultWallet());
            callableStatement.setString("@pi_vc_wallet label", request.getWalletLabel());
            callableStatement.setInt("@pi_de_wallet limit", request.getWalletLimit());

            callableStatement.execute();

            UpdateWalletLimitResponse response = new UpdateWalletLimitResponse();
            response.setErrorCode(callableStatement.getInt("@po_i_errcode"));
            response.setErrorText(callableStatement.getString("@po_vc_errortext"));

            logger.debug("TRANSACTION ID: {} WALLET LIMIT RESPONSE:{}", headers.get("transactionid"), response);

            return response;
        }
    }

}
