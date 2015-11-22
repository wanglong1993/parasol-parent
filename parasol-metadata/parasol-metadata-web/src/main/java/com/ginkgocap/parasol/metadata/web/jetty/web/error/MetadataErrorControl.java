package com.ginkgocap.parasol.metadata.web.jetty.web.error;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.ginkgocap.parasol.metadata.web.jetty.web.ResponseError;

@ControllerAdvice
public class MetadataErrorControl {
	public static Logger logger = Logger.getLogger(MetadataErrorControl.class);

	@ExceptionHandler(value = { Exception.class, RuntimeException.class })
	@ResponseBody
	public ResponseEntity<ResponseError> defaultErrorHandler(HttpServletRequest request, Exception ex) {
		ResponseError responseError = new ResponseError();
		ResponseEntity<ResponseError> responseEntity = new ResponseEntity<ResponseError>(responseError, getStatus(request, ex));
		return responseEntity;
	}

	private HttpStatus getStatus(HttpServletRequest request, Exception ex) {
		Integer statusCode = getStatusFromException(ex);
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		try {
			return HttpStatus.valueOf(statusCode);
		} catch (Exception e) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	protected Integer getStatusFromException(Exception ex) {
		if (ex instanceof NoSuchRequestHandlingMethodException) {
			return HttpServletResponse.SC_NOT_FOUND;
		} else if (ex instanceof HttpRequestMethodNotSupportedException) {
			return HttpServletResponse.SC_METHOD_NOT_ALLOWED;
		} else if (ex instanceof HttpMediaTypeNotSupportedException) {
			return HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
		} else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			return HttpServletResponse.SC_NOT_ACCEPTABLE;
		} else if (ex instanceof MissingPathVariableException) {
			return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		} else if (ex instanceof MissingServletRequestParameterException) {
			return HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof ServletRequestBindingException) {
			return HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof ConversionNotSupportedException) {
			return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		} else if (ex instanceof TypeMismatchException) {
			return HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof HttpMessageNotReadableException) {
			return HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof HttpMessageNotWritableException) {
			return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		} else if (ex instanceof MethodArgumentNotValidException) {
			return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		} else if (ex instanceof MissingServletRequestPartException) {
			return HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof BindException) {
			return HttpServletResponse.SC_BAD_REQUEST;
		}
		return null;

	}

}