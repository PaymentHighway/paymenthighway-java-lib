package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MobilePayStatusResponse extends Response {
  @JsonProperty("status") private String status;
  @JsonProperty("transaction-id") private String transactionId;
  @JsonProperty("valid-until") private String validUntil;

  public String getStatus() {
    return status;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public String getValidUntil() {
    return validUntil;
  }
}
