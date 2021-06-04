package com.excilys.cdb.persistence;

public enum ComputerAttribute {
	NAME("C.name"),
	INTRODUCED("C.introduced"),
	DISCONTINUED("C.discontinued"),
	COMPANY("Y.name");
	
	String attribute;
	
	private ComputerAttribute(String label) {
		this.attribute = label;
	}
	
	public String getAttribute() {
		return attribute;
	}
	
}
