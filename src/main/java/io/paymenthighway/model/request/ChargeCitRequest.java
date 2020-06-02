package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.paymenthighway.model.Splitting;
import io.paymenthighway.model.Token;
import io.paymenthighway.model.request.sca.StrongCustomerAuthentication;

public class ChargeCitRequest extends Request {
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
  @JsonProperty("strong_customer_authentication")
  private StrongCustomerAuthentication strongCustomerAuthentication;
  @JsonProperty("reference_number")
  private String referenceNumber;

  private ChargeCitRequest(Builder builder) {
    amount = builder.amount;
    currency = builder.currency;
    token = builder.token;
    card = builder.card;
    order = builder.order;
    customer = builder.customer;
    commit = builder.commit;
    splitting = builder.splitting;
    strongCustomerAuthentication = builder.strongCustomerAuthentication;
    referenceNumber = builder.referenceNumber;
  }

  /**
   * Payment using a card token when the customer is participating in the payment flow.
   * @param token Card token to charge
   * @param amount Payment amount
   * @param currency Payment currency
   * @param order Merchant-provided order ID for the purchase. Alphanumeric with dashes and underscores. Max length 254.
   * @param strongCustomerAuthentication Information provided for the SCA in case of a soft decline response from the issuer
   * @return Builder
   */
  public static Builder Builder(Token token, Long amount, String currency, String order, StrongCustomerAuthentication strongCustomerAuthentication) {
    return new Builder(token, amount, currency, order, strongCustomerAuthentication);
  }

  /**
   * Payment with card details when the customer is participating in the payment flow. Only for PCI DSS certified parties!
   * The usage of card must be specifically agreed upon. Normally use the token instead.
   * @param card Card to charge (Only for PCI DSS certified parties!)
   * @param amount Payment amount
   * @param currency Payment currency
   * @param order Merchant-provided order ID for the purchase. Alphanumeric with dashes and underscores. Max length 254.
   * @param strongCustomerAuthentication Information provided for the SCA in case of a soft decline response from the issuer
   * @return Builder
   */
  public static Builder Builder(Card card, Long amount, String currency, String order, StrongCustomerAuthentication strongCustomerAuthentication) {
    return new Builder(card, amount, currency, order, strongCustomerAuthentication);
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
    private StrongCustomerAuthentication strongCustomerAuthentication;
    private String referenceNumber;

    /**
     * Payment using a card token when the customer is participating in the payment flow.
     * @param token Card token to charge
     * @param amount Payment amount
     * @param currency Payment currency
     * @param order Merchant-provided order ID for the purchase. Alphanumeric with dashes and underscores. Max length 254.
     * @param strongCustomerAuthentication Information provided for the SCA in case of a soft decline response from the issuer
     */
    public Builder(Token token, Long amount, String currency, String order, StrongCustomerAuthentication strongCustomerAuthentication) {
      this.token = token;
      this.amount = amount;
      this.currency = currency;
      this.order = order;
      this.strongCustomerAuthentication = strongCustomerAuthentication;
    }

    /**
     * Payment with card details when the customer is participating in the payment flow. Only for PCI DSS certified parties!
     * The usage of card must be specifically agreed upon. Normally use the token instead.
     * @param card Card to charge (Only for PCI DSS certified parties!)
     * @param amount Payment amount
     * @param currency Payment currency
     * @param order Merchant-provided order ID for the purchase. Alphanumeric with dashes and underscores. Max length 254.
     * @param strongCustomerAuthentication Information provided for the SCA in case of a soft decline response from the issuer
     */
    public Builder(Card card, Long amount, String currency, String order, StrongCustomerAuthentication strongCustomerAuthentication) {
      this.card = card;
      this.amount = amount;
      this.currency = currency;
      this.order = order;
      this.strongCustomerAuthentication = strongCustomerAuthentication;
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

    public ChargeCitRequest build() {
      return new ChargeCitRequest(this);
    }
  }
}
