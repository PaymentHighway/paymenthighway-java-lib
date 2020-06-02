package io.paymenthighway.model.request.sca;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StrongCustomerAuthentication {

  @JsonProperty(value = "return_urls")
  private Urls returnUrls;

  @JsonProperty(value = "customer_details")
  private CustomerDetails customerDetails;

  @JsonProperty(value = "customer_account")
  private CustomerAccount customerAccount;

  @JsonProperty(value = "purchase")
  private Purchase purchase;

  @JsonProperty(value = "billing_address")
  private Address billingAddress;

  @JsonProperty(value = "shipping_address")
  private Address shippingAddress;

  @JsonProperty(value = "customer_authentication_info")
  private CustomerAuthenticationInfo customerAuthenticationInfo;

  @JsonProperty(value = "desired_challenge_window_size")
  private DesiredChallengeWindowSize desiredChallengeWindowSize;

  @JsonProperty(value = "exit_iframe_on_result")
  private Boolean exitIframeOnResult;

  @JsonProperty(value = "exit_iframe_on_three_d_secure")
  private Boolean exitIframeOnThreeDSecure;

  private StrongCustomerAuthentication(Builder builder) {
    returnUrls = builder.returnUrls;
    customerDetails = builder.customerDetails;
    customerAccount = builder.customerAccount;
    purchase = builder.purchase;
    billingAddress = builder.billingAddress;
    shippingAddress = builder.shippingAddress;
    customerAuthenticationInfo = builder.customerAuthenticationInfo;
    desiredChallengeWindowSize = builder.desiredChallengeWindowSize;
    exitIframeOnResult = builder.exitIframeOnResult;
    exitIframeOnThreeDSecure = builder.exitIframeOnThreeDSecure;
  }

  /**
   * @param returnUrls The URLs where to redirect the user and send the webhook requests to, after the
   *                   Strong Customer Authentication is performed via the 3DS url response in case of a soft decline.
   * @return Builder
   */
  public static Builder Builder(Urls returnUrls) {
    return new Builder(returnUrls);
  }

  public static final class Builder {

    private Urls returnUrls;
    private CustomerDetails customerDetails;
    private CustomerAccount customerAccount;
    private Purchase purchase;
    private Address billingAddress;
    private Address shippingAddress;
    private CustomerAuthenticationInfo customerAuthenticationInfo;
    private DesiredChallengeWindowSize desiredChallengeWindowSize;
    private Boolean exitIframeOnResult;
    private Boolean exitIframeOnThreeDSecure;

    public Builder(Urls returnUrls) {
      this.returnUrls = returnUrls;
    }

    public Builder setCustomerDetails(CustomerDetails customerDetails) {
      this.customerDetails = customerDetails;
      return this;
    }

    public Builder setCustomerAccount(CustomerAccount customerAccount) {
      this.customerAccount = customerAccount;
      return this;
    }

    public Builder setPurchase(Purchase purchase) {
      this.purchase = purchase;
      return this;
    }

    public Builder setBillingAddress(Address billingAddress) {
      this.billingAddress = billingAddress;
      return this;
    }

    public Builder setShippingAddress(Address shippingAddress) {
      this.shippingAddress = shippingAddress;
      return this;
    }

    public Builder setCustomerAuthenticationInfo(CustomerAuthenticationInfo customerAuthenticationInfo) {
      this.customerAuthenticationInfo = customerAuthenticationInfo;
      return this;
    }

    /**
     * Dimensions of the challenge window that has been displayed to the Cardholder.
     * The ACS shall reply with content that is formatted to appropriately render in this window to provide the best possible user experience.
     * @param desiredChallengeWindowSize Desired challenge window size for 3DS 2.x.
     * @return Builder
     */
    public Builder setDesiredChallengeWindowSize(DesiredChallengeWindowSize desiredChallengeWindowSize) {
      this.desiredChallengeWindowSize = desiredChallengeWindowSize;
      return this;
    }

    /**
     * @param exitIframeOnResult Exit form iframe when redirecting user back to success, failure or cancel URLs.
     * @return Builder
     */
    public Builder setExitIframeOnResult(Boolean exitIframeOnResult) {
      this.exitIframeOnResult = exitIframeOnResult;
      return this;
    }

    /**
     * @param exitIframeOnThreeDSecure Exit from iframe when redirecting the user to 3DS.
     * @return Builder
     */
    public Builder setExitIframeOnThreeDSecure(Boolean exitIframeOnThreeDSecure) {
      this.exitIframeOnThreeDSecure = exitIframeOnThreeDSecure;
      return this;
    }

    public StrongCustomerAuthentication build() {
      return new StrongCustomerAuthentication(this);
    }
  }

  /**
   * Desired challenge window size for 3DS 2.x.
   * Dimensions of the challenge window that has been displayed to the Cardholder.
   * The ACS shall reply with content that is formatted to appropriately render in this window to provide the best possible user experience.
   * {@link #Window250x400}
   * {@link #Window390x400}
   * {@link #Window500x600}
   * {@link #Window600x400}
   * {@link #FullScreen}
   */
  public enum DesiredChallengeWindowSize {

    /**
     * 01 = 250 x 400
     */
    @JsonProperty("01")
    Window250x400,

    /**
     * 02 = 390 x 400
     */
    @JsonProperty("02")
    Window390x400,

    /**
     * 03 = 500 x 600
     */
    @JsonProperty("03")
    Window500x600,

    /**
     * 04 = 600 x 400
     */
    @JsonProperty("04")
    Window600x400,

    /**
     * 05 = Full screen
     */
    @JsonProperty("05")
    FullScreen
  }
}
