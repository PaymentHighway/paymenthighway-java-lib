package com.solinor.paymenthighway.model;

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
	Token token = null;

	public TransactionRequest(String amount, String currency, Card card) {
		this.amount = amount;
		this.currency = currency;
		this.card = card;
	}

	public TransactionRequest(String amount, String currency, boolean blocking,
			Card card) {
		this.amount = amount;
		this.currency = currency;
		this.blocking = blocking;
		this.card = card;
	}

	public TransactionRequest(String amount, String currency, Token token) {
		this.amount = amount;
		this.currency = currency;
		this.token = token;
	}

	public TransactionRequest(String amount, String currency, boolean blocking,
			Token token) {
		this.amount = amount;
		this.currency = currency;
		this.blocking = blocking;
		this.token = token;
	}

	public TransactionRequest(String amount, String currency, boolean blocking,
			Card card, Token token) {
		this.amount = amount;
		this.currency = currency;
		this.blocking = blocking;
		this.card = card;
		this.token = token;
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
