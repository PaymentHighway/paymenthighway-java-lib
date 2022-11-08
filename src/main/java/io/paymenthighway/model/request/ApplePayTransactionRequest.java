package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.paymenthighway.model.applepay.PaymentData;

public class ApplePayTransactionRequest extends GenericPaymentRequest {

  @JsonProperty("payment_data")
  private PaymentData paymentData;

  @JsonProperty
  private Boolean commit;

  @JsonProperty
  private Customer customer;
  @JsonProperty("reference_number")
  private String referenceNumber;

  public PaymentData getPaymentData() {
    return paymentData;
  }

  public Boolean getCommit() {
    return commit;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String referenceNumber() {
    return referenceNumber;
  }

  public static Builder Builder(PaymentData paymentData, long amount, String currency) {
    return new Builder(paymentData, amount, currency);
  }

  public static class Builder extends GenericPaymentBuilder<ChargeCitRequest.Builder> {

    private PaymentData paymentData;

    private Customer customer = null;
    private Boolean commit;
    private String referenceNumber;

    public Builder(PaymentData paymentData, long amount, String currency) {
      super(amount, currency);
      this.paymentData = paymentData;
    }

    public Builder setCustomer(Customer customer) {
      this.customer = customer;
      return this;
    }

    public Builder setCommit(Boolean commit) {
      this.commit = commit;
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
    super(builder);
    this.paymentData  = builder.paymentData;

    this.customer     = builder.customer;
    this.commit       = builder.commit;
    this.referenceNumber = builder.referenceNumber;
  }
}
