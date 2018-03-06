package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.paymenthighway.model.applepay.PaymentToken;

public class ApplePayTransactionRequest extends Request {

  @JsonProperty("payment_data")
  private PaymentToken token;

  @JsonProperty
  private String amount;
  @JsonProperty
  private String currency;

  @JsonProperty
  private Boolean commit;

  @JsonProperty
  private String order;
  @JsonProperty
  private Customer customer;

  public static Builder Builder(PaymentToken token, long amount, String currency) {
    return new Builder(token, amount, currency);
  }

  public static class Builder {

    private PaymentToken token;

    private Long amount;
    private String currency;

    private String order = null;
    private Customer customer = null;
    private Boolean commit;

    public Builder(PaymentToken token, long amount, String currency) {
      this.token = token;
      this.amount = amount;
      this.currency = currency;
    }

    public Builder setOrder(String order) {
      this.order = order;
      return this;
    }

    public Builder setCustomer(Customer customer) {
      this.customer = customer;
      return this;
    }

    public Builder setCommit(Boolean commit) {
      this.commit = commit;
      return this;
    }

    public ApplePayTransactionRequest build() {
      return new ApplePayTransactionRequest(this);
    }
  }

  private ApplePayTransactionRequest(Builder builder) {
    // Required parameters
    this.token    = builder.token;

    this.amount   = Long.toString(builder.amount);
    this.currency = builder.currency;

    // Optional parameters
    this.order    = builder.order;
    this.customer = builder.customer;
    this.commit   = builder.commit;
  }
}
