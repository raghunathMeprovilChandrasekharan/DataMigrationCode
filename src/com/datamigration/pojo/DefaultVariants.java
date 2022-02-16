package com.datamigration.pojo;

import java.util.HashSet;
import java.util.Set;

public class DefaultVariants {
	private Set<String> variant = new HashSet<String>();
	private String sequance;


	public Set<String> getVariant() {
		return variant;
	}


	public void setVariant(Set<String> variant) {
		this.variant = variant;
	}


	public String getSequance() {
		return sequance;
	}


	public void setSequance(String sequance) {
		this.sequance = sequance;
	}


}
