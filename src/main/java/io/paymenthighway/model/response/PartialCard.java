package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Card POJO (with partial pan)
 */
public class PartialCard {

  @JsonProperty("partial_pan")
  private String partialPan;
  @JsonProperty("type")
  private String type;
  @JsonProperty("expire_year")
  private String expireYear;
  @JsonProperty("expire_month")
  private String expireMonth;
  @JsonProperty("cvc_required")
  private String cvcRequired;
  @JsonProperty("bin")
  private String bin;

  @JsonProperty("funding")
  private String funding;
  @JsonProperty("category")
  private String category;

  @JsonProperty("country_code")
  private String countryCode;

  @JsonProperty("card_fingerprint")
  private String cardFingerprint;
  @JsonProperty("pan_fingerprint")
  private String panFingerprint;

  public String getPartialPan() {
    return partialPan;
  }

  public String getType() {
    return type;
  }

  public String getExpireYear() {
    return expireYear;
  }

  public String getExpireMonth() {
    return expireMonth;
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

  public String getCardFingerprint() {
    return cardFingerprint;
  }

  public String getPanFingerprint() {
    return panFingerprint;
  }
}
