package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Siirto Refund POJO
 */
public class SiirtoRefund {
  private String id;
  private Status amount;
  @JsonProperty("reference_number")
  private String referenceNumber;
  private String message;
  private String status;
  private String timestamp;

  public String getId() {
    return id;
  }

  public Status getAmount() {
    return amount;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }

  public String getMessage() {
    return message;
  }

  public String getStatus() {
    return status;
  }

  public String getTimestamp() {
    return timestamp;
  }
}
