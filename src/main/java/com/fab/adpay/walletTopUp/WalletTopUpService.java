package com.fab.adpay.walletTopUp;

import com.fab.adpay.Datasource;

import java.sql.*;
import java.util.Map;

public class WalletTopUpService {
    public static WalletTopUpResponse WalletTopUpService(Map<String, String> headers, WalletTopUpRequest req) throws SQLException {
        try (Connection connection = Datasource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(
                     "{call Proc_mml_post_channel_txn(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
            callableStatement.registerOutParameter("@pio_vc_cardid", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_c_trackexpirydate", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_nm_avlbalamount", Types.NUMERIC);
            callableStatement.registerOutParameter("@po_nm_curbalamount", Types.NUMERIC);
            callableStatement.registerOutParameter("@po_vc_activationcode", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_cardcurrcode", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_i_txnid", Types.INTEGER);
            callableStatement.registerOutParameter("@po_c_expirydate", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_creditac", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_creditacposid", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errcode", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);


            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("countryOfOrgin"));
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    Timestamp.valueOf(headers.get("transactionDateTime")));
            callableStatement.setString("@pi_vc_clientIdentifer", headers.get("channelid"));


            callableStatement.setString("@pio_vc_cardid", req.getCardId());
            callableStatement.setInt("@pi_ti_txnsource", req.getTxnSource());
            callableStatement.setInt("@pi_si_txntype", req.getTxntype());
            callableStatement.setString("@pi_vc_txnidentifier", req.getTxnIdentifier());
            callableStatement.setString("@pi_vc_sourcemakerid", req.getSourceMakerId());
            callableStatement.setString("@pi_vc_sourceposid", req.getSourcePosId());
            callableStatement.setString("@pi_vc_sourcetxnref", req.getSourceTxnReferenceNo());
            callableStatement.setString("@pi_vc_onlhostrefno",  req.getOnlHostReferenceNo());
            callableStatement.setString("@pi_vc_txnrefno", req.getTxnReferenceNo());
            callableStatement.setString("@pi_vc_desc1", req.getRemarks());
            callableStatement.setInt("@pi_si_feetype#", req.getFeeType());
            callableStatement.setInt("@pi_nm_feeamount", req.getFeeAmount());
            callableStatement.setString("@pi_vc_feeac", req.getFeeAc());
            callableStatement.setString("@pi_vc_feedesc1", req.getFeeDescription());
            callableStatement.setBigDecimal("@pi_nm_txnamount", req.getTransactionAmount());
            callableStatement.setBigDecimal("@pi_nm_billamount  ", req.getEquivalentAmount());
            callableStatement.setString("@pi_vc_txncurrcode", req.getTransactionCurrency());
            callableStatement.setString("@pi_vc_billcurrcode", req.getEquivalentCurrency());
            callableStatement.setInt("@pi_nm_txnrate", req.getTransactionRate());
            callableStatement.setString("@pi_vc_authcode", req.getAuthorizationCode());
            callableStatement.setString("@pi_vc_merchantlocation", req.getMerchantLocation());
            callableStatement.setInt("@pi_ti_checkexpiryflag", req.getCheckExpiryFlag());
            callableStatement.setString("@pi_vc_desc2", req.getMerchantName());
            callableStatement.setString("@pi_vc_retrievalrefno", req.getRrn());
            callableStatement.setString("@pi_dt_sourcemakerdatetime", req.getSourceMakerDateTime());
            callableStatement.setString("@pi_vc_sourceauthid", req.getSourceAuthId());
            callableStatement.setString("@pi_dt_sourceauthdatetime", req.getSourceAuthDateTime());
            callableStatement.setString("@pi_vc_authcomments", req.getAuthComments());
            callableStatement.setInt("@pi_ti_surrenderflag", req.getSurrenderFlag());
            callableStatement.setInt("@pi_ti_closecardflag", req.getCloseCardFlag());
            callableStatement.setString("@pi_vc_loanID", req.getLoadId());
            callableStatement.setString("@pi_dt_txndatetime", req.getTransactionDateTime());
            callableStatement.setString("@pi_vc_mcc", req.getMcc());


            callableStatement.execute();
            if (!(callableStatement.getInt("@po_i_errorcode") == 0)) {
                throw new com.fab.cashee.exception.ElpasoException(callableStatement.getInt("@po_i_errorcode"),
                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
            }


            WalletTopUpResponse res = new WalletTopUpResponse();
            res.setCardId(callableStatement.getString("@pio_vc_cardid"));
            res.setTrackExpiryDate(callableStatement.getString("@po_c_trackexpirydate"));
            res.setAvailableBalance(callableStatement.getBigDecimal("@po_nm_avlbalamount"));
            res.setCurrentBalance(callableStatement.getBigDecimal("@po_nm_curbalamount"));
            res.setActivationCode(callableStatement.getString("@po_vc_activationcode"));
            res.setCardCurrentCode(callableStatement.getString("@po_vc_cardcurrcode"));
            res.setTransactionId(callableStatement.getInt("@po_i_txnid"));
            res.setExpiryDate(callableStatement.getString("@po_c_expirydate"));
            res.setCreditAc(callableStatement.getString("@po_vc_creditac"));
            res.setCreditAcPosId(callableStatement.getString("@po_vc_creditacposid"));
            res.setErrorCode(String.valueOf(callableStatement.getInt("@po_vc_errcode")));
            res.setErrorText(callableStatement.getString("@po_vc_errortext"));

            return res;
        }
    }
}
