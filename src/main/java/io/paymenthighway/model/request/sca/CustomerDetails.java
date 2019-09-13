package io.paymenthighway.model.request.sca;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDetails {

  @JsonProperty(value = "shipping_address_matches_billing_address")
  private Boolean shippingAddressMatchesBillingAddress;

  @JsonProperty
  private String name;

  @JsonProperty
  private String email;

  @JsonProperty(value = "home_phone")
  private PhoneNumber homePhone;

  @JsonProperty(value = "mobile_phone")
  private PhoneNumber mobilePhone;

  @JsonProperty(value = "work_phone")
  private PhoneNumber workPhone;

  private CustomerDetails(Builder builder) {
    shippingAddressMatchesBillingAddress = builder.shippingAddressMatchesBillingAddress;
    name = builder.name;
    email = builder.email;
    homePhone = builder.homePhone;
    mobilePhone = builder.mobilePhone;
    workPhone = builder.workPhone;
  }

  public static Builder Builder() {
    return new Builder();
  }

  public static final class Builder {
    private Boolean shippingAddressMatchesBillingAddress;
    private String name;
    private String email;
    private PhoneNumber homePhone;
    private PhoneNumber mobilePhone;
    private PhoneNumber workPhone;

    public Builder() { }

    public Builder setShippingAddressMatchesBillingAddress(Boolean shippingAddressMatchesBillingAddress) {
      this.shippingAddressMatchesBillingAddress = shippingAddressMatchesBillingAddress;
      return this;
    }

    /**
     * The customer's name
     * @param name Max length: 45
     * @return Builder
     */
    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    /**
     * The customer's e-mail address
     * @param email Max length: 254
     * @return Builder
     */
    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setHomePhone(PhoneNumber homePhone) {
      this.homePhone = homePhone;
      return this;
    }

    public Builder setMobilePhone(PhoneNumber mobilePhone) {
      this.mobilePhone = mobilePhone;
      return this;
    }

    public Builder setWorkPhone(PhoneNumber workPhone) {
      this.workPhone = workPhone;
      return this;
    }

    public CustomerDetails build() {
      return new CustomerDetails(this);
    }
  }
}
