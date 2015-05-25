package io.paymenthighway.connect;

import io.paymenthighway.PaymentHighwayUtility;
import io.paymenthighway.model.request.Card;
import io.paymenthighway.model.request.CommitTransactionRequest;
import io.paymenthighway.model.request.RevertTransactionRequest;
import io.paymenthighway.model.request.TransactionRequest;
import io.paymenthighway.model.response.*;
import io.paymenthighway.security.SecureSigner;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.junit.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

/**
 * PaymentAPIConnection test class
 */
public class PaymentAPIConnectionTest {

  private static String serviceUrl = null;
  private static String signatureKeyId = null;
  private static String signatureSecret = null;
  private static String account = null;
  private static String merchant = null;

  private static PaymentAPIConnection conn;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    Properties p = PaymentHighwayUtility.getProperties();
    serviceUrl = p.getProperty("service_url");
    signatureKeyId = p.getProperty("signature_key_id");
    signatureSecret = p.getProperty("signature_secret");
    account = p.getProperty("sph-account");
    merchant = p.getProperty("sph-merchant");

    conn = new PaymentAPIConnection(serviceUrl, signatureKeyId, signatureSecret, account, merchant);
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    if (conn != null) {
      conn.close();
    }
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

    List<NameValuePair> sphHeaders = new ArrayList<>();

    sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
    sphHeaders.add(new BasicNameValuePair("sph-amount", "9990"));
    sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
    sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));

    String uri = "/transaction";
    SecureSigner ss = new SecureSigner(signatureKeyId, signatureSecret);
    String sig = ss.createSignature("POST", uri, sphHeaders, "");
    assertTrue(sig.contains("SPH1"));
  }

  @Test
  public void testInitTransaction() {
    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    // test for response code 100
    assertEquals(response.getResult().getCode(), "100");
    // test for message OK
    assertEquals(response.getResult().getMessage(), "OK");
    // test for id field
    assertTrue(response.getId() != null);
  }

  @Test
  public void testFailedAuthenticationInit() {
    try {
      conn.initTransactionHandle();
    } catch (IOException e) {
      assertTrue(e.getMessage().contains("Authentication HMAC mismatch"));
    }
  }

  @Test
  public void testAddHeaders() {

    List<NameValuePair> sphHeaders = new ArrayList<>();

    sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
    sphHeaders.add(new BasicNameValuePair("sph-amount", "9990"));
    sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
    sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));

    String uri = "/transaction";
    SecureSigner ss = new SecureSigner(signatureKeyId, signatureSecret);
    String sig = ss.createSignature("POST", uri, sphHeaders, "");
    sphHeaders.add(new BasicNameValuePair("signature", sig));

    HttpPost httpPost = new HttpPost(serviceUrl + uri);

    conn.addHeaders(httpPost, sphHeaders);

    assertTrue(httpPost.getAllHeaders().length == 7);

  }

  @Test
  public void testInitTransactionResponse() {

    InitTransactionResponse response = null;

    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());
  }

  /**
   * This will test successful debit transaction
   */
  @Test
  public void testDebitTransaction1() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());

    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2017";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", true);

    TransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId,
          transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getMessage(), "OK");
    assertEquals(transactionResponse.getResult().getCode(), "100");

  }

  /**
   * No limit available in the test bank account
   */
  @Test
  public void testDebitTransaction2() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());

    UUID transactionId = response.getId();

    String pan = "4153013999700156";
    String cvc = "156";
    String expiryYear = "2017";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "99900", "EUR", true);

    TransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId,
          transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getMessage(), "Authorization failed");
    assertEquals(transactionResponse.getResult().getCode(), "200");
  }

  /**
   * Reported as stolen
   */
  @Test
  public void testDebitTransaction3() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());

    UUID transactionId = response.getId();

    String pan = "4153013999700289";
    String cvc = "289";
    String expiryYear = "2017";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "99900", "EUR", true);

    TransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getMessage(), "Authorization failed");
    assertEquals(transactionResponse.getResult().getCode(), "200");
  }

  /**
   * This will test successful credit transaction NOTE: NOT YET IMPLEMENTED
   */
  @Ignore
  @Test
  public void testCreditTransaction1() {
    fail("Not yet implemented"); // TODO
  }

  /**
   * This will test successful revert transaction for full amount
   * <p/>
   * 1. create transaction handle 2. create a debit transaction 3. revert that
   * transaction
   */
  @Test
  public void testRevertTransaction1() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());

    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2017";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", true);

    TransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getMessage(), "OK");
    assertEquals(transactionResponse.getResult().getCode(), "100");

    // revert transaction test
    TransactionResponse revertResponse = null;

    RevertTransactionRequest revertTransaction = new RevertTransactionRequest("9999", true);

    try {
      revertResponse = conn.revertTransaction(transactionId,
          revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(revertResponse);
    assertEquals(revertResponse.getResult().getMessage(), "OK");
    assertEquals(revertResponse.getResult().getCode(), "100");
  }

  /**
   * This will test successful revert transaction for partial amount
   * <p/>
   * 1. create transaction handle 2. create a debit transaction 3. revert that
   * transaction
   */
  @Test
  public void testRevertTransaction2() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());

    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2017";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR");

    TransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId,
          transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getMessage(), "OK");
    assertEquals(transactionResponse.getResult().getCode(), "100");

    // revert transaction test
    TransactionResponse revertResponse = null;

    RevertTransactionRequest revertTransaction = new RevertTransactionRequest("1000", true);

    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertEquals(revertResponse.getResult().getMessage(), "OK");
    assertEquals(revertResponse.getResult().getCode(), "100");

  }

  /**
   * This will test failed revert transaction because of insufficient balance
   * <p/>
   * 1. create transaction handle 2. create a debit transaction 3. revert that
   * transaction
   */
  @Test
  public void testRevertTransaction3() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());

    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2017";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "1000", "EUR", true);

    TransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getMessage(), "OK");
    assertEquals(transactionResponse.getResult().getCode(), "100");

    // revert transaction test
    TransactionResponse revertResponse = null;

    RevertTransactionRequest revertTransaction = new RevertTransactionRequest("1001");

    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertTrue(revertResponse.getResult().getMessage().contains("Revert failed: Insufficient balance"));
    assertEquals(revertResponse.getResult().getCode(), "211");

  }

  /**
   * This will test successfull revert transaction for the full amount without
   * specifying amount
   * <p/>
   * 1. create transaction handle 2. create a debit transaction 3. revert that
   * transaction
   */
  @Test
  public void testRevertTransaction4() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());

    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2017";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "1000", "EUR", true);

    TransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId,
          transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getMessage(), "OK");
    assertEquals(transactionResponse.getResult().getCode(), "100");

    // revert transaction test
    TransactionResponse revertResponse = null;

    RevertTransactionRequest revertTransaction = new RevertTransactionRequest();

    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertEquals(revertResponse.getResult().getMessage(), "OK");
    assertEquals(revertResponse.getResult().getCode(), "100");

  }

  /**
   * This will test successfull revert transaction for the full amount in two
   * different reverts
   * <p/>
   * 1. create transaction handle 2. create a debit transaction 3. revert half
   * transaction 4. revert rest of transaction
   */
  @Test
  public void testRevertTransaction5() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());

    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2017";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "1000", "EUR");

    TransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getMessage(), "OK");
    assertEquals(transactionResponse.getResult().getCode(), "100");

    // revert transaction test
    TransactionResponse revertResponse = null;

    RevertTransactionRequest revertTransaction = new RevertTransactionRequest("500");

    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertEquals(revertResponse.getResult().getMessage(), "OK");
    assertEquals(revertResponse.getResult().getCode(), "100");

    revertTransaction = new RevertTransactionRequest(); // no amount set, should revert rest of the transaction
    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(revertResponse.getResult().getMessage(), "OK");
    assertEquals(revertResponse.getResult().getCode(), "100");
  }

  /**
   * This will test partial revert transactions where second revert will fail
   * due insufficient funds
   * <p/>
   * 1. create transaction handle 2. create a debit transaction 3. revert half
   * transaction 4. revert more than balance
   */
  @Test
  public void testRevertTransaction6() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());

    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2017";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "1000", "EUR", true);

    TransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getMessage(), "OK");
    assertEquals(transactionResponse.getResult().getCode(), "100");

    // revert transaction test
    TransactionResponse revertResponse = null;

    RevertTransactionRequest revertTransaction = new RevertTransactionRequest("500");

    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertEquals(revertResponse.getResult().getMessage(), "OK");
    assertEquals(revertResponse.getResult().getCode(), "100");

    revertTransaction = new RevertTransactionRequest("501");// over balance
    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertTrue(revertResponse.getResult().getMessage().contains("Revert failed: Insufficient balance"));
    assertEquals(revertResponse.getResult().getCode(), "211");
  }

  /**
   * This will test successful transaction status request
   * <p/>
   * 1. create transaction handle 2. create a debit transaction 3. request a
   * transaction status
   */
  @Test
  public void testTransactionStatus1() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());

    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2017";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", true);

    TransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getMessage(), "OK");
    assertEquals(transactionResponse.getResult().getCode(), "100");

    // status response test
    TransactionStatusResponse statusResponse = null;

    try {
      statusResponse = conn.transactionStatus(transactionId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // test result
    assertNotNull(statusResponse);
    assertEquals(statusResponse.getResult().getMessage(), "OK");
    assertEquals(statusResponse.getResult().getCode(), "100");
    assertEquals(statusResponse.getTransaction().getCurrentAmount(), "9999");
    assertEquals(statusResponse.getTransaction().getId(), transactionId);
  }

  /**
   * This will test successful com transaction
   */
  @Test
  public void testCommitTransaction1() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());

    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2017";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", true);

    TransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getMessage(), "OK");
    assertEquals(transactionResponse.getResult().getCode(), "100");

    CommitTransactionRequest commitTransaction = new CommitTransactionRequest("9999", "EUR", true);

    CommitTransactionResponse commitTransactionResponse = null;
    try {
      commitTransactionResponse = conn.commitTransaction(transactionId, commitTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(commitTransactionResponse);
    assertTrue(commitTransactionResponse.getCard().getType().equalsIgnoreCase("visa"));
  }

  /**
   * This will test successful tokenization request
   */
  @Test
  public void testTokenization1() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());
    // TODO: fetch this from Form API get parameters
    String tokenizationId = "08cc223a-cf93-437c-97a2-f338eaf0d860";

    TokenizationResponse tokenResponse = null;
    try {
      tokenResponse = conn.tokenization(tokenizationId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(tokenResponse);
    assertEquals(tokenResponse.getCard().getExpireYear(), "2017");
    assertEquals(tokenResponse.getCardToken().toString(), "71435029-fbb6-4506-aa86-8529efb640b0");
  }

  /**
   * This will test successful batch report request
   */
  @Test
  public void testReport1() {

    // request batch for yesterday, today is not available
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    String date = dateFormat.format(cal.getTime());

    ReportResponse result = null;
    try {
      result = conn.fetchReport(date);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(result);
    assertEquals(result.getResult().getCode(), "100");
    assertEquals(result.getResult().getMessage(), "OK");
  }
}
