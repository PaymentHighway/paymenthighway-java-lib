package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Order Search Response POJO
 */
public class OrderSearchResponse extends Response {

  TransactionStatus[] transactions;

  public TransactionStatus[] getTransactions() {
    return this.transactions;
  }
}


