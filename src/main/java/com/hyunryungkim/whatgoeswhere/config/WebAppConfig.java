package com.hyunryungkim.whatgoeswhere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Web Application Configuration class
 * Contains view resolver and resource handler methods.
 * 
 * @author Hyunryung Kim
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.perscholas.whatgoeswhere")
public class WebAppConfig implements WebMvcConfigurer{
	/**
	 * Attaches prefix and suffix for the controller mapping
	 * 
	 * @return an Internal Resource View Resolver
	 * @see org.springframework.web.servlet.view.InternalResourceViewResolver
	 */
	@Bean
	InternalResourceViewResolver viewResolver() {
		return new InternalResourceViewResolver("/WEB-INF/views/",".jsp");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
//		registry.addResourceHandler("**/resources/**").addResourceLocations("/resources/"); // An alternative
		registry.addResourceHandler("/scripts/**").addResourceLocations("/resources/scripts/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	
}
