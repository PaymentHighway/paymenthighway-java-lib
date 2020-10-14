package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MobilePayInitRequest extends GenericPaymentInitRequest {
  @JsonProperty("return_uri") private String returnUri;
  @JsonProperty("shop_name") private String shopName;
  @JsonProperty("shop_logo_url") private String shopLogoUrl;

  /**
   *
   * @param amount amount
   * @param currency currency
   * @return builder
   */
  public static Builder Builder(long amount, String currency) {
    return new Builder(amount, currency);
  }

  public String getReturnUri() {
    return returnUri;
  }

  public String getShopName() {
    return shopName;
  }

  public String getShopLogoUrl() {
    return shopLogoUrl;
  }

  public static class Builder extends GenericPaymentInitBuilder<Builder> {
    private URI returnUri;
    private String shopName;
    private URL shopLogoUrl;

    public Builder(long amount, String currency) {
      super(amount,currency);
    }

    public Builder setReturnUri(URI returnUri) {
      this.returnUri = returnUri;
      return this;
    }

    public Builder setReturnUri(String returnUri) throws URISyntaxException {
      this.returnUri = new URI(returnUri);
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

    public MobilePayInitRequest build() {
      return new MobilePayInitRequest(this);
    }
  }

  private MobilePayInitRequest(Builder builder) {
    super(builder);
    this.returnUri = toString(builder.returnUri);
    this.shopName = builder.shopName;
    this.shopLogoUrl = toString(builder.shopLogoUrl);
  }
}
