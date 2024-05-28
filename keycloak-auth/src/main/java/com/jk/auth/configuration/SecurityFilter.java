/**
 * 
 */
package com.jk.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Junaid.Khan
 *
 */

@Configuration
@EnableWebSecurity
public class SecurityFilter {
	
	private JwtAuthConverter authConverter;

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain filter(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests()
		.requestMatchers(HttpMethod.GET,"/auth/getAdminInfo/**").hasRole("admin")
		.requestMatchers(HttpMethod.GET,"/auth/getUserInfo/**").hasRole("user")
		.anyRequest()
		.authenticated();
		
		http
		 .oauth2ResourceServer()
		 .jwt()
		 .jwtAuthenticationConverter(authConverter);
		
		
		http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		return http.build();
		
		 
	}
}
