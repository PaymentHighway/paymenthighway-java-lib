package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pivo Refund POJO
 */
public class PivoRefund {

  @JsonProperty("refund_request_id")
  private String refundRequestId;
  private String amount;
  private String timestamp;
  @JsonProperty("reference_number")
  private String referenceNumber;

  public String getRefundRequestId() {
    return refundRequestId;
  }

  public String getAmount() {
    return amount;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }
}
