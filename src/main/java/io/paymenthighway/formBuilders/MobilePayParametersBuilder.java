package io.paymenthighway.formBuilders;

import org.apache.http.message.BasicNameValuePair;

public class MobilePayParametersBuilder extends GenericPaymentParametersBuilder {

  public MobilePayParametersBuilder(String method, String signatureKeyId, String signatureSecret, String account, String merchant,
                                    String baseUrl, String successUrl, String failureUrl, String cancelUrl, String language,
                                    String amount, String currency, String orderId, String description) {
    super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl,
        language, amount, currency, orderId, description);
    serviceUri = "/form/view/mobilepay";
  }

  public MobilePayParametersBuilder exitIframeOnResult(Boolean x) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, x.toString()));
    return this;
  }

  public MobilePayParametersBuilder shopLogoUrl(String x) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SHOP_LOGO_URL, x));
    return this;
  }

  public MobilePayParametersBuilder phoneNumber(String x) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_MOBILEPAY_PHONE_NUMBER, x));
    return this;
  }

  public MobilePayParametersBuilder shopName(String x) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_MOBILEPAY_SHOP_NAME, x));
    return this;
  }

  public MobilePayParametersBuilder subMerchantId(String x) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SUB_MERCHANT_ID, x));
    return this;
  }

  public MobilePayParametersBuilder subMerchantName(String x) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SUB_MERCHANT_NAME, x));
    return this;
  }
}
