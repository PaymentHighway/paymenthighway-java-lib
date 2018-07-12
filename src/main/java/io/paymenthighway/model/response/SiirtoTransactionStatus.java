package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Siirto Transaction status POJO
 */
public class SiirtoTransactionStatus {
  private UUID id;
  private String type;
  private String amount;
  @JsonProperty("current_amount")
  private String currentAmount;
  private String currency;
  private String timestamp;
  @JsonProperty("status")
  private Status status;
  @JsonProperty("reference_number")
  private String referenceNumber;
  private Customer customer;
  private String order;
  private String message;
  private String recipient;
  private SiirtoRefund[] refunds;

  public UUID getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public String getAmount() {
    return amount;
  }

  public String getCurrentAmount() {
    return currentAmount;
  }

  public String getCurrency() {
    return currency;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public Status getStatus() {
    return status;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getOrder() {
    return order;
  }

  public String getMessage() {
    return message;
  }

  public String getRecipient() {
    return recipient;
  }

  public SiirtoRefund[] getRefunds() {
    return refunds;
  }
}
