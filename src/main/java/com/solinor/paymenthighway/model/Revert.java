package com.solinor.paymenthighway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Revert POJO
 */
public class Revert {
	
	@JsonProperty("type")
	String type;
	@JsonProperty("status")
	Status status;
	@JsonProperty("amount")
	String amount;
	@JsonProperty("timestamp")
	String timestamp;
	@JsonProperty("modified")
	String modified;
	@JsonProperty("filing_code")
	String filingCode;

	public String getType() {
		return type;
	}
	public String getAmount() {
		return amount;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public String getModified() {
		return modified;
	}
	public String getFilingCode() {
		return filingCode;
	}
	public Status getStatus() {
		return status;
	}
}
