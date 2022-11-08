package io.paymenthighway.model.request.sca;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneNumber {
  @JsonProperty("country_code")
  private String countryCode;
  @JsonProperty
  private String number;

  /**
   *
   * @param countryCode 1-3 digits Country code (ITU-E.164)
   * @param number 1-15 digits
   */
  public PhoneNumber(String countryCode, String number) {
    this.countryCode = countryCode;
    this.number = number;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getNumber() {
    return number;
  }
}
