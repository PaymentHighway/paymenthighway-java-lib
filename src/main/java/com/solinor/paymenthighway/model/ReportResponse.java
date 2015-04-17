/**
 * 
 */
package com.solinor.paymenthighway.model;

/**
 * Report Response POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class ReportResponse {

	ReportResponse.Result result;
	
	Settlement[] settlements;
	
	public ReportResponse.Result getResult() {
		return this.result;
	}
	public Settlement[] getSettlements() {
		return this.settlements;
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
