package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Reconciliation report Rejection Item
 */
public class Rejection {

  @JsonProperty("main_acquirer_merchant_id")
  String mainMerchantAcquirerMerchantId;

  @JsonProperty("transaction_type")
  String transactionType;

  @JsonProperty("amount")
  Long amount;

  @JsonProperty("currency")
  String currency;

  @JsonProperty("rejection_reason")
  String rejectionReason;

  @JsonProperty("date_received")
  String dateReceived;

  @JsonProperty("filing_code")
  String filingCode;

  public String getMainMerchantAcquirerMerchantId() {
    return mainMerchantAcquirerMerchantId;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public Long getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public String getRejectionReason() {
    return rejectionReason;
  }

  public String getDateReceived() {
    return dateReceived;
  }

  public String getFilingCode() {
    return filingCode;
  }
}
