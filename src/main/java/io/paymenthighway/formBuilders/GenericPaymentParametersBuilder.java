package io.paymenthighway.formBuilders;

import org.apache.http.message.BasicNameValuePair;

public class GenericPaymentParametersBuilder<T> extends GenericCardFormBuilder<T> {

  protected String amount;
  protected String currency;
  protected String orderId;
  protected String description;

  public GenericPaymentParametersBuilder(String method, String signatureKeyId, String signatureSecret, String account,
                                         String merchant, String baseUrl, String successUrl, String failureUrl,
                                         String cancelUrl, String amount, String currency,
                                         String orderId, String description) {
    super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl);
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.DESCRIPTION, description));
  }
}
