/**
 * 
 */
package com.solinor.paymenthighway.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Transaction Status Response POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class TransactionStatusResponse {

	TransactionStatusResponse.Result result = null;
	TransactionStatusResponse.Transaction transaction = null;
	
	public TransactionStatusResponse.Transaction getTransaction() {
		return transaction;
	}
	public TransactionStatusResponse.Result getResult() {
		return result;
	}

	public static class Result {
		String code;
		String message;
		public String getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}		
	}
	
	public static class Transaction {
		UUID id;
		@JsonProperty("acquirer")
		Acquirer acquirer;
		String type;
		String amount;
		@JsonProperty("current_amount")
		String currentAmount;
		String currency;
		String timestamp;
		String modified;
		@JsonProperty("filing_code")
		String filingCode;
		@JsonProperty("authorization_code")
		String authorizationCode;
		@JsonProperty("verification_method")
		String verificationMethod;
		String token;
		@JsonProperty("status")
		Status status;
		@JsonProperty("card")
		PartialCard card;
		@JsonProperty("reverts")
		Revert[] reverts;
		
		public UUID getId() {
			return id;
		}
		public String getType() {
			return type;
		}
		public String getAmount() {
			return amount;
		}
		public String getCurrentAmount() {
			return currentAmount;
		}
		public String getCurrency() {
			return currency;
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
		public String getAuthorizationCode() {
			return authorizationCode;
		}
		public String getVerificationMethod() {
			return verificationMethod;
		}
		public String getToken() {
			return token;
		}
	}
} 
	

