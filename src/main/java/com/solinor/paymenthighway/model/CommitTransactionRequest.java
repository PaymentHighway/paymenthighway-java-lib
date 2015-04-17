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
	 * If the blocking parameter is set to false, 
	 * call will return immediately, without waiting
	 * for the transaction to be fully processed.
	 */
	public CommitTransactionRequest(String amount, String currency, boolean blocking) {
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


}
