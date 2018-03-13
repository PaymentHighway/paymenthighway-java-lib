package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;

public class MobilePayInitResponse extends Response {
  @JsonProperty("session_token") private String sessionToken;
  @JsonProperty("uri") private URI uri;

  public String getSessionToken() {
    return sessionToken;
  }

  public URI getUri() {
    return uri;
  }
}
