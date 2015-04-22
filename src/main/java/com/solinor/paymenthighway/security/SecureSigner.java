/**
 * 
 */
package com.solinor.paymenthighway.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.solinor.paymenthighway.PaymentHighwayUtility;

/**
 * Creates a signature for PaymentHighway messages
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
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
				keySpec = new SecretKeySpec(this.secretKey.getBytes("UTF-8"),
						Algorithm);
				signer = Mac.getInstance(Algorithm);
				signer.init(keySpec);
			} catch (UnsupportedEncodingException | NoSuchAlgorithmException
					| InvalidKeyException e) {
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
	 * @param sphKeyValues
	 * @param body
	 * @return String eg:
	 *         "SPH1 testKey 51dcbaf5a9323daed24c0cdc5bb5d344f321aa84435b64e5da3d8f6c49370532"
	 */
	public String createSignature(String method, String uri,
			List<NameValuePair> keyValues, String body) {

		String signedString = String.format("%s %s %s", SignatureScheme,
				secretKeyId, sign(method, uri, keyValues, body));

		return signedString;
	}

	/**
	 * Create signature String from the actual parameters
	 * 
	 * @param method
	 * @param uri
	 * @param sphKeyValues
	 * @param body
	 * @return String signature
	 */
	private String sign(String method, String uri,
			List<NameValuePair> keyValues, String body) {
		String stringToSign = String.format("%s\n%s\n%s\n%s", method, uri,
				concatenateKeyValues(keyValues), body.trim());

		// System.out.println("String to Sign:\n" + stringToSign);
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

		for (Iterator<NameValuePair> it = keyValues.iterator(); it.hasNext();) {
			NameValuePair entry = it.next();
			keyValuesString += entry.getName().toLowerCase() + ":"
					+ entry.getValue() + "\n";
		}

		String result = keyValuesString.substring(0,
				keyValuesString.length() - 1);

		return result;
	}
	
	/**
	 * Authenticate response by checking the response signature
	 * @param response
	 * @return boolean true if signatures match
	 */
	public boolean authenticate(String method, String uri, HttpResponse response, String content) {
		
		List<NameValuePair> nameValuePairs = this.getHeadersAsNameValuePairs(response.getAllHeaders());
		
		String receivedSignature = "";
		Iterator<NameValuePair> iterator = nameValuePairs.iterator();
		while( iterator.hasNext()) {
			NameValuePair entry = iterator.next();
			if (entry.getName().equalsIgnoreCase("Signature")) {
				receivedSignature = entry.getValue();
				break;
			}
		}
		PaymentHighwayUtility.sortParameters(PaymentHighwayUtility.parseSphParameters(nameValuePairs));

		String createdSignature = this.createSignature(method, uri, nameValuePairs, content);

		if (receivedSignature.equals(createdSignature)) {
			return true;
		}
		return false;
	}
	
	private List<NameValuePair> getHeadersAsNameValuePairs(Header[] headers) {
		ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
		for (int i = 0; i < headers.length; i++) {
	        NameValuePair nameValuePair = 
	        		new BasicNameValuePair(headers[i].getName(), headers[i].getValue());
	        list.add(nameValuePair);
		}
		return list;
	}
}