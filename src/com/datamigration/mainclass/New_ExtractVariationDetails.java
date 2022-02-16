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

public class New_ExtractVariationDetails {
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
		writeExcel();
		System.out.println("finish..........");
	}
	
	
	private static void writeExcel() throws NumberFormatException, IOException, ParseException {
		List<List<PlumSliceVariationDetailsForFeed>> output = ListUtils.partition(plumSliceVariationDetailsForFeedForExcel,
				Integer.valueOf(ext.getPropValue("no_of_splits")));
		int count = 0;
		
		for (List<PlumSliceVariationDetailsForFeed> delta : output) {
			File masterSamplerFile = new File(ext.getPropValue("plumslice_variation_template_sampler")
					.replaceAll("<<Iteration>>", String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("plumslice_variation_template_mastercopy")), masterSamplerFile);
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
				cell.setCellValue(masterDetails.getVariationId());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(formatDateForTemplate(masterDetails.getFirstPublishedDate_ks()));
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(formatDateForTemplate(masterDetails.getFirstPublishedDate_kss()));
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getOnileFlag_ks() );
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getOnileFlag_kss());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(formatDateForTemplate(masterDetails.getOnileFrom_ks()) );
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(formatDateForTemplate(masterDetails.getOnileFrom_kss()));
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(formatDateForTemplate(masterDetails.getOnileTo_ks()) );
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(formatDateForTemplate(masterDetails.getOnileTo_kss()));
				columnCount++;
				
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getSearchableIfUnavaialble_ks() );
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getSearchableIfUnavaialble_kss());
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
			if(tmpPlumSliceItemDetailsForFeed != null) {
				vPlumSliceVariationDetailsForFeed.setFirstPublishedDate_ks(tmpPlumSliceItemDetailsForFeed.getFirstPublishedDate_ks());
			}else {
				vPlumSliceVariationDetailsForFeed.setFirstPublishedDate_ks("");
			}
			
			String onlineFlag_ks = generateOnlineFlagFsDetails_ks(tmpVariationInfor.getProducts());
			if(onlineFlag_ks.isBlank() && tmpPlumSliceItemDetailsForFeed != null) {
				onlineFlag_ks = tmpPlumSliceItemDetailsForFeed.getOnileFlag_ks();
				
			}
			vPlumSliceVariationDetailsForFeed.setOnileFlag_ks(onlineFlag_ks);	
			String onlineFrom_ks = generateOnlineFromFsDetails_ks(tmpVariationInfor.getProducts());
			if(onlineFrom_ks.isBlank() && tmpPlumSliceItemDetailsForFeed != null) {
				onlineFrom_ks = tmpPlumSliceItemDetailsForFeed.getOnileFrom_ks();
			}
			vPlumSliceVariationDetailsForFeed.setOnileFrom_ks(onlineFrom_ks);		
			String onlineTo_ks = generateOnlineToFsDetails_ks(tmpVariationInfor.getProducts());
			if(onlineTo_ks.isBlank() && tmpPlumSliceItemDetailsForFeed != null) {
				onlineTo_ks = tmpPlumSliceItemDetailsForFeed.getOnileTo_ks();
			}
			vPlumSliceVariationDetailsForFeed.setOnileTo_ks(onlineTo_ks);		
			String searchableIfUnavailable_ks = generateSearchableIfUnavailableFsDetails_ks(tmpVariationInfor.getProducts());
			if(searchableIfUnavailable_ks.isBlank() && tmpPlumSliceItemDetailsForFeed != null) {
				searchableIfUnavailable_ks = tmpPlumSliceItemDetailsForFeed.getSearchableIfUnavaialble_ks();
			}
			vPlumSliceVariationDetailsForFeed.setSearchableIfUnavaialble_ks(searchableIfUnavailable_ks);	
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
			
			if(tmpPlumSliceItemDetailsForFeed != null) {
				vPlumSliceVariationDetailsForFeed.setFirstPublishedDate_kss(tmpPlumSliceItemDetailsForFeed.getFirstPublishedDate_kss());
			}else {
				vPlumSliceVariationDetailsForFeed.setFirstPublishedDate_kss("");
			}
			
			
	
			String onlineFlag_kss = generateOnlineFlagFsDetails_kss(tmpVariationInfor.getProducts());
			if(onlineFlag_kss.isBlank() && tmpPlumSliceItemDetailsForFeed != null) {
				onlineFlag_kss = tmpPlumSliceItemDetailsForFeed.getOnileFlag_kss();
				
			}
			vPlumSliceVariationDetailsForFeed.setOnileFlag_kss(onlineFlag_kss);	
			String onlineFrom_kss = generateOnlineFromFsDetails_kss(tmpVariationInfor.getProducts());
			if(onlineFrom_kss.isBlank() && tmpPlumSliceItemDetailsForFeed != null) {
				onlineFrom_kss = tmpPlumSliceItemDetailsForFeed.getOnileFrom_kss();
				
			}
			vPlumSliceVariationDetailsForFeed.setOnileFrom_kss(onlineFrom_kss);		
			String onlineTo_kss = generateOnlineToFsDetails_kss(tmpVariationInfor.getProducts());
			if(onlineTo_kss.isBlank() && tmpPlumSliceItemDetailsForFeed != null) {
				onlineTo_kss = tmpPlumSliceItemDetailsForFeed.getOnileTo_kss();
			}
			
			vPlumSliceVariationDetailsForFeed.setOnileTo_kss(onlineTo_kss);		
			
			String searchableIfUnavailable_kss = generateSearchableIfUnavailableFsDetails_kss(tmpVariationInfor.getProducts());
			if(searchableIfUnavailable_kss.isBlank() && tmpPlumSliceItemDetailsForFeed != null) {
				searchableIfUnavailable_kss = tmpPlumSliceItemDetailsForFeed.getSearchableIfUnavaialble_kss();
			}
			
			
			vPlumSliceVariationDetailsForFeed.setSearchableIfUnavaialble_kss(searchableIfUnavailable_kss);	
			vPlumSliceVariationDetailsForFeeds_final.put(tmpVariationInfor.getVariationId(), vPlumSliceVariationDetailsForFeed);
		}
		
		for(String styleId : vPlumSliceVariationDetailsForFeeds_final.keySet()) {
			plumSliceVariationDetailsForFeedForExcel.add(vPlumSliceVariationDetailsForFeeds_final.get(styleId));
		}
		
	}

	





	///ks//

	private static String generateOnlineToFsDetails_ks(Set<String> products) throws Exception {
		Calendar onlineToCal = new GregorianCalendar();
		boolean onlineToCal_set = false;
		String onlineTo_kate = "";
		for(String product : products){
			PlumSliceProduct tmpPlumSliceProduct = allProductIformation.get(product)	;
			if(tmpPlumSliceProduct == null) {
				continue;
			}
			if (!tmpPlumSliceProduct.getOnlineTo_kate().isBlank()) {
				if (!onlineToCal_set) {
					onlineToCal = convertToDate(tmpPlumSliceProduct.getOnlineTo_kate());
					onlineToCal_set = true;
				}
				if (convertToDate(tmpPlumSliceProduct.getOnlineTo_kate()).after(onlineToCal) || convertToDate(tmpPlumSliceProduct.getOnlineTo_kate()).equals(onlineToCal)) {
					onlineTo_kate = tmpPlumSliceProduct.getOnlineTo_kate();
					onlineToCal = convertToDate(tmpPlumSliceProduct.getOnlineTo_kate());
				}
			}
		}
		return onlineTo_kate;
	}
	
	
	private static String generateOnlineFromFsDetails_ks(Set<String> products) throws Exception {
		Calendar onlineFromCal = new GregorianCalendar();
		boolean onlineFromCal_set = false;
		String onlineFrom_kate = "";
		for(String product : products){
			PlumSliceProduct tmpPlumSliceProduct = allProductIformation.get(product)	;
			if(tmpPlumSliceProduct == null) {
				continue;
			}
			if (!tmpPlumSliceProduct.getOnlineFrom_kate().isBlank()) {
				if (!onlineFromCal_set) {
					onlineFromCal = convertToDate(tmpPlumSliceProduct.getOnlineFrom_kate());
					onlineFromCal_set = true;
				}
				if (convertToDate(tmpPlumSliceProduct.getOnlineFrom_kate()).before(onlineFromCal) || convertToDate(tmpPlumSliceProduct.getOnlineFrom_kate()).equals(onlineFromCal) ) {
					onlineFrom_kate = tmpPlumSliceProduct.getOnlineFrom_kate();
					onlineFromCal = convertToDate(tmpPlumSliceProduct.getOnlineFrom_kate());
				}
			}
		}
		return onlineFrom_kate;
	}
	
	
	
	private static Calendar convertToDate(String onlineFrom) throws ParseException {
		Date date = df.parse(onlineFrom);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}


	private static String generateOnlineFlagFsDetails_ks(Set<String> products) {
		String flag = "";
		for(String product : products){
			PlumSliceProduct tmpPlumSliceProduct = allProductIformation.get(product)	;
			if(tmpPlumSliceProduct == null) {
				continue;
			}
			if("".equals(flag) || "N".equals(flag)) {
				flag = tmpPlumSliceProduct.getOnlineFlag_kate();
			}
		}
		return flag;
	}
	
	private static String generateSearchableIfUnavailableFsDetails_ks(Set<String> products) {
		String flag = "";
		for(String product : products){
			PlumSliceProduct tmpPlumSliceProduct = allProductIformation.get(product)	;
			if(tmpPlumSliceProduct == null) {
				continue;
			}
			if("".equals(flag) || "N".equals(flag)) {
				flag = tmpPlumSliceProduct.getSearchableifUnavailable_kate();
			}
		}
		return flag;
	}

	
	
	//ks//
	


	///kss//

	private static String generateOnlineToFsDetails_kss(Set<String> products) throws Exception {
		Calendar onlineToCal = new GregorianCalendar();
		boolean onlineToCal_set = false;
		String onlineTo_surprise = "";
		for(String product : products){
			PlumSliceProduct tmpPlumSliceProduct = allProductIformation.get(product)	;
			if(tmpPlumSliceProduct == null) {
				continue;
			}
			if (!tmpPlumSliceProduct.getOnlineTo_surprise().isBlank()) {
				if (!onlineToCal_set) {
					onlineToCal = convertToDate(tmpPlumSliceProduct.getOnlineTo_surprise());
					onlineToCal_set = true;
				}
				if (convertToDate(tmpPlumSliceProduct.getOnlineTo_surprise()).after(onlineToCal) || convertToDate(tmpPlumSliceProduct.getOnlineTo_surprise()).equals(onlineToCal)) {
					onlineTo_surprise = tmpPlumSliceProduct.getOnlineTo_surprise();
					onlineToCal = convertToDate(tmpPlumSliceProduct.getOnlineTo_surprise());
				}
			}
		}
		return onlineTo_surprise;
	}
	
	
	private static String generateOnlineFromFsDetails_kss(Set<String> products) throws Exception {
		Calendar onlineFromCal = new GregorianCalendar();
		boolean onlineFromCal_set = false;
		String onlineFrom_surprise  = "";
		for(String product : products){
			PlumSliceProduct tmpPlumSliceProduct = allProductIformation.get(product)	;
			if(tmpPlumSliceProduct == null) {
				continue;
			}
			if (!tmpPlumSliceProduct.getOnlineFrom_surprise().isBlank()) {
				if (!onlineFromCal_set) {
					onlineFromCal = convertToDate(tmpPlumSliceProduct.getOnlineFrom_surprise());
					onlineFromCal_set = true;
				}
				if (convertToDate(tmpPlumSliceProduct.getOnlineFrom_surprise()).before(onlineFromCal) || convertToDate(tmpPlumSliceProduct.getOnlineFrom_surprise()).equals(onlineFromCal) ) {
					onlineFrom_surprise = tmpPlumSliceProduct.getOnlineFrom_surprise();
					onlineFromCal = convertToDate(tmpPlumSliceProduct.getOnlineFrom_surprise());
				}
			}
		}
		return onlineFrom_surprise;
	}
	
	
	


	private static String generateOnlineFlagFsDetails_kss(Set<String> products) {
		String flag = "";
		for(String product : products){
			PlumSliceProduct tmpPlumSliceProduct = allProductIformation.get(product)	;
			if(tmpPlumSliceProduct == null) {
				continue;
			}
			
			if("".equals(flag) || "N".equals(flag)) {
				flag = tmpPlumSliceProduct.getOnlineFlag_surprise();
			}
		}
		return flag;
	}
	
	private static String generateSearchableIfUnavailableFsDetails_kss(Set<String> products) {
		String flag = "";
		for(String product : products){
			PlumSliceProduct tmpPlumSliceProduct = allProductIformation.get(product)	;
			if(tmpPlumSliceProduct == null) {
				continue;
			}
			if("".equals(flag) || "N".equals(flag)) {
				flag = tmpPlumSliceProduct.getSearchableifUnavailable_surprise();
			}
		}
		return flag;
	}

	
	
	//kss//
	
	
	
	
	
	
	
	
	
	


	private static void exatrctAllProductInformation() throws Exception {
		File file = new File(ext.getPropValue("master-catalog-path"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		PlumSliceProduct tmpPlumSliceProduct ;
		for (ComplexTypeProduct product : que.getProduct()) {
			if (product.getVariations() == null && A360DB.containsKey(product.getProductId())) {
				tmpPlumSliceProduct = new PlumSliceProduct();
				tmpPlumSliceProduct.setOnlineFlag_kate(fetchOnlineFlag(product, "KATESPADE"));			
				tmpPlumSliceProduct.setOnlineFlag_surprise(fetchOnlineFlag(product, "SURPRISE"));
				tmpPlumSliceProduct.setOnlineFrom_kate(fetchOnlineFrom(product, "KATESPADE"));			
				tmpPlumSliceProduct.setOnlineFrom_surprise(fetchOnlineFrom(product, "SURPRISE"));
				tmpPlumSliceProduct.setOnlineTo_kate(fetchOnlineTo(product, "KATESPADE"));			
				tmpPlumSliceProduct.setOnlineTo_surprise(fetchOnlineTo(product, "SURPRISE"));
				tmpPlumSliceProduct.setSearchableifUnavailable_kate(fetchSearchableIfUnAvailable(product, "KATESPADE"));			
				tmpPlumSliceProduct.setSearchableifUnavailable_surprise(fetchSearchableIfUnAvailable(product, "SURPRISE"));
				tmpPlumSliceProduct.setSkuId(product.getProductId());
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
				String c__debut_date = extractAttributeFromCustomAttribute("debut-date", product, false);
				Matcher m = p.matcher(c__debut_date);
				if (!m.matches()) {
					c__debut_date = "";
				} else {
					Date date = simpledf.parse(c__debut_date);
					c__debut_date = df.format(date);
				}
				String onlineFlag = fetchOnlineFlag(product, "SURPRISE");
				String onlineFrom = fetchOnlineFrom(product,  "SURPRISE");
				String onlineTo = fetchOnlineTo(product,  "SURPRISE");
				String searchableIfUnavailable = fetchSearchableIfUnAvailable(product, "SURPRISE");
				String c__onlineExclusive = extractAttributeFromCustomAttribute("onlineExclusive", product, true);
				String c__productVideoURL = extractAttributeFromCustomAttribute("productVideoURL", product, false);
				vPlumSliceItemDetailsForFeed.setOnileFlag_kss(onlineFlag);
				vPlumSliceItemDetailsForFeed.setOnileFrom_kss(onlineFrom);
				vPlumSliceItemDetailsForFeed.setOnileTo_kss(onlineTo);
				vPlumSliceItemDetailsForFeed.setSearchableIfUnavaialble_kss(searchableIfUnavailable);
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
				String c__debut_date = extractAttributeFromCustomAttribute("debut-date", product, false);
				Matcher m = p.matcher(c__debut_date);
				if (!m.matches()) {
					c__debut_date = "";
				} else {
					Date date = simpledf.parse(c__debut_date);
					c__debut_date = df.format(date);
				}
				String onlineFlag = fetchOnlineFlag(product, "KATESPADE");
				String onlineFrom = fetchOnlineFrom(product,  "KATESPADE");
				String onlineTo = fetchOnlineTo(product,  "KATESPADE");
				String searchableIfUnavailable = fetchSearchableIfUnAvailable(product, "KATESPADE");
				String c__onlineExclusive = extractAttributeFromCustomAttribute("onlineExclusive", product, true);
				String c__productVideoURL = extractAttributeFromCustomAttribute("productVideoURL", product, false);
				vPlumSliceItemDetailsForFeed.setOnileFlag_ks(onlineFlag);
				vPlumSliceItemDetailsForFeed.setOnileFrom_ks(onlineFrom);
				vPlumSliceItemDetailsForFeed.setOnileTo_ks(onlineTo);
				vPlumSliceItemDetailsForFeed.setSearchableIfUnavaialble_ks(searchableIfUnavailable);
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
	private static String fetchOnlineFlag(ComplexTypeProduct product, String brand) {

		List<SharedTypeSiteSpecificBoolean> vSharedTypeSiteSpecificBoolean = product.getOnlineFlag();
		boolean isOnlineFlagDefault = false;
		boolean isOnlineFlag = false;
		boolean isShopValueExists = false;
		boolean isDefaultExists = false;
		for (SharedTypeSiteSpecificBoolean pSharedTypeSiteSpecificBooleanVal : vSharedTypeSiteSpecificBoolean) {
			if (pSharedTypeSiteSpecificBooleanVal.getSiteId() == null
					|| (pSharedTypeSiteSpecificBooleanVal.getSiteId() != null
							&& "".equals(pSharedTypeSiteSpecificBooleanVal.getSiteId()))) {
				isOnlineFlagDefault = pSharedTypeSiteSpecificBooleanVal.isValue();
				isDefaultExists = true;
			}
			if ("KATESPADE".equals(brand)) {
				if (pSharedTypeSiteSpecificBooleanVal.getSiteId() != null
						&& "Shop".equalsIgnoreCase(pSharedTypeSiteSpecificBooleanVal.getSiteId())) {
					isOnlineFlag = pSharedTypeSiteSpecificBooleanVal.isValue();
					isShopValueExists = true;
				}
			}
			if ("SURPRISE".equals(brand)) {
				if (pSharedTypeSiteSpecificBooleanVal.getSiteId() != null
						&& "KateSale".equalsIgnoreCase(pSharedTypeSiteSpecificBooleanVal.getSiteId())) {
					isOnlineFlag = pSharedTypeSiteSpecificBooleanVal.isValue();
					isShopValueExists = true;
				}
			}
		}
		if (isShopValueExists) {
			if (isOnlineFlag) {
				return "Y";
			} else {
				return "N";
			}

		} else if (!isShopValueExists && isDefaultExists) {
			if (isOnlineFlagDefault) {
				return "Y";
			} else {
				return "N";
			}
		} else {
			return "";
		}
	}
	private static String fetchOnlineTo(ComplexTypeProduct product, String brand) {
		List<SharedTypeSiteSpecificDateTime> vSharedTypeSiteSpecificDateTime = product.getOnlineTo();
		String onlineTo = "";
		String onlineToDefault = "";
		boolean isShopValueExists = false;
		for (SharedTypeSiteSpecificDateTime pSharedTypeSiteSpecificDateTime : vSharedTypeSiteSpecificDateTime) {
			if (pSharedTypeSiteSpecificDateTime.getSiteId() == null
					|| (pSharedTypeSiteSpecificDateTime.getSiteId() != null
							&& "".equals(pSharedTypeSiteSpecificDateTime.getSiteId()))) {
				onlineToDefault = pSharedTypeSiteSpecificDateTime.getValue().toString();
			}
			if ("KATESPADE".equals(brand)) {
				if (pSharedTypeSiteSpecificDateTime.getSiteId() != null
						&& "Shop".equalsIgnoreCase(pSharedTypeSiteSpecificDateTime.getSiteId())) {
					onlineTo = pSharedTypeSiteSpecificDateTime.getValue().toString();
					isShopValueExists = true;
				}
			}
			if ("SURPRISE".equals(brand)) {
				if (pSharedTypeSiteSpecificDateTime.getSiteId() != null
						&& "KateSale".equalsIgnoreCase(pSharedTypeSiteSpecificDateTime.getSiteId())) {
					onlineTo = pSharedTypeSiteSpecificDateTime.getValue().toString();
					isShopValueExists = true;
				}
			}
		}
		if (isShopValueExists) {
			return onlineTo;
		} else if (!isShopValueExists) {
			return onlineToDefault;
		} else {
			return "";
		}
	}

	private static String fetchOnlineFrom(ComplexTypeProduct product,  String brand) {
		List<SharedTypeSiteSpecificDateTime> vSharedTypeSiteSpecificDateTime = product.getOnlineFrom();
		String onlineFrom = "";
		String onlineFromDefault = "";
		boolean isShopValueExists = false;
		for (SharedTypeSiteSpecificDateTime pSharedTypeSiteSpecificDateTime : vSharedTypeSiteSpecificDateTime) {
			if (pSharedTypeSiteSpecificDateTime.getSiteId() == null
					|| (pSharedTypeSiteSpecificDateTime.getSiteId() != null
							&& "".equals(pSharedTypeSiteSpecificDateTime.getSiteId()))) {
				onlineFromDefault = pSharedTypeSiteSpecificDateTime.getValue().toString();
			}

			if ("KATESPADE".equals(brand)) {
				if (pSharedTypeSiteSpecificDateTime.getSiteId() != null
						&& "Shop".equalsIgnoreCase(pSharedTypeSiteSpecificDateTime.getSiteId())) {
					onlineFrom = pSharedTypeSiteSpecificDateTime.getValue().toString();
					isShopValueExists = true;
				}
			}
			if ("SURPRISE".equals(brand)) {
				if (pSharedTypeSiteSpecificDateTime.getSiteId() != null
						&& "KateSale".equalsIgnoreCase(pSharedTypeSiteSpecificDateTime.getSiteId())) {
					onlineFrom = pSharedTypeSiteSpecificDateTime.getValue().toString();
					isShopValueExists = true;
				}
			}
		}
		if (isShopValueExists) {
			return onlineFrom;
		} else if (!isShopValueExists) {
			return onlineFromDefault;
		} else {
			return "";
		}
	}
	private static String fetchSearchableIfUnAvailable(ComplexTypeProduct product, 
			String brand) {
		List<SharedTypeSiteSpecificBoolean> vSharedTypeSiteSpecificBoolean = product.getSearchableIfUnavailableFlag();
		boolean isSearchableIfUnAvailableFlagDefault = false;
		boolean isSearchableIfUnAvailableFlag = false;
		boolean isShopValueExists = false;
		boolean isDefaultExists = false;
		for (SharedTypeSiteSpecificBoolean pSharedTypeSiteSpecificBooleanVal : vSharedTypeSiteSpecificBoolean) {
			if (pSharedTypeSiteSpecificBooleanVal.getSiteId() == null
					|| (pSharedTypeSiteSpecificBooleanVal.getSiteId() != null
							&& "".equals(pSharedTypeSiteSpecificBooleanVal.getSiteId()))) {
				isSearchableIfUnAvailableFlagDefault = pSharedTypeSiteSpecificBooleanVal.isValue();
				isDefaultExists = true;
			}
			if ("KATESPADE".equals(brand)) {
				if (pSharedTypeSiteSpecificBooleanVal.getSiteId() != null
						&& "Shop".equalsIgnoreCase(pSharedTypeSiteSpecificBooleanVal.getSiteId())) {
					isSearchableIfUnAvailableFlag = pSharedTypeSiteSpecificBooleanVal.isValue();
					isShopValueExists = true;
				}
			}

			if ("SURPRISE".equals(brand)) {
				if (pSharedTypeSiteSpecificBooleanVal.getSiteId() != null
						&& "KateSale".equalsIgnoreCase(pSharedTypeSiteSpecificBooleanVal.getSiteId())) {
					isSearchableIfUnAvailableFlag = pSharedTypeSiteSpecificBooleanVal.isValue();
					isShopValueExists = true;
				}
			}
		}
		if (isShopValueExists) {
			if (isSearchableIfUnAvailableFlag) {
				return "Y";
			} else {
				return "";
			}

		} else if (!isShopValueExists && isDefaultExists) {
			if (isSearchableIfUnAvailableFlagDefault) {
				return "Y";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
}
