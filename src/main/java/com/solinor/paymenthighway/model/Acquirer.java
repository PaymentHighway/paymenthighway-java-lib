package com.solinor.paymenthighway.model;

/**
 * Acquirer POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class Acquirer {
	String id;
	String name;
	
	public Acquirer () {}
	
	public Acquirer(String id, String name) {
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
