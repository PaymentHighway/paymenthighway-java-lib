package com.solinor.paymenthighway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.solinor.paymenthighway.model.CommitTransactionRequest;
import com.solinor.paymenthighway.model.CommitTransactionResponse;
import com.solinor.paymenthighway.model.InitTransactionResponse;
import com.solinor.paymenthighway.model.RevertTransactionRequest;
import com.solinor.paymenthighway.model.TransactionRequest;
import com.solinor.paymenthighway.model.TransactionResponse;
import com.solinor.paymenthighway.model.TransactionStatusResponse;
import com.solinor.paymenthighway.model.TransactionRequest.Card;

/**
 * PaymentHighway Payment API tests
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class PaymentAPITest {

	Properties props;

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInitTransaction() {

		String serviceUrl = this.props.getProperty("service_url");
		String signatureKeyId = this.props.getProperty("signature_key_id");
		String signatureSecret = this.props.getProperty("signature_secret");

		// create the payment highway service
		PaymentAPI paymentService = new PaymentAPI(serviceUrl, signatureKeyId,
				signatureSecret);

		List<NameValuePair> nameValueHeaders = new ArrayList<NameValuePair>();

		nameValueHeaders.add(new BasicNameValuePair("sph-account", "test"));
		nameValueHeaders.add(new BasicNameValuePair("sph-merchant",
				"test_merchantId"));
		nameValueHeaders.add(new BasicNameValuePair("sph-timestamp",
				PaymentHighwayUtility.getUtcTimestamp()));
		nameValueHeaders.add(new BasicNameValuePair("sph-request-id",
				PaymentHighwayUtility.createRequestId()));

		InitTransactionResponse response = null;
		try {
			response = paymentService.initTransaction(nameValueHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(response.getResultCode(), "100");
		assertEquals(response.getResultMessage(), "OK");
	}

	@Test
	public void testDebitTransaction() {

		String serviceUrl = this.props.getProperty("service_url");
		String signatureKeyId = this.props.getProperty("signature_key_id");
		String signatureSecret = this.props.getProperty("signature_secret");

		// create the payment highway service
		PaymentAPI paymentService = new PaymentAPI(serviceUrl, signatureKeyId,
				signatureSecret);

		List<NameValuePair> nameValueHeaders = new ArrayList<NameValuePair>();

		nameValueHeaders.add(new BasicNameValuePair("sph-account", "test"));
		nameValueHeaders.add(new BasicNameValuePair("sph-merchant",
				"test_merchantId"));
		nameValueHeaders.add(new BasicNameValuePair("sph-timestamp",
				PaymentHighwayUtility.getUtcTimestamp()));
		nameValueHeaders.add(new BasicNameValuePair("sph-request-id",
				PaymentHighwayUtility.createRequestId()));

		InitTransactionResponse response = null;
		try {
			response = paymentService.initTransaction(nameValueHeaders);
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
			transactionResponse = paymentService.debitTransaction(
					nameValueHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().get("code"), "100");
		assertEquals(transactionResponse.getResult().get("message"), "OK");
	}

	@Test
	public void testCommitTransaction() {

		String serviceUrl = this.props.getProperty("service_url");
		String signatureKeyId = this.props.getProperty("signature_key_id");
		String signatureSecret = this.props.getProperty("signature_secret");

		// create the payment highway service
		PaymentAPI paymentService = new PaymentAPI(serviceUrl, signatureKeyId,
				signatureSecret);

		List<NameValuePair> nameValueHeaders = new ArrayList<NameValuePair>();

		nameValueHeaders.add(new BasicNameValuePair("sph-account", "test"));
		nameValueHeaders.add(new BasicNameValuePair("sph-merchant",
				"test_merchantId"));
		nameValueHeaders.add(new BasicNameValuePair("sph-timestamp",
				PaymentHighwayUtility.getUtcTimestamp()));
		nameValueHeaders.add(new BasicNameValuePair("sph-request-id",
				PaymentHighwayUtility.createRequestId()));

		InitTransactionResponse response = null;
		try {
			response = paymentService.initTransaction(nameValueHeaders);
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
			transactionResponse = paymentService.debitTransaction(
					nameValueHeaders, transactionId, transaction);
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
			commitResponse = paymentService.commitTransaction(nameValueHeaders,
					transactionId, commitRequest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(commitResponse.getResult().getCode(), "100");
		assertEquals(commitResponse.getResult().getMessage(), "OK");
		assertEquals(commitResponse.getCard().getType(), "Visa");

	}

	@Test
	public void testRevertTransaction() {

		String serviceUrl = this.props.getProperty("service_url");
		String signatureKeyId = this.props.getProperty("signature_key_id");
		String signatureSecret = this.props.getProperty("signature_secret");

		// create the payment highway service
		PaymentAPI paymentService = new PaymentAPI(serviceUrl, signatureKeyId,
				signatureSecret);

		List<NameValuePair> nameValueHeaders = new ArrayList<NameValuePair>();

		nameValueHeaders.add(new BasicNameValuePair("sph-account", "test"));
		nameValueHeaders.add(new BasicNameValuePair("sph-merchant",
				"test_merchantId"));
		nameValueHeaders.add(new BasicNameValuePair("sph-timestamp",
				PaymentHighwayUtility.getUtcTimestamp()));
		nameValueHeaders.add(new BasicNameValuePair("sph-request-id",
				PaymentHighwayUtility.createRequestId()));

		// init transaction
		InitTransactionResponse response = null;
		try {
			response = paymentService.initTransaction(nameValueHeaders);
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
			transactionResponse = paymentService.debitTransaction(
					nameValueHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().get("code"), "100");
		assertEquals(transactionResponse.getResult().get("message"), "OK");

		// revert transaction
		RevertTransactionRequest revertTransaction = new RevertTransactionRequest();
		revertTransaction.setAmount("9999"); // this is optional, will default
												// to full amount
		revertTransaction.setBlocking(true); // this is optional, will default
												// to true

		TransactionResponse revertResponse = null;
		try {
			revertResponse = paymentService.revertTransaction(nameValueHeaders,
					transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(revertResponse.getResult().get("code"), "100");
		assertEquals(revertResponse.getResult().get("message"), "OK");
	}

	@Test
	public void testTransactionStatus() {

		String serviceUrl = this.props.getProperty("service_url");
		String signatureKeyId = this.props.getProperty("signature_key_id");
		String signatureSecret = this.props.getProperty("signature_secret");

		// create the payment highway service
		PaymentAPI paymentService = new PaymentAPI(serviceUrl, signatureKeyId,
				signatureSecret);

		List<NameValuePair> nameValueHeaders = new ArrayList<NameValuePair>();

		nameValueHeaders.add(new BasicNameValuePair("sph-account", "test"));
		nameValueHeaders.add(new BasicNameValuePair("sph-merchant",
				"test_merchantId"));
		nameValueHeaders.add(new BasicNameValuePair("sph-timestamp",
				PaymentHighwayUtility.getUtcTimestamp()));
		nameValueHeaders.add(new BasicNameValuePair("sph-request-id",
				PaymentHighwayUtility.createRequestId()));

		// init transaction
		InitTransactionResponse response = null;
		try {
			response = paymentService.initTransaction(nameValueHeaders);
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
			transactionResponse = paymentService.debitTransaction(
					nameValueHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().get("code"), "100");
		assertEquals(transactionResponse.getResult().get("message"), "OK");

		// revert transaction
		RevertTransactionRequest revertTransaction = new RevertTransactionRequest();
		revertTransaction.setAmount("9950"); // this is optional, will default
												// to full amount
		revertTransaction.setBlocking(true); // this is optional, will default
												// to true

		TransactionResponse revertResponse = null;
		try {
			revertResponse = paymentService.revertTransaction(nameValueHeaders,
					transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(revertResponse.getResult().get("code"), "100");
		assertEquals(revertResponse.getResult().get("message"), "OK");

		// status response test
		TransactionStatusResponse statusResponse = null;

		try {
			statusResponse = paymentService.transactionStatus(nameValueHeaders,
					transactionId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(statusResponse.getResult().toString().contains("message=OK"));
		assertTrue(statusResponse.getResult().toString().contains("code=100"));
		assertEquals(statusResponse.getTransaction().get("current_amount"), 49);
		assertEquals(statusResponse.getTransaction().get("id"),
				transactionId.toString());
	}
}
