/**
 * 
 */
package com.solinor.paymenthighway.connect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.solinor.paymenthighway.PaymentHighwayUtility;
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
 * PaymentAPIConnection test class
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class PaymentAPIConnectionTest {

	private String serviceUrl = null;
	private String signatureKeyId = null;
	private String signatureSecret = null;
		
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
		Properties p = PaymentHighwayUtility.getProperties();
		this.serviceUrl = p.getProperty("service_url");
		this.signatureKeyId = p.getProperty("signature_key_id");
		this.signatureSecret = p.getProperty("signature_secret");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testCreateSignature() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
        sphHeaders.add(new BasicNameValuePair("sph-amount", "9990"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
        
		String uri = "/transaction";
		SecureSigner ss = new SecureSigner(this.signatureKeyId, this.signatureSecret);
		String sig = ss.createSignature("POST", uri, sphHeaders, "");
		assertTrue(sig.contains("SPH1"));
	}

	@Test
	public void testInitTransaction() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// test for response code 100 
		assertEquals(response.getResultCode(), "100");
		// test for message OK
		assertEquals(response.getResultMessage(), "OK");
		// test for id field
		assertTrue(response.getId() != null);
	}
	@Test
	public void testAddHeaders() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
        sphHeaders.add(new BasicNameValuePair("sph-amount", "9990"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
        
		String uri = "/transaction";
		SecureSigner ss = new SecureSigner(this.signatureKeyId, this.signatureSecret);
		String sig = ss.createSignature("POST", uri, sphHeaders, "");
		sphHeaders.add(new BasicNameValuePair("signature", sig));
	
		HttpPost httpPost = new HttpPost(this.serviceUrl + uri);
		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		conn.addHeaders(httpPost, sphHeaders);
		
		assertTrue(httpPost.getAllHeaders().length == 7);

	}
	
	@Test
	public void testInitTransactionResponse() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
	}
	
	/**
	 * This will test successful debit transaction
	 */
	@Test
	public void testDebitTransaction1() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
		
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
			transactionResponse = conn.debitTransaction(sphHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(transactionResponse.getResult().toString().contains("message=OK"));
		assertTrue(transactionResponse.getResult().toString().contains("code=100"));
	}
	
	/**
	 * No limit available in the test bank account
	 */
	@Test
	public void testDebitTransaction2() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
		
		UUID transactionId = response.getId();
		
		TransactionRequest transaction = new TransactionRequest();
		transaction.setAmount("99900");
		transaction.setCurrency("EUR");
		transaction.setBlocking(true);
		
		Card card = new TransactionRequest.Card();
		card.setPan("4153013999700156");
		card.setCvc("156");
		card.setExpiryYear("2017");
		card.setExpiryMonth("11");
		card.setVerification("");
		transaction.setCard(card);
		
		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(sphHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(transactionResponse.getResult().toString().contains("message=Authorization failed"));
		assertTrue(transactionResponse.getResult().toString().contains("code=200"));
	}
	/**
	 * Reported as stolen
	 */
	@Test
	public void testDebitTransaction3() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
		
		UUID transactionId = response.getId();
		
		TransactionRequest transaction = new TransactionRequest();
		transaction.setAmount("99900");
		transaction.setCurrency("EUR");
		transaction.setBlocking(true);
		
		Card card = new TransactionRequest.Card();
		card.setPan("4153013999700289");
		card.setCvc("289");
		card.setExpiryYear("2017");
		card.setExpiryMonth("11");
		card.setVerification("");
		transaction.setCard(card);
		
		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(sphHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(transactionResponse.getResult().toString().contains("message=Authorization failed"));
		assertTrue(transactionResponse.getResult().toString().contains("code=200"));
	}
	/**
	 * This will test successful credit transaction
	 * NOTE: NOT YET IMPLEMENTED 
	 */
	@Ignore @Test
	public void testCreditTransaction1() {
		fail("Not yet implemented"); // TODO
	}
	
	
	/**
	 * This will test successful revert transaction for full amount
	 * 
	 * 1. create transaction handle
	 * 2. create a debit transaction 
	 * 3. revert that transaction 
	 */
	@Test
	public void testRevertTransaction1() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
		
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
			transactionResponse = conn.debitTransaction(sphHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(transactionResponse.getResult().toString().contains("message=OK"));
		assertTrue(transactionResponse.getResult().toString().contains("code=100"));
		
		
		// revert transaction test
		TransactionResponse revertResponse = null;
		
		RevertTransactionRequest revertTransaction = new RevertTransactionRequest();
		revertTransaction.setAmount("9999");
		revertTransaction.setBlocking(true); // this is optional, will default to true
		try {
			revertResponse = conn.revertTransaction(sphHeaders, transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(revertResponse.getResult().toString().contains("message=OK"));
		assertTrue(revertResponse.getResult().toString().contains("code=100"));
	}

	/**
	 * This will test successful revert transaction for partial amount
	 * 
	 * 1. create transaction handle
	 * 2. create a debit transaction 
	 * 3. revert that transaction 
	 */
	@Test
	public void testRevertTransaction2() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
		
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
			transactionResponse = conn.debitTransaction(sphHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(transactionResponse.getResult().toString().contains("message=OK"));
		assertTrue(transactionResponse.getResult().toString().contains("code=100"));
		
		// revert transaction test
		TransactionResponse revertResponse = null;
		
		RevertTransactionRequest revertTransaction = new RevertTransactionRequest();
		revertTransaction.setAmount("1000");
		revertTransaction.setBlocking(true); // this is optional, will default to true
		try {
			revertResponse = conn.revertTransaction(sphHeaders, transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(revertResponse.getResult().toString().contains("message=OK"));
		assertTrue(revertResponse.getResult().toString().contains("code=100"));
		
	}
	
	/**
	 * This will test failed revert transaction because of insufficient balance
	 * 
	 * 1. create transaction handle
	 * 2. create a debit transaction 
	 * 3. revert that transaction 
	 */
	@Test
	public void testRevertTransaction3() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
		
		UUID transactionId = response.getId();
		
		TransactionRequest transaction = new TransactionRequest();
		transaction.setAmount("1000");
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
			transactionResponse = conn.debitTransaction(sphHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(transactionResponse.getResult().toString().contains("message=OK"));
		assertTrue(transactionResponse.getResult().toString().contains("code=100"));
		
		// revert transaction test
		TransactionResponse revertResponse = null;
		
		RevertTransactionRequest revertTransaction = new RevertTransactionRequest();
		revertTransaction.setAmount("1001");
		// revertTransaction.setBlocking(true); // this is optional, will default to true
		try {
			revertResponse = conn.revertTransaction(sphHeaders, transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(revertResponse.getResult().toString().contains("message=Revert failed: Insufficient balance"));
		assertTrue(revertResponse.getResult().toString().contains("code=211"));
		
	}
	
	/**
	 * This will test successfull revert transaction for the full amount 
	 * without specifying amount
	 * 
	 * 1. create transaction handle
	 * 2. create a debit transaction 
	 * 3. revert that transaction 
	 */
	@Test
	public void testRevertTransaction4() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
		
		UUID transactionId = response.getId();
		
		TransactionRequest transaction = new TransactionRequest();
		transaction.setAmount("1000");
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
			transactionResponse = conn.debitTransaction(sphHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(transactionResponse.getResult().toString().contains("message=OK"));
		assertTrue(transactionResponse.getResult().toString().contains("code=100"));
		
		// revert transaction test
		TransactionResponse revertResponse = null;
		
		RevertTransactionRequest revertTransaction = new RevertTransactionRequest();
		// revertTransaction.setAmount("1001");
		// revertTransaction.setBlocking(true); // this is optional, will default to true
		try {
			revertResponse = conn.revertTransaction(sphHeaders, transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(revertResponse.getResult().toString().contains("message=OK"));
		assertTrue(revertResponse.getResult().toString().contains("code=100"));
		
	}
	
	/**
	 * This will test successfull revert transaction for the full amount in two different reverts
	 * 
	 * 1. create transaction handle
	 * 2. create a debit transaction 
	 * 3. revert half transaction 
	 * 4. revert rest of transaction
	 */
	@Test
	public void testRevertTransaction5() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
		
		UUID transactionId = response.getId();
		
		TransactionRequest transaction = new TransactionRequest();
		transaction.setAmount("1000");
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
			transactionResponse = conn.debitTransaction(sphHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(transactionResponse.getResult().toString().contains("message=OK"));
		assertTrue(transactionResponse.getResult().toString().contains("code=100"));
		
		// revert transaction test
		TransactionResponse revertResponse = null;
		
		RevertTransactionRequest revertTransaction = new RevertTransactionRequest();
		revertTransaction.setAmount("500");
		// revertTransaction.setBlocking(true); // this is optional, will default to true
		try {
			revertResponse = conn.revertTransaction(sphHeaders, transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(revertResponse.getResult().toString().contains("message=OK"));
		assertTrue(revertResponse.getResult().toString().contains("code=100"));
		
		revertTransaction.setAmount(null); // no amount set, should revert rest of the transaction
		try {
			revertResponse = conn.revertTransaction(sphHeaders, transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(revertResponse.getResult().toString().contains("message=OK"));
		assertTrue(revertResponse.getResult().toString().contains("code=100"));
		
	}
	
	/**
	 * This will test partial revert transactions where second revert will fail due insufficient funds
	 * 
	 * 1. create transaction handle
	 * 2. create a debit transaction 
	 * 3. revert half transaction 
	 * 4. revert more than balance
	 */
	@Test
	public void testRevertTransaction6() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
		
		UUID transactionId = response.getId();
		
		TransactionRequest transaction = new TransactionRequest();
		transaction.setAmount("1000");
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
			transactionResponse = conn.debitTransaction(sphHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(transactionResponse.getResult().toString().contains("message=OK"));
		assertTrue(transactionResponse.getResult().toString().contains("code=100"));

		// revert transaction test
		TransactionResponse revertResponse = null;
		
		RevertTransactionRequest revertTransaction = new RevertTransactionRequest();
		revertTransaction.setAmount("500");
		// revertTransaction.setBlocking(true); // this is optional, will default to true
		try {
			revertResponse = conn.revertTransaction(sphHeaders, transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(revertResponse.getResult().toString().contains("message=OK"));
		assertTrue(revertResponse.getResult().toString().contains("code=100"));
		
		revertTransaction.setAmount("501"); // over balance
		try {
			revertResponse = conn.revertTransaction(sphHeaders, transactionId, revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(revertResponse.getResult().toString().contains("message=Revert failed: Insufficient balance"));
		assertTrue(revertResponse.getResult().toString().contains("code=211"));
	}
	
	/**
	 * This will test successful transaction status request
	 * 
	 * 1. create transaction handle
	 * 2. create a debit transaction 
	 * 3. request a transaction status
	 */
	@Test
	public void testTransactionStatus1() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
		
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
			transactionResponse = conn.debitTransaction(sphHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(transactionResponse.getResult().toString().contains("message=OK"));
		assertTrue(transactionResponse.getResult().toString().contains("code=100"));
		
		// status response test
		TransactionStatusResponse statusResponse = null;
		
		try {
			statusResponse = conn.transactionStatus(sphHeaders, transactionId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// test result
		// System.out.println("status:" + statusResponse.getResult());
		assertTrue(statusResponse.getResult().toString().contains("message=OK"));
		assertTrue(statusResponse.getResult().toString().contains("code=100"));
		assertEquals(statusResponse.getTransaction().get("current_amount"), 9999);
		assertEquals(statusResponse.getTransaction().get("id"), transactionId.toString());
		
		// System.out.println(" transaction:" + statusResponse.getTransaction());
		// System.out.println(" card:" + statusResponse.getTransaction().get("card"));
		// System.out.println(" acquirer:" + statusResponse.getTransaction().get("acquirer"));
		// System.out.println(" id:" + statusResponse.getTransaction().get("id"));
		// System.out.println(" current amount:" + statusResponse.getTransaction().get("current_amount"));
	
	}
	
	/**
	 * This will test successful com transaction
	 */
	@Test
	public void testCommitTransaction1() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
		
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
			transactionResponse = conn.debitTransaction(sphHeaders, transactionId, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(transactionResponse.getResult().toString().contains("message=OK"));
		assertTrue(transactionResponse.getResult().toString().contains("code=100"));
		
		
		CommitTransactionRequest commitTransaction = new CommitTransactionRequest();
		commitTransaction.setAmount("9999");
		commitTransaction.setCurrency("EUR");
		
		CommitTransactionResponse commitTransactionResponse = null;
		try {
			commitTransactionResponse = conn.commitTransaction(sphHeaders, transactionId, commitTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		assertTrue(commitTransactionResponse.getCard().getType().equalsIgnoreCase("visa"));
		
	}
	
	
	/**
	 * This will test successful tokenization request
	 */
	@Test
	public void testTokenization1() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle(sphHeaders);
		} catch (IOException e) {
			e.printStackTrace();
		}
		UUID id = response.getId();
		// System.out.println("id:" + id);
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());
		
//		TokenizationResponse tokenResponse = null;
//		try {
//			tokenResponse = conn.tokenization(sphHeaders, id);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		// System.out.println("tokenResponse.getCardToken()=" + tokenResponse.toString());
		
	}
	/**
	 * This will test successful batch report request
	 */
	@Test
	public void testReport1() {
		
		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();
		
		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        sphHeaders.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        sphHeaders.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
  		
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		
		// day format: <yyyyMMdd>
		String date = "20150407";
		String result = null;
		try {
			result = conn.fetchReport(sphHeaders, date);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}


