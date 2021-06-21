package com.excilys.validator;

import java.time.LocalDate;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.dto.ComputerDTO;

@Component
@Scope
public class ComputerValidator {
	
	private final int NAME_MIN_SIZE = 2;
	
	
	public boolean isValid(ComputerDTO c) {
		
		return validateId(c.getId())
				&& validateName(c.getName())
				&& validateDate(c.getIntroduced())
				&& validateDate(c.getDiscontinued())
				&& validateIntrAfterDisc(c.getIntroduced(), c.getDiscontinued())
				&& validateId(c.getIdCompany());
	}
	
	
	
	private boolean validateId(int id) {
		return id >= 0;
	}
		
	private boolean validateName(String name) {
		return name != null && !name.isBlank() && !name.equals("null") && name.length() >= NAME_MIN_SIZE;
	}
	
	private boolean validateDate(String date) {
		if (date == null || date.equals("")) {
			return true;
		}
		try {
			LocalDate.parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean validateIntrAfterDisc(String intr, String disc) {
		if(disc == null || disc.equals("") ) {
			return true;
		}
		
		if(intr == null || (intr.equals("")) && (disc != null || !disc.equals(""))) {
			return false;
		}
		
		try {
			return LocalDate.parse(intr).isBefore(LocalDate.parse(disc));

		} catch (Exception e) {
			return false;
		}
		
	}
	
	
	
	

}
