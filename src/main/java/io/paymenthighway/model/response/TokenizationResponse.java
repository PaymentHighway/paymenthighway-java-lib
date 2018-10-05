package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Tokenization response POJO
 */
public class TokenizationResponse extends Response {

  @JsonProperty("card_token")
  UUID cardToken;
  PartialCard card;
  Customer customer;
  @JsonProperty("cardholder_authentication")
  String cardholderAuthentication;
  @JsonProperty("recurring")
  Boolean recurring;

  public UUID getCardToken() {
    return cardToken;
  }

  public PartialCard getCard() {
    return this.card;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getCardholderAuthentication() {
    return cardholderAuthentication;
  }

  public Boolean getRecurring() { return recurring; }

}
