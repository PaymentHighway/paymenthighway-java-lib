package io.paymenthighway.model.request;

/**
 * Revert Siirto Transaction request POJO
 */
public class RevertSiirtoTransactionRequest extends Request {

  String amount = null;
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
  public RevertSiirtoTransactionRequest(String referenceNumber, String amount) {
    this.referenceNumber = referenceNumber;
    this.amount = amount;
  }

  /*
   * Getters.
   */
  public String getAmount() {
    return amount;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }

}
