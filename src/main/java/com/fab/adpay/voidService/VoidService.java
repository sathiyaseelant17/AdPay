//package com.fab.adpay.voidService;
//
//import com.fab.adpay.Datasource;
//import com.fab.cashee.exception.ElpasoException;
//
//import java.sql.*;
//import java.util.Map;
//
//public class VoidService {
//    public static VoidServiceResponse voidService(Map<String, String> headers, VoidServiceRequest req) throws SQLException {
//        try (Connection connection = Datasource.getConnection();
//             CallableStatement callableStatement = connection.prepareCall(
//                     "{call Proc_credit_txn_wallet_v2(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
//            callableStatement.registerOutParameter("@pio_vc_cardno", Types.VARCHAR);
//            callableStatement.registerOutParameter("@po_nm_avlbalamount", Types.NUMERIC);
//            callableStatement.registerOutParameter("@po_nm_curbalamount", Types.NUMERIC);
//            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
//            callableStatement.registerOutParameter("@po_vc_cardcurrcode", Types.VARCHAR);
//            callableStatement.registerOutParameter("@po_i_txnid", Types.INTEGER);
//            callableStatement.registerOutParameter("@po_i_errorcode", Types.INTEGER);
//            callableStatement.setInt("@pi_ti_txnsource", Integer.parseInt(headers.get("transactionsource")));
//            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
//            callableStatement.setTimestamp("@pi_dt_transactionDateTime",
//                    Timestamp.valueOf(headers.get("transactiondatetime")));
//            callableStatement.setString("@pi_vc_clientIdentifer", headers.get("channelid"));
//            callableStatement.setShort("@pi_si_txntype", req.getTransactionType());
//            callableStatement.setString("@pio_vc_cardid", req.getCardId());
//            callableStatement.setString("@pi_vc_sourcemakerid", req.getTerminalId());
//            callableStatement.setString("@pi_vc_desc2", req.getMerchantName());
//            callableStatement.setString("@pi_vc_merchantlocation", req.getMerchantLocation());
//            callableStatement.setString("@pi_vc_sourceposid", req.getMerchantId());
//            callableStatement.setString("@pi_vc_retrievalrefno", req.getRrn());
//            callableStatement.setBigDecimal("@pi_nm_txnamount", req.getTransactionAmount());
//            callableStatement.setString("@pi_vc_txncurrcode", req.getTransactionCurrency());
//            callableStatement.setBigDecimal("@pi_nm_billamount", req.getEquivalentAmount());
//            callableStatement.setString("@pi_vc_billcurrcode", req.getEquivalentCurrency());
//            callableStatement.setString("@pi_vc_authcode", req.getAuthorizationCode());
//            callableStatement.setShort("@pi_si_feetype#", (short) 0);
//            callableStatement.setBigDecimal("@pi_nm_feeamount", req.getFeeAmount());
//            callableStatement.setBigDecimal("@pi_nm_txnrate", req.getTransactionRate());
//            callableStatement.setString("@pi_vc_desc1", req.getRemarks());
//            callableStatement.setString("@pi_vc_mcc", req.getMcc());
//            callableStatement.setTimestamp("@pi_dt_txndatetime", Timestamp.valueOf(req.getTransactionDateTime()));
//            callableStatement.setString("@pi_vc_feedesc1", req.getFeeDescription());
//            callableStatement.setString("@pi_vc_txnidentifier", headers.get("transactionid"));
//            callableStatement.setString("@pi_vc_sourcetxnref", req.getSourceTxnReferenceNo());
//            callableStatement.setString("@pi_vc_onlhostrefno", "");
//            callableStatement.setString("@pi_vc_txnrefno", "");
//            callableStatement.execute();
//            if (!(callableStatement.getInt("@po_i_errorcode") == 0)) {
//                throw new ElpasoException(callableStatement.getInt("@po_i_errorcode"),
//                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
//            }
//            CreditTxnResponse res = new CreditTxnResponse();
//            res.setErrorcode(String.valueOf(callableStatement.getInt("@po_i_errorcode")));
//            res.setErrorText(callableStatement.getString("@po_vc_errortext"));
//            res.setAvailableBalance(callableStatement.getBigDecimal("@po_nm_avlbalamount"));
//            res.setCurrentBalance(callableStatement.getBigDecimal("@po_nm_curbalamount"));
//            res.setCardId(callableStatement.getString("@pio_vc_cardid"));
//            res.setTxnId(String.valueOf(callableStatement.getInt("@po_i_txnid")));
//            return res;
//        }
//    }
//}
