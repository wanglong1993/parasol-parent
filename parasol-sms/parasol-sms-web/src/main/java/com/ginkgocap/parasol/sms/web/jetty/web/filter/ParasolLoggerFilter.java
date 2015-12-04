package com.ginkgocap.parasol.message.web.jetty.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

public class ParasolLoggerFilter extends OncePerRequestFilter {
	private static Logger logger = Logger.getLogger(ParasolLoggerFilter.class);

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
		logger.info("filter url:"+request.getRequestURI());
		filterChain.doFilter(request, response);
	}
	

}
