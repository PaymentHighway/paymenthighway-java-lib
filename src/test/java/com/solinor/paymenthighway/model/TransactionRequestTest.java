package com.solinor.paymenthighway.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.solinor.paymenthighway.PaymentHighwayUtility;
import com.solinor.paymenthighway.json.JsonGenerator;
import com.solinor.paymenthighway.model.Card;
import com.solinor.paymenthighway.model.Token;

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

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, cvc, expiryYear, expiryMonth, verification);
		
		TransactionRequest transaction = new TransactionRequest("9999", "EUR", true, card);

		String code = PaymentHighwayUtility.createRequestId();
		String message = "this is a token message";
		Token token = new Token(code, message);
		
		transaction.token = token;
		
		JsonGenerator jsonGenerator = new JsonGenerator();
		String json = jsonGenerator.createTransactionJson(transaction);
		assertTrue(json.contains("pan"));
	}

	@Test
	public void testStaticNestedClass() {
		
		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth, cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest("9999", "EUR", true, card);
		
		String code = PaymentHighwayUtility.createRequestId();
		String message = "this is a token message";
		Token token = new Token(code, message);
		
		transaction.token = token;
		
		assertEquals(transaction.token.getMessage(), "this is a token message");
		
		String code2 = PaymentHighwayUtility.createRequestId();
		String message2 = "this is a token message 2";
		Token token2 = new Token(code2, message2);
		
		transaction.token = token;
		
		assertEquals(transaction.token.getMessage(), "this is a token message");
		
		transaction.token = token2;
		
		assertEquals(transaction.token.getMessage(), "this is a token message 2");
		
	}
	
	@Test
	public void testStaticNestedClass2() {

		
		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		String verification = "";
		Card card = new Card(pan, expiryYear, expiryMonth, cvc, verification);
		
		TransactionRequest transaction = new TransactionRequest("1111", "EUR", true, card);
		
		String code = PaymentHighwayUtility.createRequestId();
		String message = "this is a token message";
		Token token = new Token(code, message);
		
		transaction.token = token;
		
		assertEquals(transaction.card.pan, "4153013999700024");
		assertEquals(transaction.token.getMessage(), "this is a token message");
		
		String pan2 = "4153013999700025";
		String cvc2 = "024";
		String expiryYear2 = "2017";
		String expiryMonth2 = "11";
		String verification2 = "";
		Card card2 = new Card(pan2, expiryYear2, expiryMonth2, cvc2, verification2);

		TransactionRequest transaction2 = new TransactionRequest("2222", "EUR", true, card2);
		
		String code2 = PaymentHighwayUtility.createRequestId();
		String message2 = "this is a token message 2";
		Token token2 = new Token(code2, message2);
		// transaction2.token = token2;
		transaction2 = new TransactionRequest("2222", "EUR", true, card2, token2);
		
		// these shouldn't change
		assertEquals(transaction.card.pan, "4153013999700024");
		assertEquals(transaction.token.getMessage(), "this is a token message");
		
		card2.cvc = "123";
		token2.message = "token message 123";
		
		assertEquals(transaction.card.cvc, "024");
		assertEquals(token.getMessage(), "this is a token message");
		assertEquals(transaction.token.getMessage(), "this is a token message");
		assertEquals(transaction.card.pan, "4153013999700024");

		assertEquals(transaction2.card.pan, "4153013999700025");
		assertEquals(transaction2.token.getMessage(), "token message 123");
		
		transaction.token = token2;
		
		assertEquals(transaction.token.getMessage(), "token message 123");
	}
}
