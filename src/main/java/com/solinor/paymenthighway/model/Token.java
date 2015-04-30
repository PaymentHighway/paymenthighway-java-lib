package com.solinor.paymenthighway.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Token POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class Token {
	@JsonProperty("id")
	UUID id;
	
	public Token (String id) {
		this.id = UUID.fromString(id);
	}
	public Token (UUID id) {
		this.id = id;
	}
	public String getId() {
		return id.toString();
	}

}