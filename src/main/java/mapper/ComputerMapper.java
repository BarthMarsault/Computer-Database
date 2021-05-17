package main.java.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.java.model.Company;
import main.java.model.Computer;
import main.java.persistence.CompanyDAO;

/**
 * Classe de Mapping de la classe Computer
 * @author excilys
 *
 */
public class ComputerMapper {
	
	/**
	 * Retourne un objet Computer obtenu à partir d'un ResultSet
	 * @param rs ResultSet
	 * @return Une instance de la classe Computer
	 */
	public static Computer resultSetToComputer(ResultSet rs) {
		try {
			
			Date dIntr = rs.getDate(3);
			Date dDisc = rs.getDate(4);
			
			LocalDate ldIntr =  dIntr != null ? dIntr.toLocalDate() : null;
			LocalDate ldDisc = dDisc != null ? dDisc.toLocalDate() : null;
			
			Company company = new Company(rs.getInt(5), rs.getString(6));
			
			return new Computer(rs.getInt(1), rs.getString(2), ldIntr, ldDisc, company);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Computer> resultSetToListComputer(ResultSet rs) {
		ArrayList<Computer> computers = new ArrayList<>();
		
		try {
			while(rs.next()) {
				computers.add(resultSetToComputer(rs));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return computers;
	}

}
