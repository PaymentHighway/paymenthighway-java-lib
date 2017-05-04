package io.paymenthighway;

import io.paymenthighway.formBuilders.*;
import io.paymenthighway.security.SecureSigner;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Creates parameters that can used on the form that sends them to
 * Payment Highway.
 * Creates a request id, timestamp and signature based on request parameters.
 */
public class FormBuilder {


  private String method = FormBuilderConstants.METHOD_POST;
  private String baseUrl = null;
  private String account = null;
  private String merchant = null;
  private String signatureKeyId;
  private String signatureSecret;

  SecureSigner ss = null;

  public FormBuilder(
      String method,
      String signatureKeyId,
      String signatureSecret,
      String account,
      String merchant,
      String baseUrl
  ) {
    this.method = method;
    this.signatureKeyId = signatureKeyId;
    this.signatureSecret = signatureSecret;
    this.account = account;
    this.merchant = merchant;
    this.baseUrl = baseUrl;

    this.ss = new SecureSigner(signatureKeyId, signatureSecret);
  }

  /**
   * Get parameters for Add Card request
   *
   * @param successUrl The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl  The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @return AddCardParametersInterface
   */
  public AddCardParametersInterface addCardParameters(String successUrl, String failureUrl, String cancelUrl) {
    return new AddCardParameters(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl,
        failureUrl, cancelUrl);
  }

  /**
   * Get parameters for Payment request.
   *
   * @param successUrl  The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl  The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl   The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param amount      The amount to pay.
   * @param currency    In which currency is the amount, e.g. "EUR"
   * @param orderId     A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description Description of the payment shown in the form.
   * @return CardFormParametersInterface
   */
  public CardFormParametersInterface paymentParameters(String successUrl, String failureUrl, String cancelUrl, String amount,
                                             String currency, String orderId, String description) {
    return new PaymentParameters(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl,
        failureUrl, cancelUrl, amount, currency, orderId, description);
  }

  /**
   * Get parameters for Add Card and Pay request.
   *
   * @param successUrl  The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl  The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl   The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param amount      The amount to pay.
   * @param currency    In which currency is the amount, e.g. "EUR"
   * @param orderId     A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description Description of the payment shown in the form.
   * @return CardFormParametersInterface
   */
  public CardFormParametersInterface addCardAndPaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                                 String amount, String currency, String orderId, String description) {
    return new PaymentParameters(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl,
        failureUrl, cancelUrl, amount, currency, orderId, description).tokenize(true);
  }
  /**
   * Get parameters for Pay with Token and CVC request.
   *
   * @param token       The card token to charge from.
   * @param successUrl  The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl  The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl   The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param amount      The amount to pay.
   * @param currency    In which currency is the amount, e.g. "EUR"
   * @param orderId     A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description Description of the payment shown in the form.
   * @return CardFormParametersInterface
   */
  public CardFormParametersInterface payWithTokenAndCvcParameters(String successUrl, String failureUrl, String cancelUrl,
                                                                   String amount, String currency, String orderId,
                                                                   String description, UUID token) {
    return new PayWithTokenAndCvcParameters(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl,
        failureUrl, cancelUrl, amount, currency, orderId, description, token);
  }

  /**
   * Get parameters for MobilePay request.
   *
   * @param successUrl  The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl  The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl   The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param amount      The amount to pay.
   * @param currency    In which currency is the amount, e.g. "EUR"
   * @param orderId     A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description Description of the payment shown in the form.
   * @return MobilePayParametersInterface
   */
  public MobilePayParametersInterface mobilePayParametersBuilder(String successUrl, String failureUrl, String cancelUrl,
                                                               String amount, String currency, String orderId, String description) {
    return new MobilePayParametersBuilder(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl,
        failureUrl, cancelUrl, amount, currency, orderId, description);
  }

  /**
   * Get parameters for Masterpass payment request.
   *
   * @param successUrl  The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl  The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl   The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param amount      The amount to pay.
   * @param currency    In which currency is the amount, e.g. "EUR"
   * @param orderId     A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description Description of the payment shown in the form.
   * @return CardFormParametersInterface
   */
  public CardFormParametersInterface masterpassParameters(String successUrl, String failureUrl, String cancelUrl, String amount,
                                                       String currency, String orderId, String description) {
    return new MasterpassParameters(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl,
        failureUrl, cancelUrl, amount, currency, orderId, description);
  }

  /**
   * Get parameters for Add Card request
   *
   * @param successUrl The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl  The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language   The language the form is displayed in.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generateAddCardParameters(String successUrl, String failureUrl,
                                                 String cancelUrl, String language) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    String addCardUri = "/form/view/add_card";
    String signature = this.createSignature(addCardUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(this.method, this.baseUrl, addCardUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Add Card request with the possibility to accept cards that require CVC.
   *
   * @param successUrl        The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl        The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl         The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language          The language the form is displayed in.
   * @param acceptCvcRequired Accept a payment card token even if the card requires CVC for payments.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generateAddCardParameters(String successUrl, String failureUrl,
                                                 String cancelUrl, String language, Boolean acceptCvcRequired) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ACCEPT_CVC_REQUIRED, acceptCvcRequired.toString()));

    String addCardUri = "/form/view/add_card";
    String signature = this.createSignature(addCardUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(this.method, this.baseUrl, addCardUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Add Card request with the possibility to
   * accept cards that require CVC
   * skip notifications displayed on the Payment Highway form
   * exit from iframe after a result
   * exit from iframe when redirecting the user to 3DS.
   *
   * @param successUrl            The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl            The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl             The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language              The language the form is displayed in.
   * @param acceptCvcRequired     Accept a payment card token even if the card requires CVC for payments. May be null.
   * @param skipFormNotifications Skip notifications displayed on the Payment Highway form. May be null.
   * @param exitIframeOnResult    Exit from iframe after a result. May be null.
   * @param exitIframeOn3ds       Exit from iframe when redirecting the user to 3DS. May be null.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generateAddCardParameters(String successUrl, String failureUrl,
                                                 String cancelUrl, String language, Boolean acceptCvcRequired,
                                                 Boolean skipFormNotifications, Boolean exitIframeOnResult,
                                                 Boolean exitIframeOn3ds) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    if (acceptCvcRequired != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ACCEPT_CVC_REQUIRED, acceptCvcRequired.toString()));
    }
    if (skipFormNotifications != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SKIP_FORM_NOTIFICATIONS, skipFormNotifications.toString()));
    }
    if (exitIframeOnResult != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, exitIframeOnResult.toString()));
    }
    if (exitIframeOn3ds != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_THREE_D_SECURE, exitIframeOn3ds.toString()));
    }

    String addCardUri = "/form/view/add_card";
    String signature = this.createSignature(addCardUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(this.method, this.baseUrl, addCardUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Add Card request with the possibility to
   * accept cards that require CVC
   * skip notifications displayed on the Payment Highway form
   * exit from iframe after a result
   * exit from iframe when redirecting the user to 3DS.
   * force enable/disable 3ds
   *
   * @param successUrl            The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl            The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl             The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language              The language the form is displayed in.
   * @param acceptCvcRequired     Accept a payment card token even if the card requires CVC for payments. May be null.
   * @param skipFormNotifications Skip notifications displayed on the Payment Highway form. May be null.
   * @param exitIframeOnResult    Exit from iframe after a result. May be null.
   * @param exitIframeOn3ds       Exit from iframe when redirecting the user to 3DS. May be null.
   * @param use3ds                Force enable/disable 3ds. Null to use default configured parameter.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generateAddCardParameters(String successUrl, String failureUrl,
                                                 String cancelUrl, String language, Boolean acceptCvcRequired,
                                                 Boolean skipFormNotifications, Boolean exitIframeOnResult,
                                                 Boolean exitIframeOn3ds, Boolean use3ds) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    if (acceptCvcRequired != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ACCEPT_CVC_REQUIRED, acceptCvcRequired.toString()));
    }
    if (skipFormNotifications != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SKIP_FORM_NOTIFICATIONS, skipFormNotifications.toString()));
    }
    if (exitIframeOnResult != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, exitIframeOnResult.toString()));
    }
    if (exitIframeOn3ds != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_THREE_D_SECURE, exitIframeOn3ds.toString()));
    }
    if (use3ds != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_USE_THREE_D_SECURE, use3ds.toString()));
    }

    String addCardUri = "/form/view/add_card";
    String signature = this.createSignature(addCardUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(this.method, this.baseUrl, addCardUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Payment request.
   *
   * @param successUrl  The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl  The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl   The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language    The language the form is displayed in.
   * @param amount      The amount to pay.
   * @param currency    In which currency is the amount, e.g. "EUR"
   * @param orderId     A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description Description of the payment shown in the form.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generatePaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                 String language, String amount, String currency, String orderId,
                                                 String description) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.DESCRIPTION, description));

    String payWithCardUri = "/form/view/pay_with_card";
    String signature = this.createSignature(payWithCardUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(this.method, this.baseUrl, payWithCardUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Payment request with the possibility to
   * skip notifications displayed on the Payment Highway form
   * exit from iframe after a result
   * exit from iframe when redirecting the user to 3DS.
   *
   * @param successUrl            The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl            The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl             The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language              The language the form is displayed in.
   * @param amount                The amount to pay.
   * @param currency              In which currency is the amount, e.g. "EUR"
   * @param orderId               A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description           Description of the payment shown in the form.
   * @param skipFormNotifications Skip notifications displayed on the Payment Highway form. May be null.
   * @param exitIframeOnResult    Exit from iframe after a result. May be null.
   * @param exitIframeOn3ds       Exit from iframe when redirecting the user to 3DS. May be null.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generatePaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                 String language, String amount, String currency, String orderId,
                                                 String description, Boolean skipFormNotifications,
                                                 Boolean exitIframeOnResult, Boolean exitIframeOn3ds) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.DESCRIPTION, description));
    if (skipFormNotifications != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SKIP_FORM_NOTIFICATIONS, skipFormNotifications.toString()));
    }
    if (exitIframeOnResult != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, exitIframeOnResult.toString()));
    }
    if (exitIframeOn3ds != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_THREE_D_SECURE, exitIframeOn3ds.toString()));
    }

    String payWithCardUri = "/form/view/pay_with_card";
    String signature = this.createSignature(payWithCardUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(this.method, this.baseUrl, payWithCardUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Payment request with the possibility to
   * skip notifications displayed on the Payment Highway form
   * exit from iframe after a result
   * exit from iframe when redirecting the user to 3DS.
   * force enable/disable 3ds
   *
   * @param successUrl            The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl            The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl             The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language              The language the form is displayed in.
   * @param amount                The amount to pay.
   * @param currency              In which currency is the amount, e.g. "EUR"
   * @param orderId               A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description           Description of the payment shown in the form.
   * @param skipFormNotifications Skip notifications displayed on the Payment Highway form. May be null.
   * @param exitIframeOnResult    Exit from iframe after a result. May be null.
   * @param exitIframeOn3ds       Exit from iframe when redirecting the user to 3DS. May be null.
   * @param use3ds                Force enable/disable 3ds. Null to use default configured parameter.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generatePaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                 String language, String amount, String currency, String orderId,
                                                 String description, Boolean skipFormNotifications,
                                                 Boolean exitIframeOnResult, Boolean exitIframeOn3ds, Boolean use3ds) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.DESCRIPTION, description));
    if (skipFormNotifications != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SKIP_FORM_NOTIFICATIONS, skipFormNotifications.toString()));
    }
    if (exitIframeOnResult != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, exitIframeOnResult.toString()));
    }
    if (exitIframeOn3ds != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_THREE_D_SECURE, exitIframeOn3ds.toString()));
    }
    if (use3ds != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_USE_THREE_D_SECURE, use3ds.toString()));
    }

    String payWithCardUri = "/form/view/pay_with_card";
    String signature = this.createSignature(payWithCardUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(this.method, this.baseUrl, payWithCardUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Add Card and Pay request.
   *
   * @param successUrl  The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl  The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl   The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language    The language the form is displayed in.
   * @param amount      The amount to pay.
   * @param currency    In which currency is the amount, e.g. "EUR"
   * @param orderId     A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description Description of the payment shown in the form.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generateAddCardAndPaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                           String language, String amount, String currency,
                                                           String orderId, String description) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.DESCRIPTION, description));

    String addCardAndPayUri = "/form/view/add_and_pay_with_card";
    String signature = this.createSignature(addCardAndPayUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(method, this.baseUrl, addCardAndPayUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Add Card and Pay request with the possibility to
   * skip notifications displayed on the Payment Highway form
   * exit from iframe after a result
   * exit from iframe when redirecting the user to 3DS.
   *
   * @param successUrl            The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl            The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl             The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language              The language the form is displayed in.
   * @param amount                The amount to pay.
   * @param currency              In which currency is the amount, e.g. "EUR"
   * @param orderId               A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description           Description of the payment shown in the form.
   * @param skipFormNotifications Skip notifications displayed on the Payment Highway form. May be null.
   * @param exitIframeOnResult    Exit from iframe after a result. May be null.
   * @param exitIframeOn3ds       Exit from iframe when redirecting the user to 3DS. May be null.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generateAddCardAndPaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                           String language, String amount, String currency,
                                                           String orderId, String description,
                                                           Boolean skipFormNotifications, Boolean exitIframeOnResult,
                                                           Boolean exitIframeOn3ds) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.DESCRIPTION, description));
    if (skipFormNotifications != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SKIP_FORM_NOTIFICATIONS, skipFormNotifications.toString()));
    }
    if (exitIframeOnResult != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, exitIframeOnResult.toString()));
    }
    if (exitIframeOn3ds != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_THREE_D_SECURE, exitIframeOn3ds.toString()));
    }

    String addCardAndPayUri = "/form/view/add_and_pay_with_card";
    String signature = this.createSignature(addCardAndPayUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(method, this.baseUrl, addCardAndPayUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Add Card and Pay request with the possibility to
   * skip notifications displayed on the Payment Highway form
   * exit from iframe after a result
   * exit from iframe when redirecting the user to 3DS.
   * force enable/disable 3ds
   *
   * @param successUrl            The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl            The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl             The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language              The language the form is displayed in.
   * @param amount                The amount to pay.
   * @param currency              In which currency is the amount, e.g. "EUR"
   * @param orderId               A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description           Description of the payment shown in the form.
   * @param skipFormNotifications Skip notifications displayed on the Payment Highway form. May be null.
   * @param exitIframeOnResult    Exit from iframe after a result. May be null.
   * @param exitIframeOn3ds       Exit from iframe when redirecting the user to 3DS. May be null.
   * @param use3ds                Force enable/disable 3ds. Null to use default configured parameter.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generateAddCardAndPaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                           String language, String amount, String currency,
                                                           String orderId, String description,
                                                           Boolean skipFormNotifications, Boolean exitIframeOnResult,
                                                           Boolean exitIframeOn3ds, Boolean use3ds) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.DESCRIPTION, description));
    if (skipFormNotifications != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SKIP_FORM_NOTIFICATIONS, skipFormNotifications.toString()));
    }
    if (exitIframeOnResult != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, exitIframeOnResult.toString()));
    }
    if (exitIframeOn3ds != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_THREE_D_SECURE, exitIframeOn3ds.toString()));
    }
    if (use3ds != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_USE_THREE_D_SECURE, use3ds.toString()));
    }

    String addCardAndPayUri = "/form/view/add_and_pay_with_card";
    String signature = this.createSignature(addCardAndPayUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(method, this.baseUrl, addCardAndPayUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Pay with Token and CVC request.
   *
   * @param token       The card token to charge from.
   * @param successUrl  The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl  The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl   The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language    The language the form is displayed in.
   * @param amount      The amount to pay.
   * @param currency    In which currency is the amount, e.g. "EUR"
   * @param orderId     A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description Description of the payment shown in the form.
   * @return FromContainer
   */
  @Deprecated
  public FormContainer generatePayWithTokenAndCvcParameters(UUID token, String successUrl, String failureUrl,
                                                            String cancelUrl, String language, String amount,
                                                            String currency, String orderId, String description) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_TOKEN, token.toString()));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.DESCRIPTION, description));

    String payWithTokenAndCvcUri = "/form/view/pay_with_token_and_cvc";
    String signature = this.createSignature(payWithTokenAndCvcUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(method, this.baseUrl, payWithTokenAndCvcUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Pay with Token and CVC request with the possibility to
   * skip notifications displayed on the Payment Highway form
   * exit from iframe after a result
   * exit from iframe when redirecting the user to 3DS.
   *
   * @param token                 The card token to charge from.
   * @param successUrl            The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl            The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl             The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language              The language the form is displayed in.
   * @param amount                The amount to pay.
   * @param currency              In which currency is the amount, e.g. "EUR"
   * @param orderId               A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description           Description of the payment shown in the form.
   * @param skipFormNotifications Skip notifications displayed on the Payment Highway form. May be null.
   * @param exitIframeOnResult    Exit from iframe after a result. May be null.
   * @param exitIframeOn3ds       Exit from iframe when redirecting the user to 3DS. May be null.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generatePayWithTokenAndCvcParameters(UUID token, String successUrl, String failureUrl,
                                                            String cancelUrl, String language, String amount,
                                                            String currency, String orderId, String description,
                                                            Boolean skipFormNotifications, Boolean exitIframeOnResult,
                                                            Boolean exitIframeOn3ds) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_TOKEN, token.toString()));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.DESCRIPTION, description));
    if (skipFormNotifications != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SKIP_FORM_NOTIFICATIONS, skipFormNotifications.toString()));
    }
    if (exitIframeOnResult != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, exitIframeOnResult.toString()));
    }
    if (exitIframeOn3ds != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_THREE_D_SECURE, exitIframeOn3ds.toString()));
    }

    String payWithTokenAndCvcUri = "/form/view/pay_with_token_and_cvc";
    String signature = this.createSignature(payWithTokenAndCvcUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(method, this.baseUrl, payWithTokenAndCvcUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for Pay with Token and CVC request with the possibility to
   * skip notifications displayed on the Payment Highway form
   * exit from iframe after a result
   * exit from iframe when redirecting the user to 3DS.
   * force enable/disable 3ds
   *
   * @param token                 The card token to charge from.
   * @param successUrl            The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl            The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl             The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language              The language the form is displayed in.
   * @param amount                The amount to pay.
   * @param currency              In which currency is the amount, e.g. "EUR"
   * @param orderId               A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description           Description of the payment shown in the form.
   * @param skipFormNotifications Skip notifications displayed on the Payment Highway form. May be null.
   * @param exitIframeOnResult    Exit from iframe after a result. May be null.
   * @param exitIframeOn3ds       Exit from iframe when redirecting the user to 3DS. May be null.
   * @param use3ds                Force enable/disable 3ds. Null to use default configured parameter.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generatePayWithTokenAndCvcParameters(UUID token, String successUrl, String failureUrl,
                                                            String cancelUrl, String language, String amount,
                                                            String currency, String orderId, String description,
                                                            Boolean skipFormNotifications, Boolean exitIframeOnResult,
                                                            Boolean exitIframeOn3ds, Boolean use3ds) {

    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl,
        cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_TOKEN, token.toString()));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.DESCRIPTION, description));
    if (skipFormNotifications != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SKIP_FORM_NOTIFICATIONS, skipFormNotifications.toString()));
    }
    if (exitIframeOnResult != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, exitIframeOnResult.toString()));
    }
    if (exitIframeOn3ds != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_THREE_D_SECURE, exitIframeOn3ds.toString()));
    }
    if (use3ds != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_USE_THREE_D_SECURE, use3ds.toString()));
    }

    String payWithTokenAndCvcUri = "/form/view/pay_with_token_and_cvc";
    String signature = this.createSignature(payWithTokenAndCvcUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(method, this.baseUrl, payWithTokenAndCvcUri, nameValuePairs, requestId);
  }

  /**
   * Get parameters for MobilePay request.
   *
   * @param successUrl  The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl  The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl   The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language    The language the form is displayed in.
   * @param amount      The amount to pay.
   * @param currency    In which currency is the amount, e.g. "EUR"
   * @param orderId     A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description Description of the payment shown in the form.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generatePayWithMobilePayParameters(
      String successUrl,
      String failureUrl,
      String cancelUrl,
      String language,
      String amount,
      String currency,
      String orderId,
      String description
  ) {
    return this.generatePayWithMobilePayParameters(successUrl, failureUrl, cancelUrl, language, amount, currency, orderId, description, null, null);
  }

  /**
   * Get parameters for MobilePay request.
   *
   * @param successUrl         The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl         The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl          The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language           The language the form is displayed in.
   * @param amount             The amount to pay.
   * @param currency           In which currency is the amount, e.g. "EUR"
   * @param orderId            A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description        Description of the payment shown in the form.
   * @param exitIframeOnResult Exit from iframe after a result. May be null.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generatePayWithMobilePayParameters(
      String successUrl,
      String failureUrl,
      String cancelUrl,
      String language,
      String amount,
      String currency,
      String orderId,
      String description,
      Boolean exitIframeOnResult
  ) {
    return this.generatePayWithMobilePayParameters(successUrl, failureUrl, cancelUrl, language, amount, currency, orderId, description, exitIframeOnResult, null);
  }

  /**
   * Get parameters for MobilePay request.
   *
   * @param successUrl         The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl         The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl          The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language           The language the form is displayed in.
   * @param amount             The amount to pay.
   * @param currency           In which currency is the amount, e.g. "EUR"
   * @param orderId            A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description        Description of the payment shown in the form.
   * @param exitIframeOnResult Exit from iframe after a result. May be null.
   * @param shopLogoUrl        The logo must be 250x250 pixel in .png format and must be hosted on a HTTPS (secure) server. Optional.
   * @return FormContainer
   */
  @Deprecated
  public FormContainer generatePayWithMobilePayParameters(
      String successUrl,
      String failureUrl,
      String cancelUrl,
      String language,
      String amount,
      String currency,
      String orderId,
      String description,
      Boolean exitIframeOnResult,
      String shopLogoUrl
  ) {
    String requestId = PaymentHighwayUtility.createRequestId();
    List<NameValuePair> nameValuePairs = createCommonNameValuePairs(successUrl, failureUrl, cancelUrl, language, requestId);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ORDER, orderId));
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.DESCRIPTION, description));

    if (exitIframeOnResult != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, exitIframeOnResult.toString()));
    }
    if (shopLogoUrl != null) {
      nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SHOP_LOGO_URL, shopLogoUrl));
    }

    String mobilePayUri = "/form/view/mobilepay";
    String signature = this.createSignature(mobilePayUri, nameValuePairs);

    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SIGNATURE, signature));

    return new FormContainer(method, this.baseUrl, mobilePayUri, nameValuePairs, requestId);
  }

  @Deprecated
  private List<NameValuePair> createCommonNameValuePairs(String successUrl, String failureUrl, String cancelUrl, String language, String requestId) {

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

  @Deprecated
  private String createSignature(String uri, List<NameValuePair> nameValuePairs) {

    return ss.createSignature(this.method, uri, nameValuePairs, "");
  }
}
