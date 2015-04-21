/**
 * 
 */
package com.solinor.paymenthighway;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * PaymentHighway Form API tests
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class FormAPITest {

	Properties props = null;
	private String serviceUrl;
	private String signatureKeyId;
	private String signatureSecret;
	
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
		// required system information and authentication credentials
        // we read this from file, but can be from everywhere
		try {
			this.props = PaymentHighwayUtility.getProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.serviceUrl = this.props.getProperty("service_url");
        this.signatureKeyId = this.props.getProperty("signature_key_id");
        this.signatureSecret = this.props.getProperty("signature_secret");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddCard() {
		
		// required fields
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("sph-account", "test"));
        nameValuePairs.add(new BasicNameValuePair("sph-amount", "990"));
        nameValuePairs.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com/"));
        nameValuePairs.add(new BasicNameValuePair("sph-currency", "EUR"));
        nameValuePairs.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
        nameValuePairs.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        nameValuePairs.add(new BasicNameValuePair("sph-order", "1000123A"));
        nameValuePairs.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
        nameValuePairs.add(new BasicNameValuePair("sph-success-url", "https://www.paymenthighway.fi/"));
        nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        nameValuePairs.add(new BasicNameValuePair("language", "EN"));
        
        // create the payment highway service
        FormAPI service = new FormAPI(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
        
        String result = null;
		try {
			result = service.addCard(nameValuePairs);
		} catch (IOException e) {
			e.printStackTrace();
		}
        assertTrue(result.contains("Payment Card Information"));
	}
	@Test
	public void testPayWithCard() {
		
		// required fields
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("sph-account", "test"));
        nameValuePairs.add(new BasicNameValuePair("sph-amount", "990"));
        nameValuePairs.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com/"));
        nameValuePairs.add(new BasicNameValuePair("sph-currency", "EUR"));
        nameValuePairs.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
        nameValuePairs.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        nameValuePairs.add(new BasicNameValuePair("sph-order", "1000123A"));
        nameValuePairs.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
        nameValuePairs.add(new BasicNameValuePair("sph-success-url", "https://www.paymenthighway.fi/"));
        nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        nameValuePairs.add(new BasicNameValuePair("language", "EN"));
        nameValuePairs.add(new BasicNameValuePair("description", "payment description"));
        
        // create the payment highway service
        FormAPI service = new FormAPI(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
        
        String result = null;
		try {
			result = service.payWithCard(nameValuePairs);
		} catch (IOException e) {
			e.printStackTrace();
		}
        assertTrue(result.contains("card_number_formatted"));
	}
	@Test
	public void testAddCardAndPay() {
		
		// required fields
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("sph-account", "test"));
        nameValuePairs.add(new BasicNameValuePair("sph-amount", "990"));
        nameValuePairs.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com/"));
        nameValuePairs.add(new BasicNameValuePair("sph-currency", "EUR"));
        nameValuePairs.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
        nameValuePairs.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        nameValuePairs.add(new BasicNameValuePair("sph-order", "1000123A"));
        nameValuePairs.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
        nameValuePairs.add(new BasicNameValuePair("sph-success-url", "https://www.paymenthighway.fi/"));
        nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        nameValuePairs.add(new BasicNameValuePair("language", "EN"));
        nameValuePairs.add(new BasicNameValuePair("description", "payment description"));
        
        // create the payment highway service
        FormAPI service = new FormAPI(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
        
        String result = null;
		try {
			result = service.addCardAndPay(nameValuePairs);
		} catch (IOException e) {
			e.printStackTrace();
		}
        assertTrue(result.contains("Test customer"));
        assertTrue(result.contains("sph-card-form"));
	}
}
