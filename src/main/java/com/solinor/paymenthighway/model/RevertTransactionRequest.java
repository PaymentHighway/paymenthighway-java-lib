/**
 * 
 */
package com.solinor.paymenthighway.model;

/**
 * Revert Transaction request POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class RevertTransactionRequest {

	String amount = null;
	boolean blocking = true;
	
	public RevertTransactionRequest() { 
		
	}
	
	/*
	 * Getter and setters.
	 */
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public boolean isBlocking() {
		return blocking;
	}
	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}
}
