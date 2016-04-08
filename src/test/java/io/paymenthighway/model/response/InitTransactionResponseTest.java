package io.paymenthighway.model.response;

import io.paymenthighway.json.JsonParser;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 */
public class InitTransactionResponseTest {

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
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testInitTransactionResponse() {
    String json = "{\"id\":\"4b402052-c2fd-44cf-bf44-de4cbe7d2d18\",\"result\":{\"code\":100,\"message\":\"OK\"}}";
    JsonParser parser = new JsonParser();
    InitTransactionResponse response = parser.mapResponse(json, InitTransactionResponse.class);

    assertEquals("4b402052-c2fd-44cf-bf44-de4cbe7d2d18", response.getId().toString());
    assertEquals("100", response.getResult().getCode());
    assertEquals("OK", response.getResult().getMessage());
  }
}
