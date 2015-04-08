package com.solinor.paymenthighway.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.solinor.paymenthighway.PaymentHighwayUtility;
import com.solinor.paymenthighway.json.JsonGenerator;
import com.solinor.paymenthighway.model.TransactionRequest.Card;
import com.solinor.paymenthighway.model.TransactionRequest.Token;

public class TransactionRequestTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		TransactionRequest transaction = new TransactionRequest();
		transaction.amount = "9999";
		transaction.currency = "EUR";
		transaction.blocking = true;
		
		Card card = new TransactionRequest.Card();
		card.pan = "4153013999700024";
		card.cvc = "024";
		card.expiryYear = "2017";
		card.expiryMonth = "11";
		card.verification = "";
		transaction.card = card;
		
		Token token = new TransactionRequest.Token();
		token.code = PaymentHighwayUtility.createRequestId();
		token.message = "this is a token message";
		transaction.token = token;
		
		JsonGenerator jsonGenerator = new JsonGenerator();
		String json = jsonGenerator.createTransactionJson(transaction);
		assertTrue(json.contains("pan"));
	}

	@Test
	public void testStaticNestedClass() {
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
		transaction.card = card;
		
		Token token = new TransactionRequest.Token();
		token.setCode(PaymentHighwayUtility.createRequestId());
		token.setMessage("token message");
		transaction.token = token;
		
		assertEquals(transaction.token.getMessage(), "token message");
		
		// change new token
		Token token2 = new TransactionRequest.Token();
		token2.setCode(PaymentHighwayUtility.createRequestId());
		token2.setMessage("token message 2");
		
		assertEquals(transaction.token.getMessage(), "token message");
		
		transaction.token = token2;
		
		assertEquals(transaction.token.getMessage(), "token message 2");
		
	}
	
	@Test
	public void testStaticNestedClass2() {
		TransactionRequest transaction = new TransactionRequest();
		transaction.setAmount("1111");
		transaction.setCurrency("EUR");
		transaction.setBlocking(true);
		
		Card card = new TransactionRequest.Card();
		card.setPan("4153013999700024");
		card.setCvc("024");
		card.setExpiryYear("2017");
		card.setExpiryMonth("11");
		card.setVerification("");
		transaction.card = card;
		
		Token token = new TransactionRequest.Token();
		token.setCode(PaymentHighwayUtility.createRequestId());
		token.setMessage("token message");
		transaction.token = token;
		
		assertEquals(transaction.card.getPan(), "4153013999700024");
		assertEquals(transaction.token.getMessage(), "token message");
		
		TransactionRequest transaction2 = new TransactionRequest();
		transaction2.setAmount("2222");
		transaction2.setCurrency("EUR");
		transaction2.setBlocking(true);
		
		Card card2 = new TransactionRequest.Card();
		card2.setPan("4153013999700025");
		card2.setCvc("024");
		card2.setExpiryYear("2017");
		card2.setExpiryMonth("11");
		card2.setVerification("");
		transaction2.card = card2;
		
		Token token2 = new TransactionRequest.Token();
		token2.setCode(PaymentHighwayUtility.createRequestId());
		token2.setMessage("token message 2");
		transaction2.token = token2;
		
		// these shouldn't change
		assertEquals(transaction.card.getPan(), "4153013999700024");
		assertEquals(transaction.token.getMessage(), "token message");
		
		card2.setCvc("123");
		token2.setMessage("token message 123");
		
		assertEquals(transaction.card.getCvc(), "024");
		assertEquals(token.getMessage(), "token message");
		assertEquals(transaction.token.getMessage(), "token message");
		assertEquals(transaction2.card.getPan(), "4153013999700025");
		assertEquals(transaction2.token.getMessage(), "token message 123");
		
		transaction.token = token2;
		
		assertEquals(transaction.token.getMessage(), "token message 123");
	}
}
