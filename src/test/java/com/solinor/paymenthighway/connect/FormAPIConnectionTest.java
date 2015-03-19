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
 * @author tero.kallio
 *
 */
public class FormAPIConnectionTest {
	
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
        formParameters.add(new BasicNameValuePair("sph-failure-url", "http://www.terokallio.com"));
        formParameters.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        formParameters.add(new BasicNameValuePair("sph-order", "1000123A"));
        formParameters.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        formParameters.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
        formParameters.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        
        Properties p = null;
		try {
			p = PaymentHighwayUtility.getPropertyValues();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String uri = "/form/view/add_card";
		SecureSigner ss = new SecureSigner(p.getProperty("signature_key_id"), p.getProperty("signature_secret"));
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
        formParameters.add(new BasicNameValuePair("sph-failure-url", "http://www.terokallio.com"));
        formParameters.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        formParameters.add(new BasicNameValuePair("sph-order", "1000123A"));
        formParameters.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        formParameters.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
        formParameters.add(new BasicNameValuePair("this should be removed", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        
        FormAPIConnection conn = new FormAPIConnection();
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
        formParameters.add(new BasicNameValuePair("sph-failure-url", "http://www.terokallio.com"));
        formParameters.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        formParameters.add(new BasicNameValuePair("sph-order", "1000123A"));
        formParameters.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        formParameters.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
        formParameters.add(new BasicNameValuePair("language", "EN"));

        FormAPIConnection conn = new FormAPIConnection();
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
        formParameters.add(new BasicNameValuePair("sph-failure-url", "http://www.terokallio.com"));
        formParameters.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        formParameters.add(new BasicNameValuePair("sph-order", "1000123A"));
        formParameters.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        formParameters.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
        formParameters.add(new BasicNameValuePair("language", "EN"));
        
		FormAPIConnection sph = new FormAPIConnection();
		String response = null;
		try {
			response = sph.addCardRequest(formParameters);
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
        nameValuePairs.add(new BasicNameValuePair("sph-failure-url", "http://www.terokallio.com"));
        nameValuePairs.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        nameValuePairs.add(new BasicNameValuePair("sph-order", "1000123A"));
        nameValuePairs.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        nameValuePairs.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
        nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        nameValuePairs.add(new BasicNameValuePair("language", "EN"));
        nameValuePairs.add(new BasicNameValuePair("description", "this is a description field"));
        
        FormAPIConnection sph = new FormAPIConnection();
	
        String response = null;
		try {
			response = sph.paymentRequest(nameValuePairs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(response);
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
        nameValuePairs.add(new BasicNameValuePair("sph-failure-url", "http://www.terokallio.com"));
        nameValuePairs.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        nameValuePairs.add(new BasicNameValuePair("sph-order", "1000123A"));
        nameValuePairs.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        nameValuePairs.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
        nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        nameValuePairs.add(new BasicNameValuePair("language", "EN"));
        nameValuePairs.add(new BasicNameValuePair("description", "this is a description field"));

        FormAPIConnection sph = new FormAPIConnection();
	
        String response = null;
		try {
			response = sph.addCardAndPayRequest(nameValuePairs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(response);
		assertTrue(response.contains("XXXX XXXX XXXX XXXX"));
		assertTrue(response.contains("viewport"));
		assertTrue(response.contains("Solinor Payment Highway"));
		assertTrue(response.contains("Payment Information"));
		assertTrue(response.contains("card-info"));
	}
	@Test
	public void testAddHeaders1() {
		HttpPost httpPost = new HttpPost("http://www.anygivenurl.com");
		FormAPIConnection conn = new FormAPIConnection();
		conn.addHeaders(httpPost);
		Header[] headers = httpPost.getHeaders("USER_AGENT");
		
		assertTrue((headers[0].getName().equalsIgnoreCase("USER_AGENT")));
		assertTrue((headers[0].getValue().equalsIgnoreCase("PaymentHighway Java Lib")));
		
	}
	@Test
	public void testAddHeaders2() {
		HttpPost httpPost = new HttpPost("http://www.anygivenurl.com");
		FormAPIConnection conn = new FormAPIConnection();
		conn.addHeaders(httpPost);
		Header[] headers = httpPost.getHeaders("CONTENT_TYPE");
		
		assertTrue((headers[0].getName().equalsIgnoreCase("CONTENT_TYPE")));
		assertTrue((headers[0].getValue().equalsIgnoreCase("application/x-www-form-urlencoded")));
		
	}
}
