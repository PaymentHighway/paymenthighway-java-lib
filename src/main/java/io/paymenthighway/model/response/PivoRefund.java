package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pivo Refund POJO
 */
public class PivoRefund {

  @JsonProperty("refund_request_id")
  private String refundRequestequestId;
  private Status amount;
  private String timestamp;
  @JsonProperty("reference_number")
  private String referenceNumber;

  public String getRefundRequestequestId() {
    return refundRequestequestId;
  }

  public Status getAmount() {
    return amount;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }
}
