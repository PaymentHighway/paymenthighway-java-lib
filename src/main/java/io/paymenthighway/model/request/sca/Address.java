package io.paymenthighway.model.request.sca;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

  @JsonProperty
  private String city;

  @JsonProperty
  private String country;

  @JsonProperty("address_line_1")
  private String addressLine1;

  @JsonProperty("address_line_2")
  private String addressLine2;

  @JsonProperty("address_line_3")
  private String addressLine3;

  @JsonProperty("post_code")
  private String postCode;

  @JsonProperty
  private String state;

  private Address(Builder builder) {
    this.city = builder.city;
    this.country = builder.country;
    this.addressLine1 = builder.addressLine1;
    this.addressLine2 = builder.addressLine2;
    this.addressLine3 = builder.addressLine3;
    this.postCode = builder.postCode;
    this.state = builder.state;
  }

  public static Builder Builder() {
    return new Builder();
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public String getAddressLine3() {
    return addressLine3;
  }

  public String getPostCode() {
    return postCode;
  }

  public String getState() {
    return state;
  }

  public static class Builder {

    private String city;
    private String country;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String postCode;

    private String state;

    public Builder() { }

    /**
     * @param city Max length 50
     * @return Builder
     */
    public Builder setCity(String city) {
      this.city = city;
      return this;
    }

    /**
     * 3 digit country code, ISO 3166-1
     * For example:
     *  Canada: 124
     *  China: 156
     *  Denmark: 208
     *  Estonia: 233
     *  Finland: 246
     *  France: 250
     *  Germany: 276
     *  Japan: 392
     *  Netherlands: 528
     *  Norway: 578
     *  Poland: 616
     *  Russia: 643
     *  Spain: 724
     *  Sweden: 752
     *  Switzerland: 756
     *  United Kingdom: 826
     *  United States of America: 840
     *
     * @see <a href="https://en.wikipedia.org/wiki/ISO_3166-1_numeric">https://www.iso.org/obp/ui/#search/code/</a>
     * @see <a href="https://en.wikipedia.org/wiki/ISO_3166-1_numeric">https://en.wikipedia.org/wiki/ISO_3166-1_numeric</a>
     *
     * @param country 3 digits country code, ISO 3166-1 numeric
     * @return Builder
     */
    public Builder setCountry(String country) {
      this.country = country;
      return this;
    }

    /**
     * @param addressLine1 Max length 50
     * @return Builder
     */
    public Builder setAddressLine1(String addressLine1) {
      this.addressLine1 = addressLine1;
      return this;
    }

    /**
     * @param addressLine2 Max length 50
     * @return Builder
     */
    public Builder setAddressLine2(String addressLine2) {
      this.addressLine2 = addressLine2;
      return this;
    }

    /**
     * @param addressLine3 Max length 50
     * @return Builder
     */
    public Builder setAddressLine3(String addressLine3) {
      this.addressLine3 = addressLine3;
      return this;
    }

    /**
     * Post code, Zip Code
     * @param postCode Max length 16
     * @return Builder
     */
    public Builder setPostCode(String postCode) {
      this.postCode = postCode;
      return this;
    }

    /**
     * @param state Two digit ISO 3166-2 country subdivision code
     * @return Builder
     */
    public Builder setState(String state) {
      this.state = state;
      return this;
    }

    public Address build() {
      return new Address(this);
    }
  }
}
