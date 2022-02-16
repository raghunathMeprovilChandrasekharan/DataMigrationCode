package com.datamigration.mainclass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.json.simple.JSONObject;

import com.datamigration.javaclass.Catalog;
import com.datamigration.javaclass.ComplexTypeCategory;
import com.datamigration.javaclass.ComplexTypeCategoryAssignment;
import com.datamigration.pojo.A360DB;
import com.datamigration.pojo.CategoryProductRelation;

public class ExtractOldNewIds {
	static ExtractMainProperties ext = new ExtractMainProperties();
	private static HashMap<String, A360DB> A360DB = new HashMap<String, A360DB>();
	JSONObject jsonObj = new JSONObject();
	
	public static void main(String[] args) throws IOException, JAXBException {
		HashMap<String,String> tempFIle = new HashMap<String,String>();
		HashMap<String,List<String>> tempMasterFIle = new HashMap<String,List<String>>();
		File fileDB = new File(ext.getPropValue("A360_DB"));
		
		// System.out.println(fileDB);
		Scanner myReader = new Scanner(fileDB);
		String skuId = "";
		String upcNo = "";
		String styleCode = "";
		FileWriter myBrandWriter = new FileWriter(ext.getPropValue("old_new_id"), false);
		while (myReader.hasNextLine()) {
			skuId = "";
			upcNo = "";
			String data = myReader.nextLine();
			String categoryList[] = data.split(",");
			if (categoryList.length > 2) {
				upcNo = categoryList[1];
				skuId = categoryList[2];
				styleCode = categoryList[0];
				if(upcNo.trim() != "" ){
					
					if(!tempMasterFIle.containsKey(styleCode)) {
						List<String> skus = new ArrayList<String>();
						skus.add(upcNo);
						tempMasterFIle.put(styleCode,skus);
					}else {
						tempMasterFIle.get(styleCode).add(upcNo);
					}
					
					
					if(!tempFIle.containsKey(upcNo)) {
						tempFIle.put(upcNo,skuId);
						
					}else {
						/*
						 * System.out.println("UPC>>>"+upcNo);
						 * System.out.println("OLD>>>"+tempFIle.get(upcNo));
						 * System.out.println("New>>>"+skuId);
						 * System.out.println(":::::::::::::::::::::"); tempFIle.put(upcNo,skuId);
						 */
					}
					
				}

			}

		}
		
		
		Set<String> skus = new HashSet<String>();
		List<String> category = new ArrayList<String>();
		
	//	System.out.println(tempMasterFIle);
		File file = new File(ext.getPropValue("katespade-sales-catalog-path"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog kateque = (Catalog) jaxbUnmarshaller.unmarshal(file);
		for(ComplexTypeCategory vComplexTypeCategory : kateque.getCategory()	) {
			if(vComplexTypeCategory.isOnlineFlag() && !"DEFAULT_CATEGORY".equals(vComplexTypeCategory.getCategoryId())) {
				category.add(vComplexTypeCategory.getCategoryId());
			}
		}
		
		CategoryProductRelation vCategoryProductRelation;
		String vProductId = "";
		for (ComplexTypeCategoryAssignment vComplexTypeCategoryAssignment : kateque.getCategoryAssignment()) {
			vCategoryProductRelation = new CategoryProductRelation();
			vProductId = vComplexTypeCategoryAssignment.getProductId();
		if(category.contains(vComplexTypeCategoryAssignment.getCategoryId())){		
			
				if (vProductId.indexOf("-") > -1) {
					vProductId = vProductId.split("-")[0];
				}
				if(tempMasterFIle.containsKey(vProductId)) {
					skus.addAll(tempMasterFIle.get(vProductId)	);	
				}else if(tempFIle.containsKey(vProductId)) {
					skus.add(vProductId	);	
				}
			
		}

		}
		
		StringBuilder sb = new StringBuilder();
		int count = 0 ;
		sb.append("const old_new_pair = {");
		for(String sku : skus) {
			count++;
			sb.append("\"").append(sku).append("\":\"").append(tempFIle.get(sku)).append("\",");
			if(count == 3000) {
				break;
			}
			
		}
		
		sb.append("\"1\":\"1\"");
		sb.append("};");
		sb.append("export {old_new_pair};");
		myBrandWriter.write(sb.toString());
		myBrandWriter.close();
		myReader.close();
		
		

		
		

	}

}
