/**
 * 
 */
package com.solinor.paymenthighway.connect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.solinor.paymenthighway.PaymentHighwayUtility;

/**
 * Test class for Form API connections
 * @author Tero Kallio <tero.kallio@solinor.com>
 *
 */
public class FormAPIConnectionTest {
	
	// Payment Highway Merchant configuration
	private String serviceUrl = null;
	private String account = null;
	private String merchant =  null;
	private String signatureKeyId = null;
	private String signatureSecret = null;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Properties p = PaymentHighwayUtility.getProperties();
		this.account = p.getProperty("sph-account");
		this.merchant = p.getProperty("sph-merchant");
		this.serviceUrl = p.getProperty("service_url");
		this.signatureKeyId = p.getProperty("signature_key_id");
		this.signatureSecret = p.getProperty("signature_secret");
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCreateSignature() {
		
		List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
		
        formParameters.add(new BasicNameValuePair("sph-account", "test"));
        formParameters.add(new BasicNameValuePair("sph-amount", "990"));
        formParameters.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com"));
        formParameters.add(new BasicNameValuePair("sph-currency", "EUR"));
        formParameters.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
        formParameters.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        formParameters.add(new BasicNameValuePair("sph-order", "1000123A"));
        formParameters.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        formParameters.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
        formParameters.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        
		String uri = "/form/view/add_card";
		SecureSigner ss = new SecureSigner(this.signatureKeyId, this.signatureSecret);
		String sig = ss.createSignature("POST", uri, formParameters, "");
		assertTrue(sig.contains("SPH1"));
		
	}
	@Test
	public void testParseParameters() {
		
		List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
        
		formParameters.add(new BasicNameValuePair("sph-account", "test"));
        formParameters.add(new BasicNameValuePair("sph-amount", "990"));
        formParameters.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        formParameters.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com"));
        formParameters.add(new BasicNameValuePair("sph-currency", "EUR"));
        formParameters.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
        formParameters.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        formParameters.add(new BasicNameValuePair("sph-order", "1000123A"));
        formParameters.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        formParameters.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
        formParameters.add(new BasicNameValuePair("this should be removed", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        
        FormAPIConnection conn = new FormAPIConnection(this.serviceUrl, this.account, this.merchant, this.signatureKeyId, this.signatureSecret);
        
        List<NameValuePair> map = conn.parseParameters(formParameters);
        assertEquals(new BasicNameValuePair("sph-account", "test"), map.get(0));
        assertEquals(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"), map.get(map.size()-1));
        assertTrue(!formParameters.contains("this should be removed"));
        
	}
	@Test
	public void testSortParameters() {
		
		List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
        
        formParameters.add(new BasicNameValuePair("sph-amount", "990"));
        formParameters.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        formParameters.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com"));
        formParameters.add(new BasicNameValuePair("sph-currency", "EUR"));
		formParameters.add(new BasicNameValuePair("sph-account", "test"));
        formParameters.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
        formParameters.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        formParameters.add(new BasicNameValuePair("sph-order", "1000123A"));
        formParameters.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        formParameters.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
        formParameters.add(new BasicNameValuePair("language", "EN"));

        FormAPIConnection conn = new FormAPIConnection(this.serviceUrl, this.account, this.merchant, this.signatureKeyId, this.signatureSecret);
        conn.sortParameters(formParameters);
        assertEquals(new BasicNameValuePair("sph-account", "test"), formParameters.get(1)); // first
        assertTrue(formParameters.get(formParameters.size()-1).toString().startsWith("sph-timestamp")); // last
        
	}
	@Test
	public void testPaymenthighwayAddCard() {
		
		List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
        
        formParameters.add(new BasicNameValuePair("sph-amount", "990"));
        formParameters.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        formParameters.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com"));
        formParameters.add(new BasicNameValuePair("sph-currency", "EUR"));
		formParameters.add(new BasicNameValuePair("sph-account", "test"));
        formParameters.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
        formParameters.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        formParameters.add(new BasicNameValuePair("sph-order", "1000123A"));
        formParameters.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        formParameters.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
        formParameters.add(new BasicNameValuePair("language", "EN"));
        
        FormAPIConnection conn = new FormAPIConnection(this.serviceUrl, this.account, this.merchant, this.signatureKeyId, this.signatureSecret);
		String response = null;
		try {
			response = conn.addCardRequest(formParameters);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(response.contains("XXXX XXXX XXXX XXXX"));
		assertTrue(response.contains("viewport"));
		assertTrue(response.contains("Solinor Payment Highway"));
	}
	@Test
	public void testPaymenthighwayPayment() {
		
	
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
        nameValuePairs.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com"));
        nameValuePairs.add(new BasicNameValuePair("sph-account", "test"));
        nameValuePairs.add(new BasicNameValuePair("sph-amount", "990"));
        nameValuePairs.add(new BasicNameValuePair("sph-currency", "EUR"));
        nameValuePairs.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
        nameValuePairs.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        nameValuePairs.add(new BasicNameValuePair("sph-order", "1000123A"));
        nameValuePairs.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        nameValuePairs.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
        nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        nameValuePairs.add(new BasicNameValuePair("language", "EN"));
        nameValuePairs.add(new BasicNameValuePair("description", "this is a description field"));
        
        FormAPIConnection conn = new FormAPIConnection(this.serviceUrl, this.account, this.merchant, this.signatureKeyId, this.signatureSecret);
	
        String response = null;
		try {
			response = conn.paymentRequest(nameValuePairs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(response.contains("XXXX XXXX XXXX XXXX"));
		assertTrue(response.contains("viewport"));
		assertTrue(response.contains("Solinor Payment Highway"));
		assertTrue(response.contains("Payment Information"));
		assertTrue(response.contains("card-info"));
	}
	@Test
	public void testPaymenthighwayAddCardAndPay() {
		
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
        nameValuePairs.add(new BasicNameValuePair("sph-account", "test"));
        nameValuePairs.add(new BasicNameValuePair("sph-amount", "990"));
        nameValuePairs.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com"));
        nameValuePairs.add(new BasicNameValuePair("sph-currency", "EUR"));
        nameValuePairs.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
        nameValuePairs.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        nameValuePairs.add(new BasicNameValuePair("sph-order", "1000123A"));
        nameValuePairs.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        nameValuePairs.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
        nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        nameValuePairs.add(new BasicNameValuePair("language", "EN"));
        nameValuePairs.add(new BasicNameValuePair("description", "this is a description field"));

        FormAPIConnection conn = new FormAPIConnection(this.serviceUrl, this.account, this.merchant, this.signatureKeyId, this.signatureSecret);
	
        String response = null;
		try {
			response = conn.addCardAndPayRequest(nameValuePairs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(response.contains("XXXX XXXX XXXX XXXX"));
		assertTrue(response.contains("viewport"));
		assertTrue(response.contains("Solinor Payment Highway"));
		assertTrue(response.contains("Payment Information"));
		assertTrue(response.contains("card-info"));
	}
	@Test
	public void testAddHeaders1() {
		HttpPost httpPost = new HttpPost("http://www.anygivenurl.com");
		FormAPIConnection conn = new FormAPIConnection(this.serviceUrl, this.account, this.merchant, this.signatureKeyId, this.signatureSecret);
		conn.addHeaders(httpPost);
		Header[] headers = httpPost.getHeaders("User-Agent");
		
		assertTrue((headers[0].getName().equalsIgnoreCase("User-Agent")));
		assertTrue((headers[0].getValue().equalsIgnoreCase("PaymentHighway Java Lib")));
		
	}
	@Test
	public void testAddHeaders2() {
		HttpPost httpPost = new HttpPost("http://www.anygivenurl.com");
		FormAPIConnection conn = new FormAPIConnection(this.serviceUrl, this.account, this.merchant, this.signatureKeyId, this.signatureSecret);
		conn.addHeaders(httpPost);
		Header[] headers = httpPost.getHeaders("Content-Type");
		
		assertTrue((headers[0].getName().equalsIgnoreCase("Content-Type")));
		assertTrue((headers[0].getValue().equalsIgnoreCase("application/x-www-form-urlencoded")));
		
	}
}
