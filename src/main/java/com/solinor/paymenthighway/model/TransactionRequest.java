package com.solinor.paymenthighway.model;

/**
 * Transaction request POJO
 */
public class TransactionRequest {

	String amount = null;
	String currency = null;
	Token token = null;
	Card card = null;
	boolean blocking = true;

	public TransactionRequest(Token token, String amount, String currency) {
		this.token = token;
		this.amount = amount;
		this.currency = currency;
	}

	public TransactionRequest(Token token, String amount, String currency,
			boolean blocking) {
		this.token = token;
		this.amount = amount;
		this.currency = currency;
		this.blocking = blocking;
	}

	public TransactionRequest(Card card, String amount, String currency) {
		this.card = card;
		this.amount = amount;
		this.currency = currency;
	}

	public TransactionRequest(Card card, String amount, String currency,
			boolean blocking) {
		this.card = card;
		this.amount = amount;
		this.currency = currency;
		this.blocking = blocking;
		
	}

	public String getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public boolean isBlocking() {
		return blocking;
	}

	public Card getCard() {
		return card;
	}

	public Token getToken() {
		return token;
	}
}
