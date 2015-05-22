package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

  @JsonProperty("id")
  String id;
  @JsonProperty("timestamp")
  String timestamp;
  @JsonProperty("type")
  String type;
  @JsonProperty("partial_pan")
  String partialPan;
  @JsonProperty("amount")
  String amount;
  @JsonProperty("currency")
  String currency;
  @JsonProperty("filing_code")
  String filingCode;
  @JsonProperty("authorization_code")
  String authorizationCode;
  @JsonProperty("status")
  Status status;

  public String getId() {
    return id;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getType() {
    return type;
  }

  public String getPartialPan() {
    return partialPan;
  }

  public String getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public String getFilingCode() {
    return filingCode;
  }

  public String getAuthorizationCode() {
    return authorizationCode;
  }

  public Status getStatus() {
    return status;
  }
}
