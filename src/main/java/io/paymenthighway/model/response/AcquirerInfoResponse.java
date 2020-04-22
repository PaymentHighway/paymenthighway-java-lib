package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AcquirerInfoResponse extends Response{
    @JsonProperty("acquirer")
    private Acquirer acquirer;
    @JsonProperty("acquirer_response_code")
    private String acquirerResponseCode;
    @JsonProperty("authorizer")
    private String authorizer;

    public Acquirer getAcquirer() {
        return acquirer;
    }

    public String getAcquirerResponseCode() {
        return acquirerResponseCode;
    }

    public String getAuthorizer() {
        return authorizer;
    }
}
