package io.paymenthighway.connect;

import io.paymenthighway.PaymentHighwayUtility;
import io.paymenthighway.security.SecureSigner;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SecureSignerTest {

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

	/**
	 * Test1
	 */
	@Test
	public void testCreateSignature() {
		
		String secretKeyId = "account001-key001";
		String secretKey = "account001-shared-secret001";

		SecureSigner ss = new SecureSigner(secretKeyId, secretKey);
		
		String formPaymentMethod = "POST";
        String formPaymentUri = "/form/view/payment";
        String formPaymentBody = "";
      
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("sph-account", "sampleAccount001"));
        nameValuePairs.add(new BasicNameValuePair("sph-amount", "990"));
        nameValuePairs.add(new BasicNameValuePair("sph-cancel-url", "https://merchant.example.com/payment/cancel"));
        nameValuePairs.add(new BasicNameValuePair("sph-currency", "EUR"));
        nameValuePairs.add(new BasicNameValuePair("sph-failure-url", "https://merchant.example.com/payment/failure"));
        nameValuePairs.add(new BasicNameValuePair("sph-merchant", "sampleMerchant001"));
        nameValuePairs.add(new BasicNameValuePair("sph-order", "1000123A"));
        nameValuePairs.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        nameValuePairs.add(new BasicNameValuePair("sph-success-url",  "https://merchant.example.com/payment/success"));
        nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        nameValuePairs.add(new BasicNameValuePair("language", "EN"));
        nameValuePairs.add(new BasicNameValuePair("description", "this is a description"));
        
        String sig = ss.createSignature(formPaymentMethod, formPaymentUri, nameValuePairs, formPaymentBody);
       
        assertTrue(sig.contains(secretKeyId));
	}
}
