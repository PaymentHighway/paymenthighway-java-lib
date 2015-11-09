package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Card POJO (with partial pan)
 */
public class PartialCard {

  @JsonProperty("partial_pan")
  String partialPan;
  @JsonProperty("type")
  String type;
  @JsonProperty("expire_year")
  String expireYear;
  @JsonProperty("expire_month")
  String expireMonth;
  @JsonProperty("cvc_required")
  String cvcRequired;

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
}
