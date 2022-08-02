package com.fab.adpay.voidService;


import com.fab.adpay.Datasource;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Date;
import java.util.Map;

@Service
public class VoidService {
    public static VoidServiceResponse voidTxnRvs(Map<String, String> headers, VoidServiceRequest req) throws SQLException {
        try (Connection connection = Datasource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(
                     "{call proc_mml_channel_txnreverse(?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}")) {
            callableStatement.registerOutParameter("@pio_vc_cardid", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_nm_avlbalamount", Types.NUMERIC);
            callableStatement.registerOutParameter("@po_nm_curbalamount", Types.NUMERIC);
            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errcode", Types.INTEGER);
            callableStatement.registerOutParameter("@po_vc_RequestRspTime", Types.INTEGER);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
            callableStatement.setString("@pi_vc_transactionTimezone", "GST");
            callableStatement.setString("@pi_vc_countryOforgin", "AE");
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    new Timestamp(new Date().getTime()));
            callableStatement.setString("@Pi_vc_clientidentifier", headers.get("channelid"));

            callableStatement.setString("@pio_vc_cardid", req.getWalletId());
            callableStatement.setString("@pi_vc_txnidentifier", req.getTxnIdentifier());
            callableStatement.setInt("@pi_ti_txnsource", req.getTransactionSource());
            callableStatement.setString("@pi_vc_orgtxnidentifier", req.getOrgTxnIdentifier());
            callableStatement.setString("@pi_vc_sourcemakerid", req.getSourceMakerId());
            callableStatement.setString("@pi_vc_sourceposid", req.getSourcePosId());
            callableStatement.setInt("@pi_ti_technicalrevflag", req.getTechnicalRevFlag());
            callableStatement.setString("@pi_dt_RequestRcvTime",  req.getRequestRcvTime());
            callableStatement.setString("@pi_vc_URN", req.getURN());
            callableStatement.setString("@pi_vc_org_URN", req.getOrg_URN());
            callableStatement.setString("@pi_vc_loanID", req.getLoanId());
            callableStatement.setString("@pi_vc_sourceTxnReferenceNo", req.getSourceTxnReferenceNo());
            callableStatement.setString("@pi_vc_mcc", req.getMcc());
            callableStatement.setString("@pi_vc_adgeid", req.getAdgeId());
            callableStatement.setString("@pi_vc_serviceid", req.getServiceId());

            callableStatement.execute();
            if (!(callableStatement.getInt("@po_vc_errcode") == 0)) {
                throw new com.fab.adpay.exception.ElpasoException(callableStatement.getInt("@po_vc_errcode"),
                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
            }
            VoidServiceResponse res = new VoidServiceResponse();
            res.setStatusCode(callableStatement.getInt("@po_vc_errcode"));
            res.setStatusText(callableStatement.getString("@po_vc_errortext"));
            res.setAvlBalAmount(callableStatement.getBigDecimal("@po_nm_avlbalamount"));
            res.setCurBalAmount(callableStatement.getBigDecimal("@po_nm_curbalamount"));
            res.setWalletId(callableStatement.getString("@pio_vc_cardid"));
            res.setRequestRspTime(callableStatement.getString("@po_vc_RequestRspTime"));

            return res;
        }
    }
}
