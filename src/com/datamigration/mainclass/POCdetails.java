package com.datamigration.mainclass;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.datamigration.javaclass.Catalog;
import com.datamigration.javaclass.ComplexTypeProduct;
import com.datamigration.javaclass.ComplexTypeProductVariations;
import com.datamigration.javaclass.ComplexTypeProductVariationsVariant;
import com.datamigration.javaclass.ComplexTypeProductVariationsVariants;
import com.datamigration.pojo.A360DB;

public class POCdetails {

	static ExtractMainProperties ext = new ExtractMainProperties();
	static TreeMap<String,String> ksListFromSfcc = new TreeMap<String,String>();
	private static HashMap<String, A360DB> A360DB = new HashMap<String, A360DB>();
	
	public static void main(String[] args) throws JAXBException, Exception {
		New_ExtractA360Data A360DBextract = new New_ExtractA360Data();
		A360DBextract.extractA360DB();
		A360DB = A360DBextract.getA360DB();
		ks_extaractProductMasterData();

	}
	
	private static void ks_extaractProductMasterData() throws JAXBException, Exception {
		Set<String> onewithHash = new HashSet<String>();
		File file = new File(ext.getPropValue("ks-master-catalog-path-item"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		List<String> KS_productsList = new ArrayList<String>();
			for (ComplexTypeProduct product : que.getProduct()) {
				 KS_productsList = new ArrayList<String>();
				if(product.getVariations() != null) {
					
						if(	!A360DB.containsKey(product.getProductId().split("-")[0])){
							System.out.println(product.getProductId().split("-")[0]);
						}
					
					
					if(product.getProductId().indexOf("-") > -1) {
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
						Collections.sort(KS_productsList);
						if(KS_productsList.size() > 0) {
							ksListFromSfcc.put(product.getProductId(),selectList(KS_productsList));
						}
						onewithHash.add(product.getProductId().split("-")[0]	);	
								
					}
				}
			}
			
			
			for (ComplexTypeProduct product : que.getProduct()) {
				if(product.getVariations() != null) {
					if(onewithHash.contains(product.getProductId())) {
						ComplexTypeProductVariations variations = product.getVariations();
						if (variations != null && variations.getVariants() != null) {
							for (ComplexTypeProductVariationsVariants variant : variations.getVariants()) {
								for (ComplexTypeProductVariationsVariant vComplexTypeProductVariationsVariant : variant
										.getVariant()) {
									KS_productsList.add(vComplexTypeProductVariationsVariant.getProductId().trim());
								}
							}
						}
						Collections.sort(KS_productsList);
						if(KS_productsList.size() > 0) {
							ksListFromSfcc.put(product.getProductId(),selectList(KS_productsList));
						}
					}
				}
			}
			
			
		
			
			
			//System.out.println(onewithHash.size());
			/**
			
			for ( String str : onewithHash) {
				if(	A360DB.containsKey(str)){
					System.out.println(str+"|"+str+"|'"+selectList(A360DB.get(str).getUpcNoList()));
				}
			}.
			**/
			
		
	}

	private static String selectList(List<String> kS_productsList) {
		StringBuilder sb = new StringBuilder();
		for(String str : kS_productsList) {
			if(sb.length() > 0) {
				sb.append(",").append(str);
			}else {
				sb.append(str);
			}
			
			
		}
		return sb.toString();	}

}
