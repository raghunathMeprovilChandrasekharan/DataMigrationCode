package com.datamigration.mainclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.datamigration.javaclass.Catalog;
import com.datamigration.javaclass.ComplexTypeProduct;
import com.datamigration.javaclass.ComplexTypeProductVariations;
import com.datamigration.javaclass.ComplexTypeProductVariationsVariant;
import com.datamigration.javaclass.ComplexTypeProductVariationsVariants;
import com.datamigration.pojo.A360DB;
import com.datamigration.pojo.DefaultVariants;
import com.datamigration.pojo.DefaultVariantsToExcel;
import com.datamigration.pojo.PlumSliceVariationDetailsForFeed;

public class New_ExtractDefaultVariation {
	private static ExtractMainProperties ext = new ExtractMainProperties();
	private static HashMap<String,DefaultVariantsToExcel> ks_defaultVariantList = new HashMap<String,DefaultVariantsToExcel>();
	private static HashMap<String,DefaultVariantsToExcel> kss_defaultVariantList = new HashMap<String,DefaultVariantsToExcel>();
	private static HashMap<String,List<DefaultVariantsToExcel>> ks_defaultVariantsItemList = new HashMap<String,List<DefaultVariantsToExcel>>();
	private static HashMap<String,List<DefaultVariantsToExcel>> kss_defaultVariantsItemList = new HashMap<String,List<DefaultVariantsToExcel>>();
	private static HashMap<String, String> itemNameValulePair = new HashMap<String, String>();
	
	private static List<DefaultVariantsToExcel> finalList_ks = new ArrayList<DefaultVariantsToExcel>();
	private static List<DefaultVariantsToExcel> finalList_kss = new ArrayList<DefaultVariantsToExcel>();
	
	
	private static HashMap<String, A360DB> A360DB = new HashMap<String, A360DB>();
	
	public static void main(String[] args) throws Exception {
		New_ExtractA360Data A360DBextract = new New_ExtractA360Data();
		New_ExtractA360Data.extractA360DB();
		A360DB = A360DBextract.getA360DB();
		kss_extaractProductDetails();
		ks_extaractProductDetails();
		recombineback();
		writeExcel("KS");
		writeExcel("KSS");
		System.out.println("finish..........");
	}

	private static void writeExcel(String brand) throws NumberFormatException, IOException, ParseException {
		List<List<DefaultVariantsToExcel>> output ;
		if("KS".equals(brand)){
			output = ListUtils.partition(finalList_ks,
					Integer.valueOf(ext.getPropValue("no_of_splits")));
		}else {
			output = ListUtils.partition(finalList_kss,
					Integer.valueOf(ext.getPropValue("no_of_splits")));
		}
		int count = 0;
		for (List<DefaultVariantsToExcel> delta : output) {
			File masterSamplerFile = new File(ext.getPropValue("DEFAULT_VARIANT_templatecopy")
					.replaceAll("<<Iteration>>", brand+"-"+String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("DEFAULT_VARIANT_mastercopy")), masterSamplerFile);
			FileInputStream masterInputStream = new FileInputStream(masterSamplerFile);
			Workbook masterWorkbook = WorkbookFactory.create(masterInputStream);
			Sheet masterSheet = masterWorkbook.getSheetAt(0);
			int masterRowCount = masterSheet.getLastRowNum();
			for (DefaultVariantsToExcel masterDetails : delta) {
				Row masterRow = masterSheet.createRow(++masterRowCount);
				int columnCount = 0;
				Cell cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemId());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(itemNameValulePair.containsKey(masterDetails.getItemId())?itemNameValulePair.get(masterDetails.getItemId()):"");
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getVariationNumber());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				if("KS".equals(brand)){
					cell.setCellValue(masterDetails.isDefaultrVariant_kate() ? "Y" : "N");
				}else {
					cell.setCellValue("");
				}
				columnCount++;
				cell = masterRow.createCell(columnCount);
				if("KSS".equals(brand)){
					cell.setCellValue(masterDetails.isDefaultrVariant_surprise() ? "Y" : "N");
				}else {
					cell.setCellValue("");
				}
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getDefaultrVariantSeaquance());
			}
			masterInputStream.close();
			FileOutputStream outputStream = new FileOutputStream(masterSamplerFile);
			masterWorkbook.write(outputStream);
			masterWorkbook.close();
			outputStream.close();
			count++;
		}
	}

	private static void recombineback()throws Exception {
		for(String variationId : ks_defaultVariantList.keySet() ) {
			DefaultVariantsToExcel vDefaultVariantsToExcel = ks_defaultVariantList.get(variationId);
			List<DefaultVariantsToExcel> vvDefaultVariantsToExcelList = new ArrayList<DefaultVariantsToExcel>();
			if(ks_defaultVariantsItemList.containsKey(vDefaultVariantsToExcel.getItemId())){
				vvDefaultVariantsToExcelList = ks_defaultVariantsItemList.get(vDefaultVariantsToExcel.getItemId());
			}else {
				 vvDefaultVariantsToExcelList = new ArrayList<DefaultVariantsToExcel>();
			}
			vvDefaultVariantsToExcelList.add(vDefaultVariantsToExcel);
			ks_defaultVariantsItemList.put(vDefaultVariantsToExcel.getItemId(),vvDefaultVariantsToExcelList);
		}
		for(String variationId : kss_defaultVariantList.keySet() ) {
			DefaultVariantsToExcel vDefaultVariantsToExcel = kss_defaultVariantList.get(variationId);
			List<DefaultVariantsToExcel> vvDefaultVariantsToExcelList = new ArrayList<DefaultVariantsToExcel>();
			if(kss_defaultVariantsItemList.containsKey(vDefaultVariantsToExcel.getItemId())){
				vvDefaultVariantsToExcelList = kss_defaultVariantsItemList.get(vDefaultVariantsToExcel.getItemId());
			}else {
				 vvDefaultVariantsToExcelList = new ArrayList<DefaultVariantsToExcel>();
			}
			vvDefaultVariantsToExcelList.add(vDefaultVariantsToExcel);
			kss_defaultVariantsItemList.put(vDefaultVariantsToExcel.getItemId(),vvDefaultVariantsToExcelList);
		}
		
		for(String item: kss_defaultVariantsItemList.keySet() ) {
			List<DefaultVariantsToExcel> vvDefaultVariantsToExcelList = kss_defaultVariantsItemList.get(item);		
			vvDefaultVariantsToExcelList = reshuffleSequance_kss(vvDefaultVariantsToExcelList,false);
			finalList_kss.addAll(vvDefaultVariantsToExcelList);
			
		}
		for(String item: ks_defaultVariantsItemList.keySet() ) {
			List<DefaultVariantsToExcel> vvDefaultVariantsToExcelList = ks_defaultVariantsItemList.get(item);		
			vvDefaultVariantsToExcelList = reshuffleSequance_ks(vvDefaultVariantsToExcelList,false);
			finalList_ks.addAll(vvDefaultVariantsToExcelList);
			
		}
	}

	private static List<DefaultVariantsToExcel> reshuffleSequance_ks(
			List<DefaultVariantsToExcel> vvDefaultVariantsToExcelList, boolean restartFromOne) {
		List<DefaultVariantsToExcel> tempDefaultVariantsToExcelList = new ArrayList<DefaultVariantsToExcel>();
		int sequancestart = 2;
		if(restartFromOne) {
			sequancestart = 1;
		}
		boolean isMaterAlocated = false;
		boolean hasDefault = false;
		for(DefaultVariantsToExcel pDefaultVariantsToExcel : vvDefaultVariantsToExcelList) {
			if(!pDefaultVariantsToExcel.isDefaultrVariant_kate()) {
				pDefaultVariantsToExcel.setDefaultrVariantSeaquance(sequancestart);
				tempDefaultVariantsToExcelList.add(pDefaultVariantsToExcel);
				sequancestart++;
			}else {
				hasDefault = true;
				if(!isMaterAlocated) {
					tempDefaultVariantsToExcelList.add(pDefaultVariantsToExcel);
					isMaterAlocated = true;
				}else{
					pDefaultVariantsToExcel.setDefaultrVariantSeaquance(sequancestart);
					pDefaultVariantsToExcel.setDefaultrVariant_kate(false);
					tempDefaultVariantsToExcelList.add(pDefaultVariantsToExcel);
					sequancestart++;
				}
				
			}
		}
		
		if(!hasDefault && !restartFromOne) {
			tempDefaultVariantsToExcelList = new ArrayList<DefaultVariantsToExcel>();
			tempDefaultVariantsToExcelList = reshuffleSequance_ks(vvDefaultVariantsToExcelList,true);
		}
		
		return tempDefaultVariantsToExcelList;
	}


	private static List<DefaultVariantsToExcel> reshuffleSequance_kss(
			List<DefaultVariantsToExcel> vvDefaultVariantsToExcelList, boolean restartFromOne) {
		List<DefaultVariantsToExcel> tempDefaultVariantsToExcelList = new ArrayList<DefaultVariantsToExcel>();
		int sequancestart = 2;
		if(restartFromOne) {
			sequancestart = 1;
		}
		boolean isMaterAlocated = false;
		boolean hasDefault = false;
		for(DefaultVariantsToExcel pDefaultVariantsToExcel : vvDefaultVariantsToExcelList) {
			if(!pDefaultVariantsToExcel.isDefaultrVariant_surprise()) {
				pDefaultVariantsToExcel.setDefaultrVariantSeaquance(sequancestart);
				tempDefaultVariantsToExcelList.add(pDefaultVariantsToExcel);
				sequancestart++;
			}else {
				hasDefault = true;
				if(!isMaterAlocated) {
					tempDefaultVariantsToExcelList.add(pDefaultVariantsToExcel);
					isMaterAlocated = true;
				}else{
					pDefaultVariantsToExcel.setDefaultrVariantSeaquance(sequancestart);
					pDefaultVariantsToExcel.setDefaultrVariant_surprise(false);
					tempDefaultVariantsToExcelList.add(pDefaultVariantsToExcel);
					sequancestart++;
				}
				
			}
		}
		
		if(!hasDefault && !restartFromOne) {
			tempDefaultVariantsToExcelList = new ArrayList<DefaultVariantsToExcel>();
			tempDefaultVariantsToExcelList = reshuffleSequance_kss(vvDefaultVariantsToExcelList,true);
		}
		
		return tempDefaultVariantsToExcelList;
	}


	private static void kss_extaractProductDetails() throws Exception {
		File file = new File(ext.getPropValue("kss-master-catalog-path-item"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		String ItemName = "";
		for (ComplexTypeProduct product : que.getProduct()) {
			if(product.getVariations() != null) {
				if(product.getDisplayName().size() > 0) {
					ItemName = product.getDisplayName().get(0).getValue();
				}else {
					ItemName="";
				}
				String vItemID = product.getProductId();
				if(vItemID.contains("-")) {
					vItemID = vItemID.split("-")[0];
				}
				
				itemNameValulePair.put(vItemID, ItemName);
				
				ComplexTypeProductVariations variations = product.getVariations();
				if (variations != null && variations.getVariants() != null) {					
					for (ComplexTypeProductVariationsVariants variant : variations.getVariants()) {
						for (ComplexTypeProductVariationsVariant vComplexTypeProductVariationsVariant : variant
								.getVariant()) {
							if(vComplexTypeProductVariationsVariant.isDefault() != null && vComplexTypeProductVariationsVariant.isDefault()) {
									if(A360DB.containsKey(vComplexTypeProductVariationsVariant.getProductId())) {
										A360DB vA360DB = A360DB.get(vComplexTypeProductVariationsVariant.getProductId());
										DefaultVariantsToExcel vListDefaultVariantsToExcels;
										if(kss_defaultVariantList.containsKey(vA360DB.getVariationId())){
											vListDefaultVariantsToExcels = kss_defaultVariantList.get(vA360DB.getVariationId());
										}else {
											vListDefaultVariantsToExcels = new DefaultVariantsToExcel();
											vListDefaultVariantsToExcels.setName(ItemName);
											vListDefaultVariantsToExcels.setDefaultrVariant_surprise(true);	
											vListDefaultVariantsToExcels.setDefaultrVariantSeaquance(1);	
											vListDefaultVariantsToExcels.setItemId(vA360DB.getStyleId());
											vListDefaultVariantsToExcels.setVariationNumber(vA360DB.getVariationId());	
										}

										kss_defaultVariantList.put(vA360DB.getVariationId(),vListDefaultVariantsToExcels);
									}
										
							}else {
								if(A360DB.containsKey(vComplexTypeProductVariationsVariant.getProductId())) {
									A360DB vA360DB = A360DB.get(vComplexTypeProductVariationsVariant.getProductId());
									DefaultVariantsToExcel vListDefaultVariantsToExcels;
									if(kss_defaultVariantList.containsKey(vA360DB.getVariationId())){
										vListDefaultVariantsToExcels = kss_defaultVariantList.get(vA360DB.getVariationId());
									}else {
										vListDefaultVariantsToExcels = new DefaultVariantsToExcel();
										vListDefaultVariantsToExcels.setName(ItemName);
										vListDefaultVariantsToExcels.setDefaultrVariant_surprise(false);	
										vListDefaultVariantsToExcels.setDefaultrVariantSeaquance(2);	
										vListDefaultVariantsToExcels.setItemId(vA360DB.getStyleId());
										vListDefaultVariantsToExcels.setVariationNumber(vA360DB.getVariationId());		
									}
									kss_defaultVariantList.put(vA360DB.getVariationId(),vListDefaultVariantsToExcels);
								}
								
							}
							
						}
					}
				}
			}
		
	}
	}

	private static void ks_extaractProductDetails() throws Exception {
		File file = new File(ext.getPropValue("ks-master-catalog-path-item"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		String ItemName = "";
		for (ComplexTypeProduct product : que.getProduct()) {
			if(product.getVariations() != null) {
				if(product.getDisplayName().size() > 0) {
					ItemName = product.getDisplayName().get(0).getValue();
				}else {
					ItemName="";
				}
				
				String vItemID = product.getProductId();
				if(vItemID.contains("-")) {
					vItemID = vItemID.split("-")[0];
				}
				
				itemNameValulePair.put(vItemID, ItemName);
				
				
				ComplexTypeProductVariations variations = product.getVariations();
				if (variations != null && variations.getVariants() != null) {					
					for (ComplexTypeProductVariationsVariants variant : variations.getVariants()) {
						for (ComplexTypeProductVariationsVariant vComplexTypeProductVariationsVariant : variant
								.getVariant()) {
							if(vComplexTypeProductVariationsVariant.isDefault() != null && vComplexTypeProductVariationsVariant.isDefault()) {
									if(A360DB.containsKey(vComplexTypeProductVariationsVariant.getProductId())) {
										A360DB vA360DB = A360DB.get(vComplexTypeProductVariationsVariant.getProductId());
										DefaultVariantsToExcel vListDefaultVariantsToExcels;
										if(ks_defaultVariantList.containsKey(vA360DB.getVariationId())){
											vListDefaultVariantsToExcels = ks_defaultVariantList.get(vA360DB.getVariationId());
										}else {
											vListDefaultVariantsToExcels = new DefaultVariantsToExcel();
											vListDefaultVariantsToExcels.setName(ItemName);
											vListDefaultVariantsToExcels.setDefaultrVariant_kate(true);	
											vListDefaultVariantsToExcels.setDefaultrVariantSeaquance(1);	
											vListDefaultVariantsToExcels.setItemId(vA360DB.getStyleId());
											vListDefaultVariantsToExcels.setVariationNumber(vA360DB.getVariationId());	
										}

										ks_defaultVariantList.put(vA360DB.getVariationId(),vListDefaultVariantsToExcels);
									}
										
							}else {
								if(A360DB.containsKey(vComplexTypeProductVariationsVariant.getProductId())) {
									A360DB vA360DB = A360DB.get(vComplexTypeProductVariationsVariant.getProductId());
									DefaultVariantsToExcel vListDefaultVariantsToExcels;
									if(ks_defaultVariantList.containsKey(vA360DB.getVariationId())){
										vListDefaultVariantsToExcels = ks_defaultVariantList.get(vA360DB.getVariationId());
									}else {
										vListDefaultVariantsToExcels = new DefaultVariantsToExcel();
										vListDefaultVariantsToExcels.setName(ItemName);
										vListDefaultVariantsToExcels.setDefaultrVariant_kate(false);	
										vListDefaultVariantsToExcels.setDefaultrVariantSeaquance(2);	
										vListDefaultVariantsToExcels.setItemId(vA360DB.getStyleId());
										vListDefaultVariantsToExcels.setVariationNumber(vA360DB.getVariationId());	
									}
	
									ks_defaultVariantList.put(vA360DB.getVariationId(),vListDefaultVariantsToExcels);
								}
								
							}
							
						}
					}
				}
			}
		
	}
	}
	
	
	/**
	private static void FormulateTheData() {
		DefaultVariantsToExcel vDefaultVariantsToExcel ;
		for (String itemId : KSS_defaultProduct.keySet()) {
			DefaultVariants vDefaultVariants = KSS_defaultProduct.get(itemId);
			String upcID = getUPCIdForVariant(vDefaultVariants.getVariant());
			if(upcID.isBlank()){
				continue;
			}
			if(variantToDefalut.containsKey(upcID) ) {
				vDefaultVariantsToExcel = variantToDefalut.get(upcID);
			}else {
				vDefaultVariantsToExcel = new DefaultVariantsToExcel();
			}
			
			vDefaultVariantsToExcel.setItemId(itemId);	
			List<String> namesList = vPlumSliceItemDetailsForFeeds.get(itemId);	
			if(namesList != null ) {
				vDefaultVariantsToExcel.setName(namesList.get(0));		
			}else {
				vDefaultVariantsToExcel.setName("");		
			}
			vDefaultVariantsToExcel.setDefaultrVariant_surprise(getSkuIdForVariant(vDefaultVariants.getVariant()));
			vDefaultVariantsToExcel.setVariationNumber(getVariationIdForVariant(vDefaultVariants.getVariant()));
			vDefaultVariantsToExcel.setDefaultrVariantSeaquance(vDefaultVariants.getSequance());	
			variantToDefalut.put(upcID,vDefaultVariantsToExcel);
		}
		for (String itemId : KS_defaultProduct.keySet()) {
			DefaultVariants vDefaultVariants = KS_defaultProduct.get(itemId);
			String upcID = getUPCIdForVariant(vDefaultVariants.getVariant());
			if(upcID.isBlank()){
				continue;
			}
			if(variantToDefalut.containsKey(upcID) ) {
				vDefaultVariantsToExcel = variantToDefalut.get(upcID);
			}else {
				vDefaultVariantsToExcel = new DefaultVariantsToExcel();
			}
			
			vDefaultVariantsToExcel.setItemId(itemId);	
			List<String> namesList = vPlumSliceItemDetailsForFeeds.get(itemId);	
			if(namesList != null ) {
				vDefaultVariantsToExcel.setName(namesList.get(0));		
			}else {
				vDefaultVariantsToExcel.setName("");		
			}
			vDefaultVariantsToExcel.setDefaultrVariant_kate(getSkuIdForVariant(vDefaultVariants.getVariant()));
			vDefaultVariantsToExcel.setVariationNumber(getVariationIdForVariant(vDefaultVariants.getVariant()));
			vDefaultVariantsToExcel.setDefaultrVariantSeaquance(vDefaultVariants.getSequance());		
			variantToDefalut.put(upcID,vDefaultVariantsToExcel);
		}
		
		for(String uc : variantToDefalut.keySet()) {
			finalList.add(variantToDefalut.get(uc));
		}
		
	}


	private static void writeExcel() throws NumberFormatException, IOException, ParseException {
		List<List<DefaultVariantsToExcel>> output = ListUtils.partition(finalList,
				Integer.valueOf(ext.getPropValue("no_of_splits")));
		int count = 0;
		
		for (List<DefaultVariantsToExcel> delta : output) {
			File masterSamplerFile = new File(ext.getPropValue("DEFAULT_VARIANT_templatecopy")
					.replaceAll("<<Iteration>>", String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("DEFAULT_VARIANT_mastercopy")), masterSamplerFile);
			FileInputStream masterInputStream = new FileInputStream(masterSamplerFile);
			Workbook masterWorkbook = WorkbookFactory.create(masterInputStream);
			Sheet masterSheet = masterWorkbook.getSheetAt(0);
			int masterRowCount = masterSheet.getLastRowNum();
			for (DefaultVariantsToExcel masterDetails : delta) {
				List<String> namesList = vPlumSliceItemDetailsForFeeds.get(masterDetails.getItemId());		
				
				if(namesList == null) {
					System.out.println(namesList);
					continue;
				}
				Row masterRow = masterSheet.createRow(++masterRowCount);
				int columnCount = 0;
				Cell cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemId());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getName());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getVariationNumber());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getDefaultrVariant_kate());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getDefaultrVariant_surprise());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getDefaultrVariantSeaquance());
			}
			masterInputStream.close();
			FileOutputStream outputStream = new FileOutputStream(masterSamplerFile);
			masterWorkbook.write(outputStream);
			masterWorkbook.close();
			outputStream.close();
			count++;
		}
		
	}

	
	private static String getUPCIdForVariant(Set<String> variants) {
		for(String variant : variants) {
			if(A360DB.containsKey(variant)) {
				return variant;
			}
		}
		return "";
	}
	
	private static String getSkuIdForVariant(Set<String> variants) {
		for(String variant : variants) {
			if(A360DB.containsKey(variant)) {
				return A360DB.get(variant).getSkuId();
			}
		}
		return "";
	}

	
	private static String getVariationIdForVariant(Set<String> variants) {
		for(String variant : variants) {
			if(A360DB.containsKey(variant)) {
				return A360DB.get(variant).getVariationId();
			}
		}
		return "";
	}


	



	private static void kss_extaractProductDetails() throws Exception {
		File file = new File(ext.getPropValue("kss-master-catalog-path-item"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		List<String> vPlumSliceItemDetailsForFeed;
		for (ComplexTypeProduct product : que.getProduct()) {
			if(product.getVariations() != null) {
				String vItemID = product.getProductId();
				if(vItemID.contains("-")) {
					vItemID = vItemID.split("-")[0];
				}
				if(vPlumSliceItemDetailsForFeeds.containsKey(vItemID) ) {
					vPlumSliceItemDetailsForFeed = vPlumSliceItemDetailsForFeeds.get(vItemID)	;
				}else {
					vPlumSliceItemDetailsForFeed = new ArrayList<String>();
				}
				
				if(product.getDisplayName().size() > 0) {
					vPlumSliceItemDetailsForFeed.add(product.getDisplayName().get(0).getValue());
				}else {
					System.out.println(product.getDisplayName());
					System.out.println(product.getProductId());
					System.out.println("Blank...");
					vPlumSliceItemDetailsForFeed.add("");
				}
				
				vPlumSliceItemDetailsForFeeds.put(vItemID,vPlumSliceItemDetailsForFeed);
				ComplexTypeProductVariations variations = product.getVariations();
				if (variations != null && variations.getVariants() != null) {
					if("8AR00226".equals(product.getProductId())) {
						System.out.println(product.getProductId());
					}
					DefaultVariants vDefaultVariants = new DefaultVariants();
					int seqCount = 1;
					if(variations.getVariants().size() == 0){
						System.out.println(">>>"+product.getProductId());
					}
					for (ComplexTypeProductVariationsVariants variant : variations.getVariants()) {
					
						for (ComplexTypeProductVariationsVariant vComplexTypeProductVariationsVariant : variant
								.getVariant()) {
							
							if(vComplexTypeProductVariationsVariant.isDefault() != null && vComplexTypeProductVariationsVariant.isDefault()) {
								Set<String> variants;
								if(KSS_defaultProduct.containsKey(vItemID)){
									vDefaultVariants = KSS_defaultProduct.get(vItemID);
									variants = vDefaultVariants.getVariant();
								}else {
									vDefaultVariants = new DefaultVariants();
									variants = new HashSet<String>();
								}
								variants.add(vComplexTypeProductVariationsVariant.getProductId().trim());
								vDefaultVariants.setVariant(variants);
								vDefaultVariants.setSequance(String.valueOf(seqCount));
								KSS_defaultProduct.put(vItemID, vDefaultVariants);
								KSS_productsList.add(vComplexTypeProductVariationsVariant.getProductId().trim());
							}else {
								System.out.println(":::"+product.getProductId());
								
							}
							seqCount++;
						}
					}
				}else {
					System.out.println("###"+product.getProductId());
				}
			}
		}
		
	}
	private static void ks_extaractProductDetails() throws Exception {
		File file = new File(ext.getPropValue("ks-master-catalog-path-item"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		List<String> vPlumSliceItemDetailsForFeed;
		for (ComplexTypeProduct product : que.getProduct()) {
			if(product.getVariations() != null) {			

				String vItemID = product.getProductId();
				if(vItemID.contains("-")) {
					vItemID = vItemID.split("-")[0];
				}
				if(vPlumSliceItemDetailsForFeeds.containsKey(vItemID) ) {
					vPlumSliceItemDetailsForFeed = vPlumSliceItemDetailsForFeeds.get(vItemID)	;
				}else {
					vPlumSliceItemDetailsForFeed = new ArrayList<String>();
				}
				if(product.getDisplayName().size() > 0) {
					vPlumSliceItemDetailsForFeed.add(product.getDisplayName().get(0).getValue());
				}else {
					System.out.println(product.getDisplayName());
					System.out.println(product.getProductId());
					System.out.println("Blank...");
					vPlumSliceItemDetailsForFeed.add("");
				}
				vPlumSliceItemDetailsForFeeds.put(vItemID,vPlumSliceItemDetailsForFeed);
				
				ComplexTypeProductVariations variations = product.getVariations();
				if (variations != null && variations.getVariants() != null) {
					if("8AR00226".equals(product.getProductId())) {
						System.out.println(product.getProductId());
					}
					DefaultVariants vDefaultVariants = new DefaultVariants();
					int seqCount = 1;
					if(variations.getVariants().size() == 0){
						System.out.println(">>>"+product.getProductId());
					}
					for (ComplexTypeProductVariationsVariants variant : variations.getVariants()) {
						if(variant.getVariant().size() == 0){
							System.out.println(":::>>>"+product.getProductId());
						}
						for (ComplexTypeProductVariationsVariant vComplexTypeProductVariationsVariant : variant
								.getVariant()) {
							if(vComplexTypeProductVariationsVariant.isDefault() != null && vComplexTypeProductVariationsVariant.isDefault()) {
								Set<String> variants;
								if(KS_defaultProduct.containsKey(vItemID)){
									vDefaultVariants = KS_defaultProduct.get(vItemID);
									variants = vDefaultVariants.getVariant();
								}else {
									vDefaultVariants = new DefaultVariants();
									variants = new HashSet<String>();
								}
								variants.add(vComplexTypeProductVariationsVariant.getProductId().trim());
								vDefaultVariants.setVariant(variants);
								vDefaultVariants.setSequance(String.valueOf(seqCount));
								
								
								KS_defaultProduct.put(vItemID, vDefaultVariants);
								KS_productsList.add(vComplexTypeProductVariationsVariant.getProductId().trim());
							}else {
								System.out.println(":::"+product.getProductId());
								
							}
							seqCount++;
							
						}
					}
				}else {
					System.out.println("###"+product.getProductId());
				}
			}
		}
	}**/




}
