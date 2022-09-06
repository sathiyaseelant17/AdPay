/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.walletInquiry;

import com.fab.adpay.Datasource;
import com.fab.adpay.exception.ElpasoException;

import java.sql.*;
import java.util.*;
import java.util.Date;

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
                "{call proc_mml_get_walletsbyid(?, ?, ?, ?, ?, ?, ?, ?, ?,?)}")) {

            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errcode", Types.INTEGER);
            callableStatement.setString("@pi_vc_clientidentifier", headers.get("channelid"));
            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
            callableStatement.setString("@pi_vc_transactionTimezone", "GST");
            callableStatement.setString("@pi_vc_countryOforgin", "AE");
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    new Timestamp(new Date().getTime()));
            callableStatement.setInt("@pi_ti_identitytype", request.getIdentityType());
            callableStatement.setString("@pi_vc_identitynumber", request.getIdentityNumber());
            callableStatement.setString("@pi_vc_wallet_id", request.getWalletId());


//            callableStatement.execute();
            List<WalletInquiryData> walletInquiryDataList = new ArrayList<>();
            WalletInquiryResponse response = new WalletInquiryResponse();
            PreApproved preApprovePojo=new PreApproved();
            // JSONObject walletIndex=new JSONObject();
            Map<String,Integer> walletMap=new HashMap<String,Integer>();
            int walletIndex=0;
            try( ResultSet rs = callableStatement.executeQuery()){
                if(rs!=null) {
//                     System.out.println("if condition");
                    while (rs.next()) {
//                       System.out.println("while confdition");
                        WalletInquiryData walletInquiryData = new WalletInquiryData();
                        preApprovePojo=new PreApproved();
                        walletInquiryData.setWalletId(rs.getString("walletid"));
                        if(walletMap.containsKey(walletInquiryData.getWalletId())) {
                            preApprovePojo.setAdgeId(rs.getString("adge id"));
                            preApprovePojo.setServiceId(rs.getString("service id"));
                            walletInquiryData.getPreApproved().add(preApprovePojo);
                            walletInquiryDataList.get(walletMap.get(walletInquiryData.getWalletId())).getPreApproved().add(preApprovePojo);
                        }else {
                            walletInquiryData.setWalletLabel(rs.getString("wallet label"));
                            walletInquiryData.setDefaultWallet(rs.getString("default wallet"));
                            walletInquiryData.setWalletType(rs.getString("wallet type"));
                            walletInquiryData.setFirstNameEnglish(rs.getString("firstname_english"));
                            walletInquiryData.setMiddleNameEnglish(rs.getString("middlename_english"));
                            walletInquiryData.setLastNameEnglish(rs.getString("lastname_english"));
                            walletInquiryData.setFirstNameArabic(rs.getString("firstname_arabic"));
                            walletInquiryData.setMiddleNameArabic(rs.getString("middlename_arabic"));
                            walletInquiryData.setLastNameArabic(rs.getString("lastname_arabic"));
                            walletInquiryData.setMobile(rs.getString("mobile"));
                            walletInquiryData.setEmail(rs.getString("emailid"));
                            walletInquiryData.setPreferredLanguage(rs.getString("Preflanguage"));
                            walletInquiryData.setWalletStatus(rs.getString("walletstatus#"));
                            walletInquiryData.setCreateDate(rs.getString("createdate"));
                            walletInquiryData.setLastTransactionDate(rs.getString("lasttxndate"));
                            walletInquiryData.setLastTopupAmount(rs.getString("lasttopupamount"));
                            walletInquiryData.setLastTopupDate(rs.getString("lasttopupdate"));
                            walletInquiryData.setWalletLimit(rs.getBigDecimal("walletlimit"));
                            walletInquiryData.setWalletSpendLimitPerTransaction(rs.getBigDecimal("walletspendlimitpertxn"));
                            walletInquiryData.setAvailableBalance(rs.getBigDecimal("avlbal"));
                            walletInquiryData.setCurrentBalance(rs.getBigDecimal("curbal"));
                            walletInquiryData.setKycFlag(rs.getString("KYCFlag"));
//                        walletInquiryData.setAdge(rs.getString("adge id"));
//                        walletInquiryData.setServiceId(rs.getString("service id"));
                            // System.out.println(rs.getString("adge id"));
                            preApprovePojo.setAdgeId(rs.getString("adge id"));
                            preApprovePojo.setServiceId(rs.getString("service id"));
                            walletInquiryData.getPreApproved().add(preApprovePojo);
                            walletInquiryDataList.add(walletInquiryData);
                            walletMap.put(walletInquiryData.getWalletId(),walletIndex);
                            walletIndex++;
                        }
                    }
                    response.setStatusCode(callableStatement.getInt("@po_vc_errcode"));
                    response.setStatusText(callableStatement.getString("@po_vc_errortext"));
                    response.setWalletInquiryDataList(walletInquiryDataList);
                }else {
                    response.setStatusCode(callableStatement.getInt("@po_vc_errcode"));
                    response.setStatusText(callableStatement.getString("@po_vc_errortext"));
                    response.setWalletInquiryDataList(null);
                }
            }catch(Exception e) {
                if ((callableStatement.getInt("@po_vc_errcode") != 0)) {
                    throw new ElpasoException(callableStatement.getInt("@po_vc_errcode"),
                            callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
                }
            }
            System.out.println(walletInquiryDataList);
            return response;
        }
    }

}
