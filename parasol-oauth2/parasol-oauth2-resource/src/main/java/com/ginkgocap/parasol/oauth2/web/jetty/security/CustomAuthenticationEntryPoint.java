package com.ginkgocap.parasol.oauth2.web.jetty.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final Logger log = Logger.getLogger(CustomAuthenticationEntryPoint.class);

	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		System.out.println("Pre-authenticated entry point called. Rejecting access");
//		ResponseError responseError = new ResponseError();
//		Map<String, Serializable> errMap = new HashMap<String, Serializable>();
//		errMap.put("error", responseError);
//		responseError.setCode(HttpStatus.UNAUTHORIZED.value());
//
//		if (ObjectUtils.equals(request.getParameter("debug"), "all")) {
//			StringWriter sw = new StringWriter();
//			PrintWriter pw = new PrintWriter(sw);
//			pw.flush();
//			authException.printStackTrace(pw);
//			errMap.put("__debug__", sw.toString());
//		}
//		
//		ResponseEntity<Map<String, Serializable>> responseEntity = new ResponseEntity<Map<String,Serializable>>(errMap, HttpStatus.UNAUTHORIZED);
//		
//		
//	
//		
//		if (mappingJackson2HttpMessageConverter != null) {
//			mappingJackson2HttpMessageConverter.write(responseEntity, contentType, outputMessage);
//		}
//	
//		
//		throw new ServletException(authException);
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access is denied");

	}

}
