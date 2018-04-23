package io.paymenthighway.model.response;

import io.paymenthighway.json.JsonParser;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class MobilePayStatusResponseTest {
  @Test
  public void inProgress() {
    String json = "{\"status\": \"in_progress\", \"valid_until\": \"2018-12-24T18:00:00Z\"}";
    JsonParser parser = new JsonParser();
    MobilePayStatusResponse response = parser.mapResponse(json, MobilePayStatusResponse.class);

    assertEquals("in_progress", response.getStatus());
    assertEquals("2018-12-24T18:00:00Z", response.getValidUntil());
    assertNull(response.getTransactionId());
  }

  @Test
  public void ok() {
    String txId = UUID.randomUUID().toString();
    String json = String.format("{\"status\": \"ok\", \"transaction_id\": \"%s\"}", txId);
    JsonParser parser = new JsonParser();
    MobilePayStatusResponse response = parser.mapResponse(json, MobilePayStatusResponse.class);

    assertEquals("ok", response.getStatus());
    assertNull(response.getValidUntil());
    assertEquals(txId, response.getTransactionId());
  }
}
