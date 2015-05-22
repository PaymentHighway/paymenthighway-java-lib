package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Card POJO
 */
public class Card {

  @JsonProperty("pan")
  String pan;
  @JsonProperty("expiry_year")
  String expiryYear;
  @JsonProperty("expiry_month")
  String expiryMonth;
  @JsonProperty("cvc")
  String cvc;

  public Card(String pan, String expiryYear, String expiryMonth, String cvc) {
    this.pan = pan;
    this.expiryYear = expiryYear;
    this.expiryMonth = expiryMonth;
    this.cvc = cvc;
  }

  public String getPan() {
    return pan;
  }

  public String getExpiryYear() {
    return expiryYear;
  }

  public String getExpiryMonth() {
    return expiryMonth;
  }

  public String getCvc() {
    return cvc;
  }
}
