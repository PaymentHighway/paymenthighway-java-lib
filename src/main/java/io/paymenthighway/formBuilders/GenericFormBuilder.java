package io.paymenthighway.formBuilders;

import io.paymenthighway.Constants;
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

  public GenericFormBuilder(
    String method,
    String signatureKeyId,
    String signatureSecret,
    String account,
    String merchant,
    String baseUrl,
    String successUrl,
    String failureUrl,
    String cancelUrl
  ) {
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
    this.nameValuePairs = new ArrayList<>();

    populateGenericNameValuePairs();
  }

  private void populateGenericNameValuePairs() {
    addNameValuePair(FormBuilderConstants.SPH_API_VERSION, Constants.API_VERSION);
    addNameValuePair(FormBuilderConstants.SPH_ACCOUNT, account);
    addNameValuePair(FormBuilderConstants.SPH_MERCHANT, merchant);
    addNameValuePair(FormBuilderConstants.SPH_TIMESTAMP, PaymentHighwayUtility.getUtcTimestamp());
    addNameValuePair(FormBuilderConstants.SPH_CANCEL_URL, cancelUrl);
    addNameValuePair(FormBuilderConstants.SPH_FAILURE_URL, failureUrl);
    addNameValuePair(FormBuilderConstants.SPH_SUCCESS_URL, successUrl);
    addNameValuePair(FormBuilderConstants.SPH_REQUEST_ID, requestId);
  }

  protected boolean addNameValuePair(String name, Boolean value) {
    return nameValuePairs.add(new BasicNameValuePair(name, value.toString()));
  }

  protected boolean addNameValuePair(String name, String value) {
    return nameValuePairs.add(new BasicNameValuePair(name, value));
  }

  /**
   * Set PaymentHighway form language
   *
   * @param language Two character language code e.g. FI or EN. If omitted, default language from user's browser's settings is used.
   * @return Form builder
   */
  @SuppressWarnings("unchecked")
  public T language(String language) {
    addNameValuePair(FormBuilderConstants.LANGUAGE, language);
    return (T) this;
  }

  /**
   * The URL the PH server makes request to after the transaction is handled. The payment itself may still be rejected.
   *
   * @param successUrl Webhook url to call when request is successfully handled
   * @return Form builders
   */
  @SuppressWarnings("unchecked")
  public T webhookSuccessUrl(String successUrl) {
    addNameValuePair(FormBuilderConstants.SPH_WEBHOOK_SUCCESS_URL, successUrl);
    return (T) this;
  }

  /**
   * The URL the PH server makes request to after a failure such as an authentication or connectivity error.
   *
   * @param failureUrl Webhook url to call when request failed
   * @return Form builder
   */
  @SuppressWarnings("unchecked")
  public T webhookFailureUrl(String failureUrl) {
    addNameValuePair(FormBuilderConstants.SPH_WEBHOOK_FAILURE_URL, failureUrl);
    return (T) this;
  }

  /**
   * The URL the PH server makes request to after cancelling the transaction (clicking on the cancel button).
   *
   * @param cancelUrl Webhook url to call when user cancels request
   * @return Form builder
   */
  @SuppressWarnings("unchecked")
  public T webhookCancelUrl(String cancelUrl) {
    addNameValuePair(FormBuilderConstants.SPH_WEBHOOK_CANCEL_URL, cancelUrl);
    return (T) this;
  }

  /**
   * Delay for webhook in seconds. Between 0-900
   *
   * @param delay Webhook triggering delay in seconds
   * @return Form builder
   */
  @SuppressWarnings("unchecked")
  public T webhookDelay(Integer delay) {
    addNameValuePair(FormBuilderConstants.SPH_WEBHOOK_DELAY, delay.toString());
    return (T) this;
  }

  /**
   * Builds form parameters
   *
   * @return Form container
   */
  public FormContainer build() {
    addNameValuePair(FormBuilderConstants.SIGNATURE, createSignature());
    return new FormContainer(this.method, this.baseUrl, serviceUri, nameValuePairs, requestId);
  }

  private String createSignature() {
    return ss.createSignature(this.method, serviceUri, nameValuePairs, "");
  }
}
