package com.datamigration.mainclass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.datamigration.pojo.A360DB;
import com.demandware.xml.impex.customer._2006_10_31.ComplexTypeCustomer;
import com.demandware.xml.impex.customer._2006_10_31.CustomerList;
import com.demandware.xml.impex.productlist._2009_10_28.CustomAttribute;
import com.demandware.xml.impex.productlist._2009_10_28.CustomAttributes;
import com.demandware.xml.impex.productlist._2009_10_28.Items;
import com.demandware.xml.impex.productlist._2009_10_28.Owner;
import com.demandware.xml.impex.productlist._2009_10_28.ProductItem;
import com.demandware.xml.impex.productlist._2009_10_28.ProductList;
import com.demandware.xml.impex.productlist._2009_10_28.ProductLists;
import com.demandware.xml.impex.productlist._2009_10_28.Purchases;
import com.demandware.xml.impex.productlist._2009_10_28.SimpleTypeProductListType;

public class New_ExtractCustomerProductList {
	static List<ProductList> trasformeedList = new ArrayList<ProductList>();
	static ProductLists FinalProductLists = new ProductLists();
	static ExtractMainProperties ext = new ExtractMainProperties();
	private static HashMap<String, A360DB> A360DB = new HashMap<String, A360DB>();
	static String wish_list = "wish_list";
	static HashMap<String, List<ProductList>> wish_registry_Map = new HashMap<String, List<ProductList>>();

	
	static HashMap<String, String> custToNameMap = new HashMap<String, String>();
	
	
	public static void main(String[] args) throws Exception {
		tmpCustMap();
		New_ExtractA360Data A360DBextract = new New_ExtractA360Data();
		A360DBextract.extractA360DB();
		A360DB = A360DBextract.getA360DB();
		extractProductList();
		 createBatchFile();
	}

	private static void tmpCustMap() throws JAXBException {
		CustomerList que = new CustomerList();
		File legacyfile = new File("C:\\Users\\raghunath\\Downloads\\KS_OneAccount_14022022.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(CustomerList.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		que = (CustomerList) jaxbUnmarshaller.unmarshal(legacyfile);
		List<ComplexTypeCustomer> customerList = new ArrayList<ComplexTypeCustomer>();
		customerList = que.getCustomer();
		for(ComplexTypeCustomer vComplexTypeCustomer : customerList) {
			String keyEMail = vComplexTypeCustomer.getCredentials().getLogin().toUpperCase();
			if(custToNameMap.containsKey(keyEMail)) {
				System.out.println(keyEMail);
			}
			custToNameMap.put(keyEMail,vComplexTypeCustomer.getCustomerNo());			
			
			
			
		}
		System.out.println(custToNameMap.get("shweta.jain3@publicissapient.com".toUpperCase()));
		System.out.println(">>>>>>>>>>"+custToNameMap.size());
		
	}

	private static void extractProductList() throws Exception {
		File file = new File(ext.getPropValue("product_list_path"));
		JAXBContext jaxbContext = JAXBContext.newInstance(ProductLists.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		ProductLists que = (ProductLists) jaxbUnmarshaller.unmarshal(file);
		List<ProductList> shoppingList;
		int exceptionno = 0;
		int Noexceptionno = 0;
		List<ProductList> giftWishList;
		int shoopingList = 0;
		int giiftReg = 0;
		for (ProductList prdoD : que.getProductList()) {
			SimpleTypeProductListType vSimpleTypeProductListType = prdoD.getType();
			if (prdoD.getItems() != null && prdoD.getItems().getProductItem() != null
					&& prdoD.getItems().getProductItem().size() > 0) {
				
				String customerNo = prdoD.getOwner().getCustomerNo();
				
				if(custToNameMap.size() > 0 ) {
					if("shweta.jain3@publicissapient.com".equalsIgnoreCase(prdoD.getOwner().getEmail().toUpperCase())){
						System.out.println(prdoD.getOwner());
					}
					customerNo  = custToNameMap.get(prdoD.getOwner().getEmail().toUpperCase());
				}
				
				if ("shopping_list".equals(vSimpleTypeProductListType.value())) {
					if (wish_registry_Map.containsKey(customerNo)) {
						shoppingList = wish_registry_Map.get(customerNo);
					} else {
						shoppingList = new ArrayList<ProductList>();
					}

					shoppingList.add(prdoD);
					wish_registry_Map.put(customerNo, shoppingList);
					shoopingList++;
				} else if ("gift_registry".equals(vSimpleTypeProductListType.value())) {
					if (wish_registry_Map.containsKey(customerNo)) {
						giftWishList = wish_registry_Map.get(customerNo);
					} else {
						giftWishList = new ArrayList<ProductList>();
					}
					giiftReg++;
					giftWishList.add(prdoD);
					wish_registry_Map.put(customerNo, giftWishList);
				}
				Noexceptionno++;
			}else {
				exceptionno++;
			}
		}
		System.out.println(shoopingList);
		System.out.println(giiftReg);
		System.out.println(wish_registry_Map.size());
		
		ProductList vProductList;
		for (String gift_registry : wish_registry_Map.keySet()) {
			vProductList = new ProductList();
			List<ProductList> tmpProductList = wish_registry_Map.get(gift_registry);
			Owner tmpOwner = tmpProductList.get(0).getOwner();
			tmpOwner.setCustomerNo(gift_registry);
			vProductList.setOwner(tmpProductList.get(0).getOwner());
			// SimpleTypeProductListType vSimpleTypeProductListType = new
			// SimpleTypeProductListType();
			vProductList.setType(SimpleTypeProductListType.WISH_LIST);
			vProductList.setPublic(tmpProductList.get(0).isPublic());
			if (tmpProductList.size() > 1) {
				for (ProductList listProductList : tmpProductList) {
					if (listProductList.getCustomAttributes() != null && vProductList.getCustomAttributes() == null) {
						vProductList.setCustomAttributes(listProductList.getCustomAttributes());
					}
					if (listProductList.getShippingAddress() != null && vProductList.getShippingAddress() == null) {
						vProductList.setShippingAddress(listProductList.getShippingAddress());
					}
				}
			} else {
				vProductList.setCustomAttributes(tmpProductList.get(0).getCustomAttributes());
				vProductList.setShippingAddress(tmpProductList.get(0).getShippingAddress());

			}
			
			Items Itemstoupdate = formItemsList(tmpProductList) ;
					if(Itemstoupdate != null ) {
						vProductList.setItems(formItemsList(tmpProductList));
						
						trasformeedList.add(vProductList);
					}

		}
System.out.println(trasformeedList.size());
		FinalProductLists.getProductList().addAll(trasformeedList);

	}

	private static Items formItemsList(List<ProductList> tmpProductList) {
		List<ProductItem> productItems = new ArrayList<ProductItem>();
		HashMap<String, List<ProductItem>> tempProductItemMap = new HashMap<String, List<ProductItem>>();
		HashMap<String, CustomAttribute> tempCustomAttributeMap = new HashMap<String, CustomAttribute>();
		for (ProductList tempProductList : tmpProductList) {
			Items vItems = tempProductList.getItems();
			for (ProductItem tmpProductItem : vItems.getProductItem()) {
				List<ProductItem> iterValue;
				if (tempProductItemMap.containsKey(tmpProductItem.getProductId())) {
					iterValue = tempProductItemMap.get(tmpProductItem.getProductId());
				} else {
					iterValue = new ArrayList<ProductItem>();
				}
				iterValue.add(tmpProductItem);

				tempProductItemMap.put(tmpProductItem.getProductId(), iterValue);
			}
		}
		ProductItem vProductItem;
		for (String productId : tempProductItemMap.keySet()) {
			vProductItem = new ProductItem();
			if (A360DB.containsKey(productId)) {
				vProductItem.setProductId(A360DB.get(productId).getSkuId());
				int vcount = 0;
				int priority = 0;
				boolean publicvalue = false;
				for (ProductItem valProductItem : tempProductItemMap.get(productId)) {
					if (priority < valProductItem.getPriority()) {
						priority = valProductItem.getPriority();
					}

					if (!publicvalue && valProductItem.isPublic()) {
						publicvalue = true;
					}
					if (valProductItem.getCustomAttributes() != null) {
						for (CustomAttribute vCustomAttribute : valProductItem.getCustomAttributes()
								.getCustomAttribute()) {
							tempCustomAttributeMap.put(vCustomAttribute.getAttributeId(), vCustomAttribute);
						}
					}

					vcount++;
				}
				if (tempCustomAttributeMap.size() > 0) {
					CustomAttributes vCustomAttributes = new CustomAttributes();

					for (String attributeId : tempCustomAttributeMap.keySet()) {
						vCustomAttributes.getCustomAttribute().add(tempCustomAttributeMap.get(attributeId));
					}
					vProductItem.setCustomAttributes(vCustomAttributes);
				}
				vProductItem.setQuantity(BigDecimal.valueOf(vcount));
				vProductItem.setPriority(priority);
				vProductItem.setPublic(publicvalue);
				vProductItem.setPurchases(new Purchases());	
				productItems.add(vProductItem);
			}else {
				//System.out.println("not in A360 "+ productId);
			}
			
		}
		Items itVal = null;
		if(productItems.size() > 0){
			itVal = new Items();
			itVal.getProductItem().addAll(productItems);
		}
		return itVal;
	}

	private static void createBatchFile() throws JAXBException, FileNotFoundException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ProductLists.class);
		 Marshaller marshallerObj = jaxbContext.createMarshaller();  
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
		marshallerObj.marshal(FinalProductLists, new FileOutputStream("WishListFIlesCOmbine_tmp.xml")); 
	
	}
	
	
}
