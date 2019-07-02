package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Order Search Response POJO
 */
public class OrderSearchResponse extends Response {

  TransactionStatus[] transactions;
  @JsonProperty("siirto_transactions") SiirtoTransactionStatus[] siirtoTransactions;
  @JsonProperty("pivo_transactions") PivoTransactionStatus[] pivoTransactions;
  @JsonProperty("afterpay_transactions") AfterPayTransactionStatus[] afterPayTransactions;

  public TransactionStatus[] getTransactions() {
    return this.transactions;
  }

  public SiirtoTransactionStatus[] getSiirtoTransactions() {
    return this.siirtoTransactions;
  }

  public PivoTransactionStatus[] getPivoTransactions() {
    return this.pivoTransactions;
  }

  public AfterPayTransactionStatus[] getAfterPayTransactions() {
    return this.afterPayTransactions;
  }
}
