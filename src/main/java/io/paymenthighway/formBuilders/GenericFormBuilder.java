package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;
import io.paymenthighway.PaymentHighwayUtility;
import io.paymenthighway.security.SecureSigner;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class GenericFormBuilder<T> {
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
                            String baseUrl, String successUrl, String failureUrl, String cancelUrl) {
    this.method = method;
    this.signatureKeyId = signatureKeyId;
    this.signatureSecret = signatureSecret;
    this.account = account;
    this.merchant = merchant;
    this.baseUrl = baseUrl;
    this.successUrl = successUrl;
    this.failureUrl = failureUrl;
    this.cancelUrl = cancelUrl;
    this.requestId = PaymentHighwayUtility.createRequestId();
    this.ss = new SecureSigner(signatureKeyId, signatureSecret);
    this.nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl, cancelUrl, requestId);
  }

  protected List<NameValuePair> createCommonNameValuePairs(String successUrl, String failureUrl, String cancelUrl, String requestId) {

    List<NameValuePair> nameValuePairs = new ArrayList<>();
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_API_VERSION, "20151028"));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ACCOUNT, account));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_MERCHANT, merchant));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_TIMESTAMP, PaymentHighwayUtility.getUtcTimestamp()));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CANCEL_URL, cancelUrl));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_FAILURE_URL, failureUrl));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SUCCESS_URL, successUrl));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_REQUEST_ID, requestId));

    return nameValuePairs;
  }

  /**
   * Set PaymentHighway form language
   *
   * @param language Two character language code e.q. FI or EN. If omitted, default language from user's browser's settings is used.
   * @return
   */
  public T language(String language) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.LANGUAGE, language));
    return (T) this;
  }

  /**
   * The URL the PH server makes request after the transaction is handled. The payment itself may still be rejected.
   * @param successUrl
   * @return
   */
  public T webhookSuccessUrl(String successUrl) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_WEBHOOK_SUCCESS_URL, successUrl));
    return (T) this;
  }

  /**
   * The URL the PH server makes request after a failure such as an authentication or connectivity error.
   * @param failureUrl
   * @return
   */
  public T webhookFailureUrl(String failureUrl) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_WEBHOOK_FAILURE_URL, failureUrl));
    return (T) this;
  }

  /**
   * The URL the PH server makes request after cancelling the transaction (clicking on the cancel button).
   * @param cancelUrl
   * @return
   */
  public T webhookCancelUrl(String cancelUrl) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_WEBHOOK_CANCEL_URL, cancelUrl));
    return (T) this;
  }

  /**
   * Delay for webhook in seconds. Between 0-900
   * @param delay
   * @return
   */
  public T webhookDelay(Integer delay) {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_WEBHOOK_DELAY, delay.toString()));
    return (T) this;
  }

  /**
   * Builds form parameters
   *
   * @return FormContainer
   */
  public FormContainer build() {
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, createSignature()));
    return new FormContainer(this.method, this.baseUrl, serviceUri, nameValuePairs, requestId);
  }

  private String createSignature() {
    return ss.createSignature(this.method, serviceUri, nameValuePairs, "");
  }

}
