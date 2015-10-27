package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Transaction Status Response POJO
 */
public class TransactionStatusResponse extends Response {

  TransactionStatus transaction = null;

  public TransactionStatus getTransaction() {
    return transaction;
  }

} 


