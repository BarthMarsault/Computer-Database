package main.java.model;

import java.sql.Date;
import java.time.LocalDate;

import main.java.persistence.CompanyDAO;
import main.java.persistence.ComputerDAO;

public class Computer {
	
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	public Computer() {
		super();
	}
	
	public Computer(int id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	


	public Computer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
		super();
		this.id = 0;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public boolean alreadyExistInDB() {
		return this.equals(ComputerDAO.getInstance().findComputerById(id));
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
	
	
	
	
	
	
	
	

}
