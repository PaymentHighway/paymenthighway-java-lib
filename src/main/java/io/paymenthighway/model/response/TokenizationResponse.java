package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Tokenization response POJO
 */
public class TokenizationResponse {

  @JsonProperty("card_token")
  UUID cardToken;
  TokenizationResponse.Card card;
  Result result;

  public UUID getCardToken() {
    return cardToken;
  }

  public TokenizationResponse.Card getCard() {
    return this.card;
  }

  public Result getResult() {
    return this.result;
  }

  public static class Card {
    String type;
    @JsonProperty("partial_pan")
    String partialPan;
    @JsonProperty("expire_year")
    String expireYear;
    @JsonProperty("expire_month")
    String expireMonth;

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
  }
}
