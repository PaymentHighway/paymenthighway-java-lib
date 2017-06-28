package io.paymenthighway.model.response.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * This class contains methods to get consumer address details.
 **/
public class BillingAddress {

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
    BillingAddress address = (BillingAddress) o;
    return Objects.equals(line1, address.line1) &&
      Objects.equals(line2, address.line2) &&
      Objects.equals(line3, address.line3) &&
      Objects.equals(postalCode, address.postalCode) &&
      Objects.equals(city, address.city) &&
      Objects.equals(countrySubdivision, address.countrySubdivision) &&
      Objects.equals(country, address.country);
  }
  
  /**
  *	Generates a hash code for a sequence of input values.
  */
  @Override
  public int hashCode() {
    return Objects.hash(line1, line2, line3, postalCode, city, countrySubdivision, country);
  }
}
