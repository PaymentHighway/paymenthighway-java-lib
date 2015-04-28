package com.solinor.paymenthighway.model;

/**
 * Transaction request POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class TransactionRequest {

	String amount = null;
	String currency = null;
	Token token = null;
	Card card = null;
	boolean blocking = true;

	public TransactionRequest(String amount, String currency, Token token) {
		this.amount = amount;
		this.currency = currency;
		this.token = token;
	}

	public TransactionRequest(String amount, String currency, Token token,
			boolean blocking) {
		this.amount = amount;
		this.currency = currency;
		this.blocking = blocking;
		this.token = token;
	}
	
	public TransactionRequest(String amount, String currency, Card card) {
		this.amount = amount;
		this.currency = currency;
		this.card = card;
	}

	public TransactionRequest(String amount, String currency, Card card,
			boolean blocking) {
		this.amount = amount;
		this.currency = currency;
		this.blocking = blocking;
		this.card = card;
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
