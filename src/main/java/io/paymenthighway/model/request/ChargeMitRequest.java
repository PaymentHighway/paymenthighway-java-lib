package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.paymenthighway.PaymentHighwayUtility;
import io.paymenthighway.model.Splitting;
import io.paymenthighway.model.Token;
import io.paymenthighway.model.request.sca.StrongCustomerAuthentication;

public class ChargeMitRequest extends Request {
  @JsonProperty
  private Long amount;
  @JsonProperty
  private String currency;

  @JsonProperty
  private Token token;
  @JsonProperty
  private Card card;

  @JsonProperty
  private String order;
  @JsonProperty
  private Customer customer;
  @JsonProperty
  private Boolean commit;
  @JsonProperty
  private Splitting splitting;

  private ChargeMitRequest(Builder builder) {
    amount = builder.amount;
    currency = builder.currency;
    token = builder.token;
    card = builder.card;
    order = builder.order;
    customer = builder.customer;
    commit = builder.commit;
    splitting = builder.splitting;
  }

  /**
   * Payment using a card token when the customer is not participating in the payment flow.
   * A contract and understanding between the merchant and the customer must be established, allowing this kind of payments.
   * @param token Card token to charge
   * @param amount Payment amount
   * @param currency Payment currency
   * @param order Merchant-provided order ID for the purchase. Alphanumeric with dashes and underscores. Max length 254.
   * @return Builder
   */
  public static Builder Builder(Token token, Long amount, String currency, String order) {
    return new Builder(token, amount, currency, order);
  }

  /**
   * Payment with card details when the customer is participating in the payment flow. Only for PCI DSS certified parties!
   * A contract and understanding between the merchant and the customer must be established, allowing this kind of payments.
   * The usage of card must be specifically agreed upon. Normally use the token instead.
   * @param card Card to charge (Only for PCI DSS certified parties!)
   * @param amount Payment amount
   * @param currency Payment currency
   * @param order Merchant-provided order ID for the purchase. Alphanumeric with dashes and underscores. Max length 254.
   * @return Builder
   */
  public static Builder Builder(Card card, Long amount, String currency, String order) {
    return new Builder(card, amount, currency, order);
  }

  public static final class Builder {
    private Long amount;
    private String currency;
    private Token token;
    private Card card;
    private String order;
    private Customer customer;
    private Boolean commit;
    private Splitting splitting;

    /**
     * Payment using a card token when the customeris participating in the payment flow.
     * @param token Card token to charge
     * @param amount Payment amount
     * @param currency Payment currency
     * @param order Merchant-provided order ID for the purchase. Alphanumeric with dashes and underscores. Max length 254.
     */
    public Builder(Token token, Long amount, String currency, String order) {
      this.token = token;
      this.amount = amount;
      this.currency = currency;
      this.order = order;
    }

    /**
     * Payment with card details when the customer is participating in the payment flow. Only for PCI DSS certified parties!
     * A contract and understanding between the merchant and the customer must be established, allowing this kind of payments.
     * The usage of card must be specifically agreed upon. Normally use the token instead.
     * @param card Card to charge (Only for PCI DSS certified parties!)
     * @param amount Payment amount
     * @param currency Payment currency
     * @param order Merchant-provided order ID for the purchase. Alphanumeric with dashes and underscores. Max length 254.
     */
    public Builder(Card card, Long amount, String currency, String order) {
      this.card = card;
      this.amount = amount;
      this.currency = currency;
      this.order = order;
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

    public ChargeMitRequest build() {
      return new ChargeMitRequest(this);
    }
  }
}
