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

  public static class Builder {

    private String city;
    private String country;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String postCode;

    private String state;

    public Builder() { }

    public Builder setCity(String city) {
      this.city = city;
      return this;
    }

    public Builder setCountry(String country) {
      this.country = country;
      return this;
    }

    public Builder setAddressLine1(String addressLine1) {
      this.addressLine1 = addressLine1;
      return this;
    }

    public Builder setAddressLine2(String addressLine2) {
      this.addressLine2 = addressLine2;
      return this;
    }

    public Builder setAddressLine3(String addressLine3) {
      this.addressLine3 = addressLine3;
      return this;
    }

    public Builder setPostCode(String postCode) {
      this.postCode = postCode;
      return this;
    }

    public Builder setState(String state) {
      this.state = state;
      return this;
    }

    public Address build() {
      return new Address(this);
    }
  }
}
