package com.solinor.paymenthighway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Token POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class Token {
	@JsonProperty("id")
	String id;
	@JsonProperty("cvc")
	String cvc;
	
	public Token (String id, String cvc) {
		this.id = id;
		this.cvc = cvc;
	}
	public String getId() {
		return id;
	}
	public String getCvc() {
		return cvc;
	}

}