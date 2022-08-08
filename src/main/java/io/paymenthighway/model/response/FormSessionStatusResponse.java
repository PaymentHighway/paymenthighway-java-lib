package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Form Session Status Response POJO
 */
public class FormSessionStatusResponse extends Response {

  @JsonProperty("status")
  Status status;

  @JsonProperty("transaction_id")
  String transactionId;

  @JsonProperty("operation")
  String operation;

  @JsonProperty("created")
  String created;

  @JsonProperty("valid_until")
  String validUntil;

  public Status getStatus() {
    return status;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public String getOperation() {
    return operation;
  }

  public String getCreated() {
    return created;
  }

  public String getValidUntil() {
    return validUntil;
  }
}


