package com.fab.adpay.addWallet;

import com.fab.adpay.Datasource;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Map;

@Service
public class AddWalletService {

    public AddWalletResponse addWalletService(Map<String, String> headers, AddWalletRequest req) throws SQLException {
        try (Connection connection = Datasource.getConnection(); CallableStatement callableStatement = connection.prepareCall(
                "{call proc_mml_i_addwallets(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}")) {

            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errcode", Types.INTEGER);
            callableStatement.registerOutParameter("@po_vc_new_wallet_id", Types.VARCHAR);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("countryOfOrgin"));
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    Timestamp.valueOf(headers.get("transactiondatetime")));
            callableStatement.setString("@pi_vc_clientidentifier", headers.get("channelid"));

            callableStatement.setString("@pi_vc_wallet_id", req.getWalletId());
            callableStatement.setString("@pi_vc_wallet_label",req.getWalletLabel());
            callableStatement.setBigDecimal("@pi_de_wallet_limit", req.getWalletLimit());

            callableStatement.execute();

            AddWalletResponse response = new AddWalletResponse();
            response.setNewWalletId(callableStatement.getString("@po_vc_new_wallet_id"));
            response.setErrorCode(callableStatement.getInt("@po_vc_errcode"));
            response.setErrorText(callableStatement.getString("@po_vc_errortext"));

            return response;
        }
    }
}
