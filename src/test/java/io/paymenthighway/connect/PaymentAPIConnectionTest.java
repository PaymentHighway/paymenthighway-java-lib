package io.paymenthighway.connect;

import io.paymenthighway.Constants;
import io.paymenthighway.PaymentHighwayUtility;
import io.paymenthighway.model.Token;
import io.paymenthighway.model.request.*;
import io.paymenthighway.model.request.Customer;
import io.paymenthighway.model.response.*;
import io.paymenthighway.model.response.transaction.ChargeMitResponse;
import io.paymenthighway.model.response.transaction.DebitTransactionResponse;
import io.paymenthighway.security.SecureSigner;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.junit.*;

import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

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

  private static final String RESULT_CODE_AUTHORIZATION_FAILED = "200";
  private static final String RESULT_CODE_UNMATCHED_REQUEST_PARAMETERS = "920";

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    Properties p = PaymentHighwayUtility.getProperties();
    serviceUrl = p.getProperty("service_url");
    signatureKeyId = p.getProperty("signature_key_id");
    signatureSecret = p.getProperty("signature_secret");
    account = p.getProperty("sph-account");
    merchant = p.getProperty("sph-merchant");

    conn = new PaymentAPIConnection(serviceUrl, signatureKeyId, signatureSecret, account, merchant, PaymentAPIConnection.defaultHttpClient());
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    if (conn != null) {
      conn.close();
    }
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  private void checkAcquirerInfo(AcquirerInfoResponse response, String acquirerResponseCode) {
    assertEquals("nets", response.getAcquirer().getId());
    assertEquals("Nets", response.getAcquirer().getName());
    assertEquals(acquirerResponseCode, response.getAcquirerResponseCode());
    assertEquals("666", response.getAuthorizer());
  }

  private void checkAcquirerInfo(TransactionStatus transactionStatus, String acquirerResponseCode) {
    assertEquals("nets", transactionStatus.getAcquirer().getId());
    assertEquals("Nets", transactionStatus.getAcquirer().getName());
    assertEquals(acquirerResponseCode, transactionStatus.getAcquirerResponseCode());
    assertEquals("666", transactionStatus.getAuthorizer());
  }

  private UUID createAndTestTransactionInit() {

    InitTransactionResponse response = null;
    try {
      response = conn.initTransactionHandle();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals("Transaction init should succeed", "100", response.getResult().getCode());
    assertEquals("Transaction init should succeed", "OK", response.getResult().getMessage());

    assertNotNull("Transaction init should return id", response.getId());

    return response.getId();
  }

  private TokenizationResponse createAndTestTokenizationId(String cardNumber, String expirationMonth, String expirationYear, String cvc) {
    String formUri = FormAPIConnectionTest.createAndTestAddCardForm(serviceUrl, signatureKeyId, signatureSecret, account, merchant);

    String query = FormAPIConnectionTest.postCardFormAndReturnLastQueryString(serviceUrl, formUri, cardNumber, expirationMonth, expirationYear, cvc);

    Matcher matcher = Pattern.compile("(?<=sph-tokenization-id=).{36}").matcher(query);
    assertTrue(matcher.find());
    UUID tokenizationId = UUID.fromString(matcher.group());

    TokenizationResponse tokenResponse = null;
    try {
      tokenResponse = conn.tokenization(tokenizationId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(tokenResponse);
    assertEquals(
        expirationYear,
        tokenResponse.getCard().getExpireYear()
    );
    assertEquals(
        cardNumber.substring(0, 6),
        tokenResponse.getCard().getBin()
    );
    assertEquals(
        "attempted",
        tokenResponse.getCardholderAuthentication()
    );
    assertNotNull(tokenResponse.getCustomer());
    checkAcquirerInfo(tokenResponse, "060");

    return tokenResponse;
  }

  private Card testCardOkWithCvc = new Card("4153013999700024", "2023", "11", "024");
  private Card testCardTokenizeOkPaymentFails = new Card("4153013999700156", "2023", "11", "156");

  private void isSuccessfulCommitOrResultResponse(AbstractTransactionOutcomeResponse response) {
    assertNotNull(response);
    // Does not return card token when not paying with tokenized card
    //assertNotNull(commitTransactionResponse.getCardToken());
    assertTrue(response.getCard().getType().equalsIgnoreCase("visa"));
    assertEquals("not_tested", response.getCard().getCvcRequired());
    assertEquals(testCardOkWithCvc.getPan().substring(0,6), response.getCard().getBin());
    assertEquals("debit", response.getCard().getFunding());
    assertEquals("unknown", response.getCard().getCategory());
    assertEquals("FI", response.getCard().getCountryCode());
    assertEquals("83.145.208.186", response.getCustomer().getNetworkAddress());
    assertEquals("FI", response.getCustomer().getCountryCode());
    assertEquals("no", response.getCardholderAuthentication());

    assertFingerprint(response.getCard().getPanFingerprint());
    assertFingerprint(response.getCard().getCardFingerprint());

    assertNotNull(response.getFilingCode());
    assertEquals(12, response.getFilingCode().length());
  }

  private UUID initAndDebitTransaction(Card card, Long amount, Boolean commit) {

    UUID transactionId = createAndTestTransactionInit();

    Customer customer = new Customer("83.145.208.186");

    TransactionRequest transaction = TransactionRequest.Builder(card, amount, "EUR")
        .setCustomer(customer)
        .setCommit(commit)
        .build();

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());

    return transactionId;
  }

  private void assertCommitted(AbstractTransactionOutcomeResponse response, Boolean isCommitted, String committedAmount) {
    assertNotNull(response.getCommitted());
    assertEquals(isCommitted, response.getCommitted());
    assertEquals(committedAmount, response.getCommittedAmount());
    checkAcquirerInfo(response, "000");
  }

  private void assertRecurring(AbstractTransactionOutcomeResponse response, Boolean isRecurring) {
    assertNotNull(response.getRecurring());
    assertEquals(isRecurring, response.getRecurring());
  }

  private void assertFingerprint(String fingerprint) {
    assertNotNull("Card fingerprint should not be null", fingerprint);
    assertTrue(
      "Card fingerprint didn't contain 64 hex chars",
      Pattern.compile("^[0-9a-f]{64}$").matcher(fingerprint).matches()
    );
  }

  @Test
  public void testCreateSignature() {

    List<NameValuePair> sphHeaders = new ArrayList<>();

    sphHeaders.add(new BasicNameValuePair("sph-api-version", Constants.API_VERSION));
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
    assertEquals("100", response.getResult().getCode());
    // test for message OK
    assertEquals("OK", response.getResult().getMessage());
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

    UUID transactionId = createAndTestTransactionInit();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR");

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(
        "OK",
        transactionResponse.getResult().getMessage()
    );
    assertEquals(
        "100",
        transactionResponse.getResult().getCode()
    );
    assertNotNull(transactionResponse.getFilingCode());
    assertEquals(12, transactionResponse.getFilingCode().length());
    checkAcquirerInfo(transactionResponse, "000");
  }

  private void performAndTestRejectedDebitRequest(UUID transactionId, Long amount) {

    TransactionRequest transaction = new TransactionRequest.Builder(testCardTokenizeOkPaymentFails, amount, "EUR").build();

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId,
          transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("Authorization failed", transactionResponse.getResult().getMessage());
    assertEquals("200", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "116");
  }

  private CommitTransactionResponse commitTransaction(UUID transactionId, String amount, String currency) {
    CommitTransactionRequest commitTransaction = new CommitTransactionRequest(amount, currency);

    CommitTransactionResponse commitTransactionResponse = null;
    try {
      commitTransactionResponse = conn.commitTransaction(transactionId, commitTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return commitTransactionResponse;
  }

  private TransactionResultResponse transactionResult(UUID transactionId) {

    TransactionResultResponse transactionResultResponse = null;
    try {
      transactionResultResponse = conn.transactionResult(transactionId);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return transactionResultResponse;
  }

  /**
   * No limit available in the test bank account
   */
  @Test
  public void testDebitTransaction2() {

    UUID transactionId = createAndTestTransactionInit();

    performAndTestRejectedDebitRequest(transactionId, 9999L);
  }

  /**
   * Reported as stolen
   */
  @Test
  public void testDebitTransaction3() {

    UUID transactionId = createAndTestTransactionInit();

    String pan = "4153013999700289";
    String cvc = "289";
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = TransactionRequest.Builder(card, 99900L, "EUR").build();

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(transactionResponse);
    assertEquals("Authorization failed", transactionResponse.getResult().getMessage());
    assertEquals("200", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "209");
  }

  /**
   * This will test successful debit transaction with the optional customer IP address
   */
  @Test
  public void testDebitTransaction4() {

    UUID transactionId = createAndTestTransactionInit();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);
    Customer customer = new Customer(InetAddress.getLoopbackAddress().getHostAddress());

    TransactionRequest transaction = TransactionRequest.Builder(card, 9999L, "EUR").setCustomer(customer).build();

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId,
              transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "000");

    // status response test
    TransactionStatusResponse statusResponse = null;

    try {
      statusResponse = conn.transactionStatus(transactionId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // test result
    assertNotNull(statusResponse);
    assertEquals("OK", statusResponse.getResult().getMessage());
    assertEquals("100", statusResponse.getResult().getCode());
    assertNotNull(statusResponse.getTransaction());
    assertNotNull(statusResponse.getTransaction().getStatus());
    assertEquals(
        "4000",
        statusResponse.getTransaction().getStatus().getCode()
    );
    assertEquals(
        "ok",
        statusResponse.getTransaction().getStatus().getState()
    );
    assertEquals(
        "9999",
        statusResponse.getTransaction().getCurrentAmount()
    );
    assertEquals(
        transactionId,
        statusResponse.getTransaction().getId()
    );
    assertEquals(
        "not_tested",
        statusResponse.getTransaction().getCard().getCvcRequired()
    );
   checkAcquirerInfo(statusResponse.getTransaction(), "000");
  }

  /**
   * This will test successful tokenization payment without auto commit
   */
  @Test
  public void testDebitTransaction5() {

    TokenizationResponse tokenResponse = createAndTestTokenizationId("4153013999700024", "11", "2023", "024");

    assertEquals("not_tested", tokenResponse.getCard().getCvcRequired());
    assertEquals("debit", tokenResponse.getCard().getFunding());
    assertEquals("unknown", tokenResponse.getCard().getCategory());

    UUID transactionId = createAndTestTransactionInit();

    TransactionRequest transaction = new TransactionRequest.Builder(new Token(tokenResponse.getCardToken()), 9999L, "EUR")
        .setOrder("PIPPURI")
        .setCommit(false)
        .build();

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "000");

    // status response test
    TransactionStatusResponse statusResponse = null;

    try {
      statusResponse = conn.transactionStatus(transactionId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // test result
    assertNotNull(statusResponse);
    assertEquals("OK", statusResponse.getResult().getMessage());
    assertEquals("100", statusResponse.getResult().getCode());
    assertNotNull(statusResponse.getTransaction());
    assertNotNull(statusResponse.getTransaction().getStatus());
    assertEquals(
        "4100",
        statusResponse.getTransaction().getStatus().getCode()
    );
    assertEquals(
        "ok_pending",
        statusResponse.getTransaction().getStatus().getState()
    );
    assertEquals("9999", statusResponse.getTransaction().getCurrentAmount());
    assertEquals(transactionId, statusResponse.getTransaction().getId());
    assertEquals("not_tested", statusResponse.getTransaction().getCard().getCvcRequired());
    checkAcquirerInfo(statusResponse.getTransaction(), "000");
  }

  /**
   * This will test successful tokenization payment without auto commit and using token that requires cvc
   */
  @Ignore("Requiring CVC is no longer an option in the post PSD2 world") @Test
  public void testDebitTransaction6() {

    TokenizationResponse tokenResponse = createAndTestTokenizationId("4324643990016048", "11", "2023", "048");

    assertEquals("yes", tokenResponse.getCard().getCvcRequired());
    assertEquals("debit", tokenResponse.getCard().getFunding());
    assertEquals("prepaid", tokenResponse.getCard().getCategory());

    UUID transactionId = createAndTestTransactionInit();

    TransactionRequest transaction = TransactionRequest.Builder(new Token(tokenResponse.getCardToken(), "048"), 9999L, "EUR")
        .setOrder("PIPPURI")
        .setCommit(false)
        .build();

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());

    // status response test
    TransactionStatusResponse statusResponse = null;

    try {
      statusResponse = conn.transactionStatus(transactionId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // test result
    assertNotNull(statusResponse);
    assertEquals("OK", statusResponse.getResult().getMessage());
    assertEquals("100", statusResponse.getResult().getCode());
    assertNotNull(statusResponse.getTransaction());
    assertNotNull(statusResponse.getTransaction().getStatus());
    assertEquals(
        "4100",
        statusResponse.getTransaction().getStatus().getCode()
    );
    assertEquals(
        "ok_pending",
        statusResponse.getTransaction().getStatus().getState()
    );
    assertEquals("9999", statusResponse.getTransaction().getCurrentAmount());
    assertEquals(transactionId, statusResponse.getTransaction().getId());
    assertEquals("yes", statusResponse.getTransaction().getCard().getCvcRequired());
  }

  @Test
  public void testMitTransaction() {

    UUID transactionId = createAndTestTransactionInit();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    ChargeMitRequest transaction = new ChargeMitRequest.Builder(card, 9999L, "EUR", "order").build();


    ChargeMitResponse transactionResponse = null;
    try {
      transactionResponse = conn.chargeMerchantInitiatedTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(
        "OK",
        transactionResponse.getResult().getMessage()
    );
    assertEquals(
        "100",
        transactionResponse.getResult().getCode()
    );
    assertNotNull(transactionResponse.getAcquirer());
    checkAcquirerInfo(transactionResponse, "000");
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
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR");

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "000");

    // revert transaction test
    RevertResponse revertResponse = null;

    RevertTransactionRequest revertTransaction = new RevertTransactionRequest("9999", true);

    try {
      revertResponse = conn.revertTransaction(transactionId,
          revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(revertResponse);
    assertEquals("OK", revertResponse.getResult().getMessage());
    assertEquals("100", revertResponse.getResult().getCode());
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
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR");

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId,
          transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "000");

    // revert transaction test
    RevertResponse revertResponse = null;

    RevertTransactionRequest revertTransaction = new RevertTransactionRequest("1000", true);

    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertEquals("OK", revertResponse.getResult().getMessage());
    assertEquals("100", revertResponse.getResult().getCode());

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
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "1000", "EUR");

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "000");

    // revert transaction test
    RevertResponse revertResponse = null;

    RevertTransactionRequest revertTransaction = new RevertTransactionRequest("1001");

    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertTrue(revertResponse.getResult().getMessage().contains("Revert failed: Insufficient balance"));
    assertEquals("211", revertResponse.getResult().getCode());

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
    String expiryYear = "2026";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "1000", "EUR");

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId,
          transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "000");

    // revert transaction test
    RevertResponse revertResponse = null;

    RevertTransactionRequest revertTransaction = new RevertTransactionRequest();

    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertEquals("OK", revertResponse.getResult().getMessage());
    assertEquals("100", revertResponse.getResult().getCode());

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
    String expiryYear = "2026";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "1000", "EUR");

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "000");

    // revert transaction test
    RevertResponse revertResponse = null;

    RevertTransactionRequest revertTransaction = new RevertTransactionRequest("500");

    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertEquals("OK", revertResponse.getResult().getMessage());
    assertEquals("100", revertResponse.getResult().getCode());

    revertTransaction = new RevertTransactionRequest(); // no amount set, should revert rest of the transaction
    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("OK", revertResponse.getResult().getMessage());
    assertEquals("100", revertResponse.getResult().getCode());
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
    String expiryYear = "2026";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "1000", "EUR");

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "000");

    // revert transaction test
    RevertResponse revertResponse = null;

    RevertTransactionRequest revertTransaction = new RevertTransactionRequest("500");

    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertEquals("OK", revertResponse.getResult().getMessage());
    assertEquals("100", revertResponse.getResult().getCode());

    revertTransaction = new RevertTransactionRequest("501");// over balance
    try {
      revertResponse = conn.revertTransaction(transactionId, revertTransaction);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertTrue(revertResponse.getResult().getMessage().contains("Revert failed: Insufficient balance"));
    assertEquals("211", revertResponse.getResult().getCode());
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
    String expiryYear = "2026";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);
    Customer customer = new Customer("83.145.208.186");

    TransactionRequest transaction = TransactionRequest.Builder(card, 9999L, "EUR").setCustomer(customer).build();

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "000");

    // status response test
    TransactionStatusResponse statusResponse = null;

    try {
      statusResponse = conn.transactionStatus(transactionId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // test result
    assertNotNull(statusResponse);
    assertEquals("OK", statusResponse.getResult().getMessage());
    assertEquals("100", statusResponse.getResult().getCode());
    assertEquals("9999", statusResponse.getTransaction().getCurrentAmount());
    assertEquals(transactionId, statusResponse.getTransaction().getId());
    assertEquals("not_tested", statusResponse.getTransaction().getCard().getCvcRequired());
    assertEquals("415301", statusResponse.getTransaction().getCard().getBin());
    assertEquals("debit", statusResponse.getTransaction().getCard().getFunding());
    assertEquals("unknown", statusResponse.getTransaction().getCard().getCategory());
    assertEquals("FI", statusResponse.getTransaction().getCard().getCountryCode());
    assertEquals("83.145.208.186", statusResponse.getTransaction().getCustomer().getNetworkAddress());
    assertEquals("FI", statusResponse.getTransaction().getCustomer().getCountryCode());
    assertEquals("no", statusResponse.getTransaction().getCardholderAuthentication());
    assertEquals(true, statusResponse.getTransaction().getCommitted());
    assertEquals("9999", statusResponse.getTransaction().getCommittedAmount());

    assertFingerprint(statusResponse.getTransaction().getCard().getPanFingerprint());
    assertFingerprint(statusResponse.getTransaction().getCard().getCardFingerprint());
    checkAcquirerInfo(statusResponse.getTransaction(), "000");
  }

  /**
   * This will test successful transaction status request with the option "order" parameter and no auto-commit
   * <p/>
   * 1. create transaction handle 2. create a debit transaction 3. request a
   * transaction status
   */
  @Test
  public void testTransactionStatus2() {

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
    String expiryYear = "2026";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);
    Customer customer = new Customer("83.145.208.186");
    String orderId = "ABC123";

    TransactionRequest transaction = TransactionRequest.Builder(card, 9999L, "EUR").setOrder(orderId).setCommit(false).setCustomer(customer).build();

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "000");

    // status response test
    TransactionStatusResponse statusResponse = null;

    try {
      statusResponse = conn.transactionStatus(transactionId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // test result
    assertNotNull(statusResponse);
    assertEquals("OK", statusResponse.getResult().getMessage());
    assertEquals("100", statusResponse.getResult().getCode());
    assertEquals("9999", statusResponse.getTransaction().getCurrentAmount());
    assertEquals(transactionId, statusResponse.getTransaction().getId());
    assertEquals("not_tested", statusResponse.getTransaction().getCard().getCvcRequired());
    assertEquals("415301", statusResponse.getTransaction().getCard().getBin());
    assertEquals("debit", statusResponse.getTransaction().getCard().getFunding());
    assertEquals("unknown", statusResponse.getTransaction().getCard().getCategory());
    assertEquals("FI", statusResponse.getTransaction().getCard().getCountryCode());
    assertEquals("83.145.208.186", statusResponse.getTransaction().getCustomer().getNetworkAddress());
    assertEquals("FI", statusResponse.getTransaction().getCustomer().getCountryCode());
    assertEquals("no", statusResponse.getTransaction().getCardholderAuthentication());
    assertEquals(orderId, statusResponse.getTransaction().getOrder());
    assertFalse(statusResponse.getTransaction().getCommitted());
    assertNull(statusResponse.getTransaction().getCommittedAmount());
    checkAcquirerInfo(statusResponse.getTransaction(), "000");
  }

  /**
   * This will test a successful order search request
   * <p/>
   * 1. create transaction handle 2. create a debit transaction with order id 3. search by the order id
   */
  @Test
  public void testOrderSearch1() {

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
    String expiryYear = "2026";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);
    UUID orderId = UUID.randomUUID();
    Customer customer = new Customer("83.145.208.186");

    TransactionRequest transaction = TransactionRequest.Builder(card, 9999L, "EUR").setOrder(orderId.toString()).setCustomer(customer).build();

    DebitTransactionResponse transactionResponse = null;
    try {
      transactionResponse = conn.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals("OK", transactionResponse.getResult().getMessage());
    assertEquals("100", transactionResponse.getResult().getCode());
    checkAcquirerInfo(transactionResponse, "000");

    // order search test
    OrderSearchResponse orderSearchResponse = null;

    try {
      orderSearchResponse = conn.searchOrders(orderId.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }

    // test result
    assertNotNull(orderSearchResponse);
    assertEquals("OK", orderSearchResponse.getResult().getMessage());
    assertEquals("100", orderSearchResponse.getResult().getCode());
    assertEquals("9999", orderSearchResponse.getTransactions()[0].getCurrentAmount());
    assertEquals(transactionId, orderSearchResponse.getTransactions()[0].getId());
    assertEquals("415301", orderSearchResponse.getTransactions()[0].getCard().getBin());
    assertEquals("debit", orderSearchResponse.getTransactions()[0].getCard().getFunding());
    assertEquals("unknown", orderSearchResponse.getTransactions()[0].getCard().getCategory());
    assertEquals("FI", orderSearchResponse.getTransactions()[0].getCard().getCountryCode());
    assertEquals("83.145.208.186", orderSearchResponse.getTransactions()[0].getCustomer().getNetworkAddress());
    assertEquals("FI", orderSearchResponse.getTransactions()[0].getCustomer().getCountryCode());
    assertEquals("no", orderSearchResponse.getTransactions()[0].getCardholderAuthentication());
    assertEquals(true, orderSearchResponse.getTransactions()[0].getCommitted());
    assertEquals(false, orderSearchResponse.getTransactions()[0].getRecurring());
    assertEquals("9999", orderSearchResponse.getTransactions()[0].getCommittedAmount());
    checkAcquirerInfo(orderSearchResponse.getTransactions()[0], "000");
  }

  /**
   * This will test successful com transaction
   */
  @Test
  public void testCommitTransaction1() {

    UUID transactionId = initAndDebitTransaction(testCardOkWithCvc, 9999L, false);

    CommitTransactionResponse commitTransactionResponse = commitTransaction(transactionId, "9999", "EUR");
    isSuccessfulCommitOrResultResponse(commitTransactionResponse);
    assertCommitted(commitTransactionResponse, true, "9999");
    assertRecurring(commitTransactionResponse, false);
  }

  @Test
  public void testCommitTransactionWithLessThanTotalAmount() {

    UUID transactionId = initAndDebitTransaction(testCardOkWithCvc, 9999L, false);

    CommitTransactionResponse commitTransactionResponse = commitTransaction(transactionId, "5000", "EUR");
    isSuccessfulCommitOrResultResponse(commitTransactionResponse);
    assertCommitted(commitTransactionResponse, true, "5000");
    assertRecurring(commitTransactionResponse, false);
  }

  @Test
  public void testCommitTransactionRejectedWithAmountGreaterThanPayment() {

    UUID transactionId = initAndDebitTransaction(testCardOkWithCvc, 9999L, false);

    CommitTransactionResponse commitTransactionResponse = commitTransaction(transactionId, "10000", "EUR");
    assertEquals(RESULT_CODE_UNMATCHED_REQUEST_PARAMETERS, commitTransactionResponse.getResult().getCode());
  }

  @Test
  public void testCommitTransactionTwiceWithSameValuesSucceeds() {

    UUID transactionId = initAndDebitTransaction(testCardOkWithCvc, 9999L, false);

    CommitTransactionResponse commitTransactionResponse = commitTransaction(transactionId, "9999", "EUR");
    isSuccessfulCommitOrResultResponse(commitTransactionResponse);
    assertCommitted(commitTransactionResponse, true, "9999");

    CommitTransactionResponse commitTransactionResponse2 = commitTransaction(transactionId, "9999", "EUR");
    isSuccessfulCommitOrResultResponse(commitTransactionResponse2);
    assertCommitted(commitTransactionResponse2, true, "9999");
  }

  @Test
  public void testCommitTransactionTwiceWithDifferentValueDoesNotChangeTheInitialCommittedValue() {

    UUID transactionId = initAndDebitTransaction(testCardOkWithCvc, 9999L, false);

    CommitTransactionResponse commitTransactionResponse = commitTransaction(transactionId, "9999", "EUR");
    isSuccessfulCommitOrResultResponse(commitTransactionResponse);
    assertCommitted(commitTransactionResponse, true, "9999");

    CommitTransactionResponse commitTransactionResponse2 = commitTransaction(transactionId, "5000", "EUR");
    isSuccessfulCommitOrResultResponse(commitTransactionResponse2);
    assertCommitted(commitTransactionResponse2, true, "9999");
  }

  @Test
  public void testTransactionResultReturnsCommittedAndUncommittedValues() {

    UUID transactionId = initAndDebitTransaction(testCardOkWithCvc, 9999L, false);

    TransactionResultResponse transactionResultResponse1 = transactionResult(transactionId);
    isSuccessfulCommitOrResultResponse(transactionResultResponse1);
    assertCommitted(transactionResultResponse1, false, null);

    CommitTransactionResponse commitTransactionResponse = commitTransaction(transactionId, "9999", "EUR");
    isSuccessfulCommitOrResultResponse(commitTransactionResponse);
    assertCommitted(commitTransactionResponse, true, "9999");

    TransactionResultResponse transactionResultResponse2 = transactionResult(transactionId);
    isSuccessfulCommitOrResultResponse(transactionResultResponse2);
    assertCommitted(transactionResultResponse2, true, "9999");
  }

  @Test
  public void testTransactionResultReturnsFailureIfRejectedPayment() {
    UUID transactionId = createAndTestTransactionInit();

    performAndTestRejectedDebitRequest(transactionId, 9999L);

    TransactionResultResponse transactionResultResponse = transactionResult(transactionId);

    assertEquals(RESULT_CODE_AUTHORIZATION_FAILED, transactionResultResponse.getResult().getCode());
  }

  /**
   * This will test successful tokenization request, with Customer info in the response
   */
  @Test
  public void testTokenization() {

    // TODO: fetch this from Form API get parameters
    // Test setup created manually. If this stops working then ...
    // 1. execute a new add_card request using our test form (target staging),
    // 2. replace with the value of query parameter "sph-tokenization-id" found in success redirect URL,
    // 3. adjust assertions.
    UUID tokenizationId = UUID.fromString("ffdd479c-f783-49fa-9c01-5bb60d3f9e57");

    TokenizationResponse tokenResponse = null;
    try {
      tokenResponse = conn.tokenization(tokenizationId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(tokenResponse);
    assertEquals("2026", tokenResponse.getCard().getExpireYear());
    assertEquals("feabf8e0-6836-4f9a-88e7-5e34e6c9320e", tokenResponse.getCardToken().toString());
    assertEquals("not_tested", tokenResponse.getCard().getCvcRequired());
    assertEquals("415301", tokenResponse.getCard().getBin());
    assertEquals("debit", tokenResponse.getCard().getFunding());
    assertEquals("unknown", tokenResponse.getCard().getCategory());
    assertEquals("FI", tokenResponse.getCard().getCountryCode());
    // Customer information from the time the tokenizationId was generated through the Form API add_card request
    assertEquals("83.145.208.185", tokenResponse.getCustomer().getNetworkAddress()); // Manually updated :/
    assertEquals("FI", tokenResponse.getCustomer().getCountryCode());
    assertEquals("attempted", tokenResponse.getCardholderAuthentication());
    checkAcquirerInfo(tokenResponse, "060");
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
    assertEquals("100", result.getResult().getCode());
    assertEquals("OK", result.getResult().getMessage());

    boolean found = false;

    for (Settlement settlement : result.getSettlements()) {
      if (settlement.getMerchant().getAcquirerMerchantId().equals("90000001")) {
        found = true;
        break;
      }
    }

    assertTrue("Didn't find Acquirer Merchant ID with value 90000001", found);
  }

  /**
   * This will test successful reconciliation report request
   */
  @Ignore
  @Test
  public void testReconciliationReport() {

    fail("No test report available yet");

    // request settlement report for yesterday, today is not available
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    String date = dateFormat.format(cal.getTime());

    ReconciliationReportResponse result = null;
    try {
      result = conn.fetchReconciliationReport(date);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(result);
    assertEquals("100", result.getResult().getCode());
    assertEquals("OK", result.getResult().getMessage());
    assertNotNull(result.getReconciliationSettlements()[0].getAcquirer());
    assertNotNull(result.getReconciliationSettlements()[0].getAcquirerBatchId());
    assertNotNull(result.getReconciliationSettlements()[0].getBatch());
    assertNotNull(result.getReconciliationSettlements()[0].getCurrency());
    assertNotNull(result.getReconciliationSettlements()[0].getDateProcessed());
    assertNotNull(result.getReconciliationSettlements()[0].getMainAcquirerMerchantId());
    assertNotNull(result.getReconciliationSettlements()[0].getNetAmount());
    assertNotNull(result.getReconciliationSettlements()[0].getReference());
    assertNotNull(result.getReconciliationSettlements()[0].getStatus());
    assertNotEquals(result.getReconciliationSettlements()[0].getTransactionCount(), "0");
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getMerchant());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerAmountPresented());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerCommission());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerCommissionCurrency());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerDiscountRate());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerEstimatedSettlementValue());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerEstimatedSettlementValueCurrency());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerExchangeRate());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerTransactionFee());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerTransactionFeeCurrency());
    assertEquals("0", result.getReconciliationSettlements()[0].getUnallocatedTransactionsCount());

  }

  /**
   * This will test successful reconciliation report request with unallocated transactions
   */
  @Ignore
  @Test
  public void testReconciliationReport2() {

    fail("No test report available yet");

    // request settlement report for yesterday, today is not available
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    String date = dateFormat.format(cal.getTime());

    ReconciliationReportResponse result = null;
    try {
      result = conn.fetchReconciliationReport(date);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(result);
    assertEquals("100", result.getResult().getCode());
    assertEquals("OK", result.getResult().getMessage());
    assertNotNull(result.getReconciliationSettlements()[0].getAcquirer());
    assertNotNull(result.getReconciliationSettlements()[0].getAcquirerBatchId());
    assertNotNull(result.getReconciliationSettlements()[0].getBatch());
    assertNotNull(result.getReconciliationSettlements()[0].getCurrency());
    assertNotNull(result.getReconciliationSettlements()[0].getDateProcessed());
    assertNotNull(result.getReconciliationSettlements()[0].getMainAcquirerMerchantId());
    assertNotNull(result.getReconciliationSettlements()[0].getNetAmount());
    assertNotNull(result.getReconciliationSettlements()[0].getReference());
    assertNotNull(result.getReconciliationSettlements()[0].getStatus());
    assertEquals("0", result.getReconciliationSettlements()[0].getTransactionCount());
    assertNotEquals(0, Integer.parseInt(result.getReconciliationSettlements()[0].getUnallocatedTransactionsCount()));
    assertNotNull(result.getReconciliationSettlements()[0].getUnallocatedTransactions()[0].getFilingCode());
    assertNotNull(result.getReconciliationSettlements()[0].getUnallocatedTransactions()[0].getAcquirerAmountPresented());
    assertNotNull(result.getReconciliationSettlements()[0].getUnallocatedTransactions()[0].getAcquirerCommission());
    assertNotNull(result.getReconciliationSettlements()[0].getUnallocatedTransactions()[0].getAcquirerCommissionCurrency());
    assertNotNull(result.getReconciliationSettlements()[0].getUnallocatedTransactions()[0].getAcquirerDiscountRate());
    assertNotNull(result.getReconciliationSettlements()[0].getUnallocatedTransactions()[0].getAcquirerEstimatedSettlementValue());
    assertNotNull(result.getReconciliationSettlements()[0].getUnallocatedTransactions()[0].getAcquirerEstimatedSettlementValueCurrency());
    assertNotNull(result.getReconciliationSettlements()[0].getUnallocatedTransactions()[0].getAcquirerExchangeRate());
    assertNotNull(result.getReconciliationSettlements()[0].getUnallocatedTransactions()[0].getAcquirerTransactionFee());
    assertNotNull(result.getReconciliationSettlements()[0].getUnallocatedTransactions()[0].getAcquirerTransactionFeeCurrency());
  }

  /**
   * This will test successful reconciliation report request with commission settlement data included
   */
  @Ignore
  @Test
  public void  testReconciliationReport3() {

    fail("No test report available yet");

    // request settlement report for yesterday, today is not available
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    String date = dateFormat.format(cal.getTime());

    ReconciliationReportResponse result = null;
    try {
      result = conn.fetchReconciliationReport(date);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(result);
    assertEquals("100", result.getResult().getCode());
    assertEquals("OK", result.getResult().getMessage());
    assertNotNull(result.getReconciliationSettlements()[0].getAcquirer());
    assertNotNull(result.getReconciliationSettlements()[0].getAcquirerBatchId());
    assertNotNull(result.getReconciliationSettlements()[0].getBatch());
    assertNotNull(result.getReconciliationSettlements()[0].getCurrency());
    assertNotNull(result.getReconciliationSettlements()[0].getDateProcessed());
    assertNotNull(result.getReconciliationSettlements()[0].getMainAcquirerMerchantId());
    assertNotNull(result.getReconciliationSettlements()[0].getNetAmount());
    assertNotNull(result.getReconciliationSettlements()[0].getReference());
    assertNotNull(result.getReconciliationSettlements()[0].getStatus());
    assertNotEquals("0", result.getReconciliationSettlements()[0].getTransactionCount());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getMerchant());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerAmountPresented());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerCommission());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerCommissionCurrency());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerDiscountRate());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerEstimatedSettlementValue());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerEstimatedSettlementValueCurrency());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerExchangeRate());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerTransactionFee());
    assertNotNull(result.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerTransactionFeeCurrency());
    assertNotNull(result.getCommissionSettlements());
    assertNotNull(result.getCommissionSettlements()[0].getAcquirer());
    assertNotNull(result.getCommissionSettlements()[0].getAcquirerBatchId());
    assertNotNull(result.getCommissionSettlements()[0].getAmount());
    assertNotNull(result.getCommissionSettlements()[0].getBatch());
    assertNotNull(result.getCommissionSettlements()[0].getCurrency());
    assertNotNull(result.getCommissionSettlements()[0].getDateProcessed());
    assertNotNull(result.getCommissionSettlements()[0].getMainAcquirerMerchantId());
    assertNotNull(result.getCommissionSettlements()[0].getReference());

  }

}
