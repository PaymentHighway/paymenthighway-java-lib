package io.paymenthighway;

import org.apache.http.NameValuePair;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 */
public class FormContainerTest {

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
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testAddCard() {
    String method = "POST";
    String signatureKeyId = "testKey";
    String signatureSecret = "testSecret";
    String account = "test";
    String merchant = "test_merchantId";
    String serviceUrl = "https://v1-hub-staging.sph-test-solinor.com";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "https://paymenthighway.fi/dev/";
    String cancelUrl = "https://solinor.com";
    String language = "EN";

    FormBuilder formBuilder =
        new FormBuilder(method, signatureKeyId, signatureSecret,
            account, merchant, serviceUrl);

    FormContainer formContainer =
        formBuilder.generateAddCardParameters(successUrl, failureUrl, cancelUrl, language);

    String httpMethod = formContainer.getMethod();
    String actionUrl = formContainer.getAction();
    List<NameValuePair> fields = formContainer.getFields();

    assertEquals(method, httpMethod);
    assertEquals(actionUrl, serviceUrl + "/form/view/add_card");

    int i = 9; // number of tests check
    for (NameValuePair field : fields) {
      if (field.getName().equals("sph-account")) {
        i--;
        assertEquals(account, field.getValue());
      }
      if (field.getName().equals("sph-merchant")) {
        i--;
        assertEquals(merchant, field.getValue());
      }
      if (field.getName().equals("sph-request-id")) {
        i--;
        assertTrue(field.getValue().length() == 36);
      }
      if (field.getName().equals("sph-timestamp")) {
        i--;
        assertTrue(field.getValue().length() == 20);
      }
      if (field.getName().equals("sph-success-url")) {
        i--;
        assertEquals(successUrl, field.getValue());
      }
      if (field.getName().equals("sph-failure-url")) {
        i--;
        assertEquals(failureUrl, field.getValue());
      }
      if (field.getName().equals("sph-cancel-url")) {
        i--;
        assertEquals(cancelUrl, field.getValue());
      }
      if (field.getName().equals("signature")) {
        i--;
        assertTrue(field.getValue().startsWith("SPH1"));
      }
      if (field.getName().equals("language")) {
        i--;
        assertEquals(language, field.getValue());
      }
    }
    // check that all fields were tested
    assertEquals(0, i);
  }

  /**
   * Test add card with all the optional parameters present.
   */
  @Test
  public void testAddCard2() {
    String method = "POST";
    String signatureKeyId = "testKey";
    String signatureSecret = "testSecret";
    String account = "test";
    String merchant = "test_merchantId";
    String serviceUrl = "https://v1-hub-staging.sph-test-solinor.com";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "https://paymenthighway.fi/dev/";
    String cancelUrl = "https://solinor.com";
    String language = "EN";
    Boolean acceptCvcRequired = true;
    Boolean skipFormNotifications = true;
    Boolean exitIframeOnResult = true;
    Boolean exitIframeOn3ds = true;

    FormBuilder formBuilder =
            new FormBuilder(method, signatureKeyId, signatureSecret,
                    account, merchant, serviceUrl);

    FormContainer formContainer =
            formBuilder.generateAddCardParameters(successUrl, failureUrl, cancelUrl, language,
                    acceptCvcRequired, skipFormNotifications, exitIframeOnResult, exitIframeOn3ds);

    String httpMethod = formContainer.getMethod();
    String actionUrl = formContainer.getAction();
    List<NameValuePair> fields = formContainer.getFields();

    assertEquals(method, httpMethod);
    assertEquals(actionUrl, serviceUrl + "/form/view/add_card");

    int i = 13; // number of tests check
    for (NameValuePair field : fields) {
      if (field.getName().equals("sph-account")) {
        i--;
        assertEquals(account, field.getValue());
      }
      if (field.getName().equals("sph-merchant")) {
        i--;
        assertEquals(merchant, field.getValue());
      }
      if (field.getName().equals("sph-request-id")) {
        i--;
        assertTrue(field.getValue().length() == 36);
      }
      if (field.getName().equals("sph-timestamp")) {
        i--;
        assertTrue(field.getValue().length() == 20);
      }
      if (field.getName().equals("sph-success-url")) {
        i--;
        assertEquals(successUrl, field.getValue());
      }
      if (field.getName().equals("sph-failure-url")) {
        i--;
        assertEquals(failureUrl, field.getValue());
      }
      if (field.getName().equals("sph-cancel-url")) {
        i--;
        assertEquals(cancelUrl, field.getValue());
      }
      if (field.getName().equals("signature")) {
        i--;
        assertTrue(field.getValue().startsWith("SPH1"));
      }
      if (field.getName().equals("language")) {
        i--;
        assertEquals(language, field.getValue());
      }
      if (field.getName().equals("sph-accept-cvc-required")) {
        i--;
        assertEquals(acceptCvcRequired.toString(), field.getValue());
      }
      if (field.getName().equals("sph-skip-form-notifications")) {
        i--;
        assertEquals(skipFormNotifications.toString(), field.getValue());
      }
      if (field.getName().equals("sph-exit-iframe-on-result")) {
        i--;
        assertEquals(exitIframeOnResult.toString(), field.getValue());
      }
      if (field.getName().equals("sph-exit-iframe-on-three-d-secure")) {
        i--;
        assertEquals(exitIframeOn3ds.toString(), field.getValue());
      }
    }
    // check that all fields were tested
    assertEquals(0, i);
  }

  /**
   * Test without optional parameters.
   */
  @Test
  public void testPayment() {
    String method = "POST";
    String signatureKeyId = "testKey";
    String signatureSecret = "testSecret";
    String account = "test";
    String merchant = "test_merchantId";
    String serviceUrl = "https://v1-hub-staging.sph-test-solinor.com";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "https://paymenthighway.fi/dev/";
    String cancelUrl = "https://solinor.com";
    String language = "EN";

    FormBuilder formBuilder =
        new FormBuilder(method, signatureKeyId, signatureSecret,
            account, merchant, serviceUrl);

    String amount = "1990";
    String currency = "EUR";
    String orderId = "1000123A";
    String description = "A Box of Dreams. 19,90€";

    FormContainer formContainer = formBuilder.generatePaymentParameters(
            successUrl, failureUrl, cancelUrl, language, amount, currency, orderId, description);

    String httpMethod = formContainer.getMethod();
    String actionUrl = formContainer.getAction();
    List<NameValuePair> fields = formContainer.getFields();

    assertEquals(method, httpMethod);
    assertEquals(actionUrl, serviceUrl + "/form/view/pay_with_card");

    int i = 13; // number of tests check
    for (NameValuePair field : fields) {
      if (field.getName().equals("sph-account")) {
        i--;
        assertEquals(account, field.getValue());
      }
      if (field.getName().equals("sph-merchant")) {
        i--;
        assertEquals(merchant, field.getValue());
      }
      if (field.getName().equals("sph-request-id")) {
        i--;
        assertTrue(field.getValue().length() == 36);
      }
      if (field.getName().equals("sph-timestamp")) {
        i--;
        assertTrue(field.getValue().length() == 20);
      }
      if (field.getName().equals("sph-success-url")) {
        i--;
        assertEquals(successUrl, field.getValue());
      }
      if (field.getName().equals("sph-failure-url")) {
        i--;
        assertEquals(failureUrl, field.getValue());
      }
      if (field.getName().equals("sph-cancel-url")) {
        i--;
        assertEquals(cancelUrl, field.getValue());
      }
      if (field.getName().equals("signature")) {
        i--;
        assertTrue(field.getValue().startsWith("SPH1"));
      }
      if (field.getName().equals("language")) {
        i--;
        assertEquals(language, field.getValue());
      }
      if (field.getName().equals("sph-amount")) {
        i--;
        assertEquals(amount, field.getValue());
      }
      if (field.getName().equals("sph-currency")) {
        i--;
        assertEquals(currency, field.getValue());
      }
      if (field.getName().equals("sph-order")) {
        i--;
        assertEquals(orderId, field.getValue());
      }
      if (field.getName().equals("description")) {
        i--;
        assertEquals(description, field.getValue());
      }
    }
    // check that all fields were tested
    assertEquals(0, i);
  }

  /**
   * Test with all the optional parameters present.
   */
  @Test
  public void testPayment2() {
    String method = "POST";
    String signatureKeyId = "testKey";
    String signatureSecret = "testSecret";
    String account = "test";
    String merchant = "test_merchantId";
    String serviceUrl = "https://v1-hub-staging.sph-test-solinor.com";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "https://paymenthighway.fi/dev/";
    String cancelUrl = "https://solinor.com";
    String language = "EN";

    FormBuilder formBuilder =
            new FormBuilder(method, signatureKeyId, signatureSecret,
                    account, merchant, serviceUrl);

    String amount = "1990";
    String currency = "EUR";
    String orderId = "1000123A";
    String description = "A Box of Dreams. 19,90€";
    Boolean skipFormNotifications = true;
    Boolean exitIframeOnResult = true;
    Boolean exitIframeOn3ds = true;

    FormContainer formContainer = formBuilder.generatePaymentParameters(
            successUrl, failureUrl, cancelUrl, language, amount, currency, orderId, description,
            skipFormNotifications, exitIframeOnResult, exitIframeOn3ds);

    String httpMethod = formContainer.getMethod();
    String actionUrl = formContainer.getAction();
    List<NameValuePair> fields = formContainer.getFields();

    assertEquals(method, httpMethod);
    assertEquals(actionUrl, serviceUrl + "/form/view/pay_with_card");

    int i = 16; // number of tests check
    for (NameValuePair field : fields) {
      if (field.getName().equals("sph-account")) {
        i--;
        assertEquals(account, field.getValue());
      }
      if (field.getName().equals("sph-merchant")) {
        i--;
        assertEquals(merchant, field.getValue());
      }
      if (field.getName().equals("sph-request-id")) {
        i--;
        assertTrue(field.getValue().length() == 36);
      }
      if (field.getName().equals("sph-timestamp")) {
        i--;
        assertTrue(field.getValue().length() == 20);
      }
      if (field.getName().equals("sph-success-url")) {
        i--;
        assertEquals(successUrl, field.getValue());
      }
      if (field.getName().equals("sph-failure-url")) {
        i--;
        assertEquals(failureUrl, field.getValue());
      }
      if (field.getName().equals("sph-cancel-url")) {
        i--;
        assertEquals(cancelUrl, field.getValue());
      }
      if (field.getName().equals("signature")) {
        i--;
        assertTrue(field.getValue().startsWith("SPH1"));
      }
      if (field.getName().equals("language")) {
        i--;
        assertEquals(language, field.getValue());
      }
      if (field.getName().equals("sph-amount")) {
        i--;
        assertEquals(amount, field.getValue());
      }
      if (field.getName().equals("sph-currency")) {
        i--;
        assertEquals(currency, field.getValue());
      }
      if (field.getName().equals("sph-order")) {
        i--;
        assertEquals(orderId, field.getValue());
      }
      if (field.getName().equals("description")) {
        i--;
        assertEquals(description, field.getValue());
      }
      if (field.getName().equals("sph-skip-form-notifications")) {
        i--;
        assertEquals(skipFormNotifications.toString(), field.getValue());
      }
      if (field.getName().equals("sph-exit-iframe-on-result")) {
        i--;
        assertEquals(exitIframeOnResult.toString(), field.getValue());
      }
      if (field.getName().equals("sph-exit-iframe-on-three-d-secure")) {
        i--;
        assertEquals(exitIframeOn3ds.toString(), field.getValue());
      }
    }
    // check that all fields were tested
    assertEquals(0, i);
  }

  interface AssertCallback {
    void test(String value);
  }

  // use lambda when switching finally to Java 1.8 (can be used for tests, but Idea has bug:
  // https://youtrack.jetbrains.com/issue/IDEA-85478
  private void assertField(List<NameValuePair> fields, String key, AssertCallback callback) {
    NameValuePair found = null;
    for (NameValuePair field: fields) {
      if (field.getName().equals(key)) {
        found = field;
        callback.test(field.getValue());
      }
    }

    if (found == null) {
      Assert.fail(String.format("Could not find key '%s' from field list", key));
    } else {
      fields.remove(found);
    }
  }

  /**
   * Test without optional parameters.
   */
  @Test
  public void testAddCardAndPayment() {
    final String method = "POST";
    final String signatureKeyId = "testKey";
    final String signatureSecret = "testSecret";
    final String account = "test";
    final String merchant = "test_merchantId";
    final String serviceUrl = "https://v1-hub-staging.sph-test-solinor.com";
    final String successUrl = "https://www.paymenthighway.fi/";
    final String failureUrl = "https://paymenthighway.fi/dev/";
    final String cancelUrl = "https://solinor.com";
    final String language = "EN";

    FormBuilder formBuilder = new FormBuilder(method, signatureKeyId, signatureSecret, account, merchant, serviceUrl);

    final String amount = "1990";
    final String currency = "EUR";
    final String orderId = "1000123A";
    final String description = "A Box of Dreams. 19,90€";

    FormContainer formContainer = formBuilder.paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .tokenize(true)
      .build();

    String httpMethod = formContainer.getMethod();
    String actionUrl = formContainer.getAction();
    List<NameValuePair> fields = formContainer.getFields();

    assertEquals(method, httpMethod);
    assertEquals(actionUrl, serviceUrl + "/form/view/pay_with_card");

    assertField(fields, "sph-account", new AssertCallback() {
      public void test(String value) {
        assertEquals(account, value);
      }
    });
    assertField(fields, "sph-merchant", new AssertCallback() {
      public void test(String value) {
        assertEquals(merchant, value);
      }
    });
    assertField(fields, "sph-request-id", new AssertCallback() {
      public void test(String value) {
        assertTrue(value.length() == 36);
      }
    });
    assertField(fields, "sph-timestamp", new AssertCallback() {
      public void test(String value) {
        assertTrue(value.length() == 20);
      }
    });
    assertField(fields, "sph-success-url", new AssertCallback() {
      public void test(String value) {
        assertEquals(successUrl, value);
      }
    });
    assertField(fields, "sph-failure-url", new AssertCallback() {
      public void test(String value) {
        assertEquals(failureUrl, value);
      }
    });
    assertField(fields, "sph-cancel-url", new AssertCallback() {
      public void test(String value) {
        assertEquals(cancelUrl, value);
      }
    });
    assertField(fields, "sph-tokenize", new AssertCallback() {
      public void test(String value) {
        assertEquals("true", value);
      }
    });
    assertField(fields, "signature", new AssertCallback() {
      public void test(String value) {
        assertTrue(value.startsWith("SPH1"));
      }
    });
    assertField(fields, "language", new AssertCallback() {
      public void test(String value) {
        assertEquals(language, value);
      }
    });
    assertField(fields, "sph-amount", new AssertCallback() {
      public void test(String value) {
        assertEquals(amount, value);
      }
    });
    assertField(fields, "sph-currency", new AssertCallback() {
      public void test(String value) {
        assertEquals(currency, value);
      }
    });
    assertField(fields, "sph-order", new AssertCallback() {
      public void test(String value) {
        assertEquals(orderId, value);
      }
    });
    assertField(fields, "description", new AssertCallback() {
      public void test(String value) {
        assertEquals(description, value);
      }
    });
    assertField(fields, "sph-api-version", new AssertCallback() {
      public void test(String value) {
        assertEquals(Constants.API_VERSION, value);
      }
    });

    assertTrue( "There was unexpected fields in field list", fields.isEmpty());
  }

  /**
   * Test with the optional parameters present.
   */
  @Test
  public void testAddCardAndPayment2() {
    final String method = "POST";
    final String signatureKeyId = "testKey";
    final String signatureSecret = "testSecret";
    final String account = "test";
    final String merchant = "test_merchantId";
    final String serviceUrl = "https://v1-hub-staging.sph-test-solinor.com";
    final String successUrl = "https://www.paymenthighway.fi/";
    final String failureUrl = "https://paymenthighway.fi/dev/";
    final String cancelUrl = "https://solinor.com";
    final String language = "EN";

    FormBuilder formBuilder = new FormBuilder(method, signatureKeyId, signatureSecret, account, merchant, serviceUrl);

    final String amount = "1990";
    final String currency = "EUR";
    final String orderId = "1000123A";
    final String description = "A Box of Dreams. 19,90€";
    final Boolean skipFormNotifications = true;
    final Boolean exitIframeOnResult = true;
    final Boolean exitIframeOn3ds = true;

    FormContainer formContainer = formBuilder.generateAddCardAndPaymentParameters(
            successUrl, failureUrl, cancelUrl, language, amount, currency, orderId, description,
            skipFormNotifications, exitIframeOnResult, exitIframeOn3ds);

    String httpMethod = formContainer.getMethod();
    String actionUrl = formContainer.getAction();
    List<NameValuePair> fields = formContainer.getFields();

    assertEquals(method, httpMethod);
    assertEquals(actionUrl, serviceUrl + "/form/view/pay_with_card");

    assertField(fields, "sph-account", new AssertCallback() {
      public void test(String value) {
        assertEquals(account, value);
      }
    });
    assertField(fields, "sph-merchant", new AssertCallback() {
      public void test(String value) {
        assertEquals(merchant, value);
      }
    });
    assertField(fields, "sph-request-id", new AssertCallback() {
      public void test(String value) {
        assertTrue(value.length() == 36);
      }
    });
    assertField(fields, "sph-timestamp", new AssertCallback() {
      public void test(String value) {
        assertTrue(value.length() == 20);
      }
    });
    assertField(fields, "sph-success-url", new AssertCallback() {
      public void test(String value) {
        assertEquals(successUrl, value);
      }
    });
    assertField(fields, "sph-failure-url", new AssertCallback() {
      public void test(String value) {
        assertEquals(failureUrl, value);
      }
    });
    assertField(fields, "sph-cancel-url", new AssertCallback() {
      public void test(String value) {
        assertEquals(cancelUrl, value);
      }
    });
    assertField(fields, "sph-tokenize", new AssertCallback() {
      public void test(String value) {
        assertEquals("true", value);
      }
    });
    assertField(fields, "signature", new AssertCallback() {
      public void test(String value) {
        assertTrue(value.startsWith("SPH1"));
      }
    });
    assertField(fields, "language", new AssertCallback() {
      public void test(String value) {
        assertEquals(language, value);
      }
    });
    assertField(fields, "sph-amount", new AssertCallback() {
      public void test(String value) {
        assertEquals(amount, value);
      }
    });
    assertField(fields, "sph-currency", new AssertCallback() {
      public void test(String value) {
        assertEquals(currency, value);
      }
    });
    assertField(fields, "sph-order", new AssertCallback() {
      public void test(String value) {
        assertEquals(orderId, value);
      }
    });
    assertField(fields, "description", new AssertCallback() {
      public void test(String value) {
        assertEquals(description, value);
      }
    });
    assertField(fields, "sph-skip-form-notifications", new AssertCallback() {
      public void test(String value) {
        assertEquals(skipFormNotifications.toString(), value);
      }
    });
    assertField(fields, "sph-exit-iframe-on-result", new AssertCallback() {
      public void test(String value) {
        assertEquals(exitIframeOnResult.toString(), value);
      }
    });
    assertField(fields, "sph-exit-iframe-on-three-d-secure", new AssertCallback() {
      public void test(String value) {
        assertEquals(exitIframeOn3ds.toString(), value);
      }
    });
    assertField(fields, "sph-api-version", new AssertCallback() {
      public void test(String value) {
        assertEquals(Constants.API_VERSION, value);
      }
    });

    assertTrue( "There was unexpected fields in field list", fields.isEmpty());
  }

  /**
   * Test without optional parameters.
   */
  @Test
  public void testPaymentWithTokenAndCvc() {
    String method = "POST";
    String signatureKeyId = "testKey";
    String signatureSecret = "testSecret";
    String account = "test";
    String merchant = "test_merchantId";
    String serviceUrl = "https://v1-hub-staging.sph-test-solinor.com";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "https://paymenthighway.fi/dev/";
    String cancelUrl = "https://solinor.com";
    String language = "EN";

    FormBuilder formBuilder =
            new FormBuilder(method, signatureKeyId, signatureSecret,
                    account, merchant, serviceUrl);

    String amount = "1990";
    String currency = "EUR";
    String orderId = "1000123A";
    String description = "A Box of Dreams. 19,90€";
    UUID token = UUID.fromString("71435029-fbb6-4506-aa86-8529efb640b0");

    FormContainer formContainer = formBuilder.generatePayWithTokenAndCvcParameters(
            token, successUrl, failureUrl, cancelUrl, language, amount, currency, orderId, description);

    String httpMethod = formContainer.getMethod();
    String actionUrl = formContainer.getAction();
    List<NameValuePair> fields = formContainer.getFields();

    assertEquals(method, httpMethod);
    assertEquals(actionUrl, serviceUrl + "/form/view/pay_with_token_and_cvc");

    int i = 13; // number of tests check
    for (NameValuePair field : fields) {
      if (field.getName().equals("sph-account")) {
        i--;
        assertEquals(account, field.getValue());
      }
      if (field.getName().equals("sph-merchant")) {
        i--;
        assertEquals(merchant, field.getValue());
      }
      if (field.getName().equals("sph-request-id")) {
        i--;
        assertTrue(field.getValue().length() == 36);
      }
      if (field.getName().equals("sph-timestamp")) {
        i--;
        assertTrue(field.getValue().length() == 20);
      }
      if (field.getName().equals("sph-success-url")) {
        i--;
        assertEquals(successUrl, field.getValue());
      }
      if (field.getName().equals("sph-failure-url")) {
        i--;
        assertEquals(failureUrl, field.getValue());
      }
      if (field.getName().equals("sph-cancel-url")) {
        i--;
        assertEquals(cancelUrl, field.getValue());
      }
      if (field.getName().equals("signature")) {
        i--;
        assertTrue(field.getValue().startsWith("SPH1"));
      }
      if (field.getName().equals("language")) {
        i--;
        assertEquals(language, field.getValue());
      }
      if (field.getName().equals("sph-amount")) {
        i--;
        assertEquals(amount, field.getValue());
      }
      if (field.getName().equals("sph-currency")) {
        i--;
        assertEquals(currency, field.getValue());
      }
      if (field.getName().equals("sph-order")) {
        i--;
        assertEquals(orderId, field.getValue());
      }
      if (field.getName().equals("description")) {
        i--;
        assertEquals(description, field.getValue());
      }
    }
    // check that all fields were tested
    assertEquals(0, i);
  }

  /**
   * Test with the optional parameters present.
   */
  @Test
  public void testPaymentWithTokenAndCvc2() {
    String method = "POST";
    String signatureKeyId = "testKey";
    String signatureSecret = "testSecret";
    String account = "test";
    String merchant = "test_merchantId";
    String serviceUrl = "https://v1-hub-staging.sph-test-solinor.com";
    String successUrl = "https://www.paymenthighway.fi/";
    String failureUrl = "https://paymenthighway.fi/dev/";
    String cancelUrl = "https://solinor.com";
    String language = "EN";

    FormBuilder formBuilder =
            new FormBuilder(method, signatureKeyId, signatureSecret,
                    account, merchant, serviceUrl);

    String amount = "1990";
    String currency = "EUR";
    String orderId = "1000123A";
    String description = "A Box of Dreams. 19,90€";
    UUID token = UUID.fromString("71435029-fbb6-4506-aa86-8529efb640b0");
    Boolean skipFormNotifications = true;
    Boolean exitIframeOnResult = true;
    Boolean exitIframeOn3ds = true;

    FormContainer formContainer = formBuilder.generatePayWithTokenAndCvcParameters(
            token, successUrl, failureUrl, cancelUrl, language, amount, currency, orderId, description,
            skipFormNotifications, exitIframeOnResult, exitIframeOn3ds);

    String httpMethod = formContainer.getMethod();
    String actionUrl = formContainer.getAction();
    List<NameValuePair> fields = formContainer.getFields();

    assertEquals(method, httpMethod);
    assertEquals(actionUrl, serviceUrl + "/form/view/pay_with_token_and_cvc");

    int i = 16; // number of tests check
    for (NameValuePair field : fields) {
      if (field.getName().equals("sph-account")) {
        i--;
        assertEquals(account, field.getValue());
      }
      if (field.getName().equals("sph-merchant")) {
        i--;
        assertEquals(merchant, field.getValue());
      }
      if (field.getName().equals("sph-request-id")) {
        i--;
        assertTrue(field.getValue().length() == 36);
      }
      if (field.getName().equals("sph-timestamp")) {
        i--;
        assertTrue(field.getValue().length() == 20);
      }
      if (field.getName().equals("sph-success-url")) {
        i--;
        assertEquals(successUrl, field.getValue());
      }
      if (field.getName().equals("sph-failure-url")) {
        i--;
        assertEquals(failureUrl, field.getValue());
      }
      if (field.getName().equals("sph-cancel-url")) {
        i--;
        assertEquals(cancelUrl, field.getValue());
      }
      if (field.getName().equals("signature")) {
        i--;
        assertTrue(field.getValue().startsWith("SPH1"));
      }
      if (field.getName().equals("language")) {
        i--;
        assertEquals(language, field.getValue());
      }
      if (field.getName().equals("sph-amount")) {
        i--;
        assertEquals(amount, field.getValue());
      }
      if (field.getName().equals("sph-currency")) {
        i--;
        assertEquals(currency, field.getValue());
      }
      if (field.getName().equals("sph-order")) {
        i--;
        assertEquals(orderId, field.getValue());
      }
      if (field.getName().equals("description")) {
        i--;
        assertEquals(description, field.getValue());
      }
      if (field.getName().equals("sph-skip-form-notifications")) {
        i--;
        assertEquals(skipFormNotifications.toString(), field.getValue());
      }
      if (field.getName().equals("sph-exit-iframe-on-result")) {
        i--;
        assertEquals(exitIframeOnResult.toString(), field.getValue());
      }
      if (field.getName().equals("sph-exit-iframe-on-three-d-secure")) {
        i--;
        assertEquals(exitIframeOn3ds.toString(), field.getValue());
      }
    }
    // check that all fields were tested
    assertEquals(0, i);
  }
}
