package io.paymenthighway.model.request;

import io.paymenthighway.json.JsonGenerator;
import io.paymenthighway.model.JsonTestHelper;
import io.paymenthighway.test.TestResources;
import org.junit.Test;

public class PaymentRequestBuilderTest {
    @Test
    public void testPivoRequestBuilder() throws Exception {
        PivoInitRequest request = PivoInitRequest.Builder(1L, "EUR")
            .setWebhookCancelUrl("http://www.example.com/cancel")
            .setWebhookFailureUrl("http://www.example.com/failure")
            .setWebhookSuccessUrl("http://www.example.com/success")
            .setLanguage("FI")
            .setAppUrl("app://url")
            .setOrder("orderNumber")
            .setDescription("simple description")
            .setSubMerchant(TestResources.TestSubMerchant)
            .build();

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

        TestResources.assertTestSubMerchant(json);
    }
}
