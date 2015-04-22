package com.solinor.paymenthighway.model;

/**
 * Merchant POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class Merchant {
	String id;
	String name;
	
	public Merchant () {}
	
	public Merchant(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}