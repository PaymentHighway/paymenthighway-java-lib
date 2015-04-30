package com.solinor.paymenthighway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpCoreContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.solinor.paymenthighway.exception.AuthenticationException;
import com.solinor.paymenthighway.model.Card;
import com.solinor.paymenthighway.model.CommitTransactionResponse;
import com.solinor.paymenthighway.model.InitTransactionResponse;
import com.solinor.paymenthighway.model.ReportResponse;
import com.solinor.paymenthighway.model.Token;
import com.solinor.paymenthighway.model.TokenizationResponse;
import com.solinor.paymenthighway.model.TransactionRequest;
import com.solinor.paymenthighway.model.TransactionResponse;
import com.solinor.paymenthighway.model.TransactionStatusResponse;

/**
 * PaymentHighway Payment API tests
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class PaymentAPITest {

	Properties props;
	private String serviceUrl;
	private String signatureKeyId;
	private String signatureSecret;
	private String account;
	private String merchant;

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

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInitTransaction() {

		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(serviceUrl, signatureKeyId,
				signatureSecret, account, merchant);
		
		InitTransactionResponse response = null;
	
		try {
			response = paymentAPI.initTransaction();
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(response.getResultCode(), "100");
		assertEquals(response.getResultMessage(), "OK");
		assertEquals(response.getId().toString().length(), 36);
	}

	@Test
	public void testDebitWithCard() {

		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account, this.merchant);

		InitTransactionResponse response = null;
	
		try {
			response = paymentAPI.initTransaction();
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		} catch (HttpResponseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth, cvc, verification);
		TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", true);
		
		TransactionResponse transactionResponse = null;
	
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		assertEquals(transactionResponse.getResult().getCode(), "100");
		assertEquals(transactionResponse.getResult().getMessage(), "OK");
	}

	@Test
	public void testCommitWithCardTransaction() {

		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account, this.merchant);

		InitTransactionResponse response = null;
	
		try {
			response = paymentAPI.initTransaction();
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		} catch (HttpResponseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth, cvc, verification);

		TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", true);
		
		TransactionResponse transactionResponse = null;
		
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		} catch (HttpResponseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		assertEquals(transactionResponse.getResult().getCode(), "100");
		assertEquals(transactionResponse.getResult().getMessage(), "OK");

		CommitTransactionResponse commitResponse = null;
		
		try {
			commitResponse = paymentAPI.commitTransaction(transactionId, "9999", "EUR");
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(commitResponse.getResult().getCode(), "100");
		assertEquals(commitResponse.getResult().getMessage(), "OK");
		assertEquals(commitResponse.getCard().getType(), "Visa");

	}

	@Test
	public void testRevertTransaction() {

		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account, this.merchant);

		// init transaction
		InitTransactionResponse response = null;
	
		try {
			response = paymentAPI.initTransaction();
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		} catch (HttpResponseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// create transaction
		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth, cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", true);
		TransactionResponse transactionResponse = null;
	
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		} catch (HttpResponseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		assertEquals(transactionResponse.getResult().getCode(), "100");
		assertEquals(transactionResponse.getResult().getMessage(), "OK");

		// revert transaction
		TransactionResponse revertResponse = null;
		try {
			revertResponse = paymentAPI.revertTransaction(transactionId, "9999");
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		assertEquals(revertResponse.getResult().getCode(), "100");
		assertEquals(revertResponse.getResult().getMessage(), "OK");
	}
	@Test
	public void testRevertFullAmountTransaction() {

		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account, this.merchant);

		// init transaction
		InitTransactionResponse response = null;
	
		try {
			response = paymentAPI.initTransaction();
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		} catch (HttpResponseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// create transaction
		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth, cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", true);
		TransactionResponse transactionResponse = null;
	
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		} catch (HttpResponseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		assertEquals(transactionResponse.getResult().getCode(), "100");
		assertEquals(transactionResponse.getResult().getMessage(), "OK");

		// revert transaction
		TransactionResponse revertResponse = null;
		try {
			revertResponse = paymentAPI.revertTransaction(transactionId);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		assertEquals(revertResponse.getResult().getCode(), "100");
		assertEquals(revertResponse.getResult().getMessage(), "OK");
		
		// check status
		TransactionStatusResponse statusResponse = null;

		try {
			statusResponse = paymentAPI.transactionStatus(transactionId);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(statusResponse.getResult().getMessage(), "OK");
		assertEquals(statusResponse.getResult().getCode(), "100");
		assertEquals(statusResponse.getTransaction().getCurrentAmount(), "0");
		assertEquals(statusResponse.getTransaction().getId(), transactionId);
	}

	@Test
	public void testTransactionStatus() {

		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account, this.merchant);
 
		// init transaction
		InitTransactionResponse response = null;
		
		try {
			response = paymentAPI.initTransaction();
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		} catch (HttpResponseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// create transaction
		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth, cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", true);
		
		TransactionResponse transactionResponse = null;
		
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		} catch (HttpResponseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getCode(), "100");
		assertEquals(transactionResponse.getResult().getMessage(), "OK");

		TransactionResponse revertResponse = null;

		try {
			revertResponse = paymentAPI.revertTransaction(transactionId, "9950");
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		} catch (HttpResponseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		assertEquals(revertResponse.getResult().getCode(), "100");
		assertEquals(revertResponse.getResult().getMessage(), "OK");

		// status response test
		TransactionStatusResponse statusResponse = null;

		try {
			statusResponse = paymentAPI.transactionStatus(transactionId);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(statusResponse.getResult().getMessage(), "OK");
		assertEquals(statusResponse.getResult().getCode(), "100");
		assertEquals(statusResponse.getTransaction().getCurrentAmount(), "49");
		assertEquals(statusResponse.getTransaction().getId(), transactionId);
	}
	
	@Test
	public void testCommit() {
		
		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account, this.merchant);
		
		// create the payment highway request parameters
		FormBuilder formBuilder = new FormBuilder(
				"POST", this.signatureKeyId, this.signatureSecret,
				this.account, this.merchant, this.serviceUrl,
				"http://www.paymenthighway.fi", "http://www.solinor.com/", "http://www.solinor.fi", "EN");

		FormContainer formContainer = formBuilder
				.generateAddCardAndPaymentParameters("9999", "EUR", "1",
						"test payment");

		FormAPI formApi = new FormAPI(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		String result = null;
		
		try {
			result = formApi.addCardAndPay(formContainer.getFields());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Matcher matcher = Pattern.compile("(?<=form action=\").{50}").matcher(result);
		matcher.find();
		String formUri = matcher.group();
		
		// submit form (fake a browser)
		HttpPost httpPost = new HttpPost(this.serviceUrl + formUri);
		List<NameValuePair> submitParameters = new ArrayList<NameValuePair>();
		submitParameters.add(new BasicNameValuePair("card_number_formatted", "4153 0139 9970 0024"));
		submitParameters.add(new BasicNameValuePair("card_number", "4153013999700024"));
		submitParameters.add(new BasicNameValuePair("expiration_month", "11"));
		submitParameters.add(new BasicNameValuePair("expiration_year", "2017"));
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
		
        List<NameValuePair> params = null;
        params = URLEncodedUtils.parse(currentReq.getURI(), "UTF-8");
		
        // get sph-transaction-id
        String transactionId = null;
        for (NameValuePair param : params) {
        	if (param.getName().equalsIgnoreCase("sph-transaction-id")) {
        		transactionId = param.getValue();
        	}
        }
     
		CommitTransactionResponse commitResponse = null;
		
		try {
			commitResponse = paymentAPI.commitTransaction(UUID.fromString(transactionId), "9999", "EUR");
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(commitResponse.getResult().getMessage(), "OK");
		assertTrue(commitResponse.getCardToken().toString().length() == 36);
		assertEquals(commitResponse.getCard().getPartialPan(), "0024");
		assertEquals(commitResponse.getCard().getExpireYear(), "2017");
		
	}
	@Test
	public void testTokenize() {
		
		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account, this.merchant);
		
		// create the payment highway request parameters
		FormBuilder formBuilder = new FormBuilder(
				"POST", this.signatureKeyId, this.signatureSecret,
				this.account, this.merchant, this.serviceUrl,
				"http://www.paymenthighway.fi", "http://www.solinor.com/", "http://www.solinor.fi", "EN");

		FormContainer formContainer = formBuilder
				.generateAddCardAndPaymentParameters("9900", "EUR", "123",
						"test payment");

		FormAPI formApi = new FormAPI(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		String result = null;
		try {
			result = formApi.addCard(formContainer.getFields());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		Matcher matcher = Pattern.compile("(?<=form action=\").{51}").matcher(result);
		matcher.find();
		String formUri = matcher.group();
		
		// submit form (fake a browser)
		HttpPost httpPost = new HttpPost(this.serviceUrl + formUri);
		List<NameValuePair> submitParameters = new ArrayList<NameValuePair>();
		submitParameters.add(new BasicNameValuePair("card_number_formatted", "4153 0139 9970 0024"));
		submitParameters.add(new BasicNameValuePair("card_number", "4153013999700024"));
		submitParameters.add(new BasicNameValuePair("expiration_month", "11"));
		submitParameters.add(new BasicNameValuePair("expiration_year", "2017"));
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
		
        List<NameValuePair> params = null;
        params = URLEncodedUtils.parse(currentReq.getURI(), "UTF-8");
		
        // get sph-tokenization-id
        String tokenizationId = null;
        for (NameValuePair param : params) {
        	if (param.getName().equalsIgnoreCase("sph-tokenization-id")) {
        		tokenizationId = param.getValue();
        	}
        }
     
		TokenizationResponse tokenResponse = null;
		
		try {
			tokenResponse = paymentAPI.tokenize(tokenizationId);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(tokenResponse.getCard().getExpireYear(), "2017");
		assertTrue(tokenResponse.getCardToken().toString().length() == 36);
		assertEquals(tokenResponse.getResult().getMessage(), "OK");

	}
	@Test
	public void testDebitWithToken() {
		
		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account, this.merchant);
		
		// create the payment highway request parameters
		FormBuilder formBuilder = new FormBuilder(
				"POST", this.signatureKeyId, this.signatureSecret,
				this.account, this.merchant, this.serviceUrl,
				"http://www.paymenthighway.fi", "http://www.solinor.com/", "http://www.solinor.fi", "EN");

		FormContainer formContainer = formBuilder
				.generateAddCardParameters();

		FormAPI formApi = new FormAPI(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		String result = null;
		try {
			result = formApi.addCard(formContainer.getFields());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Matcher matcher = Pattern.compile("(?<=form action=\").{51}").matcher(result);
		matcher.find();
		String formUri = matcher.group();
		
		// submit form (fake a browser)
		HttpPost httpPost = new HttpPost(this.serviceUrl + formUri);
		List<NameValuePair> submitParameters = new ArrayList<NameValuePair>();
		submitParameters.add(new BasicNameValuePair("card_number_formatted", "4153 0139 9970 0024"));
		submitParameters.add(new BasicNameValuePair("card_number", "4153013999700024"));
		submitParameters.add(new BasicNameValuePair("expiration_month", "11"));
		submitParameters.add(new BasicNameValuePair("expiration_year", "2017"));
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
		
        List<NameValuePair> params = null;
        params = URLEncodedUtils.parse(currentReq.getURI(), "UTF-8");
		
        // get sph-tokenization-id
        String tokenizationId = null;
        for (NameValuePair param : params) {
        	if (param.getName().equalsIgnoreCase("sph-tokenization-id")) {
        		tokenizationId = param.getValue();
        	}
        }
     
		TokenizationResponse tokenResponse = null;
		
		try {
			tokenResponse = paymentAPI.tokenize(tokenizationId);
		} catch (AuthenticationException e2) {
			e2.printStackTrace();
		} catch (HttpResponseException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	
		assertEquals(tokenResponse.getCard().getExpireYear(), "2017");
		assertTrue(tokenResponse.getCardToken().toString().length() == 36);
		assertEquals(tokenResponse.getResult().getMessage(), "OK");

		// debit with token (success)
		InitTransactionResponse initResponse = null;
		
		try {
			initResponse = paymentAPI.initTransaction();
		} catch (AuthenticationException e2) {
			e2.printStackTrace();
		} catch (HttpResponseException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}	
		
		Token token = new Token(tokenResponse.getCardToken().toString());
		TransactionRequest transaction = 
			    new TransactionRequest(token, "1111", "EUR");

		TransactionResponse debitResponse = null;
		
		try {
			debitResponse = 
					paymentAPI.debitTransaction(initResponse.getId(), transaction);
		} catch (AuthenticationException e2) {
			e2.printStackTrace();
		} catch (HttpResponseException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		assertEquals(debitResponse.getResult().getCode(), "100");
		assertEquals(debitResponse.getResult().getMessage(), "OK");
		
	}
	@Test
	public void testDailyBatchReport() {
		
		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account, this.merchant);
		
		// request batch for yesterday, today is not available
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);   
		String date = dateFormat.format(cal.getTime());
		
		ReportResponse reportResponse = null;
		
		try {
			reportResponse = paymentAPI.fetchDailyReport(date);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		assertEquals(reportResponse.getResult().getCode(), "100");
		assertEquals(reportResponse.getResult().getMessage(), "OK");
	}
}
