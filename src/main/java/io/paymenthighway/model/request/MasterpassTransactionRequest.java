package io.paymenthighway.model.request;

/**
 * Masterpass Transaction request POJO
 */
public class MasterpassTransactionRequest extends Request {

  private String amount = null;
  private String currency = null;

  private Boolean commit;

  public static Builder Builder(long amount, String currency) {
    return new Builder(amount, currency);
  }

  public static class Builder {

    private Long amount = null;
    private String currency = null;

    private Boolean commit;


    public Builder(long amount, String currency) {
      this.amount = amount;
      this.currency = currency;
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
    // Required parameters
    this.amount   = Long.toString(builder.amount);
    this.currency = builder.currency;

    // Optional parameters
    this.commit   = builder.commit;
  }

  public String getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public Boolean getCommit() {
    return commit;
  }
}
