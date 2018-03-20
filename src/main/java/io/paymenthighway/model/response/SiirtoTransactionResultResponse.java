package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *  Siirto Transaction Result response POJO.
 *  Used to find out whether or not an Siirto transaction succeeded.
 */
public class SiirtoTransactionResultResponse extends Response {
    @JsonProperty("status")
    protected String status;
    Customer customer;
    @JsonProperty("amount")
    protected Long amount;
    @JsonProperty("reference_number")
    protected String referenceNumber;

    public String getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Long getAmount() {
        return amount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }
}
