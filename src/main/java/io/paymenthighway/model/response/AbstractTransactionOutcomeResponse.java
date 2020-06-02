package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Common response for e.g. Commit and Result transaction requests.
 */
abstract public class AbstractTransactionOutcomeResponse extends AcquirerInfoResponse {

  @JsonProperty("card_token")
  UUID cardToken;
  PartialCard card;
  Customer customer;
  @JsonProperty("cardholder_authentication")
  String cardholderAuthentication;
  @JsonProperty("filing_code")
  protected String filingCode;
  @JsonProperty("committed")
  protected Boolean committed;
  @JsonProperty("committed_amount")
  protected String committedAmount;
  @JsonProperty("recurring")
  protected Boolean recurring;
  @JsonProperty("reference_number")
  protected String referenceNumber;


  public UUID getCardToken() {
    return cardToken;
  }

  public PartialCard getCard() {
    return this.card;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getCardholderAuthentication() {
    return cardholderAuthentication;
  }

  public String getFilingCode() {
    return filingCode;
  }

  public Boolean getCommitted() {
    return committed;
  }

  public Boolean getRecurring() { return recurring; }

  /**
   * @return Committed amount or null if transaction not committed
   */
  public String getCommittedAmount() {
    return committedAmount;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }
}
