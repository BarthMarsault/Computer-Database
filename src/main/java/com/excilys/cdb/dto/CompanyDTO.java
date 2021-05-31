package com.excilys.cdb.dto;

public class CompanyDTO {
	
	private int id;
	private String name;
	
	private CompanyDTO(CompanyDTOBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}
	
	
	public static class CompanyDTOBuilder {
		private int id = 0;
		private String name = null;
		
		public CompanyDTOBuilder() {
			
		}
		
		public CompanyDTOBuilder withId(int id) {
			this.id = id;
			return this;
		}
		
		public CompanyDTOBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public CompanyDTO build() {
			return new CompanyDTO(this);
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
	
	
	

}
