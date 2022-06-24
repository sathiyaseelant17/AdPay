package com.fab.adpay.voidService;


import com.fab.adpay.Datasource;

import java.sql.*;
import java.util.Map;

public class VoidService {
    public static VoidServiceResponse voidTxnRvs(Map<String, String> headers, VoidServiceRequest req) throws SQLException {
        try (Connection connection = Datasource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(
                     "{call proc_mml_channel_txnreverse(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}")) {
            callableStatement.registerOutParameter("@pio_vc_cardid", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_nm_avlbalamount", Types.NUMERIC);
            callableStatement.registerOutParameter("@po_nm_curbalamount", Types.NUMERIC);
            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errCode", Types.INTEGER);
            callableStatement.registerOutParameter("@po_vc_RequestRspTime", Types.INTEGER);


            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("countryOfOrgin"));
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    Timestamp.valueOf(headers.get("transactionDateTime")));
            callableStatement.setString("@pi_vc_clientIdentifer", headers.get("channelid"));


            callableStatement.setString("@pio_vc_cardid", req.getCardId());
            callableStatement.setString("@pi_vc_txnidentifier", req.getTxnIdentifier());
            callableStatement.setString("@pi_vc_orgtxnidentifier", req.getOrgTxnIdentifier());
            callableStatement.setInt("@pi_ti_txnsource", req.getTxnSource());
            callableStatement.setString("@pi_vc_sourcemakerid", req.getSourceMakerId());
            callableStatement.setString("@pi_vc_sourceposid", req.getSourcePosId());
            callableStatement.setInt("@pi_vc_technicalrevflag", req.getTechnicalRevFlag());
            callableStatement.setString("@pi_dt_RequestRcvTime",  req.getRequestRcvTime());
            callableStatement.setString("@pi_vc_URN", req.getURN());
            callableStatement.setString("@pi_vc_org_URN", req.getOrg_URN());
            callableStatement.setString("@pi_vc_loanID", req.getLoanId());
            callableStatement.setString("@pi_vc_sourceTxnReferenceNo", req.getSourceTxnReferenceNo());
            callableStatement.setString("@pi_vc_nmc", req.getNmc());
            callableStatement.setString("@pi_vc_ageid", req.getAgeId());
            callableStatement.setString("@pi_vc_serviceid", req.getServiceId());

            callableStatement.execute();
            if (!(callableStatement.getInt("@po_i_errorcode") == 0)) {
                throw new com.fab.cashee.exception.ElpasoException(callableStatement.getInt("@po_i_errorcode"),
                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
            }
            VoidServiceResponse res = new VoidServiceResponse();
            res.setErrorCode(String.valueOf(callableStatement.getInt("@po_i_errcode")));
            res.setErrorText(callableStatement.getString("@po_vc_errortext"));
            res.setAvlBalAmount(callableStatement.getBigDecimal("@po_nm_avlbalamount"));
            res.setCurBalAmount(callableStatement.getBigDecimal("@po_nm_curbalamount"));
            res.setCardId(callableStatement.getString("@pio_vc_cardid"));
            res.setRequestRspTime(callableStatement.getString("@po_vc_RequestRspTime"));

            return res;
        }
    }
}
