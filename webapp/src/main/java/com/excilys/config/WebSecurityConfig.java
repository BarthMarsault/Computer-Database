package com.excilys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.excilys.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
	JwtRequestFilter jwtRequestFilter;

	
	/*@Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }*/
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    /*@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }*/
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
    	return super.authenticationManager();
    }
    

    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//auth.authenticationProvider(authenticationProvider());
    	auth.userDetailsService(userDetailsServiceImpl);
    }
    
    
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable();
		
	
		
		http.authorizeRequests()
		.mvcMatchers("/login","/authenticate").permitAll()
        .mvcMatchers("/addComputer", "/editComputer", "/api/companies").hasRole("admin")
        .mvcMatchers("/", "/dashboard", "/api/computers").authenticated()
        .and().formLogin().defaultSuccessUrl("/dashboard", false)
        .and().logout().logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
}
