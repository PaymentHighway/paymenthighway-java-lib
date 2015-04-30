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
		
		TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", true);

		String tokenId = PaymentHighwayUtility.createRequestId();
		
		Token token = new Token(tokenId);
		
		transaction.token = token;
		
		JsonGenerator jsonGenerator = new JsonGenerator();
		String json = jsonGenerator.createTransactionJson(transaction);
		
		assertTrue(json.contains("pan"));
	}
}
