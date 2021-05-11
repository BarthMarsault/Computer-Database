package main.java.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import main.java.model.Computer;

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
			
			return new Computer(rs.getInt(1), rs.getString(2), ldIntr, ldDisc, rs.getInt(5));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
