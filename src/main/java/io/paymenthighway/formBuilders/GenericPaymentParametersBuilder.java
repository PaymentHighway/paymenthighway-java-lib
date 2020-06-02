package io.paymenthighway.formBuilders;

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
  @Deprecated
  public T showPaymentMethodSelectionPage(Boolean show) {
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

  /**
   * Reference number used when settling the transaction to the merchant account.
   * Only used if one-by-ony transaction settling is configured.
   *
   * @param referenceNumber In RF or Finnish reference number format.
   * @return Form builder
   */
  @SuppressWarnings("unchecked")
  public T referenceNumber(String referenceNumber) {
    addNameValuePair(FormBuilderConstants.SPH_REFERENCE_NUMBER, referenceNumber);
    return (T) this;
  }

  /**
   * Payment splitting
   *
   * @param merchantId Sub-merchant ID from the settlements provider. Not to be confused with the sph-merchant value.
   * @param amount The amount settled to the sub-merchant's account. The rest will be considered as the main merchant's commission. In the smallest currency unit. E.g. 99.99 € = 9999.
   * @return Form builder
   */
  public T splitting(Long merchantId, Long amount) {
    addNameValuePair(FormBuilderConstants.SPH_SPLITTING_MERCHANT_ID, merchantId.toString());
    addNameValuePair(FormBuilderConstants.SPH_SPLITTING_AMOUNT, amount.toString());
    return (T) this;
  }
}
