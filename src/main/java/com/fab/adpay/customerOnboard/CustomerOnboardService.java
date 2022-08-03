
package com.fab.adpay.customerOnboard;

import java.sql.*;
import java.util.Arrays;
import java.util.Date;
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
                     "{call Proc_mml_i_chnlcardboardreq(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?,? )}");) {
            callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_vc_errcode", Types.VARCHAR);
            callableStatement.registerOutParameter("@po_i_ApplicationID", Types.VARCHAR);

            callableStatement.setString("@pi_vc_transactionidentifier", headers.get("transactionId"));
            callableStatement.setString("@pi_vc_transactiontimezone", "GST");
            callableStatement.setString("@pi_vc_countryorigin", "AE");
            callableStatement.setTimestamp("@pi_dt_transactiondate",
                    new Timestamp(new Date().getTime()));
            callableStatement.setString("@pi_vc_clientidentifier", headers.get("channelid"));

            callableStatement.setInt("@pi_ti_txnsource", 48);
            callableStatement.setInt("@pi_ti_txntype", 602);
            callableStatement.setString("@pi_vc_bpmsrefnum", "");
            callableStatement.setString("@pi_vc_cinno", null);
            callableStatement.setInt("@pi_i_producttypeid", 25);
            callableStatement.setFloat("@pi_n_custriskrate", request.getRiskRating());
            callableStatement.setInt("@pi_i_kyb", 0);
            callableStatement.setString("@pi_c_custtype", "C");
            callableStatement.setString("@pi_vc_salut", request.getTitle());
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
            callableStatement.setString("@pi_vc_add2", request.getAddressLine2());
            callableStatement.setString("@pi_vc_add3", request.getAddressLine3());
            callableStatement.setString("@pi_vc_add4", request.getAddressLine4());
            callableStatement.setString("@pi_vc_city", request.getCity());
            callableStatement.setInt("@pi_ti_emiratescd", request.getEmirate());
            callableStatement.setString("@pi_vc_postalcd", request.getPostalCode());
            callableStatement.setString("@pi_vc_resphoneno", request.getTelephoneNo());
            callableStatement.setString("@pi_vc_ofcphoneno", "");
            callableStatement.setString("@pi_vc_faxno", "");
            callableStatement.setString("@pi_vc_mob", request.getMobileNumber());
            callableStatement.setString("@pi_vc_emailid", request.getEmailID());
            callableStatement.setString("@pi_vc_nationality", request.getNationality());
            callableStatement.setString("@pi_vc_comcregno", request.getCompanyRegNo());
            callableStatement.setString("@pi_vc_dateofestb", request.getDateOfEstablishment());
            callableStatement.setInt("@pi_ti_docsVrfFlag", 1);
            callableStatement.setString("@pi_vc_makerid", "ADPW");
            callableStatement.setString("@pi_vc_companyembname_arabic", "");
            callableStatement.setString("@pi_vc_embname_arabic", "");
            callableStatement.setString("@pi_vc_companyembname_english", request.getCompanyEmbName());
            callableStatement.setInt("@pi_i_identitytype", request.getIdType());
            callableStatement.setString("@pi_vc_identitytypedesc", "");
            callableStatement.setString("@pi_vc_identityissuedate", request.getEidIssueDate());
            callableStatement.setString("@pi_vc_identityexpirydate", request.getEidExpiryDate());
            callableStatement.setString("@pi_vc_identityno", request.getEmiratesID());
            callableStatement.setString("@pi_vc_identityissueplace", request.getEidIssuePlace());
            callableStatement.setString("@pi_i_identityissuecountry", request.getEidIssueCountry());
            callableStatement.setString("@pi_vc_preflanguage", request.getPreferredLanguage());
            callableStatement.setInt("@pi_ti_deliverymode", 2);
            callableStatement.setString("@pi_vc_deliveryposid", "A01");
            callableStatement.setString("@pi_vc_occupcode", request.getOccupation());
            callableStatement.setString("@Pi_dmsReqID", request.getDocumentDetails());
            callableStatement.setString("@pi_vc_docsCollection", "");
            callableStatement.setString ("@pi_vc_deliveryvendorid", "");
            callableStatement.setString("@pi_xml_fatcacrs", "");
            callableStatement.setString("@pi_vc_screeningResult", "1");
            
            callableStatement.execute();

            CustomerOnboardResponse response = new CustomerOnboardResponse();
            response.setStatusCode(callableStatement.getString("@po_vc_errcode"));
            response.setStatusText(callableStatement.getString("@po_vc_errortext"));
            response.setApplicationId(callableStatement.getString("@po_i_ApplicationID"));
            return response;

        }
    }

}
