package com.solinor.paymenthighway.model;

import java.util.Map;

/**
 * Transaction Response POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class TransactionResponse {

	TransactionStatusResponse.Result result = null;
	
	public TransactionStatusResponse.Result getResult() {
		return result;
	}

	public static class Result {
		String code;
		String message;
		public String getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}		
	}
}
