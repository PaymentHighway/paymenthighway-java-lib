package com.solinor.paymenthighway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Card POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class Card {
	
	@JsonProperty("pan")
	String pan;
	@JsonProperty("expiry_year")
	String expiryYear;
	@JsonProperty("expiry_month")
	String expiryMonth;
	@JsonProperty("cvc")
	String cvc;
	@JsonProperty("verification")
	String verification;
	
	public Card(String pan, String expiryYear, String expiryMonth, String cvc, String verification) { 
		this.pan = pan;
		this.expiryYear = expiryYear;
		this.expiryMonth = expiryMonth;
		this.cvc = cvc;
		this.verification = verification;
	}
	
	public String getPan() {
		return pan;
	}
	public String getExpiryYear() {
		return expiryYear;
	}
	public String getExpiryMonth() {
		return expiryMonth;
	}
	public String getCvc() {
		return cvc;
	}
	public String getVerification() {
		return verification;
	}
}