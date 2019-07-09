package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AfterPay Revert POJO
 */
public class AfterPayRevert {

  @JsonProperty("type")
  protected String type;
  @JsonProperty("amount")
  protected String amount;
  @JsonProperty("timestamp")
  protected String timestamp;
  @JsonProperty("status")
  protected Status status;
  @JsonProperty("afterpay_refund_type")
  protected String afterPayRefundType;
  @JsonProperty("afterpay_refund_number")
  protected String afterPayRefundNumber;

  /**
   * Revert type, may be Refund or Void
   * @return revert type
   */
  public String getType() {
    return type;
  }

  public String getAmount() {
    return amount;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public Status getStatus() {
    return status;
  }

  /**
   * Refund type, may be Return or Refund
   * Only exists, if the type of the revert is Refund
   * @return refund type
   */
  public String getAfterPayRefundType() {
    return afterPayRefundType;
  }

  /**
   * AfterPay given refund number
   * Only exists, if the type of the revert is Refund
   * @return refund number
   */
  public String getAfterPayRefundNumber() {
    return afterPayRefundNumber;
  }
}
