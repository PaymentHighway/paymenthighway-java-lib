package com.solinor.paymenthighway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.solinor.paymenthighway.model.CommitTransactionRequest;
import com.solinor.paymenthighway.model.CommitTransactionResponse;
import com.solinor.paymenthighway.model.InitTransactionResponse;
import com.solinor.paymenthighway.model.RevertTransactionRequest;
import com.solinor.paymenthighway.model.TokenizationResponse;
import com.solinor.paymenthighway.model.TransactionRequest;
import com.solinor.paymenthighway.model.TransactionRequest.Card;
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

		TransactionRequest transaction = new TransactionRequest();
		transaction.setAmount("9999");
		transaction.setCurrency("EUR");
		transaction.setBlocking(true);

		Card card = new TransactionRequest.Card();
		
		// TODO: move to constructor
		card.setPan("4153013999700024");
		card.setCvc("024"); // optional
		card.setExpiryYear("2017");
		card.setExpiryMonth("11");
		card.setVerification(""); // TODO: remove
		transaction.setCard(card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().get("code"), "100");
		assertEquals(transactionResponse.getResult().get("message"), "OK");
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

		TransactionRequest transaction = new TransactionRequest();
		transaction.setAmount("9999");
		transaction.setCurrency("EUR");
		transaction.setBlocking(true);

		Card card = new TransactionRequest.Card();
		card.setPan("4153013999700024");
		card.setCvc("024");
		card.setExpiryYear("2017");
		card.setExpiryMonth("11");
		card.setVerification("");
		transaction.setCard(card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().get("code"), "100");
		assertEquals(transactionResponse.getResult().get("message"), "OK");

		CommitTransactionRequest commitRequest = new CommitTransactionRequest();
		commitRequest.setAmount("9999");
		commitRequest.setBlocking(true);
		commitRequest.setCurrency("EUR");

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

		TransactionRequest transaction = new TransactionRequest();
		transaction.setAmount("9999");
		transaction.setCurrency("EUR");
		transaction.setBlocking(true);

		Card card = new TransactionRequest.Card();
		card.setPan("4153013999700024");
		card.setCvc("024");
		card.setExpiryYear("2017");
		card.setExpiryMonth("11");
		card.setVerification("");
		transaction.setCard(card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().get("code"), "100");
		assertEquals(transactionResponse.getResult().get("message"), "OK");

		// revert transaction
		RevertTransactionRequest revertTransaction = new RevertTransactionRequest();
		revertTransaction.setAmount("9999");
		revertTransaction.setBlocking(true);

		TransactionResponse revertResponse = null;
		try {
			revertResponse = paymentAPI.revertTransaction(transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(revertResponse.getResult().get("code"), "100");
		assertEquals(revertResponse.getResult().get("message"), "OK");
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

		TransactionRequest transaction = new TransactionRequest();
		transaction.setAmount("9999");
		transaction.setCurrency("EUR");
		transaction.setBlocking(true);

		Card card = new TransactionRequest.Card();
		card.setPan("4153013999700024");
		card.setCvc("024");
		card.setExpiryYear("2017");
		card.setExpiryMonth("11");
		card.setVerification("");
		transaction.setCard(card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = paymentAPI.debitTransaction(transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().get("code"), "100");
		assertEquals(transactionResponse.getResult().get("message"), "OK");

		// revert transaction
		RevertTransactionRequest revertTransaction = new RevertTransactionRequest();
		revertTransaction.setAmount("9950");
		revertTransaction.setBlocking(true);

		TransactionResponse revertResponse = null;
		try {
			revertResponse = paymentAPI.revertTransaction(transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(revertResponse.getResult().get("code"), "100");
		assertEquals(revertResponse.getResult().get("message"), "OK");

		// status response test
		TransactionStatusResponse statusResponse = null;

		try {
			statusResponse = paymentAPI.transactionStatus(transactionId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(statusResponse.getResult().toString().contains("message=OK"));
		assertTrue(statusResponse.getResult().toString().contains("code=100"));
		assertEquals(statusResponse.getTransaction().get("current_amount"), 49);
		assertEquals(statusResponse.getTransaction().get("id"),
				transactionId.toString());
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
}
