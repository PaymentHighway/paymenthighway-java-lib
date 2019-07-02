package io.paymenthighway.model.response;

/**
 * AfterPay Transaction Status Response POJO
 */
public class AfterPayTransactionStatusResponse extends Response {

  AfterPayTransactionStatus transaction = null;

  public AfterPayTransactionStatus getTransaction() {
    return transaction;
  }

}
