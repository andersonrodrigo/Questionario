package com.anderson.rodrigo.questionario.Questionario.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;

@Component
public class JWTAuthenticationFilter extends GenericFilterBean {
	@Autowired
	private TokenHelper tokenHelper;
	
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse  response, FilterChain filterChain) throws IOException, ServletException {
		Authentication authentication = null;
		
		Claims claims = tokenHelper.getClaims(((HttpServletRequest)request));
		
		if (claims != null) {
			Map<String, Object> user = (Map<String, Object>)claims.get("user");
			if (user != null) {
				authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.EMPTY_LIST);
			}
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		filterChain.doFilter(request, response);
	}

}
