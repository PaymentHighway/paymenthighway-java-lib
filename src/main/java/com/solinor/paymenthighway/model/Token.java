package com.solinor.paymenthighway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Token POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class Token {
	@JsonProperty("code")
	String code;
	@JsonProperty("message")
	String message;
	
	public Token (String code, String message) {
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}

}