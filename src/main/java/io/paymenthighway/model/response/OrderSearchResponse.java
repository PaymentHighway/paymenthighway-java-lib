package io.paymenthighway.model.response;

/**
 * Order Search Response POJO
 */
public class OrderSearchResponse extends Response {

  TransactionStatus[] transactions;
  SiirtoTransactionStatus[] siirtoTransactions;
  PivoTransactionStatus[] pivoTransactions;

  public TransactionStatus[] getTransactions() {
    return this.transactions;
  }

  public SiirtoTransactionStatus[] getSiirtoTransactions() {
    return this.siirtoTransactions;
  }

  public PivoTransactionStatus[] getPivoTransactions() {
    return this.pivoTransactions;
  }
}


