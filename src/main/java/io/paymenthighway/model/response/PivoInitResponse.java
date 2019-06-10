package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PivoInitResponse extends Response {
    @JsonProperty("transaction_id") private String transactionId;
    @JsonProperty("uri") private String uri;
    @JsonProperty("valid_until") private String validUntil;
    @JsonProperty("result") private Result result;

    public String getTransactionId() {
        return transactionId;
    }

    public String getUri() {
        return uri;
    }

    public String getValidUntil() { return validUntil; }

    public Result getResult() { return result; }
}

