package io.paymenthighway;

import io.paymenthighway.connect.FormAPIConnection;
import io.paymenthighway.formBuilders.FormBuilderConstants;
import io.paymenthighway.formBuilders.PaymentParameters;
import org.apache.http.NameValuePair;
import org.junit.*;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class NewFormBuilderTest {

  Properties props = null;
  private String serviceUrl;
  private String signatureKeyId;
  private String signatureSecret;
  private String method;
  private String account;
  private String merchant;
  private String successUrl;
  private String failureUrl;
  private String cancelUrl;
  private String amount;
  private String currency;
  private String orderId;
  private String language;
  private String description;
  private String webhookSuccessUrl;
  private String webhookFailureUrl;
  private String webhookCancelUrl;
  private Integer webhookDelay;
  private UUID token;
  private FormBuilder formBuilder;
  private FormAPIConnection formApi;

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
    try {
      this.props = PaymentHighwayUtility.getProperties();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.serviceUrl = this.props.getProperty("service_url");
    this.signatureKeyId = this.props.getProperty("signature_key_id");
    this.signatureSecret = this.props.getProperty("signature_secret");
    this.method = "POST";
    this.account = "test";
    this.merchant = "test_merchantId";
    this.successUrl = "https://www.paymenthighway.fi/";
    this.failureUrl = "https://paymenthighway.fi/index-en.html";
    this.cancelUrl = "https://solinor.fi";
    this.amount = "9999";
    this.currency = "EUR";
    this.orderId = "1000123A";
    this.language = "EN";
    this.description = "this is payment description";
    this.webhookSuccessUrl = "http://example.com/?success";
    this.webhookFailureUrl = "http://example.com/?failure";
    this.webhookCancelUrl = "http://example.com/?cancel";
    this.webhookDelay = 0;
    this.token = UUID.fromString("71435029-fbb6-4506-aa86-8529efb640b0");
    this.formBuilder = new FormBuilder(
      this.method,
      this.signatureKeyId,
      this.signatureSecret,
      this.account,
      this.merchant,
      this.serviceUrl
    );
    this.formApi = new FormAPIConnection(
      this.serviceUrl,
      this.signatureKeyId,
      this.signatureSecret
    );
  }

  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test with acceptCvcRequired set to false.
   */
  @Test
  public void testAddCardParameters() {

    FormContainer formContainer = this.formBuilder.addCardParameters(this.successUrl, this.failureUrl, this.cancelUrl)
      .acceptCvcRequired(false)
      .build();

    String signature = Helper.assertFieldExists(formContainer.getFields(), FormBuilderConstants.SIGNATURE).getValue();
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));
  }

  /**
   * Test with acceptCvcRequired set to true.
   */
  @Test
  public void testAddCardParameters2() {

    FormContainer formContainer = this.formBuilder.addCardParameters(this.successUrl, this.failureUrl, this.cancelUrl)
      .acceptCvcRequired(true)
      .language(this.language)
      .build();

    String response = null;
    try {
      response = this.formApi.addCardRequest(formContainer.getFields(), false);
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

    FormContainer formContainer = this.formBuilder.addCardParameters(this.successUrl, this.failureUrl, this.cancelUrl)
      .acceptCvcRequired(true)
      .skipFormNotifications(true)
      .exitIframeOnResult(true)
      .exitIframeOn3ds(true)
      .build();

    String response = null;
    try {
      response = this.formApi.addCardRequest(formContainer.getFields(), false);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
    assertTrue(response.contains("Payment Highway"));
  }

  @Test
  public void testAddCardUse3ds() {

    FormContainer formContainer = this.formBuilder.addCardParameters(this.successUrl, this.failureUrl, this.cancelUrl)
      .use3ds(false)
      .language(this.language)
      .build();

    String response = null;
    try {
      response = this.formApi.addCardRequest(formContainer.getFields(), false);
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
    FormContainer formContainer = this.formBuilder.addCardParameters(this.successUrl, this.failureUrl, this.cancelUrl).build();

    String signature = Helper.assertFieldExists(formContainer.getFields(), FormBuilderConstants.SIGNATURE).getValue();
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));
  }

  @Test
  public void testPaymentParameters() {
    FormContainer formContainer = generatePaymentParameters()
      .language(this.language)
      .build();

    String signature = Helper.assertFieldExists(formContainer.getFields(), FormBuilderConstants.SIGNATURE).getValue();
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));
  }

  /**
   * Test with only the mandatory parameters.
   */

  @Test
  public void testGetPaymentParameters2() {
    FormContainer formContainer = generatePaymentParameters()
      .build();

    String response = null;
    try {
      response = this.formApi.paymentRequest(formContainer.getFields(), false);
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

    FormContainer formContainer = generatePaymentParameters()
      .skipFormNotifications(true)
      .exitIframeOnResult(true)
      .exitIframeOn3ds(true)
      .language(this.language)
      .referenceNumber("1313")
      .build();

    String response = null;
    try {
      response = this.formApi.paymentRequest(formContainer.getFields(), false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }


  @Test
  public void testGetPaymentFormWithUse3ds() {

    FormContainer formContainer = generatePaymentParameters()
      .use3ds(true)
      .language(this.language)
      .build();

    String response = null;
    try {
      response = this.formApi.paymentRequest(formContainer.getFields(), false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }


  @Test
  public void testGetAddCardAndPaymentParameters() {
    FormContainer formContainer = generatePaymentParameters()
      .tokenize(true)
      .build();

    String signature = Helper.assertFieldExists(formContainer.getFields(), FormBuilderConstants.SIGNATURE).getValue();
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));
  }


  /**
   * Test without optional parameters.
   */

  @Test
  public void testGetAddCardAndPaymentParameters2() {
    FormContainer formContainer = generatePaymentParameters()
      .tokenize(true)
      .language(this.language)
      .build();

    String response = null;
    try {
      response = this.formApi.paymentRequest(formContainer.getFields(), false);
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
    FormContainer formContainer = generatePaymentParameters()
      .skipFormNotifications(true)
      .exitIframeOnResult(true)
      .exitIframeOn3ds(true)
      .tokenize(true)
      .language(this.language)
      .build();

    String response = null;
    try {
      response = this.formApi.paymentRequest(formContainer.getFields(), false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }


  @Test
  public void testGetAddCardAndPaymentWithUse3ds() {
    FormContainer formContainer = generatePaymentParameters()
      .skipFormNotifications(true)
      .exitIframeOnResult(true)
      .exitIframeOn3ds(true)
      .use3ds(true)
      .build();

    String response = null;
    try {
      response = this.formApi.paymentRequest(formContainer.getFields(), false);
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
    FormContainer formContainer = this.formBuilder.payWithTokenAndCvcParameters(this.successUrl, this.failureUrl, this.cancelUrl,
      this.amount, this.currency, this.orderId, this.description, this.token)
      .language(this.language)
      .build();

    String response = null;
    try {
      response = this.formApi.payWithTokenAndCvcRequest(formContainer.getFields(), false);
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
  public void testGetPayWithTokenAndCvcParameters2() {

    FormContainer formContainer = this.formBuilder.payWithTokenAndCvcParameters(this.successUrl, this.failureUrl, this.cancelUrl,
      this.amount, this.currency, this.orderId, this.description, this.token)
      .skipFormNotifications(true)
      .exitIframeOnResult(true)
      .exitIframeOn3ds(true)
      .referenceNumber("1313")
      .build();

    String response = null;
    try {
      response = this.formApi.payWithTokenAndCvcRequest(formContainer.getFields(), false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }

  @Test
  public void testGetPayWithTokenAndCvcWithUse3ds() {

    FormContainer formContainer = this.formBuilder.payWithTokenAndCvcParameters(this.successUrl, this.failureUrl, this.cancelUrl,
      this.amount, this.currency, this.orderId, this.description, this.token)
      .skipFormNotifications(false)
      .exitIframeOn3ds(false)
      .use3ds(false)
      .language(this.language)
      .build();

    String response = null;
    try {
      response = this.formApi.payWithTokenAndCvcRequest(formContainer.getFields(), false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertNotNull(response);
    assertTrue(response.contains("card_number_formatted"));
  }


  @Test
  public void testMobilePayForm() {
    FormContainer formContainer = this.formBuilder.mobilePayParametersBuilder(this.successUrl, this.failureUrl, this.cancelUrl,
      this.amount, this.currency, this.orderId, this.description)
      .build();
    assertEquals(13, formContainer.getFields().size());
  }


  @Test
  public void testMobilePayFormWithOptionalParameters() {
    String logoUrl = "https://foo.bar";
    String phoneNumber = "+35844123465";
    String shopName = "Jaskan kello";
    String submerchantId = "submerchantId";
    String submerchantName = "submerchantName";

    FormContainer formContainer = this.formBuilder.mobilePayParametersBuilder(this.successUrl, this.failureUrl, this.cancelUrl,
      this.amount, this.currency, this.orderId, this.description)
      .exitIframeOnResult(true)
      .shopLogoUrl(logoUrl)
      .phoneNumber(phoneNumber)
      .shopName(shopName)
      .subMerchantId(submerchantId)
      .subMerchantName(submerchantName)
      .language(this.language)
      .referenceNumber("1313")
      .build();

    assertEquals(21, formContainer.getFields().size());
  }

  @Test
  public void testAddCardWebhookParameters() {
    FormContainer formContainer = this.formBuilder.addCardParameters(this.successUrl, this.failureUrl, this.cancelUrl)
      .webhookSuccessUrl(this.webhookSuccessUrl)
      .webhookFailureUrl(this.webhookFailureUrl)
      .webhookCancelUrl(this.webhookCancelUrl)
      .webhookDelay(this.webhookDelay)
      .language(this.language)
      .build();

    String signature = Helper.assertFieldExists(formContainer.getFields(), FormBuilderConstants.SIGNATURE).getValue();
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));

    this.validateWebhookParameters(formContainer.getFields(), false);
  }

  @Test
  public void testPaymentWebhookParameters() {
    FormContainer formContainer = generatePaymentParameters()
      .webhookSuccessUrl(this.webhookSuccessUrl)
      .webhookFailureUrl(this.webhookFailureUrl)
      .webhookCancelUrl(this.webhookCancelUrl)
      .webhookDelay(this.webhookDelay)
      .language(this.language)
      .build();

    String signature = Helper.assertFieldExists(formContainer.getFields(), FormBuilderConstants.SIGNATURE).getValue();
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));

    this.validateWebhookParameters(formContainer.getFields(), false);
  }

  @Test
  public void testAddCardAndPaymentWebhookParameters() {
    FormContainer formContainer = generatePaymentParameters()
      .webhookSuccessUrl(this.webhookSuccessUrl)
      .webhookFailureUrl(this.webhookFailureUrl)
      .webhookCancelUrl(this.webhookCancelUrl)
      .webhookDelay(this.webhookDelay)
      .tokenize(true)
      .language(this.language)
      .build();

    String signature = Helper.assertFieldExists(formContainer.getFields(), FormBuilderConstants.SIGNATURE).getValue();
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));

    this.validateWebhookParameters(formContainer.getFields(), false);
  }

  @Test
  public void testPayWithTokenAndCvcWebhookParameters() {
    FormContainer formContainer = this.formBuilder.payWithTokenAndCvcParameters(this.successUrl, this.failureUrl, this.cancelUrl,
      this.amount, this.currency, this.orderId, this.description, this.token)
      .webhookSuccessUrl(this.webhookSuccessUrl)
      .webhookFailureUrl(this.webhookFailureUrl)
      .webhookCancelUrl(this.webhookCancelUrl)
      .webhookDelay(this.webhookDelay)
      .language(this.language)
      .build();

    String signature = Helper.assertFieldExists(formContainer.getFields(), FormBuilderConstants.SIGNATURE).getValue();
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));

    this.validateWebhookParameters(formContainer.getFields(), false);
  }

  @Test
  public void testMobilePayWebhookParameters() {
    FormContainer formContainer = this.formBuilder.mobilePayParametersBuilder(this.successUrl, this.failureUrl, this.cancelUrl,
      this.amount, this.currency, this.orderId, this.description)
      .webhookSuccessUrl(this.webhookSuccessUrl)
      .webhookFailureUrl(this.webhookFailureUrl)
      .webhookCancelUrl(this.webhookCancelUrl)
      .webhookDelay(this.webhookDelay)
      .language(this.language)
      .build();

    String signature = Helper.assertFieldExists(formContainer.getFields(), FormBuilderConstants.SIGNATURE).getValue();
    assertNotNull(signature);
    assertTrue(signature.startsWith("SPH1"));

    this.validateWebhookParameters(formContainer.getFields(), false);
  }

  private void validateWebhookParameters(List<NameValuePair> nameValuePairs, boolean ignoreDelay) {
    for (NameValuePair nameValuePair : nameValuePairs) {
      String name = nameValuePair.getName();
      if (name.equalsIgnoreCase(FormBuilderConstants.SPH_WEBHOOK_SUCCESS_URL)) {
        assertEquals(nameValuePair.getValue(), this.webhookSuccessUrl);
      } else if (name.equalsIgnoreCase(FormBuilderConstants.SPH_WEBHOOK_FAILURE_URL)) {
        assertEquals(nameValuePair.getValue(), this.webhookFailureUrl);
      } else if (name.equalsIgnoreCase(FormBuilderConstants.SPH_WEBHOOK_CANCEL_URL)) {
        assertEquals(nameValuePair.getValue(), this.webhookCancelUrl);
      } else if (name.equalsIgnoreCase(FormBuilderConstants.SPH_WEBHOOK_DELAY) && !ignoreDelay) {
        assertEquals(nameValuePair.getValue(), this.webhookDelay.toString());
      }
    }
  }

  private PaymentParameters generatePaymentParameters() {
    return this.formBuilder.paymentParameters(
      this.successUrl,
      this.failureUrl,
      this.cancelUrl,
      this.amount,
      this.currency,
      this.orderId,
      this.description
    );
  }
}
