package com.fab.adpay.walletTransactions;

import com.fab.adpay.Datasource;
import com.fab.adpay.exception.ElpasoException;
import com.fab.adpay.exception.ElpasoException;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;

@Service
public class WalletTransactionService {

    public  WalletTransactionResponse walletTransaction(Map<String, String> headers, WalletTransactionRequest request)
            throws SQLException {
        try ( Connection connection = Datasource.getConnection();  CallableStatement callableStatement = connection.prepareCall(
                "{call proc_mml_authtransaction(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
            callableStatement.registerOutParameter("@pio_vc_cardid", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_nm_avlbalamount", Types.NUMERIC);
            callableStatement.registerOutParameter("@po_nm_curbalamount", Types.NUMERIC);
            callableStatement.registerOutParameter("@po_vc_RequestRspTime", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_c_trackexpirydate", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_i_errcode", Types.VARCHAR);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("countryOfOrgin"));
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    Timestamp.valueOf(headers.get("transactionDateTime")));
            callableStatement.setString("@pi_vc_clientIdentifer", headers.get("channelid"));

            callableStatement.setInt("@pi_ti_txnsource", request.getTransactionSource());
            callableStatement.setString("@pio_vc_cardid", request.getCardId());
            callableStatement.setString("@pi_vc_txnidentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_sourcemakerid", request.getSourceMakerId());
            callableStatement.setString("@pi_vc_sourceposid", request.getSourcePosId());
            callableStatement.setString("@pi_vc_sourcetxnref", request.getSourceTxnRef());
            callableStatement.setString("@pi_vc_onlhostrefno", request.getOnlHostRefNo());
            callableStatement.setString("@pi_vc_txnrefno", request.getTxnRefNo());
            callableStatement.setString("@pi_vc_desc1", request.getDesc1());
            callableStatement.setInt("@pi_si_feetype#", request.getFeeType());
            callableStatement.setBigDecimal("@pi_nm_feeamount", request.getFeeAmount());
            callableStatement.setBigDecimal("@pi_nm_txnamount", request.getTxnAmount());
            callableStatement.setBigDecimal("@pi_nm_billamount", request.getBillAmount());
            callableStatement.setString("@pi_vc_txncurrcode", request.getTxnCurrCode());
            callableStatement.setString("@pi_vc_billcurrcode", request.getBillCurrCode());
            callableStatement.setInt("@pi_nm_txnrate", request.getTxnRate());
            callableStatement.setString("@pi_vc_authcode", request.getAuthCode());
            callableStatement.setString("@pi_vc_merchantlocation", request.getMerchantlocation());
            callableStatement.setString("@pi_c_expirydate", request.getExpiryDate());
            callableStatement.setInt("@pi_ti_checkexpiryflag", request.getCheckExpiryFlag());
            callableStatement.setString("@pi_vc_retrievalrefno", request.getRetrievalRefNo());
            callableStatement.setString("@pi_vc_passcode", request.getPassCode());
            callableStatement.setString("@pi_dt_txndatetime", headers.get("transactionDateTime"));
            callableStatement.setString("@pi_dt_freezeexpirydate", request.getFreezeExpiryDate());
            callableStatement.setInt("@pi_ti_begintranflag", request.getBegintranflag());
            callableStatement.setString("@pi_vc_desc2", request.getDesc2());
            callableStatement.setInt("@pi_si_txntype#", request.getTxnType());
            callableStatement.setString("@pi_vc_merchantcategorycode", request.getMerchantcategorycode());
            callableStatement.setString("@pi_dt_RequestRcvTime", request.getRequestRcvTime());
            callableStatement.setString("@pi_vc_adgeid", request.getAdgeId());
            callableStatement.setString("@pi_vc_serviceid", request.getServiceId());
            callableStatement.setString("@pi_vc_feeDescription", request.getFeeDesc());
            callableStatement.setString("@pi_vc_clientIdentifer", headers.get("channelId"));
            callableStatement.setTimestamp("@pi_dt_transactiondate", Timestamp.valueOf(headers.get("transactionDateTime")));
            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("countryOfOrgin"));

            callableStatement.execute();
            if (!(callableStatement.getInt("@po_i_errcode") == 0)) {
                throw new ElpasoException(callableStatement.getInt("@po_i_errcode"),
                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
            }
            WalletTransactionResponse walletTransactionResponse = new WalletTransactionResponse();
            walletTransactionResponse.setCardId(callableStatement.getString("@pio_vc_cardid"));
            walletTransactionResponse.setErrorText(callableStatement.getString("@po_vc_errortext"));
            walletTransactionResponse.setAvlBalAmount(callableStatement.getBigDecimal("@po_nm_avlbalamount"));
            walletTransactionResponse.setCurBalAmount(callableStatement.getBigDecimal("@po_nm_curbalamount"));
            walletTransactionResponse.setReqRspTime(callableStatement.getString("@po_vc_RequestRspTime"));
            walletTransactionResponse.setTrackExpiryDate(callableStatement.getString("@po_c_trackexpirydate"));
            walletTransactionResponse.setErrorCode(callableStatement.getInt("@po_i_errorcode"));

            return walletTransactionResponse;
        }
    }
}
