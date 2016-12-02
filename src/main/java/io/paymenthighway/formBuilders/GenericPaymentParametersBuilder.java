package io.paymenthighway.formBuilders;

public class GenericPaymentParametersBuilder extends GenericCardFormBuilder {

  protected String amount;
  protected String currency;
  protected String orderId;
  protected String description;

  public GenericPaymentParametersBuilder(String method, String signatureKeyId, String signatureSecret, String account,
                                         String merchant, String baseUrl, String successUrl, String failureUrl,
                                         String cancelUrl, String language, String amount, String currency,
                                         String orderId, String description) {
    super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl, language);
    this.amount = amount;
    this.currency = currency;
    this.orderId = orderId;
    this.description = description;
  }
}
