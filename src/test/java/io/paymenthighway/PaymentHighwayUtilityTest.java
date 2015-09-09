package io.paymenthighway;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 */
public class PaymentHighwayUtilityTest {

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
  public void testCreateRequestId() {
    String id = PaymentHighwayUtility.createRequestId();
    // test not null
    assertNotNull(id);
    // length must be 36
    assertEquals(36, id.length());
  }

  @Test
  public void testGetUTCTimestamp() {
    String time = PaymentHighwayUtility.getUtcTimestamp();
    // test not null
    assertNotNull(time);
    // length must be 20
    assertEquals(20, time.length());
  }

  @Test
  public void testReadProperties() {
    Properties p = null;
    try {
      p = PaymentHighwayUtility.getProperties();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert p != null;
    assertEquals(5, p.size());
    assertTrue(p.containsKey("service_url"));
    assertTrue(p.containsKey("sph-account"));
    assertTrue(p.containsKey("sph-merchant"));
    assertTrue(p.containsKey("signature_key_id"));
    assertTrue(p.containsKey("signature_secret"));
  }
}

