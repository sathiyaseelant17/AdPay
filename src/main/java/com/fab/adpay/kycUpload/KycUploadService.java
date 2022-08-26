package com.fab.adpay.kycUpload;


import com.fab.adpay.Datasource;
import com.fab.adpay.exception.ElpasoException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class KycUploadService {
	private static final Logger LOGGER = LoggerFactory.getLogger(KycUploadService.class);

	public static DMSConfiguration getDMSConfiguration(Map<String, String> headers, KycUploadRequest kycUploadRequest)
			throws SQLException {
		LOGGER.info("Calling procedure proc_getDMS_configuration");
		try (Connection connection = Datasource.getConnection();
			 CallableStatement callableStatement = connection.prepareCall(
					 "{call proc_get_dmsconfig(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
			callableStatement.registerOutParameter("@po_vc_errcode", Types.INTEGER);
			callableStatement.registerOutParameter("@po_vc_errortext", Types.VARCHAR);
			callableStatement.setString("@pi_c_type","T");
			callableStatement.setString("@pi_vc_cardno","");
			callableStatement.setString("@pi_vc_transactionIdentifier", headers.get("transactionid"));
			callableStatement.setString("@pi_vc_clientIdentifier", headers.get("channelid"));
			callableStatement.setString("@pi_vc_transactionTimezone", "GST");
			callableStatement.setString("@pi_vc_countryOforgin", "AE");
			callableStatement.setTimestamp("@pi_dt_transactiondate",
					Timestamp.valueOf(new Timestamp(new Date().getTime()).toString()));
			List<DMSConfigurationElpResponse> dmsConfigurationElpResponseList =new ArrayList<>();
			DMSResponse dmsResponseWithList=new DMSResponse();
			try( ResultSet rs = callableStatement.executeQuery()){
				if(rs!=null) {
					while (rs.next()) {
						LOGGER.info("Transaction id: {} While block : {}", headers.get("transactionid"),
								"checking while");
						DMSConfigurationElpResponse dmsConfigurationElpResponse =new DMSConfigurationElpResponse();
						dmsConfigurationElpResponse.setDirDtlAttAclDomain(rs.getString("dirDtlAttAclDomain"));
						dmsConfigurationElpResponse.setDirDtlAttAclName(rs.getString("dirDtlAttAclName"));
						dmsConfigurationElpResponse.setDocDtlAttAclCardId(rs.getString("docDtlAttAclCardId"));
						dmsConfigurationElpResponse.setDocDtlAttAclDocType(rs.getString("docDtlAttAclDocType"));
						dmsConfigurationElpResponse.setDocDtlAttAclDomain(rs.getString("docDtlAttAclDomain"));
						dmsConfigurationElpResponse.setDocDtlAttAclName(rs.getString("docDtlAttAclName"));
						dmsConfigurationElpResponse.setDocDtlAttAclObjName(rs.getString("docDtlAttAclObjName"));
						dmsConfigurationElpResponse.setDocumentType(rs.getString("documentType"));
						dmsConfigurationElpResponse.setFileName(rs.getString("fileName"));
						dmsConfigurationElpResponse.setObjectFolder(rs.getString("objectFolder"));
						dmsConfigurationElpResponseList.add(dmsConfigurationElpResponse);
						LOGGER.info("Elpaso Response", dmsConfigurationElpResponse);

					}
					dmsResponseWithList.setStatusCode(callableStatement.getInt("@po_vc_errcode"));
					dmsResponseWithList.setStatusText(callableStatement.getString("@po_vc_errortext"));
					dmsResponseWithList.setDmsConfigurationElpResponsesList(dmsConfigurationElpResponseList);
				}else {
					LOGGER.info("Transaction id: {} ResultSet Empty: {}", headers.get("transactionid"),
							"resultSet value null/empty");
					throw new ElpasoException(callableStatement.getInt("@po_vc_errcode"),
							callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
				}
			}catch(Exception e) {
				LOGGER.info("Transaction id: {} Catch block excecuted erorrCode: {}", headers.get("transactionid"),callableStatement.getInt("@po_vc_errcode"));
				callableStatement.execute();
				throw new ElpasoException(callableStatement.getInt("@po_vc_errcode"),
						callableStatement.getString("@po_vc_errortext"), headers.get("transactionid"));
			}
			DMSConfiguration DMSConfiguration = new DMSConfiguration();
			DMSConfiguration.setRequestId(headers.get("transactionid"));
			DMSConfiguration.setSourceSystemName("adpay");
			DMSConfiguration.setTargetPathToUpload("/Consumer banking/ADPay Wallet Temp");
			DMSConfiguration.setObjectPath("ADPay Wallet Temp");
			DMSConfiguration.setObjectFolder(dmsConfigurationElpResponseList.get(0).getObjectFolder());
			DirectoryDetails directoryDetails = new DirectoryDetails();
			directoryDetails.setFolderName("ADPay Wallet Temp");
			directoryDetails.setAclName(dmsConfigurationElpResponseList.get(0).getDirDtlAttAclName());
			directoryDetails.setAclDomain(dmsConfigurationElpResponseList.get(0).getDirDtlAttAclDomain());
			DMSConfiguration.setDirectoryDetails(directoryDetails);
			DMSConfiguration.setDocumentType(dmsConfigurationElpResponseList.get(0).getDocumentType());
			DocumentDetails documentDetails = new DocumentDetails();
			documentDetails.setEmiratesId("");
			documentDetails.setElpasaCif("");
			documentDetails.setProduct("Prepaid Wallet");
			documentDetails.setReferenceNumber(headers.get("transactionid"));
			documentDetails.setAclName(dmsConfigurationElpResponseList.get(0).getDocDtlAttAclName());
			documentDetails.setAclDomain(dmsConfigurationElpResponseList.get(0).getDocDtlAttAclDomain());
			documentDetails.setDocumentType(dmsConfigurationElpResponseList.get(0).getDocumentType());
			documentDetails.setObjectName(dmsConfigurationElpResponseList.get(0).getDocDtlAttAclObjName());
			documentDetails.setAclCardId(dmsConfigurationElpResponseList.get(0).getDocDtlAttAclCardId());
			DMSConfiguration.setDocumentDetails(documentDetails);
			DMSConfiguration.setFileName(dmsConfigurationElpResponseList.get(0).getFileName());
			DMSConfiguration.setFileType("pdf");
			DMSConfiguration.setStatusCode(String.valueOf(dmsResponseWithList.getStatusCode()));
			DMSConfiguration.setStatusText(dmsResponseWithList.getStatusText());
			return DMSConfiguration;
		}
	}

	public static KycUploadResponse updateDocumentUploadStatus(Map<String, String> headers, JsonNode node) throws SQLException {
		KycUploadResponse response = new KycUploadResponse();
		response.setDocumentId("");
		if (!node.get("Body").get("UploadDocumentResponse").get("status").textValue().equalsIgnoreCase("FAILURE")) {
			response.setDocumentId(node.get("Body").get("UploadDocumentResponse").get("data").get("documentID").textValue());
			response.setStatusCode("0");
			response.setStatusText(null);
		} else {
			response.setDocumentId("");
			response.setStatusCode(
					node.get("Body").get("UploadDocumentResponse").get("error").get("code").textValue());
			response.setStatusText(
					node.get("Body").get("UploadDocumentResponse").get("error").get("description").textValue());
		}
		LOGGER.info("Transaction id: {} SetDocumentResponse: {}", headers.get("transactionid"),response);
		return response;
	}
	public static XMLGregorianCalendar getGregorianCalendarFormat(String dateStr)
			throws ParseException, DatatypeConfigurationException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse(dateStr);

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);

		return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	}

	public static String uploadDocument(DMSConfiguration dmsConfiguration, KycUploadRequest kycUploadRequest,
			Map<String, String> headers) throws DatatypeConfigurationException, ParseException, IOException {
		String payload = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
				+ "\t<soapenv:Body>\n" + "\t<mes:UploadDocumentRequest\n"
				+ "\txmlns:mes=\"http://services.fgb.com/doc/message\"\n" + "\txmlns:ser=\"http://services.fgb.com/\"\n"
				+ "\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + "\t<ser:requestId>"
				+ dmsConfiguration.getRequestId() + "</ser:requestId>\n" + "\t<ser:sourceSystem>"
				+ dmsConfiguration.getSourceSystemName() + "</ser:sourceSystem>\n" + "\t<ser:requestTime>"
				+ getGregorianCalendarFormat(headers.get("transactiondatetime")).toXMLFormat() + "</ser:requestTime>\n"
				+ "\t<ser:data xsi:type=\"mes:UploadDocumentRequestType\">\n" + "\t<targetPath>"
				+ dmsConfiguration.getTargetPathToUpload() + "</targetPath>\n" + "\t<directoryDetails>\n"
				+ "\t<directoryAttributes>\n" + "\t<objectPath>" + dmsConfiguration.getObjectPath() + "</objectPath>\n"
				+ "\t<objectType>" + dmsConfiguration.getObjectFolder() + "</objectType>\n" + "\t<objectAttributes>\n"
				+ "\t<name>object_name</name>\n" + "\t<value>" + dmsConfiguration.getDirectoryDetails().getFolderName()
				+ "</value>\n" + "\t</objectAttributes>\n" + "\t<objectAttributes>\n" + "\t<name>acl_name</name>\n"
				+ "\t<value>" + dmsConfiguration.getDirectoryDetails().getAclName() + "</value>\n" + "\t</objectAttributes>\n"
				+ "\t<objectAttributes>\n" + "\t<name>acl_domain</name>\n" + "\t<value>"
				+ dmsConfiguration.getDirectoryDetails().getAclDomain() + "</value>\n" + "\t</objectAttributes>\n"
				+ "\t</directoryAttributes>\n" + "\t</directoryDetails>\n" + "\t<documentDetails>\n"
				+ "\t<documentType>" + dmsConfiguration.getDocumentType() + "</documentType>\n"
				+ "\t<documentAttributes>\n" + "\t<name>object_name</name>\n" + "\t<value>"
				+ dmsConfiguration.getDocumentDetails().getObjectName() + "</value>\n" + "\t</documentAttributes>\n"
				+ "\t<documentAttributes>\n" + "\t<name>emirates_id</name>\n" + "\t<value>"
				+ dmsConfiguration.getDocumentDetails().getEmiratesId() + "</value>\n" + "\t</documentAttributes>\n"
				+ "\t<documentAttributes>\n" + "\t<name>product</name>\n" + "\t<value>"
				+ dmsConfiguration.getDocumentDetails().getProduct() + "</value>\n" + "\t</documentAttributes>\n"
				+ "\t<documentAttributes>\n" + "\t<name>reference_number</name>\n" + "\t<value>"
				+ dmsConfiguration.getDocumentDetails().getReferenceNumber() + "</value>\n" + "\t</documentAttributes>\n"
				+ "\t<documentAttributes>\n" + "\t<name>type_of_document</name>\n" + "\t<value>"
				+ dmsConfiguration.getDocumentType() + "</value>\n" + "\t</documentAttributes>\n"
				+ "\t<documentAttributes>\n" + "\t<name>customer_id</name>\n" + "\t<value>"
				+ dmsConfiguration.getDocumentDetails().getElpasaCif() + "</value>\n" + "\t</documentAttributes>\n"
				+ "\t<documentAttributes>\n" + "\t<name>wallet_id</name>\n" + "\t<value>"
				+ dmsConfiguration.getDocumentDetails().getAclCardId() + "</value>\n" + "\t</documentAttributes>\n"
				+ "\t<documentAttributes>\n" + "\t<name>customer_name</name>\n" + "\t<value></value>\n"
				+ "\t</documentAttributes>\n" + "\t<documentAttributes>\n" + "\t<name>acl_name</name>\n" + "\t<value>"
				+ dmsConfiguration.getDocumentDetails().getAclName() + "</value>\n" + "\t</documentAttributes>\n"
				+ "\t<documentAttributes>\n" + "\t<name>acl_domain</name>\n" + "\t<value>"
				+ dmsConfiguration.getDocumentDetails().getAclDomain() + "</value>\n" + "\t</documentAttributes>\n"
				+ "\t<documentAttributes>\n" + "\t<name>source_system</name>\n" + "\t<value>"
				+ dmsConfiguration.getSourceSystemName() + "</value>\n" + "\t</documentAttributes>\n"
				+ "\t<attachment>\n" + "\t<fileName>" + dmsConfiguration.getFileName() + "</fileName>\n"
				+ "\t<fileType>" + dmsConfiguration.getFileType() + "</fileType>\n" + "\t<content>"
				+ kycUploadRequest.getContent() + "</content>\n" + "\t</attachment>\n" + "\t</documentDetails>\n"
				+ "\t</ser:data>\n" + "\t</mes:UploadDocumentRequest>\n" + "\t</soapenv:Body>\n"
				+ "\t</soapenv:Envelope>";
		LOGGER.info("Transaction Id : {} DMS Service payload:\n{}",headers.get("transactionid"), payload);
		String dmsServiceUrl = System.getenv("DMS_SERVICE_URL");
		URL url = new URL(dmsServiceUrl);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/xml");
		connection.setRequestProperty("Accept", "text/xml");
		connection.setDoOutput(true);
		try (OutputStream os = connection.getOutputStream()) {
			byte[] input = payload.getBytes(StandardCharsets.UTF_8);
			os.write(input, 0, input.length);
		}
		LOGGER.info("Transaction Id : {} DMS service response code: {}",headers.get("transactionid"),connection.getResponseCode());
		if (100 <= connection.getResponseCode() && connection.getResponseCode() <= 399) {
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				LOGGER.info("Transaction Id : {} DMS service response:\n{}",headers.get("transactionid"),response);
				return response.toString();
			}
		} else {
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				LOGGER.info("Transaction Id : {} DMS service response:\n{}",headers.get("transactionid"), response);
				return response.toString();
			}
		}
	}
}
