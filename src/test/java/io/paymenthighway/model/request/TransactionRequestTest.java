package io.paymenthighway.model.request;

import io.paymenthighway.json.JsonGenerator;
import io.paymenthighway.model.Token;
import org.junit.*;

import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    TransactionRequest transaction = TransactionRequest.Builder(card, 9999, "EUR").build();

    JsonGenerator jsonGenerator = new JsonGenerator();
    String json = jsonGenerator.createTransactionJson(transaction);

    assertTrue(json.contains("pan"));
    assertFalse(json.contains("token"));
  }

  @Test
  public void test2() {

    Token token = new Token(UUID.randomUUID());

    TransactionRequest transaction = TransactionRequest.Builder(token, 9999, "EUR").build();

    JsonGenerator jsonGenerator = new JsonGenerator();
    String json = jsonGenerator.createTransactionJson(transaction);

    assertFalse(json.contains("pan"));
    assertTrue(json.contains("token"));
  }
}
