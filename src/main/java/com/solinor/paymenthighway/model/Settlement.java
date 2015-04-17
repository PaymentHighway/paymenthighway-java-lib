package com.solinor.paymenthighway.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Settlement POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class Settlement {

	@JsonProperty("status")
	Status status;
	@JsonProperty("id")
	UUID id;
	@JsonProperty("batch")
	String batch;
	@JsonProperty("timestamp")
	String timestamp;
	@JsonProperty("reference")
	String reference;
	@JsonProperty("merchant")
	Merchant merchant;
	@JsonProperty("acquirer")
	Acquirer acquirer;
	@JsonProperty("transaction_count")
	String transactionCount;
	@JsonProperty("net_amount")
	String netAmount;
	@JsonProperty("currency")
	String currency;
	@JsonProperty("transactions")
	Settlement.Transaction[] transactions;
	
	public static class Transaction {

		@JsonProperty("id")
		String id;
		@JsonProperty("timestamp")
		String timestamp;
		@JsonProperty("type")
		String type;
		@JsonProperty("partial_pan")
		String partialPan;
		@JsonProperty("amount")
		String amount;
		@JsonProperty("currency")
		String currency;
		@JsonProperty("filing_code")
		String filingCode;
		@JsonProperty("authorization_code")
		String authorizationCode;
		@JsonProperty("verification_method")
		String verificationMethod;
		@JsonProperty("status")
		Status status;
		
		public String getId() {
			return id;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public String getType() {
			return type;
		}
		public String getPartialPan() {
			return partialPan;
		}
		public String getAmount() {
			return amount;
		}
		public String getCurrency() {
			return currency;
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
		public Status getStatus() {
			return status;
		}
	}
	
	public Status getStatus() {
		return status;
	}

	public UUID getId() {
		return id;
	}

	public String getBatch() {
		return batch;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getReference() {
		return reference;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public Acquirer getAcquirer() {
		return acquirer;
	}

	public String getTransactionCount() {
		return transactionCount;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public Settlement.Transaction[] getTransactions() {
		return transactions;
	}

}
