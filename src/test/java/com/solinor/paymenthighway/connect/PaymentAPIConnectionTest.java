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
import com.solinor.paymenthighway.model.Card;
import com.solinor.paymenthighway.model.TransactionResponse;
import com.solinor.paymenthighway.model.TransactionStatusResponse;
import com.solinor.paymenthighway.security.SecureSigner;

/**
 * PaymentAPIConnection test class
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class PaymentAPIConnectionTest {

	private String serviceUrl = null;
	private String signatureKeyId = null;
	private String signatureSecret = null;
	private String account = null;
	private String merchant = null;

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
		this.account = p.getProperty("sph-account");
		this.merchant = p.getProperty("sph-merchant");
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
		sphHeaders.add(new BasicNameValuePair("sph-timestamp",
				PaymentHighwayUtility.getUtcTimestamp()));
		sphHeaders.add(new BasicNameValuePair("sph-request-id",
				PaymentHighwayUtility.createRequestId()));

		String uri = "/transaction";
		SecureSigner ss = new SecureSigner(this.signatureKeyId,
				this.signatureSecret);
		String sig = ss.createSignature("POST", uri, sphHeaders, "");
		assertTrue(sig.contains("SPH1"));
	}

	@Test
	public void testInitTransaction() {
		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
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
		sphHeaders.add(new BasicNameValuePair("sph-timestamp",
				PaymentHighwayUtility.getUtcTimestamp()));
		sphHeaders.add(new BasicNameValuePair("sph-request-id",
				PaymentHighwayUtility.createRequestId()));

		String uri = "/transaction";
		SecureSigner ss = new SecureSigner(this.signatureKeyId,
				this.signatureSecret);
		String sig = ss.createSignature("POST", uri, sphHeaders, "");
		sphHeaders.add(new BasicNameValuePair("signature", sig));

		HttpPost httpPost = new HttpPost(this.serviceUrl + uri);

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		conn.addHeaders(httpPost, sphHeaders);

		assertTrue(httpPost.getAllHeaders().length == 7);

	}

	@Test
	public void testInitTransactionResponse() {

		List<NameValuePair> sphHeaders = new ArrayList<NameValuePair>();

		sphHeaders.add(new BasicNameValuePair("sph-account", "test"));
		sphHeaders
				.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
		sphHeaders.add(new BasicNameValuePair("sph-timestamp",
				PaymentHighwayUtility.getUtcTimestamp()));
		sphHeaders.add(new BasicNameValuePair("sph-request-id",
				PaymentHighwayUtility.createRequestId()));

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;

		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
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

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());

		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth,
				cvc, verification);

		TransactionRequest transaction = new TransactionRequest("9999", "EUR", true, card);
		
		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(transactionId,
					transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getMessage(), "OK");
		assertEquals(transactionResponse.getResult().getCode(), "100");
				
	}

	/**
	 * No limit available in the test bank account
	 */
	@Test
	public void testDebitTransaction2() {

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());

		UUID transactionId = response.getId();

		String pan = "4153013999700156";
		String cvc = "156";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth,
				cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest("99900", "EUR", true, card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(transactionId,
					transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getMessage(), "Authorization failed");
		assertEquals(transactionResponse.getResult().getCode(), "200");
	}

	/**
	 * Reported as stolen
	 */
	@Test
	public void testDebitTransaction3() {

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());

		UUID transactionId = response.getId();

		String pan = "4153013999700289";
		String cvc = "289";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth,
				cvc, verification);
			
		TransactionRequest transaction = new TransactionRequest("99900", "EUR", true, card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(transactionId,
					transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(transactionResponse.getResult().getMessage(), "Authorization failed");
		assertEquals(transactionResponse.getResult().getCode(), "200");
	}

	/**
	 * This will test successful credit transaction NOTE: NOT YET IMPLEMENTED
	 */
	@Ignore
	@Test
	public void testCreditTransaction1() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * This will test successful revert transaction for full amount
	 * 
	 * 1. create transaction handle 2. create a debit transaction 3. revert that
	 * transaction
	 */
	@Test
	public void testRevertTransaction1() {

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());

		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth,
				cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest("9999", "EUR", true, card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(transactionId,
					transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getMessage(), "OK");
		assertEquals(transactionResponse.getResult().getCode(), "100");

		// revert transaction test
		TransactionResponse revertResponse = null;

		RevertTransactionRequest revertTransaction = new RevertTransactionRequest("9999", true);
		
		try {
			revertResponse = conn.revertTransaction(transactionId,
					revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(revertResponse.getResult().getMessage(), "OK");
		assertEquals(revertResponse.getResult().getCode(), "100");
	}

	/**
	 * This will test successful revert transaction for partial amount
	 * 
	 * 1. create transaction handle 2. create a debit transaction 3. revert that
	 * transaction
	 */
	@Test
	public void testRevertTransaction2() {

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());

		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth,
				cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest("9999", "EUR", true, card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(transactionId,
					transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getMessage(),"OK");
		assertEquals(transactionResponse.getResult().getCode(), "100");
			
		// revert transaction test
		TransactionResponse revertResponse = null;

		RevertTransactionRequest revertTransaction = new RevertTransactionRequest("1000", true);
	
		try {
			revertResponse = conn.revertTransaction(transactionId,
					revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(revertResponse.getResult().getMessage(), "OK");
		assertEquals(revertResponse.getResult().getCode(), "100");

	}

	/**
	 * This will test failed revert transaction because of insufficient balance
	 * 
	 * 1. create transaction handle 2. create a debit transaction 3. revert that
	 * transaction
	 */
	@Test
	public void testRevertTransaction3() {

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());

		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth,
				cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest("1000", "EUR", true, card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(transactionId,
					transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getMessage(), "OK");
		
		assertEquals(transactionResponse.getResult().getCode(), "100");

		// revert transaction test
		TransactionResponse revertResponse = null;

		RevertTransactionRequest revertTransaction = new RevertTransactionRequest("1001");
	
		try {
			revertResponse = conn.revertTransaction(transactionId,
					revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertTrue(revertResponse.getResult().getMessage()
				.contains("Revert failed: Insufficient balance"));
		assertEquals(revertResponse.getResult().getCode(), "211");

	}

	/**
	 * This will test successfull revert transaction for the full amount without
	 * specifying amount
	 * 
	 * 1. create transaction handle 2. create a debit transaction 3. revert that
	 * transaction
	 */
	@Test
	public void testRevertTransaction4() {

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());

		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth,
				cvc, verification);

		TransactionRequest transaction = new TransactionRequest("1000", "EUR", true, card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(transactionId,
					transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getMessage(), "OK");				
		assertEquals(transactionResponse.getResult().getCode(), "100");

		// revert transaction test
		TransactionResponse revertResponse = null;

		RevertTransactionRequest revertTransaction = new RevertTransactionRequest();
	
		try {
			revertResponse = conn.revertTransaction(transactionId,
					revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(revertResponse.getResult().getMessage(), "OK");
		assertEquals(revertResponse.getResult().getCode(), "100");

	}

	/**
	 * This will test successfull revert transaction for the full amount in two
	 * different reverts
	 * 
	 * 1. create transaction handle 2. create a debit transaction 3. revert half
	 * transaction 4. revert rest of transaction
	 */
	@Test
	public void testRevertTransaction5() {

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());

		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth,
				cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest("1000", "EUR", true, card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(transactionId,
					transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getMessage(), "OK");
		assertEquals(transactionResponse.getResult().getCode(), "100");

		// revert transaction test
		TransactionResponse revertResponse = null;

		RevertTransactionRequest revertTransaction = new RevertTransactionRequest("500");

		try {
			revertResponse = conn.revertTransaction(transactionId,
					revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(revertResponse.getResult().getMessage(), "OK");
		assertEquals(revertResponse.getResult().getCode(), "100");

		revertTransaction = new RevertTransactionRequest(); // no amount set, should revert rest of the transaction
		try {
			revertResponse = conn.revertTransaction(transactionId,
					revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(revertResponse.getResult().getMessage(), "OK");
		assertEquals(revertResponse.getResult().getCode(), "100");
	}

	/**
	 * This will test partial revert transactions where second revert will fail
	 * due insufficient funds
	 * 
	 * 1. create transaction handle 2. create a debit transaction 3. revert half
	 * transaction 4. revert more than balance
	 */
	@Test
	public void testRevertTransaction6() {

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());

		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth,
				cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest("1000", "EUR", true, card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(transactionId,
					transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getMessage(), "OK");
		assertEquals(transactionResponse.getResult().getCode(), "100");

		// revert transaction test
		TransactionResponse revertResponse = null;

		RevertTransactionRequest revertTransaction = new RevertTransactionRequest("500");

		try {
			revertResponse = conn.revertTransaction(transactionId,
					revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(revertResponse.getResult().getMessage(), "OK");
		assertEquals(revertResponse.getResult().getCode(), "100");

		revertTransaction = new RevertTransactionRequest("501");// over balance
		try {
			revertResponse = conn.revertTransaction(transactionId,
					revertTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(revertResponse.getResult().getMessage()
				.contains("Revert failed: Insufficient balance"));
		assertEquals(revertResponse.getResult().getCode(), "211");
	}

	/**
	 * This will test successful transaction status request
	 * 
	 * 1. create transaction handle 2. create a debit transaction 3. request a
	 * transaction status
	 */
	@Test
	public void testTransactionStatus1() {

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());

		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth,
				cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest("9999", "EUR", true, card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(transactionId,
					transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getMessage(), "OK");
		assertEquals(transactionResponse.getResult().getCode(), "100");

		// status response test
		TransactionStatusResponse statusResponse = null;

		try {
			statusResponse = conn.transactionStatus(transactionId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// test result
		assertEquals(statusResponse.getResult().getMessage(), "OK");
		assertEquals(statusResponse.getResult().getCode(), "100");
		assertEquals(statusResponse.getTransaction().getCurrentAmount(),
				"9999");
		assertEquals(statusResponse.getTransaction().getId(), transactionId);
	}

	/**
	 * This will test successful com transaction
	 */
	@Test
	public void testCommitTransaction1() {

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());

		UUID transactionId = response.getId();

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth,
				cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest("9999", "EUR", true, card);

		TransactionResponse transactionResponse = null;
		try {
			transactionResponse = conn.debitTransaction(transactionId,
					transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(transactionResponse.getResult().getMessage(), "OK");
		assertEquals(transactionResponse.getResult().getCode(), "100")
		;
		CommitTransactionRequest commitTransaction = new CommitTransactionRequest("9999", "EUR", true);

		CommitTransactionResponse commitTransactionResponse = null;
		try {
			commitTransactionResponse = conn.commitTransaction(transactionId,
					commitTransaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertTrue(commitTransactionResponse.getCard().getType()
				.equalsIgnoreCase("visa"));

	}

	/**
	 * This will test successful tokenization request
	 */
	@Test
	public void testTokenization1() {

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);
		InitTransactionResponse response = null;
		try {
			response = conn.initTransactionHandle();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("100", response.getResultCode());
		assertEquals("OK", response.getResultMessage());

		String tokenizationId = "08cc223a-cf93-437c-97a2-f338eaf0d860";

		TokenizationResponse tokenResponse = null;
		try {
			tokenResponse = conn.tokenization(tokenizationId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(tokenResponse.getCard().getExpireYear(), "2017");
		assertEquals(tokenResponse.getCardToken().toString(),
				"71435029-fbb6-4506-aa86-8529efb640b0");
	}

	/**
	 * This will test successful batch report request
	 */
	@Test
	public void testReport1() {

		PaymentAPIConnection conn = new PaymentAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret, this.account,
				this.merchant);

		// day format: <yyyyMMdd>
		String date = "20150408";
		String result = null;
		try {
			result = conn.fetchReport(date);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// tests only that response is from the right function
		assertTrue(result.contains("settlements"));
	}
}
