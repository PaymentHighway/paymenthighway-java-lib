package io.paymenthighway.model.response.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileInfo {

  @JsonProperty(value = "first_name", required = false)
  private String firstName;

  @JsonProperty(value = "middle_name", required = false)
  private String middleName;

  @JsonProperty(value = "last_name", required = false)
  private String lastName;


  @JsonProperty(value = "gender", required = false)
  private String gender;

  @JsonProperty(value = "date_of_birth", required = false)
  private DateOfBirth dob;

  @JsonProperty(value = "national_id", required = false)
  private String nationalID;


  @JsonProperty(value = "country", required = false)
  private String country;

  @JsonProperty(value = "email_address", required = false)
  private String emailAddress;

  @JsonProperty(value = "phone_number", required = false)
  private String phoneNumber;


  @JsonProperty(value = "billing_address", required = false)
  private BillingAddress billingAddress;

  @JsonProperty(value = "shipping_address", required = false)
  private ShippingAddress shippingAddress;

  /**
   * @return First name for user
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return Middle name for user
   */
  public String getMiddleName() {
    return middleName;
  }

  /**
   * @return Last name for user
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return Gender (M or F). NOTE: Requires contract with MasterPass
   */
  public String getGender() {
    return gender;
  }

  /**
   * @return DOB. NOTE: Requires contract with MasterPass
   */
  public DateOfBirth getDob() {
    return dob;
  }

  /**
   * @return National Identification. NOTE: Requires contract with MasterPass
   */
  public String getNationalID() {
    return nationalID;
  }

  /**
   * @return country of residence
   */
  public String getCountry() {
    return country;
  }

  /**
   * @return email address.
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * @return phone
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @return Billing address for the card holder.
   */
  public BillingAddress getBillingAddress() {
    return billingAddress;
  }

  /**
   * @return Shipping address details.
   */
  public ShippingAddress getShippingAddress() {
    return shippingAddress;
  }
}
