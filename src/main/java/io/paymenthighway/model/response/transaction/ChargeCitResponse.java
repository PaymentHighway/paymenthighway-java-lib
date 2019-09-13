package io.paymenthighway.model.response.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.paymenthighway.model.response.TransactionResponse;

public class ChargeCitResponse extends TransactionResponse {

  @JsonProperty("filing_code")
  private String filingCode;

  @JsonProperty("three_d_secure_url")
  private String threeDSecureUrl;

  public String getFilingCode() {
    return filingCode;
  }

  /**
   * Populated in case of soft decline result code "400" (Issuer requests 3DS step-up).
   *
   * @return The URL to redirect the cardholder to, for strong customer authentication.
   */
  public String getThreeDSecureUrl() {
    return threeDSecureUrl;
  }
}
