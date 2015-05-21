package com.solinor.paymenthighway.model;

/**
 * Status POJO
 */
public class Status {
	String state;
	String code; 
	String message;
	
	public Status() {}
			
	public Status(String state, String code) {
		this.state = state;
		this.code = code;
		this.message = null;
	}
	
	public Status(String state, String code, String message) {
		this.state = state;
		this.code = code;
		this.message = message;
	}
	
	public String getState() {
		return state;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}