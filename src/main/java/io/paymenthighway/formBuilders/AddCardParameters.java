package io.paymenthighway.formBuilders;

import org.apache.http.message.BasicNameValuePair;

public class AddCardParameters extends GenericCardFormBuilder {

  public AddCardParameters(String method, String signatureKeyId, String signatureSecret, String account, String merchant,
                           String baseUrl, String successUrl, String failureUrl, String cancelUrl, String language) {
    super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl, language);
    serviceUri = "/form/view/add_card";
  }

  public GenericCardFormBuilder acceptCvcRequired(Boolean x) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ACCEPT_CVC_REQUIRED, x.toString()));
    return this;
  }
}
