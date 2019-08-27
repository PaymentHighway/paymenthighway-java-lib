package io.paymenthighway.model.request.sca;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Urls {

  @JsonProperty("success_url")
  private String successUrl;

  @JsonProperty("failure_url")
  private String failureUrl;

  @JsonProperty("cancel_url")
  private String cancelUrl;

  @JsonProperty("webhook_success_url")
  private String webhookSuccessUrl;

  @JsonProperty("webhook_failure_url")
  private String webhookFailureUrl;

  @JsonProperty("webhook_cancel_url")
  private String webhookCancelUrl;

  @JsonProperty("webhook_delay")
  private Integer webhookDelay;

  private Urls(Builder builder) {
    successUrl = builder.successUrl;
    failureUrl = builder.failureUrl;
    cancelUrl = builder.cancelUrl;
    webhookSuccessUrl = builder.webhookSuccessUrl;
    webhookFailureUrl = builder.webhookFailureUrl;
    webhookCancelUrl = builder.webhookCancelUrl;
    webhookDelay = builder.webhookDelay;
  }

  /**
   * @param successUrl The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl  The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   */
  public static Builder Builder(String successUrl, String failureUrl, String cancelUrl) {
    return new Builder(successUrl, failureUrl, cancelUrl);
  }

  /**
   * {@code Urls} builder static inner class.
   */
  public static class Builder {
    private String successUrl;
    private String failureUrl;
    private String cancelUrl;
    private String webhookSuccessUrl;
    private String webhookCancelUrl;
    private String webhookFailureUrl;
    private Integer webhookDelay;

    /**
     * @param successUrl The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
     * @param failureUrl The URL the user is redirected after a failure such as an authentication or connectivity error.
     * @param cancelUrl  The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
     */
    public Builder(String successUrl, String failureUrl, String cancelUrl) {
      this.successUrl = successUrl;
      this.failureUrl = failureUrl;
      this.cancelUrl = cancelUrl;
    }

    /**
     /**
     * The URL the PH server makes request to after the transaction is handled. The payment itself may still be rejected.
     *
     * @param webhookSuccessUrl Webhook url to call when request is successfully handled
     * @return a reference to this Builder
     */
    public Builder setWebhookSuccessUrl(String webhookSuccessUrl) {
      this.webhookSuccessUrl = webhookSuccessUrl;
      return this;
    }

    /**
     * The URL the PH server makes request to after a failure such as an authentication or connectivity error.
     *
     * @param webhookFailureUrl Webhook url to call when request failed
     * @return a reference to this Builder
     */
    public Builder setWebhookFailureUrl(String webhookFailureUrl) {
      this.webhookFailureUrl = webhookFailureUrl;
      return this;
    }

    /**
     * The URL the PH server makes request to after cancelling the transaction (clicking on the cancel button).
     *
     * @param webhookCancelUrl Webhook url to call when user cancels request
     * @return a reference to this Builder
     */
    public Builder setWebhookCancelUrl(String webhookCancelUrl) {
      this.webhookCancelUrl = webhookCancelUrl;
      return this;
    }

    /**
     * Delay for webhook in seconds. Between 0-900
     *
     * @param webhookDelay Webhook triggering delay in seconds
     * @return a reference to this Builder
     */
    public Builder setWebhookDelay(Integer webhookDelay) {
      this.webhookDelay = webhookDelay;
      return this;
    }

    /**
     * Returns a {@code Urls} built from the parameters previously set.
     *
     * @return a {@code Urls} built with parameters of this {@code Urls.Builder}
     */
    public Urls build() {
      return new Urls(this);
    }
  }
}
