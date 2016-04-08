package io.paymenthighway.model.request;

import io.paymenthighway.model.Token;

/**
 * Transaction request POJO
 */
public class TransactionRequest extends Request {

  private String amount = null;
  private String currency = null;
  private Token token = null;
  private Card card = null;
  private String order = null;
  private Customer customer = null;
  private boolean commit;

  public TransactionRequest(Token token, String amount, String currency) {
    this.token = token;
    this.amount = amount;
    this.currency = currency;
  }

  @Deprecated
  public TransactionRequest(Token token, String amount, String currency, String order) {
    this.token = token;
    this.amount = amount;
    this.currency = currency;
    this.order = order;
  }

  @Deprecated
  public TransactionRequest(Token token, String amount, String currency, boolean blocking) {
    this.token = token;
    this.amount = amount;
    this.currency = currency;
  }

  @Deprecated
  public TransactionRequest(Token token, String amount, String currency, boolean blocking, String order) {
    this.token = token;
    this.amount = amount;
    this.currency = currency;
    this.order = order;
  }

  @Deprecated
  public TransactionRequest(Token token, String amount, String currency, boolean blocking, Customer customer) {
    this.token = token;
    this.amount = amount;
    this.currency = currency;
    this.customer = customer;
  }

  @Deprecated
  public TransactionRequest(Token token, String amount, String currency, boolean blocking, String order, Customer customer) {
    this.token = token;
    this.amount = amount;
    this.currency = currency;
    this.order = order;
    this.customer = customer;
  }

  public TransactionRequest(Card card, String amount, String currency) {
    this.card = card;
    this.amount = amount;
    this.currency = currency;
  }

  @Deprecated
  public TransactionRequest(Card card, String amount, String currency, Customer customer) {
    this.card = card;
    this.amount = amount;
    this.currency = currency;
    this.customer = customer;
  }

  @Deprecated
  public TransactionRequest(Card card, String amount, String currency, String order) {
    this.card = card;
    this.amount = amount;
    this.currency = currency;
    this.order = order;
  }

  @Deprecated
  public TransactionRequest(Card card, String amount, String currency, String order, Customer customer) {
    this.card = card;
    this.amount = amount;
    this.currency = currency;
    this.order = order;
    this.customer = customer;
  }

  @Deprecated
  public TransactionRequest(Card card, String amount, String currency, boolean blocking) {
    this.card = card;
    this.amount = amount;
    this.currency = currency;
  }

  @Deprecated
  public TransactionRequest(Card card, String amount, String currency, boolean blocking, Customer customer) {
    this.card = card;
    this.amount = amount;
    this.currency = currency;
    this.customer = customer;
  }

  @Deprecated
  public TransactionRequest(Card card, String amount, String currency, boolean blocking, String order) {
    this.card = card;
    this.amount = amount;
    this.currency = currency;
    this.order = order;
  }

  @Deprecated
  public TransactionRequest(Card card, String amount, String currency, boolean blocking, String order, Customer customer) {
    this.card = card;
    this.amount = amount;
    this.currency = currency;
    this.order = order;
    this.customer = customer;
  }

  public static Builder Builder(Token token, long amount, String currency) {
    return new Builder(token, amount, currency);
  }

  public static Builder Builder(Card card, long amount, String currency) {
    return new Builder(card, amount, currency);
  }

  public static class Builder {

    private Token token = null;
    private Card card = null;

    private Long amount = null;
    private String currency = null;

    private String order = null;
    private Customer customer = null;
    private boolean commit;


    public Builder(Token token, long amount, String currency) {
      this.token = token;
      this.amount = amount;
      this.currency = currency;
    }

    public Builder(Card card, long amount, String currency) {
      this.card = card;
      this.amount = amount;
      this.currency = currency;
    }

    public Builder setOrder(String order) {
      this.order = order;
      return this;
    }

    public Builder setCustomer(Customer customer) {
      this.customer = customer;
      return this;
    }

    public Builder setCommit(boolean commit) {
      this.commit = commit;
      return this;
    }

    public TransactionRequest build() {
      return new TransactionRequest(this);
    }
  }

  private TransactionRequest(Builder builder) {
    // Required parameters
    this.card     = builder.card;
    this.token    = builder.token;

    this.amount   = Long.toString(builder.amount);
    this.currency = builder.currency;

    // Optional parameters
    this.order    = builder.order;
    this.customer = builder.customer;
    this.commit   = builder.commit;
  }

  public String getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  @Deprecated
  public boolean isBlocking() {
    return true;
  }

  public Card getCard() {
    return card;
  }

  public Token getToken() {
    return token;
  }

  public String getOrder() {
    return order;
  }

  public Customer getCustomer() {
    return customer;
  }

  public Boolean getCommit() {
    return commit;
  }
}
