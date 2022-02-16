package com.datamigration.mainclass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.datamigration.javaclass.Catalog;
import com.datamigration.javaclass.ComplexTypeCategory;
import com.datamigration.javaclass.ComplexTypeCategoryAssignment;
import com.datamigration.javaclass.ComplexTypeProduct;
import com.datamigration.javaclass.ComplexTypeProductClassificationCategory;
import com.datamigration.javaclass.ComplexTypeProductVariations;
import com.datamigration.javaclass.ComplexTypeProductVariationsVariant;
import com.datamigration.javaclass.ComplexTypeProductVariationsVariants;
import com.datamigration.javaclass.SharedTypeLocalizedString;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttribute;
import com.datamigration.javaclass.SharedTypeSiteSpecificCustomAttributes;
import com.datamigration.pojo.A360DB;
import com.datamigration.pojo.CategoryProductRelation;

public class ExtractMasterData {
	static ExtractMainProperties ext = new ExtractMainProperties();
	private static HashMap<String, A360DB> A360DB = new HashMap<String, A360DB>();
	private static HashMap<String, String> categoryProductRelation = new HashMap<String, String>();
	private static List<CategoryProductRelation> vCategoryProductRelationList = new ArrayList<CategoryProductRelation>();
	
	private static HashMap<String,String> ksCategoryMap = new HashMap<String,String>();
	private static HashMap<String,String> kssCategoryMap = new HashMap<String,String>();
	
	private static HashMap<String,String> childToMaster = new HashMap<String,String>();
	
	
	private static List<String> itemCatList = new ArrayList<String>();

	public static List<CategoryProductRelation> getvCategoryProductRelationList() {
		return vCategoryProductRelationList;
	}

	public static void setvCategoryProductRelationList(List<CategoryProductRelation> vCategoryProductRelationList) {
		ExtractMasterData.vCategoryProductRelationList = vCategoryProductRelationList;
	}

	public static void main(String[] args) {
		try {
			System.out.println("--Starting--");
			executeLoad();
			System.out.println("--Ending--");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void executeLoad() throws JAXBException, IOException {
	/**
		ksCategoryMap.put("ks-accessories","ks-accessories");
		ksCategoryMap.put("ks-accessories-cosmetic-cases","ks-accessories=>ks-accessories-cosmetic-cases");
		ksCategoryMap.put("ks-accessories-eyewear","ks-accessories=>ks-accessories-eyewear");
		ksCategoryMap.put("ks-accessories-hats-hair-accessories","ks-accessories=>ks-accessories-hats-hair-accessories");
		ksCategoryMap.put("ks-accessories-keychains","ks-accessories=>ks-accessories-keychains");
		ksCategoryMap.put("ks-accessories-legwear","ks-accessories=>ks-accessories-legwear");
		ksCategoryMap.put("ks-accessories-scarves","ks-accessories=>ks-accessories-scarves");
		ksCategoryMap.put("ks-accessories-tech","ks-accessories=>ks-accessories-tech");
		ksCategoryMap.put("ks-accessories-tech-accessories","ks-accessories=>ks-accessories-tech=>ks-accessories-tech-accessories");
		ksCategoryMap.put("ks-accessories-tech-iphone-cases","ks-accessories=>ks-accessories-tech=>ks-accessories-tech-iphone-cases");
		ksCategoryMap.put("ks-accessories-tech-laptop-bags","ks-accessories=>ks-accessories-tech=>ks-accessories-tech-laptop-bags");
		ksCategoryMap.put("ks-accessories-travel-accessories","ks-accessories=>ks-accessories-travel-accessories");
		ksCategoryMap.put("ks-accessories-view-all","ks-accessories=>ks-accessories-view-all");
		ksCategoryMap.put("ks-accessories-watches","ks-accessories=>ks-accessories-watches");
		ksCategoryMap.put("ks-accessories-wearable-tech","ks-accessories=>ks-accessories-wearable-tech");
		ksCategoryMap.put("ks-accesssories-beauty","ks-accessories=>ks-accesssories-beauty");
		ksCategoryMap.put("ks-bedding","ks-home=>ks-bedding");
		ksCategoryMap.put("ks-bedding-duvets","ks-home=>ks-bedding=>ks-bedding-duvets");
		ksCategoryMap.put("ks-bifold-wallets","ks-wallets=>ks-bifold-wallets");
		ksCategoryMap.put("ks-cards-and-gifts","ks-gifts=>ks-gifts-occasions=>ks-cards-and-gifts");
		ksCategoryMap.put("ks-clothing","ks-clothing");
		ksCategoryMap.put("ks-clothing-bottoms","ks-clothing=>ks-clothing-bottoms");
		ksCategoryMap.put("ks-clothing-dresses-jumpsuits","ks-clothing=>ks-clothing-dresses-jumpsuits");
		ksCategoryMap.put("ks-clothing-dresses-jumpsuits-day","ks-clothing=>ks-clothing-dresses-jumpsuits=>ks-clothing-dresses-jumpsuits-day");
		ksCategoryMap.put("ks-clothing-dresses-jumpsuits-evening","ks-clothing=>ks-clothing-dresses-jumpsuits=>ks-clothing-dresses-jumpsuits-evening");
		ksCategoryMap.put("ks-clothing-dresses-jumpsuits-midi","ks-clothing=>ks-clothing-dresses-jumpsuits=>ks-clothing-dresses-jumpsuits-midi");
		ksCategoryMap.put("ks-clothing-dresses-jumpsuits-mini","ks-clothing=>ks-clothing-dresses-jumpsuits=>ks-clothing-dresses-jumpsuits-mini");
		ksCategoryMap.put("ks-clothing-dresses-jumpsuits-wear-to-work","ks-clothing=>ks-clothing-dresses-jumpsuits=>ks-clothing-dresses-jumpsuits-wear-to-work");
		ksCategoryMap.put("ks-clothing-matching-sets","ks-clothing=>ks-clothing-matching-sets");
		ksCategoryMap.put("ks-dresses-bridal","ks-clothing=>ks-clothing-shops=>ks-clothing-shops-the-bridal-shop");
		ksCategoryMap.put("ks-clothing-sleepwear","ks-clothing=>ks-clothing-sleepwear");
		ksCategoryMap.put("ks-clothing-swimwear","ks-clothing=>ks-clothing-swimwear");
		ksCategoryMap.put("ks-clothing-tops","ks-clothing=>ks-clothing-tops");
		ksCategoryMap.put("ks-clothing-view-all","ks-clothing=>ks-clothing-view-all");
		ksCategoryMap.put("ks-coffee-and-tea","ks-home=>ks-home-kitchenware=>ks-coffee-and-tea");
		ksCategoryMap.put("ks-coin-purses-keychain wallets","ks-wallets=>ks-coin-purses-keychain wallets");
		ksCategoryMap.put("ks-decorative-pillows","ks-home=>ks-home-accents-and-decor=>ks-decorative-pillows");
		ksCategoryMap.put("ks-gift-cards","ks-gifts=>ks-gift-cards");
		ksCategoryMap.put("ks-gift-guide-100-and-under","ks-gifts=>ks-gift-guide-100-and-under");
		ksCategoryMap.put("ks-gift-guide-200-and-under","ks-gifts=>ks-gift-guide-200-and-under");
		ksCategoryMap.put("ks-gift-guide-50-and-under","ks-gifts=>ks-gift-guide-50-and-under");
		ksCategoryMap.put("ks-gift-guide-for-him","ks-gifts=>ks-gifts-occasions=>ks-gift-guide-for-him");
		ksCategoryMap.put("ks-gifts","ks-gifts");
		ksCategoryMap.put("ks-gifts-anniversary","ks-gifts=>ks-gifts-occasions=>ks-gifts-anniversary");
		ksCategoryMap.put("ks-gifts-best-sellers","ks-gifts=>ks-gifts-best-sellers");
		ksCategoryMap.put("ks-gifts-birthday","ks-gifts=>ks-gifts-occasions=>ks-gifts-birthday");
		ksCategoryMap.put("ks-gift-services","ks-gifts=>ks-gift-services");
		ksCategoryMap.put("ks-gifts-housewarming","ks-gifts=>ks-gifts-occasions=>ks-gifts-housewarming");
		ksCategoryMap.put("ks-gifts-little-indulgences","ks-gifts=>ks-gifts-little-indulgences");
		ksCategoryMap.put("ks-gifts-luxury","ks-gifts=>ks-gifts-luxury");
		ksCategoryMap.put("ks-gifts-view-all","ks-gifts=>ks-gifts-view-all");
		ksCategoryMap.put("ks-handbags","ks-handbags");
		ksCategoryMap.put("ks-all-day-tote","ks-handbags=>ks-handbags-collections=>ks-handbags-all-day-tote");
		ksCategoryMap.put("ks-travel-bags","ks-handbags=>ks-handbags-backpacks");
		ksCategoryMap.put("ks-handbags-camera-bags","ks-handbags=>ks-handbags-cross-body=>ks-handbags-camera-bags");
		ksCategoryMap.put("ks-handbags-classic-shop","ks-handbags=>ks-handbags-shops=>ks-handbags-classic-shop");
		ksCategoryMap.put("ks-handbags-cross-body","ks-handbags=>ks-handbags-cross-body");
		ksCategoryMap.put("ks-knott-shop","ks-handbags=>ks-handbags-collections=>ks-handbags-knott-shop");
		ksCategoryMap.put("ks-matching-handbags-and-wallets","ks-handbags=>ks-handbags-shops=>ks-handbags-matching-handbags-and-wallets");
		ksCategoryMap.put("ks-handbags-new-essentials","ks-handbags=>ks-handbags-shops=>ks-handbags-new-essentials");
		ksCategoryMap.put("ks-handbags-new-nylon","ks-handbags=>ks-handbags-shops=>ks-handbags-new-nylon");
		ksCategoryMap.put("ks-handbags-personalization","ks-handbags=>ks-handbags-shops=>ks-handbags-personalization");
		ksCategoryMap.put("ks-handbags-phone-crossbody-bags","ks-handbags=>ks-handbags-cross-body=>ks-handbags-phone-crossbody-bags");
		ksCategoryMap.put("ks-handbags-phone-crossbody-bags-TECH","ks-accessories=>ks-accessories-tech=>ks-handbags-phone-crossbody-bags-TECH");
		ksCategoryMap.put("ks-handbags-satchels","ks-handbags=>ks-handbags-satchels");
		ksCategoryMap.put("ks-handbags-shoulder-bags","ks-handbags=>ks-handbags-shoulder-bags");
		ksCategoryMap.put("ks-handbags-new-logo","ks-handbags=>ks-handbags-collections=>ks-handbags-the-spade-flower-shop");
		ksCategoryMap.put("ks-handbags-totes","ks-handbags=>ks-handbags-totes");
		ksCategoryMap.put("ks-handbags-work-totes","ks-handbags=>ks-handbags-work-totes");
		ksCategoryMap.put("ks-holiday-dressing-shop","ks-new-arrivals=>ks-new-arrivals-shops=>ks-holiday-dressing-shop");
		ksCategoryMap.put("ks-home","ks-home");
		ksCategoryMap.put("ks-home-accents-and-decor","ks-home=>ks-home-accents-and-decor");
		ksCategoryMap.put("ks-home-accents-and-decor-view-all","ks-home=>ks-home-accents-and-decor=>ks-home-accents-and-decor-view-all");
		ksCategoryMap.put("ks-home-agendas-and-journals","ks-home=>ks-home-office-accessories=>ks-home-agendas-and-journals");
		ksCategoryMap.put("ks-home-ceiling-lighting","ks-home=>ks-home-lighting=>ks-home-ceiling-lighting");
		ksCategoryMap.put("ks-home-cocktail-and-bar","ks-home=>ks-home-kitchenware=>ks-home-cocktail-and-bar");
		ksCategoryMap.put("ks-home-desk-accessories","ks-home=>ks-home-office-accessories=>ks-home-desk-accessories");
		ksCategoryMap.put("ks-home-dinnerware-and-flatwware","ks-home=>ks-home-kitchenware=>ks-home-dinnerware-and-flatware");
		ksCategoryMap.put("ks-home-frames","ks-home=>ks-home-accents-and-decor=>ks-home-frames");
		ksCategoryMap.put("ks-home-hosting-supplies","ks-home=>ks-home-shops=>ks-home-hosting-supplies");
		ksCategoryMap.put("ks-home-id-holder","ks-home=>ks-home-office-accessories=>ks-home-id-holder");
		ksCategoryMap.put("ks-home-jewelry-holders","ks-home=>ks-home-accents-and-decor=>ks-home-jewelry-holders");
		ksCategoryMap.put("ks-home-kitchenware","ks-home=>ks-home-kitchenware");
		ksCategoryMap.put("ks-home-kitchenware-view-all","ks-home=>ks-home-kitchenware=>ks-home-kitchenware-view-all");
		ksCategoryMap.put("ks-home-lighting","ks-home=>ks-home-lighting");
		ksCategoryMap.put("ks-home-paperless-post","ks-home=>ks-home-collections=>ks-home-paperless-post");
		ksCategoryMap.put("ks-home-serveware","ks-home=>ks-home-lighting=>ks-home-serveware");
		ksCategoryMap.put("ks-home-table-lamps","ks-home=>ks-home-lighting=>ks-home-table-lamps");
		ksCategoryMap.put("ks-home-urban-stems","ks-home=>ks-home-collections=>ks-home-urban-stems");
		ksCategoryMap.put("ks-home-view-all","ks-home=>ks-home-view-all");
		ksCategoryMap.put("ks-home-wall-lighting","ks-home=>ks-home-lighting=>ks-home-wall-lighting");
		ksCategoryMap.put("ks-jewelry","ks-jewelry");
		ksCategoryMap.put("ks-jewelry-bracelets","ks-jewelry=>ks-jewelry-bracelets");
		ksCategoryMap.put("ks-jewelry-bridal","ks-jewelry=>ks-jewelry-shops=>ks-jewelry-bridal");
		ksCategoryMap.put("ks-jewelry-dangle","ks-jewelry=>ks-jewelry-earrings=>ks-jewelry-dangle");
		ksCategoryMap.put("ks-jewelry-earrings","ks-jewelry=>ks-jewelry-earrings");
		ksCategoryMap.put("ks-jewelry-necklaces","ks-jewelry=>ks-jewelry-necklaces");
		ksCategoryMap.put("ks-personalized-jewelry","ks-jewelry=>ks-jewelry-shops=>ks-jewelry-personalization");
		ksCategoryMap.put("ks-jewelry-rings","ks-jewelry=>ks-jewelry-rings");
		ksCategoryMap.put("ks-jewelry-studs","ks-jewelry=>ks-jewelry-earrings=>ks-jewelry-studs");
		ksCategoryMap.put("ks-jewelry-view-all","ks-jewelry=>ks-jewelry-view-all");
		ksCategoryMap.put("ks-jewelry-watches","ks-jewelry=>ks-jewelry-watches");
		ksCategoryMap.put("ks-jewlery-hoops","ks-jewelry=>ks-jewelry-earrings=>ks-jewlery-hoops");
		ksCategoryMap.put("ks-keds-shoes","ks-shoes=>ks-shoes-collection=>ks-keds-shoes");
		ksCategoryMap.put("ks-kitchen-accessories","ks-home=>ks-home-kitchenware=>ks-kitchen-accessories");
		ksCategoryMap.put("ks-kitchen-organization-and-storage","ks-home=>ks-home-kitchenware=>ks-kitchen-organization-and-storage");
		ksCategoryMap.put("ks-new-arrivals","ks-new-arrivals");
		ksCategoryMap.put("ks-new-arrivals-accessories","ks-new-arrivals=>ks-new-arrivals-accessories");
		ksCategoryMap.put("ks-new-arrivals-clothing","ks-new-arrivals=>ks-new-arrivals-clothing");
		ksCategoryMap.put("ks-new-arrivals-handbags","ks-new-arrivals=>ks-new-arrivals-handbags");
		ksCategoryMap.put("ks-new-arrivals-home-stationery","ks-new-arrivals=>ks-new-arrivals-home-stationery");
		ksCategoryMap.put("ks-new-arrivals-jewelry","ks-new-arrivals=>ks-new-arrivals-jewelry");
		ksCategoryMap.put("ks-new-arrivals-shoes","ks-new-arrivals=>ks-new-arrivals-shoes");
		ksCategoryMap.put("ks-new-arrivals-view-all","ks-new-arrivals=>ks-new-arrivals-view-all");
		ksCategoryMap.put("ks-new-arrivals-wallets","ks-new-arrivals=>ks-new-arrivals-wallets");
		ksCategoryMap.put("ks-new-nylon","ks-new-arrivals=>ks-new-arrivals-shops=>ks-new-nylon");
		ksCategoryMap.put("ks-pillows","ks-home=>ks-bedding=>ks-pillows");
		ksCategoryMap.put("ks-shoes","ks-shoes");
		ksCategoryMap.put("ks-shoes-boots","ks-shoes=>ks-shoes-boots-booties");
		ksCategoryMap.put("ks-shoes-flats","ks-shoes=>ks-shoes-flats");
		ksCategoryMap.put("ks-shoes-heels","ks-shoes=>ks-shoes-heels");
		ksCategoryMap.put("ks-shoes-kids","ks-shoes=>ks-shoes-kids");
		ksCategoryMap.put("ks-shoes-sandals","ks-shoes=>ks-shoes-sandals");
		ksCategoryMap.put("ks-shoes-slippers","ks-shoes=>ks-shoes-slippers");
		ksCategoryMap.put("ks-shoes-sneakers","ks-shoes=>ks-shoes-sneakers");
		ksCategoryMap.put("ks-shoes-view-all","ks-shoes=>ks-shoes-view-all");
		ksCategoryMap.put("ks-wallets","ks-wallets");
		ksCategoryMap.put("ks-wallets-card-holders","ks-wallets=>ks-wallets-card-holders");
		ksCategoryMap.put("ks-wallets-crossbody","ks-wallets=>ks-wallets-crossbody");
		ksCategoryMap.put("ks-wallets-large-wallets","ks-wallets=>ks-wallets-large-wallets");
		ksCategoryMap.put("ks-wallets-cosmetic-cases","ks-wallets=>ks-wallets-makeup-bags-cosmetic-cases");
		ksCategoryMap.put("ks-handbags-matching-handbags-and-wallets","ks-wallets=>ks-wallets-shops=>ks-wallets-matching-handbags-and-wallets");
		ksCategoryMap.put("ks-wallets-view-all","ks-wallets=>ks-wallets-view-all");
		ksCategoryMap.put("ks-wallets-wristlets-pouches","ks-wallets=>ks-wallets-wristlets-pouches");
		kssCategoryMap.put("ks-accessories","kss-accessories");
		kssCategoryMap.put("ks-accessories-cold-weather","kss-accessories=>kss-accessories-cold-weather");
		kssCategoryMap.put("ks-accessories-keychains","kss-accessories=>kss-accessories-keychains");
		kssCategoryMap.put("ks-accessories-makeup-bags","kss-accessories=>kss-accessories-makeup-bags");
		kssCategoryMap.put("ks-accessories-scarves","kss-accessories=>kss-accessories-scarves");
		kssCategoryMap.put("ks-accessories-sunglasses","kss-accessories=>kss-accessories-sunglasses");
		kssCategoryMap.put("ks-accessories-tech","kss-accessories=>kss-accessories-tech");
		kssCategoryMap.put("ks-accessories-view-all","kss-accessories=>kss-accessories-view-all");
		kssCategoryMap.put("ks-bundles","kss-bundles");
		kssCategoryMap.put("ks-clothing-all","kss-clothing-all");
		kssCategoryMap.put("ks-clothing-dresses-jumpsuits","kss-clothing-all=>kss-clothing-dresses-jumpsuits");
		kssCategoryMap.put("ks-clothing-jackets-coats","kss-clothing-all=>kss-clothing-jackets-coats");
		kssCategoryMap.put("ks-clothing-lounge","kss-clothing-all=>kss-clothing-lounge");
		kssCategoryMap.put("ks-clothing-pajamas","kss-clothing-all=>kss-clothing-pajamas");
		kssCategoryMap.put("ks-clothing-sweaters","kss-clothing-all=>kss-clothing-sweaters");
		kssCategoryMap.put("ks-clothing-tops-blouses","kss-clothing-all=>kss-clothing-tops-blouses");
		kssCategoryMap.put("ks-clothing-view-all","kss-clothing-all=>kss-clothing-view-all");
		kssCategoryMap.put("ks-daily-deals","kss-daily-deals");
		kssCategoryMap.put("ks-gifts","kss-gifts");
		kssCategoryMap.put("ks-handbags","kss-handbags");
		kssCategoryMap.put("ks-handbags-backpacks-travel-bags","kss-handbags=>kss-handbags-backpackss-travel-bags");
		kssCategoryMap.put("ks-handbags-bucket-bags","kss-handbags=>kss-handbags-bucket-bags");
		kssCategoryMap.put("ks-handbags-crossbodies","kss-handbags=>kss-handbags-crossbodies");
		kssCategoryMap.put("ks-handbags-diaper-bags","kss-handbags=>kss-handbags-diaper-bags");
		kssCategoryMap.put("ks-handbags-laptop-bags","kss-handbags=>kss-handbags-laptop-bags");
		kssCategoryMap.put("ks-handbags-satchels","kss-handbags=>kss-handbags-satchels");
		kssCategoryMap.put("ks-handbags-shoulder-bags","kss-handbags=>kss-handbags-shoulder-bags");
		kssCategoryMap.put("ks-handbags-view-all","kss-handbags=>kss-handbags-view-all");
		kssCategoryMap.put("ks-jewelry","kss-jewelry");
		kssCategoryMap.put("ks-jewelry-bracelets","kss-jewelry=>kss-jewelry-bracelets");
		kssCategoryMap.put("ks-jewelry-earrings","kss-jewelry=>kss-jewelry-earrings");
		kssCategoryMap.put("ks-jewelry-necklaces","kss-jewelry=>kss-jewelry-necklaces");
		kssCategoryMap.put("ks-jewelry-rings","kss-jewelry=>kss-jewelry-rings");
		kssCategoryMap.put("ks-jewelry-view-all","kss-jewelry=>kss-jewelry-view-all");
		kssCategoryMap.put("ks-jewelry-watches","kss-jewelry=>kss-jewelry-watches");
		kssCategoryMap.put("ks-new-arrivals","kss-new-arrivals");
		kssCategoryMap.put("kss-clearance","kss-daily-deals=>kss-clearance");
		kssCategoryMap.put("kss-deal-of-the-day","kss-daily-deals=>kss-deal-of-the-day");
		kssCategoryMap.put("kss-gift-guide-shipping-guidelines","kss-gifts=>kss-gift-guide-shipping-guidelines");
		kssCategoryMap.put("kss-gifts-boxed-sets","kss-gifts=>kss-gifts-boxed-sets");
		kssCategoryMap.put("kss-gift-services","kss-gifts=>kss-gift-services");
		kssCategoryMap.put("kss-gifts-for-grads","kss-gifts=>kss-gifts-for-grads");
		kssCategoryMap.put("kss-gifts-jewelry-gifts","kss-gifts=>kss-gifts-jewelry-gifts");
		kssCategoryMap.put("kss-gifts-lp","kss-gifts=>kss-gifts-lp");
		kssCategoryMap.put("kss-gifts-perfect-pairs","kss-gifts=>kss-gifts-perfect-pairs");
		kssCategoryMap.put("kss-gifts-top-picks","kss-gifts=>kss-gifts-top-picks");
		kssCategoryMap.put("kss-gifts-under-100","kss-gifts=>kss-gifts-under-100");
		kssCategoryMap.put("kss-gifts-under-25","kss-gifts=>kss-gifts-under-25");
		kssCategoryMap.put("kss-gifts-under-50","kss-gifts=>kss-gifts-under-50");
		kssCategoryMap.put("kss-gifts-valentines-day","kss-gifts=>kss-gifts-valentines-day");
		kssCategoryMap.put("kss-gifts-view-all","kss-gifts=>kss-gifts-view-all");
		kssCategoryMap.put("kss-outlet","kss-outlet");
		kssCategoryMap.put("kss-outlet-accessories","kss-outlet=>kss-outlet-accessories");
		kssCategoryMap.put("kss-outlet-bopis","kss-outlet=>kss-outlet-bopis");
		kssCategoryMap.put("kss-outlet-clothing","kss-outlet=>kss-outlet-clothing");
		kssCategoryMap.put("kss-outlet-handbags","kss-outlet=>kss-outlet-handbags");
		kssCategoryMap.put("kss-outlet-jewelry","kss-outlet=>kss-outlet-jewelry");
		kssCategoryMap.put("kss-outlet-locator","kss-outlet=>kss-outlet-locator");
		kssCategoryMap.put("kss-outlet-shoes","kss-outlet=>kss-outlet-shoes");
		kssCategoryMap.put("kss-outlet-view-all","kss-outlet=>kss-outlet-view-all");
		kssCategoryMap.put("kss-outlet-wallets","kss-outlet=>kss-outlet-wallets");
		kssCategoryMap.put("ks-view-all","kss-view-all");
		kssCategoryMap.put("ks-wallets-wristlet-pouches","kss-wallets-wristlet-pouches");
		kssCategoryMap.put("ks-wallets-wristlets-cardholders","kss-wallets-wristlet-pouches=>kss-wallets-wristlets-cardholders");
		kssCategoryMap.put("ks-wallets-wristlets-large-wallets","kss-wallets-wristlet-pouches=>kss-wallets-wristlets-large-wallets");
		kssCategoryMap.put("ks-wallets-wristlets-view-all","kss-wallets-wristlet-pouches=>kss-wallets-wristlets-view-all");



		
		
		
		
		kssCategoryMap.put("fall-essentials","fall-essentials");
		kssCategoryMap.put("fall-essentials","fall-essentials");
		kssCategoryMap.put("fall-essentials","fall-essentials");
		kssCategoryMap.put("fall-essentials","fall-essentials");
		kssCategoryMap.put("fall-essentials","fall-essentials");
		kssCategoryMap.put("ks-back-to-school-shop","ks-back-to-school-shop");
		kssCategoryMap.put("ks-back-to-school-shop","ks-back-to-school-shop");
		kssCategoryMap.put("ks-back-to-school-shop","ks-back-to-school-shop");
		kssCategoryMap.put("ks-back-to-school-shop","ks-back-to-school-shop");
		kssCategoryMap.put("ks-back-to-school-shop","ks-back-to-school-shop");
		kssCategoryMap.put("kss-backtowork","kss-backtowork");
		kssCategoryMap.put("kss-backtowork","kss-backtowork");
		kssCategoryMap.put("kss-backtowork","kss-backtowork");
		kssCategoryMap.put("kss-backtowork","kss-backtowork");
		kssCategoryMap.put("kss-backtowork","kss-backtowork");
		kssCategoryMap.put("kss-backtowork","kss-backtowork");
		kssCategoryMap.put("kss-backtowork","kss-backtowork");
		kssCategoryMap.put("kss-black-friday-deals","kss-black-friday-deals");
		kssCategoryMap.put("kss-black-friday-deals","kss-black-friday-deals");
		kssCategoryMap.put("kss-black-friday-deals","kss-black-friday-deals");
		kssCategoryMap.put("kss-black-friday-deals","kss-black-friday-deals");
		kssCategoryMap.put("kss-black-friday-deals","kss-black-friday-deals");
		kssCategoryMap.put("kss-cyber-monday-deals","kss-cyber-monday-deals");
		kssCategoryMap.put("kss-cyber-monday-deals","kss-cyber-monday-deals");
		kssCategoryMap.put("kss-cyber-monday-deals","kss-cyber-monday-deals");
		kssCategoryMap.put("kss-cyber-monday-deals","kss-cyber-monday-deals");
		kssCategoryMap.put("kss-cyber-monday-deals","kss-cyber-monday-deals");
		kssCategoryMap.put("kss-essentials","kss-essentials");
		kssCategoryMap.put("kss-essentials","kss-essentials");
		kssCategoryMap.put("kss-essentials","kss-essentials");
		kssCategoryMap.put("kss-essentials","kss-essentials");
		kssCategoryMap.put("kss-essentials","kss-essentials");
		kssCategoryMap.put("kss-essentials","kss-essentials");
		kssCategoryMap.put("kss-essentials","kss-essentials");
		kssCategoryMap.put("kss-gift-cards","kss-gift-cards");
		kssCategoryMap.put("kss-gift-cards","kss-gift-cards");
		kssCategoryMap.put("kss-gift-cards","kss-gift-cards");
		kssCategoryMap.put("kss-gift-cards","kss-gift-cards");
		kssCategoryMap.put("kss-gift-cards","kss-gift-cards");
		kssCategoryMap.put("kss-gift-cards","kss-gift-cards");
		kssCategoryMap.put("kss-gift-cards","kss-gift-cards");
		kssCategoryMap.put("kss-gift-cards","kss-gift-cards");
		kssCategoryMap.put("ks-shoes-view-all","ks-shoes-view-all");
		kssCategoryMap.put("ks-shoes-view-all","ks-shoes-view-all");
		kssCategoryMap.put("ks-shops-summer-must-haves","ks-shops-summer-must-haves");
		kssCategoryMap.put("ks-shops-summer-must-haves","ks-shops-summer-must-haves");
		kssCategoryMap.put("ks-shops-summer-must-haves","ks-shops-summer-must-haves");
		kssCategoryMap.put("ks-shops-summer-must-haves","ks-shops-summer-must-haves");
		kssCategoryMap.put("ks-shops-summer-must-haves","ks-shops-summer-must-haves");
		kssCategoryMap.put("kss-minnie","kss-minnie");
		kssCategoryMap.put("kss-minnie","kss-minnie");
		kssCategoryMap.put("kss-minnie","kss-minnie");
		kssCategoryMap.put("kss-minnie","kss-minnie");
		kssCategoryMap.put("kss-minnie","kss-minnie");
		kssCategoryMap.put("kss-novelty","kss-novelty");
		kssCategoryMap.put("kss-novelty","kss-novelty");
		kssCategoryMap.put("kss-novelty","kss-novelty");
		kssCategoryMap.put("kss-novelty","kss-novelty");
		kssCategoryMap.put("kss-novelty","kss-novelty");
		kssCategoryMap.put("kss-novelty","kss-novelty");
		kssCategoryMap.put("kss-novelty","kss-novelty");
		kssCategoryMap.put("kss-rainbow-shop","kss-rainbow-shop");
		kssCategoryMap.put("kss-rainbow-shop","kss-rainbow-shop");
		kssCategoryMap.put("kss-rainbow-shop","kss-rainbow-shop");
		kssCategoryMap.put("kss-rainbow-shop","kss-rainbow-shop");
		kssCategoryMap.put("kss-sweepstakes-terms","kss-sweepstakes-terms");
		kssCategoryMap.put("kss-sweepstakes-terms","kss-sweepstakes-terms");
		kssCategoryMap.put("kss-sweepstakes-terms","kss-sweepstakes-terms");
		kssCategoryMap.put("kss-sweepstakes-terms","kss-sweepstakes-terms");
		kssCategoryMap.put("kss-sweepstakes-terms","kss-sweepstakes-terms");
		kssCategoryMap.put("kss-travel-shop","kss-travel-shop");
		kssCategoryMap.put("kss-travel-shop","kss-travel-shop");
		kssCategoryMap.put("kss-travel-shop","kss-travel-shop");
		kssCategoryMap.put("kss-travel-shop","kss-travel-shop");
		kssCategoryMap.put("kss-travel-shop","kss-travel-shop");
		kssCategoryMap.put("kss-travel-shop","kss-travel-shop");
		kssCategoryMap.put("kss-travel-shop","kss-travel-shop");
		kssCategoryMap.put("ks-wallets-wristlets-small-wallets","ks-wallets-wristlets-small-wallets");
		kssCategoryMap.put("ks-wallets-wristlets-small-wallets","ks-wallets-wristlets-small-wallets");
ksCategoryMap.put("ks-best-sellers","ks-best-sellers");
ksCategoryMap.put("ks-best-sellers","ks-best-sellers");
ksCategoryMap.put("ks-best-sellers","ks-best-sellers");
ksCategoryMap.put("ks-best-sellers","ks-best-sellers");
ksCategoryMap.put("ks-bridal","ks-bridal");
ksCategoryMap.put("ks-bridal","ks-bridal");
ksCategoryMap.put("ks-classic-shop","ks-classic-shop");
ksCategoryMap.put("ks-classic-shop","ks-classic-shop");
ksCategoryMap.put("ks-classic-shop","ks-classic-shop");
ksCategoryMap.put("ks-classic-shop","ks-classic-shop");
ksCategoryMap.put("ks-handbags-i-love-ny","ks-handbags-i-love-ny");
ksCategoryMap.put("ks-handbags-i-love-ny","ks-handbags-i-love-ny");
ksCategoryMap.put("ks-handbags-new-novelty","ks-handbags-new-novelty");
ksCategoryMap.put("ks-handbags-new-novelty","ks-handbags-new-novelty");
ksCategoryMap.put("ks-home-office-accessories-view-all","ks-home-office-accessories-view-all");
ksCategoryMap.put("ks-home-office-accessories-view-all","ks-home-office-accessories-view-all");
ksCategoryMap.put("ks-i-love-ny","ks-i-love-ny");
ksCategoryMap.put("ks-i-love-ny","ks-i-love-ny");
ksCategoryMap.put("ks-i-love-ny","ks-i-love-ny");
ksCategoryMap.put("ks-i-love-ny","ks-i-love-ny");
ksCategoryMap.put("ks-new-essentials","ks-new-essentials");
ksCategoryMap.put("ks-new-essentials","ks-new-essentials");
ksCategoryMap.put("ks-new-essentials","ks-new-essentials");
ksCategoryMap.put("ks-new-logo","ks-new-logo");
ksCategoryMap.put("ks-new-logo","ks-new-logo");
ksCategoryMap.put("ks-new-logo","ks-new-logo");
ksCategoryMap.put("ks-new-novelty","ks-new-novelty");
ksCategoryMap.put("ks-new-novelty","ks-new-novelty");
ksCategoryMap.put("ks-new-novelty","ks-new-novelty");
ksCategoryMap.put("ks-new-novelty","ks-new-novelty");
ksCategoryMap.put("ks-new-novelty","ks-new-novelty");
ksCategoryMap.put("ks-personalization","ks-personalization");
ksCategoryMap.put("ks-personalization","ks-personalization");
ksCategoryMap.put("ks-personalization","ks-personalization");
ksCategoryMap.put("ks-spencer-shop","ks-spencer-shop");
ksCategoryMap.put("ks-spencer-shop","ks-spencer-shop");

**/


		// System.out.println("--Extrating category Details for Surprise--");
		ExtractCategoryDetails("surprise");
		// System.out.println("--Extration complerte for category Details for
		// Surprise--");
		// System.out.println("--Extrating category Details for Katespade--");
		ExtractCategoryDetails("katepsade");
		// System.out.println("--Extration complerte for category Details for
		// Katespade--");
		// System.out.println("--Extrating A360 Details --");
		ExtractA360SkuDetails();
		ExtractA360MasterDetails();

		// System.out.println(A360DB.size());
		// System.out.println("--Extrating product Details --");
		ExtractCategoryProductRelation("surprise");
		ExtractCategoryProductRelation("katepsade");
		formchildToMaster();
		ExtractProductDEtails();
		
		/*
		 * FileWriter myWriter = new
		 * FileWriter(ext.getPropValue("plumslice_category_product_relation_csv"),
		 * false); StringBuilder tempSb = new StringBuilder();
		 * for(CategoryProductRelation vCategoryProductRelation :
		 * vCategoryProductRelationList) {
		 * tempSb.append("\"").append(vCategoryProductRelation.getProductId()).append(
		 * "\",\"").append(vCategoryProductRelation.getCategoryIdKate()).append("\",\"")
		 * .append(vCategoryProductRelation.getCategoryIdSurprise()).append("\",\"").
		 * append(vCategoryProductRelation.getPrimaryKate()).append("\",\"").append(
		 * vCategoryProductRelation.getPrimarySurprise()).append("\"\n"); }
		 * myWriter.write(tempSb.toString()); myWriter.close();
		 */
		
		// System.out.println("--Extration completed product Details --");

	}



	private static void ExtractA360MasterDetails() throws IOException {
		File fileDB = new File(ext.getPropValue("A360_DB"));
		// System.out.println(fileDB);
		Scanner myReader = new Scanner(fileDB);
		String styleId = "";
		String color = "";
		String upcNo = "";

		while (myReader.hasNextLine()) {
			styleId = "";
			color = "";
			upcNo = "";
			String data = myReader.nextLine();
			String categoryList[] = data.split(",");
			if (categoryList.length > 1 && categoryList[0].replaceAll("\"", "").trim() != "") {
				styleId = categoryList[0];
				upcNo = categoryList[1];
				if (categoryList.length > 3) {
					color = categoryList[4];
				}
				if (!A360DB.containsKey(styleId)) {
					A360DB vA360DB = new A360DB();
					vA360DB.setStyleId(styleId);
					vA360DB.setColorCode(color);
					vA360DB.setSkuId(styleId);
					vA360DB.setVariationId(styleId.trim() + "-" + color.trim());
					vA360DB.setMaster(true);
					A360DB.put(styleId, vA360DB);
				}
			}

		}
		myReader.close();

	}

	public HashMap<String, A360DB> getA360DB() {
		return A360DB;
	}

	private static void ExtractA360SkuDetails() throws IOException {
		File fileDB = new File(ext.getPropValue("A360_DB"));
		System.out.println(fileDB);
		Scanner myReader = new Scanner(fileDB);
		String styleId = "";
		String color = "";
		String skuId = "";
		String upcNo = "";

		while (myReader.hasNextLine()) {
			styleId = "";
			color = "";
			upcNo = "";
			skuId = "";
			String data = myReader.nextLine();
			String categoryList[] = data.split(",");
			if (categoryList.length > 1 && categoryList[0].replaceAll("\"", "").trim() != "") {
				if (categoryList.length > 3) {
					color = categoryList[4];
				}
				styleId = categoryList[0];
				upcNo = categoryList[1];
				skuId = categoryList[2];

				if (!A360DB.containsKey(upcNo)) {
					A360DB vA360DB = new A360DB();
					vA360DB.setStyleId(styleId);
					vA360DB.setSkuId(skuId);
					vA360DB.setColorCode(color);
					vA360DB.setUpcNo(upcNo);
					vA360DB.setVariationId(styleId.trim() + "-" + color.trim());
					vA360DB.setMaster(false);
					A360DB.put(upcNo, vA360DB);
				}
			}

		}
		myReader.close();

	}

	private static void ExtractProductDEtails() throws IOException, JAXBException {
		File file = new File(ext.getPropValue("master-catalog-path"));
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
		FileWriter myWriter = new FileWriter(ext.getPropValue("product_mapping_fileName"), false);
		FileWriter myBrandWriter = new FileWriter(ext.getPropValue("product_mapping_Brand_fileName"), false);
		
		FileWriter defaultProductWriter = new FileWriter(ext.getPropValue("master_product_default_mapping_fileName"),
				false);
		StringBuilder sb_default_ns = new StringBuilder();
		FileWriter no_style_product_mapping_fileName = new FileWriter(
				ext.getPropValue("no_style_product_mapping_fileName"), false);
		StringBuilder sb = new StringBuilder();
		StringBuilder sbBrand = new StringBuilder();
		
		StringBuilder sb_ns = new StringBuilder();
		sb.append(
				"\"ProductId\",\"Item Id\",\"is Master\",\"Variation Id\",\"Master Id\",\"Brand\",\"Class Description\",\"Class Id\",\"Department Description\",\"Department Id\",\"Group Description\",\"Group Id\",\"Custom Attribute 1\",\"Custom Attribute 2\"\n");
		sb_ns.append("\"ProductId\"\n");
		for (ComplexTypeProduct product : que.getProduct()) {
			String style = "";
			String color = "";
			String size = "";
			String dimension = "";
			String classDescription = "";
			String classId = "";
			String departmentDescription = "";
			String departmentId = "";
			String groupDescription = "";
			String groupId = "";
			String customAttribute1 = "";
			String customAttribute2 = "";
			SharedTypeSiteSpecificCustomAttributes customAttribute = product.getCustomAttributes();
			/**
			 * if(customAttribute !=null) { for (SharedTypeSiteSpecificCustomAttribute
			 * customAttributeVal :customAttribute.getCustomAttribute()) { //
			 * System.out.println(customAttributeVal.getAttributeId() +
			 * ":::::"+customAttributeVal.getContent().get(0).toString());
			 * 
			 * dimension = extractAttributeFromCustomAttribute("dimension",product);
			 * if("*N".equalsIgnoreCase(dimension)){ dimension = "ND"; } dimension =
			 * resetToDefaultLength(dimension,2);
			 * 
			 * style = extractAttributeFromCustomAttribute("style",product); style =
			 * resetToDefaultLength(style,9);
			 * 
			 * 
			 * //color = extractAttributeFromCustomAttribute("color",product); //color =
			 * resetToDefaultLength(color,3); color = ""; color =
			 * extractAttributeFromCustomAttribute("color",product); color =
			 * resetToDefaultLength(color,3);
			 * 
			 * 
			 * size = extractAttributeFromCustomAttribute("color",product); size =
			 * resetToDefaultLength(size,5);
			 * 
			 * classDescription =
			 * extractAttributeFromCustomAttribute("class-description",product); classId =
			 * extractAttributeFromCustomAttribute("class-id",product);
			 * departmentDescription =
			 * extractAttributeFromCustomAttribute("department-description",product);
			 * departmentId = extractAttributeFromCustomAttribute("department-id",product);
			 * groupDescription =
			 * extractAttributeFromCustomAttribute("group-description",product); groupId =
			 * extractAttributeFromCustomAttribute("group-id",product); customAttribute1 =
			 * extractAttributeFromCustomAttribute("custom-attribute1",product);
			 * customAttribute2 =
			 * extractAttributeFromCustomAttribute("custom-attribute2",product); } }
			 **/
			style = extractAttributeFromCustomAttribute("style",product);
			A360DB tempA360DB = null;
			if (A360DB.containsKey(product.getProductId())) {
				tempA360DB = A360DB.get(product.getProductId());
			}
			String brandType = "";
			if (brandType.isBlank() && childToMaster.containsKey(product.getProductId()) && categoryProductRelation.containsKey(childToMaster.get(product.getProductId()))){
				brandType = categoryProductRelation.get(childToMaster.get(product.getProductId()));
			}
			if (brandType.isBlank() && tempA360DB != null && categoryProductRelation.containsKey(tempA360DB.getStyleId())){
				brandType = categoryProductRelation.get(tempA360DB.getStyleId());
			}

			if (brandType.isBlank() && categoryProductRelation.containsKey(style.trim())){
				brandType = categoryProductRelation.get(style.trim());
			}
			if (brandType.isBlank() && categoryProductRelation.containsKey(product.getProductId())) {
				brandType = categoryProductRelation.get(product.getProductId());
			}


			


			
			
		
			ComplexTypeProductClassificationCategory catoryTyep = product.getClassificationCategory();
			if (catoryTyep != null && brandType.isBlank()) {
				if (catoryTyep.getCatalogId() != null) {
					if ("kateandsaturday-site-catalog".equals(catoryTyep.getCatalogId())) {
						brandType = "KATESPADE";
					} else if ("katesale-site-catalog".equals(catoryTyep.getCatalogId())) {
						brandType = "SURPRISE";
					}

				}
			}

	

			// System.out.println("end");
			if (tempA360DB != null) {
				if (product.getVariations() == null && tempA360DB.getSkuId() != null) {
					if (tempA360DB != null) {
						sb.append("\"").append(product.getProductId()).append("\",\"").append(tempA360DB.getSkuId())
								.append("\",\"").append("false").append("\",\"").append(tempA360DB.getVariationId())
								.append("\",\"").append(tempA360DB.getStyleId()).append("\",\"").append(brandType)
								.append("\",\"").append(classDescription).append("\",\"").append(classId)
								.append("\",\"").append(departmentDescription).append("\",\"").append(departmentId)
								.append("\",\"").append(groupDescription).append("\",\"").append(groupId)
								.append("\",\"").append(customAttribute1).append("\",\"").append(customAttribute2)
								.append("\"\n");
						
						sbBrand.append("\"\",\"").append(tempA360DB.getVariationId()).append("\",\"\",\"").append(brandType).append("\"\n");
					}

				} else if (product.getVariations() != null) {
					ComplexTypeProductVariations variations = product.getVariations();
					if (variations != null && variations.getVariants() != null) {
						for (ComplexTypeProductVariationsVariants variant : variations.getVariants()) {
							int sequence = 0;
							for (ComplexTypeProductVariationsVariant vComplexTypeProductVariationsVariant : variant
									.getVariant()) {
								A360DB tempvariantA360DB = A360DB
										.get(vComplexTypeProductVariationsVariant.getProductId());
								String pVariantChildId = "";
								if (pVariantChildId.indexOf("-") > -1) {
									pVariantChildId = pVariantChildId.split("-")[0];
								}
								if (tempvariantA360DB != null) {
									pVariantChildId = tempvariantA360DB.getSkuId();
									sb_default_ns.append("\"").append(product.getProductId()).append("\",\"")
											.append(pVariantChildId).append("\",\"")
											.append((vComplexTypeProductVariationsVariant.isDefault() != null
													&& vComplexTypeProductVariationsVariant.isDefault()) ? "Yes" : "No")
											.append("\",\"").append(sequence++).append("\"\n");
									;
								}

							}

						}
					}

					// sb_default_ns
					sb.append("\"").append(product.getProductId()).append("\",\"").append(product.getProductId())
							.append("\",\"").append("true").append("\",\"\",\"\",\"").append(brandType).append("\",\"")
							.append(classDescription).append("\",\"").append(classId).append("\",\"")
							.append(departmentDescription).append("\",\"").append(departmentId).append("\",\"")
							.append(groupDescription).append("\",\"").append(groupId).append("\",\"")
							.append(customAttribute1).append("\",\"").append(customAttribute2).append("\"\n");
					
					
					sbBrand.append("\"").append(product.getProductId()).append("\",\"\",\"").append(brandType).append("\",\"\"\n");
					
					//sbBrand.append("\"").append(product.getProductId()).append("\",\"").append(brandType).append("\"\n");
				}

			}

		}
		myBrandWriter.write(sbBrand.toString());
		myBrandWriter.close();
		myWriter.write(sb.toString());
		myWriter.close();
		defaultProductWriter.write(sb_default_ns.toString());
		defaultProductWriter.close();
		no_style_product_mapping_fileName.write(sb_ns.toString());
		no_style_product_mapping_fileName.close();
	}
	
	private static void formchildToMaster() throws IOException, JAXBException {
		File file = new File(ext.getPropValue("master-catalog-path"));
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
							childToMaster.put(vComplexTypeProductVariationsVariant.getProductId().trim(),product.getProductId().trim()	);					
						}
					}
				}
			}
		}
	}

	private static String extractAttributeFromCustomAttribute(String pCustomAttributeToBeFetched,
			ComplexTypeProduct product) {
		String result = "";
		SharedTypeSiteSpecificCustomAttributes customAttribute = product.getCustomAttributes();
		if (customAttribute == null) {
			return "";
		}
		List<SharedTypeSiteSpecificCustomAttribute> customAttributes = customAttribute.getCustomAttribute();
		if (customAttributes == null) {
			return "";
		}
		for (SharedTypeSiteSpecificCustomAttribute pSharedTypeSiteSpecificCustomAttribute : customAttributes) {
			if (pCustomAttributeToBeFetched.equalsIgnoreCase(pSharedTypeSiteSpecificCustomAttribute.getAttributeId())) {
				result = pSharedTypeSiteSpecificCustomAttribute.getContent().get(0).toString();
			}

		}
		if (result.indexOf("-") > -1) {
			result = result.split("-")[0];
		}
		// System.out.println(pCustomAttributeToBeFetched+" > "+result);
		return result;
	}

	private static Object extractVariationId(String skuId) {
		String styleId = skuId.substring(0, 9);
		String colour = skuId.substring(9, 12);
		return styleId.trim() + "_" + colour.trim();
	}

	private static String resetToDefaultLength(String inutAttr, int MinLength) {
		int noOfSpace = 0;
		if (inutAttr.length() < MinLength) {
			noOfSpace = MinLength - inutAttr.length();
		}
		String converTedAttr = inutAttr;
		for (int i = 0; i < noOfSpace; i++) {
			converTedAttr = converTedAttr + " ";
		}
		return converTedAttr;
	}

	private static void ExtractCategoryProductRelation(String subBrands) throws IOException, JAXBException {
		if ("surprise".equalsIgnoreCase(subBrands)) {
			File file = new File(ext.getPropValue("surprise-sales-catalog-path"));
			JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
			CategoryProductRelation vCategoryProductRelation;
			String vProductId = "";
			A360DB tempA360DB = null;

			for (ComplexTypeCategoryAssignment vComplexTypeCategoryAssignment : que.getCategoryAssignment()) {
				vCategoryProductRelation = new CategoryProductRelation();
				vProductId = vComplexTypeCategoryAssignment.getProductId();
				
				if (vProductId.indexOf("-") > -1) {
					vProductId = vProductId.split("-")[0];
				}
				if (A360DB.containsKey(vProductId)) {
					tempA360DB = A360DB.get(vProductId);
				}
				if (categoryProductRelation.containsKey(vProductId) && ("KATESPADE"
						.equalsIgnoreCase(categoryProductRelation.get(vProductId))
						|| "BOTH".equalsIgnoreCase(
								categoryProductRelation.get(vProductId)))) {
					categoryProductRelation.put(vProductId, "BOTH");
				} else {
					categoryProductRelation.put(vProductId, "SURPRISE");
				}
			//	if(tempA360DB != null && kssCategoryMap.containsKey(vComplexTypeCategoryAssignment.getCategoryId()) && !itemCatList.contains(vProductId+vComplexTypeCategoryAssignment.getCategoryId())) {
					if(tempA360DB != null) {
					itemCatList.add(vProductId+vComplexTypeCategoryAssignment.getCategoryId());
				vCategoryProductRelation.setBrand("KSUSSUR");
				vCategoryProductRelation.setCategoryId(kssCategoryMap.get(vComplexTypeCategoryAssignment.getCategoryId()));
				vCategoryProductRelation.setPrimary((vComplexTypeCategoryAssignment.isPrimaryFlag() != null && vComplexTypeCategoryAssignment.isPrimaryFlag())?"Yes":"No");	
				vCategoryProductRelation.setProductId(tempA360DB.getSkuId());	
				vCategoryProductRelation.setMasterId(tempA360DB.getStyleId());	
				vCategoryProductRelation.setVariationId(tempA360DB.getVariationId());
				vCategoryProductRelation.setMaster(tempA360DB.isMaster());
				vCategoryProductRelationList.add(vCategoryProductRelation);
				}
			}
		} else {
			File file = new File(ext.getPropValue("katespade-sales-catalog-path"));
			JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
			CategoryProductRelation vCategoryProductRelation;
			String vProductId = "";
			A360DB tempA360DB = null;
			for (ComplexTypeCategoryAssignment vComplexTypeCategoryAssignment : que.getCategoryAssignment()) {
				vCategoryProductRelation = new CategoryProductRelation();
				vProductId = vComplexTypeCategoryAssignment.getProductId();
				if (vProductId.indexOf("-") > -1) {
					vProductId = vProductId.split("-")[0];
				}
				if (A360DB.containsKey(vProductId)) {
					tempA360DB = A360DB.get(vProductId);
				}
				if (categoryProductRelation.containsKey(vProductId) && ("SURPRISE"
						.equalsIgnoreCase(categoryProductRelation.get(vProductId))
						|| "BOTH".equalsIgnoreCase(
								categoryProductRelation.get(vProductId)))) {
					categoryProductRelation.put(vProductId, "BOTH");
				} else {
					categoryProductRelation.put(vProductId, "KATESPADE");
				}
				//if(tempA360DB != null && ksCategoryMap.containsKey(vComplexTypeCategoryAssignment.getCategoryId())  && !itemCatList.contains(vProductId+vComplexTypeCategoryAssignment.getCategoryId())) {
					if(tempA360DB != null) {
					itemCatList.add(vProductId+vComplexTypeCategoryAssignment.getCategoryId());
					vCategoryProductRelation.setBrand("KSUSRT");
					vCategoryProductRelation.setCategoryId(ksCategoryMap.get(vComplexTypeCategoryAssignment.getCategoryId()));
					vCategoryProductRelation.setPrimary((vComplexTypeCategoryAssignment.isPrimaryFlag() != null && vComplexTypeCategoryAssignment.isPrimaryFlag())?"Yes":"No");	
					vCategoryProductRelation.setProductId(tempA360DB.getSkuId());	
					vCategoryProductRelation.setMasterId(tempA360DB.getStyleId());	
					vCategoryProductRelation.setMasterId(tempA360DB.getStyleId());	
					vCategoryProductRelation.setVariationId(tempA360DB.getVariationId());
					vCategoryProductRelation.setMaster(tempA360DB.isMaster());
					vCategoryProductRelationList.add(vCategoryProductRelation);
				}

			}

		}

	}

	private static void ExtractCategoryDetails(String subBrands) throws JAXBException, IOException {
		List<String> customAttriubutesttributes = new ArrayList<String>();

		if ("surprise".equalsIgnoreCase(subBrands)) {
			File file = new File(ext.getPropValue("surprise-sales-catalog-path"));
			JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
			FileWriter myWriter = new FileWriter(ext.getPropValue("surprise_category_fileName"), false);
			StringBuilder sb = new StringBuilder();
			HashMap<String,String> surpriseCatList = new HashMap<String,String> ();
			for (ComplexTypeCategory cataegory : que.getCategory()) {
				surpriseCatList.put(cataegory.getCategoryId(), cataegory.getParent());			
			}
			
			sb.append("\"CategoryId\",\"Name\",\"parent\",\"OnlineFlag\",\"Path\"\n");
			for (ComplexTypeCategory cataegory : que.getCategory()) {
				/*
				 * SharedTypeCustomAttributes customAttribute = cataegory.getCustomAttributes();
				 * if(customAttribute != null ) { for(SharedTypeCustomAttribute custom :
				 * customAttribute.getCustomAttribute()) {
				 * if(!customAttriubutesttributes.contains(custom.getAttributeId())) {
				 * customAttriubutesttributes.add(custom.getAttributeId());
				 * 
				 * } } }
				 */

				String disaplayName = "";
				for (SharedTypeLocalizedString lang : cataegory.getDisplayName()) {
					if (lang.getLang().equals("x-default")) {
						disaplayName = lang.getValue();
					}
				}
				sb.append("\"").append(cataegory.getCategoryId()).append("\",\"").append(disaplayName).append("\",\"")
						.append(cataegory.getParent()).append("\",\"").append(cataegory.isOnlineFlag()).append("\",\"").append(createPath(surpriseCatList,cataegory.getCategoryId())).append("\"\n");
			}
			myWriter.write(sb.toString());
			myWriter.close();
		} else {
			File file = new File(ext.getPropValue("katespade-sales-catalog-path"));
			JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
			FileWriter myWriter = new FileWriter(ext.getPropValue("katespade_category_fileName"), false);
			StringBuilder sb = new StringBuilder();
			
			
			HashMap<String,String> kateCatList = new HashMap<String,String> ();
			for (ComplexTypeCategory cataegory : que.getCategory()) {
				kateCatList.put(cataegory.getCategoryId(), cataegory.getParent());			
			}
			
			sb.append("\"CategoryId\",\"Name\",\"parent\",\"OnlineFlag\",\"Path\"\n");
			for (ComplexTypeCategory cataegory : que.getCategory()) {
				/*
				 * SharedTypeCustomAttributes customAttribute = cataegory.getCustomAttributes();
				 * if(customAttribute != null ) { for(SharedTypeCustomAttribute custom :
				 * customAttribute.getCustomAttribute()) {
				 * if(!customAttriubutesttributes.contains(custom.getAttributeId())) {
				 * customAttriubutesttributes.add(custom.getAttributeId());
				 * 
				 * } } }
				 */
				String disaplayName = "";
				for (SharedTypeLocalizedString lang : cataegory.getDisplayName()) {
					if (lang.getLang().equals("x-default")) {
						disaplayName = lang.getValue();
					}
				}
				sb.append("\"").append(cataegory.getCategoryId()).append("\",\"").append(disaplayName).append("\",\"")
						.append(cataegory.getParent()).append("\",\"").append(cataegory.isOnlineFlag()).append("\",\"").append(createPath(kateCatList,cataegory.getCategoryId())).append("\"\n");

			}
			myWriter.write(sb.toString());
			myWriter.close();
		}
	}

	private static Object createPath(HashMap<String, String> surpriseCatList, String categoryId) {
		StringBuilder path = new StringBuilder();
		List<String> paths = new ArrayList<String>();
		paths.add(categoryId);
		String newCategoryId = categoryId;
		while(surpriseCatList.containsKey(newCategoryId)) {
			if(surpriseCatList.get(newCategoryId) != null) {
				paths.add(surpriseCatList.get(newCategoryId));
			}

			newCategoryId = surpriseCatList.get(newCategoryId);
		}
		for(int i = (paths.size() -1) ; i >= 0; i--) {
			if(i > 0) {
				path.append(paths.get(i)).append(" > ");
			}else {
				path.append(paths.get(i));
			}
			
		}
		return path;
	}

}
