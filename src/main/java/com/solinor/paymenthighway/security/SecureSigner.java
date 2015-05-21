/**
 * 
 */
package com.solinor.paymenthighway.security;

import com.solinor.paymenthighway.PaymentHighwayUtility;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a signature for PaymentHighway messages
 */
public class SecureSigner {

	private static final String SignatureScheme = "SPH1";
	private static final String Algorithm = "HmacSHA256";

	private String secretKeyId = null;
	private String secretKey = null;

	private Mac signer = null;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param key
	 */
	public SecureSigner(String id, String key) {
		this.secretKeyId = id;
		this.secretKey = key;
		this.signer = this.initSigner();
	}

	/**
	 * Init signer
	 * 
	 * @return javax.crypto.Mac Instance
	 */
	private Mac initSigner() {
		if (this.signer == null) {
			SecretKeySpec keySpec;
			try {
				keySpec = new SecretKeySpec(this.secretKey.getBytes("UTF-8"), Algorithm);
				signer = Mac.getInstance(Algorithm);
				signer.init(keySpec);
			} catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
				e.printStackTrace();
			}
		}

		return signer;
	}

	/**
	 * Create signature
	 * 
	 * @param method
	 * @param uri
	 * @param body
	 * @return String eg:
	 *         "SPH1 testKey 51dcbaf5a9323daed24c0cdc5bb5d344f321aa84435b64e5da3d8f6c49370532"
	 */
	public String createSignature(String method, String uri, List<NameValuePair> keyValues, String body) {

		return String.format("%s %s %s", SignatureScheme, secretKeyId, sign(method, uri, keyValues, body));
	}

	/**
	 * Create signature String from the actual parameters
	 * 
	 * @param method
	 * @param uri
	 * @param body
	 * @return String signature
	 */
	private String sign(String method, String uri, List<NameValuePair> keyValues, String body) {
		String stringToSign = String.format("%s\n%s\n%s\n%s", method, uri, concatenateKeyValues(keyValues), body.trim());

		byte[] signature = null;
		try {
			signature = this.signer.doFinal(stringToSign.getBytes("UTF-8"));
		} catch (IllegalStateException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return DatatypeConverter.printHexBinary(signature).toLowerCase();
	}

	/**
	 * Concanate key values into key:param\n String
	 * 
	 * @param keyValues
	 * @return String
	 */
	private String concatenateKeyValues(List<NameValuePair> keyValues) {

		String keyValuesString = "";

		for (NameValuePair entry : keyValues) {
			keyValuesString += entry.getName().toLowerCase() + ":" + entry.getValue() + "\n";
		}

		return keyValuesString.substring(0, keyValuesString.length() - 1);
	}
	
	/**
	 * Authenticate response by checking the response signature
	 * @param response
	 * @return boolean true if signatures match
	 */
	public boolean authenticate(String method, String uri, HttpResponse response, String content) {
		
		List<NameValuePair> nameValuePairs = this.getHeadersAsNameValuePairs(response.getAllHeaders());
		
		String receivedSignature = "";
		for (NameValuePair entry : nameValuePairs) {
			if (entry.getName().equalsIgnoreCase("Signature")) {
				receivedSignature = entry.getValue();
				break;
			}
		}
		PaymentHighwayUtility.sortParameters(PaymentHighwayUtility.parseSphParameters(nameValuePairs));

		String createdSignature = this.createSignature(method, uri, nameValuePairs, content);

		return receivedSignature.equals(createdSignature);
	}
	
	private List<NameValuePair> getHeadersAsNameValuePairs(Header[] headers) {
		ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Header header : headers) {
			NameValuePair nameValuePair = new BasicNameValuePair(header.getName(), header.getValue());
			list.add(nameValuePair);
		}
		return list;
	}
}
