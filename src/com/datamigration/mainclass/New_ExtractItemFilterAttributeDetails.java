package com.datamigration.mainclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttribute;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttributes;
import com.datamigration.pojo.A360DB;
import com.datamigration.pojo.PlumSliceItemDetailsForFeed;

public class New_ExtractItemFilterAttributeDetails {
	static ExtractMainProperties ext = new ExtractMainProperties();
	private static Set<String> KS_productsList = new HashSet<String>();
	private static Set<String> KSS_productsList = new HashSet<String>();
	private static HashMap<String,PlumSliceItemDetailsForFeed> vPlumSliceItemDetailsForFeeds = new HashMap<String,PlumSliceItemDetailsForFeed>();
	private static TreeMap<String,PlumSliceItemDetailsForFeed> vPlumSliceItemDetailsForFeeds_Final = new TreeMap<String,PlumSliceItemDetailsForFeed>();
	private static List<PlumSliceItemDetailsForFeed>vPlumSliceItemDetailsForFeeds_Final_list = new ArrayList<PlumSliceItemDetailsForFeed>();
	private static HashMap<String, A360DB> A360DB = new HashMap<String, A360DB>();
	static String regex = "[0-9]+";
	static DateFormat simpledf = new SimpleDateFormat("yyyyMMdd");
	static DateFormat templateDateFOrmat = new SimpleDateFormat("MM-dd-yyyy");
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	static Pattern p = Pattern.compile(regex);
	public static void main(String[] args) throws Exception {
		New_ExtractA360Data A360DBextract = new New_ExtractA360Data();
		New_ExtractA360Data.extractA360DB();
		A360DB = A360DBextract.getA360DB();
		kss_extaractProductDetails();
		ks_extaractProductDetails();
		filterWithA360Data();
		wrietToExcel("KS");
		wrietToExcel("KSS");
	
		
	}

	private static void wrietToExcel(String brand) throws NumberFormatException, IOException, ParseException {
		List<List<PlumSliceItemDetailsForFeed>> output = ListUtils.partition(vPlumSliceItemDetailsForFeeds_Final_list,
				Integer.valueOf(ext.getPropValue("no_of_splits")));
		int count = 0;
		for (List<PlumSliceItemDetailsForFeed> delta : output) {
			File masterSamplerFile = new File(ext.getPropValue("Item_Filter_Attribute_templatecopy")
					.replaceAll("<<Iteration>>", brand+"_"+String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("Item_Filter_Attribute_mastercopy")), masterSamplerFile);
			FileInputStream masterInputStream = new FileInputStream(masterSamplerFile);
			Workbook masterWorkbook = WorkbookFactory.create(masterInputStream);
			Sheet masterSheet = masterWorkbook.getSheetAt(0);
			int masterRowCount = masterSheet.getLastRowNum();
			for (PlumSliceItemDetailsForFeed masterDetails : delta) {
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
				if("KS".equalsIgnoreCase(brand)){
					cell.setCellValue(masterDetails.getCa8TechFit_ks());
				}else {
					cell.setCellValue("");
				}
				columnCount++;
				cell = masterRow.createCell(columnCount);
				if("KS".equalsIgnoreCase(brand)){
					cell.setCellValue("");
				}else {
					cell.setCellValue("");
				}
				columnCount++;				
				cell = masterRow.createCell(columnCount);
				if("KS".equalsIgnoreCase(brand)){
					cell.setCellValue("");
				}else {
					cell.setCellValue(masterDetails.getCa7EarringStyle_kss());
				}
				columnCount++;
				cell = masterRow.createCell(columnCount);
				if("KS".equalsIgnoreCase(brand)){
					cell.setCellValue("");
				}else {
					cell.setCellValue("");
				}
				columnCount++;				
				cell = masterRow.createCell(columnCount);
				if("KS".equalsIgnoreCase(brand)){
					cell.setCellValue("");
				}else {
					cell.setCellValue(masterDetails.getCa5DressLength_kss());
				}
				columnCount++;
				cell = masterRow.createCell(columnCount);
				if("KS".equalsIgnoreCase(brand)){
					cell.setCellValue("");
				}else {
					cell.setCellValue("");
				}
				columnCount++;				
				cell = masterRow.createCell(columnCount);
				if("KS".equalsIgnoreCase(brand)){
					cell.setCellValue("");
				}else {
					cell.setCellValue(masterDetails.getCa17DressOcation_kss());
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

	private static String formatDateForTemplate(String firstPublishDate) throws ParseException {
		if (firstPublishDate == null || (firstPublishDate != null && firstPublishDate.isBlank())) {
			return "";
		}
		return templateDateFOrmat.format(df.parse(firstPublishDate));
	}
	private static void filterWithA360Data() {
		A360DB vA360DB =  new A360DB();
		for (String productId : KS_productsList) {
			if(A360DB.containsKey(productId) ) {
				vA360DB = A360DB.get(productId);
				if(vPlumSliceItemDetailsForFeeds.containsKey(vA360DB.getStyleId())){
					vPlumSliceItemDetailsForFeeds_Final.put(vA360DB.getStyleId(),vPlumSliceItemDetailsForFeeds.get(vA360DB.getStyleId()));
				}else {
					PlumSliceItemDetailsForFeed vPlumSliceItemDetailsForFeed = new PlumSliceItemDetailsForFeed();
					vPlumSliceItemDetailsForFeed.setItemId(vA360DB.getStyleId());
					vPlumSliceItemDetailsForFeeds_Final.put(vA360DB.getStyleId(),vPlumSliceItemDetailsForFeed);
				}
				
			}
		}
		for (String productId : KSS_productsList) {
			if(A360DB.containsKey(productId) ) {
				vA360DB = A360DB.get(productId);
				if(vPlumSliceItemDetailsForFeeds.containsKey(vA360DB.getStyleId())){
					vPlumSliceItemDetailsForFeeds_Final.put(vA360DB.getStyleId(),vPlumSliceItemDetailsForFeeds.get(vA360DB.getStyleId()));
				}else {
					PlumSliceItemDetailsForFeed vPlumSliceItemDetailsForFeed = new PlumSliceItemDetailsForFeed();
					vPlumSliceItemDetailsForFeed.setItemId(vA360DB.getStyleId());
					vPlumSliceItemDetailsForFeeds_Final.put(vA360DB.getStyleId(),vPlumSliceItemDetailsForFeed);
				}
				
			}
		}
		
		for(String styleId : vPlumSliceItemDetailsForFeeds_Final.keySet()) {
			vPlumSliceItemDetailsForFeeds_Final_list.add(vPlumSliceItemDetailsForFeeds_Final.get(styleId));
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
				String dressLength = extractAttributeFromCustomAttribute("custom-attribute5", product, false);
				String dressOccation = extractAttributeFromCustomAttribute("custom-attribute17", product, false);
				String earingStyle = extractAttributeFromCustomAttribute("custom-attribute7", product, false);
				String techFit = extractAttributeFromCustomAttribute("custom-attribute8", product, false);
				vPlumSliceItemDetailsForFeed.setItemId(vItemID);
				vPlumSliceItemDetailsForFeed.setCa5DressLength_kss(dressLength);	
				vPlumSliceItemDetailsForFeed.setCa17DressOcation_kss(dressOccation);
				vPlumSliceItemDetailsForFeed.setCa7EarringStyle_kss(earingStyle);
				vPlumSliceItemDetailsForFeed.setCa8TechFit_kss(techFit);			
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
				String dressLength = extractAttributeFromCustomAttribute("custom-attribute5", product, false);
				String dressOccation = extractAttributeFromCustomAttribute("custom-attribute17", product, false);
				String earingStyle = extractAttributeFromCustomAttribute("custom-attribute7", product, false);
				String techFit = extractAttributeFromCustomAttribute("custom-attribute8", product, false);
				vPlumSliceItemDetailsForFeed.setItemId(vItemID);
				vPlumSliceItemDetailsForFeed.setCa5DressLength_ks(dressLength);	
				vPlumSliceItemDetailsForFeed.setCa17DressOcation_ks(dressOccation);
				vPlumSliceItemDetailsForFeed.setCa7EarringStyle_ks(earingStyle);
				vPlumSliceItemDetailsForFeed.setCa8TechFit_ks(techFit);			
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
