package com.excilys.cdb.model;

public class Company {
		
	private int id;
	private String name;
	
	
	public Company(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Company(CompanyBuilder builder) {
		id = builder.id;
		name = builder.name;
	}
	
	public static class CompanyBuilder{
		private int id;
		private String name;
		
		public CompanyBuilder() {
			
		}
		
		public CompanyBuilder withId(int id) {
			this.id = id;
			return this;
		}
		
		public CompanyBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public Company build() {
			return new Company(this);
		}
		
	}
		
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



	
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
