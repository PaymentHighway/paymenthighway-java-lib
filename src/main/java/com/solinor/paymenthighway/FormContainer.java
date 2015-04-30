/**
 * 
 */
package com.solinor.paymenthighway;

import java.util.List;

import org.apache.http.NameValuePair;

/**
 * Everything you need for a form
 *
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class FormContainer {

	private String method;
	private String baseUrl;
	private String actionUrl;
	private List<NameValuePair> nameValuePairs;;
	
	public FormContainer(String method, String baseUrl, String actionUrl,
			List<NameValuePair> nameValuePairs) {
		this.method = method;
		this.baseUrl = baseUrl;
		this.actionUrl = actionUrl;
		this.nameValuePairs = nameValuePairs;
	}

	public String getMethod() {
		return this.method;
	}

	public String getAction() {
		return this.baseUrl + this.actionUrl;
	}

	public List<NameValuePair> getFields() {
		return nameValuePairs;
	}
}
