package io.paymenthighway.model.applepay;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentData {

  @JsonProperty
  String data = null;
  @JsonProperty
  PaymentDataHeader header = null;
  @JsonProperty
  String signature = null;
  @JsonProperty
  String version = null;
}
