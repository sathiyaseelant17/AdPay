
package com.fab.adpay.customerOnboard;

import java.sql.*;
import java.util.*;
import java.util.Date;

import com.fab.adpay.Datasource;
import com.fab.adpay.controller.AdPayController;
import com.fab.adpay.customerOnboard.model.*;
import com.fab.adpay.exception.ElpasoException;
import com.fab.adpay.fetchCustomerOnbordingDetails.FetchDetailsResponse;
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
    @Autowired
    RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdPayController.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
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
            if (!(callableStatement.getInt("@po_vc_errcode") == 0)) {
                throw new ElpasoException(callableStatement.getInt("@po_vc_errcode"),
                        callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
            }
            CustomerOnboardResponse response = new CustomerOnboardResponse();
            response.setStatusCode(callableStatement.getString("@po_vc_errcode"));
            response.setStatusText(callableStatement.getString("@po_vc_errortext"));
            response.setApplicationId(callableStatement.getString("@po_i_ApplicationID"));
            return response;


        }



    }

    public BPMSResponse initiateBPMS(String id, Map<String, String> headers, CustomerOnboardRequest request) {

        String URL = System.getenv("INITIATE_BPMS");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        
        
        BPMSRequest payload =new BPMSRequest();

        CustomerDetails customerDetails=new CustomerDetails();
        customerDetails.setAddressLine1(request.getAddressLine1());
        customerDetails.setAddressLine2(request.getAddressLine2());
        customerDetails.setAddressLine3(request.getAddressLine3());
        customerDetails.setAddressLine4(request.getAddressLine4());
        customerDetails.setCashTransactionMode(request.getCashTransactionMode());
        customerDetails.setCity(request.getCity());
        customerDetails.setCompanyEmbName(request.getCompanyEmbName());
        customerDetails.setCompanyName(request.getCompanyName());
        customerDetails.setCompanyRegNo(request.getCompanyRegNo());
        customerDetails.setCountryCode(request.getCountryOfResidence());
        customerDetails.setCountryOfBirth(request.getCountryOfBirth());
        customerDetails.setCustomerID(request.getCustomerID());
        customerDetails.setDateOfBirth(request.getDateOfBirth());
        customerDetails.setDateOfEstablishment(request.getDateOfEstablishment());
        customerDetails.setEidExpiryDate(request.getEidExpiryDate());
        customerDetails.setEidIssueCountry(request.getEidIssueCountry());
        customerDetails.setEidIssueDate(request.getEidIssueDate());
        customerDetails.setEidIssuePlace(Integer.parseInt(request.getEidIssuePlace()));
        customerDetails.setEmailID(request.getEmailID());
        customerDetails.setEmbossedName(request.getEmbossedName());
        customerDetails.setEmirate(request.getEmirate());
        customerDetails.setEmirateDesc(request.getEmirateDesc());
        customerDetails.setEmiratesID(request.getEmiratesID());
        customerDetails.setFirstName(request.getFirstName());
        customerDetails.setGender(request.getGender());
        customerDetails.setHoldMailService(request.getHoldMailService());
        customerDetails.setIdType(request.getIdType());
        customerDetails.setLastName(request.getLastName());
        customerDetails.setMiddleName(request.getMiddleName());
        customerDetails.setMobileNumber(request.getMobileNumber());
        customerDetails.setNationality(request.getNationality());
        customerDetails.setOccupation(request.getOccupation());
        customerDetails.setPEPStatus(request.getPepStatus());
        customerDetails.setPlaceOfBirth(request.getPlaceOfBirth());
        customerDetails.setPostalCode(request.getPostalCode());
        customerDetails.setPreferredBranchCode(request.getPreferredBranchCode());
        customerDetails.setPreferredBranchName(request.getPreferredBranchName());
        customerDetails.setPreferredLanguage(request.getPreferredLanguage());
        customerDetails.setRiskRating(request.getRiskRating());
        customerDetails.setRiskRatingAMLSegment(request.getT24AMLSegment());
        customerDetails.setT24AccountOfficer(request.getT24AccountOfficer());
        customerDetails.setT24Company(request.getT24Company());
        customerDetails.setT24CompanyCode(request.getT24CompanyCode());
        customerDetails.setT24Industry(request.getT24Industry());
        customerDetails.setT24Sector(request.getT24Sector());
        customerDetails.setT24Target(request.getT24Target());
        customerDetails.setTelephoneNo(request.getTelephoneNo());
        customerDetails.setTitle(request.getTitle());
        customerDetails.setCardID(id);

        FatcaCRSDetails fatcaCRSDetails=new FatcaCRSDetails();

        fatcaCRSDetails.setAlternateNationality(request.getAlternateNationality());
        fatcaCRSDetails.setAvgTransactionValue(request.getAverageTransactionValue());
        fatcaCRSDetails.setCRSDeclaration(request.getCrsDeclaration());
        fatcaCRSDetails.setDeclaration(request.getDeclaration());
        fatcaCRSDetails.setDualNationality(request.getDualNationality());
        fatcaCRSDetails.setEmployerName(request.getEmployerName());
        fatcaCRSDetails.setEmploymentStatus(request.getEmploymentStatus());
        fatcaCRSDetails.setGreenCardID(request.getGreenCardID());
        fatcaCRSDetails.setIndustries(request.getIndustries());
        List<Jurisdiction> jurisdictionList=new ArrayList<>();
        Jurisdiction jurisdiction=new Jurisdiction();
        jurisdiction.setOtherResidency(request.getOtherResidency());
        jurisdiction.setResidenceFromDate(request.getResidenceFromDate());
        jurisdiction.setResidenceToDate(request.getResidenceToDate());
        jurisdictionList.add(jurisdiction);
        fatcaCRSDetails.setJurisdiction(jurisdictionList);
        fatcaCRSDetails.setMonthlyIncome(request.getMonthlyIncome());
        fatcaCRSDetails.setNonUAENonUSTaxResident(request.getNonUAENonUSTaxResident());
        fatcaCRSDetails.setPassportNumber(request.getPassportNumber());
        fatcaCRSDetails.setPersonalTaxJurisdiction(request.getPersonalTaxJurisdiction());
        fatcaCRSDetails.setReasonForNoDeclaration(request.getReasonForNoDeclaration());
        fatcaCRSDetails.setResidentInOtherJurisdiction(request.getResidentInOtherJurisdiction());
        List<TaxResidency> taxResidencyList=new ArrayList<>();
        TaxResidency taxResidency=new TaxResidency();
        taxResidency.setTINNumber(request.getTIN());
        taxResidency.setReason(request.getReason());
        taxResidency.setReasonForNoTIN(request.getReasonNoTIN());
        taxResidency.setTaxCountry(request.getJurisdictionCountry());
        taxResidencyList.add(taxResidency);
        fatcaCRSDetails.setTaxResidency(taxResidencyList);
        fatcaCRSDetails.setTINNumber(request.getTinNumber());
        fatcaCRSDetails.setTrdLicensePlaceOfIssue(request.getTrdLicensePlaceOfIssue());
        fatcaCRSDetails.setUAEResidencyByRBIScheme(request.getUaeResidencyByRBIScheme());
        fatcaCRSDetails.setUSPerson(request.getUsPerson());

        RequestData requestData=new RequestData();
        requestData.setActionName(request.getActionName());
        requestData.setApplicationRefNo(request.getApplicationRefNo());
        requestData.setApplicationRemarks(request.getApplicationRemarks());
        requestData.setApplicationStatus(request.getApplicationStatus());
        List<String> DocumentDetailsList=new ArrayList<>();
        DocumentDetailsList.add(request.getDocumentDetails());
        requestData.setDocumentDetails(DocumentDetailsList);
        requestData.setProduct(request.getProduct());
        requestData.setTandCAgreed(request.getTandCAgreed());
        requestData.setUAEPassResult(request.getUaePassResult());
        requestData.setWebsitePageIdentifier(request.getWebsitePageIdentifier());
        requestData.setCustomerDetails(customerDetails);
        requestData.setFatcaCRSDetails(fatcaCRSDetails);

        payload.setChannelID(headers.get("channelid"));
        payload.setRequestID(headers.get("transactionid"));
        payload.setRequestTimeStamp(new Timestamp(new Date().getTime()).toString());
        payload.setRequestData(requestData);
        
        System.out.println("payload"+ payload);
        HttpEntity<BPMSRequest> entity = new HttpEntity<BPMSRequest>(payload, header);
        BPMSResponse response=new BPMSResponse();
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
            LOGGER.info("Transaction Id : {} Initiate BPMS Status Code: {}", headers.get("transactionid"), responseEntity.getStatusCode());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String bpmsResponse = responseEntity.getBody();
                response = OBJECT_MAPPER.readValue(bpmsResponse,BPMSResponse.class);
                LOGGER.info("Transaction Id : {} Response body: {}", headers.get("transactionid"), bpmsResponse);
            } else {
                LOGGER.info("Initiate BPMS Service Response status fails",responseEntity.getStatusCode());
            }
        }catch(Exception e){
            LOGGER.info("Transaction Id : {} Initiate BPMS catch block executed", headers.get("transactionid"), e);

        }

        return response;
    }

}
