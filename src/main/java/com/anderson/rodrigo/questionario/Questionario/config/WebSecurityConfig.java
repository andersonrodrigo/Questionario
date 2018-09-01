package com.anderson.rodrigo.questionario.Questionario.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.anderson.rodrigo.questionario.Questionario.security.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JWTAuthenticationFilter jWTAuthenticationFilter;
	
	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/auth/token").permitAll()
				.antMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll()
				.antMatchers(HttpMethod.POST, "/api/auth/logout").permitAll()
				.antMatchers(HttpMethod.POST, "/api/auth/salvarUsuario").permitAll()
				.antMatchers("/api/**").authenticated().and()
			.addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class)
			.addFilterBefore(jWTAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}