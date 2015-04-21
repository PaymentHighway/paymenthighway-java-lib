package com.solinor.paymenthighway;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.solinor.paymenthighway.model.Card;
import com.solinor.paymenthighway.model.CommitTransactionRequest;
import com.solinor.paymenthighway.model.CommitTransactionResponse;
import com.solinor.paymenthighway.model.InitTransactionResponse;
import com.solinor.paymenthighway.model.ReportResponse;
import com.solinor.paymenthighway.model.RevertTransactionRequest;
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
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(response.getResultCode(), "100");
		assertEquals(response.getResultMessage(), "OK");
	}

	@Test
	public void testDebitTransaction() {

		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account, this.merchant);

		InitTransactionResponse response = null;
		try {
			response = paymentAPI.initTransaction();
		} catch (IOException e) {
			e.printStackTrace();
		}

		UUID transactionId = response.getId();

		

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth, cvc, verification);
		TransactionRequest transaction = new TransactionRequest("9999", "EUR", true, card);
		
		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getCode(), "100");
		assertEquals(transactionResponse.getResult().getMessage(), "OK");
	}

	@Test
	public void testCommitTransaction() {

		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account, this.merchant);

	
		InitTransactionResponse response = null;
		try {
			response = paymentAPI.initTransaction();
		} catch (IOException e) {
			e.printStackTrace();
		}

		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth, cvc, verification);

		TransactionRequest transaction = new TransactionRequest("9999", "EUR", true, card);
		
		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getCode(), "100");
		assertEquals(transactionResponse.getResult().getMessage(), "OK");

		CommitTransactionRequest commitRequest = new CommitTransactionRequest("9999", "EUR", true);

		CommitTransactionResponse commitResponse = null;
		try {
			commitResponse = paymentAPI.commitTransaction(transactionId, commitRequest);
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
		} catch (IOException e) {
			e.printStackTrace();
		}

		// create transaction
		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth, cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest("9999", "EUR", true, card);
		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getCode(), "100");
		assertEquals(transactionResponse.getResult().getMessage(), "OK");

		// revert transaction
		RevertTransactionRequest revertTransaction = new RevertTransactionRequest("9999", true);
	
		TransactionResponse revertResponse = null;
		try {
			revertResponse = paymentAPI.revertTransaction(transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(revertResponse.getResult().getCode(), "100");
		assertEquals(revertResponse.getResult().getMessage(), "OK");
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
		} catch (IOException e) {
			e.printStackTrace();
		}

		// create transaction
		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth, cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest("9999", "EUR", true, card);
		
		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getCode(), "100");
		assertEquals(transactionResponse.getResult().getMessage(), "OK");

		// revert transaction
		RevertTransactionRequest revertTransaction = new RevertTransactionRequest("9950", true);

		TransactionResponse revertResponse = null;
		try {
			revertResponse = paymentAPI.revertTransaction(transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(revertResponse.getResult().getCode(), "100");
		assertEquals(revertResponse.getResult().getMessage(), "OK");

		// status response test
		TransactionStatusResponse statusResponse = null;

		try {
			statusResponse = paymentAPI.transactionStatus(transactionId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		assertEquals(statusResponse.getResult().getMessage(), "OK");
		assertEquals(statusResponse.getResult().getCode(), "100");
		assertEquals(statusResponse.getTransaction().getCurrentAmount(), "49");
		assertEquals(statusResponse.getTransaction().getId(), transactionId);
	}
	
	@Test
	public void testTokenize() {
		
		// create the payment highway service
		PaymentAPI paymentAPI = new PaymentAPI(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account, this.merchant);
		
		// TODO: Change this so that a fresh tokenId is read from payment highway
		String tokenizationId = "08cc223a-cf93-437c-97a2-f338eaf0d860";
		
		TokenizationResponse tokenResponse = null;
		try {
			tokenResponse = paymentAPI.tokenize(tokenizationId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(tokenResponse.getCard().getExpireYear(), "2017");
		assertEquals(tokenResponse.getCardToken().toString(), "71435029-fbb6-4506-aa86-8529efb640b0");
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		assertEquals(reportResponse.getResult().getCode(), "100");
		assertEquals(reportResponse.getResult().getMessage(), "OK");
	}
}
