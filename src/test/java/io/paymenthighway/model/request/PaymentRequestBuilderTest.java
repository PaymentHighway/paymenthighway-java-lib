package io.paymenthighway.model.request;

import io.paymenthighway.json.JsonGenerator;
import io.paymenthighway.model.JsonTestHelper;
import org.junit.Test;

public class PaymentRequestBuilderTest {
    @Test
    public void testPivoRequestBuilder() {
        PivoInitRequest request = PaymentRequestBuilder.pivoInitRequest(1L, "EUR")
            .webhookCancelUrl("http://www.example.com/cancel")
            .webhookFailureUrl("http://www.example.com/failure")
            .webhookSuccessUrl("http://www.example.com/success")
            .language("FI")
            .appUrl("app://url")
            .order("orderNumber")
            .description("simple description");

        JsonGenerator jsonGenerator = new JsonGenerator();
        String json = jsonGenerator.createTransactionJson(request);

        JsonTestHelper.testJson(json, "webhook_cancel_url", "http://www.example.com/cancel");
        JsonTestHelper.testJson(json, "webhook_failure_url", "http://www.example.com/failure");
        JsonTestHelper.testJson(json, "webhook_success_url", "http://www.example.com/success");
        JsonTestHelper.testJson(json, "app_url", "app://url");
        JsonTestHelper.testJson(json, "amount", 1L);
        JsonTestHelper.testJson(json, "language", "FI");
        JsonTestHelper.testJson(json, "order", "orderNumber");
        JsonTestHelper.testJson(json, "description", "simple description");

    }
}
