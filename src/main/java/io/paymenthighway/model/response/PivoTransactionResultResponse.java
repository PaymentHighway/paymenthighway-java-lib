package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  Pivo Transaction Result response POJO.
 *  Used to find out whether or not a Pivo transaction succeeded.
 */
public class PivoTransactionResultResponse {
    @JsonProperty("state")
    protected String state;
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
    @JsonProperty("card")
    protected PartialCard card;
    @JsonProperty("authorization_code")
    protected String authorizationCode;
    @JsonProperty("filing_code")
    protected String filingCode;

    /**
     *@deprecated Use getState instead.
     * @return status
     */
    @Deprecated
    public String getStatus() { return state; }

    public String getState() {
        return state;
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

    public PartialCard getCard() {
        return card;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public String getFilingCode() {
        return filingCode;
    }
}
