package com.piti.java.school.onlinevideotraining.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.piti.java.school.onlinevideotraining.config.security.jwt.JwtLoginFilter;
import com.piti.java.school.onlinevideotraining.config.security.jwt.TokenVerifyFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {
	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {	
		http
		   .csrf()
           .disable()
           .addFilter(new JwtLoginFilter(authenticationManager2(authenticationConfiguration)))
           .addFilterAfter(new TokenVerifyFilter(), JwtLoginFilter.class)
           .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           .authorizeHttpRequests()
           .requestMatchers(
        		   "/",
        		   "index.html",
        		   "home",
        		   "/api/v*/registration/**",
        		   "api/v*/videos/**",
        		   "api/v*/courses/**",        		  
                   "/swagger-resources/*",                   
                   "/swagger-ui/**",
                   "/bus/v3/api-docs/**"
        		   
            )
           
           .permitAll()
           .anyRequest()
           .authenticated();
	
		return http.build();
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getAuthenticationProvider());
	}
	
	@Bean
	public AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
	
	@Bean
	AuthenticationManager authenticationManager2(
	        AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
}


