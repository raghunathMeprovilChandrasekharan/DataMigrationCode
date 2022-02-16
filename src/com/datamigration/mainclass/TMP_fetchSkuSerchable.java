package com.datamigration.mainclass;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.datamigration.javaclass.Catalog;
import com.datamigration.javaclass.ComplexTypeProduct;
import com.datamigration.javaclass.ComplexTypeProductVariations;
import com.datamigration.javaclass.ComplexTypeProductVariationsVariant;
import com.datamigration.javaclass.ComplexTypeProductVariationsVariants;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttribute;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttributes;
import com.datamigration.pojo.A360DB;

public class TMP_fetchSkuSerchable {
	static ExtractMainProperties ext = new ExtractMainProperties();
	private static HashMap<String, A360DB> A360DB = new HashMap<String, A360DB>();
	private static HashMap<String, ComplexTypeProduct> derivedList = new HashMap<String, ComplexTypeProduct>();
	private static List<String> singleList = new ArrayList<String>();
	private static HashMap<String,HashMap<String,HashMap<String,String>>> finalList = new HashMap<String,HashMap<String,HashMap<String,String>>>();

	public static void main(String[] args) throws Exception {
		New_ExtractA360Data A360DBextract = new New_ExtractA360Data();
		New_ExtractA360Data.extractA360DB();
		A360DB = A360DBextract.getA360DB();
		fetchSkudetail();
		kss_extaractProductDetails();
		ks_extaractProductDetails();
		formFinalList();
		printSearchable();
	}

	private static void printSearchable() {
		for(String itemId : finalList.keySet()) {
			HashMap<String,HashMap<String,String>> variationMap = finalList.get(itemId);
			for(String variationId : variationMap.keySet()) {
				HashMap<String,String> skuList = variationMap.get(variationId);
				for(String skuID : skuList.keySet()) {
						ComplexTypeProduct skuComplexTypeProduct = derivedList.get(skuList.get(skuID));
						if(skuComplexTypeProduct != null) {
							String searchable = "";
							if(skuComplexTypeProduct.getSearchableFlag() != null && skuComplexTypeProduct.getSearchableFlag().size() > 0) {
								if(skuComplexTypeProduct.getSearchableFlag().get(0).isValue()) {
									searchable = "Y";
								}else {
									searchable = "N";
								}
							}else {
								searchable = "";
							}
							System.out.println(itemId+","+variationId+","+skuID+","+searchable);
						}
						
					}
				
			}
			
		}
		
	}

	
	private static void formFinalList() {
		for(String productId: singleList) {
			A360DB itemObj = A360DB.get(productId);
			if(itemObj == null) {
				continue;
			}
			String itemId = itemObj.getStyleId();
			String SkuId = itemObj.getSkuId();
			String variationId = itemObj.getVariationId();
			HashMap<String,HashMap<String,String>> variationMap;
			HashMap<String,String> skuList;
			if(finalList.containsKey(itemId)) {
				variationMap = finalList.get(itemId);				
			}else {
				variationMap = new HashMap<String,HashMap<String,String>>();				
			}
			
			if(variationMap.containsKey(variationId)) {
				skuList =  variationMap.get(variationId);			
			}else {
				 skuList = new HashMap<String,String>();
				
			}
			if(!skuList.containsKey(SkuId)){
				skuList.put(SkuId,productId);
			}
			
			variationMap.put(variationId, skuList);
			finalList.put(itemId, variationMap);
			
		}
		System.out.println(finalList.size());
	}
	
	

	private static void ks_extaractProductDetails() throws Exception {
		File file = new File(ext.getPropValue("ks-master-catalog-path-item"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		for (ComplexTypeProduct product : que.getProduct()) {
			if(product.getVariations() != null) {
				ComplexTypeProductVariations variations = product.getVariations();
				if (variations != null && variations.getVariants() != null) {
					for (ComplexTypeProductVariationsVariants variant : variations.getVariants()) {
						for (ComplexTypeProductVariationsVariant vComplexTypeProductVariationsVariant : variant
								.getVariant()) {
							singleList.add(vComplexTypeProductVariationsVariant.getProductId().trim());
						}
					}
				}
			}
		}
		
		
	}

	private static void kss_extaractProductDetails() throws Exception {
		File file = new File(ext.getPropValue("kss-master-catalog-path-item"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		for (ComplexTypeProduct product : que.getProduct()) {
			if(product.getVariations() != null) {
				ComplexTypeProductVariations variations = product.getVariations();
				if (variations != null && variations.getVariants() != null) {
					for (ComplexTypeProductVariationsVariants variant : variations.getVariants()) {
						for (ComplexTypeProductVariationsVariant vComplexTypeProductVariationsVariant : variant
								.getVariant()) {
							singleList.add(vComplexTypeProductVariationsVariant.getProductId().trim());
						}
					}
				}
			}
		}
		
	}

	private static void fetchSkudetail() throws Exception {
		File file = new File(ext.getPropValue("master-catalog-path"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		for (ComplexTypeProduct prod : que.getProduct()) {
			if("810012198738".equals(prod.getProductId())) {
				System.out.println(prod.getVariations() );
			}
			if (prod.getVariations() == null && A360DB.containsKey(prod.getProductId())) {
				derivedList.put(prod.getProductId(), prod);
			}
		}

	}

}
