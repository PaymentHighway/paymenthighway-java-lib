package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.paymenthighway.model.Splitting;
import io.paymenthighway.model.applepay.PaymentData;

public class ApplePayTransactionRequest extends Request {

  @JsonProperty("payment_data")
  private PaymentData paymentData;

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
  @JsonProperty("reference_number")
  private String referenceNumber;
  @JsonProperty
  private Splitting splitting;

  public static Builder Builder(PaymentData paymentData, long amount, String currency) {
    return new Builder(paymentData, amount, currency);
  }

  public static class Builder {

    private PaymentData paymentData;

    private Long amount;
    private String currency;

    private String order = null;
    private Customer customer = null;
    private Boolean commit;
    private String referenceNumber;
    private Splitting splitting;

    public Builder(PaymentData paymentData, long amount, String currency) {
      this.paymentData = paymentData;
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

    public Builder setSplitting(Splitting splitting) {
      this.splitting = splitting;
      return this;
    }

    /**
     * Reference number used when settling the transaction to the merchant account.
     * Only used if one-by-ony transaction settling is configured.
     * @param referenceNumber In RF or Finnish reference number format.
     * @return Builder
     */
    public Builder setReferenceNumber(String referenceNumber) {
      this.referenceNumber = referenceNumber;
      return this;
    }

    public ApplePayTransactionRequest build() {
      return new ApplePayTransactionRequest(this);
    }
  }

  private ApplePayTransactionRequest(Builder builder) {
    // Required parameters
    this.paymentData  = builder.paymentData;

    this.amount       = Long.toString(builder.amount);
    this.currency     = builder.currency;

    // Optional parameters
    this.order        = builder.order;
    this.customer     = builder.customer;
    this.commit       = builder.commit;
    this.referenceNumber = builder.referenceNumber;
    this.splitting = builder.splitting;
  }
}
