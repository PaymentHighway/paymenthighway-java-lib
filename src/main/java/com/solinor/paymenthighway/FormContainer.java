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

	public String method() {
		return this.method;
	}

	public String baseUrl() {
		return this.baseUrl;
	}

	public String actionUrl() {
		return this.actionUrl;
	}

	public String fullUrl() {
		return this.baseUrl + this.actionUrl;
	}

	public List<NameValuePair> nameValuePairs() {
		return nameValuePairs;
	}
}
