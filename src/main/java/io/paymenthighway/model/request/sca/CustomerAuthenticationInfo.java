package io.paymenthighway.model.request.sca;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerAuthenticationInfo {

  @JsonProperty
  private Method method;

  @JsonProperty
  private String timestamp;

  @JsonProperty
  private String data;

  private CustomerAuthenticationInfo(Builder builder) {
    method = builder.method;
    timestamp = builder.timestamp;
    data = builder.data;
  }

  public static Builder Builder() {
    return new Builder();
  }

  public Method getMethod() {
    return method;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getData() {
    return data;
  }

  public static final class Builder {
    private Method method;
    private String timestamp;
    private String data;

    public Builder() { }

    /**
     *
     * @param method Information about how the cardholder is authenticated before or during the transaction.
     * @return Builder
     */
    public Builder setMethod(Method method) {
      this.method = method;
      return this;
    }

    /**
     * Date and time in UTC of the cardholder authentication.
     * @param timestamp Date format: "yyyy-MM-dd'T'HH:mm:ss'Z'", e.g. 2019-08-27T09:22:52Z
     * @return Builder
     */
    public Builder setTimestamp(String timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * Data that documents and supports a specific authentication process.
     * Only populate if instructed to do so.
     * @param data Max length 2048
     * @return Builder
     */
    public Builder setData(String data) {
      this.data = data;
      return this;
    }

    public CustomerAuthenticationInfo build() {
      return new CustomerAuthenticationInfo(this);
    }
  }

  /**
   * Information about how the cardholder is authenticated before or during the transaction.
   * {@link #NoAuthentication}
   * {@link #OwnCredentials}
   * {@link #FederatedId}
   * {@link #IssuerCredentials}
   * {@link #ThirdPartyAuthentication}
   * {@link #FidoAuthenticator}
   * {@link #FidoAuthenticatorWithAssuranceDataSigned}
   * {@link #SrcAssuranceData}
   */
  public enum Method {

    /**
     * 01 = No 3DS Requestor authentication occurred (i.e. cardholder “logged in” as guest)
     */
    @JsonProperty("01")
    NoAuthentication,

    /**
     * 02 = Login to the cardholder account at the 3DS Requestor system using 3DS Requestor’s own credentials
     */
    @JsonProperty("02")
    OwnCredentials,

    /**
     * 03 = Login to the cardholder account at the 3DS Requestor system using federated ID
     */
    @JsonProperty("03")
    FederatedId,

    /**
     * 04 = Login to the cardholder account at the 3DS Requestor system using issuer credentials
     */
    @JsonProperty("04")
    IssuerCredentials,

    /**
     * 05 = Login to the cardholder account at the 3DS Requestor system using third-party authentication
     */
    @JsonProperty("05")
    ThirdPartyAuthentication,

    /**
     * 06 = Login to the cardholder account at the 3DS Requestor system using FIDO Authenticator
     */
    @JsonProperty("06")
    FidoAuthenticator,

    /**
     * 07 = Login to the cardholder account at the 3DS Requestor system using FIDO Authenticator (FIDO assurance data signed)
     */
    @JsonProperty("07")
    FidoAuthenticatorWithAssuranceDataSigned,

    /**
     * 08 = SRC Assurance Data
     */
    @JsonProperty("08")
    SrcAssuranceData
  }
}
