package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MobilePayInitResponse extends Response {
  @JsonProperty("session_token") private String sessionToken;
  @JsonProperty("uri") private String uri;
  @JsonProperty("valid_until") private String validUntil;

  public String getSessionToken() {
    return sessionToken;
  }

  public String getUri() {
    return uri;
  }

  public String getValidUntil() { return validUntil; }
}
