package com.ginkgocap.parasol.organ.web.jetty.web.filter;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ginkgocap.parasol.organ.web.jetty.web.utils.CommonUtil;
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
		logger.info(request.getRequestURI());
		//filterChain.doFilter(request, response);
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String s = req.getHeader("s");





        // 敏感词过滤
        BufferedReader reader = request.getReader();
        String line = null;
        StringBuffer jsonIn = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            jsonIn.append(line);
        }
        String result = jsonIn.toString();
        if (result.equals("")) {
            result = request.getParameter("json");
        }
        String requestJson = result;
        reader.close();

        logger.info(result);

        if (requestJson != null && !"".equals(requestJson)) {
            request.setAttribute("requestJson", requestJson);
            CommonUtil.setRequestIsFromWebFlag(false);
            filterChain.doFilter(request, response);
        } else {
            request.setAttribute("requestJson", "{}");
            CommonUtil.setRequestIsFromWebFlag(false);
            filterChain.doFilter(request, response);
        }
	}
	

}
