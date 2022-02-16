package com.datamigration.pojo;

import java.util.List;

public class A360DB {
	private String styleId;
	private String skuId;
	private String colorCode;
	private String upcNo;
	private List<String> upcNoList;
	public List<String> getUpcNoList() {
		return upcNoList;
	}
	public void setUpcNoList(List<String> upcNoList) {
		this.upcNoList = upcNoList;
	}
	private String variationId;
	public String getVariationId() {
		return variationId;
	}
	public void setVariationId(String variationId) {
		this.variationId = variationId;
	}
	private boolean master;
	public boolean isMaster() {
		return master;
	}
	public void setMaster(boolean master) {
		this.master = master;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getUpcNo() {
		return upcNo;
	}
	public void setUpcNo(String upcNo) {
		this.upcNo = upcNo;
	}

}
