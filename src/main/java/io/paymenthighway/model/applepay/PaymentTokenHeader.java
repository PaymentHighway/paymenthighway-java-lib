package io.paymenthighway.model.applepay;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentTokenHeader {

  @JsonProperty
  String applicationData = null;
  @JsonProperty
  String ephemeralPublicKey = null;
  @JsonProperty
  String publicKeyHash = null;
  @JsonProperty
  String transactionId = null;

}
