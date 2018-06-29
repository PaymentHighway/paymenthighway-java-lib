package io.paymenthighway.model.request;

/**
 * Revert Pivo Transaction request POJO
 */
public class RevertPivoTransactionRequest extends Request {

    Long amount = null;
    String referenceNumber = null;

    /**
     * Reverts the full amount without prefilled reference number
     */
    public RevertPivoTransactionRequest() {
    }

    /**
     * Reverts the full amount
     *
     * @param referenceNumber Reference number
     */
    public RevertPivoTransactionRequest(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * reverts specified amount without prefilled reference number
     *
     * @param amount Amount to revert
     */
    public RevertPivoTransactionRequest(Long amount) {
        this.amount = amount;
    }

    /**
     * reverts specified amount
     *
     * @param referenceNumber Reference number
     * @param amount          Amount to revert
     */
    public RevertPivoTransactionRequest(String referenceNumber, Long amount) {
        this.referenceNumber = referenceNumber;
        this.amount = amount;
    }

    /*
     * Getters.
     */
    public Long getAmount() {
        return amount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

}
