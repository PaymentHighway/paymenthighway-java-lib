package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;
import io.paymenthighway.PaymentHighwayUtility;
import io.paymenthighway.security.SecureSigner;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class GenericFormBuilder {
  protected String method;
  protected String signatureKeyId;
  protected String signatureSecret;
  protected String account;
  protected String merchant;
  protected String baseUrl;
  protected String successUrl;
  protected String failureUrl;
  protected String cancelUrl;
  protected String language;
  protected String serviceUri;
  protected List<NameValuePair> nameValuePairs;

  private SecureSigner ss;
  private String requestId;

  public GenericFormBuilder(String method, String signatureKeyId, String signatureSecret, String account, String merchant,
                            String baseUrl, String successUrl, String failureUrl, String cancelUrl, String language) {
    this.method = method;
    this.signatureKeyId = signatureKeyId;
    this.signatureSecret = signatureSecret;
    this.account = account;
    this.merchant = merchant;
    this.baseUrl = baseUrl;
    this.successUrl = successUrl;
    this.failureUrl = failureUrl;
    this.cancelUrl = cancelUrl;
    this.language = language;
    this.requestId = PaymentHighwayUtility.createRequestId();
    this.ss = new SecureSigner(signatureKeyId, signatureSecret);
    this.nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl, cancelUrl, language, requestId);
  }

  protected List<NameValuePair> createCommonNameValuePairs(String successUrl, String failureUrl, String cancelUrl, String language, String requestId) {

    List<NameValuePair> nameValuePairs = new ArrayList<>();
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_API_VERSION, "20151028"));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ACCOUNT, account));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_MERCHANT, merchant));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_TIMESTAMP, PaymentHighwayUtility.getUtcTimestamp()));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CANCEL_URL, cancelUrl));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_FAILURE_URL, failureUrl));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SUCCESS_URL, successUrl));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_REQUEST_ID, requestId));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.LANGUAGE, language));

    return nameValuePairs;
  }

  public FormContainer build() {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, createSignature()));
    return new FormContainer(this.method, this.baseUrl, serviceUri, nameValuePairs, requestId);
  }

  private String createSignature() {
    return ss.createSignature(this.method, serviceUri, nameValuePairs, "");
  }

}
