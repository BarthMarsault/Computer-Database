package com.excilys.cdb.model;

import java.sql.Date;
import java.time.LocalDate;

import com.excilys.cdb.persistence.ComputerDAO;

public class Computer {
	
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	
	private Computer(ComputerBuilder builder) {
		id = builder.id;
		name = builder.name;
		introduced = builder.introduced;
		discontinued = builder.discontinued;
		company = builder.company;
	}
	
	
	public static class ComputerBuilder {
		private int id = 0;
		private String name = null;
		private LocalDate introduced = null;
		private LocalDate discontinued = null;
		private Company company = null;
		
		public ComputerBuilder() {
			
		}
		
				
		public ComputerBuilder withId(int id) {
			this.id = id;
			return this;
		}
		
		public ComputerBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public ComputerBuilder withIntroduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public ComputerBuilder withDiscontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public ComputerBuilder withCompany(Company company) {
			this.company = company;
			return this;
		}
		
		public Computer build() {
			return new Computer(this);
		}
	}
		
		
		
	

	
	
	
	public boolean alreadyExistInDB() {
		return this.equals(ComputerDAO.getInstance().findById(id));
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


	public LocalDate getIntroduced() {
		return introduced;
	}


	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}


	public LocalDate getDiscontinued() {
		return discontinued;
	}


	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	

	@Override
	public String toString() {
		String str = "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued;
		if(company != null) {
			str +=  " Company = " + company.getName();
		}

		str += "]";
		return str;
	}




	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof Computer)) return false;
		
		Computer c = (Computer) obj;
		
		return id == c.id && name.equals(c.name) && introduced.equals(c.introduced)
				&& discontinued.equals(c.discontinued) && company == c.company;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + id;
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	
	
	
	
	
	
	
	
	

}
