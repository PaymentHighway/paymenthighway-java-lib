package io.paymenthighway.formBuilders;


import org.apache.http.message.BasicNameValuePair;

public class GenericCardFormBuilder extends GenericFormBuilder{

  public GenericCardFormBuilder(String method, String signatureKeyId, String signatureSecret, String account,
                                String merchant, String baseUrl, String successUrl, String failureUrl, String cancelUrl,
                                String language) {
    super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl, language);
  }

  public GenericCardFormBuilder skipFormNotifications(Boolean x) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SKIP_FORM_NOTIFICATIONS, x.toString()));
    return this;
  }

  public GenericCardFormBuilder exitIframeOnResult(Boolean x) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, x.toString()));
    return this;
  }

  public GenericCardFormBuilder exitIframeOn3ds(Boolean x) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_THREE_D_SECURE, x.toString()));
    return this;
  }

  public GenericCardFormBuilder use3ds(Boolean x) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_USE_THREE_D_SECURE, x.toString()));
    return this;
  }
}
