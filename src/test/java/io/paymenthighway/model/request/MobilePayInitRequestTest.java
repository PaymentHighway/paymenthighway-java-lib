package io.paymenthighway.model.request;

import io.paymenthighway.json.JsonGenerator;
import org.junit.Test;

import java.net.URI;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class MobilePayInitRequestTest {
  @Test
  public void serializeUriAndUrls() throws Exception {
    MobilePayInitRequest request = MobilePayInitRequest.Builder(1000, "EUR")
            .setWebhookSuccessUrl(new URL("https://myapp.server/success"))
            .setWebhookCancelUrl("https://myapp.server/cancel")
            .setReturnUri(new URI("myapp://view"))
            .build();

    JsonGenerator jsonGenerator = new JsonGenerator();
    String json = jsonGenerator.createTransactionJson(request);

    assertTrue(json.contains("https://myapp.server/success"));
    assertTrue(json.contains("https://myapp.server/cancel"));
    assertTrue(json.contains("myapp://view"));
  }
}
