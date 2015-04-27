/**
 * 
 */
package com.solinor.paymenthighway.connect;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.solinor.paymenthighway.security.SecureSigner;

/**
 * Custom Response Handler
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class PaymentHighwayResponseHandler implements ResponseHandler<String> {

	private SecureSigner ss = null;
	private String method = null;
	private String uri = null;
	
	public PaymentHighwayResponseHandler(SecureSigner ss, String method, String uri) {
		this.ss = ss;
		this.method = method;
		this.uri = uri;
	}
	
	@Override
	public String handleResponse(final HttpResponse response)
			throws ClientProtocolException, IOException {

		String content = EntityUtils.toString(response.getEntity());
		int status = response.getStatusLine().getStatusCode();

		if (status >= 200 && status < 300) {
			boolean authenticated = ss.authenticate(this.method, this.uri, response, content);
			if (!authenticated) {
				System.out.println("Message authentication failed. content:" + content);
				throw new ClientProtocolException(
						"Message authentication failed. content:" + content);
			}
			HttpEntity entity = response.getEntity();
			return entity != null ? content
					: null;
		} else {
			System.err.println("response=" + response.toString());
			throw new ClientProtocolException(
					"Unexpected response status: " + status);
		}
	}
}

