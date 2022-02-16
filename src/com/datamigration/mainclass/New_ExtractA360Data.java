package com.datamigration.mainclass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.datamigration.pojo.A360DB;

public class New_ExtractA360Data {
	static ExtractMainProperties ext = new ExtractMainProperties();
	private static HashMap<String, A360DB> A360DB = new HashMap<String, A360DB>();
	private static HashMap<String,Set<String>> masterVariationDB = new HashMap<String, Set<String>>();
	private static HashMap<String,Set<String>> masterproductsDB = new HashMap<String, Set<String>>();
	
	
	public static HashMap<String, Set<String>> getMasterVariationDB() {
		return masterVariationDB;
	}

	public static void setMasterVariationDB(HashMap<String, Set<String>> masterVariationDB) {
		New_ExtractA360Data.masterVariationDB = masterVariationDB;
	}

	public static void main(String[] args) throws IOException {
		extractA360DB();

	}

	public static HashMap<String, A360DB> getA360DB() {
		return A360DB;
	}
 
	public static void setA360DB(HashMap<String, A360DB> a360db) {
		A360DB = a360db;
	}

	public static void extractA360DB() throws IOException { 
		ExtractA360SkuDetails();
		ExtractA360MasterDetails();
		
		ExtractMasterVariationDB();
		ExtractMasterProductsDB();
		
	}
	public static HashMap<String, Set<String>> getMasterproductsDB() {
		return masterproductsDB;
	}

	public static void setMasterproductsDB(HashMap<String, Set<String>> masterproductsDB) {
		New_ExtractA360Data.masterproductsDB = masterproductsDB;
	}

	private static void ExtractMasterVariationDB() throws IOException {
		File fileDB = new File(ext.getPropValue("A360_DB"));
		Scanner myReader = new Scanner(fileDB);
		String styleId = "";
		String color = "";
		String skuId = "";
		String upcNo = "";
		while (myReader.hasNextLine()) {
			styleId = "";
			color = "";
			upcNo = "";
			skuId = "";
			String data = myReader.nextLine();
			String categoryList[] = data.split(",");
			if (categoryList.length > 3 && categoryList[0].replaceAll("\"", "").trim() != "") {
				color = categoryList[4];
				styleId = categoryList[0];
				if(masterVariationDB.containsKey(styleId)) {
					Set<String> variationList = masterVariationDB.get(styleId.trim());
					variationList.add(styleId.trim()+"-"+color.trim());
					masterVariationDB.put(styleId.trim(),variationList);
				}else {
					Set<String> variationList = new HashSet<String>();
					variationList.add(styleId.trim()+"-"+color.trim());
					masterVariationDB.put(styleId.trim(),variationList);
				}
			}
		}
	}
	
	private static void ExtractMasterProductsDB() throws IOException {
		File fileDB = new File(ext.getPropValue("A360_DB"));
		Scanner myReader = new Scanner(fileDB);
		String styleId = "";
		String color = "";
		String skuId = "";
		String upcNo = "";
		while (myReader.hasNextLine()) {
			styleId = "";
			color = "";
			upcNo = "";
			skuId = "";
			String data = myReader.nextLine();
			String categoryList[] = data.split(",");
			if (categoryList.length > 3 && categoryList[0].replaceAll("\"", "").trim() != "") {
				upcNo = categoryList[1];
				color = categoryList[4];
				styleId = categoryList[0];
				if(masterproductsDB.containsKey(styleId)) {
					Set<String> variationList = masterproductsDB.get(styleId.trim());
					variationList.add(upcNo);
					masterproductsDB.put(styleId.trim(),variationList);
				}else {
					Set<String> variationList = new HashSet<String>();
					variationList.add(upcNo);
					masterproductsDB.put(styleId.trim(),variationList);
				}
			}
		}
	}

	private static void ExtractA360SkuDetails() throws IOException {
		File fileDB = new File(ext.getPropValue("A360_DB"));
		Scanner myReader = new Scanner(fileDB);
		String styleId = "";
		String color = "";
		String skuId = "";
		String upcNo = "";

		while (myReader.hasNextLine()) {
			styleId = "";
			color = "";
			upcNo = "";
			skuId = "";
			String data = myReader.nextLine();
			String categoryList[] = data.split(",");
			if (categoryList.length > 1 && categoryList[0].replaceAll("\"", "").trim() != "") {
				if (categoryList.length > 3) {
					color = categoryList[4];
				}
				styleId = categoryList[0];
				upcNo = categoryList[1];
				skuId = categoryList[2];

				if (!A360DB.containsKey(upcNo)) {
					A360DB vA360DB = new A360DB();
					vA360DB.setStyleId(styleId);
					vA360DB.setSkuId(skuId);
					vA360DB.setColorCode(color);
					vA360DB.setUpcNo(upcNo);
					vA360DB.setVariationId(styleId.trim() + "-" + color.trim());
					vA360DB.setMaster(false);
					A360DB.put(upcNo, vA360DB);
				}
			}

		}
		myReader.close();

	}
	private static void ExtractA360MasterDetails() throws IOException {
		File fileDB = new File(ext.getPropValue("A360_DB"));
		// System.out.println(fileDB);
		Scanner myReader = new Scanner(fileDB);
		String styleId = "";
		String color = "";
		String upcNo = "";

		while (myReader.hasNextLine()) {
			styleId = "";
			color = "";
			upcNo = "";
			String data = myReader.nextLine();
			String categoryList[] = data.split(",");
			if (categoryList.length > 1 && categoryList[0].replaceAll("\"", "").trim() != "") {
				styleId = categoryList[0];
				upcNo = categoryList[1];
				if (categoryList.length > 3) {
					color = categoryList[4];
				}
				if (!A360DB.containsKey(styleId)) {
					List<String> upcList = new ArrayList<String> ();
					upcList.add(upcNo);
					A360DB vA360DB = new A360DB();
					vA360DB.setStyleId(styleId);
					vA360DB.setColorCode(color);
					vA360DB.setUpcNoList(upcList);
					vA360DB.setSkuId(styleId);
					vA360DB.setVariationId(styleId.trim() + "-" + color.trim());
					vA360DB.setMaster(true);
					A360DB.put(styleId, vA360DB);
				}else {
					A360DB vA360DB = A360DB.get(styleId);
					List<String> upcList = vA360DB.getUpcNoList();
					upcList.add(upcNo);
					Collections.sort(upcList);
					vA360DB.setUpcNoList(upcList);
					A360DB.put(styleId, vA360DB);
				}
				
			}

		}
		myReader.close();

	}
}
