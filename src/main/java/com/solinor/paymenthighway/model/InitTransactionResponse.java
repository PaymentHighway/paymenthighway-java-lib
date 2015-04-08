/**
 * 
 */
package com.solinor.paymenthighway.model;
import java.util.Map;

/**
 * InitTransactionResponse POJO
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class InitTransactionResponse {

	public java.util.UUID id = null;
	public Map<String, String> result = null;

	/* getters and setters */
	public java.util.UUID getId() {
		return id;
	}
	public void setId(java.util.UUID id) {
		this.id = id;
	}
	public Map<String, String> getResult() {
		return result;
	}
	public void setResult(Map<String, String> result) {
		this.result = result;
	}
	public String getResultCode() {
		return this.result.get("code");
	}
	public String getResultMessage() {
		return this.result.get("message");
	}
}
