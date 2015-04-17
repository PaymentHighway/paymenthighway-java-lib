package com.solinor.paymenthighway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Card POJO (with partial pan)
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class PartialCard {
	
	@JsonProperty("partial_pan")
	String partialPan;
	@JsonProperty("type")
	String type;
	@JsonProperty("expire_year")
	String expireYear;
	@JsonProperty("expire_month")
	String expireMonth;
	
	public String getPartialPan() {
		return partialPan;
	}
	public String getType() {
		return type;
	}
	public String getExpiryYear() {
		return expireYear;
	}
	public String getExpiryMonth() {
		return expireMonth;
	}
	
}