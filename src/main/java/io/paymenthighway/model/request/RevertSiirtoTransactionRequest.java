package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Revert Siirto Transaction request POJO
 */
public class RevertSiirtoTransactionRequest extends Request {

  @JsonProperty
  Long amount = null;

  @JsonProperty("reference_number")
  String referenceNumber;

  /**
   * Reverts the full amount
   */
  public RevertSiirtoTransactionRequest(String referenceNumber) {
    this.referenceNumber = referenceNumber;
  }

  /**
   * reverts specified amount
   *
   * @param amount Amount to revert
   */
  public RevertSiirtoTransactionRequest(String referenceNumber, Long amount) {
    this.referenceNumber = referenceNumber;
    this.amount = amount;
  }

  /*
   * Getters.
   */
  public Long getAmount() {
    return amount;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }

}
