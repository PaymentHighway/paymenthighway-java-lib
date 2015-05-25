package io.paymenthighway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Token POJO
 */
public class Token {
  @JsonProperty("id")
  UUID id;

  public Token(String id) {
    this.id = UUID.fromString(id);
  }

  public Token(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }
}
