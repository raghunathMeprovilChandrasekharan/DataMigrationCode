package com.datamigration.mainclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.datamigration.javaclass.ComplexTypeCategoryAssignment;
import com.datamigration.javaclass.ComplexTypeProduct;
import com.datamigration.javaclass.ComplexTypeProductVariations;
import com.datamigration.javaclass.ComplexTypeProductVariationsVariant;
import com.datamigration.javaclass.ComplexTypeProductVariationsVariants;
import com.datamigration.pojo.A360DB;
import com.datamigration.pojo.CategoryProductRelation;
import com.datamigration.pojo.CategoryProductRelationVO;
import com.datamigration.pojo.CatgeoryExclusionInclusion;
import com.datamigration.pojo.PlumSliceItemDetailsForFeed;

public class New_ExtractCategoryToProduct {
	static ExtractMainProperties ext = new ExtractMainProperties();
	private static HashMap<String, A360DB> A360DB = new HashMap<String, A360DB>();
	private static HashMap<String, String> categoryProductRelation = new HashMap<String, String>();
	private static List<CategoryProductRelation> vCategoryProductRelationList = new ArrayList<CategoryProductRelation>();
	private static CatgeoryExclusionInclusion  CatgeoryExclusionInclusion   = new CatgeoryExclusionInclusion();
	private static HashMap<String,Set<String>> masterProductDB = new HashMap<String, Set<String>>();
	private static HashMap<String,CategoryProductRelationVO> itemCategoryRelation_inclusion = new HashMap<String,CategoryProductRelationVO>();
	private static HashMap<String,CategoryProductRelationVO> variationCategoryRelation_inclusion = new HashMap<String,CategoryProductRelationVO>();
	private static HashMap<String,CategoryProductRelationVO> itemCategoryRelation_exclusion = new HashMap<String,CategoryProductRelationVO>();
	private static HashMap<String,CategoryProductRelationVO> variationCategoryRelation_exclusion = new HashMap<String,CategoryProductRelationVO>();
	
	
	private static Set<String> KS_productsList = new HashSet<String>();
	private static Set<String> KSS_productsList = new HashSet<String>();
	private static Set<String> KS_ItemList = new HashSet<String>();
	private static Set<String> KSS_ItemList = new HashSet<String>();
	
	
	
	public static void main(String[] args) throws Exception {
		New_ExtractA360Data A360DBextract = new New_ExtractA360Data();
		New_ExtractA360Data.extractA360DB();
		masterProductDB = A360DBextract.getMasterproductsDB();
		A360DB = A360DBextract.getA360DB();
		ks_extaractProductMasterData();
		kss_extaractProductMasterData();
		
		extaractItemMasterData();
		
		
		ExtractCategoryDetails("surprise");
		ExtractCategoryDetails("katepsade");
		wrietToCSV();
		
	}
	
	

	private static void extaractItemMasterData() {
		for(String produt : KS_productsList) {
			if(A360DB.containsKey(produt)) {
				KS_ItemList.add(A360DB.get(produt).getStyleId());
			}
		}
		
		for(String produt : KSS_productsList) {
			if(A360DB.containsKey(produt)) {
				KSS_ItemList.add(A360DB.get(produt).getStyleId());
			}
		}
	}



	private static void ks_extaractProductMasterData() throws JAXBException, Exception {
		File file = new File(ext.getPropValue("ks-master-catalog-path-item"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
			for (ComplexTypeProduct product : que.getProduct()) {
				if(product.getVariations() != null) {
					ComplexTypeProductVariations variations = product.getVariations();
					if (variations != null && variations.getVariants() != null) {
						for (ComplexTypeProductVariationsVariants variant : variations.getVariants()) {
							int sequence = 0;
							for (ComplexTypeProductVariationsVariant vComplexTypeProductVariationsVariant : variant
									.getVariant()) {
								KS_productsList.add(vComplexTypeProductVariationsVariant.getProductId().trim());
							}
						}
					}
				}
			}
		
		
	}

	private static void kss_extaractProductMasterData() throws JAXBException, Exception {
		File file = new File(ext.getPropValue("kss-master-catalog-path-item"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
			for (ComplexTypeProduct product : que.getProduct()) {
				if(product.getVariations() != null) {
					ComplexTypeProductVariations variations = product.getVariations();
					if (variations != null && variations.getVariants() != null) {
						for (ComplexTypeProductVariationsVariants variant : variations.getVariants()) {
							int sequence = 0;
							for (ComplexTypeProductVariationsVariant vComplexTypeProductVariationsVariant : variant
									.getVariant()) {
								KSS_productsList.add(vComplexTypeProductVariationsVariant.getProductId().trim());
							}
						}
					}
				}
			}
		
		
	}
	
	private static void ExtractCategoryDetails(String subBrands) throws JAXBException, IOException {
		List<String> customAttriubutesttributes = new ArrayList<String>();
		 HashMap<String, String>  CategoryMap_Inclusion =  new HashMap<String,String>();
		 HashMap<String, String>  CategoryMap_Exclusion =  new HashMap<String,String>();
		File file;
		if ("surprise".equalsIgnoreCase(subBrands)) {
			file = new File(ext.getPropValue("surprise-sales-catalog-path"));
			 CategoryMap_Inclusion = 	CatgeoryExclusionInclusion.getKssCategoryMap_Inclusion()		;
			 CategoryMap_Exclusion = 	CatgeoryExclusionInclusion.getKssCategoryMap_Exclusion()		;
		}else {
			file = new File(ext.getPropValue("katespade-sales-catalog-path"));
			 CategoryMap_Inclusion = 	CatgeoryExclusionInclusion.getKsCategoryMap_Inclusion()		;
			 CategoryMap_Exclusion = 	CatgeoryExclusionInclusion.getKsCategoryMap_Exclusion()		;
		}
		
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		
		for(ComplexTypeCategoryAssignment cataegoryAssignment : que.getCategoryAssignment()) {
			CategoryProductRelationVO vCategoryProductRelationVO_inclusion = new CategoryProductRelationVO();
			if(CategoryMap_Inclusion.containsKey(cataegoryAssignment.getCategoryId())) {
				if(masterProductDB.containsKey(cataegoryAssignment.getProductId().split("-")[0]) && (("katepsade".equalsIgnoreCase(subBrands) && KS_ItemList.contains(cataegoryAssignment.getProductId().split("-")[0])) ||
						("surprise".equalsIgnoreCase(subBrands) && KSS_ItemList.contains(cataegoryAssignment.getProductId().split("-")[0])) )   ) {
					if(itemCategoryRelation_inclusion.containsKey(cataegoryAssignment.getProductId().split("-")[0])) {
						vCategoryProductRelationVO_inclusion = itemCategoryRelation_inclusion.get(cataegoryAssignment.getProductId().split("-")[0]);
					}else {
						vCategoryProductRelationVO_inclusion = new CategoryProductRelationVO();
					}
					
					vCategoryProductRelationVO_inclusion.setItemId(cataegoryAssignment.getProductId().split("-")[0]);	
					if ("surprise".equalsIgnoreCase(subBrands)) {
						vCategoryProductRelationVO_inclusion.setItemStoreFront("KSUSSUR");	
						vCategoryProductRelationVO_inclusion.setItemCategory_kss(CategoryMap_Inclusion.get(cataegoryAssignment.getCategoryId()));
					}else {
						vCategoryProductRelationVO_inclusion.setItemStoreFront("KSUSRT");	
						vCategoryProductRelationVO_inclusion.setItemCategory_ks(CategoryMap_Inclusion.get(cataegoryAssignment.getCategoryId()));
					}
					itemCategoryRelation_inclusion.put(cataegoryAssignment.getProductId().split("-")[0],vCategoryProductRelationVO_inclusion);
					
					Set<String> vaiations = getVariationList(cataegoryAssignment.getProductId().split("-")[0],subBrands);
					
					
					for (String str : vaiations) {
						if(variationCategoryRelation_inclusion.containsKey(str)) {
							vCategoryProductRelationVO_inclusion = variationCategoryRelation_inclusion.get(str);
						}else {
							vCategoryProductRelationVO_inclusion = new CategoryProductRelationVO();
						}
						vCategoryProductRelationVO_inclusion.setItemId(cataegoryAssignment.getProductId().split("-")[0]);	
						vCategoryProductRelationVO_inclusion.setVariationId(str);	
						if ("surprise".equalsIgnoreCase(subBrands)) {
							vCategoryProductRelationVO_inclusion.setItemStoreFront("KSUSSUR");	
							vCategoryProductRelationVO_inclusion.setVariationStoreFront("KSUSSUR");	
							vCategoryProductRelationVO_inclusion.setVariationCategory_kss(CategoryMap_Inclusion.get(cataegoryAssignment.getCategoryId()));
						}else {
							vCategoryProductRelationVO_inclusion.setItemStoreFront("KSUSRT");	
							vCategoryProductRelationVO_inclusion.setVariationStoreFront("KSUSRT");	
							vCategoryProductRelationVO_inclusion.setVariationCategory_ks(CategoryMap_Inclusion.get(cataegoryAssignment.getCategoryId()));
						}
						variationCategoryRelation_inclusion.put(str,vCategoryProductRelationVO_inclusion);
					}
				}
			}
			
			if(CategoryMap_Exclusion.containsKey(cataegoryAssignment.getCategoryId())) {
				if(masterProductDB.containsKey(cataegoryAssignment.getProductId().split("-")[0]) && (("katepsade".equalsIgnoreCase(subBrands) && KS_ItemList.contains(cataegoryAssignment.getProductId().split("-")[0])) 
						|| ("surprise".equalsIgnoreCase(subBrands) && KSS_ItemList.contains(cataegoryAssignment.getProductId().split("-")[0])) )  ) {
					if(itemCategoryRelation_exclusion.containsKey(cataegoryAssignment.getProductId().split("-")[0])) {
						vCategoryProductRelationVO_inclusion = itemCategoryRelation_exclusion.get(cataegoryAssignment.getProductId().split("-")[0]);
					}else {
						vCategoryProductRelationVO_inclusion = new CategoryProductRelationVO();
					}
					vCategoryProductRelationVO_inclusion.setItemId(cataegoryAssignment.getProductId().split("-")[0]);	
					if ("surprise".equalsIgnoreCase(subBrands)) {
						vCategoryProductRelationVO_inclusion.setItemStoreFront("KSUSSUR");	
						vCategoryProductRelationVO_inclusion.setItemCategory_kss(CategoryMap_Exclusion.get(cataegoryAssignment.getCategoryId()));
					}else {
						vCategoryProductRelationVO_inclusion.setItemStoreFront("KSUSRT");	
						vCategoryProductRelationVO_inclusion.setItemCategory_ks(CategoryMap_Exclusion.get(cataegoryAssignment.getCategoryId()));
					}
					itemCategoryRelation_exclusion.put(cataegoryAssignment.getProductId().split("-")[0],vCategoryProductRelationVO_inclusion);
					
					
		Set<String> vaiations = getVariationList(cataegoryAssignment.getProductId().split("-")[0],subBrands);
					
					for (String str : vaiations) {
						if(variationCategoryRelation_exclusion.containsKey(str)) {
							vCategoryProductRelationVO_inclusion = variationCategoryRelation_exclusion.get(str);
						}else {
							vCategoryProductRelationVO_inclusion = new CategoryProductRelationVO();
						}
						vCategoryProductRelationVO_inclusion.setItemId(cataegoryAssignment.getProductId().split("-")[0]);	
						vCategoryProductRelationVO_inclusion.setVariationId(str);	
						if ("surprise".equalsIgnoreCase(subBrands)) {
							vCategoryProductRelationVO_inclusion.setItemStoreFront("KSUSSUR");	
							vCategoryProductRelationVO_inclusion.setVariationStoreFront("KSUSSUR");	
							vCategoryProductRelationVO_inclusion.setVariationCategory_kss(CategoryMap_Exclusion.get(cataegoryAssignment.getCategoryId()));
						}else {
							vCategoryProductRelationVO_inclusion.setItemStoreFront("KSUSRT");	
							vCategoryProductRelationVO_inclusion.setVariationStoreFront("KSUSRT");	
							vCategoryProductRelationVO_inclusion.setVariationCategory_ks(CategoryMap_Exclusion.get(cataegoryAssignment.getCategoryId()));
						}
						variationCategoryRelation_exclusion.put(str,vCategoryProductRelationVO_inclusion);
					}
					
					
					
				}
				
			}
			
		}

	}
	private static Set<String> getVariationList(String productId, String subBrands) {
		Set<String> variats = new HashSet<String>();
		Set<String> products = masterProductDB.get(productId);
		if ("surprise".equalsIgnoreCase(subBrands)) {
			for(String productIdinList :products) {
				if(KSS_productsList.contains(productIdinList) ){
					variats.add(A360DB.get(productIdinList).getVariationId());		
				}
			}
			
			
		}else {
			for(String productIdinList :products) {
				if(KS_productsList.contains(productIdinList) ){
					variats.add(A360DB.get(productIdinList).getVariationId());		
				}
			}
		}
		
		return variats;
	}



	private static void wrietToCSV() throws NumberFormatException, IOException {
		List<CategoryProductRelationVO> itemCategoryRelation_inclusion_list = extractList(itemCategoryRelation_inclusion);
		List<CategoryProductRelationVO> itemCategoryRelation_exclusion_list =  extractList(itemCategoryRelation_exclusion);
		List<CategoryProductRelationVO> variationCategoryRelation_inclusion_list =  extractList(variationCategoryRelation_inclusion);
		List<CategoryProductRelationVO> variationCategoryRelation_exclusion_list = extractList(variationCategoryRelation_exclusion);
		
		
		List<List<CategoryProductRelationVO>> itemCategoryRelation_inclusion_list_output = ListUtils.partition(itemCategoryRelation_inclusion_list,
				Integer.valueOf(ext.getPropValue("no_of_splits")));
		List<List<CategoryProductRelationVO>> itemCategoryRelation_exclusion_list_output = ListUtils.partition(itemCategoryRelation_exclusion_list,
				Integer.valueOf(ext.getPropValue("no_of_splits")));
		List<List<CategoryProductRelationVO>> variationCategoryRelation_inclusion_list_output = ListUtils.partition(variationCategoryRelation_inclusion_list,
				Integer.valueOf(ext.getPropValue("no_of_splits")));
		List<List<CategoryProductRelationVO>> variationCategoryRelation_exclusion_list_output = ListUtils.partition(variationCategoryRelation_exclusion_list,
				Integer.valueOf(ext.getPropValue("no_of_splits")));
		
		
		

		
		int count = 0;
		for (List<CategoryProductRelationVO> delta : itemCategoryRelation_inclusion_list_output) {
			File masterSamplerFile = new File(ext.getPropValue("plumslice_master_categoryrelation_template_sampler")
					.replaceAll("<<Iteration>>", String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("plumslice_master_categoryrelation_template_mastercopy")), masterSamplerFile);
			FileInputStream masterInputStream = new FileInputStream(masterSamplerFile);
			Workbook masterWorkbook = WorkbookFactory.create(masterInputStream);
			Sheet masterSheet = masterWorkbook.getSheetAt(0);
			int masterRowCount = masterSheet.getLastRowNum();
			for (CategoryProductRelationVO masterDetails : delta) {
				Row masterRow = masterSheet.createRow(++masterRowCount);
				int columnCount = 0; 
				Cell cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemId());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemStoreFront());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemCategory_ks());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemCategory_kss());
			}
			masterInputStream.close();
			FileOutputStream outputStream = new FileOutputStream(masterSamplerFile);
			masterWorkbook.write(outputStream);
			masterWorkbook.close();
			outputStream.close();
			count++;
		}
		count = 0;
		for (List<CategoryProductRelationVO> delta : itemCategoryRelation_exclusion_list_output) {
			File masterSamplerFile = new File(ext.getPropValue("plumslice_master_categoryrelation_exclusion_template_sampler")
					.replaceAll("<<Iteration>>", String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("plumslice_master_categoryrelation_template_mastercopy")), masterSamplerFile);
			FileInputStream masterInputStream = new FileInputStream(masterSamplerFile);
			Workbook masterWorkbook = WorkbookFactory.create(masterInputStream);
			Sheet masterSheet = masterWorkbook.getSheetAt(0);
			int masterRowCount = masterSheet.getLastRowNum();
			for (CategoryProductRelationVO masterDetails : delta) {
				Row masterRow = masterSheet.createRow(++masterRowCount);
				int columnCount = 0; 
				Cell cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemId());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemStoreFront());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemCategory_ks());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemCategory_kss());
			}
			masterInputStream.close();
			FileOutputStream outputStream = new FileOutputStream(masterSamplerFile);
			masterWorkbook.write(outputStream);
			masterWorkbook.close();
			outputStream.close();
			count++;
		}
		count = 0;
		for (List<CategoryProductRelationVO> delta : variationCategoryRelation_inclusion_list_output) {
			File masterSamplerFile = new File(ext.getPropValue("plumslice_variation_categoryrelation_template_sampler")
					.replaceAll("<<Iteration>>", String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("plumslice_variation_categoryrelation_template_mastercopy")), masterSamplerFile);
			FileInputStream masterInputStream = new FileInputStream(masterSamplerFile);
			Workbook masterWorkbook = WorkbookFactory.create(masterInputStream);
			Sheet masterSheet = masterWorkbook.getSheetAt(0);
			int masterRowCount = masterSheet.getLastRowNum();
			for (CategoryProductRelationVO masterDetails : delta) {
				Row masterRow = masterSheet.createRow(++masterRowCount);
				int columnCount = 0; 
				Cell cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemId());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemStoreFront());
				columnCount++;
				
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getVariationId());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getVariationStoreFront());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getVariationCategory_ks());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getVariationCategory_kss());
			}
			masterInputStream.close();
			FileOutputStream outputStream = new FileOutputStream(masterSamplerFile);
			masterWorkbook.write(outputStream);
			masterWorkbook.close();
			outputStream.close();
			count++;
		}
		count = 0;
		for (List<CategoryProductRelationVO> delta : variationCategoryRelation_exclusion_list_output) {
			File masterSamplerFile = new File(ext.getPropValue("plumslice_variation_categoryrelation_exclusion_template_sampler")
					.replaceAll("<<Iteration>>", String.valueOf(count)));
			FileUtils.copyFile(new File(ext.getPropValue("plumslice_variation_categoryrelation_template_mastercopy")), masterSamplerFile);
			FileInputStream masterInputStream = new FileInputStream(masterSamplerFile);
			Workbook masterWorkbook = WorkbookFactory.create(masterInputStream);
			Sheet masterSheet = masterWorkbook.getSheetAt(0);
			int masterRowCount = masterSheet.getLastRowNum();
			for (CategoryProductRelationVO masterDetails : delta) {
				Row masterRow = masterSheet.createRow(++masterRowCount);
				int columnCount = 0; 
				Cell cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemId());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getItemStoreFront());
				columnCount++;
				
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getVariationId());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getVariationStoreFront());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getVariationCategory_ks());
				columnCount++;
				cell = masterRow.createCell(columnCount);
				cell.setCellValue(masterDetails.getVariationCategory_kss());
			}
			masterInputStream.close();
			FileOutputStream outputStream = new FileOutputStream(masterSamplerFile);
			masterWorkbook.write(outputStream);
			masterWorkbook.close();
			outputStream.close();
			count++;
		}
		
		
		
		
		
	}
	private static List<CategoryProductRelationVO> extractList(
			HashMap<String, CategoryProductRelationVO> fullMap) {
		List<CategoryProductRelationVO> vCategoryProductRelationVOList = new ArrayList<CategoryProductRelationVO>();
		for(String str: fullMap.keySet()) {
			vCategoryProductRelationVOList.add(fullMap.get(str));
		}
		return vCategoryProductRelationVOList;
	}
	
	/**
	
	**/
}
