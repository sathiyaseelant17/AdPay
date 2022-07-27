/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.walletStatusUpdate;

import com.fab.adpay.Datasource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WalletStatusUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(WalletStatusUpdateService.class);

    public WalletStatusUpdateResponse walletStatusUpdate(Map<String, String> headers, WalletStatusUpdateRequest request)
            throws SQLException {
        try ( Connection connection = Datasource.getConnection();  CallableStatement callableStatement = connection.prepareCall(
                "{call proc_mml_u_cardstatus(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_i_errcode", Types.INTEGER);
            callableStatement.registerOutParameter("@po_i_txnid#", Types.INTEGER);
            callableStatement.registerOutParameter("pio_vc_cardid",Types.VARCHAR);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
            callableStatement.setTimestamp("@pi_dt_transactionDateTime",
                    Timestamp.valueOf(headers.get("transactiondatetime")));
            callableStatement.setString("@pi_vc_clientIdentifier", headers.get("channelid"));
            callableStatement.setInt("@pi_ti_txnsource", request.getTransactionSource());
            callableStatement.setInt("@pi_dt_chngdate",request.getChangeDate());
            callableStatement.setInt("@pi_ti_surrenderflag",request.getSurrenderFlag());
            callableStatement.setString("@pi_c_replacedflag",request.getReplacedFlag());
            callableStatement.setInt("@pi_i_passcodestatus#",request.getPassCodeStatus());
            callableStatement.setInt("@pi_i_passcodecount",request.getPassCodeCount());
            callableStatement.setInt("@pi_i_registrationstatus#",request.getRegistrationStatus());
            callableStatement.setString("@pi_c_pinactive",request.getPinActive());
            callableStatement.setString("@pi_dt_expirydate",request.getExpiryDate());
            callableStatement.setString("@pi_dt_trackexpirydate",request.getNewTrackExpiryDate());
            callableStatement.setString("@pi_c_dormantflag",request.getDormantFlag());
            callableStatement.setInt("@pi_ti_cardsalestatus#",request.getCardSaleStatus());
            callableStatement.setString("@pi_dt_newtrackexpirydate",request.getNewTrackExpiryDate());
            callableStatement.setInt("@pi_ti_newcardstatus#",request.getNewCardStatus());
            callableStatement.setInt("@pi_ti_reasonkey#",request.getReasonKey());
            callableStatement.setString("@pi_vc_makerid",request.getMakerId());
            callableStatement.setString("@pi_dt_makerdt",request.getMakerDt());
            callableStatement.setString("@pi_vc_authid",request.getAuthorizationId());
            callableStatement.setInt("@pi_ti_cardused",request.getCardUsed());
            callableStatement.setString("@pi_ch_cardrenewalflag",request.getCardRenewalFlag());
            callableStatement.setInt("@pi_ti_riskrating#",request.getRiskRating());
            callableStatement.setString("@pi_c_recardflag",request.getReCardFlag());
            callableStatement.setString("@pi_c_firstactflag",request.getFirstActFlag());
            callableStatement.setInt("@pi_ti_txnsource",request.getTransactionSource());
            callableStatement.setInt("@pi_i_txntype",request.getTransactionType());
            callableStatement.setString("@pi_vc_posid",request.getPosId());
            callableStatement.setInt("@pi_ti_audittrail",request.getAudItTrail());
            callableStatement.setString("@pi_vc_desc2",request.getDesc2());
            callableStatement.setString("@pi_vc_sourcetxnref",request.getSourceTransactionReference());
            callableStatement.setString("@pi_c_smsflag",request.getSmsFlag());
            callableStatement.setString("@pi_c_AutoCardRenewalflag",request.getAutoCardRenewalFlag());
            callableStatement.setInt("@pi_i_cardstatus#",request.getNewStatus());
            callableStatement.setString("@pi_vc_reasontext",request.getReasonText());

            callableStatement.execute();

            WalletStatusUpdateResponse response = new WalletStatusUpdateResponse();
            response.setErrorCode(callableStatement.getInt("@po_i_errcode"));
            response.setErrorText(callableStatement.getString("@po_vc_errortext"));
            response.setCardId(callableStatement.getString("@pio_vc_cardid"));
            response.setTransactionId(callableStatement.getInt("@po_i_txnid#"));

            logger.debug("TRANSACTION ID: {} UPDATE CARD STATUS RESPONSE:{}", headers.get("transactionid"), response);

            return response;
        }
    }

}
