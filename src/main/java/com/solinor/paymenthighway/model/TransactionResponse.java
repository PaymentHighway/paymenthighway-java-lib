package com.solinor.paymenthighway.model;

/**
 * Transaction Response POJO
 */
public class TransactionResponse {

	TransactionResponse.Result result = null;
	
	public TransactionResponse.Result getResult() {
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
