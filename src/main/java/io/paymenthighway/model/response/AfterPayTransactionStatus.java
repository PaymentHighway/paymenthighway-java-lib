package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.paymenthighway.model.Splitting;

import java.util.UUID;

/**
 * AfterPay Transaction status POJO
 */
public class AfterPayTransactionStatus {
  UUID id;
  String type;
  Long amount;
  @JsonProperty("current_amount")
  Long currentAmount;
  String currency;
  String timestamp;
  String modified;
  @JsonProperty("status")
  Status status;
  @JsonProperty("reverts")
  AfterPayRevert[] reverts;
  Customer customer;
  String order;
  @JsonProperty("committed")
  private Boolean committed;
  @JsonProperty("committed_amount")
  private String committedAmount;

  @JsonProperty("afterpay_payment_type")
  private String afterPayPaymentType;
  @JsonProperty("afterpay_checkout_id")
  private String afterPayCheckoutId;
  @JsonProperty("afterpay_reservation_id")
  private String afterPayReservationId;
  @JsonProperty("afterpay_customer_number")
  private String afterPayCustomerNumber;
  @JsonProperty("afterpay_capture_number")
  private String afterPayCaptureNumber;
  @JsonProperty("afterpay_outcome")
  private String afterPayOutcome;
  @JsonProperty("reference_number")
  private String referenceNumber;
  @JsonProperty("splitting")
  private Splitting splitting;

  public UUID getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public Long getAmount() {
    return amount;
  }

  public Long getCurrentAmount() {
    return currentAmount;
  }

  public String getCurrency() {
    return currency;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getModified() {
    return modified;
  }

  public Status getStatus() {
    return status;
  }

  public AfterPayRevert[] getReverts() {
    return reverts;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getOrder() {
    return order;
  }

  public Boolean getCommitted() {
    return committed;
  }

  public String getCommittedAmount() {
    return committedAmount;
  }

  public String getAfterPayPaymentType() {
    return afterPayPaymentType;
  }

  public String getAfterPayCheckoutId() {
    return afterPayCheckoutId;
  }

  public String getAfterPayReservationId() {
    return afterPayReservationId;
  }

  public String getAfterPayCustomerNumber() {
    return afterPayCustomerNumber;
  }

  public String getAfterPayCaptureNumber() {
    return afterPayCaptureNumber;
  }

  public String getAfterPayOutcome() {
    return afterPayOutcome;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }

  /**
   * @return Splitting information if the transaction was splitted into sub-merchant and commission parts.
   */
  public Splitting getSplitting() {
    return splitting;
  }
}
