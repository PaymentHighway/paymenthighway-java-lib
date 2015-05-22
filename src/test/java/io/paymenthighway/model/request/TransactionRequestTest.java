package io.paymenthighway.model.request;

import static org.junit.Assert.*;

import io.paymenthighway.model.Token;
import io.paymenthighway.json.JsonGenerator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

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
	public void test1() {

		String pan = "4153013999700024";
		String cvc = "024";
		String expiryYear = "2017";
		String expiryMonth = "11";
		Card card = new Card(pan, cvc, expiryYear, expiryMonth);
		
		TransactionRequest transaction = new TransactionRequest(card, "9999", "EUR", true);

		JsonGenerator jsonGenerator = new JsonGenerator();
		String json = jsonGenerator.createTransactionJson(transaction);
		
		assertTrue(json.contains("pan"));
		assertFalse(json.contains("token"));
	}

	@Test
	public void test2() {

		Token token = new Token(UUID.randomUUID());

		TransactionRequest transaction = new TransactionRequest(token, "9999", "EUR", true);

		JsonGenerator jsonGenerator = new JsonGenerator();
		String json = jsonGenerator.createTransactionJson(transaction);

		assertFalse(json.contains("pan"));
		assertTrue(json.contains("token"));
	}
}
