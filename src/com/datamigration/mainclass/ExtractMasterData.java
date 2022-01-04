package com.datamigration.mainclass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.datamigration.javaclass.Catalog;
import com.datamigration.javaclass.ComplexTypeCategory;
import com.datamigration.javaclass.ComplexTypeProduct;
import com.datamigration.javaclass.ComplexTypeProductClassificationCategory;
import com.datamigration.javaclass.SharedTypeCustomAttribute;
import com.datamigration.javaclass.SharedTypeCustomAttributes;
import com.datamigration.javaclass.SharedTypeLocalizedString;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttribute;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttributes;

public class ExtractMasterData {
	static ExtractMainProperties ext = new ExtractMainProperties();
	private static HashMap<String,String> A360DB = new HashMap<String,String>();
	public static void main(String[] args) {
		try {
			System.out.println("--Starting--");
			System.out.println("--Extrating category Details for Surprise--");
			ExtractCategoryDetails("surprise");
			System.out.println("--Extration complerte for category Details for Surprise--");
			System.out.println("--Extrating category Details for Katespade--");
			ExtractCategoryDetails("katepsade");
			System.out.println("--Extration complerte for category Details for Katespade--");
			System.out.println("--Extrating A360  Details --");
			ExtractA360ProductDEtails();
			System.out.println("--Extration completed A360  Details --");
			System.out.println("--Extrating product Details --");
			ExtractProductDEtails();
			System.out.println("--Extration completed product Details --");
			System.out.println("--Ending--");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void ExtractA360ProductDEtails() throws IOException {
		File fileDB = new File(ext.getPropValue("A360_DB"));
		System.out.println(fileDB);
		 Scanner myReader = new Scanner(fileDB);
	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        String categoryList[] = data.split(",")   ;
	        if(categoryList.length > 1 && categoryList[0].replaceAll("\"", "").trim() != "") {
		        if(A360DB.containsKey(categoryList[1].replaceAll("\"", ""))) {
		        /**	System.out.println(categoryList[1].replaceAll("\"", "")+" Have multiple entries.....");
		        	System.out.println("................................................................Existing Entry for UPC No >"+ categoryList[1].replaceAll("\"", "")+" is \""+A360DB.get(categoryList[1].replaceAll("\"", ""))+"\"");
		        	System.out.println("................................................................New Entry for UPC No > "+categoryList[1].replaceAll("\"", "")+" is \""+categoryList[0].replaceAll("\"", "")+"\"");
		        	System.out.println("                        ");
		       **/ }else {
		        	 A360DB.put(categoryList[1].replaceAll("\"", ""),categoryList[0].replaceAll("\"", ""));
		        }
	        }

	       
	      }
	      myReader.close();
		
	}

	private static void ExtractProductDEtails() throws IOException, JAXBException {
		File file = new File(ext.getPropValue("master-catalog-path"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		FileWriter myWriter = new FileWriter(ext.getPropValue("product_mapping_fileName"), false);
		FileWriter no_style_product_mapping_fileName = new FileWriter(ext.getPropValue("no_style_product_mapping_fileName"), false);
		StringBuilder sb = new StringBuilder();
		StringBuilder sb_ns = new StringBuilder();
		sb.append("\"ProductId\",\"Item Id\",\"is Master\",\"Variation Id\",\"Master Id\",\"Brand\",\"Master Id\",\"Class Description\",\"Class Id\",\"Department Description\",\"Department Id\",\"Group Description\",\"Group Id\",\"Custom Attribute 1\",\"Custom Attribute 2\"\n");
		 sb_ns.append("\"ProductId\"\n");
		for ( ComplexTypeProduct product : que.getProduct()) {
			String style="";
			String color="";
			String size = "";
			String dimension ="";
			String classDescription  = "";
			String classId  = "";
			String departmentDescription  = "";
			String departmentId  = "";		
			String groupDescription  = "";
			String groupId  = "";
			String customAttribute1  = "";
			String customAttribute2  = "";
			SharedTypeSiteSpecificCustomAttributes customAttribute = product.getCustomAttributes();
			if(customAttribute !=null) {
				for (SharedTypeSiteSpecificCustomAttribute customAttributeVal :customAttribute.getCustomAttribute()) {
				//	System.out.println(customAttributeVal.getAttributeId() + ":::::"+customAttributeVal.getContent().get(0).toString());
					
					dimension = extractAttributeFromCustomAttribute("dimension",product);
					if("*N".equalsIgnoreCase(dimension)){
						dimension = "ND";
					}
					dimension = resetToDefaultLength(dimension,2);
					
					style = extractAttributeFromCustomAttribute("style",product);
					style = resetToDefaultLength(style,9);

					
					color = extractAttributeFromCustomAttribute("color",product);
					color = resetToDefaultLength(color,3);
					
					
					size = extractAttributeFromCustomAttribute("color",product);
					size = resetToDefaultLength(size,5);
					
					classDescription = extractAttributeFromCustomAttribute("class-description",product);
					classId = extractAttributeFromCustomAttribute("class-id",product);
					departmentDescription = extractAttributeFromCustomAttribute("department-description",product);
					departmentId = extractAttributeFromCustomAttribute("department-id",product);					
					groupDescription = extractAttributeFromCustomAttribute("group-description",product);			
					groupId = extractAttributeFromCustomAttribute("group-id",product);		
					customAttribute1 = extractAttributeFromCustomAttribute("custom-attribute1",product);		
					customAttribute2 = extractAttributeFromCustomAttribute("custom-attribute2",product);	
				}
			}
			String brandType = "";
			ComplexTypeProductClassificationCategory catoryTyep = product.getClassificationCategory();
			if(catoryTyep != null) {
				if(catoryTyep.getCatalogId() != null) {
					if("kateandsaturday-site-catalog".equals(catoryTyep.getCatalogId())) {
						brandType = "KATESPADE";
					}else if("katesale-site-catalog".equals(catoryTyep.getCatalogId())) {
						brandType = "SURPRISE";
					}			
							
				}				
			}
			
			//System.out.println("end");
				if(product.getVariations() == null) {	
					if(style.trim() == "") {
						sb_ns.append("\"").append(product.getProductId()).append("\"\n");						
					}else {
						if(A360DB.containsKey(product.getProductId())) {
							sb.append("\"").append(product.getProductId()).append("\",\"").append(A360DB.get(product.getProductId())).append("\",\"").append("false").append("\",\"").append(style.trim()+"-"+color.trim()).append("\",\"").append(style).append("\",\"").append(brandType)
							.append("\",\"").append(classDescription)
							.append("\",\"").append(classId)
							.append("\",\"").append(departmentDescription)
							.append("\",\"").append(departmentId)
							.append("\",\"").append(groupDescription)
							.append("\",\"").append(groupId)
							.append("\",\"").append(customAttribute1)
							.append("\",\"").append(customAttribute2)
							.append("\"\n");
						}else {
							sb_ns.append("\"").append(product.getProductId()).append("\"\n");	
						}
						
					}
					
					
				}else{
					sb.append("\"").append(product.getProductId()).append("\",\"").append(product.getProductId()).append("\",\"").append("true").append("\",\"\",\"\",\"").append(brandType)
					.append("\",\"").append(classDescription)
					.append("\",\"").append(classId)
					.append("\",\"").append(departmentDescription)
					.append("\",\"").append(departmentId)
					.append("\",\"").append(groupDescription)
					.append("\",\"").append(groupId)
					.append("\",\"").append(customAttribute1)
					.append("\",\"").append(customAttribute2)
					.append("\"\n");
				}
		}
		myWriter.write(sb.toString());
		myWriter.close();
		no_style_product_mapping_fileName.write(sb_ns.toString());
		no_style_product_mapping_fileName.close();
	}
	
	private static String extractAttributeFromCustomAttribute(String pCustomAttributeToBeFetched, ComplexTypeProduct product) {
		String result = "";
		SharedTypeSiteSpecificCustomAttributes customAttribute = product.getCustomAttributes();
		if(customAttribute == null) {
			return "";
		}
		List<SharedTypeSiteSpecificCustomAttribute> customAttributes = customAttribute.getCustomAttribute();
		if(customAttributes == null) {
			return "";
		}
		for(SharedTypeSiteSpecificCustomAttribute pSharedTypeSiteSpecificCustomAttribute : customAttributes) {
			if(pCustomAttributeToBeFetched.equalsIgnoreCase(pSharedTypeSiteSpecificCustomAttribute.getAttributeId()	)){
				result = pSharedTypeSiteSpecificCustomAttribute.getContent().get(0).toString();
			}
					
		}
	//	System.out.println(pCustomAttributeToBeFetched+" > "+result);
		return result;
	}
	
	private static Object extractVariationId(String skuId) {
		String styleId= skuId.substring(0, 9);	
		String colour= skuId.substring(9, 12);	
		return styleId.trim()+"_"+colour.trim();
	}

	private static String resetToDefaultLength(String inutAttr, int MinLength) {
		int noOfSpace = 0;
		if(inutAttr.length() < MinLength) {
			noOfSpace = MinLength - inutAttr.length();
		}
		String converTedAttr = inutAttr;
		for(int i = 0 ;i < noOfSpace; i++) {
			converTedAttr =  converTedAttr+" ";
		}
		return converTedAttr;
	}
	private static void ExtractCategoryDetails(String subBrands) throws JAXBException, IOException {
		List <String> customAttriubutesttributes = new ArrayList<String>();
		if ("surprise".equalsIgnoreCase(subBrands)) {
			File file = new File(ext.getPropValue("surprise-sales-catalog-path"));
			JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
			FileWriter myWriter = new FileWriter(ext.getPropValue("surprise_category_fileName"), false);
			StringBuilder sb = new StringBuilder();
			sb.append("\"CategoryId\",\"Name\",\"parent\",\"OnlineFlag\"\n");
			for (ComplexTypeCategory cataegory : que.getCategory()) {
				/*
				 * SharedTypeCustomAttributes customAttribute = cataegory.getCustomAttributes();
				 * if(customAttribute != null ) { for(SharedTypeCustomAttribute custom :
				 * customAttribute.getCustomAttribute()) {
				 * if(!customAttriubutesttributes.contains(custom.getAttributeId())) {
				 * customAttriubutesttributes.add(custom.getAttributeId());
				 * 
				 * } } }
				 */
				
				String disaplayName = "";
				for (SharedTypeLocalizedString lang : cataegory.getDisplayName()) {
					if (lang.getLang().equals("x-default")) {
						disaplayName = lang.getValue();
					}
				}
				sb.append("\"").append(cataegory.getCategoryId()).append("\",\"").append(disaplayName).append("\",\"")
						.append(cataegory.getParent()).append("\",\"").append(cataegory.isOnlineFlag()).append("\"\n");
			}
			myWriter.write(sb.toString());
			myWriter.close();
		} else {
			File file = new File(ext.getPropValue("katespade-sales-catalog-path"));
			JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
			FileWriter myWriter = new FileWriter(ext.getPropValue("katespade_category_fileName"), false);
			StringBuilder sb = new StringBuilder();
			sb.append("\"CategoryId\",\"Name\",\"parent\",\"OnlineFlag\"\n");
			for (ComplexTypeCategory cataegory : que.getCategory()) {
				/*
				 * SharedTypeCustomAttributes customAttribute = cataegory.getCustomAttributes();
				 * if(customAttribute != null ) { for(SharedTypeCustomAttribute custom :
				 * customAttribute.getCustomAttribute()) {
				 * if(!customAttriubutesttributes.contains(custom.getAttributeId())) {
				 * customAttriubutesttributes.add(custom.getAttributeId());
				 * 
				 * } } }
				 */
				String disaplayName = "";
				for (SharedTypeLocalizedString lang : cataegory.getDisplayName()) {
					if (lang.getLang().equals("x-default")) {
						disaplayName = lang.getValue();
					}
				}
				sb.append("\"").append(cataegory.getCategoryId()).append("\",\"").append(disaplayName).append("\",\"")
						.append(cataegory.getParent()).append("\",\"").append(cataegory.isOnlineFlag()).append("\"\n");

			}
			myWriter.write(sb.toString());
			myWriter.close();
		}
System.out.println(customAttriubutesttributes);
	}

}
