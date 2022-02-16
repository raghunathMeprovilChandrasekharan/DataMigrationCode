package com.datamigration.mainclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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
import com.datamigration.javaclass.SharedTypeSiteSpecificBoolean;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttribute;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttributes;
import com.datamigration.javaclass.SharedTypeSiteSpecificDateTime;
import com.datamigration.pojo.CategoryProductRelation;
import com.datamigration.pojo.PlumSliceProduct;
import com.datamigration.pojo.ProductData;
import com.sun.org.apache.xpath.internal.operations.Equals;

public class PlumSliceProductDetailsTemplate {
	private static String VARIATION_MASTER = "Variation-Master";
	private static String VARIATION = "Variation";
	private static String SKU = "SKU";
	static ExtractMainProperties ext = new ExtractMainProperties();
	static HashMap<String, PlumSliceProduct> MasterProuct_DB = new HashMap<String, PlumSliceProduct>();
	static HashMap<String, ProductData> prouct_DB = new HashMap<String, ProductData>();
	static List<String> masterListDB = new ArrayList<String>();
	private static List<CategoryProductRelation> vCategoryProductRelationList = new ArrayList<CategoryProductRelation>();
	static HashMap<String, PlumSliceProduct> masterListPlusliceMainDB = new HashMap<String, PlumSliceProduct>();
	static HashMap<String, List<PlumSliceProduct>> variationListPlusliceMainDB = new HashMap<String, List<PlumSliceProduct>>();

	static HashMap<String, String> completedProducts = new HashMap<String, String>();
	static HashMap<String, List<PlumSliceProduct>> variationListMap = new HashMap<String, List<PlumSliceProduct>>();
	static HashMap<String, List<PlumSliceProduct>> masterListMap = new HashMap<String, List<PlumSliceProduct>>();
	static HashMap<String, String> productTypeList = new HashMap<String, String>();
	static List<PlumSliceProduct> plumsliceExatrct = new ArrayList<PlumSliceProduct>();
	static List<List<PlumSliceProduct>> MainExtractForPlumSlice = new ArrayList<List<PlumSliceProduct>>();
	static String regex = "[0-9]+";
	static DateFormat simpledf = new SimpleDateFormat("yyyyMMdd");
	static DateFormat templateDateFOrmat = new SimpleDateFormat("MM-dd-yyyy");
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	static Pattern p = Pattern.compile(regex);


	public static void main(String[] args) throws IOException, JAXBException, ParseException {

		ExtractMasterData extMaster = new ExtractMasterData();
		extMaster.executeLoad();
		vCategoryProductRelationList = extMaster.getvCategoryProductRelationList();

		PopulateMainDB mainDB = new PopulateMainDB();
		mainDB.populateDB();
		prouct_DB = mainDB.getProuct_DB();
		populateCategoryToProductRelationForItems();
		// loadDeltaDataForTemp();

		// Integer chunkSize = (int)
		// Math.ceil(prouct_DB.size()/Integer.valueOf(ext.getPropValue("no_of_splits")));
		File file = new File(ext.getPropValue("master-catalog-path"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		String productIdToCompare = "";
		for (ComplexTypeProduct product : que.getProduct()) {
			productIdToCompare = product.getProductId();
			if (productIdToCompare.indexOf("-") > -1) {
				productIdToCompare = product.getProductId().split("-")[0];
			}
			if (!("#N/A").equals(product.getProductId())) {
				if (!completedProducts.containsKey(product.getProductId())) {
					ProductData tmpPrdDta = prouct_DB.get(product.getProductId());
					if (tmpPrdDta == null) {
						// System.out.println(product.getProductId()+">>>>>>>>>>>> is Not valid");
						continue;
					}
					String c_itemType = extractAttributeFromCustomAttribute("ItemType", product, false);

					// String c__VideoThumbPosition =
					// extractAttributeFromCustomAttribute("VideoThumbPosition",product,false);
					String c__debut_date = extractAttributeFromCustomAttribute("debut-date", product, false);
					Matcher m = p.matcher(c__debut_date);
					if (!m.matches()) {
						c__debut_date = "";
					} else {

						Date date = simpledf.parse(c__debut_date);
						c__debut_date = df.format(date);
						// Calendar cal = Calendar.getInstance();
						// cal.setTime(date);

						// c__debut_date
					}
					String c__isNew = extractAttributeFromCustomAttribute("isNew", product, true);
					String c__onlineExclusive = extractAttributeFromCustomAttribute("onlineExclusive", product, true);
					String c__productVideoURL = extractAttributeFromCustomAttribute("productVideoURL", product, false);

					String onlineFlag = fetchOnlineFlag(product, tmpPrdDta, "KATESPADE");
					String onlineFrom = fetchOnlineFrom(product, tmpPrdDta, "KATESPADE");
					String onlineTo = fetchOnlineTo(product, tmpPrdDta, "KATESPADE");
					String searchable = fetchSearchable(product, tmpPrdDta, "KATESPADE");
					String searchableIfUnavailable = fetchSearchableIfUnAvailable(product, tmpPrdDta, "KATESPADE");
					String onlineFlag_surprise = fetchOnlineFlag(product, tmpPrdDta, "SURPRISE");
					String onlineFrom_surprise = fetchOnlineFrom(product, tmpPrdDta, "SURPRISE");
					String onlineTo_surprise = fetchOnlineTo(product, tmpPrdDta, "SURPRISE");
					String searchable_surprise = fetchSearchable(product, tmpPrdDta, "SURPRISE");
					String searchableIfUnavailable_surprise = fetchSearchableIfUnAvailable(product, tmpPrdDta,
							"SURPRISE");

					productTypeList.put(c_itemType, c_itemType);
					PlumSliceProduct plumSliceProduct = new PlumSliceProduct();
					if (tmpPrdDta.isMaster()) {
						PlumSliceProduct plumSliceProductMaster = new PlumSliceProduct();
						plumSliceProductMaster.setItemCategory(VARIATION_MASTER);
						plumSliceProductMaster.setSkuId("");
						plumSliceProductMaster.setVariationId("");
						plumSliceProductMaster.setMasterId(tmpPrdDta.getSkuNumber());
						plumSliceProductMaster.setProductType(c_itemType);
						plumSliceProductMaster.setProuctVideo(c__productVideoURL);
						plumSliceProductMaster.setFirstPublishDate(c__debut_date);
						plumSliceProductMaster.setIsNewArrival(c__isNew);
						plumSliceProductMaster.setIsExclusive(c__onlineExclusive);
						plumSliceProductMaster.setOnlineFlag_kate(onlineFlag);
						plumSliceProductMaster.setOnlineFrom_kate(onlineFrom);
						plumSliceProductMaster.setOnlineTo_kate(onlineTo);
						plumSliceProductMaster.setSearchableFlag_kate(searchable);
						plumSliceProductMaster.setSearchableifUnavailable_kate(searchableIfUnavailable);
						plumSliceProductMaster.setOnlineFlag_surprise(onlineFlag_surprise);
						plumSliceProductMaster.setOnlineFrom_surprise(onlineFrom_surprise);
						plumSliceProductMaster.setOnlineTo_surprise(onlineTo_surprise);
						plumSliceProductMaster.setSearchableFlag_surprise(searchable_surprise);
						plumSliceProductMaster.setSearchableifUnavailable_surprise(searchableIfUnavailable_surprise);
						plumSliceProductMaster.setAssociatedCatalog(tmpPrdDta.getBrand());
						MasterProuct_DB.put(tmpPrdDta.getSkuNumber(), plumSliceProductMaster);
					} else {
						plumSliceProduct.setItemCategory(SKU);
						plumSliceProduct.setSkuId(tmpPrdDta.getSkuNumber());
						plumSliceProduct.setMasterId(tmpPrdDta.getMasterId());
						plumSliceProduct.setVariationId(tmpPrdDta.getVariationId());
						plumSliceProduct.setProductType(c_itemType);
						plumSliceProduct.setProuctVideo(c__productVideoURL);
						plumSliceProduct.setFirstPublishDate(c__debut_date);
						plumSliceProduct.setIsNewArrival(c__isNew);
						plumSliceProduct.setIsExclusive(c__onlineExclusive);
						plumSliceProduct.setOnlineFlag_kate(onlineFlag);
						plumSliceProduct.setOnlineFrom_kate(onlineFrom);
						plumSliceProduct.setOnlineTo_kate(onlineTo);
						plumSliceProduct.setSearchableFlag_kate(searchable);
						plumSliceProduct.setSearchableifUnavailable_kate(searchableIfUnavailable);
						plumSliceProduct.setOnlineFlag_surprise(onlineFlag_surprise);
						plumSliceProduct.setOnlineFrom_surprise(onlineFrom_surprise);
						plumSliceProduct.setOnlineTo_surprise(onlineTo_surprise);
						plumSliceProduct.setSearchableFlag_surprise(searchable_surprise);
						plumSliceProduct.setSearchableifUnavailable_surprise(searchableIfUnavailable_surprise);
						plumSliceProduct.setAssociatedCatalog(tmpPrdDta.getBrand());
						PlumSliceProduct plumSliceProductVariant = new PlumSliceProduct();
						plumSliceProductVariant.setItemCategory(VARIATION);
						plumSliceProductVariant.setMasterId(tmpPrdDta.getMasterId());
						plumSliceProductVariant.setSkuId("");
						plumSliceProductVariant.setVariationId(tmpPrdDta.getVariationId());
						plumSliceProductVariant.setFirstPublishDate(c__debut_date);
						plumSliceProductVariant.setIsNewArrival(c__isNew);
						plumSliceProductVariant.setIsExclusive(c__onlineExclusive);
						plumSliceProductVariant.setProuctVideo(c__productVideoURL);
						plumSliceProductVariant.setProductType(c_itemType);
						plumSliceProductVariant.setOnlineFlag_kate(onlineFlag);
						plumSliceProductVariant.setOnlineFrom_kate(onlineFrom);
						plumSliceProductVariant.setOnlineTo_kate(onlineTo);
						plumSliceProductVariant.setSearchableFlag_kate(searchable);
						plumSliceProductVariant.setSearchableifUnavailable_kate(searchableIfUnavailable);
						plumSliceProductVariant.setOnlineFlag_surprise(onlineFlag_surprise);
						plumSliceProductVariant.setOnlineFrom_surprise(onlineFrom_surprise);
						plumSliceProductVariant.setOnlineTo_surprise(onlineTo_surprise);
						plumSliceProductVariant.setSearchableFlag_surprise(searchable_surprise);
						plumSliceProductVariant.setSearchableifUnavailable_surprise(searchableIfUnavailable_surprise);
						plumSliceProductVariant.setAssociatedCatalog(tmpPrdDta.getBrand());
						if (!variationListMap.containsKey(tmpPrdDta.getVariationId())) {

							List<PlumSliceProduct> PlumSliceProductList = new ArrayList<PlumSliceProduct>();
							PlumSliceProductList.add(plumSliceProductVariant);
							variationListMap.put(tmpPrdDta.getVariationId(), PlumSliceProductList);
							// completedProducts.put(tmpPrdDta.getVariationId(), "added");
							// plumsliceExatrct.add(plumSliceProductVariant);
						} else {
							List<PlumSliceProduct> PlumSliceProductList = variationListMap
									.get(tmpPrdDta.getVariationId());
							PlumSliceProductList.add(plumSliceProductVariant);
							variationListMap.put(tmpPrdDta.getVariationId(), PlumSliceProductList);

						}
						if (!masterListMap.containsKey(tmpPrdDta.getMasterId())) {

							List<PlumSliceProduct> PlumSliceProductList = new ArrayList<PlumSliceProduct>();
							PlumSliceProductList.add(plumSliceProductVariant);
							masterListMap.put(tmpPrdDta.getMasterId(), PlumSliceProductList);
							// completedProducts.put(tmpPrdDta.getVariationId(), "added");
							// plumsliceExatrct.add(plumSliceProductVariant);
						} else {
							List<PlumSliceProduct> PlumSliceProductList = masterListMap.get(tmpPrdDta.getMasterId());
							PlumSliceProductList.add(plumSliceProductVariant);
							masterListMap.put(tmpPrdDta.getMasterId(), PlumSliceProductList);

						}
						plumsliceExatrct.add(plumSliceProduct);
					}

					/*
					 * if(chunkSize < plumsliceExatrct.size()){
					 * MainExtractForPlumSlice.add(plumsliceExatrct); plumsliceExatrct = new
					 * ArrayList<PlumSliceProduct>(); }
					 */
					completedProducts.put(product.getProductId(), "added");
				}
			}
		}

		// System.out.println(MainExtractForPlumSlice);
		// MainExtractForPlumSlice.add(plumsliceExatrct);

		// System.out.println(productTypeList.keySet());
		// writeInExcel();

		extractMasterLevelProduct();
		extractVariationLevelProducts();
		for (PlumSliceProduct dataToInsert : plumsliceExatrct) {
			if (!masterListDB.contains(dataToInsert.getMasterId().trim())) {
				masterListDB.add(dataToInsert.getMasterId().trim());
			}
			if (VARIATION_MASTER.equals(dataToInsert.getItemCategory())) {
				masterListPlusliceMainDB.put(dataToInsert.getMasterId(), dataToInsert);
			} else if (VARIATION.equals(dataToInsert.getItemCategory())) {
				if (variationListPlusliceMainDB.containsKey(dataToInsert.getMasterId().trim())) {
					variationListPlusliceMainDB.get(dataToInsert.getMasterId().trim()).add(dataToInsert);
				} else {
					List<PlumSliceProduct> tempPlumSliceProductList = new ArrayList<PlumSliceProduct>();
					tempPlumSliceProductList.add(dataToInsert);
					variationListPlusliceMainDB.put(dataToInsert.getMasterId().trim(), tempPlumSliceProductList);
				}

			}

		}
		/**
		 * 
		 * masterListDB = new ArrayList<String>(); masterListDB.add("PXRUA250");
		 * masterListDB.add("S287693GG"); masterListDB.add("S287693GF");
		 * masterListDB.add("LP3192KS"); masterListDB.add("H6RU0549");
		 * masterListDB.add("LP4286KS"); masterListDB.add("O0RU2192");
		 * masterListDB.add("O0RU2194"); masterListDB.add("OUMU0894");
		 * masterListDB.add("SP2126KS"); masterListDB.add("WKRU3540");
		 * masterListDB.add("WKRU5160"); masterListDB.add("WKRU5217");
		 * masterListDB.add("WKRU5218");
		 * 
		 * masterListDB.add("WKRU5265"); masterListDB.add("WKRU5279");
		 * masterListDB.add("WLRU4802"); masterListDB.add("WLRU4804");
		 * 
		 * 
		 * 
		 * 
		 **/

		Collections.sort(masterListDB);
		// System.out.println(masterListDB.size()/Integer.valueOf(ext.getPropValue("no_of_splits")));
		List<List<String>> output = ListUtils.partition(masterListDB,
				Integer.valueOf(ext.getPropValue("no_of_splits")));
		// FileUtils.copyFile(new
		// File(ext.getPropValue("plumslice_master_template_mastercopy")), new
		// File(ext.getPropValue("plumslice_master_template_sampler").replaceAll("<<Iteration>>",
		// "22")));
		performTransformation(output);

		// writeMasterCsv();
		// writeMasterVariationCsv();
		System.out.println("FINISH..........................");
		// System.out.println(plumsliceExatrct.size());
	}

	private static void populateCategoryToProductRelationForItems() throws NumberFormatException, IOException {
	//	vCategoryProductRelationList
		//prouct_DB
		
		List<List<CategoryProductRelation>> output = ListUtils.partition(vCategoryProductRelationList,Integer.valueOf(ext.getPropValue("no_of_splits")));
		int count = 0;
		for (List<CategoryProductRelation> delta : output) {
			File masterSamplerFile = new File(ext.getPropValue("plumslice_master_categoryrelation_template_sampler")
					.replaceAll("<<Iteration>>", String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("plumslice_master_categoryrelation_template_mastercopy")), masterSamplerFile);
			File variationSamplerFile = new File(ext.getPropValue("plumslice_variation_categoryrelation_template_sampler")
					.replaceAll("<<Iteration>>", String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("plumslice_variation_categoryrelation_template_mastercopy")),
					variationSamplerFile);
			FileInputStream masterInputStream = new FileInputStream(masterSamplerFile);
			Workbook masterWorkbook = WorkbookFactory.create(masterInputStream);
			Sheet masterSheet = masterWorkbook.getSheetAt(0);
			int masterRowCount = masterSheet.getLastRowNum();

			FileInputStream variationInputStream = new FileInputStream(variationSamplerFile);
			Workbook variationWorkbook = WorkbookFactory.create(variationInputStream);
			Sheet variationSheet = variationWorkbook.getSheetAt(0);
			int variationRowCount = variationSheet.getLastRowNum();
			for (CategoryProductRelation vCategoryProductRelation : delta) {
				if(vCategoryProductRelation.isMaster()){
					Row masterRow = masterSheet.createRow(++masterRowCount);
					int columnCount = 0;
					Cell cell = masterRow.createCell(columnCount);
					cell.setCellValue(vCategoryProductRelation.getMasterId());
					columnCount++;
					cell = masterRow.createCell(columnCount);
					cell.setCellValue(vCategoryProductRelation.getBrand());
					columnCount++;
					cell = masterRow.createCell(columnCount);
					if(("KSUSRT").equals(vCategoryProductRelation.getBrand())){
						cell.setCellValue(vCategoryProductRelation.getCategoryId());
					}else {
						cell.setCellValue("");
						
					}
					columnCount++;
					cell = masterRow.createCell(columnCount);
					if(("KSUSSUR").equals(vCategoryProductRelation.getBrand())) {
						cell.setCellValue(vCategoryProductRelation.getCategoryId());
					}else {						
						cell.setCellValue("");
					}
				}else {
					Row variatonRow = variationSheet.createRow(++variationRowCount);
					int variationColumnCount = 0;
					Cell cell = variatonRow.createCell(variationColumnCount);
					cell.setCellValue(vCategoryProductRelation.getMasterId());
					variationColumnCount++;
					cell = variatonRow.createCell(variationColumnCount);
					cell.setCellValue(vCategoryProductRelation.getBrand());
					variationColumnCount++;
					cell = variatonRow.createCell(variationColumnCount);
					cell.setCellValue(vCategoryProductRelation.getVariationId());
					variationColumnCount++;
					cell = variatonRow.createCell(variationColumnCount);
					cell.setCellValue(vCategoryProductRelation.getBrand());
					variationColumnCount++;
					cell = variatonRow.createCell(variationColumnCount);
					if(("KSUSRT").equals(vCategoryProductRelation.getBrand())){
						cell.setCellValue(vCategoryProductRelation.getCategoryId());
					}else {
						cell.setCellValue("");
					}
					variationColumnCount++;
					cell = variatonRow.createCell(variationColumnCount);
					if(("KSUSSUR").equals(vCategoryProductRelation.getBrand())) {
						cell.setCellValue(vCategoryProductRelation.getCategoryId());
					}else {						
						cell.setCellValue("");
					}
				}
			}
			variationInputStream.close();
			FileOutputStream variationoutputStream = new FileOutputStream(variationSamplerFile);
			variationWorkbook.write(variationoutputStream);
			variationWorkbook.close();
			variationoutputStream.close();
			masterInputStream.close();
			FileOutputStream outputStream = new FileOutputStream(masterSamplerFile);
			masterWorkbook.write(outputStream);
			masterWorkbook.close();
			outputStream.close();
			count++;
		}
	}

	private static void performTransformation(List<List<String>> output) throws IOException, ParseException {
		int count = 0;
		for (List<String> delta : output) {
			File masterSamplerFile = new File(ext.getPropValue("plumslice_master_template_sampler")
					.replaceAll("<<Iteration>>", String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("plumslice_master_template_mastercopy")), masterSamplerFile);
			File variationSamplerFile = new File(ext.getPropValue("plumslice_variation_template_sampler")
					.replaceAll("<<Iteration>>", String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("plumslice_variation_template_mastercopy")),
					variationSamplerFile);

			FileInputStream masterInputStream = new FileInputStream(masterSamplerFile);
			Workbook masterWorkbook = WorkbookFactory.create(masterInputStream);
			Sheet masterSheet = masterWorkbook.getSheetAt(0);
			int masterRowCount = masterSheet.getLastRowNum();

			FileInputStream variationInputStream = new FileInputStream(variationSamplerFile);
			Workbook variationWorkbook = WorkbookFactory.create(variationInputStream);
			Sheet variationSheet = variationWorkbook.getSheetAt(0);
			int variationRowCount = variationSheet.getLastRowNum();

			for (String masterId : delta) {
				Row masterRow = masterSheet.createRow(++masterRowCount);
				int columnCount = 0;
				PlumSliceProduct dataToUpdate = masterListPlusliceMainDB.get(masterId);
				if (dataToUpdate == null) {
					System.out.println(masterId);
					continue;
				}
				Cell cell = masterRow.createCell(columnCount);
				cell.setCellValue(dataToUpdate.getMasterId());
				columnCount++;
				if ("KATESPADE".equals(dataToUpdate.getAssociatedCatalog())
						|| "BOTH".equals(dataToUpdate.getAssociatedCatalog())) {
					cell = masterRow.createCell(columnCount);
					cell.setCellValue(formatDateForTemplate(dataToUpdate.getFirstPublishDate()));
				} else {
					cell = masterRow.createCell(columnCount);
					cell.setCellValue("");
				}
				columnCount++;
				if ("SURPRISE".equals(dataToUpdate.getAssociatedCatalog())
						|| "BOTH".equals(dataToUpdate.getAssociatedCatalog())) {
					cell = masterRow.createCell(columnCount);
					cell.setCellValue(formatDateForTemplate(dataToUpdate.getFirstPublishDate()));
				} else {
					cell = masterRow.createCell(columnCount);
					cell.setCellValue("");
				}
				columnCount++;
				if ("KATESPADE".equals(dataToUpdate.getAssociatedCatalog())
						|| "BOTH".equals(dataToUpdate.getAssociatedCatalog())) {
					cell = masterRow.createCell(columnCount);
					cell.setCellValue(dataToUpdate.getIsExclusive());
				} else {
					cell = masterRow.createCell(columnCount);
					cell.setCellValue("");
				}
				columnCount++;
				if ("SURPRISE".equals(dataToUpdate.getAssociatedCatalog())
						|| "BOTH".equals(dataToUpdate.getAssociatedCatalog())) {
					cell = masterRow.createCell(columnCount);
					cell.setCellValue(dataToUpdate.getIsExclusive());
				} else {
					cell = masterRow.createCell(columnCount);
					cell.setCellValue("");
				}
				columnCount++;
				if ("KATESPADE".equals(dataToUpdate.getAssociatedCatalog())
						|| "BOTH".equals(dataToUpdate.getAssociatedCatalog())) {
					cell = masterRow.createCell(columnCount);
					cell.setCellValue(dataToUpdate.getProuctVideo());
				} else {
					cell = masterRow.createCell(columnCount);
					cell.setCellValue("");
				}
				columnCount++;
				if ("SURPRISE".equals(dataToUpdate.getAssociatedCatalog())
						|| "BOTH".equals(dataToUpdate.getAssociatedCatalog())) {
					cell = masterRow.createCell(columnCount);
					cell.setCellValue(dataToUpdate.getProuctVideo());
				} else {
					cell = masterRow.createCell(columnCount);
					cell.setCellValue("");
				}

				List<PlumSliceProduct> datasToUpdate = variationListPlusliceMainDB.get(masterId);

				if (datasToUpdate != null) {
					for (PlumSliceProduct variationToUpdate : datasToUpdate) {
						Row variatonRow = variationSheet.createRow(++variationRowCount);
						int variationColumnCount = 0;
						Cell variationCell = variatonRow.createCell(variationColumnCount);
						variationCell.setCellValue(variationToUpdate.getMasterId());
						if(variationToUpdate.getMasterId() == "1090048FU") {
							System.out.println("Ffffffffffffffffff");
						}
						variationColumnCount++;
						variationCell = variatonRow.createCell(variationColumnCount);
						variationCell.setCellValue(variationToUpdate.getVariationId());
						variationColumnCount++;
						if ("KATESPADE".equals(variationToUpdate.getAssociatedCatalog())
								|| "BOTH".equals(variationToUpdate.getAssociatedCatalog())) {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue(formatDateForTemplate(variationToUpdate.getFirstPublishDate()));
						} else {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue("");
						}
						variationColumnCount++;
						if ("SURPRISE".equals(variationToUpdate.getAssociatedCatalog())
								|| "BOTH".equals(variationToUpdate.getAssociatedCatalog())) {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue(formatDateForTemplate(variationToUpdate.getFirstPublishDate()));
						} else {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue("");
						}

						variationColumnCount++;
					
						if ("KATESPADE".equals(variationToUpdate.getAssociatedCatalog())
								|| "BOTH".equals(variationToUpdate.getAssociatedCatalog())) {
							
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue(
									"Yes".equalsIgnoreCase(variationToUpdate.getOnlineFlag_kate()) ? "Y" : "N");
						} else {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue("");
						}
						variationColumnCount++;
						if ("SURPRISE".equals(variationToUpdate.getAssociatedCatalog())
								|| "BOTH".equals(variationToUpdate.getAssociatedCatalog())) {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue(
									"Yes".equalsIgnoreCase(variationToUpdate.getOnlineFlag_surprise()) ? "Y" : "N");
						} else {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue("");
						}
						variationColumnCount++;
						if ("KATESPADE".equals(variationToUpdate.getAssociatedCatalog())
								|| "BOTH".equals(variationToUpdate.getAssociatedCatalog())) {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue(formatDateForTemplate(variationToUpdate.getOnlineFrom_kate()));
						} else {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue("");
						}
						variationColumnCount++;
						if ("SURPRISE".equals(variationToUpdate.getAssociatedCatalog())
								|| "BOTH".equals(variationToUpdate.getAssociatedCatalog())) {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell
									.setCellValue(formatDateForTemplate(variationToUpdate.getOnlineFrom_surprise()));
						} else {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue("");
						}
						variationColumnCount++;
						if ("KATESPADE".equals(variationToUpdate.getAssociatedCatalog())
								|| "BOTH".equals(variationToUpdate.getAssociatedCatalog())) {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue(formatDateForTemplate(variationToUpdate.getOnlineTo_kate()));
						} else {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue("");
						}
						variationColumnCount++;
						if ("SURPRISE".equals(variationToUpdate.getAssociatedCatalog())
								|| "BOTH".equals(variationToUpdate.getAssociatedCatalog())) {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue(formatDateForTemplate(variationToUpdate.getOnlineTo_surprise()));
						} else {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue("");
						}
						variationColumnCount++;
						if ("KATESPADE".equals(variationToUpdate.getAssociatedCatalog())
								|| "BOTH".equals(variationToUpdate.getAssociatedCatalog())) {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue(
									"Yes".equalsIgnoreCase(variationToUpdate.getSearchableifUnavailable_kate()) ? "Y"
											: "");
						} else {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue("");
						}
						variationColumnCount++;
						if ("SURPRISE".equals(variationToUpdate.getAssociatedCatalog())
								|| "BOTH".equals(variationToUpdate.getAssociatedCatalog())) {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue(
									"Yes".equalsIgnoreCase(variationToUpdate.getSearchableifUnavailable_surprise())
											? "Y"
											: "");
						} else {
							variationCell = variatonRow.createCell(variationColumnCount);
							variationCell.setCellValue("");
						}

					}
				}

			}
			variationInputStream.close();
			FileOutputStream variationoutputStream = new FileOutputStream(variationSamplerFile);
			variationWorkbook.write(variationoutputStream);
			variationWorkbook.close();
			variationoutputStream.close();
			masterInputStream.close();
			FileOutputStream outputStream = new FileOutputStream(masterSamplerFile);
			masterWorkbook.write(outputStream);
			masterWorkbook.close();
			outputStream.close();
			count++;
		}

	}

	private static void writeMasterVariationCsv() throws IOException, ParseException {
		FileWriter myWriter = new FileWriter(ext.getPropValue("plumslice_transformed_feed_template_styleVariation_csv"),
				false);
		StringBuilder rows = new StringBuilder();
		Collections.sort(plumsliceExatrct);
		rows.append(
				"\"Item#\",\"@Variation Number\",\"@First Published Date - KSUSRT\",\"@First Published Date - KSUSSUR\",\"@Online Flag - KSUSRT\",\"@Online Flag - KSUSSUR\",\"@Online From - KSUSRT\",\"@Online From - KSUSSUR\",\"@Online To - KSUSRT\",\"@Online To - KSUSSUR\",\"@Searchable If Unavailable - KSUSRT\",\"@Searchable If Unavailable - KSUSSUR\"\n");
		for (PlumSliceProduct dataToInsert : plumsliceExatrct) {
			if (VARIATION.equals(dataToInsert.getItemCategory()) && masterListDB.contains(dataToInsert.getMasterId())) {
				rows.append("\"");
				rows.append(dataToInsert.getMasterId().trim());
				rows.append("\",\"");
				rows.append(dataToInsert.getVariationId().trim());
				rows.append("\",\"");
				if ("KATESPADE".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append(formatDateForTemplate(dataToInsert.getFirstPublishDate()));
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("SURPRISE".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append(formatDateForTemplate(dataToInsert.getFirstPublishDate()));
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("KATESPADE".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append("Yes".equalsIgnoreCase(dataToInsert.getOnlineFlag_kate()) ? "Y" : "N");
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("SURPRISE".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append("Yes".equalsIgnoreCase(dataToInsert.getOnlineFlag_surprise()) ? "Y" : "N");
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("KATESPADE".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append(formatDateForTemplate(dataToInsert.getOnlineFrom_kate()));
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("SURPRISE".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append(formatDateForTemplate(dataToInsert.getOnlineFrom_surprise()));
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("KATESPADE".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append(formatDateForTemplate(dataToInsert.getOnlineTo_kate()));
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("SURPRISE".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append(formatDateForTemplate(dataToInsert.getOnlineTo_surprise()));
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("KATESPADE".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append("Yes".equalsIgnoreCase(dataToInsert.getSearchableifUnavailable_kate()) ? "Y" : "");
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("SURPRISE".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append("Yes".equalsIgnoreCase(dataToInsert.getSearchableifUnavailable_surprise()) ? "Y" : "");
					rows.append("\"\n");
				} else {
					rows.append("\"\n");
				}

			}

		}

		myWriter.write(rows.toString());
		myWriter.close();

	}

	private static void writeMasterCsv() throws IOException, ParseException {
		FileWriter myWriter = new FileWriter(ext.getPropValue("plumslice_transformed_feed_template_style_csv"), false);
		StringBuilder rows = new StringBuilder();
		Collections.sort(plumsliceExatrct);
		rows.append(
				"\"Item#\",\"First Published Date - KSUSRT\",\"First Published Date - KSUSSUR\",\"Is Exclusive - KSUSRT\",\"Is Exclusive - KSUSSUR\",\"Product Video - KSUSRT\",\"Product Video - KSUSSUR\"\n");
		for (PlumSliceProduct dataToInsert : plumsliceExatrct) {

			if (VARIATION_MASTER.equals(dataToInsert.getItemCategory())
					&& masterListDB.contains(dataToInsert.getMasterId())) {

				rows.append("\"");
				rows.append(dataToInsert.getMasterId().trim());
				rows.append("\",\"");
				if ("KATESPADE".equals(dataToInsert.getAssociatedCatalog())
						|| "BOTH".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append(formatDateForTemplate(dataToInsert.getFirstPublishDate()));
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("SURPRISE".equals(dataToInsert.getAssociatedCatalog())
						|| "BOTH".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append(formatDateForTemplate(dataToInsert.getFirstPublishDate()));
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("KATESPADE".equals(dataToInsert.getAssociatedCatalog())
						|| "BOTH".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append(dataToInsert.getIsExclusive());
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("SURPRISE".equals(dataToInsert.getAssociatedCatalog())
						|| "BOTH".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append(dataToInsert.getIsExclusive());
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}

				if ("KATESPADE".equals(dataToInsert.getAssociatedCatalog())
						|| "BOTH".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append(dataToInsert.getProuctVideo());
					rows.append("\",\"");
				} else {
					rows.append("\",\"");
				}
				if ("SURPRISE".equals(dataToInsert.getAssociatedCatalog())
						|| "BOTH".equals(dataToInsert.getAssociatedCatalog())) {
					rows.append(dataToInsert.getProuctVideo());
					rows.append("\"\n");
				} else {
					rows.append("\"\n");
				}

			}

		}

		myWriter.write(rows.toString());
		myWriter.close();

	}

	private static String formatDateForTemplate(String firstPublishDate) throws ParseException {
		if (firstPublishDate.isBlank()) {
			return "";
		}
		return templateDateFOrmat.format(df.parse(firstPublishDate));
	}

	private static void loadDeltaDataForTemp() throws IOException {
		File file = new File(ext.getPropValue("delta-file-to-include"));
		Scanner myReader = new Scanner(file);
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			data = data.trim();
			if (!masterListDB.contains(data)) {
				masterListDB.add(data);
			}
		}
		myReader.close();

	}

	private static void extractMasterLevelProduct() throws ParseException {

		for (String masterId : masterListMap.keySet()) {

			List<PlumSliceProduct> skus = masterListMap.get(masterId);
			PlumSliceProduct plumSliceProduct = new PlumSliceProduct();
			PlumSliceProduct masterVo = MasterProuct_DB.get(masterId);
			plumSliceProduct.setSkuId("");
			plumSliceProduct.setMasterId(masterId);
			plumSliceProduct.setVariationId("");
			plumSliceProduct.setItemCategory(VARIATION_MASTER);
			plumSliceProduct.setFirstPublishDate(masterVo != null ? masterVo.getFirstPublishDate() : "");
			plumSliceProduct.setSearchableifUnavailable_kate(
					masterVo != null ? masterVo.getSearchableifUnavailable_kate() : "");
			plumSliceProduct.setSearchableFlag_kate(masterVo != null ? masterVo.getSearchableFlag_kate() : "");
			plumSliceProduct.setSearchableifUnavailable_surprise(
					masterVo != null ? masterVo.getSearchableifUnavailable_surprise() : "");
			plumSliceProduct.setSearchableFlag_surprise(masterVo != null ? masterVo.getSearchableFlag_surprise() : "");
			plumSliceProduct.setAssociatedCatalog(masterVo != null ? masterVo.getAssociatedCatalog() : "");
			plumSliceProduct.setProductType(masterVo != null ? masterVo.getProductType() : "");
			plumSliceProduct.setProuctVideo(masterVo != null ? masterVo.getProuctVideo() : "");
			plumSliceProduct.setIsExclusive(masterVo != null ? masterVo.getIsExclusive() : "");
			String isExclusive = "";
			String isNewVal = "";
			String onlineFlag_kate = "";
			String onlineFrom_kate = "";
			String isSearchableFlag_kate = "";
			String isSearchableifUnavailable_kate = "";
			String onlineFlag_surprise = "";
			String onlineFrom_surprise = "";
			String isSearchableFlag_surprise = "";
			String isSearchableifUnavailable_surprise = "";
			String isProductType = "";
			String prodctVedio = "";
			String AssociatedCatalog = "";
			Calendar onlineFromCal_kate = new GregorianCalendar();
			Calendar onlineFromCal_surprise = new GregorianCalendar();
			String onlineTo_kate = "";
			String onlineTo_surprise = "";
			Calendar onlineToCal_kate = new GregorianCalendar();

			Calendar onlineToCal_surprise = new GregorianCalendar();
			boolean onlineToCal_kate_set = false;
			boolean onlineToCal_surprise_set = false;
			boolean onlineFromCal_kate_set = false;
			boolean onlineFromCal_surprise_set = false;

			for (PlumSliceProduct sku : skus) {
				if ("Yes".equals(sku.getIsNewArrival())) {
					isNewVal = "Yes";
				}
				if ("Yes".equals(sku.getOnlineFlag_kate())) {
					onlineFlag_kate = "Yes";
				}
				if ("Yes".equals(sku.getOnlineFlag_surprise())) {
					onlineFlag_surprise = "Yes";
				}
				if (!sku.getOnlineFrom_kate().isBlank()) {

					if (!onlineFromCal_kate_set) {
						onlineFromCal_kate = convertToDate(sku.getOnlineFrom_kate());
						onlineFromCal_kate_set = true;
					}

					if (convertToDate(sku.getOnlineFrom_kate()).before(onlineFromCal_kate) || convertToDate(sku.getOnlineFrom_kate()).equals(onlineFromCal_kate) ) {
						onlineFrom_kate = sku.getOnlineFrom_kate();
						onlineFromCal_kate = convertToDate(sku.getOnlineFrom_kate());
					}

				}
				if (!sku.getOnlineFrom_surprise().isBlank()) {
					if (!onlineFromCal_surprise_set) {
						onlineFromCal_surprise = convertToDate(sku.getOnlineFrom_surprise());
						onlineFromCal_surprise_set = true;
					}

					if (convertToDate(sku.getOnlineFrom_surprise()).before(onlineFromCal_surprise) || convertToDate(sku.getOnlineFrom_surprise()).equals(onlineFromCal_surprise) ) {
						onlineFrom_surprise = sku.getOnlineFrom_surprise();
						onlineFromCal_surprise = convertToDate(sku.getOnlineFrom_surprise());
					}

				}

				if (!sku.getOnlineTo_kate().isBlank()) {
					if (!onlineToCal_kate_set) {
						onlineToCal_kate = convertToDate(sku.getOnlineTo_kate());
						onlineToCal_kate_set = true;
					}

					if (convertToDate(sku.getOnlineTo_kate()).after(onlineToCal_kate) || convertToDate(sku.getOnlineTo_kate()).equals(onlineToCal_kate)) {
						onlineTo_kate = sku.getOnlineTo_kate();
						onlineToCal_kate = convertToDate(sku.getOnlineTo_kate());
					}

				}

				if (!sku.getOnlineTo_surprise().isBlank()) {
					if (!onlineToCal_surprise_set) {
						onlineToCal_surprise = convertToDate(sku.getOnlineTo_surprise());
						onlineToCal_surprise_set = true;
					}

					if (convertToDate(sku.getOnlineTo_surprise()).after(onlineToCal_surprise) || convertToDate(sku.getOnlineTo_surprise()).equals(onlineToCal_surprise)) {
						onlineTo_surprise = sku.getOnlineTo_surprise();
						onlineToCal_surprise = convertToDate(sku.getOnlineTo_surprise());
					}

				}

				if (plumSliceProduct.getIsExclusive().isBlank()) {
					if ("Yes".equals(sku.getIsExclusive())) {
						isExclusive = "Yes";
					}
				} else {
					isExclusive = plumSliceProduct.getIsExclusive();
				}

				if (plumSliceProduct.getAssociatedCatalog().isBlank()) {
					if (!sku.getAssociatedCatalog().isBlank()) {
						AssociatedCatalog = sku.getAssociatedCatalog();
					}
				} else {
					AssociatedCatalog = plumSliceProduct.getAssociatedCatalog();
				}
				if (plumSliceProduct.getSearchableFlag_kate().isBlank()) {
					if ("Yes".equals(sku.getSearchableFlag_kate())) {
						isSearchableFlag_kate = "Yes";
					}
				} else {
					isSearchableFlag_kate = plumSliceProduct.getSearchableFlag_kate();
				}
				if (plumSliceProduct.getSearchableFlag_surprise().isBlank()) {
					if ("Yes".equals(sku.getSearchableFlag_surprise())) {
						isSearchableFlag_surprise = "Yes";
					}
				} else {
					isSearchableFlag_surprise = plumSliceProduct.getSearchableFlag_surprise();
				}

				if (plumSliceProduct.getProductType().isBlank()) {
					if (!sku.getProductType().isBlank()) {
						isProductType = sku.getProductType();
					}
				} else {
					isProductType = plumSliceProduct.getProductType();
				}
				if (plumSliceProduct.getSearchableifUnavailable_kate().isBlank()) {
					if ("Yes".equals(sku.getSearchableifUnavailable_kate())) {
						isSearchableifUnavailable_kate = "Yes";
					}
				} else {
					isSearchableifUnavailable_kate = plumSliceProduct.getSearchableifUnavailable_kate();
				}
				if (plumSliceProduct.getSearchableifUnavailable_surprise().isBlank()) {
					if ("Yes".equals(sku.getSearchableifUnavailable_surprise())) {
						isSearchableifUnavailable_surprise = "Yes";
					}
				} else {
					isSearchableifUnavailable_surprise = plumSliceProduct.getSearchableifUnavailable_surprise();
				}

				if (!sku.getProuctVideo().isBlank() && plumSliceProduct.getProuctVideo().isBlank()) {
					prodctVedio = sku.getProuctVideo();

				} else {
					prodctVedio = plumSliceProduct.getProuctVideo();
				}
			}

			plumSliceProduct.setIsExclusive(isExclusive);
			plumSliceProduct.setIsNewArrival(isNewVal);
			plumSliceProduct.setOnlineFrom_kate(onlineFrom_kate);
			plumSliceProduct.setOnlineTo_kate(onlineTo_kate);
			plumSliceProduct.setOnlineFlag_kate(onlineFlag_kate);
			plumSliceProduct.setSearchableFlag_kate(isSearchableFlag_kate);
			plumSliceProduct.setSearchableifUnavailable_kate(isSearchableifUnavailable_kate);
			plumSliceProduct.setOnlineFrom_surprise(onlineFrom_surprise);
			plumSliceProduct.setOnlineTo_surprise(onlineTo_surprise);
			plumSliceProduct.setOnlineFlag_surprise(onlineFlag_surprise);
			plumSliceProduct.setSearchableFlag_surprise(isSearchableFlag_surprise);
			plumSliceProduct.setSearchableifUnavailable_surprise(isSearchableifUnavailable_surprise);
			plumSliceProduct.setProductType(isProductType);
			plumSliceProduct.setProuctVideo(prodctVedio);
			plumSliceProduct.setAssociatedCatalog(AssociatedCatalog);
			plumsliceExatrct.add(plumSliceProduct);
		}

		for (String masterId : MasterProuct_DB.keySet()) {
			if (!masterListMap.containsKey(masterId)) {
				plumsliceExatrct.add(MasterProuct_DB.get(masterId));
			}
		}

	}

	private static void extractVariationLevelProducts() throws ParseException {
		for (String variation : variationListMap.keySet()) {
			List<PlumSliceProduct> skus = variationListMap.get(variation);
			PlumSliceProduct plumSliceProduct = new PlumSliceProduct();
			String masterId = variation.split("-")[0];
			PlumSliceProduct masterVo = MasterProuct_DB.get(masterId);
			plumSliceProduct.setSkuId("");
			plumSliceProduct.setMasterId(masterId);
			plumSliceProduct.setVariationId(variation);
			plumSliceProduct.setItemCategory(VARIATION);
			plumSliceProduct.setFirstPublishDate(masterVo != null ? masterVo.getFirstPublishDate() : "");
			plumSliceProduct.setSearchableifUnavailable_kate(
					masterVo != null ? masterVo.getSearchableifUnavailable_kate() : "");
			plumSliceProduct.setSearchableFlag_kate(masterVo != null ? masterVo.getSearchableFlag_kate() : "");
			plumSliceProduct.setSearchableifUnavailable_surprise(
					masterVo != null ? masterVo.getSearchableifUnavailable_surprise() : "");
			plumSliceProduct.setSearchableFlag_surprise(masterVo != null ? masterVo.getSearchableFlag_surprise() : "");
			plumSliceProduct.setIsExclusive(masterVo != null ? masterVo.getIsExclusive() : "");
			plumSliceProduct.setAssociatedCatalog(masterVo != null ? masterVo.getAssociatedCatalog() : "");

			String isExclusive = "";
			String isNewVal = "";
			String onlineFlag_kate = "";
			String onlineFrom_kate = "";
			String isSearchableFlag_kate = "";
			String isSearchableifUnavailable_kate = "";
			String onlineFlag_surprise = "";
			String onlineFrom_surprise = "";
			String isSearchableFlag_surprise = "";
			String isSearchableifUnavailable_surprise = "";

			String prodctVedio = "";
			String AssociatedCatalog = "";
			Calendar onlineFromCal_kate = new GregorianCalendar();
			Calendar onlineFromCal_surprise = new GregorianCalendar();
			String onlineTo_kate = "";
			String onlineTo_surprise = "";
			Calendar onlineToCal_kate = new GregorianCalendar();
			Calendar onlineToCal_surprise = new GregorianCalendar();
			boolean onlineToCal_kate_set = false;
			boolean onlineToCal_surprise_set = false;
			boolean onlineFromCal_kate_set = false;
			boolean onlineFromCal_surprise_set = false;

			for (PlumSliceProduct sku : skus) {
				if ("Yes".equals(sku.getIsNewArrival())) {
					isNewVal = "Yes";
				}
				if ("Yes".equals(sku.getOnlineFlag_kate())) {
					onlineFlag_kate = "Yes";
				}
				if ("Yes".equals(sku.getOnlineFlag_surprise())) {
					onlineFlag_surprise = "Yes";
				}

				if (plumSliceProduct.getIsExclusive().isBlank()) {
					if ("Yes".equals(sku.getIsExclusive())) {
						isExclusive = "Yes";
					}
				} else {
					isExclusive = plumSliceProduct.getIsExclusive();
				}

				if (!sku.getOnlineFrom_kate().isBlank()) {

					if (!onlineFromCal_kate_set) {
						onlineFromCal_kate = convertToDate(sku.getOnlineFrom_kate());
						onlineFromCal_kate_set = true;
					}

					if (convertToDate(sku.getOnlineFrom_kate()).before(onlineFromCal_kate) || convertToDate(sku.getOnlineFrom_kate()).equals(onlineFromCal_kate)) {
						onlineFrom_kate = sku.getOnlineFrom_kate();
						onlineFromCal_kate = convertToDate(sku.getOnlineFrom_kate());
					}

				}
				if (!sku.getOnlineFrom_surprise().isBlank()) {

					if (!onlineFromCal_surprise_set) {
						onlineFromCal_surprise = convertToDate(sku.getOnlineFrom_surprise());
						onlineFromCal_surprise_set = true;
					}

					if (convertToDate(sku.getOnlineFrom_surprise()).before(onlineFromCal_surprise) || convertToDate(sku.getOnlineFrom_surprise()).equals(onlineFromCal_surprise)) {
						onlineFrom_surprise = sku.getOnlineFrom_surprise();
						onlineFromCal_surprise = convertToDate(sku.getOnlineFrom_surprise());
					}

				}

				if (!sku.getOnlineTo_kate().isBlank()) {

					if (!onlineToCal_kate_set) {
						onlineToCal_kate = convertToDate(sku.getOnlineTo_kate());
						onlineToCal_kate_set = true;
					}

					if (convertToDate(sku.getOnlineTo_kate()).after(onlineToCal_kate) || convertToDate(sku.getOnlineTo_kate()).equals(onlineToCal_kate)) {
						onlineTo_kate = sku.getOnlineTo_kate();
						onlineToCal_kate = convertToDate(sku.getOnlineTo_kate());
					}

				}

				if (!sku.getOnlineTo_surprise().isBlank()) {

					if (!onlineToCal_surprise_set) {
						onlineToCal_surprise = convertToDate(sku.getOnlineTo_surprise());
						onlineToCal_surprise_set = true;
					}

					if (convertToDate(sku.getOnlineTo_surprise()).after(onlineToCal_surprise) || convertToDate(sku.getOnlineTo_surprise()).equals(onlineToCal_surprise) ) {
						onlineTo_surprise = sku.getOnlineTo_surprise();
						onlineToCal_surprise = convertToDate(sku.getOnlineTo_surprise());
					}

				}

				if (!sku.getProuctVideo().isBlank() && prodctVedio.isBlank()) {
					prodctVedio = sku.getProuctVideo();

				}

				if (plumSliceProduct.getAssociatedCatalog().isBlank()) {
					if (!sku.getAssociatedCatalog().isBlank()) {
						AssociatedCatalog = sku.getAssociatedCatalog();
					}
				} else {
					AssociatedCatalog = plumSliceProduct.getAssociatedCatalog();
				}

				if (plumSliceProduct.getSearchableFlag_kate().isBlank()) {
					if ("Yes".equals(sku.getSearchableFlag_kate())) {
						isSearchableFlag_kate = "Yes";
					}
				} else {
					isSearchableFlag_kate = plumSliceProduct.getSearchableFlag_kate();
				}
				if (plumSliceProduct.getSearchableFlag_surprise().isBlank()) {
					if ("Yes".equals(sku.getSearchableFlag_surprise())) {
						isSearchableFlag_surprise = "Yes";
					}
				} else {
					isSearchableFlag_surprise = plumSliceProduct.getSearchableFlag_surprise();
				}

				if (plumSliceProduct.getSearchableifUnavailable_kate().isBlank()) {
					if ("Yes".equals(sku.getSearchableifUnavailable_kate())) {
						isSearchableifUnavailable_kate = "Yes";
					}
				} else {
					isSearchableifUnavailable_kate = plumSliceProduct.getSearchableifUnavailable_kate();
				}
				if (plumSliceProduct.getSearchableifUnavailable_surprise().isBlank()) {
					if ("Yes".equals(sku.getSearchableifUnavailable_surprise())) {
						isSearchableifUnavailable_surprise = "Yes";
					}
				} else {
					isSearchableifUnavailable_surprise = plumSliceProduct.getSearchableifUnavailable_surprise();
				}

			}
			plumSliceProduct.setIsExclusive(isExclusive);
			plumSliceProduct.setIsNewArrival(isNewVal);
			plumSliceProduct.setProuctVideo(prodctVedio);
			plumSliceProduct.setOnlineFrom_kate(onlineFrom_kate);
			plumSliceProduct.setOnlineTo_kate(onlineTo_kate);
			plumSliceProduct.setOnlineFlag_kate(onlineFlag_kate);
			plumSliceProduct.setSearchableFlag_kate(isSearchableFlag_kate);
			plumSliceProduct.setSearchableifUnavailable_kate(isSearchableifUnavailable_kate);
			plumSliceProduct.setOnlineFrom_surprise(onlineFrom_surprise);
			plumSliceProduct.setOnlineTo_surprise(onlineTo_surprise);
			plumSliceProduct.setOnlineFlag_surprise(onlineFlag_surprise);
			plumSliceProduct.setSearchableFlag_surprise(isSearchableFlag_surprise);
			plumSliceProduct.setSearchableifUnavailable_surprise(isSearchableifUnavailable_surprise);
			plumSliceProduct.setAssociatedCatalog(AssociatedCatalog);
			plumsliceExatrct.add(plumSliceProduct);
		}

	}

	private static Calendar convertToDate(String onlineFrom) throws ParseException {

		Date date = df.parse(onlineFrom);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	private static void writeCsv() throws IOException {
		// TODO Auto-generated method stub
		FileWriter myWriter = new FileWriter(ext.getPropValue("plumslice_transformed_feed_csv"), false);
		StringBuilder rows = new StringBuilder();
		rows.append(
				"\"SKU\",\"Variation ID\",\"Master ID\",\"Classification\",\"Product Type\",\"First Published Date\",\"Is New Arrival\",\"Is Exclusive\",\"Product Video\",\"Online Flag_KATE\",\"Online Flag_SURPRISE\",\"Online From_KATE\",\"Online From_SURPRISE\",\"Online To_KATE\",\"Online To_SURPRISE\",\"Searchable Flag_KATE\",\"Searchable Flag_SURPRISE\",\"SearchableifUnavailable_KATE\",\"SearchableifUnavailable_SURPRISE\",\"BRAND\"\n");
		for (PlumSliceProduct dataToInsert : plumsliceExatrct) {
			rows.append("\"");
			rows.append(dataToInsert.getSkuId());
			rows.append("\",\"");
			rows.append(dataToInsert.getVariationId());
			rows.append("\",\"");
			rows.append(dataToInsert.getMasterId());
			rows.append("\",\"");
			rows.append(dataToInsert.getItemCategory());
			rows.append("\",\"");
			rows.append(dataToInsert.getProductType());
			rows.append("\",\"");
			rows.append(dataToInsert.getFirstPublishDate());
			rows.append("\",\"");
			rows.append(dataToInsert.getIsNewArrival());
			rows.append("\",\"");
			rows.append(dataToInsert.getIsExclusive());
			rows.append("\",\"");
			rows.append(dataToInsert.getProuctVideo());
			rows.append("\",\"");
			rows.append(dataToInsert.getOnlineFlag_kate());
			rows.append("\",\"");
			rows.append(dataToInsert.getOnlineFlag_surprise());
			rows.append("\",\"");
			rows.append(dataToInsert.getOnlineFrom_kate());
			rows.append("\",\"");
			rows.append(dataToInsert.getOnlineFrom_surprise());
			rows.append("\",\"");
			rows.append(dataToInsert.getOnlineTo_kate());
			rows.append("\",\"");
			rows.append(dataToInsert.getOnlineTo_surprise());
			rows.append("\",\"");
			rows.append(dataToInsert.getSearchableFlag_kate());
			rows.append("\",\"");
			rows.append(dataToInsert.getSearchableFlag_surprise());
			rows.append("\",\"");
			rows.append(dataToInsert.getSearchableifUnavailable_kate());
			rows.append("\",\"");
			rows.append(dataToInsert.getSearchableifUnavailable_surprise());
			rows.append("\",\"");
			rows.append(dataToInsert.getAssociatedCatalog());
			rows.append("\"\n");
		}
		myWriter.write(rows.toString());
		myWriter.close();
	}

	/*
	 * private static void writeInExcel() throws IOException { Workbook workbook ;
	 * String[] columns =
	 * {"SKU","Variation ID","Master ID","Classification","Product Type",
	 * "First Published Date", "Is New Arrival", "Is Exclusive","Product Video",
	 * "Online Flag", "Online From", "Online To",
	 * "Searchable Flag","SearchableifUnavailable"}; int counter = 1; for
	 * (List<PlumSliceProduct> list : MainExtractForPlumSlice) { workbook = new
	 * XSSFWorkbook(); FileOutputStream fileOut = new
	 * FileOutputStream(ext.getPropValue("plumslice_transformed_feed")+"_"+counter+
	 * ".xlsx"); Sheet sheet = workbook.createSheet("SFCC-Feed"); Font headerFont =
	 * workbook.createFont(); headerFont.setBold(true);
	 * headerFont.setFontHeightInPoints((short) 14);
	 * headerFont.setColor(IndexedColors.BLACK.getIndex()); CellStyle
	 * headerCellStyle = workbook.createCellStyle();
	 * headerCellStyle.setFont(headerFont); Row headerRow = sheet.createRow(0);
	 * for(int i = 0; i < columns.length; i++) { Cell cell =
	 * headerRow.createCell(i); cell.setCellValue(columns[i]);
	 * cell.setCellStyle(headerCellStyle); } int rowNum = 1; for(PlumSliceProduct
	 * dataToInsert : list) { Row row = sheet.createRow(rowNum++);
	 * row.createCell(0).setCellValue(dataToInsert.getSkuId());
	 * row.createCell(1).setCellValue(dataToInsert.getVariationId());
	 * row.createCell(3).setCellValue(dataToInsert.getItemCategory());
	 * row.createCell(2).setCellValue(dataToInsert.getMasterId());
	 * row.createCell(4).setCellValue(dataToInsert.getProductType());
	 * row.createCell(5).setCellValue(dataToInsert.getFirstPublishDate());
	 * row.createCell(6).setCellValue(dataToInsert.getIsNewArrival());
	 * row.createCell(7).setCellValue(dataToInsert.getIsExclusive());
	 * row.createCell(8).setCellValue(dataToInsert.getProuctVideo());
	 * row.createCell(9).setCellValue(dataToInsert.getOnlineFlag());
	 * row.createCell(10).setCellValue(dataToInsert.getOnlineFrom());
	 * row.createCell(11).setCellValue(dataToInsert.getOnlineTo());
	 * row.createCell(12).setCellValue(dataToInsert.getSearchableFlag());
	 * row.createCell(13).setCellValue(dataToInsert.getSearchableifUnavailable()); }
	 * for(int i = 0; i < columns.length; i++) { sheet.autoSizeColumn(i); }
	 * 
	 * workbook.write(fileOut); fileOut.close();
	 * 
	 * // Closing the workbook workbook.close(); counter++;
	 * //System.out.println(counter); }
	 * 
	 * }
	 */
	private static String fetchSearchableIfUnAvailable(ComplexTypeProduct product, ProductData tmpPrdDta,
			String brand) {
		List<SharedTypeSiteSpecificBoolean> vSharedTypeSiteSpecificBoolean = product.getSearchableIfUnavailableFlag();
		boolean isSearchableIfUnAvailableFlagDefault = false;
		boolean isSearchableIfUnAvailableFlag = false;
		boolean isShopValueExists = false;
		for (SharedTypeSiteSpecificBoolean pSharedTypeSiteSpecificBooleanVal : vSharedTypeSiteSpecificBoolean) {
			if (pSharedTypeSiteSpecificBooleanVal.getSiteId() == null
					|| (pSharedTypeSiteSpecificBooleanVal.getSiteId() != null
							&& "".equals(pSharedTypeSiteSpecificBooleanVal.getSiteId()))) {
				isSearchableIfUnAvailableFlagDefault = pSharedTypeSiteSpecificBooleanVal.isValue();
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
				return "Yes";
			} else {
				return "No";
			}

		} else if (!isShopValueExists && isSearchableIfUnAvailableFlagDefault) {
			return "Yes";
		} else {
			return "";
		}
	}

	private static String fetchOnlineFlag(ComplexTypeProduct product, ProductData tmpPrdDta, String brand) {
		if("046249587087".equals(product.getProductId() )) {
			System.out.println("yesss");
		}
		List<SharedTypeSiteSpecificBoolean> vSharedTypeSiteSpecificBoolean = product.getOnlineFlag();
		boolean isOnlineFlagDefault = false;
		boolean isOnlineFlag = false;
		boolean isShopValueExists = false;
		for (SharedTypeSiteSpecificBoolean pSharedTypeSiteSpecificBooleanVal : vSharedTypeSiteSpecificBoolean) {
			if (pSharedTypeSiteSpecificBooleanVal.getSiteId() == null
					|| (pSharedTypeSiteSpecificBooleanVal.getSiteId() != null
							&& "".equals(pSharedTypeSiteSpecificBooleanVal.getSiteId()))) {
				isOnlineFlagDefault = pSharedTypeSiteSpecificBooleanVal.isValue();
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
				return "Yes";
			} else {
				return "No";
			}

		} else if (!isShopValueExists && isOnlineFlagDefault) {
			return "Yes";
		} else {
			return "";
		}
	}

	private static String fetchSearchable(ComplexTypeProduct product, ProductData tmpPrdDta, String brand) {
		List<SharedTypeSiteSpecificBoolean> vSharedTypeSiteSpecificBoolean = product.getSearchableFlag();
		boolean isSearchableFlag = false;
		boolean isSearchableFlagDefault = false;
		boolean isShopValueExists = false;
		for (SharedTypeSiteSpecificBoolean pSharedTypeSiteSpecificBooleanVal : vSharedTypeSiteSpecificBoolean) {
			if (pSharedTypeSiteSpecificBooleanVal.getSiteId() == null
					|| (pSharedTypeSiteSpecificBooleanVal.getSiteId() != null
							&& "".equals(pSharedTypeSiteSpecificBooleanVal.getSiteId()))) {
				isSearchableFlagDefault = pSharedTypeSiteSpecificBooleanVal.isValue();
			}
			if ("KATESPADE".equals(brand)) {
				if (pSharedTypeSiteSpecificBooleanVal.getSiteId() != null
						&& "Shop".equalsIgnoreCase(pSharedTypeSiteSpecificBooleanVal.getSiteId())) {
					isSearchableFlag = pSharedTypeSiteSpecificBooleanVal.isValue();
					isShopValueExists = true;
				}
			}
			if ("SURPRISE".equals(brand)) {
				if (pSharedTypeSiteSpecificBooleanVal.getSiteId() != null
						&& "KateSale".equalsIgnoreCase(pSharedTypeSiteSpecificBooleanVal.getSiteId())) {
					isSearchableFlag = pSharedTypeSiteSpecificBooleanVal.isValue();
					isShopValueExists = true;
				}
			}
		}
		if (isShopValueExists && isSearchableFlag) {
			return "Yes";
		} else if (!isShopValueExists && isSearchableFlagDefault) {
			return "Yes";
		} else {
			return "";
		}
	}

	private static String fetchOnlineTo(ComplexTypeProduct product, ProductData tmpPrdDta, String brand) {
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

	private static String fetchOnlineFrom(ComplexTypeProduct product, ProductData tmpPrdDta, String brand) {
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
					result = "Yes";
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

}
