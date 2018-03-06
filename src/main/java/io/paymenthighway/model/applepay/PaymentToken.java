package io.paymenthighway.model.applepay;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentToken {

  @JsonProperty
  String data = null;
  @JsonProperty
  PaymentTokenHeader header = null;
  @JsonProperty
  String signature = null;
  @JsonProperty
  String version = null;
}
