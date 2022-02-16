package com.datamigration.mainclass;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.datamigration.javaclass.Catalog;
import com.datamigration.javaclass.ComplexTypeProduct;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttribute;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttributes;
import com.datamigration.pojo.A360DB;
import com.datamigration.pojo.PlumSliceItemDetailsForFeed;

public class ExtractProductVedioUrl {
	static ExtractMainProperties ext = new ExtractMainProperties();
	private static HashMap<String, A360DB> A360DB = new HashMap<String, A360DB>();
	public static void main(String[] args) throws Exception {
		New_ExtractA360Data A360DBextract = new New_ExtractA360Data();
		New_ExtractA360Data.extractA360DB();
		A360DB = A360DBextract.getA360DB();
		File file_ks = new File("C:\\Users\\raghunath\\Downloads\\vedioLink-Kss.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file_ks);
		PlumSliceItemDetailsForFeed vPlumSliceItemDetailsForFeed;
		for (ComplexTypeProduct product : que.getProduct()) {
			System.out.println((A360DB.containsKey(product.getProductId()) ? A360DB.get(product.getProductId()).getSkuId() :  product.getProductId()  )+","+extractAttributeFromCustomAttribute("productVideoURL", product, false)+",surprise");
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
}

