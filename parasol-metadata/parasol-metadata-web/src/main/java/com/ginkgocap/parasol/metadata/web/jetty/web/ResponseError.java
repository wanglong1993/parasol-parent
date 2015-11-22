package com.ginkgocap.parasol.metadata.web.jetty.web;

import java.io.Serializable;

public final class ResponseError implements Serializable {
	private String message; // "Message describing the error",
	private String type;// "OAuthException",
	private int code;// 190,
	private int error_subcode;// 460,
	private String error_user_title;// "A title",
	private String error_user_msg;// "A message"
	private String trace_id;// "EJplcsCHuLu"

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getError_subcode() {
		return error_subcode;
	}

	public void setError_subcode(int error_subcode) {
		this.error_subcode = error_subcode;
	}

	public String getError_user_title() {
		return error_user_title;
	}

	public void setError_user_title(String error_user_title) {
		this.error_user_title = error_user_title;
	}

	public String getError_user_msg() {
		return error_user_msg;
	}

	public void setError_user_msg(String error_user_msg) {
		this.error_user_msg = error_user_msg;
	}

	public String getTrace_id() {
		return trace_id;
	}

	public void setTrace_id(String trace_id) {
		this.trace_id = trace_id;
	}

}
