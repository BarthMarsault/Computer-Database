package main.java.model;

import java.sql.Date;

import main.java.persistence.ComputerDAO;

public class Computer {
	
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private int company_id;
	
	
	public Computer(int id, String name, Date introduced, Date discontinued, int company_id) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}
	
	


	public Computer(String name, Date introduced, Date discontinued, int company_id) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}
	
	public boolean alreadyExistInDB() {
		return this.equals(ComputerDAO.findComputerById(id));
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


	public Date getIntroduced() {
		return introduced;
	}


	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}


	public Date getDiscontinued() {
		return discontinued;
	}


	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}


	public int getCompany_id() {
		return company_id;
	}


	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}


	
	
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company_id=" + company_id + "]";
	}




	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof Computer)) return false;
		
		Computer c = (Computer) obj;
		
		return id == c.id && name.equals(c.name) && introduced.equals(c.introduced)
				&& discontinued.equals(c.discontinued) && company_id == c.company_id;
	}
	
	
	
	
	
	
	
	

}
