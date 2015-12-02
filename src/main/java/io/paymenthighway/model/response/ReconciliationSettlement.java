package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Reconciliation Settlement POJO
 */
public class ReconciliationSettlement {

  @JsonProperty("acquirer_batch_id")
  String acquirerBatchId;
  @JsonProperty("status")
  Status status;
  @JsonProperty("batch")
  String batch;
  @JsonProperty("date_processed")
  String dateProcessed;
  @JsonProperty("reference")
  String reference;
  @JsonProperty("acquirer")
  Acquirer acquirer;
  @JsonProperty("transaction_count")
  String transactionCount;
  @JsonProperty("net_amount")
  String netAmount;
  @JsonProperty("currency")
  String currency;
  ReconciliationTransaction[] transactions;
  @JsonProperty("main_acquirer_merchant_id")
  String mainAcquirerMerchantId;
  @JsonProperty("unallocated_transactions_count")
  String unallocatedTransactionsCount;
  @JsonProperty("unallocated_transactions")
  UnallocatedTransaction[] unallocatedTransactions;

  public String getAcquirerBatchId() {
    return acquirerBatchId;
  }

  public Status getStatus() {
    return status;
  }

  public String getBatch() {
    return batch;
  }

  public String getDateProcessed() {
    return dateProcessed;
  }

  public String getReference() {
    return reference;
  }

  public Acquirer getAcquirer() {
    return acquirer;
  }

  public String getTransactionCount() {
    return transactionCount;
  }

  public String getNetAmount() {
    return netAmount;
  }

  public String getCurrency() {
    return currency;
  }

  public ReconciliationTransaction[] getTransactions() {
    return transactions;
  }

  public String getMainAcquirerMerchantId() {
    return mainAcquirerMerchantId;
  }

  public String getUnallocatedTransactionsCount() {
    return unallocatedTransactionsCount;
  }

  public UnallocatedTransaction[] getUnallocatedTransactions() {
    return unallocatedTransactions;
  }
}
