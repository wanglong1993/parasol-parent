package com.ginkgocap.parasol.tags.web.jetty.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class ParasolLoggerFilter extends OncePerRequestFilter {
	private static Logger logger = LoggerFactory.getLogger(ParasolLoggerFilter.class);

	private String parameter;

	public ParasolLoggerFilter() {
		logger.info("ParasolLoggerFilter create....");
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		logger.info(request.getRequestURI());
		filterChain.doFilter(request, response);
	}
	

}
