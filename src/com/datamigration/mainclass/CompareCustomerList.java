package com.datamigration.mainclass;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.demandware.xml.impex.customer._2006_10_31.ComplexTypeAddress;
import com.demandware.xml.impex.customer._2006_10_31.ComplexTypeCustomer;
import com.demandware.xml.impex.customer._2006_10_31.CustomerList;
import com.demandware.xml.impex.customer._2006_10_31.SharedTypeCustomAttribute;


public class CompareCustomerList {
	static ExtractMainProperties ext = new ExtractMainProperties();
	public static void main(String[] args) throws IOException, JAXBException, IntrospectionException {
		File legacyfile = new File(ext.getPropValue("legacy-customer-list"));
		File torofile = new File(ext.getPropValue("toro-customer-list"));
		JAXBContext jaxbContext = JAXBContext.newInstance(CustomerList.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		CustomerList que = (CustomerList) jaxbUnmarshaller.unmarshal(legacyfile);
		
		JAXBContext jaxbContextToro = JAXBContext.newInstance(CustomerList.class);
		Unmarshaller jaxbUnmarshallerToro = jaxbContextToro.createUnmarshaller();
		CustomerList queToro = (CustomerList) jaxbUnmarshallerToro.unmarshal(torofile);
		//compareHeader(que.getHeader(),queToro.getHeader());
		extractCustomAttributesFromAddress(que,queToro);
		/*
		 * for(ComplexTypeCustomer customer : que.getCustomer()) {
		 * System.out.println(customer.getCustomerNo());
		 * 
		 * }
		 */

	}
	private static void extractCustomAttributesFromAddress(CustomerList que, CustomerList queToro) {
		List<String> toroAttribute = new ArrayList<String>();
		List<String> legacyAttribute = new ArrayList<String>();
		for (ComplexTypeCustomer vComplexTypeCustomer : que.getCustomer()) {
			if(vComplexTypeCustomer != null &&  vComplexTypeCustomer.getAddresses() != null) {
				for (ComplexTypeAddress vComplexTypeAddress :  vComplexTypeCustomer.getAddresses().getAddress()) {
					if(vComplexTypeAddress != null &&  vComplexTypeAddress.getCustomAttributes() != null && vComplexTypeAddress.getCustomAttributes().getCustomAttribute() != null) {
						for(SharedTypeCustomAttribute vSharedTypeCustomAttribute : vComplexTypeAddress.getCustomAttributes().getCustomAttribute()) {
							if(!legacyAttribute.contains(vSharedTypeCustomAttribute.getAttributeId())) {
								legacyAttribute.add(vSharedTypeCustomAttribute.getAttributeId());
							}		
						}
					}
				}
				

			}

			
		}
		
		
		for (ComplexTypeCustomer vComplexTypeCustomer : queToro.getCustomer()) {
			if(vComplexTypeCustomer != null &&  vComplexTypeCustomer.getAddresses() != null) {
				for (ComplexTypeAddress vComplexTypeAddress :  vComplexTypeCustomer.getAddresses().getAddress()) {
					if(vComplexTypeAddress != null &&  vComplexTypeAddress.getCustomAttributes() != null && vComplexTypeAddress.getCustomAttributes().getCustomAttribute() != null) {
						for(SharedTypeCustomAttribute vSharedTypeCustomAttribute : vComplexTypeAddress.getCustomAttributes().getCustomAttribute()) {
							if(!toroAttribute.contains(vSharedTypeCustomAttribute.getAttributeId())) {
								toroAttribute.add(vSharedTypeCustomAttribute.getAttributeId());
							}		
						}
					}
				}
				

			}

			
		}
		
		
		System.out.println(toroAttribute);
		System.out.println(legacyAttribute);
		
	}
	
	private static void extractCustomAttributes(CustomerList que, CustomerList queToro) {
		List<String> toroAttribute = new ArrayList<String>();
		List<String> legacyAttribute = new ArrayList<String>();
		for (ComplexTypeCustomer vComplexTypeCustomer : que.getCustomer()) {
			if(vComplexTypeCustomer != null &&  vComplexTypeCustomer.getProfile() != null && vComplexTypeCustomer.getProfile().getCustomAttributes() !=null &&  vComplexTypeCustomer.getProfile().getCustomAttributes().getCustomAttribute() != null) {
				for(SharedTypeCustomAttribute vSharedTypeCustomAttribute : vComplexTypeCustomer.getProfile().getCustomAttributes().getCustomAttribute()) {
					if(!legacyAttribute.contains(vSharedTypeCustomAttribute.getAttributeId())) {
						legacyAttribute.add(vSharedTypeCustomAttribute.getAttributeId());
					}		
				}
			}

			
		}
		for (ComplexTypeCustomer vComplexTypeCustomer : queToro.getCustomer()) {
			if(vComplexTypeCustomer != null &&  vComplexTypeCustomer.getProfile() != null && vComplexTypeCustomer.getProfile().getCustomAttributes() !=null &&  vComplexTypeCustomer.getProfile().getCustomAttributes().getCustomAttribute() != null) {
				for(SharedTypeCustomAttribute vSharedTypeCustomAttribute : vComplexTypeCustomer.getProfile().getCustomAttributes().getCustomAttribute()) {
					if(!toroAttribute.contains(vSharedTypeCustomAttribute.getAttributeId())) {
						toroAttribute.add(vSharedTypeCustomAttribute.getAttributeId());
					}		
				}
			}
			
		}
		System.out.println(toroAttribute);
		System.out.println(legacyAttribute);
		
	}
	private static void compareHeader(Object legacyHeader, Object toroHEader) throws IntrospectionException {
		Field[] fields = legacyHeader.getClass().getDeclaredFields();
		for(Field field  : fields) {
			System.out.println(field.getName()+" >>> Legacy: "+callGetter(legacyHeader, field.getName())+" >>> Toro: "+callGetter(toroHEader, field.getName()));
		}
		
	}
	 private static String callGetter(Object obj, String fieldName) throws IntrospectionException{
		  try {
			  PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
	            Method getter = pd.getReadMethod();
	            Object f = getter.invoke(obj);
	            return f == null ? "" : f.toString();
		  } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		   // TODO Auto-generated catch block
		  // e.printStackTrace();
		  }
		  return "";
		 }
}
