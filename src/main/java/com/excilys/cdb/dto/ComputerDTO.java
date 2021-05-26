package com.excilys.cdb.dto;

import java.time.LocalDate;

public class ComputerDTO {
	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	private int idCompany;
	
	private ComputerDTO(ComputerDTOBuilder builder) {
		id = builder.id;
		name = builder.name;
		introduced = builder.introduced;
		discontinued = builder.discontinued;
		idCompany = builder.idCompany;
	}
	
	
	public boolean isValid() {
		
		if (name == null || name.equals("")) {
			return false;
		}
		if(discontinued != null && introduced == null) {
			return false;
		}
		
		if(introduced != null && discontinued != null) {
			LocalDate dateIntr = LocalDate.parse(introduced);
			LocalDate dateDisc = LocalDate.parse(discontinued);
			
			if((dateIntr != null && dateDisc != null) && (dateIntr.isAfter(dateDisc))) {
				return false;
			}
			
		}
		
		
		return true;
		
	}
	
	
	
	public static class ComputerDTOBuilder {
		private int id = 0;
		private String name = null;
		private String introduced = null;
		private String discontinued = null;
		private int idCompany = 0;
		
		public ComputerDTOBuilder() {
			
		}
		
				
		public ComputerDTOBuilder withId(int id) {
			this.id = id;
			return this;
		}
		
		public ComputerDTOBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public ComputerDTOBuilder withIntroduced(String introduced) {
			this.introduced = introduced.equals("") ? null : introduced;
			return this;
		}
		
		public ComputerDTOBuilder withDiscontinued(String discontinued) {
			this.discontinued = discontinued.equals("") ? null : discontinued;
			return this;
		}
		
		public ComputerDTOBuilder withIdCompany(int company) {
			this.idCompany = company;
			return this;
		}
		
		public ComputerDTO build() {
			return new ComputerDTO(this);
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



	public String getIntroduced() {
		return introduced;
	}



	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}



	public String getDiscontinued() {
		return discontinued;
	}



	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}



	public int getIdCompany() {
		return idCompany;
	}



	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}
	
	
	
	
}
