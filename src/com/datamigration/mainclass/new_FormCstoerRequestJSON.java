package com.datamigration.mainclass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.demandware.xml.impex.customer._2006_10_31.ComplexTypeAddress;
import com.demandware.xml.impex.customer._2006_10_31.ComplexTypeCustomer;
import com.demandware.xml.impex.customer._2006_10_31.ComplexTypeProfile;
import com.demandware.xml.impex.customer._2006_10_31.CustomerList;
import com.demandware.xml.impex.customer._2006_10_31.SharedTypeCustomAttribute;
import com.demandware.xml.impex.customer._2006_10_31.SharedTypeCustomAttributes;

public class new_FormCstoerRequestJSON {
	static ExtractMainProperties ext = new ExtractMainProperties();
	public static void main(String[] args) throws IOException, JAXBException {
		File legacyfile = new File(ext.getPropValue("json-Form-List"));
		CustomerList que = new CustomerList();
		JAXBContext jaxbContext = JAXBContext.newInstance(CustomerList.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		que = (CustomerList) jaxbUnmarshaller.unmarshal(legacyfile);
		
		
		FileWriter myWriter = new FileWriter(ext.getPropValue("json-Form-List-output"), false);
		StringBuilder sb = new StringBuilder();
		
		boolean isLooptarted = false;
		
		
		
		for(ComplexTypeCustomer vComplexTypeCustomer : que.getCustomer()) {
			sb.append("{\"CreateCustomerRequest\" : [");
			ComplexTypeProfile vComplexTypeProfile = vComplexTypeCustomer.getProfile();
			if(vComplexTypeProfile == null) {
				continue;
			}
				SharedTypeCustomAttributes vSharedTypeCustomAttributes = vComplexTypeProfile.getCustomAttributes();
			String optin = getCustomAttribute(vSharedTypeCustomAttributes,"optIn");
			
			
			
			
			
			sb.append("{");
			sb.append("\"CustomerId\":\"").append(vComplexTypeCustomer.getCustomerNo()).append("\",\"Title\":\"\"");
			if(vComplexTypeCustomer.getAddresses() != null) {
				boolean isAddressStarted = false;
				JSONArray JSONObjectAray = new JSONArray();
				
				JSONObject CreateCustomerRequestAddress = new JSONObject();
				
			for(ComplexTypeAddress vComplexTypeAddress : vComplexTypeCustomer.getAddresses().getAddress()) {
				CreateCustomerRequestAddress = new JSONObject();
				CreateCustomerRequestAddress.put("AddressId",vComplexTypeAddress.getAddressId());
				CreateCustomerRequestAddress.put("Type","");
				CreateCustomerRequestAddress.put("AddressLine",vComplexTypeAddress.getAddress1()+" "+vComplexTypeAddress.getAddress2());
				CreateCustomerRequestAddress.put("City",vComplexTypeAddress.getCity());
				CreateCustomerRequestAddress.put("State",vComplexTypeAddress.getStateCode());
				CreateCustomerRequestAddress.put("Zip",vComplexTypeAddress.getPostalCode());
				CreateCustomerRequestAddress.put("Country",vComplexTypeAddress.getCountryCode());
				if("true".equalsIgnoreCase(optin)){
					CreateCustomerRequestAddress.put("ReceiveMail","Yes");
				}else if("false".equalsIgnoreCase(optin)) {
					CreateCustomerRequestAddress.put("ReceiveMail","No");
				}else {
					CreateCustomerRequestAddress.put("ReceiveMail","Unknown");
				}
				CreateCustomerRequestAddress.put("IsDeleted","No");
				JSONObjectAray.add(CreateCustomerRequestAddress);
			}
			
			if(JSONObjectAray.size() > 0) {
				sb.append(",\"Address\":").append(JSONObjectAray.toJSONString());
			}
			}
			sb.append(",\"Phone\":[{\"Type\":\"Mobile\",\"Number\":\"").append(vComplexTypeProfile.getPhoneHome()).append("\",\"ReceiveCalls\":\"\"}],");
			sb.append("\"Email\":[{\"EmailId\":\"").append(vComplexTypeProfile.getEmail()).append("\",\"Email\":\"").append(vComplexTypeProfile.getEmail()).append("\",\"ReceiveEmails\":\"No\"}],");
			sb.append("\"BirthYear\":\"\",\"BirthMonth\":\"\",\"BirthDay\":\"\",\"Gender\":\"\",\"AltFirstName\":\"\",\"AltLastName\":\"\",\"SystemCountryCode\":\"US\",\"MktSourceCode\":\"KS-ACCOUNT-REG\",\"FirstName\":\"");
			sb.append(vComplexTypeProfile.getFirstName()).append("\",\"LastName\":\"").append(vComplexTypeProfile.getLastName()).append("\",\"Brand\":\"KS\",\"Source\":\"SFCCNA\",\"Channel\":\"RETAIL\"");		
			sb.append("}\n");
	
			isLooptarted = true;
			sb.delete(sb.length()-1, sb.length());
			sb.append("]}\n");
		}

		
		myWriter.write(sb.toString());
		myWriter.close();
		
	}
	private static String getCustomAttribute(SharedTypeCustomAttributes vSharedTypeCustomAttributes, String val) {
		if(vSharedTypeCustomAttributes == null || (vSharedTypeCustomAttributes != null && vSharedTypeCustomAttributes.getCustomAttribute() == null)) {
			return "";
		}
		for(SharedTypeCustomAttribute vSharedTypeCustomAttribute : vSharedTypeCustomAttributes.getCustomAttribute()) {
			if("val".equals(vSharedTypeCustomAttribute.getAttributeId())){
				return vSharedTypeCustomAttribute.getContent().get(0).toString();					
			}
			
		}
		return "";
	}

}
