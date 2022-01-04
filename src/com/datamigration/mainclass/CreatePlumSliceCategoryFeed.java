package com.datamigration.mainclass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.datamigration.javaclass.Catalog;
import com.datamigration.javaclass.ComplexTypeProduct;
import com.datamigration.javaclass.ComplexTypeProductClassificationCategory;
import com.datamigration.pojo.CategoryData;
import com.datamigration.pojo.ProductData;

public class CreatePlumSliceCategoryFeed {
	static HashMap<String,CategoryData> Kate_prouct_DB = new HashMap<String,CategoryData>();
	static HashMap<String,CategoryData> surprise_prouct_DB = new HashMap<String,CategoryData>();
	static HashMap<String,ProductData> prouct_DB = new HashMap<String,ProductData>();
	static HashMap<String,ProductData> productCatgeoryMapping_Surprise = new HashMap<String,ProductData>();
	static HashMap<String,ProductData> productCatgeoryMapping_katespade = new HashMap<String,ProductData>();
	static ExtractMainProperties ext = new ExtractMainProperties();
	public static void main(String[] args) throws IOException, JAXBException {
		PopulateMainDB mainDB = new PopulateMainDB();
		mainDB.populateDB();
		Kate_prouct_DB = mainDB.getKatespade_category_DB();
		surprise_prouct_DB = mainDB.getSurprise_category_DB();
		prouct_DB= mainDB.getProuct_DB();
	 	File file = new File(ext.getPropValue("master-catalog-path"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		for ( ComplexTypeProduct product : que.getProduct()) {
			if(!("#N/A").equals(product.getProductId())){
				ProductData tmpPrdDta = prouct_DB.get(product.getProductId());
				if(tmpPrdDta == null) {
					continue;
				}
				ComplexTypeProductClassificationCategory categotyClassification = product.getClassificationCategory();
				String catgeory_product_relation = "";
				if(categotyClassification !=null && categotyClassification.getCatalogId() != null && "kateandsaturday-site-catalog".equals(categotyClassification.getCatalogId())) {
					catgeory_product_relation = tmpPrdDta.getSkuNumber()+"#"+categotyClassification.getValue();	
					if(!productCatgeoryMapping_katespade.containsKey(catgeory_product_relation)) {
						productCatgeoryMapping_katespade.put(catgeory_product_relation,tmpPrdDta);
					}
				}else if( categotyClassification !=null &&  categotyClassification.getCatalogId() != null && "katesale-site-catalog".equals(categotyClassification.getCatalogId())) {
					catgeory_product_relation = tmpPrdDta.getSkuNumber()+"#"+categotyClassification.getValue();
					if(!productCatgeoryMapping_Surprise.containsKey(catgeory_product_relation)) {
						productCatgeoryMapping_Surprise.put(catgeory_product_relation,tmpPrdDta);
					}
					
				} 		
			}
		}
		writeInExcel_Kate();
		writeInExcel_surprise();
	}
	private static void writeInExcel_Kate() throws FileNotFoundException, IOException {
		String[] headerColumn = {"Id","Name","Parent ID","Is Online"};
		String[] headerColumn_1 = {"Product_ID","Category_ID","Class Description","Class Id","Department Description","Department Id","Group Description","Group Id","Custom Attribute 1","Custom Attribute 2"};
		Workbook Katespade_workbook  = new XSSFWorkbook();
		 FileOutputStream fileOut_katespade = new FileOutputStream(ext.getPropValue("katespade_category_product_relation_export"));
			Sheet katespade_category_details_sheet = Katespade_workbook.createSheet("Catgeory Details");
			 Font headerFont = Katespade_workbook.createFont();
		        headerFont.setBold(true);
		        headerFont.setFontHeightInPoints((short) 14);
		        headerFont.setColor(IndexedColors.BLACK.getIndex());
		        CellStyle headerCellStyle = Katespade_workbook.createCellStyle();
		        headerCellStyle.setFont(headerFont);
		        Row headerRow = katespade_category_details_sheet.createRow(0);
		        for(int i = 0; i < headerColumn.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(headerColumn[i]);
		            cell.setCellStyle(headerCellStyle);
		        }		        
		        int rowNum = 1;		        
		        for (String key : Kate_prouct_DB.keySet()) {
		        	 Row row = katespade_category_details_sheet.createRow(rowNum++);
		        	 CategoryData vCategoryData = (CategoryData)Kate_prouct_DB.get(key);
		        	 row.createCell(0).setCellValue(vCategoryData.getCategoryId());
		        	 row.createCell(1).setCellValue(vCategoryData.getDisplayName());
		        	 row.createCell(2).setCellValue(vCategoryData.getParentCategory());
		        	 row.createCell(3).setCellValue(vCategoryData.isOnline()?"Yes":"No");		        	 
		        }		        
		        for(int i = 0; i < headerColumn.length; i++) {
		        	katespade_category_details_sheet.autoSizeColumn(i);
		        }
		        
		    	Sheet katespade_category_Relation_sheet = Katespade_workbook.createSheet("Catgeory product relation ");
		    	 CellStyle headerCellStyle_1 = Katespade_workbook.createCellStyle();
			        headerCellStyle.setFont(headerFont);
			        Row headerRow_1 = katespade_category_Relation_sheet.createRow(0);
			        for(int i = 0; i < headerColumn_1.length; i++) {
			            Cell cell = headerRow_1.createCell(i);
			            cell.setCellValue(headerColumn_1[i]);
			            cell.setCellStyle(headerCellStyle);
			        }		
		    	 
			        int rowNum_1 = 1;		        
			        for (String pair : productCatgeoryMapping_katespade.keySet()) {
			        	 Row row = katespade_category_Relation_sheet.createRow(rowNum_1++);
			        	 row.createCell(0).setCellValue(pair.split("#")[0]);
			        	 row.createCell(1).setCellValue(pair.split("#")[1]);
			        	 ProductData tmpData= productCatgeoryMapping_katespade.get(pair)	;
			        	 row.createCell(2).setCellValue(tmpData.getClassDescription());
			        	 row.createCell(3).setCellValue(tmpData.getClassId());
			        	 row.createCell(4).setCellValue(tmpData.getDepartmentDescription());
			        	 row.createCell(5).setCellValue(tmpData.getDepartmentId());
			        	 row.createCell(6).setCellValue(tmpData.getGroupDescription());
			        	 row.createCell(7).setCellValue(tmpData.getGroupId());
			        	 row.createCell(8).setCellValue(tmpData.getCustomAttribute1());
			        	 row.createCell(9).setCellValue(tmpData.getCustomAttribute2());
			        	 
			        	 }		        
			        for(int i = 0; i < headerColumn_1.length; i++) {
			        	katespade_category_Relation_sheet.autoSizeColumn(i);
			        }
		        
		        Katespade_workbook.write(fileOut_katespade);
		        fileOut_katespade.close();
		        Katespade_workbook.close();
	}
	private static void writeInExcel_surprise() throws FileNotFoundException, IOException {
		String[] headerColumn = {"Id","Name","Parent ID","Is Online"};
		String[] headerColumn_1 = {"Product_ID","Category_ID","Class Description","Class Id","Department Description","Department Id","Group Description","Group Id","Custom Attribute 1","Custom Attribute 2"};
		Workbook surprise_workbook  = new XSSFWorkbook();
		 FileOutputStream fileOut_surprise = new FileOutputStream(ext.getPropValue("surprise_category_product_relation_export"));
		        
		        
		        Sheet surprise_category_details_sheet = surprise_workbook.createSheet("Catgeory Details");
			        CellStyle surpriseheaderCellStyle = surprise_workbook.createCellStyle();
					 Font headerFont = surprise_workbook.createFont();
				        headerFont.setBold(true);
				        headerFont.setFontHeightInPoints((short) 14);
				        headerFont.setColor(IndexedColors.BLACK.getIndex());
			        surpriseheaderCellStyle.setFont(headerFont);
			        Row surprise_headerRow = surprise_category_details_sheet.createRow(0);
			        for(int i = 0; i < headerColumn.length; i++) {
			            Cell cell = surprise_headerRow.createCell(i);
			            cell.setCellValue(headerColumn[i]);
			            cell.setCellStyle(surpriseheaderCellStyle);
			        }		        
			        int rowNum_sur = 1;		        
			        for (String key : surprise_prouct_DB.keySet()) {
			        	 Row row = surprise_category_details_sheet.createRow(rowNum_sur++);
			        	 CategoryData vCategoryData = (CategoryData)surprise_prouct_DB.get(key);
			        	 row.createCell(0).setCellValue(vCategoryData.getCategoryId());
			        	 row.createCell(1).setCellValue(vCategoryData.getDisplayName());
			        	 row.createCell(2).setCellValue(vCategoryData.getParentCategory());
			        	 row.createCell(3).setCellValue(vCategoryData.isOnline()?"Yes":"No");		        	 
			        }		        
			        for(int i = 0; i < headerColumn.length; i++) {
			        	surprise_category_details_sheet.autoSizeColumn(i);
			        }
			        
			    	Sheet surprise_category_Relation_sheet = surprise_workbook.createSheet("Catgeory product relation ");
			    	 CellStyle surpriseheaderCellStyle_1 = surprise_workbook.createCellStyle();
			    	 surpriseheaderCellStyle_1.setFont(headerFont);
				        Row surprise_headerRow_1 = surprise_category_Relation_sheet.createRow(0);
				        for(int i = 0; i < headerColumn_1.length; i++) {
				            Cell cell = surprise_headerRow_1.createCell(i);
				            cell.setCellValue(headerColumn_1[i]);
				            cell.setCellStyle(surpriseheaderCellStyle_1);
				        }		
			    	 
				        int rowNum_sur_1 = 1;		        
				        for (String pair : productCatgeoryMapping_Surprise.keySet()) {
				        	 Row row = surprise_category_Relation_sheet.createRow(rowNum_sur_1++);
				        	 row.createCell(0).setCellValue(pair.split("#")[0]);
				        	 row.createCell(1).setCellValue(pair.split("#")[1]);
				        	 ProductData tmpData= productCatgeoryMapping_Surprise.get(pair)	;
				        	 row.createCell(2).setCellValue(tmpData.getClassDescription());
				        	 row.createCell(3).setCellValue(tmpData.getClassId());
				        	 row.createCell(4).setCellValue(tmpData.getDepartmentDescription());
				        	 row.createCell(5).setCellValue(tmpData.getDepartmentId());
				        	 row.createCell(6).setCellValue(tmpData.getGroupDescription());
				        	 row.createCell(7).setCellValue(tmpData.getGroupId());
				        	 row.createCell(8).setCellValue(tmpData.getCustomAttribute1());
				        	 row.createCell(9).setCellValue(tmpData.getCustomAttribute2());
				        	 
				        }		        
				        for(int i = 0; i < headerColumn_1.length; i++) {
				        	surprise_category_Relation_sheet.autoSizeColumn(i);
				        }
			        
				        surprise_workbook.write(fileOut_surprise);
				        fileOut_surprise.close();
			        surprise_workbook.close();
		        	        
	}

}
