package com.excilys.cdb.persistence;

public enum ComputerAttribute {
	NAME("name"),
	INTRODUCED("introduced"),
	DISCONTINUED("discontinued"),
	COMPANY("name");
	
	String attribute;
	
	private ComputerAttribute(String label) {
		this.attribute = label;
	}
	
	public String getAttribute() {
		return attribute;
	}
	
}
