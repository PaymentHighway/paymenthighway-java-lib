package io.paymenthighway.model.request;

import io.paymenthighway.model.Splitting;

public class GenericPaymentBuilder<T> {
  protected Long amount;
  protected String currency;
  protected String order;
  protected Splitting splitting;

  public GenericPaymentBuilder(Long amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  public GenericPaymentBuilder(Long amount, String currency, String order) {
    this.amount = amount;
    this.currency = currency;
    this.order = order;
  }

  /**
   * Merchant defined order identifier. Should be unique per transaction.
   *
   * @param order Order identifier
   * @return builder
   */
  @SuppressWarnings("unchecked")
  public T setOrder(String order) {
    this.order = order;
    return (T) this;
  }

  /**
   * Payment splitting
   *
   * @param splitting Splitting (merchant id and amount)
   * @return builder
   */
  @SuppressWarnings("unchecked")
  public T setSplitting(Splitting splitting) {
    this.splitting = splitting;
    return (T) this;
  }
}
