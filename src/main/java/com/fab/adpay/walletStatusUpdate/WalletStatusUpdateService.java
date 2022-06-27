/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.walletStatusUpdate;

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
public class WalletStatusUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(WalletStatusUpdateService.class);

    public WalletStatusUpdateResponse walletStatusUpdate(Map<String, String> headers, WalletStatusUpdateRequest request)
            throws SQLException {
        try ( Connection connection = Datasource.getConnection();  CallableStatement callableStatement = connection.prepareCall(
                "{call Proc_setcardstatus_wallet_v2(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_i_errcode", Types.INTEGER);
            callableStatement.registerOutParameter("@po_i_txnid#", Types.INTEGER);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
            callableStatement.setTimestamp("@pi_dt_transactionDateTime",
                    Timestamp.valueOf(headers.get("transactiondatetime")));
            callableStatement.setString("@pi_vc_clientIdentifier", headers.get("channelid"));
            callableStatement.setShort("@pi_ti_txnsource", (short) 0);
            callableStatement.setString("@pi_vc_cardno", "");
            callableStatement.setShort("@pi_ti_newstatus", (short) request.getNewStatus());
            callableStatement.setString("@pi_vc_reasontext", request.getReasonText());
            callableStatement.setShort("@pi_ti_checkexpiryflag", (short) 0);
            callableStatement.setString("@pi_c_expirydate", "");
            callableStatement.setString("@pi_vc_cardid", request.getCardId());
            callableStatement.setShort("@pi_ti_reasonkey#", (short) 0);
            callableStatement.setString("@pi_vc_makerid", "");
            callableStatement.setString("@pi_vc_sourcetxnref", "");
            callableStatement.execute();

            WalletStatusUpdateResponse response = new WalletStatusUpdateResponse();
            response.setErrorCode(callableStatement.getInt("@po_i_errcode"));
            response.setErrorText(callableStatement.getString("@po_vc_errortext"));

            logger.debug("TRANSACTION ID: {} UPDATE CARD STATUS RESPONSE:{}", headers.get("transactionid"), response);

            return response;
        }
    }

}
