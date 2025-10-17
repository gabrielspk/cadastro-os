package com.github.gabrielspk.cadastro_os.security;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.github.gabrielspk.cadastro_os.exceptions.InvalidJwtAuthenticationException;
import com.github.gabrielspk.cadastro_os.exceptions.JwtTokenExpiredException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends GenericFilterBean {
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);


	public JwtTokenFilter(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
	        throws IOException, ServletException {

	    try {
	        var token = tokenProvider.resolveToken((HttpServletRequest) request);
	        if (StringUtils.isNotBlank(token) && tokenProvider.validateToken(token)) {
	            Authentication authentication = tokenProvider.getAuthentication(token);
	            if (authentication != null) {
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
	        } else {
	            logger.debug("JWT ausente ou inválido na requisição: {}", ((HttpServletRequest) request).getRequestURI());
	        }
	        filter.doFilter(request, response);
	    } catch (JwtTokenExpiredException ex) {
	        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType("application/json");
	        response.getWriter().write("{\"error\":\"" + ex.getMessage() + "\"}");
	    } catch (InvalidJwtAuthenticationException ex) {
	        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType("application/json");
	        response.getWriter().write("{\"error\":\"" + ex.getMessage() + "\"}");
	    }
	}

}