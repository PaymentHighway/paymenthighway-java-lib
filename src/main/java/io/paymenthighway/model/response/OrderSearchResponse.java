package io.paymenthighway.model.response;

/**
 * Order Search Response POJO
 */
public class OrderSearchResponse extends Response {

  TransactionStatus[] transactions;
  PivoTransactionStatus[] pivoTransactions;

  public TransactionStatus[] getTransactions() {
    return this.transactions;
  }

  public PivoTransactionStatus[] getPivoTransactions() {
    return this.pivoTransactions;
  }
}
