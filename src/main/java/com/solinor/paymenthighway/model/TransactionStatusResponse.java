/**
 * 
 */
package com.solinor.paymenthighway.model;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Transaction Status Response POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class TransactionStatusResponse {
	
	public TransactionStatusResponse() { }
	/** result code and message */
	public Map<String, String> result = null;
	public Map<String, Object> transaction = null;
	
	public Map<String, Object> getTransaction() {
		return transaction;
	}
	public void setTransaction(Map<String, Object> transaction) {
		this.transaction = transaction;
	}
	/* getters and setter */
	public Map<String, String> getResult() {
		return result;
	}
	public void setResult(Map<String, String> result) {
		this.result = result;
	}
	public static class Result {
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
		
	public static class Transaction {
		UUID id;
		public static class Acquirer {
			String id;
			String name;
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			
		}
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
		public UUID getId() {
			return id;
		}
		public void setId(UUID id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getCurrentAmount() {
			return currentAmount;
		}
		public void setCurrentAmount(String currentAmount) {
			this.currentAmount = currentAmount;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		public String getModified() {
			return modified;
		}
		public void setModified(String modified) {
			this.modified = modified;
		}
		public String getFilingCode() {
			return filingCode;
		}
		public void setFilingCode(String filingCode) {
			this.filingCode = filingCode;
		}
		public String getAuthorizationCode() {
			return authorizationCode;
		}
		public void setAuthorizationCode(String authorizationCode) {
			this.authorizationCode = authorizationCode;
		}
		public String getVerificationMethod() {
			return verificationMethod;
		}
		public void setVerificationMethod(String verificationMethod) {
			this.verificationMethod = verificationMethod;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		
		
		
		public static class Status {
			String state;
			String code; 
			String message;
			public String getState() {
				return state;
			}
			public void setState(String state) {
				this.state = state;
			}
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
		public static class Card {
			@JsonProperty("partial_pan")
			String partialPan;
			String type;
			@JsonProperty("expiry_year")
			String expiryYear;
			@JsonProperty("expiry_month")
			String expiryMonth;
			public String getPartialPan() {
				return partialPan;
			}
			public void setPartialPan(String partialPan) {
				this.partialPan = partialPan;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
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
		}
		public static class Reverts {
			String type;
			String amount;
			String timestamp;
			String modified;
			@JsonProperty("filing_code")
			String filingCode;
			
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public String getAmount() {
				return amount;
			}
			public void setAmount(String amount) {
				this.amount = amount;
			}
			public String getTimestamp() {
				return timestamp;
			}
			public void setTimestamp(String timestamp) {
				this.timestamp = timestamp;
			}
			public String getModified() {
				return modified;
			}
			public void setModified(String modified) {
				this.modified = modified;
			}
			public String getFilingCode() {
				return filingCode;
			}
			public void setFilingCode(String filingCode) {
				this.filingCode = filingCode;
			}
			public static class Status {
				String state;
				String code;
				String message;
				public String getState() {
					return state;
				}
				public void setState(String state) {
					this.state = state;
				}
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
	} 
	
}
