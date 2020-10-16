package io.paymenthighway;

import io.paymenthighway.connect.FormAPIConnection;
import io.paymenthighway.formBuilders.FormBuilderConstants;
import org.apache.http.NameValuePair;
import org.junit.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 */
public class FormBuilderTest {

  Properties props = null;
  private String serviceUrl;
  private String signatureKeyId;
  private String signatureSecret;

  public static class TestValues {
    static final String methodPost = "POST";
    static final String account = "test";
    static final String merchant = "test_merchantId";
    static final long amount = 9999L;
    static final String orderId = "1000123A";
    static final String successUrl = "https://www.paymenthighway.fi/";
    static final String failureUrl = "http://www.solinor.com";
    static final String cancelUrl = "https://solinor.fi";
    static final String language = "EN";
    static final String description = "this is payment description";
  }

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

  private FormBuilder createFormBuilder(String method, String account, String merchant) {
    return new FormBuilder(method, this.signatureKeyId, this.signatureSecret, account, merchant, this.serviceUrl);
  }



  /**
   * Test with acceptCvcRequired set to false.
   */
  @Test
  public void testAddCardParameters() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "https://paymenthighway.fi/index-en.html";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.addCardParameters(successUrl, failureUrl, cancelUrl)
      .language(language)
      .acceptCvcRequired(false)
      .build();

    List<NameValuePair> nameValuePairs = formContainer.getFields();

    // test that the result has a signature
    Iterator<NameValuePair> it = nameValuePairs.iterator();
    String signature = Helper.assertFieldExists(nameValuePairs, FormBuilderConstants.SIGNATURE).getValue();
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));
  }

  /**
   * Test with acceptCvcRequired set to true.
   */
  @Test
  public void testAddCardParameters2() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";

    // create the payment highway request parameters
    FormBuilder formBuilder = new FormBuilder(method,
        this.signatureKeyId, this.signatureSecret, account, merchant,
        this.serviceUrl);

    FormContainer formContainer = formBuilder.addCardParameters(successUrl, failureUrl, cancelUrl)
      .language(language)
      .acceptCvcRequired(false)
      .build();

    // test that Payment Highway accepts this as a request
    FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl,
        this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = formApi.addCardRequest(formContainer.getFields());
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
    assertTrue(response.contains("Payment Highway"));
  }

  /**
   * Test with acceptCvcRequired, skipFormNotifications, exitIframeOnResult and exitIframeon3ds set to true.
   */
  @Test
  public void testAddCardParameters3() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.addCardParameters(successUrl, failureUrl, cancelUrl)
      .language(language)
      .acceptCvcRequired(true)
      .skipFormNotifications(true)
      .exitIframeOnResult(true)
      .exitIframeOn3ds(true)
      .build();

    // test that Payment Highway accepts this as a request
    FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = formApi.addCardRequest(formContainer.getFields());
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
    assertTrue(response.contains("Payment Highway"));
  }

  @Test
  public void testAddCardUse3ds() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";

    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.addCardParameters(successUrl, failureUrl, cancelUrl)
      .language(language)
      .use3ds(false)
      .build();

    FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = formApi.addCardRequest(formContainer.getFields());
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
    assertTrue(response.contains("Payment Highway"));
  }

  /**
   * Test with only the mandatory parameters.
   */
  @Test
  public void testAddCardParameters4() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "https://paymenthighway.fi/index-en.html";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.addCardParameters(successUrl, failureUrl, cancelUrl)
      .language(language)
      .build();

    List<NameValuePair> nameValuePairs = formContainer.getFields();

    // test that the result has a signature
    Iterator<NameValuePair> it = nameValuePairs.iterator();
    String signature = Helper.assertFieldExists(nameValuePairs, FormBuilderConstants.SIGNATURE).getValue();
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));
  }

  @Test
  public void testPaymentParameters() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "https://paymenthighway.fi/index-en.html";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .build();

    // test that the result has a signature
    String signature = Helper.assertFieldExists(formContainer.getFields(), FormBuilderConstants.SIGNATURE).getValue();
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));
  }

  @Test
  public void testPaymentParametersWithSplitting() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "https://paymenthighway.fi/index-en.html";
    String cancelUrl = "https://solinor.fi";
    String description = "this is payment description";
    Long splittingMerchantId = 123L;
    String splittingMerchantIdString = "123";
    Long splittingAmount = 10L;
    String splittingAmountString = "10";

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
        .splitting(splittingMerchantId, splittingAmount)
        .build();
    assertEquals(15, formContainer.getFields().size());
    checkSplittingParameters(formContainer.getFields(), splittingMerchantIdString, splittingAmountString);

  }

  /**
   * Test with only the mandatory parameters.
   */
  @Test
  public void testGetPaymentParameters2() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .build();

    // test that Payment Highway accepts this as a request
    FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = formApi.paymentRequest(formContainer.getFields());
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }

  /**
   * Test with all the optional parameters present.
   */
  @Test
  public void testGetPaymentParameters3() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .skipFormNotifications(true)
      .exitIframeOnResult(true)
      .exitIframeOn3ds(true)
      .build();

    // test that Payment Highway accepts this as a request
    FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = formApi.paymentRequest(formContainer.getFields());
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }

  @Test
  public void testGetPaymentFormWithUse3ds() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";

    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .use3ds(true)
      .build();

    FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = formApi.paymentRequest(formContainer.getFields());
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }

  @Test
  public void testGetAddCardAndPaymentParameters() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "https://paymenthighway.fi/index-en.html";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .tokenize(true)
      .build();

    // test that the result has a signature
    Iterator<NameValuePair> it = formContainer.getFields().iterator();
    String signature = null;
    while (it.hasNext()) {
      NameValuePair nameValuePair = it.next();
      String name = nameValuePair.getName();

      if (name.equalsIgnoreCase("signature")) {
        signature = nameValuePair.getValue();
      }
    }
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));
  }

  /**
   * Test without optional parameters.
   */
  @Test
  public void testGetAddCardAndPaymentParameters2() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .tokenize(true)
      .build();

    // test that Payment Highway accepts this as a request
    FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = formApi.paymentRequest(formContainer.getFields());
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }

  /**
   * Test with optional parameters present.
   */
  @Test
  public void testGetAddCardAndPaymentParameters3() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .tokenize(true)
      .skipFormNotifications(true)
      .exitIframeOnResult(true)
      .exitIframeOn3ds(true)
      .build();

    // test that Payment Highway accepts this as a request
    FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = formApi.paymentRequest(formContainer.getFields());
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }

  @Test
  public void testGetAddCardAndPaymentWithUse3ds() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .tokenize(true)
      .skipFormNotifications(true)
      .exitIframeOnResult(true)
      .exitIframeOn3ds(true)
      .use3ds(true)
      .build();

    // test that Payment Highway accepts this as a request
    FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = formApi.paymentRequest(formContainer.getFields());
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }

  /**
   * Test without optional parameters.
   */
  @Test
  public void testGetPayWithTokenAndCvcParameters() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";
    UUID token = UUID.fromString("71435029-fbb6-4506-aa86-8529efb640b0");

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.payWithTokenAndCvcParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description, token)
      .language(language)
      .build();

    // test that Payment Highway accepts this as a request
    FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = formApi.payWithTokenAndCvcRequest(formContainer.getFields());
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }

  @Test
  public void testPayWithTokenAndCvcWithSplitting() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String description = "this is payment description";
    UUID token = UUID.fromString("71435029-fbb6-4506-aa86-8529efb640b0");
    Long splittingMerchantId = 123L;
    String splittingMerchantIdString = "123";
    Long splittingAmount = 10L;
    String splittingAmountString = "10";

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.payWithTokenAndCvcParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description, token)
        .splitting(splittingMerchantId, splittingAmount)
        .build();

    assertEquals(16, formContainer.getFields().size());
    checkSplittingParameters(formContainer.getFields(), splittingMerchantIdString, splittingAmountString);

  }

  /**
   * Test with optional parameters present.
   */
  @Test
  public void testGetPayWithTokenAndCvcParameters2() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";
    UUID token = UUID.fromString("71435029-fbb6-4506-aa86-8529efb640b0");

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.payWithTokenAndCvcParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description, token)
      .language(language)
      .skipFormNotifications(true)
      .exitIframeOnResult(true)
      .exitIframeOn3ds(true)
      .build();

    // test that Payment Highway accepts this as a request
    FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = formApi.payWithTokenAndCvcRequest(formContainer.getFields());
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }

  @Test
  public void testGetPayWithTokenAndCvcWithUse3ds() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";
    UUID token = UUID.fromString("71435029-fbb6-4506-aa86-8529efb640b0");

    // create the payment highway request parameters
    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.payWithTokenAndCvcParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description, token)
      .language(language)
      .skipFormNotifications(false)
      .exitIframeOn3ds(false)
      .use3ds(false)
      .build();

    // test that Payment Highway accepts this as a request
    FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

    String response = null;
    try {
      response = formApi.payWithTokenAndCvcRequest(formContainer.getFields());
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }

  @Test
  public void testMobilePayForm() {
    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";

    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.mobilePayParametersBuilder(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .build();

    assertEquals(14, formContainer.getFields().size());
  }

  @Test
  public void testMobilePayFormWithLogo() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";
    String logoUrl = "https://foo.bar";

    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.mobilePayParametersBuilder(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .shopLogoUrl(logoUrl)
      .exitIframeOnResult(true)
      .build();

    assertEquals(16, formContainer.getFields().size());
  }

  @Test
  public void testMobilePaySplitting() {
    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    String amount = "9999";
    String currency = "EUR";
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String description = "this is payment description";
    Long splittingMerchantId = 123L;
    String splittingMerchantIdString = "123";
    Long splittingAmount = 10L;
    String splittingAmountString = "10";


    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.mobilePayParametersBuilder(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
        .splitting(splittingMerchantId, splittingAmount)
        .build();

    assertEquals(15, formContainer.getFields().size());
    checkSplittingParameters(formContainer.getFields(), splittingMerchantIdString, splittingAmountString);
  }

  @Test
  public void testPivoPaymentParameters() {

    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    long amount = 9999L;
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String language = "EN";
    String description = "this is payment description";
    String appUrl = "app://appi";
    String phoneNumber = "+358441234567";
    String referenceNumber = "1313";

    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.pivoParametersBuilder(
        successUrl,
        failureUrl,
        cancelUrl,
        amount,
        orderId,
        description)
        .language(language)
        .exitIframeOnResult(true)
        .appUrl(appUrl)
        .phoneNumber(phoneNumber)
        .referenceNumber(referenceNumber)
        .build();

    assertEquals(18, formContainer.getFields().size());
  }

  @Test
  public void testPivoSplittingParameters() {
    String method = "POST";
    String account = "test";
    String merchant = "test_merchantId";
    long amount = 9999L;
    String orderId = "1000123A";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "http://www.solinor.com";
    String cancelUrl = "https://solinor.fi";
    String description = "this is payment description";
    Long splittingMerchantId = 123L;
    String splittingMerchantIdString = "123";
    Long splittingAmount = 10L;
    String splittingAmountString = "10";

    FormBuilder formBuilder = createFormBuilder(method, account, merchant);

    FormContainer formContainer = formBuilder.pivoParametersBuilder(
        successUrl,
        failureUrl,
        cancelUrl,
        amount,
        orderId,
        description
    )
        .splitting(splittingMerchantId, splittingAmount)
        .build();

    assertEquals(15, formContainer.getFields().size());
    checkSplittingParameters(formContainer.getFields(), splittingMerchantIdString, splittingAmountString);
  }

  @Test
  public void testAfterPayParameters() {

     String orderDescription = "Description on the invoice";
     String prefilledSocialSecurityNumber = "121222-123X";
     String prefilledEmailAddress = "test@example.com";

     FormBuilder formBuilder = createFormBuilder(TestValues.methodPost, TestValues.account, TestValues.merchant);

     FormContainer formContainer = formBuilder.afterPayParametersBuilder(
       TestValues.successUrl,
       TestValues.failureUrl,
       TestValues.cancelUrl,
       TestValues.amount,
       TestValues.orderId,
       TestValues.description,
       orderDescription
     )
       .language(TestValues.language)
       .exitIframeOnResult(true)
       .socialSecurityNumber(prefilledSocialSecurityNumber)
       .emailAddress(prefilledEmailAddress)
       .build();

     assertEquals(18, formContainer.getFields().size());
  }

  @Test
  public void testAfterPaySplitting() {

    String orderDescription = "Description on the invoice";
    Long splittingMerchantId = 123L;
    String splittingMerchantIdString = "123";
    Long splittingAmount = 10L;
    String splittingAmountString = "10";

    FormBuilder formBuilder = createFormBuilder(TestValues.methodPost, TestValues.account, TestValues.merchant);

    FormContainer formContainer = formBuilder.afterPayParametersBuilder(
        TestValues.successUrl,
        TestValues.failureUrl,
        TestValues.cancelUrl,
        TestValues.amount,
        TestValues.orderId,
        TestValues.description,
        orderDescription
    )
        .splitting(splittingMerchantId, splittingAmount)
        .build();
    assertEquals(16, formContainer.getFields().size());
    checkSplittingParameters(formContainer.getFields(), splittingMerchantIdString, splittingAmountString);
  }

  private void checkSplittingParameters(List<NameValuePair> parameterList, String splittingMerchantId, String splittingAmount) {
    Helper.assertFieldValueExists(parameterList, FormBuilderConstants.SPH_SPLITTING_MERCHANT_ID, splittingMerchantId);
    Helper.assertFieldValueExists(parameterList, FormBuilderConstants.SPH_SPLITTING_AMOUNT, splittingAmount);
  }
}
