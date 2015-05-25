package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Commit Transaction request POJO
 */
public class CommitTransactionResponse extends Response {

  @JsonProperty("card_token")
  UUID cardToken;
  PartialCard card;

  public UUID getCardToken() {
    return cardToken;
  }

  public PartialCard getCard() {
    return this.card;
  }
}
