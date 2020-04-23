package io.paymenthighway.model.response.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.paymenthighway.model.response.AcquirerInfoResponse;

public class DebitTransactionResponse extends AcquirerInfoResponse {
  @JsonProperty("filing_code")
  private String filingCode;

  public String getFilingCode() {
    return filingCode;
  }
}
