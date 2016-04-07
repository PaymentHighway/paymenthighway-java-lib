package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Merchant POJO
 */
public class Merchant {
  String id;
  String name;
  @JsonProperty("acquirer_merchant_id")
  String acquirerMerchantId;

  public Merchant() {
  }

  public Merchant(String id, String name, String acquirerMerchantId) {
    this.id = id;
    this.name = name;
    this.acquirerMerchantId = acquirerMerchantId;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAcquirerMerchantId() {
    return acquirerMerchantId;
  }
}
