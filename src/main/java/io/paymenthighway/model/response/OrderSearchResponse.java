package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Order Search Response POJO
 */
public class OrderSearchResponse extends Response {

  TransactionStatus[] transactions;
  @JsonProperty("siirto_transactions") SiirtoTransactionStatus[] siirtoTransactions;
  @JsonProperty("pivo_transactions") PivoTransactionStatus[] pivoTransactions;

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


