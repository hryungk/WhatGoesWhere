package com.hyunryungkim.whatgoeswhere.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Web Security Configuration class
 * Needed for Spring Security
 * 
 * @author Hyunryung Kim
 *
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.hyunryungkim.whatgoeswhere")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * A class that implements UserDetailService interface
	 */
	private UserDetailsService userDetailsService;
	
	/**
	 * Autowires the UserDetailsService implementation class
	 * 
	 * @param userDetailsService a UserDetailService instance
	 * @see com.hyunryungkim.whatgoeswhere.services.impl.UserDetailsServiceImpl
	 */
	@Autowired
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Returns a BCrypt password encoder
	 * 
	 * @return a BCryptPasswordEncoder object used to encode user password
	 */
	@Bean
	public PasswordEncoder pswdEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Returns a DaoAutehnticationProvider object 
	 * UserDetailsService and PasswordEncoder are injected here.
	 * 
	 * @return a DaoAutehnticationProvider object 
	 */
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(pswdEncoder());
		return daoAuthenticationProvider;
	}
	
	@Override
	public void configure(WebSecurity web){
		web
		.ignoring()
		.antMatchers("/js/**", "/images/**", "/css/**", "/resources/**", "/scripts/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// CSRF is disabled for simplification and demonstration.
		// Do not use this configuration for production.
		http.csrf().disable()
		
		.authorizeRequests()
		// Permit all users to access the following pages. All other pages requires authentication.
		.antMatchers("/", "/find", "/about","/list", "/login", "/loginPost", "/register", "/registerNewUser","/contact").permitAll()
		// Restrict Admin page to "ADMIN" roles
		.antMatchers("/admin").hasRole("ADMIN").anyRequest().authenticated()
		// Specifies that we would like to use a custom form to login
		.and().formLogin().loginPage("/login").permitAll()
//		.loginProcessingUrl("/performLogin")
		.defaultSuccessUrl("/")		
		.and()
		// Upon logout, invalidate the session and clear authentication
		.logout().invalidateHttpSession(true).clearAuthentication(true)
		// Specifies Logout URL
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		// Forward to /logoutsuccess upon logout and allow all requests
		.logoutSuccessUrl("/logoutSuccess").permitAll()
		// Redirect to access denied page if access not authorize for user
		.and().exceptionHandling().accessDeniedPage("/accessDenied");
	}

	
}
