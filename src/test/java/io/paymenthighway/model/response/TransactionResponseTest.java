package io.paymenthighway.model.response;

import org.junit.*;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionResponseTest {

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
    Method method = null;
    try {
      method = TransactionResponse.class.getMethod("getResult");
    } catch (Exception e) {
      System.err.print("exception e=" + e);
    }
    assertNotNull(method);
    assertEquals("getResult", method.getName());
  }
}
