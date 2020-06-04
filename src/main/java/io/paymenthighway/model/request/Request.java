package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.paymenthighway.PaymentHighwayUtility;

import java.net.URI;
import java.net.URL;

public abstract class Request {
  @JsonIgnore
  private String requestId;

  public Request() {
    this.requestId = PaymentHighwayUtility.createRequestId();
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  protected String toString(URI uri) {
    if (uri == null) return null;
    else return uri.toString();
  }

  protected String toString(URL url) {
    if (url == null) return null;
    else return url.toString();
  }
}
