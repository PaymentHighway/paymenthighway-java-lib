package io.paymenthighway.model.request;

/**
 * Masterpass Transaction request POJO
 */
public class MasterpassTransactionRequest extends GenericPaymentRequest {

  private Boolean commit;

  public static Builder Builder(long amount, String currency) {
    return new Builder(amount, currency);
  }

  public static class Builder extends GenericPaymentBuilder<Builder> {

    private Boolean commit;

    public Builder(long amount, String currency) {
      super(amount, currency);
    }

    public Builder setCommit(Boolean commit) {
      this.commit = commit;
      return this;
    }

    public MasterpassTransactionRequest build() {
      return new MasterpassTransactionRequest(this);
    }
  }

  private MasterpassTransactionRequest(Builder builder) {
    super(builder);
    this.commit   = builder.commit;
  }

  public Boolean getCommit() {
    return commit;
  }
}
