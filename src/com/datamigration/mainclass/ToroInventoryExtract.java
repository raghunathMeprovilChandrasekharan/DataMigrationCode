package com.datamigration.mainclass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.datamigration.pojo.ProductData;
import com.demandware.xml.impex.inventory._2007_05_31.ComplexTypeInventoryList;
import com.demandware.xml.impex.inventory._2007_05_31.ComplexTypeInventoryRecord;
import com.demandware.xml.impex.inventory._2007_05_31.ComplexTypeInventoryRecords;
import com.demandware.xml.impex.inventory._2007_05_31.Inventory;

public class ToroInventoryExtract {
	static ExtractMainProperties ext = new ExtractMainProperties();
	static BigDecimal zeroVal = new BigDecimal("0");
	static HashMap<String, ProductData> prouct_DB = new HashMap<String, ProductData>();
	public static void main(String[] args) throws IOException, JAXBException {
		ExtractMasterData extMaster = new ExtractMasterData();
		extMaster.executeLoad();
		PopulateMainDB mainDB = new PopulateMainDB();
		mainDB.populateDB();
		prouct_DB = mainDB.getProuct_DB();
		
		
		
		List<ComplexTypeInventoryList> invnetoryLuist_KSNA = new ArrayList<ComplexTypeInventoryList>();
		List<ComplexTypeInventoryList> invnetoryLuist_KSS = new ArrayList<ComplexTypeInventoryList>();
		
		File inventory_file_KSNA = new File(ext.getPropValue("KS_inventory_file"));
		File inventory_file_KSS = new File(ext.getPropValue("KSS_inventory_file"));
		
		
		FileWriter ksna_myWriter = new FileWriter(ext.getPropValue("KS_inventory_file_extract"), false);
		FileWriter kss_myWriter = new FileWriter(ext.getPropValue("KSS_inventory_file_extract"), false);
		
		StringBuilder ksna = new StringBuilder();
		StringBuilder kss = new StringBuilder();
		
		Inventory que_KSNA = new Inventory();
		Inventory que_KSS = new Inventory();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Inventory.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		que_KSNA = (Inventory) jaxbUnmarshaller.unmarshal(inventory_file_KSNA);
		que_KSS = (Inventory) jaxbUnmarshaller.unmarshal(inventory_file_KSS);
		
		
		
		invnetoryLuist_KSNA = que_KSNA.getInventoryList()	;		
		invnetoryLuist_KSS= que_KSS.getInventoryList()	;
		
		ksna.append("\"Sku ID\",\"Inventory\"\n");
		kss.append("\"Sku ID\",\"Inventory\"\n");
		
		for (ComplexTypeInventoryList vComplexTypeInventoryList : invnetoryLuist_KSNA) {
			ComplexTypeInventoryRecords vComplexTypeInventoryRecords = vComplexTypeInventoryList.getRecords();			
			List<ComplexTypeInventoryRecord> vComplexTypeInventoryRecord = vComplexTypeInventoryRecords.getRecord();		
			for(ComplexTypeInventoryRecord pComplexTypeInventoryRecord : vComplexTypeInventoryRecord) {
				 
				if(prouct_DB.containsKey(pComplexTypeInventoryRecord.getProductId()) &&  !prouct_DB.get(pComplexTypeInventoryRecord.getProductId()).isMaster() &&   pComplexTypeInventoryRecord.getAllocation() != null && pComplexTypeInventoryRecord.getAllocation().compareTo(zeroVal) == 1) {
					
					ksna.append("\"").append(prouct_DB.get(pComplexTypeInventoryRecord.getProductId()).getSkuNumber()).append("\",\"").append(pComplexTypeInventoryRecord.getAllocation()).append("\"\n");
					
				}
				
				}
			
			
		}
		System.out.println(":::::::::::::::::::::::::::::::::::::::");
		
		for (ComplexTypeInventoryList vComplexTypeInventoryList : invnetoryLuist_KSS) {
			ComplexTypeInventoryRecords vComplexTypeInventoryRecords = vComplexTypeInventoryList.getRecords();			
			List<ComplexTypeInventoryRecord> vComplexTypeInventoryRecord = vComplexTypeInventoryRecords.getRecord();		
			for(ComplexTypeInventoryRecord pComplexTypeInventoryRecord : vComplexTypeInventoryRecord) {
				if(prouct_DB.containsKey(pComplexTypeInventoryRecord.getProductId()) &&  !prouct_DB.get(pComplexTypeInventoryRecord.getProductId()).isMaster() && pComplexTypeInventoryRecord.getAllocation() != null && pComplexTypeInventoryRecord.getAllocation().compareTo(zeroVal) == 1) {
					kss.append("\"").append(prouct_DB.get(pComplexTypeInventoryRecord.getProductId()).getSkuNumber()).append("\",\"").append(pComplexTypeInventoryRecord.getAllocation()).append("\"\n");
				}
				
				}
			
			
		}
		ksna_myWriter.write(ksna.toString());
		ksna_myWriter.close();
		kss_myWriter.write(kss.toString());
		kss_myWriter.close();	
	}

}
