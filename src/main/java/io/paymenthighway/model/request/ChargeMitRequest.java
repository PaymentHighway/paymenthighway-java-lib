package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.paymenthighway.model.Token;

public class ChargeMitRequest extends GenericPaymentRequest {
  @JsonProperty
  private Token token;
  @JsonProperty
  private Card card;

  @JsonProperty
  private Customer customer;
  @JsonProperty
  private Boolean commit;
  @JsonProperty("reference_number")
  private String referenceNumber;

  private ChargeMitRequest(Builder builder) {
    super(builder);
    token = builder.token;
    card = builder.card;
    customer = builder.customer;
    commit = builder.commit;
    referenceNumber = builder.referenceNumber;
  }

  public Token getToken() {
    return token;
  }

  public Card getCard() {
    return card;
  }

  public Customer getCustomer() {
    return customer;
  }

  public Boolean getCommit() {
    return commit;
  }

  public String getReferenceNumber() {
    return referenceNumber;
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

  public static final class Builder extends GenericPaymentBuilder<Builder> {
    private Token token;
    private Card card;
    private Customer customer;
    private Boolean commit;
    private String referenceNumber;

    /**
     * Payment using a card token when the customeris participating in the payment flow.
     * @param token Card token to charge
     * @param amount Payment amount
     * @param currency Payment currency
     * @param order Merchant-provided order ID for the purchase. Alphanumeric with dashes and underscores. Max length 254.
     */
    public Builder(Token token, Long amount, String currency, String order) {
      super(amount, currency, order);
      this.token = token;
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
      super(amount, currency, order);
      this.card = card;
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

    public ChargeMitRequest build() {
      return new ChargeMitRequest(this);
    }
  }
}
