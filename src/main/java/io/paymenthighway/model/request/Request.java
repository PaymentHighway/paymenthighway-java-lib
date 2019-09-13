package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.paymenthighway.PaymentHighwayUtility;

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
}
