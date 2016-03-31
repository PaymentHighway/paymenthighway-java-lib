package io.paymenthighway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Token POJO
 */
public class Token {
  @JsonProperty("id")
  UUID id;

  String cvc;

  public Token(String id) {
    this.id = UUID.fromString(id);
  }

  public Token(UUID id) {
    this.id = id;
  }

  public Token(String id, String cvc) {
    this.id = UUID.fromString(id);
    this.cvc = cvc;
  }

  public Token(UUID id, String cvc) {
    this.id = id;
    this.cvc = cvc;
  }

  public UUID getId() {
    return id;
  }

  public String getCvc() {
    return cvc;
  }
}
