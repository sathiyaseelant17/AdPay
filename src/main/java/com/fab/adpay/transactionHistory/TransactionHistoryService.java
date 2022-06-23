package com.fab.adpay.transactionHistory;

import com.fab.adpay.Datasource;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TransactionHistoryService {

    public TransactionHistoryResponse getTransactionHistory(Map<String, String> headers,
            TransactionHistoryRequest request) throws SQLException {
        try ( Connection connection = Datasource.getConnection();  CallableStatement callableStatement = connection.prepareCall(
                "{call proc_get_cardtxnhistory_wallet(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");) {
            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_i_errorcode", Types.INTEGER);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
            callableStatement.setTimestamp("@pi_dt_transactiondate",Timestamp.valueOf(headers.get("transactiondatetime")));
            callableStatement.setString("@pi_vc_clientIdentifier", headers.get("clientidentifier"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("contryOfOrgin"));
            
            callableStatement.setInt("@pi_ti_txnsource", Integer.parseInt(headers.get("txnSource")));
            callableStatement.setShort("@pi_i_requesttype", (short) request.getRequestType());
            callableStatement.setString("@pi_vc_cardid", request.getCardId());
            callableStatement.setString("@pi_vc_startdate", request.getStartDate());
            callableStatement.setString("@pi_vc_enddate", request.getEndDate());
            callableStatement.setInt("@pi_i_maxrecordstofetch", request.getNumberOfTxns());
            callableStatement.setInt("@pi_ti_requestmode", request.getRequestMode());
            
            callableStatement.execute();
            List<TransactionHistory> transactionHistoryList = new ArrayList<>();
            TransactionHistoryResponse response = new TransactionHistoryResponse();
            if ((callableStatement.getInt("@po_i_errorcode") != 0)) {
                response.setErrorCode(String.valueOf(callableStatement.getInt("@po_i_errorcode")));
                response.setErrorText(callableStatement.getString("@po_vc_errortext"));
                response.setTransactionHistory(null);

            } else {

                ResultSet rs = callableStatement.getResultSet();
                while (rs.next()) {
                    TransactionHistory transactionHistory = new TransactionHistory();
                    transactionHistory.setTxnDateTime(rs.getString("txndatetime"));
                    transactionHistory.setDesc1(rs.getString("desc1"));
                    transactionHistory.setDesc2(rs.getString("desc2"));
                    transactionHistory.setTransactionAmount(rs.getBigDecimal("txnamount"));
                    transactionHistory.setCreditDebitFlag(rs.getString("crdbflag"));
                    transactionHistory.setCurrentBalance(rs.getBigDecimal("currentbalance"));
                    transactionHistory.setTransactionSourceDesc(rs.getString("txnsourcedesc"));
                    transactionHistory.setTransactionTypeDesc(rs.getString("txntypedesc"));
                    transactionHistory.setTransactionCurrencyCode(rs.getString("txncurrencycode"));
                    transactionHistory.setBilledCurrencyCode(rs.getString("billcurrencycode"));
                    transactionHistory.setBilledAmount(rs.getBigDecimal("billamount"));
                    transactionHistory.setTransactionReferenceNumber(rs.getString("txnreferenceNo"));
                    transactionHistory.setMcc(rs.getString("mcc"));
                    transactionHistoryList.add(transactionHistory);
                }
                response.setErrorCode(String.valueOf(callableStatement.getInt("@po_i_errorcode")));
                response.setErrorText(callableStatement.getString("@po_vc_errortext"));
                response.setTransactionHistory(transactionHistoryList);
            }
            return response;
        }
    }
}
