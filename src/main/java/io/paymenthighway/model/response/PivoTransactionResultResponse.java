package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  Pivo Transaction Result response POJO.
 *  Used to find out whether or not a Pivo transaction succeeded.
 */
public class PivoTransactionResultResponse {
    @JsonProperty("status")
    protected String status;
    Customer customer;
    @JsonProperty("amount")
    protected Long amount;
    @JsonProperty("current_amount")
    protected Long currentAmount;
    @JsonProperty("reference_number")
    protected String referenceNumber;
    @JsonProperty("archive_id")
    protected String archiveId;
    @JsonProperty("payment_type")
    protected String paymentType;

    public String getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Long getAmount() {
        return amount;
    }

    public Long getCurrentAmount() {
        return currentAmount;
    }

    public String getArchiveId() {
        return archiveId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }
}
