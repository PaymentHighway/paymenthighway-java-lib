package io.paymenthighway.formBuilders;

import org.apache.http.message.BasicNameValuePair;

public class GenericPaymentParametersBuilder<T>
  extends GenericCardFormBuilder<T> {

  protected String amount;
  protected String currency;
  protected String orderId;
  protected String description;

  public GenericPaymentParametersBuilder(
    String method,
    String signatureKeyId,
    String signatureSecret,
    String account,
    String merchant,
    String baseUrl,
    String successUrl,
    String failureUrl,
    String cancelUrl,
    String amount,
    String currency,
    String orderId,
    String description
  ) {
    super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl);
    addNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount);
    addNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency);
    addNameValuePair(FormBuilderConstants.SPH_ORDER, orderId);
    addNameValuePair(FormBuilderConstants.DESCRIPTION, description);
  }

  /**
   * Show payment method selection page.
   *
   * @param show Show payment method selection page.
   * @return Form builder
   */
  @SuppressWarnings("unchecked")
  public T showPaymentMethodSelectionPage(Boolean show) {
    addNameValuePair(FormBuilderConstants.SPH_SHOW_PAYMENT_METHOD_SELECTOR, show);
    return (T) this;
  }

  /**
   * Tokenize card or mobile wallet.
   *
   * @param tokenize Tokenize card or mobile wallet.
   * @return Form builder
   */
  @SuppressWarnings("unchecked")
  public T tokenize(Boolean tokenize) {
    addNameValuePair(FormBuilderConstants.SPH_TOKENIZE, tokenize);
    return (T) this;
  }
}
