package org.perscholas.whatgoeswhere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("org.perscholas.whatgoeswhere")
public class WebAppConfig implements WebMvcConfigurer{
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
