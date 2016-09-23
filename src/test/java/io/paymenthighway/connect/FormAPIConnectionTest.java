package io.paymenthighway.connect;

import io.paymenthighway.PaymentHighwayUtility;
import io.paymenthighway.security.SecureSigner;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hamcrest.core.StringStartsWith;
import org.junit.*;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Test class for Form API connections
 */
public class FormAPIConnectionTest {

  // Payment Highway Merchant configuration
  private String serviceUrl = null;
  private String signatureKeyId = null;
  private String signatureSecret = null;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    Properties p = PaymentHighwayUtility.getProperties();
    this.serviceUrl = p.getProperty("service_url");
    this.signatureKeyId = p.getProperty("signature_key_id");
    this.signatureSecret = p.getProperty("signature_secret");

  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testCreateSignature() {

    List<NameValuePair> formParameters = new ArrayList<>();

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

  static String createAndTestAddCardForm(String serviceUrl, String signatureKeyId, String signatureSecret, String account, String merchant) {

    List<NameValuePair> formParameters = new ArrayList<>();

    formParameters.add(new BasicNameValuePair("sph-accept-cvc-required", "true"));
    formParameters.add(new BasicNameValuePair("sph-api-version", "20151028"));
    formParameters.add(new BasicNameValuePair("sph-amount", "990"));
    formParameters.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
    formParameters.add(new BasicNameValuePair("sph-cancel-url", "https://www.paymenthighway.fi/?cancel"));
    formParameters.add(new BasicNameValuePair("sph-currency", "EUR"));
    formParameters.add(new BasicNameValuePair("sph-account", account));
    formParameters.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/?failed"));
    formParameters.add(new BasicNameValuePair("sph-merchant", merchant));
    formParameters.add(new BasicNameValuePair("sph-order", "1000123A"));
    formParameters.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
    formParameters.add(new BasicNameValuePair("sph-success-url", "https://www.paymenthighway.fi/?success"));
    formParameters.add(new BasicNameValuePair("language", "EN"));

    FormAPIConnection conn = new FormAPIConnection(serviceUrl, signatureKeyId, signatureSecret);

    String response = null;
    try {
      response = conn.addCardRequest(formParameters);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("XXXX XXXX XXXX XXXX"));
    assertTrue(response.contains("viewport"));
    assertTrue(response.contains("Solinor Payment Highway"));

    Matcher matcher = Pattern.compile("(?<=form action=\").{51}").matcher(response);
    assertTrue(matcher.find());
    return matcher.group();
  }

  static String postCardFormAndReturnLastQueryString(
      String serviceUrl,
      String formUri,
      String cardNumber,
      String expirationMonth,
      String expirationYear,
      String cvc
  ) {
    HttpPost httpPost = new HttpPost(serviceUrl + formUri);
    List<NameValuePair> submitParameters = new ArrayList<>();
    submitParameters.add(new BasicNameValuePair("card_number", cardNumber));
    submitParameters.add(new BasicNameValuePair("expiration_month", expirationMonth));
    submitParameters.add(new BasicNameValuePair("expiration_year", expirationYear));
    submitParameters.add(new BasicNameValuePair("cvv", cvc));

    httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
    httpPost.addHeader("User-Agent", "PaymentHighway Java Lib");

    String submitResponse = null;
    HttpClientContext context = null;

    try {
      httpPost.setEntity(new UrlEncodedFormEntity(submitParameters));
      CloseableHttpClient httpclient = HttpClients.createDefault();
      context = HttpClientContext.create();

      submitResponse = httpclient.execute(httpPost, FormAPIConnection.bodyResponseHandler(), context);
      httpclient.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // test that response is from success url
    assertNotNull(submitResponse);
    assertTrue(submitResponse.contains("paymenthighway"));

    List<URI> redirectURIs = context.getRedirectLocations();
    assertEquals(2, redirectURIs.size());

    String query = redirectURIs.get(1).getQuery();
    assertThat(query, StringStartsWith.startsWith("success"));
    return query;
  }

  @Test
  public void testPaymenthighwayAddCard() {

    createAndTestAddCardForm(this.serviceUrl, this.signatureKeyId, this.signatureSecret, "test", "test_merchantId");
  }

  @Test
  public void testPaymenthighwayPayment() {

    List<NameValuePair> nameValuePairs = new ArrayList<>();

    nameValuePairs.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com"));
    nameValuePairs.add(new BasicNameValuePair("sph-account", "test"));
    nameValuePairs.add(new BasicNameValuePair("sph-amount", "990"));
    nameValuePairs.add(new BasicNameValuePair("sph-currency", "EUR"));
    nameValuePairs.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
    nameValuePairs.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
    nameValuePairs.add(new BasicNameValuePair("sph-order", "1000123A"));
    nameValuePairs.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
    nameValuePairs.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
    nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
    nameValuePairs.add(new BasicNameValuePair("language", "EN"));
    nameValuePairs.add(new BasicNameValuePair("description", "this is a description field"));

    FormAPIConnection conn = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = conn.paymentRequest(nameValuePairs);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertTrue(response.contains("XXXX XXXX XXXX XXXX"));
    assertTrue(response.contains("viewport"));
    assertTrue(response.contains("Solinor Payment Highway"));
    assertTrue(response.contains("Payment Information"));
    assertTrue(response.contains("card-info"));
  }

  @Test
  public void testPaymenthighwayAddCardAndPay() {

    List<NameValuePair> nameValuePairs = new ArrayList<>();

    nameValuePairs.add(new BasicNameValuePair("sph-account", "test"));
    nameValuePairs.add(new BasicNameValuePair("sph-amount", "990"));
    nameValuePairs.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com"));
    nameValuePairs.add(new BasicNameValuePair("sph-currency", "EUR"));
    nameValuePairs.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
    nameValuePairs.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
    nameValuePairs.add(new BasicNameValuePair("sph-order", "1000123A"));
    nameValuePairs.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
    nameValuePairs.add(new BasicNameValuePair("sph-success-url", "https://www.solinor.com"));
    nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
    nameValuePairs.add(new BasicNameValuePair("language", "EN"));
    nameValuePairs.add(new BasicNameValuePair("description", "this is a description field"));

    FormAPIConnection conn = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = conn.addCardAndPayRequest(nameValuePairs);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertTrue(response.contains("XXXX XXXX XXXX XXXX"));
    assertTrue(response.contains("viewport"));
    assertTrue(response.contains("Solinor Payment Highway"));
    assertTrue(response.contains("Payment Information"));
    assertTrue(response.contains("card-info"));
  }

  @Test
  public void testAddHeaders1() {
    HttpPost httpPost = new HttpPost("http://www.anygivenurl.com");
    FormAPIConnection conn = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
    conn.addHeaders(httpPost);
    Header[] headers = httpPost.getHeaders("User-Agent");

    assertTrue((headers[0].getName().equalsIgnoreCase("User-Agent")));
    assertTrue((headers[0].getValue().equalsIgnoreCase("PaymentHighway Java Lib")));

  }

  @Test
  public void testAddHeaders2() {
    HttpPost httpPost = new HttpPost("http://www.anygivenurl.com");
    FormAPIConnection conn = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
    conn.addHeaders(httpPost);
    Header[] headers = httpPost.getHeaders("Content-Type");

    assertTrue((headers[0].getName().equalsIgnoreCase("Content-Type")));
    assertTrue((headers[0].getValue().equalsIgnoreCase("application/x-www-form-urlencoded")));

  }

  @Test
  public void testGetFormSubmitResponse() {

    UUID requestId = UUID.randomUUID();
    List<NameValuePair> formParameters = new ArrayList<>();
    formParameters.add(new BasicNameValuePair("sph-amount", "990"));
    formParameters.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
    formParameters.add(new BasicNameValuePair("sph-cancel-url", "https://www.paymenthighway.fi/?cancel"));
    formParameters.add(new BasicNameValuePair("sph-currency", "EUR"));
    formParameters.add(new BasicNameValuePair("sph-account", "test"));
    formParameters.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/?failed"));
    formParameters.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
    formParameters.add(new BasicNameValuePair("sph-order", "1000123A"));
    formParameters.add(new BasicNameValuePair("sph-request-id", requestId.toString()));
    formParameters.add(new BasicNameValuePair("sph-success-url", "https://www.paymenthighway.fi/?success"));
    formParameters.add(new BasicNameValuePair("language", "EN"));

    FormAPIConnection conn = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
    String response = null;
    try {
      response = conn.addCardAndPayRequest(formParameters);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // this is a bit of a hack, faking a browser, parsing url
    assertNotNull(response);
    Matcher matcher = Pattern.compile("(?<=form action=\").{50}").matcher(response);
    assertTrue(matcher.find());
    String formUri = matcher.group();

    postCardFormAndReturnLastQueryString(this.serviceUrl, formUri, "4153013999700024", "11", "2017", "024");
  }
}
