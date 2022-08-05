package com.fab.adpay.transactionHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fab.adpay.Datasource;
import com.fab.adpay.exception.ElpasoException;

@Service
public class TransactionHistoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionHistoryService.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public TransactionHistoryResponse getTransactionHistory(Map<String, String> headers,
															TransactionHistoryRequest request) throws SQLException {
		try (Connection connection = Datasource.getConnection();
			 CallableStatement callableStatement = connection.prepareCall(
					 "{call proc_get_cardTxnHistory_wallet(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");) {
			callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
			callableStatement.registerOutParameter("@po_i_errorcode", Types.INTEGER);
			callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
			callableStatement.setTimestamp("@pi_dt_transactionDateTime",
					new Timestamp(new Date().getTime()));
			callableStatement.setString("@pi_vc_clientIdentifer", headers.get("channelid"));
			callableStatement.setShort("@pi_ti_requesttype", (short) request.getRequestType());
			callableStatement.setString("@pi_vc_cardid", request.getWalletId());
			callableStatement.setString("@pi_vc_startdate", request.getStartDate());
			callableStatement.setString("@pi_vc_enddate", request.getEndDate());
			callableStatement.setInt("@pi_i_numberOfTxns", request.getNumberOfTxns());
			List<TransactionHistory> transactionHistoryResponseList=new ArrayList<>();
			TransactionHistoryResponse res=new TransactionHistoryResponse();
			try(ResultSet rs=callableStatement.executeQuery()){
				if(rs!=null) {
					while(rs.next()){
						LOGGER.info("Transaction id: {} currentBal: {}", headers.get("transactionid"),
								rs.getBigDecimal("@po_nm_currentbal"));

						TransactionHistory response = new TransactionHistory();
						response.setTxnDateTime(String.valueOf(rs.getTimestamp("@po_dt_txndatetime")));
						response.setDesc1(rs.getString("@po_vc_desc1"));
						response.setDesc2(rs.getString("@po_vc_desc2"));
						response.setTransactionAmount(rs.getBigDecimal("@po_nm_txnamount"));
						response.setCreditDebitFlag(rs.getString("@po_c_crdbflag"));
						response.setCurrentBalance(rs.getBigDecimal("@po_nm_currentbal"));
						response.setTransactionSourceDesc(rs.getString("@po_vc_txnsourcedesc"));
						response.setTransactionTypeDesc(rs.getString("@po_vc_txntypedesc"));
						response.setTransactionCurrencyCode(rs.getString("@po_vc_txncurrcode"));
						response.setBilledCurrencyCode(rs.getString("@po_vc_billcurrcode"));
						response.setBilledAmount(rs.getBigDecimal("@po_nm_billamount"));
						response.setTransactionReferenceNumber(rs.getString("@po_vc_txnrefno"));
						response.setMcc(rs.getString("@po_vc_mcc"));

						transactionHistoryResponseList.add(response);

					}
					LOGGER.info("Transaction id: {} TransactionResultSet data: {}", headers.get("transactionid"),
							OBJECT_MAPPER.writeValueAsString(transactionHistoryResponseList));

					res.setStatusCode(callableStatement.getInt("@po_i_errorcode"));
					res.setStatusText(callableStatement.getString("@po_vc_errortext"));
					res.setTransactionHistory(transactionHistoryResponseList);
				}else {
					res.setStatusCode(callableStatement.getInt("@po_i_errorcode"));
					res.setStatusText(callableStatement.getString("@po_vc_errortext"));

				}

			}catch(Exception e) {
				if ((callableStatement.getInt("@po_i_errorcode") != 0)) {
					throw new ElpasoException(callableStatement.getInt("@po_i_errorcode"),
							callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
				}
			}

			return res;
		}
	}

}
