package io.paymenthighway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Splitting payments POJO
 * Requires splitting functionality to be enabled by the Payment Highway contract.
 */
public class Splitting {

  @JsonProperty("merchant_id")
  String merchantId;

  @JsonProperty("amount")
  Long amount;

  public Splitting(String merchantId, Long amount) {
    this.merchantId = merchantId;
    this.amount = amount;
  }

  public String getMerchantId() {
    return merchantId;
  }

  public Long getAmount() {
    return amount;
  }
}
