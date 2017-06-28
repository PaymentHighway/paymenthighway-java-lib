package io.paymenthighway.model.response.masterpass;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MasterpassInfo {

  @JsonProperty(value = "amount", required = true)
  private long amount;
  @JsonProperty(value = "currency", required = true)
  private String currency;

  @JsonProperty(value = "checkout_oauth_token", required = true)
  private String checkoutOauthToken;
  @JsonProperty(value = "masterpass_transaction_id", required = true)
  private String masterpassTransactionId;
  @JsonProperty(value = "masterpass_wallet_id", required = true)
  private String masterpassWalletId;

  /**
   * Original amount given to masterpass
   *
   * @return Amount without decimal point
   */
  public long getAmount() {
    return amount;
  }

  /**
   * Original currency given to masterpass
   *
   * @return ISO 4217 currency code
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * @return  Original token used communicating with masterpass
   */
  public String getCheckoutOauthToken() {
    return checkoutOauthToken;
  }

  /**
   * @return Masterpass transaction id
   */
  public String getMasterpassTransactionId() {
    return masterpassTransactionId;
  }

  /**
   * @return Masterpass wallet id
   */
  public String getMasterpassWalletId() {
    return masterpassWalletId;
  }
}
