package io.paymenthighway.model.response.transaction;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.paymenthighway.model.response.Acquirer;
import io.paymenthighway.model.response.TransactionResponse;

public class DebitTransactionResponse extends TransactionResponse {
  @JsonProperty("filing_code")
  private String filingCode;
  @JsonProperty("acquirer")
  private Acquirer acquirer;
  @JsonProperty("acquirer_response_code")
  private String acquirerResponseCode;
  @JsonProperty("authorizer")
  private String authorizer;

  public String getFilingCode() {
    return filingCode;
  }

  public Acquirer getAcquirer() {
    return acquirer;
  }

  public String getAcquirerResponseCode() {
    return acquirerResponseCode;
  }

  public String getAuthorizer() {
    return authorizer;
  }
}
