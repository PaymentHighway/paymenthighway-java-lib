package io.paymenthighway.model.request;

/**
 * Revert Pivo Transaction request POJO
 */
public class RevertPivoTransactionRequest extends Request {

  String amount = null;
  String referenceNumber;

  /**
   * Reverts the full amount
   */
  public RevertPivoTransactionRequest(String referenceNumber) {
    this.referenceNumber = referenceNumber;
  }

  /**
   * reverts specified amount
   *
   * @param amount Amount to revert
   */
  public RevertPivoTransactionRequest(String referenceNumber, String amount) {
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
