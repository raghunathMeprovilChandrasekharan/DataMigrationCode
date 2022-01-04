package com.datamigration.mainclass;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.datamigration.javaclass.Catalog;
import com.datamigration.javaclass.ComplexTypeCategory;
import com.datamigration.javaclass.ComplexTypeCategoryAssignment;
import com.datamigration.javaclass.ComplexTypeProduct;
import com.datamigration.javaclass.ComplexTypeProductClassificationCategory;
import com.datamigration.javaclass.SharedTypeLocalizedString;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttribute;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttributes;  
   
public class XmlToObject {

	public static HashMap<String,String> SurpriseMap = new HashMap<String,String>();
	public static HashMap<String,String> KateMap = new HashMap<String,String>();
	public static HashMap<String,String> productVariationMap = new HashMap<String,String>();
	public static HashMap<String,String> skuNumberMap = new HashMap<String,String>();
	
	public static void main(String[] args) {
		 try {  
			   
			 
			 File surpriseCategoryFile = new File("SurpriseCategoryDB.csv");  
			 File  katespadeCategoryFile = new File("KatespadeCategoryDB.csv");  
			 loadCategoryData(surpriseCategoryFile,SurpriseMap);
			 loadCategoryData(katespadeCategoryFile,KateMap);

			 
			 
			 String[] paramasToTransfer = args[0].split(",");
			 boolean isCategory = false;
			 boolean isCatgoryRelation = false;
			for(String arg : paramasToTransfer) {
				if("category".equalsIgnoreCase(arg)) {
					isCategory = true;
				}
				if("categoryrelation".equalsIgnoreCase(arg)) {
					isCatgoryRelation = true;
				}
				
			} 

		        File file = new File("D:\\dataMigration\\ks_catalog\\master_site_catalog.xml");  
				
		        JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);  
		   
		        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
		        Catalog que= (Catalog) jaxbUnmarshaller.unmarshal(file);  
		        System.out.println(":::::::::::::::::Conversion Completed::::::::::::::");  
		        formIdtoSkuMapping(que);
		        //formCategoryRelationToProduct("Surprise",SurpriseMap,que);
		       // formCategoryRelationToProduct("Katespade",KateMap,que);
		        /**
		        if(isCategory) {
		        	extractCategory(que);
		        }
		        if(isCatgoryRelation) {
		        	extractCategoryAssignment(que);
		        }**/
		        System.out.println(":::::::::::::::::Process Completed::::::::::::::"); 
		        
		      } catch (JAXBException e) {  
		        e.printStackTrace();  
		      } catch (Exception e) {  
			        e.printStackTrace();  
			      }  
	}

	private static void formCategoryRelationToProduct(String type, HashMap<String, String> categoryMap, Catalog que) throws IOException {
		FileWriter myWriter = new FileWriter(type+"_categoryAssignment_"+System.currentTimeMillis()+".csv");
		StringBuilder sb = new StringBuilder();
		for ( ComplexTypeProduct product : que.getProduct()) {
			ComplexTypeProductClassificationCategory clasificationCatgeoy = product.getClassificationCategory();
			if(clasificationCatgeoy == null) {
				continue;
			}
			String CatalogId = clasificationCatgeoy.getCatalogId();
			if(product.getVariations() != null  && product.getVariations().getVariants().size() > 0  && CatalogId != null && (("Surprise".equalsIgnoreCase(type) && CatalogId.equalsIgnoreCase("katesale-site-catalog")) ||(("Katespade".equalsIgnoreCase(type) && CatalogId.equalsIgnoreCase("kateandsaturday-site-catalog"))) )) {
				sb.append("\"").append(skuNumberMap.get(product.getProductId())).append("\",\"").append(categoryMap.get(clasificationCatgeoy.getValue())).append("\",\"").append(clasificationCatgeoy.getValue()).append("\"\n");
			}
		}
		
		  myWriter.write(sb.toString());
	        myWriter.close();
		
	}

	private static void loadCategoryData(File surpriseCategoryFile, HashMap<String, String> kateMap2) throws FileNotFoundException {
		 Scanner myReader = new Scanner(surpriseCategoryFile);
	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        String categoryList[] = data.split(",")   ;
	        kateMap2.put(categoryList[0].replaceAll("\"", ""), categoryList[1].replaceAll("\"", ""));
	      }
	      myReader.close();
		
	}

	private static void formIdtoSkuMapping(Catalog que) {
		for ( ComplexTypeProduct product : que.getProduct()) {
			String style="";
			String color="";
			String size = "";
			String dimension ="";
			SharedTypeSiteSpecificCustomAttributes customAttribute = product.getCustomAttributes();
			if(customAttribute !=null) {
				for (SharedTypeSiteSpecificCustomAttribute customAttributeVal :customAttribute.getCustomAttribute()) {
				//	System.out.println(customAttributeVal.getAttributeId() + ":::::"+customAttributeVal.getContent().get(0).toString());
					if("dimension".equals(customAttributeVal.getAttributeId())) {
						dimension = customAttributeVal.getContent().get(0).toString();
						if("*N".equalsIgnoreCase(dimension)){
							dimension = "ND";
						}
						dimension = resetToDefaultLength(dimension,2);
						
					}
					if("style".equals(customAttributeVal.getAttributeId())) {
						style = customAttributeVal.getContent().get(0).toString();
						style = resetToDefaultLength(style,9);
					}
					if("color".equals(customAttributeVal.getAttributeId())) {
						color = customAttributeVal.getContent().get(0).toString();
						color = resetToDefaultLength(color,3);
					}
					if("size".equals(customAttributeVal.getAttributeId())) {
						size = customAttributeVal.getContent().get(0).toString();
						size = resetToDefaultLength(size,5);
					}
				}
			}
			//System.out.println("end");
				if(product.getVariations() == null && style.trim() != "") {
					String displayName=product.getDisplayName().size() > 0?product.getDisplayName().get(0).getValue():"";			
							productVariationMap.put(product.getProductId(), style.trim()+"_"+color.trim());
							skuNumberMap.put(product.getProductId(), (style+color+size+dimension).trim());
				}else{
					productVariationMap.put(product.getProductId(), "");
					skuNumberMap.put(product.getProductId(), product.getProductId());
				}
			}
		
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

	private static void extractCategoryAssignment(Catalog que) throws IOException {
		FileWriter myWriter = new FileWriter("categoryAssignment_"+System.currentTimeMillis()+".csv");
		StringBuilder sb = new StringBuilder();
		 sb.append("CategoryId,productId\n");
		 
		 for( ComplexTypeCategoryAssignment cataegory : que.getCategoryAssignment()) {
			 sb.append(cataegory.getCategoryId()).append(",").append(cataegory.getProductId()).append("\n");

		 }
		  myWriter.write(sb.toString());
	        myWriter.close();
		
	}

	private static void extractCategory(Catalog que) throws IOException {
		FileWriter myWriter = new FileWriter("category_"+System.currentTimeMillis()+".csv");
		StringBuilder sb = new StringBuilder();
        sb.append("\"CategoryId\",\"Name\",\"parent\",\"OnlineFlag\"\n");
          
        for( ComplexTypeCategory cataegory : que.getCategory()) {
        	String disaplayName = "";
        	for(SharedTypeLocalizedString lang : cataegory.getDisplayName()){
        		if(lang.getLang().equals("x-default")) {
        			disaplayName = lang.getValue();
        		}
        	}
        	sb.append("\"").append(cataegory.getCategoryId()).append("\"").append(",").append("\"").append(disaplayName).append("\"").append(",").append("\"").append(cataegory.getParent()).append("\"").append(",").append("\"").append(cataegory.isOnlineFlag()).append("\"").append("\n");
        	
        } 
        myWriter.write(sb.toString());
        myWriter.close();
		
	}

}
