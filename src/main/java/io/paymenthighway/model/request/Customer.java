package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Customer POJO
 */
public class Customer {

  @JsonProperty("network_address")
  String networkAddress;

  public Customer(String networkAddress) {
    this.networkAddress = networkAddress;
  }

  public String getNetworkAddress() {
    return networkAddress;
  }
}
