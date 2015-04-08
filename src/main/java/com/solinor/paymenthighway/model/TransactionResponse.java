package com.solinor.paymenthighway.model;

import java.util.Map;

/**
 * Transaction Response POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class TransactionResponse {

	/** result code and message */
	public Map<String, String> result = null;

	/* getters and setter */
	public Map<String, String> getResult() {
		return result;
	}
	public void setResult(Map<String, String> result) {
		this.result = result;
	}

}
