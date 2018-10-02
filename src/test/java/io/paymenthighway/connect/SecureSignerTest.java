package io.paymenthighway.connect;

import io.paymenthighway.Constants;
import io.paymenthighway.PaymentHighwayUtility;
import io.paymenthighway.security.SecureSigner;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

    List<NameValuePair> nameValuePairs = new ArrayList<>();
    nameValuePairs.add(new BasicNameValuePair("sph-api-version", Constants.API_VERSION));
    nameValuePairs.add(new BasicNameValuePair("sph-account", "sampleAccount001"));
    nameValuePairs.add(new BasicNameValuePair("sph-amount", "990"));
    nameValuePairs.add(new BasicNameValuePair("sph-cancel-url", "https://merchant.example.com/payment/cancel"));
    nameValuePairs.add(new BasicNameValuePair("sph-currency", "EUR"));
    nameValuePairs.add(new BasicNameValuePair("sph-failure-url", "https://merchant.example.com/payment/failure"));
    nameValuePairs.add(new BasicNameValuePair("sph-merchant", "sampleMerchant001"));
    nameValuePairs.add(new BasicNameValuePair("sph-order", "1000123A"));
    nameValuePairs.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
    nameValuePairs.add(new BasicNameValuePair("sph-success-url", "https://merchant.example.com/payment/success"));
    nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
    nameValuePairs.add(new BasicNameValuePair("language", "EN"));
    nameValuePairs.add(new BasicNameValuePair("description", "this is a description"));

    String sig = ss.createSignature(formPaymentMethod, formPaymentUri, nameValuePairs, formPaymentBody);

    assertTrue(sig.contains(secretKeyId));
  }

  /**
   * Tests that signature does not change even if there is modifications done for code
   */
  @Test
  public void testConstantSignature() {

    String secretKeyId = "account001-key001";
    String secretKey = "account001-shared-secret001";

    SecureSigner ss = new SecureSigner(secretKeyId, secretKey);

    String formPaymentMethod = "POST";
    String formPaymentUri = "/form/view/payment";
    String formPaymentBody = "";

    List<NameValuePair> nameValuePairs = new ArrayList<>();
    nameValuePairs.add(new BasicNameValuePair("sph-api-version", "20180927"));
    nameValuePairs.add(new BasicNameValuePair("sph-account", "sampleAccount001"));
    nameValuePairs.add(new BasicNameValuePair("sph-amount", "990"));
    nameValuePairs.add(new BasicNameValuePair("sph-cancel-url", "https://merchant.example.com/payment/cancel"));
    nameValuePairs.add(new BasicNameValuePair("sph-currency", "EUR"));
    nameValuePairs.add(new BasicNameValuePair("sph-failure-url", "https://merchant.example.com/payment/failure"));
    nameValuePairs.add(new BasicNameValuePair("sph-merchant", "sampleMerchant001"));
    nameValuePairs.add(new BasicNameValuePair("sph-order", "1000123A"));
    nameValuePairs.add(new BasicNameValuePair("sph-request-id", "f47ac10b-58cc-4372-a567-0e02b2c3d479"));
    nameValuePairs.add(new BasicNameValuePair("sph-success-url", "https://merchant.example.com/payment/success"));
    nameValuePairs.add(new BasicNameValuePair("sph-timestamp", "2014-09-18T10:32:59Z"));
    nameValuePairs.add(new BasicNameValuePair("language", "EN"));
    nameValuePairs.add(new BasicNameValuePair("description", "this is a description"));

    String sig = ss.createSignature(formPaymentMethod, formPaymentUri, nameValuePairs, formPaymentBody);

    assertEquals("SPH1 account001-key001 68cd8138dec1cc2d4fd61d27d965a7f52946fdbcef73b8ceb65c356828c9fd41", sig);
  }
}
