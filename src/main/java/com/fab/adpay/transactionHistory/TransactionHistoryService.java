package com.fab.adpay.transactionHistory;

import com.fab.adpay.Datasource;
import com.fab.cashee.exception.ElpasoException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TransactionHistoryService {

    public TransactionHistoryResponse getTransactionHistory(Map<String, String> headers,
            TransactionHistoryRequest request) throws SQLException {
        try ( Connection connection = Datasource.getConnection();  CallableStatement callableStatement = connection.prepareCall(
                "{call proc_get_cardTxnHistory_wallet(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");) {
            callableStatement.registerOutParameter("@po_dt_txndatetime", Types.TIMESTAMP);
            callableStatement.registerOutParameter("@po_vc_desc1", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_desc2", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_nm_txnamount", Types.NUMERIC);
            callableStatement.registerOutParameter("@po_c_crdbflag", Types.CHAR);
            callableStatement.registerOutParameter("@po_nm_currentbal", Types.NUMERIC);
            callableStatement.registerOutParameter("@po_vc_txnsourcedesc", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_txntypedesc", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_txncurrcode", Types.VARCHAR);
//            callableStatement.registerOutParameter("@po_nm_txnrate", Types.NUMERIC);
            callableStatement.registerOutParameter("@po_vc_billcurrcode", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_nm_billamount", Types.NUMERIC);
            callableStatement.registerOutParameter("@po_vc_txnrefno", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_mcc", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_i_errorcode", Types.INTEGER);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
            callableStatement.setTimestamp("@pi_dt_transactionDateTime",
                    Timestamp.valueOf(headers.get("transactiondatetime")));
            callableStatement.setString("@pi_vc_clientIdentifer", headers.get("channelid"));
            callableStatement.setShort("@pi_ti_requesttype", (short) request.getRequestType());
            callableStatement.setString("@pi_vc_cardid", request.getCardId());
            callableStatement.setString("@pi_vc_startdate", request.getStartDate());
            callableStatement.setString("@pi_vc_enddate", request.getEndDate());
            callableStatement.setInt("@pi_i_numberOfTxns", request.getNumberOfTxns());
            callableStatement.execute();

            if ((callableStatement.getInt("@po_i_errorcode") != 0)) {
                throw new ElpasoException(callableStatement.getInt("@po_i_errorcode"),
                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
            }

            TransactionHistoryResponse response = new TransactionHistoryResponse();
            response.setErrorCode(String.valueOf(callableStatement.getInt("@po_i_errorcode")));
            response.setErrorText(callableStatement.getString("@po_vc_errortext"));
            response.setTxnDateTime(String.valueOf(callableStatement.getTimestamp("@po_dt_txndatetime")));
            response.setDesc1(callableStatement.getString("@po_vc_desc1"));
            response.setDesc2(callableStatement.getString("@po_vc_desc2"));
            response.setTransactionAmount(callableStatement.getBigDecimal("@po_nm_txnamount"));
            response.setCreditDebitFlag(callableStatement.getString("@po_c_crdbflag"));
            response.setCurrentBalance(callableStatement.getBigDecimal("@po_nm_currentbal"));
            response.setTransactionSourceDesc(callableStatement.getString("@po_vc_txnsourcedesc"));
            response.setTransactionTypeDesc(callableStatement.getString("@po_vc_txntypedesc"));
            response.setTransactionCurrencyCode(callableStatement.getString("@po_vc_txncurrcode"));
//            response.setTransactionRate(callableStatement.getBigDecimal("@po_nm_txnrate"));
            response.setBilledCurrencyCode(callableStatement.getString("@po_vc_billcurrcode"));
            response.setBilledAmount(callableStatement.getBigDecimal("@po_nm_billamount"));
            response.setTransactionReferenceNumber(callableStatement.getString("@po_vc_txnrefno"));
            response.setMcc(callableStatement.getString("@po_vc_mcc"));
            return response;
        }
    }
}
