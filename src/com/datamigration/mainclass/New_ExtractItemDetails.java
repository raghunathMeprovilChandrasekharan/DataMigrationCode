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

public class New_ExtractItemDetails {
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
		wrietToExcel();
		
	
		
	}

	private static void wrietToExcel() throws NumberFormatException, IOException, ParseException {
		List<List<PlumSliceItemDetailsForFeed>> output = ListUtils.partition(vPlumSliceItemDetailsForFeeds_Final_list,
				Integer.valueOf(ext.getPropValue("no_of_splits")));
		int count = 0;
		for (List<PlumSliceItemDetailsForFeed> delta : output) {
			File masterSamplerFile = new File(ext.getPropValue("plumslice_master_template_sampler")
					.replaceAll("<<Iteration>>", String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("plumslice_master_template_mastercopy")), masterSamplerFile);
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
				cell.setCellValue(formatDateForTemplate(masterDetails.getFirstPublishedDate_ks()));
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(formatDateForTemplate(masterDetails.getFirstPublishedDate_kss()));
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getIsExclusive_ks());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getIsExclusive_kss());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getProductVedio_ks());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getProductVedio_kss());
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
				String c__debut_date = extractAttributeFromCustomAttribute("debut-date", product, false);
				Matcher m = p.matcher(c__debut_date);
				if (!m.matches()) {
					c__debut_date = "";
				} else {
					Date date = simpledf.parse(c__debut_date);
					c__debut_date = df.format(date);
				}
				String c__onlineExclusive = extractAttributeFromCustomAttribute("onlineExclusive", product, true);
				String c__productVideoURL = extractAttributeFromCustomAttribute("productVideoURL", product, false);
				vPlumSliceItemDetailsForFeed.setItemId(vItemID);		
				vPlumSliceItemDetailsForFeed.setFirstPublishedDate_kss(c__debut_date);
				vPlumSliceItemDetailsForFeed.setIsExclusive_kss(c__onlineExclusive);	
				vPlumSliceItemDetailsForFeed.setProductVedio_kss(c__productVideoURL);	
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
				String c__debut_date = extractAttributeFromCustomAttribute("debut-date", product, false);
				Matcher m = p.matcher(c__debut_date);
				if (!m.matches()) {
					c__debut_date = "";
				} else {
					Date date = simpledf.parse(c__debut_date);
					c__debut_date = df.format(date);
				}
				String c__onlineExclusive = extractAttributeFromCustomAttribute("onlineExclusive", product, true);
				String c__productVideoURL = extractAttributeFromCustomAttribute("productVideoURL", product, false);
				vPlumSliceItemDetailsForFeed.setItemId(vItemID);		
				vPlumSliceItemDetailsForFeed.setFirstPublishedDate_ks(c__debut_date);
				vPlumSliceItemDetailsForFeed.setIsExclusive_ks(c__onlineExclusive);	
				vPlumSliceItemDetailsForFeed.setProductVedio_ks(c__productVideoURL);	
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
