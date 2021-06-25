package com.excilys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.core.User;
import com.excilys.persistence.UserDAO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
    private UserDAO userDAO;
	
	public UserDetailsServiceImpl() {
		
	}
     
    public UserDetailsServiceImpl(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}



	@Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
         
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new MyUserDetails(user);
    }
}
