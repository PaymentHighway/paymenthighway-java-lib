/**
 * 
 */
package com.solinor.paymenthighway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
 * @author tero.kallio
 *
 */
public class PaymentHighwayUtilityTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testCreateRequestId() {
		String id = PaymentHighwayUtility.createRequestId();
		// test not null
		assertNotNull(id);
		// length must be 36
		assertEquals(36, id.length());
	}
	@Test
	public void testGetUTCTimestamp() {
		String time = PaymentHighwayUtility.getUtcTimestamp();
		// test not null
		assertNotNull(time);
		// length must be 20
		assertEquals(20, time.length());
	}
	@Test
	public void testReadProperties() { 
		Properties p = null;
		try {
			p = PaymentHighwayUtility.getProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(5, p.size());	
		assertTrue(p.containsKey("service_url"));
		assertTrue(p.containsKey("sph-account"));
		assertTrue(p.containsKey("sph-merchant"));
		assertTrue(p.containsKey("signature_key_id"));
		assertTrue(p.containsKey("signature_secret"));
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

        PaymentHighwayUtility.sortParameters(formParameters);
        assertEquals(new BasicNameValuePair("sph-account", "test"), formParameters.get(1)); // first
        assertTrue(formParameters.get(formParameters.size()-1).toString().startsWith("sph-timestamp")); // last
        
	}
	@Test
	public void testParseSphParameters() {
		
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
        
        PaymentHighwayUtility.parseSphParameters(formParameters);
        assertEquals(new BasicNameValuePair("sph-account", "test"), formParameters.get(0));
        assertEquals(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"), formParameters.get(formParameters.size()-1));
        assertTrue(!formParameters.contains("this should be removed"));
        
	}
}

