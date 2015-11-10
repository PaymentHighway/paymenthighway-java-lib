package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Customer POJO
 */
public class Customer {
  @JsonProperty("network_address")
  String networkAddress;
  @JsonProperty("country_code")
  String countryCode;

  public String getNetworkAddress() {
    return networkAddress;
  }

  public String getCountryCode() {
    return countryCode;
  }
}
