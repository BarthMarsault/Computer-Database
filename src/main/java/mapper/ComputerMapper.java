package main.java.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.model.Computer;

/**
 * Classe de Mapping de la classe Computer
 * @author excilys
 *
 */
public class ComputerMapper {
	
	/**
	 * Retourne un objet Computer obtenu à partir du contenu d'un ResultSet
	 * @param rs ResultSet
	 * @return Une instance de la classe Computer
	 */
	public static Computer resultSetToComputer(ResultSet rs) {
		try {
			return new Computer(rs.getInt(1), rs.getString(2), rs.getDate(3) , rs.getDate(4), rs.getInt(5));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
