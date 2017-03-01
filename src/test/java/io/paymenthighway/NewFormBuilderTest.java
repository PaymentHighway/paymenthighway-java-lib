package io.paymenthighway;


import io.paymenthighway.connect.FormAPIConnection;
import org.apache.http.NameValuePair;
import org.junit.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


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
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test with acceptCvcRequired set to false.
     */
    @Test
    public void testAddCardParameters() {

        Boolean acceptCvcRequired = false;

        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.addCardParameters(this.successUrl, this.failureUrl, this.cancelUrl)
            .acceptCvcRequired(acceptCvcRequired)
            .build();

        List<NameValuePair> nameValuePairs = formContainer.getFields();

        // test that the result has a signature
        Iterator<NameValuePair> it = nameValuePairs.iterator();
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
     * Test with acceptCvcRequired set to true.
     */
    @Test
    public void testAddCardParameters2() {

        Boolean acceptCvcRequired = true;

        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.addCardParameters(this.successUrl, this.failureUrl, this.cancelUrl)
            .acceptCvcRequired(acceptCvcRequired)
            .language(this.language)
            .build();

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

        Boolean acceptCvcRequired = true;
        Boolean skipFormNotifications = true;
        Boolean exitIframeOnResult = true;
        Boolean exitIframeOn3ds = true;

        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.addCardParameters(this.successUrl, this.failureUrl, this.cancelUrl)
            .acceptCvcRequired(acceptCvcRequired)
            .skipFormNotifications(skipFormNotifications)
            .exitIframeOnResult(exitIframeOnResult)
            .exitIframeOn3ds(exitIframeOn3ds)
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

    @Test
    public void testAddCardUse3ds() {

        Boolean use3ds = false;

        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.addCardParameters(this.successUrl, this.failureUrl, this.cancelUrl)
            .language(this.language)
            .use3ds(use3ds)
            .build();

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
     * Test with only the mandatory parameters.
     */
    @Test
    public void testAddCardParameters4() {

        // create the payment highway request parameters
        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.addCardParameters(this.successUrl, this.failureUrl, this.cancelUrl).build();

        List<NameValuePair> nameValuePairs = formContainer.getFields();

        // test that the result has a signature
        Iterator<NameValuePair> it = nameValuePairs.iterator();
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

    @Test
    public void testPaymentParameters() {

        // create the payment highway request parameters
        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.paymentParameters(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description)
            .language(this.language)
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
     * Test with only the mandatory parameters.
     */

    @Test
    public void testGetPaymentParameters2() {

        // create the payment highway request parameters
        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.paymentParameters(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description)
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
        Boolean skipFormNotifications = true;
        Boolean exitIframeOnResult = true;
        Boolean exitIframeOn3ds = true;

        // create the payment highway request parameters
        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.paymentParameters(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description)
            .skipFormNotifications(skipFormNotifications)
            .language(this.language)
            .exitIframeOnResult(exitIframeOnResult)
            .exitIframeOn3ds(exitIframeOn3ds)
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

        Boolean use3ds = true;

        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.paymentParameters(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description)
            .use3ds(use3ds)
            .language(this.language)
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

        // create the payment highway request parameters
        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.addCardAndPaymentParameters(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description)
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

        // create the payment highway request parameters
        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.addCardAndPaymentParameters(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description)
            .language(this.language)
            .build();

        // test that Payment Highway accepts this as a request
        FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

        String response = null;
        try {
            response = formApi.addCardAndPayRequest(formContainer.getFields());
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

        Boolean skipFormNotifications = true;
        Boolean exitIframeOnResult = true;
        Boolean exitIframeOn3ds = true;

        // create the payment highway request parameters
        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.addCardAndPaymentParameters(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description)
            .skipFormNotifications(skipFormNotifications)
            .exitIframeOnResult(exitIframeOnResult)
            .exitIframeOn3ds(exitIframeOn3ds)
            .language(this.language)
            .build();

        // test that Payment Highway accepts this as a request
        FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

        String response = null;
        try {
            response = formApi.addCardAndPayRequest(formContainer.getFields());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(response);
        assertTrue(response.contains("card_number_formatted"));
    }


    @Test
    public void testGetAddCardAndPaymentWithUse3ds() {

        Boolean skipFormNotifications = true;
        Boolean exitIframeOnResult = true;
        Boolean exitIframeOn3ds = true;
        Boolean use3ds = true;

        // create the payment highway request parameters
        FormBuilder formBuilder = new FormBuilder(this.method,
            this.signatureKeyId, this.signatureSecret, this.account, this.merchant,
            this.serviceUrl);

        FormContainer formContainer = formBuilder.addCardAndPaymentParameters(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description)
            .skipFormNotifications(skipFormNotifications)
            .exitIframeOnResult(exitIframeOnResult)
            .exitIframeOn3ds(exitIframeOn3ds)
            .use3ds(use3ds)
            .build();

        // test that Payment Highway accepts this as a request
        FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);

        String response = null;
        try {
            response = formApi.addCardAndPayRequest(formContainer.getFields());
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

        UUID token = UUID.fromString("71435029-fbb6-4506-aa86-8529efb640b0");

        // create the payment highway request parameters
        FormBuilder formBuilder = new FormBuilder(this.method,
            this.signatureKeyId, this.signatureSecret, this.account, this.merchant,
            this.serviceUrl);

        FormContainer formContainer = formBuilder.payWithTokenAndCvcParameters(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description, token)
            .language(this.language)
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

    /**
     * Test with optional parameters present.
     */

    @Test
    public void testGetPayWithTokenAndCvcParameters2() {

        UUID token = UUID.fromString("71435029-fbb6-4506-aa86-8529efb640b0");
        Boolean skipFormNotifications = true;
        Boolean exitIframeOnResult = true;
        Boolean exitIframeOn3ds = true;

        // create the payment highway request parameters
        FormBuilder formBuilder = new FormBuilder(this.method, this.signatureKeyId, this.signatureSecret, this.account,
            this.merchant, this.serviceUrl);

        FormContainer formContainer = formBuilder.payWithTokenAndCvcParameters(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description, token)
            .skipFormNotifications(skipFormNotifications)
            .exitIframeOnResult(exitIframeOnResult)
            .exitIframeOn3ds(exitIframeOn3ds)
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

        UUID token = UUID.fromString("71435029-fbb6-4506-aa86-8529efb640b0");
        Boolean skipFormNotifications = false;
        Boolean exitIframeOn3ds = false;
        Boolean use3ds = false;

        // create the payment highway request parameters
        FormBuilder formBuilder = new FormBuilder(this.method,
            this.signatureKeyId, this.signatureSecret, this.account, this.merchant,
            this.serviceUrl);

        FormContainer formContainer = formBuilder.payWithTokenAndCvcParameters(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description, token)
            .language(this.language)
            .skipFormNotifications(skipFormNotifications)
            .exitIframeOn3ds(exitIframeOn3ds)
            .use3ds(use3ds)
            .build();

        // test that Payment Highway accepts this as a request
        FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl,
            this.signatureKeyId, this.signatureSecret);

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

        FormBuilder formBuilder = new FormBuilder(this.method,
            this.signatureKeyId, this.signatureSecret, this.account, this.merchant,
            this.serviceUrl);
        FormContainer formContainer = formBuilder.mobilePayParametersBuilder(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description)
            .build();
        assertTrue(formContainer.getFields().size() == 13);
    }


    @Test
    public void testMobilePayFormWithOptionalParameters() {

        String logoUrl = "https://foo.bar";
        Boolean exitIframeOnResult = true;
        String phoneNumber = "+35844123465";
        String shopName = "Jaskan kello";
        String submerchantId = "submerchantId";
        String submerchantName = "submerchantName";

        FormBuilder formBuilder = new FormBuilder(this.method,
            this.signatureKeyId, this.signatureSecret, this.account, this.merchant,
            this.serviceUrl);
        FormContainer formContainer = formBuilder.mobilePayParametersBuilder(this.successUrl, this.failureUrl, this.cancelUrl,
            this.amount, this.currency, this.orderId, this.description)
            .language(this.language)
            .exitIframeOnResult(exitIframeOnResult)
            .shopLogoUrl(logoUrl)
            .phoneNumber(phoneNumber)
            .shopName(shopName)
            .subMerchantId(submerchantId)
            .subMerchantName(submerchantName)
            .build();
        assertTrue(formContainer.getFields().size() == 20);
    }

}

