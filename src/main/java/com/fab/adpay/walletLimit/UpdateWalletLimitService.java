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
import java.util.Date;
import java.util.Map;

import com.fab.adpay.exception.ElpasoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UpdateWalletLimitService {

    private static final Logger logger = LoggerFactory.getLogger(UpdateWalletLimitService.class);

    public UpdateWalletLimitResponse udpateWalletLimit(Map<String, String> headers, UpdateWalletLimitRequest request)
            throws SQLException {
        try ( Connection connection = Datasource.getConnection();  CallableStatement callableStatement = connection.prepareCall(
                "{call proc_mml_u_limitandlabel(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errcode", Types.INTEGER);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
            callableStatement.setString("@Pi_vc_clientidentifier", headers.get("channelid"));
            callableStatement.setString("@pi_vc_transactionTimezone", "GST");
            callableStatement.setString("@pi_vc_countryOforgin", "AE");
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    new Timestamp(new Date().getTime()));
            callableStatement.setString("@pi_vc_wallet_id", request.getWalletId());
            callableStatement.setString("@pi_vc_default_wallet", request.getDefaultWallet());
            callableStatement.setString("@pi_vc_wallet_label", request.getWalletLabel());
            callableStatement.setInt("@pi_de_wallet_limit", request.getWalletLimit());

            callableStatement.execute();
            if (!(callableStatement.getInt("@po_vc_errcode") == 0)) {
                throw new ElpasoException(callableStatement.getInt("@po_vc_errcode"),
                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
            }
            UpdateWalletLimitResponse response = new UpdateWalletLimitResponse();
            response.setStatusCode(callableStatement.getInt("@po_vc_errcode"));
            response.setStatusText(callableStatement.getString("@po_vc_errortext"));

            logger.debug("TRANSACTION ID: {} WALLET LIMIT RESPONSE:{}", headers.get("transactionid"), response);

            return response;
        }
    }

}
