package io.paymenthighway.model.request;

/**
 * Revert Transaction request POJO
 */
public class RevertTransactionRequest {

	String amount = null;
	boolean blocking = true;
	
	/**
	 * Reverts the full amount
	 */
	public RevertTransactionRequest() {
	}
	/**
	 * reverts specified amount
	 * @param amount
	 */
	public RevertTransactionRequest(String amount) {
		this.amount = amount;
	}
	/**
	 * reverts specified amount
	 * If the blocking parameter is set to false, the debit 
	 * and credit calls return immediately, without waiting
	 * for the transaction to be fully processed. In this case 
	 * one is encouraged to poll the /transaction/<id> GET request
	 * to check the result of the transaction
	 * 
	 * @param amount
	 * @param blocking
	 */
	public RevertTransactionRequest(String amount, boolean blocking) { 
		this.amount = amount;
		this.blocking = blocking;
	}
	
	/*
	 * Getters.
	 */
	public String getAmount() {
		return amount;
	}
	public boolean isBlocking() {
		return blocking;
	}
}
