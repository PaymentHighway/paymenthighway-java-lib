package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnallocatedTransaction {

  @JsonProperty("filing_code")
  String filingCode;
  @JsonProperty("acquirer_amount_presented")
  String acquirerAmountPresented;
  @JsonProperty("acquirer_amount_presented_currency")
  String acquirerAmountPresentedCurrency;
  @JsonProperty("acquirer_estimated_settlement_value")
  String acquirerEstimatedSettlementValue;
  @JsonProperty("acquirer_estimated_settlement_value_currency")
  String acquirerEstimatedSettlementValueCurrency;
  @JsonProperty("acquirer_exchange_rate")
  String acquirerExchangeRate;
  @JsonProperty("acquirer_discount_rate")
  String acquirerDiscountRate;
  @JsonProperty("acquirer_transaction_fee")
  String acquirerTransactionFee;
  @JsonProperty("acquirer_transaction_fee_currency")
  String acquirerTransactionFeeCurrency;
  @JsonProperty("acquirer_commission")
  String acquirerCommission;
  @JsonProperty("acquirer_commission_currency")
  String acquirerCommissionCurrency;
  @JsonProperty("partial_pan")
  String partialPan;
  @JsonProperty("card_type")
  String cardType;
  @JsonProperty("transaction_date")
  String transactionDate;

  public String getFilingCode() {
    return filingCode;
  }

  public String getAcquirerAmountPresented() {
    return acquirerAmountPresented;
  }

  public String getAcquirerAmountPresentedCurrency() {
    return acquirerAmountPresentedCurrency;
  }

  public String getAcquirerEstimatedSettlementValue() {
    return acquirerEstimatedSettlementValue;
  }

  public String getAcquirerEstimatedSettlementValueCurrency() {
    return acquirerEstimatedSettlementValueCurrency;
  }

  public String getAcquirerExchangeRate() {
    return acquirerExchangeRate;
  }

  public String getAcquirerDiscountRate() {
    return acquirerDiscountRate;
  }

  public String getAcquirerTransactionFee() {
    return acquirerTransactionFee;
  }

  public String getAcquirerTransactionFeeCurrency() {
    return acquirerTransactionFeeCurrency;
  }

  public String getAcquirerCommission() {
    return acquirerCommission;
  }

  public String getAcquirerCommissionCurrency() {
    return acquirerCommissionCurrency;
  }

  public String getPartialPan() {
    return partialPan;
  }

  public String getCardType() {
    return cardType;
  }

  public String getTransactionDate() {
    return transactionDate;
  }
}
