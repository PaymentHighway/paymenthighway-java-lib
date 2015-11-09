package io.paymenthighway;

import io.paymenthighway.security.SecureSigner;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates parameters that can used on the form that sends them to
 * Payment Highway.
 * <p/>
 * Creates a request id, timestamp and signature based on request parameters.
 */
public class FormBuilder {

  private final static String METHOD_POST = "POST";
  private final static String SPH_API_VERSION = "sph-api-version";
  private final static String SPH_ACCEPT_CVC_REQUIRED = "sph-accept-cvc-required";
  private final static String SPH_ACCOUNT = "sph-account";
  private final static String SPH_MERCHANT = "sph-merchant";
  private final static String SPH_AMOUNT = "sph-amount";
  private final static String SPH_CURRENCY = "sph-currency";
  private final static String SPH_ORDER = "sph-order";
  private final static String SPH_SUCCESS_URL = "sph-success-url";
  private final static String SPH_FAILURE_URL = "sph-failure-url";
  private final static String SPH_CANCEL_URL = "sph-cancel-url";
  private final static String SPH_REQUEST_ID = "sph-request-id";
  private final static String SPH_TIMESTAMP = "sph-timestamp";
  private final static String LANGUAGE = "language";
  private final static String DESCRIPTION = "description";
  private final static String SIGNATURE = "signature";

  private String method = METHOD_POST;
  private String baseUrl = null;
  private String signatureKeyId = null;
  private String signatureSecret = null;
  private String account = null;
  private String merchant = null;

  public FormBuilder(String method, String signatureKeyId,
                     String signatureSecret, String account, String merchant,
                     String baseUrl) {
    this.method = method;
    this.signatureKeyId = signatureKeyId;
    this.signatureSecret = signatureSecret;
    this.account = account;
    this.merchant = merchant;
    this.baseUrl = baseUrl;
  }

  /**
   * Get parameters for Add Card request
   *
   * @param successUrl The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language The language the form is displayed in.
   * @return FormContainer
   */
  public FormContainer generateAddCardParameters(String successUrl, String failureUrl,
                                                 String cancelUrl, String language) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
            cancelUrl, language, requestId);

    String addCardUri = "/form/view/add_card";
    String signature = this.createSignature(addCardUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(SIGNATURE, signature));

    return new FormContainer(this.method, this.baseUrl, addCardUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Add Card request
   *
   * @param successUrl The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language The language the form is displayed in.
   * @param acceptCvcRequired Accept a payment card token even if the card requires CVC for payments.
   * @return FormContainer
   */
  public FormContainer generateAddCardParameters(String successUrl, String failureUrl,
                                                 String cancelUrl, String language, Boolean acceptCvcRequired) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
            cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(SPH_ACCEPT_CVC_REQUIRED, acceptCvcRequired.toString()));

    String addCardUri = "/form/view/add_card";
    String signature = this.createSignature(addCardUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(SIGNATURE, signature));

    return new FormContainer(this.method, this.baseUrl, addCardUri, nameValuePairs, requestId);
  }


  /**
   * Get parameters for Payment request.
   *
   * @param successUrl The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language The language the form is displayed in.
   * @param amount The amount to pay.
   * @param currency In which currency is the amount, e.g. "EUR"
   * @param orderId A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description Description of the payment shown in the form.
   * @return FormContainer
   */
  public FormContainer generatePaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                 String language, String amount, String currency, String orderId,
                                                 String description) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
            cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(DESCRIPTION, description));

    String payWithCardUri = "/form/view/pay_with_card";
    String signature = this.createSignature(payWithCardUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(SIGNATURE, signature));

    return new FormContainer(this.method, this.baseUrl, payWithCardUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Add Card and Pay request.
   *
   * @param successUrl The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language The language the form is displayed in.
   * @param amount The amount to pay.
   * @param currency In which currency is the amount, e.g. "EUR"
   * @param orderId A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description Description of the payment shown in the form.
   * @return FormContainer
   */
  public FormContainer generateAddCardAndPaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                           String language, String amount, String currency,
                                                           String orderId, String description) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
            cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(DESCRIPTION, description));

    String addCardAndPayUri = "/form/view/add_and_pay_with_card";
    String signature = this.createSignature(addCardAndPayUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(SIGNATURE, signature));

    return new FormContainer(method, this.baseUrl, addCardAndPayUri, nameValuePairs, requestId);
  }

  private List<NameValuePair> createCommonNameValuePairs(String successUrl, String failureUrl, String cancelUrl,
                                                         String language, String requestId) {

    List<NameValuePair> nameValuePairs = new ArrayList<>();
    nameValuePairs.add(new BasicNameValuePair(SPH_API_VERSION, "20150605"));
    nameValuePairs.add(new BasicNameValuePair(SPH_ACCOUNT, account));
    nameValuePairs.add(new BasicNameValuePair(SPH_MERCHANT, merchant));
    nameValuePairs.add(new BasicNameValuePair(SPH_TIMESTAMP, PaymentHighwayUtility.getUtcTimestamp()));
    nameValuePairs.add(new BasicNameValuePair(SPH_CANCEL_URL, cancelUrl));
    nameValuePairs.add(new BasicNameValuePair(SPH_FAILURE_URL, failureUrl));
    nameValuePairs.add(new BasicNameValuePair(SPH_SUCCESS_URL, successUrl));
    nameValuePairs.add(new BasicNameValuePair(SPH_REQUEST_ID, requestId));
    nameValuePairs.add(new BasicNameValuePair(LANGUAGE, language));

    return nameValuePairs;
  }

  private String createSignature(String uri, List<NameValuePair> nameValuePairs) {

    SecureSigner ss = new SecureSigner(this.signatureKeyId, this.signatureSecret);
    return ss.createSignature(this.method, uri, nameValuePairs, "");
  }
}
