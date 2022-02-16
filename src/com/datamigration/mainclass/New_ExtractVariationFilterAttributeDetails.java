package com.datamigration.mainclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.datamigration.javaclass.SharedTypeSiteSpecificBoolean;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttribute;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttributes;
import com.datamigration.javaclass.SharedTypeSiteSpecificDateTime;
import com.datamigration.pojo.A360DB;
import com.datamigration.pojo.PlumSliceItemDetailsForFeed;
import com.datamigration.pojo.PlumSliceProduct;
import com.datamigration.pojo.PlumSliceVariationDetailsForFeed;
import com.datamigration.pojo.VariationInfo;

public class New_ExtractVariationFilterAttributeDetails {
	static ExtractMainProperties ext = new ExtractMainProperties();
	private static Set<String> KS_productsList = new HashSet<String>();
	private static Set<String> KSS_productsList = new HashSet<String>();
	private static List<String> ks_vaeriationList = new ArrayList<String>();
	private static List<String> kss_vaeriationList = new ArrayList<String>();
	private static HashMap<String, A360DB> A360DB = new HashMap<String, A360DB>();
	private static HashMap<String,PlumSliceItemDetailsForFeed> vPlumSliceItemDetailsForFeeds = new HashMap<String,PlumSliceItemDetailsForFeed>();
	private static HashMap<String, VariationInfo> variaionList_ks = new HashMap<String, VariationInfo>();
	private static HashMap<String, VariationInfo> variaionList_kss = new HashMap<String, VariationInfo>();
	static String regex = "[0-9]+";
	static DateFormat simpledf = new SimpleDateFormat("yyyyMMdd");
	static DateFormat templateDateFOrmat = new SimpleDateFormat("MM-dd-yyyy");
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	static Pattern p = Pattern.compile(regex);
	static HashMap<String,PlumSliceProduct> allProductIformation = new HashMap<String,PlumSliceProduct>();
	private static List<PlumSliceVariationDetailsForFeed> plumSliceVariationDetailsForFeedForExcel = new ArrayList<PlumSliceVariationDetailsForFeed>();
	private static TreeMap<String,PlumSliceVariationDetailsForFeed> vPlumSliceVariationDetailsForFeeds_final = new TreeMap<String,PlumSliceVariationDetailsForFeed>();
	public static void main(String[] args) throws Exception {
		New_ExtractA360Data A360DBextract = new New_ExtractA360Data();
		New_ExtractA360Data.extractA360DB();
		A360DB = A360DBextract.getA360DB();
		kss_extaractProductDetails();
		ks_extaractProductDetails();
		createVariationMap();
		exatrctAllProductInformation();
		exatrctDataToBePouplated();
		writeExcel("KS");
		writeExcel("KSS");
		System.out.println("finish..........");
	}
	
	
	private static void writeExcel(String brand) throws NumberFormatException, IOException, ParseException {
		List<List<PlumSliceVariationDetailsForFeed>> output = ListUtils.partition(plumSliceVariationDetailsForFeedForExcel,
				Integer.valueOf(ext.getPropValue("no_of_splits")));
		int count = 0;
		
		for (List<PlumSliceVariationDetailsForFeed> delta : output) {
			File masterSamplerFile = new File(ext.getPropValue("variation_Filter_Attribute_templatecopy")
					.replaceAll("<<Iteration>>", brand+"_"+String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("variation_Filter_Attribute_mastercopy")), masterSamplerFile);
			FileInputStream masterInputStream = new FileInputStream(masterSamplerFile);
			Workbook masterWorkbook = WorkbookFactory.create(masterInputStream);
			Sheet masterSheet = masterWorkbook.getSheetAt(0);
			int masterRowCount = masterSheet.getLastRowNum();
			for (PlumSliceVariationDetailsForFeed masterDetails : delta) {
				Row masterRow = masterSheet.createRow(++masterRowCount);
				int columnCount = 0;
				Cell cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemId());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				if("KS".equalsIgnoreCase(brand)){
					cell.setCellValue("KSUSRT");
				}else {
					cell.setCellValue("KSUSSUR");
				}
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getVariationId());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				if("KS".equalsIgnoreCase(brand)){
					cell.setCellValue("KSUSRT");
				}else {
					cell.setCellValue("KSUSSUR");
				}				
				columnCount++;
				cell = masterRow.createCell(columnCount);
				if("KS".equalsIgnoreCase(brand)){
					cell.setCellValue(masterDetails.getPattern_ks());
				}else {
					cell.setCellValue("");
				}
			}
			masterInputStream.close();
			FileOutputStream outputStream = new FileOutputStream(masterSamplerFile);
			masterWorkbook.write(outputStream);
			masterWorkbook.close();
			outputStream.close();
			count++;
		}
		
	}


	
	private static void exatrctDataToBePouplated() throws Exception {
		PlumSliceVariationDetailsForFeed vPlumSliceVariationDetailsForFeed ;
		for(String variationId : variaionList_ks.keySet()) {
			VariationInfo tmpVariationInfor = variaionList_ks.get(variationId);
			if(vPlumSliceVariationDetailsForFeeds_final.containsKey(tmpVariationInfor.getVariationId())) {
				vPlumSliceVariationDetailsForFeed = vPlumSliceVariationDetailsForFeeds_final.get(tmpVariationInfor.getVariationId());
			}else {
				vPlumSliceVariationDetailsForFeed = new PlumSliceVariationDetailsForFeed();
			}
			vPlumSliceVariationDetailsForFeed.setItemId(tmpVariationInfor.getItemId());
			vPlumSliceVariationDetailsForFeed.setVariationId(variationId);
			PlumSliceItemDetailsForFeed tmpPlumSliceItemDetailsForFeed = vPlumSliceItemDetailsForFeeds.get(tmpVariationInfor.getItemId());
			
			String patternDetails = generatePatternDetails_ks(tmpVariationInfor.getProducts());
			if(patternDetails.isBlank() && tmpPlumSliceItemDetailsForFeed != null) {
				patternDetails = tmpPlumSliceItemDetailsForFeed.getCa3Pattern_ks();
				
			}
			vPlumSliceVariationDetailsForFeed.setPattern_ks(patternDetails);		
			vPlumSliceVariationDetailsForFeeds_final.put(tmpVariationInfor.getVariationId(), vPlumSliceVariationDetailsForFeed);
		}
		for(String variationId : variaionList_kss.keySet()) {
			VariationInfo tmpVariationInfor = variaionList_kss.get(variationId);
			if(vPlumSliceVariationDetailsForFeeds_final.containsKey(tmpVariationInfor.getVariationId())) {
				vPlumSliceVariationDetailsForFeed = vPlumSliceVariationDetailsForFeeds_final.get(tmpVariationInfor.getVariationId());
			}else {
				vPlumSliceVariationDetailsForFeed = new PlumSliceVariationDetailsForFeed();
			}
			vPlumSliceVariationDetailsForFeed.setItemId(tmpVariationInfor.getItemId());
			vPlumSliceVariationDetailsForFeed.setVariationId(variationId);
			PlumSliceItemDetailsForFeed tmpPlumSliceItemDetailsForFeed = vPlumSliceItemDetailsForFeeds.get(tmpVariationInfor.getItemId());
			
			String patternDetails = generatePatternDetails_kss(tmpVariationInfor.getProducts());
			if(patternDetails.isBlank() && tmpPlumSliceItemDetailsForFeed != null) {
				patternDetails = tmpPlumSliceItemDetailsForFeed.getCa3Pattern_kss();
				
			}
			vPlumSliceVariationDetailsForFeed.setPattern_kss(patternDetails);
			vPlumSliceVariationDetailsForFeeds_final.put(tmpVariationInfor.getVariationId(), vPlumSliceVariationDetailsForFeed);
		}
		
		for(String styleId : vPlumSliceVariationDetailsForFeeds_final.keySet()) {
			plumSliceVariationDetailsForFeedForExcel.add(vPlumSliceVariationDetailsForFeeds_final.get(styleId));
		}
		
	}
	private static String generatePatternDetails_kss(Set<String> products) {
		String flag = "";
		for(String product : products){
			PlumSliceProduct tmpPlumSliceProduct = allProductIformation.get(product)	;
			if(tmpPlumSliceProduct == null) {
				continue;
			}
			if("".equals(flag)) {
				flag = tmpPlumSliceProduct.getPattern_surprise();
			}
		}
		return flag;
	}
	
	private static String generatePatternDetails_ks(Set<String> products) {
		String flag = "";
		for(String product : products){
			PlumSliceProduct tmpPlumSliceProduct = allProductIformation.get(product)	;
			if(tmpPlumSliceProduct == null) {
				continue;
			}
			if("".equals(flag)) {
				flag = tmpPlumSliceProduct.getPattern_kate();
			}
		}
		return flag;
	}
	private static void exatrctAllProductInformation() throws Exception {
		File file = new File(ext.getPropValue("master-catalog-path"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		PlumSliceProduct tmpPlumSliceProduct ;
		for (ComplexTypeProduct product : que.getProduct()) {
			if (product.getVariations() == null && A360DB.containsKey(product.getProductId())) {
				String Pattern = extractAttributeFromCustomAttribute("custom-attribute3", product, false);
				tmpPlumSliceProduct = new PlumSliceProduct();
				tmpPlumSliceProduct.setSkuId(product.getProductId());
				tmpPlumSliceProduct.setPattern_kate(Pattern);
				tmpPlumSliceProduct.setPattern_surprise(Pattern);
				allProductIformation.put(product.getProductId(), tmpPlumSliceProduct);
			}
		}
		
	}


	private static void createVariationMap() {
		A360DB vA360DB =  new A360DB();
		VariationInfo vVariationInfo;
		for (String ksmasterId : KS_productsList) {
			if(A360DB.containsKey(ksmasterId) ) {				
				vA360DB = A360DB.get(ksmasterId)	;
				if(!ks_vaeriationList.contains(vA360DB.getVariationId()+vA360DB.getStyleId())) {
					if(variaionList_ks.containsKey(vA360DB.getVariationId())) {
						vVariationInfo = variaionList_ks.get(vA360DB.getVariationId())	;
						vVariationInfo.setItemId(vA360DB.getStyleId());
						vVariationInfo.setVariationId(vA360DB.getVariationId());	
						Set<String> products = vVariationInfo.getProducts();
						products.add(ksmasterId);
						vVariationInfo.setProducts(products);			
						}else {
						vVariationInfo = new VariationInfo();
						vVariationInfo.setItemId(vA360DB.getStyleId());
						vVariationInfo.setVariationId(vA360DB.getVariationId());			
						Set<String> products = new HashSet<String>();
						products.add(ksmasterId)	;
						vVariationInfo.setProducts(products);	
					}
					variaionList_ks.put(vA360DB.getVariationId(),vVariationInfo);
					ks_vaeriationList.add(vA360DB.getVariationId()+vA360DB.getStyleId());
				}
			}
		}
		
		for (String ksmasterId : KSS_productsList) {
			if(A360DB.containsKey(ksmasterId) ) {				
				vA360DB = A360DB.get(ksmasterId)	;
				if(!kss_vaeriationList.contains(vA360DB.getVariationId()+vA360DB.getStyleId())) {
					if(variaionList_kss.containsKey(vA360DB.getVariationId())) {
						vVariationInfo = variaionList_kss.get(vA360DB.getVariationId())	;
						vVariationInfo.setItemId(vA360DB.getStyleId());
						vVariationInfo.setVariationId(vA360DB.getVariationId());	
						Set<String> products = vVariationInfo.getProducts();
						products.add(ksmasterId);
						vVariationInfo.setProducts(products);			
						}else {
						vVariationInfo = new VariationInfo();
						vVariationInfo.setItemId(vA360DB.getStyleId());
						vVariationInfo.setVariationId(vA360DB.getVariationId());			
						Set<String> products = new HashSet<String>();
						products.add(ksmasterId)	;
						vVariationInfo.setProducts(products);	
					}
					variaionList_kss.put(vA360DB.getVariationId(),vVariationInfo);
					kss_vaeriationList.add(vA360DB.getVariationId()+vA360DB.getStyleId());
				}
			}
		}
	}


	private static void kss_extaractProductDetails() throws Exception {
		File file = new File(ext.getPropValue("kss-master-catalog-path-item"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		PlumSliceItemDetailsForFeed vPlumSliceItemDetailsForFeed;
		for (ComplexTypeProduct product : que.getProduct()) {
			if(product.getVariations() != null) {
				String vItemID = product.getProductId();
				if(vItemID.contains("-")) {
					vItemID = vItemID.split("-")[0];
				}
				if(vPlumSliceItemDetailsForFeeds.containsKey(vItemID) ) {
					vPlumSliceItemDetailsForFeed = vPlumSliceItemDetailsForFeeds.get(vItemID)	;
				}else {
					vPlumSliceItemDetailsForFeed = new PlumSliceItemDetailsForFeed();
				}
				String Pattern = extractAttributeFromCustomAttribute("custom-attribute3", product, false);
				vPlumSliceItemDetailsForFeed.setCa3Pattern_kss(Pattern);	
				vPlumSliceItemDetailsForFeeds.put(vItemID,vPlumSliceItemDetailsForFeed);
				ComplexTypeProductVariations variations = product.getVariations();
				if (variations != null && variations.getVariants() != null) {
					for (ComplexTypeProductVariationsVariants variant : variations.getVariants()) {
						for (ComplexTypeProductVariationsVariant vComplexTypeProductVariationsVariant : variant
								.getVariant()) {
							KSS_productsList.add(vComplexTypeProductVariationsVariant.getProductId().trim());
						}
					}
				}
			}
		}
		
	}
	private static String extractAttributeFromCustomAttribute(String pCustomAttributeToBeFetched,
			ComplexTypeProduct product, boolean isBoolean) {
		String result = "";
		try {
			SharedTypeSiteSpecificCustomAttributes customAttribute = product.getCustomAttributes();
			List<SharedTypeSiteSpecificCustomAttribute> customAttributes = customAttribute.getCustomAttribute();
			for (SharedTypeSiteSpecificCustomAttribute pSharedTypeSiteSpecificCustomAttribute : customAttributes) {
				if (pCustomAttributeToBeFetched
						.equalsIgnoreCase(pSharedTypeSiteSpecificCustomAttribute.getAttributeId())) {
					result = pSharedTypeSiteSpecificCustomAttribute.getContent().get(0).toString();
				}

			}
			if (isBoolean) {
				if ("true".equalsIgnoreCase(result) || "1".equalsIgnoreCase(result)) {
					result = "Y";
				} else if ("false".equalsIgnoreCase(result) || "0".equalsIgnoreCase(result)) {
					result = "";
				}
			}
		} catch (Exception e) {
			System.out.println(pCustomAttributeToBeFetched + " has error for product " + product.getProductId());
		}
		// System.out.println(pCustomAttributeToBeFetched+" > "+result);
		return result;
	}
	private static void ks_extaractProductDetails() throws Exception {
		File file = new File(ext.getPropValue("ks-master-catalog-path-item"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		PlumSliceItemDetailsForFeed vPlumSliceItemDetailsForFeed;
		for (ComplexTypeProduct product : que.getProduct()) {
			if(product.getVariations() != null) {			

				String vItemID = product.getProductId();
				if(vItemID.contains("-")) {
					vItemID = vItemID.split("-")[0];
				}
				if(vPlumSliceItemDetailsForFeeds.containsKey(vItemID) ) {
					vPlumSliceItemDetailsForFeed = vPlumSliceItemDetailsForFeeds.get(vItemID)	;
				}else {
					vPlumSliceItemDetailsForFeed = new PlumSliceItemDetailsForFeed();
				}
				
				String Pattern = extractAttributeFromCustomAttribute("custom-attribute3", product, false);
				vPlumSliceItemDetailsForFeed.setCa3Pattern_ks(Pattern);	
				vPlumSliceItemDetailsForFeeds.put(vItemID,vPlumSliceItemDetailsForFeed);
				
				ComplexTypeProductVariations variations = product.getVariations();
				if (variations != null && variations.getVariants() != null) {
					for (ComplexTypeProductVariationsVariants variant : variations.getVariants()) {
						for (ComplexTypeProductVariationsVariant vComplexTypeProductVariationsVariant : variant
								.getVariant()) {
							KS_productsList.add(vComplexTypeProductVariationsVariant.getProductId().trim());
						}
					}
				}
			}
		}
	}




}
