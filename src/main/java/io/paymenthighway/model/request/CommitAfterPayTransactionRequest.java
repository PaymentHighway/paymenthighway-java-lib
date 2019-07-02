package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Commit AfterPay Transaction request POJO
 */
public class CommitAfterPayTransactionRequest extends Request {

    @JsonProperty
    private Long amount = null;

    @JsonProperty
    private String currency = "EUR";


    /**
     * Commit transaction with the specified amount
     *
     * @param amount Amount to revert
     */
    public CommitAfterPayTransactionRequest(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

  public String getCurrency() {
    return currency;
  }
}
