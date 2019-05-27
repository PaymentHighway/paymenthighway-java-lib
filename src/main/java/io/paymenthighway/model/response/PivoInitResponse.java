package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PivoInitResponse extends Response {
    @JsonProperty("uri") private String uri;
    @JsonProperty("valid_until") private String validUntil;

    public String getUri() {
        return uri;
    }

    public String getValidUntil() { return validUntil; }
}
