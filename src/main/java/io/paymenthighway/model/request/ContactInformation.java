package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactInformation {

  String name;
  @JsonProperty("street_address")
  String streetAddress;
  String city;
  @JsonProperty("postal_code")
  String postalCode;
  @JsonProperty("country_code")
  String countryCode;
  String telephone;

  /**
   * Sub-merchant's contact details, only used if initiated by a Payment Facilitator
   * @param name Doing business as name
   * @param streetAddress Street address
   * @param city City
   * @param postalCode Postal code, e.g. 00200
   * @param countryCode Two letter country code (ISO 3166-1 alpha-2), e.g. FI
   * @param telephone Contanct telephone number in local or international format, e.g. 091234123 or +35891234123
   */
  public ContactInformation(String name, String streetAddress, String city, String postalCode, String countryCode, String telephone) {
    this.name = name;
    this.streetAddress = streetAddress;
    this.city = city;
    this.postalCode = postalCode;
    this.countryCode = countryCode;
    this.telephone = telephone;
  }

  public String getName() {
    return name;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public String getCity() {
    return city;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getTelephone() {
    return telephone;
  }
}
