package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Revert AfterPay Transaction request POJO
 */
public class RevertAfterPayTransactionRequest extends Request {

    @JsonProperty
    private Long amount = null;

    /**
     * Reverts the full amount without prefilled reference number
     */
    public RevertAfterPayTransactionRequest() {
    }

    /**
     * Reverts the specified amount
     *
     * @param amount Amount to revert
     */
    public RevertAfterPayTransactionRequest(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }
}
