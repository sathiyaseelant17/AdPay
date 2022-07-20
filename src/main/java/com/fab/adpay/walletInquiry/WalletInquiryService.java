/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.walletInquiry;

import com.fab.adpay.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fab.adpay.transactionHistory.TransactionHistory;
import com.fab.adpay.transactionHistory.TransactionHistoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WalletInquiryService {

    private static final Logger logger = LoggerFactory.getLogger(WalletInquiryService.class);

    public WalletInquiryResponse walletInquiry(Map<String, String> headers, WalletInquiryRequest request)
            throws SQLException {
        try (Connection connection = Datasource.getConnection(); CallableStatement callableStatement = connection.prepareCall(
                "{call proc_mml_get_walletsbyid(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errcode", Types.INTEGER);

            callableStatement.setString("@Pi_vc_clientIdentifier", headers.get("channelId"));
            callableStatement.setTimestamp("@pi_dt_transactiondate", Timestamp.valueOf(headers.get("transactiondatetime")));
            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("countryOfOrgin"));
            callableStatement.setInt("@pi_ti_identitytype", request.getIdentityType());
            callableStatement.setString("@pi_vc_identitynumber", request.getIdentityNumber());

            callableStatement.execute();
            List<WalletInquiryData> walletInquiryDataList = new ArrayList<>();
            WalletInquiryResponse response = new WalletInquiryResponse();
            if ((callableStatement.getInt("@po_vc_errcode") != 0)) {
                response.setErrorCode(callableStatement.getInt("@po_vc_errcode"));
                response.setErrorText(callableStatement.getString("@po_vc_errortext"));
                response.setWalletInquiryDataList(null);
            } else {

                ResultSet rs = callableStatement.getResultSet();
                while (rs.next()) {
                    WalletInquiryData walletInquiryData = new WalletInquiryData();
                    walletInquiryData.setWalletId(callableStatement.getString("walletid"));
                    walletInquiryData.setWalletLabel(callableStatement.getString("wallet label"));
                    walletInquiryData.setDefaultWallet(callableStatement.getString("default wallet"));
                    walletInquiryData.setWalletType(callableStatement.getString("wallet type"));
                    walletInquiryData.setFirstNameEnglish(callableStatement.getString("firstname_english"));
                    walletInquiryData.setMiddleNameEnglish(callableStatement.getString("middlename_english"));
                    walletInquiryData.setLastNameEnglish(callableStatement.getString("lastname_english"));
                    walletInquiryData.setFirstNameArabic(callableStatement.getString("firstname_arabic"));
                    walletInquiryData.setMiddleNameArabic(callableStatement.getString("middlename_arabic"));
                    walletInquiryData.setLastNameEnglish(callableStatement.getString("lastname_arabic"));
                    walletInquiryDataList.add(walletInquiryData);
                }
                response.setErrorCode(callableStatement.getInt("@po_i_errcode"));
                response.setErrorText(callableStatement.getString("@po_vc_errortext"));
                response.setWalletInquiryDataList(walletInquiryDataList);

            }
            logger.debug("TRANSACTION ID: {} UPDATE CARD STATUS RESPONSE:{}", headers.get("transactionid"), response);

            return response;
        }
    }

}
