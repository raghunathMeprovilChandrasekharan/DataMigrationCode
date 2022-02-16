package com.datamigration.mainclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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

public class New_ExtractStoreFrontDataForItem {
	static ExtractMainProperties ext = new ExtractMainProperties();
	private static HashMap<String, A360DB> A360DB = new HashMap<String, A360DB>();
	private static Set<String> KS_productsList = new HashSet<String>();
	private static Set<String> KSS_productsList = new HashSet<String>();
	private static List<String> ks_vaeriationList = new ArrayList<String>();
	private static List<String> kss_vaeriationList = new ArrayList<String>();
	public static void main(String[] args) throws Exception {
	
		New_ExtractA360Data A360DBextract = new New_ExtractA360Data();
		A360DBextract.extractA360DB();
		A360DB = A360DBextract.getA360DB();
		ks_extaractProductMasterData();
		kss_extaractProductMasterData();
		writeDataToFile();
	}
	
	private static void writeDataToFile() throws IOException {
		File ksSamplerFile = new File(ext.getPropValue("plumslice_storefront_assignment_sampler_ks")
				.replaceAll("<<Iteration>>", "1"));
		FileUtils.copyFile(new File(ext.getPropValue("plumslice_storefront_assignment_mastercopy_ks")), ksSamplerFile);
		File kssSamplerFile = new File(ext.getPropValue("plumslice_storefront_assignment_sampler_kss")
				.replaceAll("<<Iteration>>", "1"));
		FileUtils.copyFile(new File(ext.getPropValue("plumslice_storefront_assignment_mastercopy_kss")),
				kssSamplerFile);

		FileInputStream ksInputStream = new FileInputStream(ksSamplerFile);
		Workbook ksWorkbook = WorkbookFactory.create(ksInputStream);
		Sheet ksSheet = ksWorkbook.getSheetAt(0);
		int ksRowCount = ksSheet.getLastRowNum();

		FileInputStream kssInputStream = new FileInputStream(kssSamplerFile);
		Workbook kssWorkbook = WorkbookFactory.create(kssInputStream);
		Sheet kssSheet = kssWorkbook.getSheetAt(0);
		int kssRowCount = kssSheet.getLastRowNum();
		A360DB vA360DB =  new A360DB();
		for (String ksmasterId : KS_productsList) {
			if(A360DB.containsKey(ksmasterId) ) {
				
				vA360DB = A360DB.get(ksmasterId)	;
				if(!ks_vaeriationList.contains(vA360DB.getVariationId()+vA360DB.getStyleId())) {
				Row ksRow = ksSheet.createRow(++ksRowCount);
				int columnCount_ks = 0;
				Cell cell = ksRow.createCell(columnCount_ks);
				cell.setCellValue(vA360DB.getStyleId());
				columnCount_ks++;
				cell = ksRow.createCell(columnCount_ks);
				cell.setCellValue(vA360DB.getVariationId());
				columnCount_ks++;
				cell = ksRow.createCell(columnCount_ks);
				cell.setCellValue("KSUSRT");
				columnCount_ks++;
				cell = ksRow.createCell(columnCount_ks);
				cell.setCellValue("KSUSRT");
				ks_vaeriationList.add(vA360DB.getVariationId()+vA360DB.getStyleId());
				}
			}

			
		}
		ksInputStream.close();
		FileOutputStream ksoutputStream = new FileOutputStream(ksSamplerFile);
		ksWorkbook.write(ksoutputStream);
		ksWorkbook.close();
		ksoutputStream.close();
		
	for (String kssmasterId : KSS_productsList) {
		if(A360DB.containsKey(kssmasterId)) {
			vA360DB = A360DB.get(kssmasterId)	;
			if(!kss_vaeriationList.contains(vA360DB.getVariationId()+vA360DB.getStyleId())) {
			Row kssRow = kssSheet.createRow(++kssRowCount);
			int columnCount_kss = 0;
			Cell cell = kssRow.createCell(columnCount_kss);
			cell.setCellValue(vA360DB.getStyleId());
			columnCount_kss++;
			cell = kssRow.createCell(columnCount_kss);
			cell.setCellValue(vA360DB.getVariationId());
			columnCount_kss++;
			cell = kssRow.createCell(columnCount_kss);
			cell.setCellValue("KSUSSUR");
			columnCount_kss++;
			cell = kssRow.createCell(columnCount_kss);
			cell.setCellValue("KSUSSUR");
			kss_vaeriationList.add(vA360DB.getVariationId()+vA360DB.getStyleId());
			}
		}

		
		}
	kssInputStream.close();
	FileOutputStream kssoutputStream = new FileOutputStream(kssSamplerFile);
	kssWorkbook.write(kssoutputStream);
	kssWorkbook.close();
	kssoutputStream.close();
		
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
	
}
