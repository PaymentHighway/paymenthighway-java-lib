package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.paymenthighway.model.Splitting;

public class GenericPaymentRequest extends Request {

  @JsonProperty("amount")
  protected Long amount;
  @JsonProperty("currency")
  protected String currency;
  @JsonProperty("order")
  protected String order;
  @JsonProperty
  protected Splitting splitting;
  @JsonProperty("sub_merchant")
  protected SubMerchant subMerchant;

  protected <T> GenericPaymentRequest(GenericPaymentBuilder<T> builder) {
    this.amount = builder.amount;
    this.currency = builder.currency;
    this.order = builder.order;
    this.splitting = builder.splitting;
    this.subMerchant = builder.subMerchant;
  }

  /**
   * @return amount
   */
  public Long getAmount() {
    return amount;
  }

  /**
   * @return currency
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * @return order
   */
  public String getOrder() {
    return order;
  }

  /**
   * @return splitting
   */
  public Splitting getSplitting() {
    return splitting;
  }

  /**
   * @return Payment Facilitator's sub-merchant
   */
  public SubMerchant getSubMerchant() {
    return subMerchant;
  }
}
