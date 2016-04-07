package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Commission Settlement POJO
 */
public class CommissionSettlement {

  @JsonProperty("acquirer_batch_id")
  String acquirerBatchId;
  @JsonProperty("batch")
  String batch;
  @JsonProperty("date_processed")
  String dateProcessed;
  @JsonProperty("reference")
  String reference;
  @JsonProperty("acquirer")
  Acquirer acquirer;
  String amount;
  @JsonProperty("currency")
  String currency;
  @JsonProperty("main_acquirer_merchant_id")
  String mainAcquirerMerchantId;

  public String getAcquirerBatchId() {
    return acquirerBatchId;
  }

  public String getBatch() {
    return batch;
  }

  public String getDateProcessed() {
    return dateProcessed;
  }

  public String getReference() {
    return reference;
  }

  public Acquirer getAcquirer() {
    return acquirer;
  }

  public String getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public String getMainAcquirerMerchantId() {
    return mainAcquirerMerchantId;
  }
}
