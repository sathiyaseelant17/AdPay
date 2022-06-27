/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.preApproval;

import com.fab.adpay.Datasource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreApprovalService {

    private static final Logger logger = LoggerFactory.getLogger(PreApprovalService.class);

    public PreApprovalResponse preApprovalService(Map<String, String> headers, PreApprovalRequest request)
            throws SQLException {
        try ( Connection connection = Datasource.getConnection();  CallableStatement callableStatement = connection.prepareCall(
                "{call proc_mml_preapproval(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}")) {

            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_i_errcode", Types.INTEGER);

            callableStatement.setString("@Pi_vc_clientIdentifier", headers.get("channelId"));
            callableStatement.setTimestamp("@pi_dt_transactiondate", Timestamp.valueOf(headers.get("transactionDateTime")));
            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("countryOfOrgin"));
            callableStatement.setString("@pi_vc_cardid", request.getCardId());
            callableStatement.setString("@pi_vc_adgeid", request.getAdgeId());
            callableStatement.setString("@pi_vc_serviceid", request.getServiceId());
            callableStatement.setString("@@Pi_vc_servicename", request.getServiceName());
            callableStatement.setInt("@pi_ti_requesttype", request.getRequestType());

            callableStatement.execute();

            PreApprovalResponse response = new PreApprovalResponse();
            response.setErrorCode(callableStatement.getInt("@po_i_errcode"));
            response.setErrorText(callableStatement.getString("@po_vc_errortext"));

            logger.debug("TRANSACTION ID: {} UPDATE CARD STATUS RESPONSE:{}", headers.get("transactionid"), response);

            return response;
        }
    }

}
