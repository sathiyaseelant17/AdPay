package com.fab.adpay.updateCustomer;

import com.fab.adpay.Datasource;

import java.sql.*;
import java.util.Map;

public class UpdateCustomerService {

    public UpdateCustomerResponse updateCustomerData(Map<String, String> headers, UpdateCustomerRequest request)
            throws SQLException {
        try (Connection connection = Datasource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(
                     "{call proc_u_customerDetails_wallet(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");) {
            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_i_errorcode", Types.INTEGER);

            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("countryOfOrgin"));
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    Timestamp.valueOf(headers.get("transactionDateTime")));
            callableStatement.setString("@pi_vc_clientIdentifer", headers.get("channelid"));


            callableStatement.setString("@pi_vc_cardid", request.getCardId());
            callableStatement.setInt("@pi_i_salutation", request.getPrefixTitle());
            callableStatement.setString("@pi_vc_firstnameenglish", request.getFirstName());
            callableStatement.setString("@pi_vc_middlenameenglish", request.getMiddleName());
            callableStatement.setString("@pi_vc_lastnameenglish", request.getLastName());
            callableStatement.setString("@pi_vc_embossednameenglish", request.getEmbossedName());
            callableStatement.setString("@pi_vc_dob", request.getDob());
            callableStatement.setString("@pi_vc_nationality", request.getNationality());
            callableStatement.setString("@pi_vc_countryofresidence", request.getCountryOfResidence());
            callableStatement.setString("@pi_vc_address1", request.getAddressLine1());
            callableStatement.setString("@pi_vc_address2", request.getAddressLine2());
            callableStatement.setString("@pi_vc_address3", request.getAddressLine3());
            callableStatement.setString("@pi_vc_address4", request.getAddressLine4());
            callableStatement.setString("@pi_vc_city", request.getCity());
            callableStatement.setString("@pi_vc_countrycode", request.getCountryCode());
            callableStatement.setString("@pi_vc_postalcode", request.getPostalCode());
            callableStatement.setString("@pi_vc_mobile", request.getMobile());
            callableStatement.setString("@pi_vc_email", request.getEmail());
            callableStatement.setString("@pi_vc_occupation", request.getOccupation());
            callableStatement.setShort("@pi_ti_idtype", (short) request.getIdType());
            callableStatement.setString("@pi_vc_id#", request.getIdNumber());
            callableStatement.setString("@pi_vc_idexpdate", request.getIdExpiryDate());
            callableStatement.setString("@pi_vc_idissuecountry", request.getIdIssueCountryId());
            callableStatement.setString("@pi_vc_gender", request.getGender());
            callableStatement.setString("@pi_vc_VisaExpiryDate", request.getVisaExpiryDate());
            callableStatement.setString("@pi_vc_birthPlace", request.getBirthPlace());
            callableStatement.setShort("@pi_ti_emirates", (short) request.getEmirates());
            callableStatement.setString("@pi_vc_companyName", request.getCompanyName());
            callableStatement.setString("@pi_vc_IBAN", request.getIban());
            callableStatement.execute();

            UpdateCustomerResponse response = new UpdateCustomerResponse();
            response.setErrorCode(callableStatement.getInt("@po_i_errorcode"));
            response.setErrorText(callableStatement.getString("@po_vc_errortext"));
            return response;
        }
    }

}
