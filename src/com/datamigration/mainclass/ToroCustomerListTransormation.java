package com.datamigration.mainclass;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.demandware.xml.impex.customer._2006_10_31.ComplexTypeAddress;
import com.demandware.xml.impex.customer._2006_10_31.ComplexTypeCustomer;
import com.demandware.xml.impex.customer._2006_10_31.ComplexTypeHeader;
import com.demandware.xml.impex.customer._2006_10_31.ComplexTypeProfile;
import com.demandware.xml.impex.customer._2006_10_31.CustomerList;
import com.demandware.xml.impex.customer._2006_10_31.Customers;
import com.demandware.xml.impex.customer._2006_10_31.SharedTypeCustomAttribute;


public class ToroCustomerListTransormation {
	static XMLGregorianCalendar compareCar;
	static GregorianCalendar c_startDate = new GregorianCalendar();
	static GregorianCalendar c_endDate = new GregorianCalendar();
	static XMLGregorianCalendar startDate;
	static XMLGregorianCalendar endDate;
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	static ExtractMainProperties ext = new ExtractMainProperties();
	static List<ComplexTypeCustomer> updatedCustomer =new ArrayList<ComplexTypeCustomer>();
	//static ComplexTypeHeader updatedHEader;	
	static String updatedListId ="Shop";
	static int batchSize;
	static String exportPrefix ;
	static boolean isFullLoad = false;
	public static void main(String[] args) throws IOException, JAXBException, IntrospectionException, ParseException, DatatypeConfigurationException {
		File legacyfile = new File(ext.getPropValue("legacy-customer-list"));
		File legacyDeltafile = new File(ext.getPropValue("delta-legacy-customers"));
		String startDateString=ext.getPropValue("start_date_for_profile");
		String endDateString=ext.getPropValue("end_date_for_profile");
		exportPrefix=ext.getPropValue("toro_customer_list_export_prefix");
		batchSize=Integer.valueOf(ext.getPropValue("batchSize_customerProfile"));
		String delta_Load =ext.getPropValue("delta_Load");
		if("false".equals(delta_Load)){
			isFullLoad = true;
		}
		
		if(!startDateString.isBlank()) {
			c_startDate.setTime(df.parse(startDateString));
			startDate= DatatypeFactory.newInstance().newXMLGregorianCalendar(c_startDate);
		}
		if(!endDateString.isBlank()) {
			c_endDate.setTime(df.parse(endDateString));
			endDate= DatatypeFactory.newInstance().newXMLGregorianCalendar(c_endDate);
		}
		//File torofile = new File(ext.getPropValue("toro-customer-list"));
		
		List<ComplexTypeCustomer> customerList = new ArrayList<ComplexTypeCustomer>();
		if(isFullLoad) {
			CustomerList que = new CustomerList();
			JAXBContext jaxbContext = JAXBContext.newInstance(CustomerList.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			que = (CustomerList) jaxbUnmarshaller.unmarshal(legacyfile);
			//updatedHEader = que.getHeader();
			updatedListId = que.getListId();
			customerList = que.getCustomer();
			que = null;
			}else {
				Customers que = new Customers();
			JAXBContext jaxbContext = JAXBContext.newInstance(Customers.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			que = (Customers) jaxbUnmarshaller.unmarshal(legacyDeltafile);
			customerList = que.getCustomer();
		}

		
		
		for(ComplexTypeCustomer vComplexTypeCustomer : customerList) {
			ComplexTypeProfile profiles = vComplexTypeCustomer.getProfile();
			 if(profiles == null ) {
				 continue;
			 }
			 if(profiles.getLastVisitTime() != null) {
				 compareCar = profiles.getLastVisitTime();
			 }else if(profiles.getLastLoginTime() != null) {
				 compareCar = profiles.getLastLoginTime(); 
			 }else if(profiles.getCreationDate()!= null) {
				 compareCar = profiles.getCreationDate(); 
			 }
			 if(compareCar == null) { continue; }else {
				 if(compareCar.compare(startDate) >= 0 && compareCar.compare(endDate) <= 0 ) {
					 updatedCustomer.add(vComplexTypeCustomer);
				 }
			 }
			 
		}
		
		
		int counter = 0;
		CustomerList tempque = new CustomerList();
		 List<ComplexTypeCustomer> tempUpdatedCustomer =new ArrayList<ComplexTypeCustomer>();
		for(ComplexTypeCustomer tempComplexTypeCustomer : updatedCustomer) {
			tempUpdatedCustomer.add(tempComplexTypeCustomer);	
			counter ++;
			if(counter%batchSize == 0) {
				tempque = new CustomerList();
				//tempque.setHeader(updatedHEader);	
				tempque.setListId(updatedListId);	
				tempque.getCustomer().addAll(tempUpdatedCustomer);				
				createBatchFile(tempque);				
				tempUpdatedCustomer =new ArrayList<ComplexTypeCustomer>();
				}else if(counter == updatedCustomer.size()){
					tempque = new CustomerList();
					//tempque.setHeader(updatedHEader);	
					tempque.setListId(updatedListId);	
					tempque.getCustomer().addAll(tempUpdatedCustomer);					
					createBatchFile(tempque);					
					tempUpdatedCustomer =new ArrayList<ComplexTypeCustomer>();
			}
			
		}
		
		System.out.println(updatedCustomer.size());
		System.out.println(updatedCustomer.size()/batchSize);
	
	

	}
	private static void createBatchFile(CustomerList tempque) throws JAXBException, FileNotFoundException {
		JAXBContext jaxbContext = JAXBContext.newInstance(CustomerList.class);
		 Marshaller marshallerObj = jaxbContext.createMarshaller();  
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
		long currentTimestamp = System.currentTimeMillis();
		marshallerObj.marshal(tempque, new FileOutputStream(exportPrefix+"_"+currentTimestamp+".xml")); 
	
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
