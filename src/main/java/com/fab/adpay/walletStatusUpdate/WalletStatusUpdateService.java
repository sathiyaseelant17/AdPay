/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.walletStatusUpdate;

import com.fab.adpay.Datasource;
import com.fab.adpay.exception.ElpasoException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WalletStatusUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(WalletStatusUpdateService.class);

    public WalletStatusUpdateResponse walletStatusUpdate(Map<String, String> headers, WalletStatusUpdateRequest request)
            throws SQLException {
        try ( Connection connection = Datasource.getConnection();  CallableStatement callableStatement = connection.prepareCall(
                "{call proc_setcardstatus_Wallet_v2(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_i_errorCode", Types.INTEGER);
            callableStatement.registerOutParameter("@po_i_txnid#", Types.INTEGER);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
            callableStatement.setTimestamp("@pi_dt_transactionDateTime",
                    new Timestamp(new Date().getTime()));
            callableStatement.setString("@pi_vc_clientIdentifier", headers.get("channelid"));
            
            callableStatement.setShort("@pi_ti_txnsource", (short) 48);
            callableStatement.setString("@pi_vc_cardno", "");
            callableStatement.setShort("@pi_ti_newstatus", (short) request.getNewStatus());
            callableStatement.setString("@pi_vc_reasontext", request.getReasonText());
            callableStatement.setShort("@pi_ti_checkexpiryflag", (short) 0);
            callableStatement.setString("@pi_c_expirydate", "");
            callableStatement.setString("@pi_vc_cardid", request.getWalletId());
            callableStatement.setShort("@pi_ti_reasonkey#", (short) 0);
            callableStatement.setString("@pi_vc_Makerid", "");
            callableStatement.setString("@pi_vc_sourcetxnref", "");
            callableStatement.execute();
            if (!(callableStatement.getInt("@po_i_errorCode") == 0)) {
                throw new ElpasoException(callableStatement.getInt("@po_i_errorCode"),
                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
            }
            WalletStatusUpdateResponse response = new WalletStatusUpdateResponse();
            response.setStatusCode(callableStatement.getInt("@po_i_errorCode"));
            response.setStatusText(callableStatement.getString("@po_vc_errortext"));
            response.setTransactionId(callableStatement.getInt("@po_i_txnid#"));


            logger.debug("TRANSACTION ID: {} UPDATE CARD STATUS RESPONSE:{}", headers.get("transactionid"), response);

            return response;
        }
    }

}
