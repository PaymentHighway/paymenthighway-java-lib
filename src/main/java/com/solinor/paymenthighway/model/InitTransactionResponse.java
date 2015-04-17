/**
 * 
 */
package com.solinor.paymenthighway.model;
import java.util.Map;
import java.util.UUID;

/**
 * InitTransactionResponse POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class InitTransactionResponse {

	java.util.UUID id = null;
	Map<String, String> result = null;

	/* getters */
	public java.util.UUID getId() {
		return id;
	}
	public Map<String, String> getResult() {
		return result;
	}
	public String getResultCode() {
		return this.result.get("code");
	}
	public String getResultMessage() {
		return this.result.get("message");
	}
}
