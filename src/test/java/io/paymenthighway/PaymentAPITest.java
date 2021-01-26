package io.paymenthighway;

import io.paymenthighway.model.Splitting;
import io.paymenthighway.model.Token;
import io.paymenthighway.model.request.*;
import io.paymenthighway.model.request.sca.*;
import io.paymenthighway.model.response.*;
import io.paymenthighway.model.response.transaction.ChargeCitResponse;
import io.paymenthighway.model.response.transaction.ChargeMitResponse;
import io.paymenthighway.model.response.transaction.DebitTransactionResponse;
import io.paymenthighway.test.ExternalServiceTest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpCoreContext;
import org.junit.*;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * PaymentHighway Payment API tests
 */
public class PaymentAPITest {

  Properties props;
  private String serviceUrl;
  private String signatureKeyId;
  private String signatureSecret;
  private String account;
  private String merchant;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    // required system information and authentication credentials
    // we read this from file, but can be from everywhere
    this.props = null;
    try {
      this.props = PaymentHighwayUtility.getProperties();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.serviceUrl = this.props.getProperty("service_url");
    this.signatureKeyId = this.props.getProperty("signature_key_id");
    this.signatureSecret = this.props.getProperty("signature_secret");
    this.account = this.props.getProperty("sph-account");
    this.merchant = this.props.getProperty("sph-merchant");
  }

  @After
  public void tearDown() throws Exception {
  }

  private PaymentAPI createPaymentAPI() {
    return new PaymentAPI(serviceUrl, signatureKeyId, signatureSecret, account, merchant);
  }

  static private final String RESULT_CODE_OK = "100";
  static private final String RESULT_CODE_SOFT_DECLINE = "400";

  private static Card validTestCard = new Card("4153013999700321", "2023", "11", "321");
  private static Card softDeclineCard = new Card("4153013999701170", "2023", "11", "170");

  private UUID initTransaction(PaymentAPI paymentAPI) throws IOException {
    InitTransactionResponse response = paymentAPI.initTransaction();
    assertApiResponseSuccessful(response);
    return response.getId();
  }

  private void assertApiResponseSuccessful(Response response)  {
    assertNotNull(response);
    assertEquals(response.getResult().getMessage(), "OK");
    assertEquals(response.getResult().getCode(), "100");
  }

  private ChargeCitResponse performCustomerInitiatedTransaction(
      Long amount,
      Card card,
      StrongCustomerAuthentication strongCustomerAuthentication
  ) throws IOException {
    PaymentAPI paymentAPI = createPaymentAPI();
    UUID transactionId = initTransaction(paymentAPI);

    ChargeCitRequest request = new ChargeCitRequest.Builder(card, amount, "EUR", "test-order-id", strongCustomerAuthentication).build();

    return  paymentAPI.chargeCustomerInitiatedTransaction(transactionId, request);
  }

  @Test
  public void testInitTransaction() {

    // create the payment highway service
    PaymentAPI paymentAPI = createPaymentAPI();

    InitTransactionResponse response = null;

    try {
      response = paymentAPI.initTransaction();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertEquals(response.getResult().getCode(), "100");
    assertEquals(response.getResult().getMessage(), "OK");
    assertEquals(response.getId().toString().length(), 36);
  }

  @Test
  public void testDebitWithCard() {

    // create the payment highway service
    PaymentAPI paymentAPI = createPaymentAPI();

    InitTransactionResponse response = null;

    try {
      response = paymentAPI.initTransaction();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    assertNotNull(response);
    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);
    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR");

    DebitTransactionResponse transactionResponse = null;

    try {
      transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getCode(), "100");
    assertEquals(transactionResponse.getResult().getMessage(), "OK");
  }

  @Test
  public void testCitWithCardNoSoftDecline() throws IOException {

    Urls urls = Urls.Builder("https://success.example.com", "https://failure.example.com", "https://cancel.example.com").build();
    StrongCustomerAuthentication strongCustomerAuthentication = StrongCustomerAuthentication.Builder(urls).build();

    ChargeCitResponse citResponse = performCustomerInitiatedTransaction(99L, validTestCard, strongCustomerAuthentication);

    assertNotNull(citResponse);
    assertEquals(RESULT_CODE_OK, citResponse.getResult().getCode());
    assertEquals("OK", citResponse.getResult().getMessage());
  }

  @Test
  public void testCitWithCardSoftDecline() throws IOException {

    Urls urls = Urls.Builder("https://success.example.com", "https://failure.example.com", "https://cancel.example.com").build();
    StrongCustomerAuthentication strongCustomerAuthentication = StrongCustomerAuthentication.Builder(urls).build();

    ChargeCitResponse citResponse = performCustomerInitiatedTransaction(99L, softDeclineCard, strongCustomerAuthentication);

    assertNotNull(citResponse);
    assertEquals(RESULT_CODE_SOFT_DECLINE, citResponse.getResult().getCode());

    String resultMessage = citResponse.getResult().getMessage();
    assertNotNull(citResponse);
    assertTrue("Unexpected result: " + resultMessage, resultMessage.matches(".*Soft Decline.*"));
  }

  @Test
  public void testCitWithFullScaDetails() throws IOException {

    Long testAmountInMinorUnits = 9999L;

    Urls returnUrls = Urls.Builder("https://success.example.com", "https://failure.example.com","https://cancel.example.com")
        .setWebhookSuccessUrl("https://example.com/webhook/success")
        .setWebhookCancelUrl("https://example.com/webhook/cancel")
        .setWebhookFailureUrl("https://example.com/webhook/failure")
        .setWebhookDelay(0)
        .build();

    CustomerDetails customerDetails = CustomerDetails.Builder()
        .setShippingAddressMatchesBillingAddress(true)
        .setName("Eric Example")
        .setEmail("eric.example@example.com")
        .setHomePhone(new PhoneNumber("358", "123456789"))
        .setMobilePhone(new PhoneNumber("358", "441234566"))
        .setWorkPhone(new PhoneNumber("358", "441234566"))
        .build();

    Address address = Address.Builder()
      .setCity("Helsinki")
      .setCountry("246")
      .setAddressLine1("Arkadiankatu 1")
      .setAddressLine2("Something")
      .setAddressLine3("Else")
      .setPostCode("00101")
      .setState("18")
      .build();

    CustomerAccount customerAccount = CustomerAccount.Builder()
        .setAccountAgeIndicator(CustomerAccount.AccountAgeIndicator.MoreThan60Days)
        .setAccountDate("2018-07-05")
        .setChangeIndicator(CustomerAccount.ChangeIndicator.MoreThan60Days)
        .setChangeDate("2018-09-11")
        .setPasswordChangeIndicator(CustomerAccount.PasswordChangeIndicator.NoChange)
        .setPasswordChangeDate("2018-07-05")
        .setNumberOfRecentPurchases(7)
        .setNumberOfAddCardAttemptsDay(1)
        .setNumberOfTransactionActivityDay(3)
        .setNumberOfTransactionActivityYear(8)
        .setShippingAddressIndicator(CustomerAccount.ShippingAddressIndicator.Between30And60Days)
        .setShippingAddressUsageDate("2019-07-01")
        .setSuspiciousActivity(CustomerAccount.SuspiciousActivity.NoSuspiciousActivity)
        .build();

    Purchase purchase = Purchase.Builder()
        .setShippingIndicator(Purchase.ShippingIndicator.ShipToCardholdersAddress)
        .setDeliveryTimeFrame(Purchase.DeliveryTimeFrame.SameDayShipping)
        .setDeliveryEmail("eric.example@example.com")
        .setReorderItemsIndicator(Purchase.ReorderItemsIndicator.FirstTimeOrdered)
        .setPreOrderPurchaseIndicator(Purchase.PreOrderItemsIndicator.FutureAvailability)
        .setPreOrderDate("2025-12-10")
        .setShippingNameIndicator(Purchase.ShippingNameIndicator.AccountNameMatchesShippingName)
        .setGiftCardAmount(testAmountInMinorUnits)
        .setGiftCardCount(20)
        .build();

    CustomerAuthenticationInfo customerAuthenticationInfo = CustomerAuthenticationInfo.Builder()
        .setMethod(CustomerAuthenticationInfo.Method.OwnCredentials)
        .setTimestamp("2019-08-27T09:22:52Z")
        .setData("Unused")
        .build();

    StrongCustomerAuthentication strongCustomerAuthentication = new StrongCustomerAuthentication.Builder(returnUrls)
        .setCustomerDetails(customerDetails)
        .setCustomerAccount(customerAccount)
        .setPurchase(purchase)
        .setBillingAddress(address)
        .setShippingAddress(address)
        .setCustomerAuthenticationInfo(customerAuthenticationInfo)
        .setDesiredChallengeWindowSize(StrongCustomerAuthentication.DesiredChallengeWindowSize.Window600x400)
        .setExitIframeOnResult(false)
        .setExitIframeOnThreeDSecure(false)
        .build();

    ChargeCitResponse citResponse = performCustomerInitiatedTransaction(
        testAmountInMinorUnits,
        validTestCard,
        strongCustomerAuthentication
    );

    assertNotNull(citResponse);
    assertEquals(RESULT_CODE_OK, citResponse.getResult().getCode());
    assertEquals("OK", citResponse.getResult().getMessage());
  }

  @Test
  public void testSoftDeclineCardIsAcceptedWithMitRequest() throws IOException {

    PaymentAPI paymentAPI = createPaymentAPI();
    UUID transactionId = initTransaction(paymentAPI);

    ChargeMitRequest request = ChargeMitRequest.Builder(
        softDeclineCard, 99L, "EUR", "test-order-id"
    ).setReferenceNumber("1313").build();

    ChargeMitResponse chargeMitResponse = paymentAPI.chargeMerchantInitiatedTransaction(transactionId, request);

    assertNotNull(chargeMitResponse);
    assertEquals(RESULT_CODE_OK, chargeMitResponse.getResult().getCode());
    assertEquals("OK", chargeMitResponse.getResult().getMessage());
  }

  @Test
  public void testCommitWithCardTransaction() {

    // create the payment highway service
    PaymentAPI paymentAPI = createPaymentAPI();

    InitTransactionResponse response = null;

    try {
      response = paymentAPI.initTransaction();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    assertNotNull(response);
    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR");

    DebitTransactionResponse transactionResponse = null;

    try {
      transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getCode(), "100");
    assertEquals(transactionResponse.getResult().getMessage(), "OK");

    CommitTransactionResponse commitResponse = null;

    try {
      commitResponse = paymentAPI.commitTransaction(transactionId, "9999", "EUR");
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(commitResponse);
    //TODO: Should get a token, but the test card does not return one
    //assertNotNull(commitResponse.getCardToken());
    assertEquals(commitResponse.getResult().getCode(), "100");
    assertEquals(commitResponse.getResult().getMessage(), "OK");
    assertEquals(commitResponse.getCard().getType(), "Visa");
    //TODO: Should get a "no", but the test card gives "not_tested"
    assertEquals(commitResponse.getCard().getCvcRequired(), "not_tested");

  }

  @Test
  public void testRevertTransaction() {

    // create the payment highway service
    PaymentAPI paymentAPI = createPaymentAPI();

    // init transaction
    InitTransactionResponse response = null;

    try {
      response = paymentAPI.initTransaction();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    // create transaction
    assertNotNull(response);
    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR");
    DebitTransactionResponse transactionResponse = null;

    try {
      transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getCode(), "100");
    assertEquals(transactionResponse.getResult().getMessage(), "OK");

    // revert transaction
    RevertResponse revertResponse = null;
    try {
      revertResponse = paymentAPI.revertTransaction(transactionId, "9999");
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertEquals(revertResponse.getResult().getCode(), "100");
    assertEquals(revertResponse.getResult().getMessage(), "OK");
  }

  @Test
  public void testRevertFullAmountTransaction() {

    // create the payment highway service
    PaymentAPI paymentAPI = createPaymentAPI();

    // init transaction
    InitTransactionResponse response = null;

    try {
      response = paymentAPI.initTransaction();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    // create transaction
    assertNotNull(response);
    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", true);
    DebitTransactionResponse transactionResponse = null;

    try {
      transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getCode(), "100");
    assertEquals(transactionResponse.getResult().getMessage(), "OK");

    // revert transaction
    RevertResponse revertResponse = null;
    try {
      revertResponse = paymentAPI.revertTransaction(transactionId);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertEquals(revertResponse.getResult().getCode(), "100");
    assertEquals(revertResponse.getResult().getMessage(), "OK");

    // check status
    TransactionStatusResponse statusResponse = null;
    try {
      statusResponse = paymentAPI.transactionStatus(transactionId);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(statusResponse);
    assertEquals(statusResponse.getResult().getMessage(), "OK");
    assertEquals(statusResponse.getResult().getCode(), "100");
    assertEquals(statusResponse.getTransaction().getCurrentAmount(), "0");
    assertEquals(statusResponse.getTransaction().getId(), transactionId);
  }

  @Test
  public void testTransactionStatus() {

    // create the payment highway service
    PaymentAPI paymentAPI = createPaymentAPI();

    // init transaction
    InitTransactionResponse response = null;

    try {
      response = paymentAPI.initTransaction();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    // create transaction
    assertNotNull(response);
    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR");

    DebitTransactionResponse transactionResponse = null;

    try {
      transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getCode(), "100");
    assertEquals(transactionResponse.getResult().getMessage(), "OK");

    RevertResponse revertResponse = null;

    try {
      revertResponse = paymentAPI.revertTransaction(transactionId, "9950");
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    assertNotNull(revertResponse);
    assertEquals(revertResponse.getResult().getCode(), "100");
    assertEquals(revertResponse.getResult().getMessage(), "OK");

    // status response test
    TransactionStatusResponse statusResponse = null;

    try {
      statusResponse = paymentAPI.transactionStatus(transactionId);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(statusResponse);
    assertEquals(statusResponse.getResult().getMessage(), "OK");
    assertEquals(statusResponse.getResult().getCode(), "100");
    assertEquals(statusResponse.getTransaction().getCurrentAmount(), "49");
    assertEquals(statusResponse.getTransaction().getId(), transactionId);
    assertEquals(statusResponse.getTransaction().getCard().getCvcRequired(), "not_tested");
  }

  @Test
  public void testOrderSearch() {

    // create the payment highway service
    PaymentAPI paymentAPI = createPaymentAPI();

    // init transaction
    InitTransactionResponse response = null;

    try {
      response = paymentAPI.initTransaction();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    // create transaction
    assertNotNull(response);
    UUID transactionId = response.getId();

    String pan = "4153013999700024";
    String cvc = "024";
    String expiryYear = "2023";
    String expiryMonth = "11";
    Card card = new Card(pan, expiryYear, expiryMonth, cvc);
    UUID orderId = UUID.randomUUID();

    TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", orderId.toString());

    DebitTransactionResponse transactionResponse = null;

    try {
      transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    assertNotNull(transactionResponse);
    assertEquals(transactionResponse.getResult().getCode(), "100");
    assertEquals(transactionResponse.getResult().getMessage(), "OK");

    // order search test
    OrderSearchResponse orderSearchResponse = null;

    try {
      orderSearchResponse = paymentAPI.searchOrders(orderId.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(orderSearchResponse);
    assertEquals(orderSearchResponse.getResult().getMessage(), "OK");
    assertEquals(orderSearchResponse.getResult().getCode(), "100");
    assertEquals(orderSearchResponse.getTransactions()[0].getCurrentAmount(), "9999");
    assertEquals(orderSearchResponse.getTransactions()[0].getId(), transactionId);
  }

  @Test
  public void testPaymentSplittingDetailsAreReturnedInTransactionStatus() throws IOException {

    Long paymentAmount = 1000L;
    Long subMerchantReceivesAmount = 900L;
    String subMerchantId = "12345";

    PaymentAPI paymentAPI = createPaymentAPI();
    UUID transactionId = initTransaction(paymentAPI);

    Splitting splitting = new Splitting(subMerchantId, subMerchantReceivesAmount);
    TransactionRequest transaction = new TransactionRequest.Builder(validTestCard, paymentAmount, "EUR")
        .setSplitting(splitting)
        .build();

    DebitTransactionResponse transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
    assertApiResponseSuccessful(transactionResponse);

    TransactionStatusResponse statusResponse = paymentAPI.transactionStatus(transactionId);
    assertApiResponseSuccessful(statusResponse);

    assertEquals(statusResponse.getTransaction().getAmount(), Long.toString(paymentAmount));
    assertEquals(statusResponse.getTransaction().getSplitting().getAmount(), subMerchantReceivesAmount);
    assertEquals(statusResponse.getTransaction().getSplitting().getMerchantId(), subMerchantId);
  }

  @Test
  public void testCommit() {

    // create the payment highway service
    PaymentAPI paymentAPI = createPaymentAPI();

    // create the payment highway request parameters
    FormBuilder formBuilder = new FormBuilder(
        "POST", this.signatureKeyId, this.signatureSecret,
        this.account, this.merchant, this.serviceUrl);

    FormContainer formContainer = formBuilder.generateAddCardAndPaymentParameters(
                "http://www.paymenthighway.fi", "http://www.solinor.com/", "http://www.solinor.fi", "EN",
                "9999", "EUR", "1", "test payment");

    FormAPI formApi = new FormAPI(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
    String result = null;

    try {
      result = formApi.payWithCard(formContainer.getFields());
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    assertNotNull(result);
    Matcher matcher = Pattern.compile("(?<=form action=\").{50}").matcher(result);
    assertTrue(matcher.find());
    String formUri = matcher.group();

    // submit form (fake a browser)
    HttpPost httpPost = new HttpPost(this.serviceUrl + formUri);
    List<NameValuePair> submitParameters = new ArrayList<>();
    submitParameters.add(new BasicNameValuePair("card_number_formatted", "4153 0139 9970 0024"));
    submitParameters.add(new BasicNameValuePair("card_number", "4153013999700024"));
    submitParameters.add(new BasicNameValuePair("expiration_month", "11"));
    submitParameters.add(new BasicNameValuePair("expiration_year", "2023"));
    submitParameters.add(new BasicNameValuePair("expiry", "11 / 17"));
    submitParameters.add(new BasicNameValuePair("cvv", "024"));

    httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
    httpPost.addHeader("User-Agent", "PaymentHighway Java Lib");

    HttpClientContext context = HttpClientContext.create();
    try {

      httpPost.setEntity(new UrlEncodedFormEntity(submitParameters));
      CloseableHttpClient httpclient = HttpClients.createDefault();

      httpclient.execute(httpPost, context);

    } catch (IOException e) {
      e.printStackTrace();
    }
    // read redirect uri and GET params from it.
    HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(HttpCoreContext.HTTP_REQUEST);

    List<NameValuePair> params = URLEncodedUtils.parse(currentReq.getURI(), "UTF-8");

    // get sph-transaction-id
    String transactionId = null;
    for (NameValuePair param : params) {
      if (param.getName().equalsIgnoreCase("sph-transaction-id")) {
        transactionId = param.getValue();
      }
    }
    assertNotNull(transactionId);

    CommitTransactionResponse commitResponse = null;

    assertNotNull(transactionId);
    try {
      commitResponse = paymentAPI.commitTransaction(UUID.fromString(transactionId), "9999", "EUR");
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(commitResponse);
    assertEquals(commitResponse.getResult().getMessage(), "OK");
    assertTrue(commitResponse.getCardToken().toString().length() == 36);
    assertEquals(commitResponse.getCard().getPartialPan(), "0024");
    assertEquals(commitResponse.getCard().getExpireYear(), "2023");

  }

  @Test
  public void testTokenize() {

    // create the payment highway service
    PaymentAPI paymentAPI = createPaymentAPI();

    // create the payment highway request parameters
    FormBuilder formBuilder = new FormBuilder(
        "POST", this.signatureKeyId, this.signatureSecret,
        this.account, this.merchant, this.serviceUrl);

    FormContainer formContainer = formBuilder.generateAddCardParameters(
            "http://www.paymenthighway.fi", "http://www.solinor.com/", "http://www.solinor.fi", "EN");

    FormAPI formApi = new FormAPI(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
    String result = null;
    try {
      result = formApi.addCard(formContainer.getFields());
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    assertNotNull(result);
    Matcher matcher = Pattern.compile("(?<=form action=\").{51}").matcher(result);
    assertTrue(matcher.find());
    String formUri = matcher.group();

    // submit form (fake a browser)
    HttpPost httpPost = new HttpPost(this.serviceUrl + formUri);
    List<NameValuePair> submitParameters = new ArrayList<>();
    submitParameters.add(new BasicNameValuePair("card_number_formatted", "4153 0139 9970 0024"));
    submitParameters.add(new BasicNameValuePair("card_number", "4153013999700024"));
    submitParameters.add(new BasicNameValuePair("expiration_month", "11"));
    submitParameters.add(new BasicNameValuePair("expiration_year", "2023"));
    submitParameters.add(new BasicNameValuePair("expiry", "11 / 17"));
    submitParameters.add(new BasicNameValuePair("cvv", "024"));

    httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
    httpPost.addHeader("User-Agent", "PaymentHighway Java Lib");

    HttpClientContext context = HttpClientContext.create();
    try {

      httpPost.setEntity(new UrlEncodedFormEntity(submitParameters));
      CloseableHttpClient httpclient = HttpClients.createDefault();

      httpclient.execute(httpPost, context);

    } catch (IOException e) {
      e.printStackTrace();
    }
    // read redirect uri and GET params from it.
    HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(
        HttpCoreContext.HTTP_REQUEST);

    List<NameValuePair> params = URLEncodedUtils.parse(currentReq.getURI(), "UTF-8");

    // get sph-tokenization-id
    UUID tokenizationId = null;
    for (NameValuePair param : params) {
      if (param.getName().equalsIgnoreCase("sph-tokenization-id")) {
        tokenizationId = UUID.fromString(param.getValue());
      }
    }

    TokenizationResponse tokenResponse = null;

    try {
      tokenResponse = paymentAPI.tokenize(tokenizationId);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(tokenResponse);
    assertEquals(tokenResponse.getCard().getExpireYear(), "2023");
    assertTrue(tokenResponse.getCardToken().toString().length() == 36);
    assertEquals(tokenResponse.getResult().getMessage(), "OK");
    assertEquals(tokenResponse.getCard().getCvcRequired(), "no");

  }

  @Test
  public void testDebitWithToken() {

    // create the payment highway service
    PaymentAPI paymentAPI = createPaymentAPI();

    // create the payment highway request parameters
    FormBuilder formBuilder = new FormBuilder(
        "POST", this.signatureKeyId, this.signatureSecret,
        this.account, this.merchant, this.serviceUrl);

    FormContainer formContainer = formBuilder.generateAddCardParameters(
            "http://www.paymenthighway.fi", "http://www.solinor.com/", "http://www.solinor.fi", "EN");

    FormAPI formApi = new FormAPI(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
    String result = null;
    try {
      result = formApi.addCard(formContainer.getFields());
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    assertNotNull(result);
    Matcher matcher = Pattern.compile("(?<=form action=\").{51}").matcher(result);
    assertTrue(matcher.find());
    String formUri = matcher.group();

    // submit form (fake a browser)
    HttpPost httpPost = new HttpPost(this.serviceUrl + formUri);
    List<NameValuePair> submitParameters = new ArrayList<>();
    submitParameters.add(new BasicNameValuePair("card_number_formatted", "4153 0139 9970 0024"));
    submitParameters.add(new BasicNameValuePair("card_number", "4153013999700024"));
    submitParameters.add(new BasicNameValuePair("expiration_month", "11"));
    submitParameters.add(new BasicNameValuePair("expiration_year", "2023"));
    submitParameters.add(new BasicNameValuePair("expiry", "11 / 17"));
    submitParameters.add(new BasicNameValuePair("cvv", "024"));

    httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
    httpPost.addHeader("User-Agent", "PaymentHighway Java Lib");

    HttpClientContext context = HttpClientContext.create();
    try {

      httpPost.setEntity(new UrlEncodedFormEntity(submitParameters));
      CloseableHttpClient httpclient = HttpClients.createDefault();

      httpclient.execute(httpPost, context);

    } catch (IOException e) {
      e.printStackTrace();
    }
    // read redirect uri and GET params from it.
    HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(
        HttpCoreContext.HTTP_REQUEST);

    List<NameValuePair> params = URLEncodedUtils.parse(currentReq.getURI(), "UTF-8");

    // get sph-tokenization-id
    UUID tokenizationId = null;
    for (NameValuePair param : params) {
      if (param.getName().equalsIgnoreCase("sph-tokenization-id")) {
        tokenizationId = UUID.fromString(param.getValue());
      }
    }

    TokenizationResponse tokenResponse = null;

    try {
      tokenResponse = paymentAPI.tokenize(tokenizationId);
    } catch (IOException e2) {
      e2.printStackTrace();
    }

    assertNotNull(tokenResponse);
    assertEquals(tokenResponse.getCard().getExpireYear(), "2023");
    assertTrue(tokenResponse.getCardToken().toString().length() == 36);
    assertEquals(tokenResponse.getResult().getMessage(), "OK");

    // debit with token (success)
    InitTransactionResponse initResponse = null;

    try {
      initResponse = paymentAPI.initTransaction();
    } catch (IOException e2) {
      e2.printStackTrace();
    }
    assertNotNull(initResponse);

    Token token = new Token(tokenResponse.getCardToken().toString());
    TransactionRequest transaction = new TransactionRequest(token, "1111", "EUR");

    DebitTransactionResponse debitResponse = null;

    try {
      debitResponse = paymentAPI.debitTransaction(initResponse.getId(), transaction);
    } catch (IOException e2) {
      e2.printStackTrace();
    }

    assertNotNull(debitResponse);
    assertEquals(debitResponse.getResult().getCode(), "100");
    assertEquals(debitResponse.getResult().getMessage(), "OK");

  }

  @Test
  public void testDailyBatchReport() {

    // create the payment highway service
    PaymentAPI paymentAPI = createPaymentAPI();

    // request batch for yesterday, today is not available
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    String date = dateFormat.format(cal.getTime());

    ReportResponse reportResponse = null;

    try {
      reportResponse = paymentAPI.fetchDailyReport(date);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(reportResponse);
    assertEquals(reportResponse.getResult().getCode(), "100");
    assertEquals(reportResponse.getResult().getMessage(), "OK");
  }

  @Ignore
  @Test
  public void testReconciliationReport() {

    fail("No test report available yet");

    // create the payment highway service
    PaymentAPI paymentAPI = createPaymentAPI();

    // request batch for yesterday, today is not available
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    String date = dateFormat.format(cal.getTime());

    ReconciliationReportResponse reconciliationReportResponse = null;

    try {
      reconciliationReportResponse = paymentAPI.fetchReconciliationReport(date);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(reconciliationReportResponse);
    assertEquals(reconciliationReportResponse.getResult().getCode(), "100");
    assertEquals(reconciliationReportResponse.getResult().getMessage(), "OK");
    assertNotNull(reconciliationReportResponse.getReconciliationSettlements()[0].getTransactions()[0].getMerchant());
    assertNotNull(reconciliationReportResponse.getReconciliationSettlements()[0].getTransactions()[0].getAcquirerAmountPresented());
  }

  @Test
  @Category(ExternalServiceTest.class)
  public void initMobilePayAppFlow() throws Exception {
    MobilePayInitRequest request = MobilePayInitRequest.Builder(100, "EUR")
        .setWebhookSuccessUrl("https://myserver.com/success")
        .setWebhookCancelUrl("https://myserver.com/cancel")
        .setWebhookFailureUrl("https://myserver.com/failure")
        .setReturnUri(new URI("myapp://paid"))
        .setLanguage("fi")
        .setShopLogoUrl("https://myserver.com/shop-logo")
        .setOrder(UUID.randomUUID().toString())
        .build();

    PaymentAPI paymentAPI = createPaymentAPI();
    MobilePayInitResponse response = paymentAPI.initMobilePaySession(request);

    assertNotNull(response.getUri());
    assertNotNull(response.getSessionToken());
    assertNotNull(response.getValidUntil());
  }

  @Test
  @Category(ExternalServiceTest.class)
  public void mobilePaySessionStatus() throws Exception {
    MobilePayInitRequest request = MobilePayInitRequest.Builder(100, "EUR")
            .setWebhookSuccessUrl("https://myserver.com/success")
            .setWebhookCancelUrl("https://myserver.com/cancel")
            .setWebhookFailureUrl("https://myserver.com/failure")
            .setReturnUri(new URI("myapp://paid"))
            .setOrder(UUID.randomUUID().toString())
            .build();

    PaymentAPI paymentAPI = createPaymentAPI();
    MobilePayInitResponse initResponse = paymentAPI.initMobilePaySession(request);
    assertNotNull("Initialization failed.", initResponse.getSessionToken());

    MobilePayStatusResponse response = paymentAPI.mobilePaySessionStatus(initResponse.getSessionToken());
    assertEquals("in_progress", response.getStatus());
    assertEquals(
            "Valid until value should be same in init and status check.",
            initResponse.getValidUntil(),
            response.getValidUntil()
    );
    assertNull("When status is 'in_progress' transaction id should be null.", response.getTransactionId());
  }

  @Test
  @Category(ExternalServiceTest.class)
  public void initPivoAppFlow() throws Exception {
    PivoInitRequest request = PivoInitRequest.Builder(100L, "EUR")
        .setWebhookSuccessUrl("https://myserver.com/success")
        .setWebhookCancelUrl("https://myserver.com/cancel")
        .setWebhookFailureUrl("https://myserver.com/failure")
        .setAppUrl("myapp://paid")
        .setLanguage("FI")
        .setOrder(UUID.randomUUID().toString())
        .setSplitting(new Splitting("12345", 10L))
        .build();

    PaymentAPI paymentAPI = createPaymentAPI();
    PivoInitResponse response = paymentAPI.initPivoTransaction(request);

    assertNotNull(response.getUri());
    assertNotNull(response.getTransactionId());
    assertNotNull(response.getValidUntil());
  }
}
