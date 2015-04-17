/**
 * 
 */
package com.solinor.paymenthighway.connect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.solinor.paymenthighway.PaymentHighwayUtility;
import com.solinor.paymenthighway.json.JsonGenerator;
import com.solinor.paymenthighway.json.JsonParser;
import com.solinor.paymenthighway.model.CommitTransactionRequest;
import com.solinor.paymenthighway.model.CommitTransactionResponse;
import com.solinor.paymenthighway.model.InitTransactionResponse;
import com.solinor.paymenthighway.model.ReportResponse;
import com.solinor.paymenthighway.model.RevertTransactionRequest;
import com.solinor.paymenthighway.model.TokenizationResponse;
import com.solinor.paymenthighway.model.TransactionRequest;
import com.solinor.paymenthighway.model.TransactionResponse;
import com.solinor.paymenthighway.model.TransactionStatusResponse;
import com.solinor.paymenthighway.security.SecureSigner;

/**
 * PaymentHighway Payment API Connections
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 *
 */
public class PaymentAPIConnection {

	/* Payment API headers */
	private static final String USER_AGENT = "PaymentHighway Java Lib";
	private static final String METHOD_POST = "POST";
	private static final String METHOD_GET = "GET";

	private String serviceUrl = "";
	private String signatureKeyId = null;
	private String signatureSecret = null;
	private String account = null;
	private String merchant = null;

	/**
	 * Constructor
	 * 
	 * @param serviceUrl
	 * @param account
	 * @param merchant
	 * @param signatureKeyId
	 * @param signatureSecret
	 */
	public PaymentAPIConnection(String serviceUrl, String signatureKeyId,
			String signatureSecret, String account, String merchant) {

		this.serviceUrl = serviceUrl;
		this.signatureKeyId = signatureKeyId;
		this.signatureSecret = signatureSecret;
		this.account = account;
		this.merchant = merchant;
	}

	public InitTransactionResponse initTransactionHandle() 
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// sort alphabetically per key
		List<NameValuePair> nameValuePairs = this.sortParameters(createNameValuePairs());

		final String paymentUri = "/transaction";
		try {
			HttpPost httpPost = new HttpPost(this.serviceUrl + paymentUri);

			// create signature
			String signature = this.createSignature(METHOD_POST, paymentUri,
					nameValuePairs, null);
			nameValuePairs.add(new BasicNameValuePair("signature", signature));

			// add request headers
			this.addHeaders(httpPost, nameValuePairs);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						System.err.println("response=" + response.toString());
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};

			JsonParser parser = new JsonParser();
			return parser.mapInitTransactionResponse(httpclient.execute(
					httpPost, responseHandler));

		} finally {
			httpclient.close();
		}
	}

	public TransactionResponse debitTransaction(UUID transactionId,
			TransactionRequest request) throws ClientProtocolException,
			IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		// sort alphabetically per key
		List<NameValuePair> nameValuePairs = this.sortParameters(createNameValuePairs());

		final String paymentUri = "/transaction/";
		final String actionUri = "/debit";
		String debitUri = paymentUri + transactionId + actionUri;

		try {
			HttpPost httpPost = new HttpPost(this.serviceUrl + debitUri);

			// create signature
			String signature = this.createSignature(METHOD_POST, debitUri,
					nameValuePairs, request);
			nameValuePairs.add(new BasicNameValuePair("signature", signature));

			// add request headers
			this.addHeaders(httpPost, nameValuePairs);

			// add request body
			this.addBody(httpPost, request);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) { // 401 PaymentHighwayAuthException?
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						System.err.println("response=" + response.toString());
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};

			JsonParser jpar = new JsonParser();
			return jpar.mapTransactionResponse(httpclient.execute(httpPost,
					responseHandler));

		} finally {
			httpclient.close();
		}
	}

	public TransactionResponse creditTransaction(UUID transactionId,
			TransactionRequest request) throws ClientProtocolException,
			IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// sort alphabetically per key
		List<NameValuePair> nameValuePairs = this.sortParameters(createNameValuePairs());

		final String paymentUri = "/transaction/";
		final String actionUri = "/credit";
		String creditUri = paymentUri + transactionId + actionUri;

		try {
			HttpPost httpPost = new HttpPost(this.serviceUrl + creditUri);

			// create signature
			String signature = this.createSignature(METHOD_POST, creditUri,
					nameValuePairs, request);
			nameValuePairs.add(new BasicNameValuePair("signature", signature));

			// add request headers
			this.addHeaders(httpPost, nameValuePairs);

			// add request body
			this.addBody(httpPost, request);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						System.err.println("response=" + response.toString());
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};

			JsonParser jpar = new JsonParser();
			return jpar.mapTransactionResponse(httpclient.execute(httpPost,
					responseHandler));

		} finally {
			httpclient.close();
		}
	}

	public TransactionResponse revertTransaction(UUID transactionId,
			RevertTransactionRequest request) throws ClientProtocolException,
			IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// sort alphabetically per key
		List<NameValuePair> nameValuePairs = this.sortParameters(createNameValuePairs());

		final String paymentUri = "/transaction/";
		final String actionUri = "/revert";
		String revertUri = paymentUri + transactionId + actionUri;

		try {
			HttpPost httpPost = new HttpPost(this.serviceUrl + revertUri);

			// create signature
			String signature = this.createSignature(METHOD_POST, revertUri,
					nameValuePairs, request);
			nameValuePairs.add(new BasicNameValuePair("signature", signature));

			// add request headers
			this.addHeaders(httpPost, nameValuePairs);

			// add request body
			this.addBody(httpPost, request);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						System.err.println("response=" + response.toString());
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};

			JsonParser jpar = new JsonParser();
			return jpar.mapTransactionResponse(httpclient.execute(httpPost,
					responseHandler));

		} finally {
			httpclient.close();
		}
	}

	public CommitTransactionResponse commitTransaction(UUID transactionId,
			CommitTransactionRequest request) throws ClientProtocolException,
			IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		// sort alphabetically per key
		List<NameValuePair> nameValuePairs = this.sortParameters(createNameValuePairs());

		final String paymentUri = "/transaction/";
		final String actionUri = "/commit";
		String commitUri = paymentUri + transactionId + actionUri;

		try {
			HttpPost httpPost = new HttpPost(this.serviceUrl + commitUri);

			// create signature
			String signature = this.createSignature(METHOD_POST, commitUri,
					nameValuePairs, request);
			nameValuePairs.add(new BasicNameValuePair("signature", signature));

			// add request headers
			this.addHeaders(httpPost, nameValuePairs);

			// add request body
			this.addBody(httpPost, request);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						System.err.println("response=" + response.toString());
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};

			JsonParser jpar = new JsonParser();
			return jpar.mapCommitTransactionResponse(httpclient.execute(
					httpPost, responseHandler));

		} finally {
			httpclient.close();
		}
	}

	public TransactionStatusResponse transactionStatus(UUID transactionId)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// sort alphabetically per key
		List<NameValuePair> nameValuePairs = this.sortParameters(createNameValuePairs());

		final String paymentUri = "/transaction/";

		String statusUri = paymentUri + transactionId;

		try {
			HttpGet httpGet = new HttpGet(this.serviceUrl + statusUri);

			// create signature
			String signature = this.createSignature(METHOD_GET, statusUri,
					nameValuePairs, null);
			nameValuePairs.add(new BasicNameValuePair("signature", signature));

			// add request headers
			this.addHeaders(httpGet, nameValuePairs);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						System.err.println("response=" + response.toString());
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};

			JsonParser jpar = new JsonParser();
			return jpar.mapTransactionStatusResponse(httpclient.execute(
					httpGet, responseHandler));

		} finally {
			httpclient.close();
		}
	}

	public TokenizationResponse tokenization(String tokenizationId)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// sort alphabetically per key
		List<NameValuePair> nameValuePairs = this.sortParameters(createNameValuePairs());

		final String paymentUri = "/tokenization/";

		String tokenUri = paymentUri + tokenizationId;
		
		try {
			HttpGet httpGet = new HttpGet(this.serviceUrl + tokenUri);

			// create signature
			String signature = this.createSignature(METHOD_GET, tokenUri,
					nameValuePairs, null);
			nameValuePairs.add(new BasicNameValuePair("signature", signature));

			// add request headers
			this.addHeaders(httpGet, nameValuePairs);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						System.err.println("response=" + response.toString());
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};

			JsonParser jpar = new JsonParser();
			return jpar.mapTokenizationResponse(httpclient.execute(httpGet,
					responseHandler));

		} finally {
			httpclient.close();
		}
	}

	public ReportResponse fetchReport(String date)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// sort alphabetically per key
		List<NameValuePair> nameValuePairs = this.sortParameters(createNameValuePairs());

		final String reportUri = "/report/batch/";

		String tokenUri = reportUri + date;

		try {
			HttpGet httpGet = new HttpGet(this.serviceUrl + tokenUri);

			// create signature
			String signature = this.createSignature(METHOD_GET, tokenUri,
					nameValuePairs, null);
			nameValuePairs.add(new BasicNameValuePair("signature", signature));

			// add request headers
			this.addHeaders(httpGet, nameValuePairs);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			JsonParser jpar = new JsonParser();
			return jpar.mapReportResponse(httpclient.execute(httpGet,
					responseHandler));
			
		} finally {
			httpclient.close();
		}

	}

	protected void addHeaders(HttpRequestBase httpPost,
			List<NameValuePair> nameValuePairs) {

		httpPost.addHeader(HTTP.USER_AGENT, USER_AGENT);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");

		ListIterator<NameValuePair> li = nameValuePairs.listIterator();

		while (li.hasNext()) {
			NameValuePair param = li.next();
			httpPost.addHeader(param.getName(), param.getValue());
		}
	}

	private void addBody(HttpPost httpPost, Object request) {
		JsonGenerator jsonGen = new JsonGenerator();
		String requestBody = jsonGen.createTransactionJson(request);
		StringEntity requestEntity = new StringEntity(requestBody, "utf-8");

		httpPost.setEntity(requestEntity);
	}

	/**
	 * Create secure signature
	 * 
	 * @param method
	 * @param uri
	 * @param formPaymentSphParameters
	 * @return String signature
	 */
	private String createSignature(String method, String uri,
			List<NameValuePair> nameValuePairs, Object request) {

		nameValuePairs = this.parseParameters(nameValuePairs);
		SecureSigner ss = new SecureSigner(this.signatureKeyId,
				this.signatureSecret);
		String json = "";
		if (request != null) {
			JsonGenerator jsonGenerator = new JsonGenerator();
			json = jsonGenerator.createTransactionJson(request);
		}
		return ss.createSignature(method, uri, nameValuePairs, json);
	}

	/**
	 * Signature is formed from parameters that start with "sph-" Therefore we
	 * remove other parameters from the signature param set.
	 * 
	 * @param map TreeMap that may include all params           
	 * @return TreeMap with only params starting "sph-"
	 */
	protected List<NameValuePair> parseParameters(List<NameValuePair> map) {
		for (Iterator<NameValuePair> it = map.iterator(); it.hasNext();) {
			NameValuePair entry = it.next();
			if (!entry.getName().startsWith("sph-")) {
				it.remove();
			}
		}
		return map;
	}

	/**
	 * Sort alphabetically per key
	 * 
	 * @param nameValuePairs
	 * @return List sorted list
	 */
	protected List<NameValuePair> sortParameters(List<NameValuePair> nameValuePairs) {
		Comparator<NameValuePair> comp = new Comparator<NameValuePair>() {
			@Override
			public int compare(NameValuePair p1, NameValuePair p2) {
				return p1.getName().compareTo(p2.getName());
			}
		};
		Collections.sort(nameValuePairs, comp);
		return nameValuePairs;
	}

	/**
	 * Create name value pairs
	 * @return
	 */
	private List<NameValuePair> createNameValuePairs() {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("sph-account", this.account));
		nameValuePairs.add(new BasicNameValuePair("sph-merchant", this.merchant));
		nameValuePairs.add(new BasicNameValuePair("sph-timestamp",
				PaymentHighwayUtility.getUtcTimestamp()));
		nameValuePairs.add(new BasicNameValuePair("sph-request-id",
				PaymentHighwayUtility.createRequestId()));
		return nameValuePairs;
	}
}
