/**
 * 
 */
package com.solinor.paymenthighway.model;

/**
 * Commit transaction request POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class CommitTransactionRequest {
	
	String amount;
	String currency;
	boolean blocking = true;

	/**
	 * 
	 */
	public CommitTransactionRequest() {
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
	
}
