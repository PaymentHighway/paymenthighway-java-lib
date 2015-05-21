package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Settlement POJO
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
	Transaction[] transactions;

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

	public Transaction[] getTransactions() {
		return transactions;
	}
}
