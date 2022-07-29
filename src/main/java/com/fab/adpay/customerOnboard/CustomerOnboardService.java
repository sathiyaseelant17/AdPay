/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.customerOnboard;

import java.sql.*;
import java.util.Arrays;
import java.util.Map;

import com.fab.adpay.Datasource;
import com.fab.adpay.updateCustomer.UpdateCustomerRequest;
import com.fab.adpay.updateCustomer.UpdateCustomerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class CustomerOnboardService {
    public CustomerOnboardResponse customerOnboarding(Map<String, String> headers, CustomerOnboardRequest request)
            throws SQLException {
        try (Connection connection = Datasource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(
                     "{call Proc_mml_i_chnlcardboardreq(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");) {
            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errorcode", Types.INTEGER);
            callableStatement.registerOutParameter("@po_i_ApplicationID", Types.INTEGER);


            callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactionTimezone", headers.get("transactionTimeZone"));
            callableStatement.setString("@pi_vc_countryOforgin", headers.get("countryOfOrgin"));
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    Timestamp.valueOf(headers.get("transactionDateTime")));
            callableStatement.setString("@pi_vc_clientIdentifer", headers.get("channelid"));


            callableStatement.setInt("@pi_ti_txnsource", 48);
            callableStatement.setInt("@pi_ti_txntype", 1);
            callableStatement.setString("@pi_vc_bpmsrefnum", "");
            callableStatement.setString("@pi_vc_cinno", "");
            callableStatement.setInt("@pi_i_producttypeid", 0);
            callableStatement.setInt("@pi_n_custriskrate", 0);
            callableStatement.setInt("@pi_i_kyb", 0);
            callableStatement.setString("@pi_c_custtype", "I");
            callableStatement.setString("@pi_vc_salut", "DR.");
            callableStatement.setString("@pi_vc_fn_eng", request.getFirstName());
            callableStatement.setString("@pi_vc_mn_eng", request.getMiddleName());
            callableStatement.setString("@pi_vc_ln_eng", request.getLastName());
            callableStatement.setString("@pi_vc_fn_arb", "");
            callableStatement.setString("@pi_vc_mn_arb", "");
            callableStatement.setString("@pi_vc_ln_arb", "");
            callableStatement.setString("@pi_vc_copn_eng", "");
            callableStatement.setString("@pi_vc_copn_arb", "");
            callableStatement.setString("@pi_vc_embnm_eng", request.getEmbossedName());
            callableStatement.setString("@pi_country_of_Residence", request.getCountryOfResidence());
            callableStatement.setString("@pi_vc_dob",  request.getDateOfBirth());
            callableStatement.setString("@pi_vc_add1", request.getAddressLine1());
            callableStatement.setString("@pi_vc_occupation", request.getOccupation());
            callableStatement.setString("@pi_vc_add2", request.getAddressLine2());
            callableStatement.setString("@pi_vc_add3", request.getAddressLine3());
            callableStatement.setString("@pi_vc_add4", request.getAddressLine4());
            callableStatement.setString("@pi_vc_city", request.getCity());
            callableStatement.setInt("@pi_ti_emiratescd", 1);
            callableStatement.setString("@pi_vc_postalcd", request.getPostalCode());
            callableStatement.setString("@pi_vc_resphoneno", "");
            callableStatement.setString("@pi_vc_ofcphoneno", "");
            callableStatement.setString("@pi_vc_faxno", "");
            callableStatement.setString("@pi_vc_mob", request.getMobileNumber());
            callableStatement.setString("@pi_vc_emailid", request.getEmailID());
            callableStatement.setString("@pi_vc_nationality", request.getNationality());
            callableStatement.setString("@pi_vc_comcregno", "");
            callableStatement.setString("@pi_vc_dateofestb", "");
            callableStatement.setInt("@pi_ti_docsVrfFlag", 1);
            callableStatement.setString("@pi_vc_makerid", "ADPW");
            callableStatement.setString("@pi_vc_companyembname_arabic", "");
            callableStatement.setString("@pi_vc_embname_arabic", "");
            callableStatement.setString("@pi_vc_companyembname_english", request.getCompanyEmbName());
            callableStatement.setInt("@pi_i_identitytype", request.getIdType());
            callableStatement.setString("@pi_vc_identitytypedesc", "");
            callableStatement.setString("@pi_vc_identityissuedate", request.getIdIssueDate());
            callableStatement.setString("@pi_vc_identityexpirydate", request.getIdExpiryDate());
            callableStatement.setString("@pi_vc_identityno", "");
            callableStatement.setString("@pi_vc_identityissueplace", request.getIdIssuePlace());
            callableStatement.setString("@pi_i_identityissuecountry", request.getIdIssueCountry());
            callableStatement.setString("@pi_vc_preflanguage", request.getPreferredLanguage());
            callableStatement.setInt("@pi_ti_deliverymode", 2);
            callableStatement.setString("@pi_vc_deliveryposid", "A01");
            callableStatement.setString("@pi_vc_occupcode", "101");
            callableStatement.setString("@Pi_dmsReqID", "");
            callableStatement.setString("@pi_vc_docsCollection", "");
            callableStatement.setString ("@pi_vc_deliveryvendorid", "");
            callableStatement.setString("@pi_xml_fatcacrs", "");
            callableStatement.setString("@pi_vc_screeningResult", "");


            callableStatement.execute();

            CustomerOnboardResponse response = new CustomerOnboardResponse();
            response.setErrorCode(callableStatement.getString("@po_vc_errorcode"));
            response.setErrorText(callableStatement.getString("@po_vc_errortext"));
            response.setApplicationId(callableStatement.getString("@po_i_ApplicationID"));
            return response;
        }
    }

}
