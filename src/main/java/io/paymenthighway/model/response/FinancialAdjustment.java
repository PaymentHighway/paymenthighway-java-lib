package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinancialAdjustment {

  @JsonProperty("main_acquirer_merchant_id")
  String mainMerchantAcquirerMerchantId;

  @JsonProperty("merchant_description")
  String merchantDescription;

  @JsonProperty("amount")
  Long amount;

  @JsonProperty("currency")
  String currency;

  @JsonProperty("adjustment_description")
  String adjustmentDescription;

  @JsonProperty("date_received")
  String dateReceived;

  @JsonProperty("filing_code")
  String filingCode;

  public String getMainMerchantAcquirerMerchantId() {
    return mainMerchantAcquirerMerchantId;
  }

  public String getMerchantDescription() {
    return merchantDescription;
  }

  public Long getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public String getAdjustmentDescription() {
    return adjustmentDescription;
  }

  public String getDateReceived() {
    return dateReceived;
  }

  public String getFilingCode() {
    return filingCode;
  }
}
