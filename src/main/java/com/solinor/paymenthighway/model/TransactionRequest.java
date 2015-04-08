package com.solinor.paymenthighway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Transaction request POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class TransactionRequest {

	String amount = null;
	String currency = null;
	boolean blocking = true;	
	Card card = null; 
	Token token;
	
	public TransactionRequest() { 
		
	}
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public boolean isBlocking() {
		return blocking;
	}
	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}

	public static class Card {
		String pan;
		@JsonProperty("expiry_year")
		String expiryYear;
		@JsonProperty("expiry_month")
		String expiryMonth;
		String cvc;
		String verification;
		
		public String getPan() {
			return pan;
		}
		public void setPan(String pan) {
			this.pan = pan;
		}
		public String getExpiryYear() {
			return expiryYear;
		}
		public void setExpiryYear(String expiryYear) {
			this.expiryYear = expiryYear;
		}
		public String getExpiryMonth() {
			return expiryMonth;
		}
		public void setExpiryMonth(String expiryMonth) {
			this.expiryMonth = expiryMonth;
		}
		public String getCvc() {
			return cvc;
		}
		public void setCvc(String cvc) {
			this.cvc = cvc;
		}
		public String getVerification() {
			return verification;
		}
		public void setVerification(String verification) {
			this.verification = verification;
		}
		
	}

	public static class Token {
		String code;
		String message;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
}
