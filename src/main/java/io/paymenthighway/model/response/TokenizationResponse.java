package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Tokenization response POJO
 */
public class TokenizationResponse extends Response {

  @JsonProperty("card_token")
  UUID cardToken;
  TokenizationResponse.Card card;
  Customer customer;
  @JsonProperty("cardholder_authentication")
  String cardholderAuthentication;
  @JsonProperty("recurring")
  Boolean recurring;

  public UUID getCardToken() {
    return cardToken;
  }

  public TokenizationResponse.Card getCard() {
    return this.card;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getCardholderAuthentication() {
    return cardholderAuthentication;
  }

  public Boolean getRecurring() { return recurring; }

  public static class Card {
    String type;
    @JsonProperty("partial_pan")
    String partialPan;
    @JsonProperty("expire_year")
    String expireYear;
    @JsonProperty("expire_month")
    String expireMonth;
    @JsonProperty("cvc_required")
    String cvcRequired;
    String bin;
    String funding;
    String category;
    @JsonProperty("country_code")
    String countryCode;

    public String getType() {
      return type;
    }

    public String getPartialPan() {
      return partialPan;
    }

    public String getExpireMonth() {
      return expireMonth;
    }

    public String getExpireYear() {
      return expireYear;
    }

    public String getCvcRequired() {
      return cvcRequired;
    }

    public String getBin() {
      return bin;
    }

    public String getFunding() {
      return funding;
    }

    public String getCategory() {
      return category;
    }

    public String getCountryCode() {
      return countryCode;
    }
  }
}
