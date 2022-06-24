package com.fab.adpay.addWallet;

import com.fab.adpay.Datasource;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Map;

@Service
public class AddWalletService {

    public AddWalletResponse addWalletService(Map<String, String> headers, AddWalletRequest req) throws SQLException {
        try (Connection connection = Datasource.getConnection(); CallableStatement callableStatement = connection.prepareCall(
                "{call proc_mml_i_addwallets(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errcode", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_newwalletid", Types.VARCHAR);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("countryOfOrgin"));
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    Timestamp.valueOf(headers.get("transactionDateTime")));
            callableStatement.setString("@pi_vc_clientIdentifer", headers.get("channelid"));

            callableStatement.setString("@pi_vc_walletid", req.getWalletId());
            callableStatement.setString("@pi_vc_walletlabel",req.getWalletLabel());
            callableStatement.setBigDecimal("@pi_de_walletlimit", req.getWalletLimit());

            callableStatement.execute();

            AddWalletResponse response = new AddWalletResponse();
            response.setNewWalletId(callableStatement.getString("@po_vc_newwalletid"));
            response.setErrorCode(callableStatement.getString("@po_vc_errcode"));
            response.setErrorText(callableStatement.getString("@po_vc_errortext"));

            return response;
        }
    }
}
