package com.ginkgocap.parasol.favorite.web.jetty.web.error;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
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

import com.alibaba.dubbo.rpc.RpcException;
import com.ginkgocap.parasol.favorite.web.jetty.web.ResponseError;

@ControllerAdvice
public class FavoriteErrorControl {
	public static Logger logger = Logger.getLogger(FavoriteErrorControl.class);
	private static Pattern service_method_parttern = Pattern.compile("Failed to invoke the method (.+?) in the service (.+?). No provider");

	@ExceptionHandler(value = { Exception.class, RuntimeException.class })
	@ResponseBody
	public ResponseEntity<Map<String, Serializable>> defaultErrorHandler(HttpServletRequest request, Exception ex) {
		ex.printStackTrace(System.err);
		ResponseError responseError = new ResponseError();
		Map<String, Serializable> errMap = new HashMap<String, Serializable>();
		errMap.put("error", responseError);
		HttpStatus status = getStatus(request, ex);

		if (ObjectUtils.equals(status, HttpStatus.BAD_REQUEST)) {
			responseError.setMessage(ex.getLocalizedMessage());
		} else if (ObjectUtils.equals(status, HttpStatus.INTERNAL_SERVER_ERROR)) {
			ResponseError prcError = processRpcExceptionError(ex); // Rpc错误
			if (prcError != null) { // prc error
				responseError.setMessage(prcError.getMessage());
				responseError.setError_subcode(prcError.getError_subcode());
				responseError.setType(prcError.getType());
			} else {
				ResponseError bizError = getErrorCode(ex); // 业务错误
				if (bizError != null) {
					responseError.setMessage(bizError.getError_subcode() == -1 ? "未知业务错误": bizError.getMessage());
					responseError.setError_subcode(bizError.getError_subcode());
					responseError.setType(bizError.getType());
				}
			}
		} else if (ObjectUtils.equals(status, HttpStatus.UNAUTHORIZED)) {
			responseError.setMessage(ex.getMessage());
			responseError.setError_subcode(HttpStatus.UNAUTHORIZED.value());
			responseError.setType("authentication");
		}

		if (ObjectUtils.equals(request.getParameter("debug"), "all")) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.flush();
			ex.printStackTrace(pw);
			errMap.put("__debug__", sw.toString());
		}
		ResponseEntity<Map<String, Serializable>> responseEntity = new ResponseEntity<Map<String, Serializable>>(errMap, status);
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
		// 业务错误
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
//		} else if (ex instanceof AccessDeniedException) {
//			return HttpServletResponse.SC_UNAUTHORIZED;
//		}
		return null;
	}

	protected ResponseError getErrorCode(Exception ex) {
		ResponseError prcError = null;
		if (ex != null && ex.getClass().getName().endsWith("ServiceException")) {
			prcError = new ResponseError();
			try {
				prcError.setError_subcode((Integer) MethodUtils.invokeMethod(ex, "getErrorCode", null));
			} catch (Exception e) {

			}
			prcError.setMessage(ex.getMessage());
			prcError.setType("BizException");
		}
		return prcError;
	}

	/**
	 * 处理RpcException
	 * 
	 * @param exception
	 * @return
	 */
	protected ResponseError processRpcExceptionError(Exception exception) {
		if (exception != null && exception instanceof RpcException) {
			ResponseError error = new ResponseError();
			RpcException rpcException = (RpcException) exception;
			if (rpcException.isBiz()) { // 业务错误
				error.setType("RpcException");
				error.setMessage("业务错误");
			} else if (rpcException.isNetwork()) {
				error.setType("RpcException");
				error.setMessage("网络故障");
			} else if (rpcException.isSerialization()) {
				error.setType("RpcException");
				error.setMessage("无法序列化");
			} else if (rpcException.isTimeout()) {
				error.setType("RpcException");
				error.setMessage("请求超时");
			} else {
				String serviceName_err = getServiceNameByRpcMessage(rpcException);
				if (serviceName_err != null) {
					logger.info(serviceName_err + " error");
					error.setType("RpcException");
					error.setMessage(serviceName_err + "停止服务，请稍后重试！");
				}
			}
			return error;
		} else {
			return null;
		}

	}

	/**
	 * 从错误日志中找到服务名称
	 * 
	 * @param errMessage
	 * @return
	 */
	private String getServiceNameByRpcMessage(RpcException rpc) {
		if (rpc != null && StringUtils.isNotBlank(rpc.getMessage())) {
			Matcher matcher = service_method_parttern.matcher(rpc.getMessage());
			if (matcher.find()) {
				return ClassUtils.getShortCanonicalName(matcher.group(2)) + "#" + matcher.group(1);
			}
		}
		return null;
	}

}
