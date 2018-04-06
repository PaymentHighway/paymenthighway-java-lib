package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MobilePayInitRequest extends Request {
  @JsonProperty("amount") private Long amount;
  @JsonProperty("currency") private String currency;
  @JsonProperty("order") private String order;
  @JsonProperty("return_uri") private URI returnUri;
  @JsonProperty("language") private String language;
  @JsonProperty("webhook_success_url") private URL webhookSuccessUrl;
  @JsonProperty("webhook_cancel_url") private URL webhookCancelUrl;
  @JsonProperty("webhook_failure_url") private URL webhookFailureUrl;

  @JsonProperty("sub_merchant_name") private String subMerchantName;
  @JsonProperty("sub_merchant_id") private String subMerhantId;
  @JsonProperty("shop_name") private String shopName;
  @JsonProperty("shop_logo_url") private URL shopLogoUrl;

  protected MobilePayInitRequest() {}

  public static Builder Builder(long amount, String currency) {
    return new Builder(amount, currency);
  }

  public Long getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public String getOrder() {
    return order;
  }

  public URI getReturnUri() {
    return returnUri;
  }

  public String getLanguage() {
    return language;
  }

  public String getSubMerchantName() {
    return subMerchantName;
  }

  public String getSubMerhantId() {
    return subMerhantId;
  }

  public String getShopName() {
    return shopName;
  }

  public URL getShopLogoUrl() {
    return shopLogoUrl;
  }

  public URL getWebhookSuccessUrl() {
    return webhookSuccessUrl;
  }

  public URL getWebhookCancelUrl() {
    return webhookCancelUrl;
  }

  public URL getWebhookFailureUrl() {
    return webhookFailureUrl;
  }

  public static class Builder {
    private Long amount = null;
    private String currency = null;

    private String order = null;
    private URI returnUri;
    private String language;
    private String subMerchantName;
    private String subMerhantId;
    private String shopName;
    private URL shopLogoUrl;
    private URL webhookSuccessUrl;
    private URL webhookCancelUrl;
    private URL webhookFailureUrl;


    public Builder(long amount, String currency) {
      this.amount = amount;
      this.currency = currency;
    }

    public Builder setOrder(String order) {
      this.order = order;
      return this;
    }

    public Builder setReturnUri(URI returnUri) {
      this.returnUri = returnUri;
      return this;
    }

    public Builder setReturnUri(String returnUri) throws URISyntaxException {
      this.returnUri = new URI(returnUri);
      return this;
    }

    public Builder setLanguage(String language) {
      this.language = language;
      return this;
    }

    public Builder setSubMerchantName(String subMerchantName) {
      this.subMerchantName = subMerchantName;
      return this;
    }

    public Builder setSubMerhantId(String subMerhantId) {
      this.subMerhantId = subMerhantId;
      return this;
    }

    public Builder setShopName(String shopName) {
      this.shopName = shopName;
      return this;
    }

    public Builder setShopLogoUrl(String shopLogoUrl) throws MalformedURLException {
      return setShopLogoUrl(new URL(shopLogoUrl));
    }

    public Builder setShopLogoUrl(URL shopLogoUrl) {
      this.shopLogoUrl = shopLogoUrl;
      return this;
    }

    public Builder setWebhookSuccessUrl(String webhookSuccessUrl) throws MalformedURLException {
      return setWebhookSuccessUrl(new URL(webhookSuccessUrl));
    }

    public Builder setWebhookSuccessUrl(URL webhookSuccessUrl) {
      this.webhookSuccessUrl = webhookSuccessUrl;
      return this;
    }

    public Builder setWebhookFailureUrl(String webhookFailureUrl) throws MalformedURLException {
      return setWebhookFailureUrl(new URL(webhookFailureUrl));
    }

    public Builder setWebhookFailureUrl(URL webhookFailureUrl) {
      this.webhookFailureUrl = webhookFailureUrl;
      return this;
    }

    public Builder setWebhookCancelUrl(String webhookCancelUrl) throws MalformedURLException {
      return setWebhookCancelUrl(new URL(webhookCancelUrl));
    }

    public Builder setWebhookCancelUrl(URL webhookCancelUrl) {
      this.webhookCancelUrl = webhookCancelUrl;
      return this;
    }

    public MobilePayInitRequest build() {
      return new MobilePayInitRequest(this);
    }
  }

  private MobilePayInitRequest(Builder builder) {
    // Required parameters
    this.amount = builder.amount;
    this.currency = builder.currency;
    this.order = builder.order;
    this.returnUri = builder.returnUri;
    this.webhookSuccessUrl = builder.webhookSuccessUrl;
    this.webhookCancelUrl = builder.webhookCancelUrl;
    this.webhookFailureUrl = builder.webhookFailureUrl;

    // Optional parameters
    this.language = builder.language;
    this.subMerchantName = builder.subMerchantName;
    this.subMerhantId = builder.subMerhantId;
    this.shopName = builder.shopName;
    this.shopLogoUrl = builder.shopLogoUrl;
  }
}
