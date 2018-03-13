package io.paymenthighway.model.response;

import io.paymenthighway.json.JsonParser;
import org.junit.*;

import java.net.URI;

import static org.junit.Assert.assertEquals;

public class MobilePayInitResponseTest {
  @Test
  public void testParsing() throws Exception {
    String json = "{\"session_token\":\"11d22268-cc71-4894-bf41-e13a9039c327\",\"uri\":\"mobilepay://test\"}";
    System.out.println(json);

    JsonParser parser = new JsonParser();
    MobilePayInitResponse response = parser.mapResponse(json, MobilePayInitResponse.class);

    assertEquals("11d22268-cc71-4894-bf41-e13a9039c327", response.getSessionToken());
    assertEquals(new URI("mobilepay://test"), response.getUri());
  }
}
