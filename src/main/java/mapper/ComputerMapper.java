package main.java.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.model.Computer;

public class ComputerMapper {
	
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
