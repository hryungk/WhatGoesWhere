package org.perscholas.whatgoeswhere.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan("org.perscholas.whatgoeswhere")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private UserDetailsService userDetailsService;
	
	@Autowired
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
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
		// Permit all users to access the following pages. All other pages requires authentication.
		.authorizeRequests().antMatchers("/", "/find", "/about","/list", "/login", "/loginPost", "/register", "/registerNewUser","/contact").permitAll()
		
		// Specifies that we would like to use a custom form to login
		.and().formLogin().loginPage("/login").permitAll()
//		.loginProcessingUrl("/performLogin")
		.defaultSuccessUrl("/")
		
		// Specifies that any authenticated user can access all URLs
		.and().authorizeRequests().anyRequest().authenticated()
		.and()
		// Upon logout, invalidate the session and clear authentication
		.logout().invalidateHttpSession(true).clearAuthentication(true)
		// Specifies Logout URL
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		// Forward to /logoutsuccess upon logout and allow all requests
		.logoutSuccessUrl("/logoutSuccess").permitAll();
	}

	
}
