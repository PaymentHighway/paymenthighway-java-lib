package io.paymenthighway.model.response.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * This class contains methods to get person set to receive the shipped order.
 **/
public class ShippingAddress {

  @JsonProperty(value = "recipient_name", required = false)
  private String recipientName = null;

  @JsonProperty(value = "recipient_phone_number", required = false)
  private String recipientPhoneNumber = null;

  @JsonProperty(value = "line1", required = true)
  private String line1 = null;

  @JsonProperty(value = "line2", required = false)
  private String line2 = null;

  @JsonProperty(value = "line3", required = false)
  private String line3 = null;

  @JsonProperty(value = "postal_code", required = false)
  private String postalCode = null;

  @JsonProperty(value = "city", required = true)
  private String city = null;

  @JsonProperty(value = "country_subdivision", required = false)
  private String countrySubdivision = null;

  @JsonProperty(value = "country", required = true)
  private String country = null;


  /**
   * @return Name of person set to receive the shipped order.
   **/
  public String getRecipientName() {
    return recipientName;
  }

  /**
   * @return Phone number of the person set to receive the shipped order.
   **/
  public String getRecipientPhoneNumber() {
    return recipientPhoneNumber;
  }

  /**
   * @return Address line 1 used for Street number and Street Name.
   **/
  public String getLine1() {
    return line1;
  }

  /**
   * @return Address line 2 used for Apt Number, Suite Number, and so on.
   **/
  public String getLine2() {
    return line2;
  }

  /**
   * @return Address line 3 used to enter remaining address information if it does not fit in Line 1 and Line 2.
   **/
  public String getLine3() {
    return line3;
  }

  /**
   * @return Postal code or zip code appended to mailing address for the purpose of sorting mail.
   **/
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * @return Cardholder's city.
   **/
  public String getCity() {
    return city;
  }

  /**
   * @return Cardholder's country subdivision. Defined by ISO 3166-1 alpha-2 digit code, for example, US-VA is Virginia, US-OH is Ohio
   **/
  public String getCountrySubdivision() {
    return countrySubdivision;
  }

  /**
   * @return Cardholder's country. Defined by ISO 3166-1 alpha-2 digit country codes, for example, US is United States, AU is Australia, CA is Canada, GB is United Kingdom, and so on.
   **/
  public String getCountry() {
    return country;
  }


  /**
   * Indicates whether some other object is "equal to" this one.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShippingAddress shippingAddress = (ShippingAddress) o;
    return Objects.equals(recipientName, shippingAddress.recipientName) &&
      Objects.equals(recipientPhoneNumber, shippingAddress.recipientPhoneNumber) &&
      Objects.equals(line1, shippingAddress.line1) &&
      Objects.equals(line2, shippingAddress.line2) &&
      Objects.equals(line3, shippingAddress.line3) &&
      Objects.equals(postalCode, shippingAddress.postalCode) &&
      Objects.equals(city, shippingAddress.city) &&
      Objects.equals(countrySubdivision, shippingAddress.countrySubdivision) &&
      Objects.equals(country, shippingAddress.country);
  }
  
  /**
  *	Generates a hash code for a sequence of input values.
  */
  @Override
  public int hashCode() {
    return Objects.hash(recipientName, recipientPhoneNumber, line1, line2, line3, postalCode, city, countrySubdivision, country);
  }
}
