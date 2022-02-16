package com.datamigration.pojo;

import java.util.Set;

public class VariationInfo {
	private String itemId;
	private String variationId;
	private String variationId_kate;
	private String variationId_surprise;
	public String getVariationId_kate() {
		return variationId_kate;
	}
	public void setVariationId_kate(String variationId_kate) {
		this.variationId_kate = variationId_kate;
	}
	public String getVariationId_surprise() {
		return variationId_surprise;
	}
	public void setVariationId_surprise(String variationId_surprise) {
		this.variationId_surprise = variationId_surprise;
	}
	public String getVariationId() {
		return variationId;
	}
	public void setVariationId(String variationId) {
		this.variationId = variationId;
	}
	private Set<String> products;
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Set<String> getProducts() {
		return products;
	}
	public void setProducts(Set<String> products) {
		this.products = products;
	}

}
