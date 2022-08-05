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
import java.util.Date;
import java.util.Map;

import com.fab.adpay.exception.ElpasoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PreApprovalService {

    private static final Logger logger = LoggerFactory.getLogger(PreApprovalService.class);

    public PreApprovalResponse preApprovalService(Map<String, String> headers, PreApprovalRequest request)
            throws SQLException {
        try ( Connection connection = Datasource.getConnection();  CallableStatement callableStatement = connection.prepareCall(
                "{call proc_mml_preapproval(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}")) {
        	
            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errcode", Types.INTEGER);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
            callableStatement.setString("@Pi_vc_clientidentifier", headers.get("channelid"));
            callableStatement.setString("@pi_vc_transactionTimezone", "GST");
            callableStatement.setString("@pi_vc_countryOforgin", "AE");
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    new Timestamp(new Date().getTime()));

            callableStatement.setString("@pio_vc_cardid", request.getWalletId());
            callableStatement.setString("@pi_vc_adgeid", request.getAdgeId());
            callableStatement.setString("@pi_vc_serviceid", request.getServiceId());
            callableStatement.setString("@Pi_vc_servicename", request.getServiceName());
            callableStatement.setInt("@pi_ti_requesttype", request.getRequestType());

            callableStatement.execute();
            if (!(callableStatement.getInt("@po_vc_errcode") == 0)) {
                throw new ElpasoException(callableStatement.getInt("@po_vc_errcode"),
                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
            }
            PreApprovalResponse response = new PreApprovalResponse();
            response.setStatusCode(callableStatement.getInt("@po_vc_errcode"));
            response.setStatusText(callableStatement.getString("@po_vc_errortext"));

            logger.debug("TRANSACTION ID: {} UPDATE CARD STATUS RESPONSE:{}", headers.get("transactionid"), response);

            return response;
        }
    }

}
