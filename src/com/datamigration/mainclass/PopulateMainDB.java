package com.datamigration.mainclass;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import com.datamigration.pojo.CategoryData;
import com.datamigration.pojo.ProductData;

public class PopulateMainDB {
	static ExtractMainProperties ext = new ExtractMainProperties();
	private HashMap<String,CategoryData> surprise_category_DB = new HashMap<String,CategoryData>();
	private HashMap<String,CategoryData> katespade_category_DB = new HashMap<String,CategoryData>();
	private HashMap<String,ProductData> prouct_DB = new HashMap<String,ProductData>();
	//extractProductDetails();
	public HashMap<String, CategoryData> getSurprise_category_DB() {
		return surprise_category_DB;
	}
	public void setSurprise_category_DB(HashMap<String, CategoryData> surprise_category_DB) {
		this.surprise_category_DB = surprise_category_DB;
	}
	public HashMap<String, CategoryData> getKatespade_category_DB() {
		return katespade_category_DB;
	}
	public void setKatespade_category_DB(HashMap<String, CategoryData> katespade_category_DB) {
		this.katespade_category_DB = katespade_category_DB;
	}
	public HashMap<String, ProductData> getProuct_DB() {
		return prouct_DB;
	}
	public void setProuct_DB(HashMap<String, ProductData> prouct_DB) {
		this.prouct_DB = prouct_DB;
	}
	
	public void populateDB() throws IOException {
		File surpriseCategoryDB = new File(ext.getPropValue("surprise_category_fileName"));
		File katespadeCategoryDB = new File(ext.getPropValue("katespade_category_fileName"));
		File produyctDetailsDB = new File(ext.getPropValue("product_mapping_fileName"));
		 Scanner surpriseCategoryDBReader = new Scanner(surpriseCategoryDB);
	      while (surpriseCategoryDBReader.hasNextLine()) {
	        String surprise_data = surpriseCategoryDBReader.nextLine();
	        String surprseCategoryList[] = surprise_data.split(",")   ;
	        if(surprseCategoryList.length > 1 && surprseCategoryList[0].replaceAll("\"", "").trim() != "") {
	        	CategoryData tmpData = new CategoryData();
	        	tmpData.setCategoryId(surprseCategoryList[0].replaceAll("\"", "").trim());
	        	tmpData.setDisplayName(surprseCategoryList[1].replaceAll("\"", "").trim());        
	        	tmpData.setOnline("true".equalsIgnoreCase(surprseCategoryList[3].replaceAll("\"", "").trim()) ? true : false ); 
	        	tmpData.setParentCategory(surprseCategoryList[2].replaceAll("\"", "").trim()); 
	        	surprise_category_DB.put(surprseCategoryList[0].replaceAll("\"", "").trim(), tmpData);
	        }
      }
	      surpriseCategoryDBReader.close();
	      
	      Scanner katespadeCategoryDBReader = new Scanner(katespadeCategoryDB);
	      while (katespadeCategoryDBReader.hasNextLine()) {
	        String katespadedata = katespadeCategoryDBReader.nextLine();
	        String katespadeCategoryList[] = katespadedata.split(",")   ;
	        if(katespadeCategoryList.length > 1 && katespadeCategoryList[0].replaceAll("\"", "").trim() != "") {
	        	CategoryData tmpData = new CategoryData();
	        	tmpData.setCategoryId(katespadeCategoryList[0].replaceAll("\"", "").trim());
	        	tmpData.setDisplayName(katespadeCategoryList[1].replaceAll("\"", "").trim());        
	        	tmpData.setOnline("true".equalsIgnoreCase(katespadeCategoryList[3].replaceAll("\"", "").trim()) ? true : false ); 
	        	tmpData.setParentCategory(katespadeCategoryList[2].replaceAll("\"", "").trim()); 
	        	katespade_category_DB.put(katespadeCategoryList[0].replaceAll("\"", "").trim(), tmpData);
	        }
      }
	      katespadeCategoryDBReader.close();
	      
	      Scanner productDBDetails = new Scanner(produyctDetailsDB);
	      while (productDBDetails.hasNextLine()) {
	        String productdata = productDBDetails.nextLine();
	        String productList[] = productdata.split(",")   ;
	        if(productList.length > 1 && productList[0].replaceAll("\"", "").trim() != "") {
	        	ProductData tmpData = new ProductData();
	        	tmpData.setProductId(productList[0].replaceAll("\"", "").trim());
	        	tmpData.setSkuNumber(productList[1].replaceAll("\"", "").trim());
	        	tmpData.setMaster("true".equalsIgnoreCase(productList[2].replaceAll("\"", "").trim()) ? true : false);
	        	tmpData.setVariationId(productList[3].replaceAll("\"", "").trim());
	        	tmpData.setMasterId(productList[4].replaceAll("\"", "").trim());
	        	tmpData.setBrand(productList[5].replaceAll("\"", "").trim());      
	        	
	        	tmpData.setClassDescription(productList[6].replaceAll("\"", "").trim());  
	        	tmpData.setClassId(productList[7].replaceAll("\"", "").trim());  
	        	tmpData.setDepartmentDescription(productList[8].replaceAll("\"", "").trim());  
	        	tmpData.setDepartmentId(productList[9].replaceAll("\"", "").trim());  
	        	tmpData.setGroupDescription(productList[10].replaceAll("\"", "").trim());  
	        	tmpData.setGroupId(productList[11].replaceAll("\"", "").trim());  
	        	tmpData.setCustomAttribute1(productList[12].replaceAll("\"", "").trim());  
	        	tmpData.setCustomAttribute2(productList[13].replaceAll("\"", "").trim());  
	        					
	        	prouct_DB.put(productList[0].replaceAll("\"", "").trim(), tmpData);
	        }
      }
	      productDBDetails.close();
	      setSurprise_category_DB(surprise_category_DB);
	      setKatespade_category_DB(katespade_category_DB);
	      setProuct_DB(prouct_DB);
	      
	      
	}
	

}
