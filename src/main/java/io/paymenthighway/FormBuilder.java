package io.paymenthighway;

import io.paymenthighway.formBuilders.*;

import java.util.UUID;

/**
 * Creates parameters that can used on the form that sends them to
 * Payment Highway.
 * <p>
 * Creates a request id, timestamp and signature based on request parameters.
 */
public class FormBuilder {

  private String method = FormBuilderConstants.METHOD_POST;
  private String baseUrl = null;
  private String account = null;
  private String merchant = null;
  private String signatureKeyId;
  private String signatureSecret;

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
  }

  /**
   * Get parameters for Add Card request
   *
   * @param successUrl The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl  The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @return Form builder
   */
  public AddCardParameters addCardParameters(String successUrl, String failureUrl, String cancelUrl) {
    return new AddCardParameters(
      method,
      signatureKeyId,
      signatureSecret,
      account,
      merchant,
      baseUrl,
      successUrl,
      failureUrl,
      cancelUrl
    );
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
   * @return Form builder
   */
  public PaymentParameters paymentParameters(String successUrl, String failureUrl, String cancelUrl, String amount,
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
   * @deprecated Use paymentParameters-method with .tokenize(true)
   * @return Form builder
   */
  @Deprecated
  public PaymentParameters addCardAndPaymentParameters(String successUrl, String failureUrl, String cancelUrl,
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
   * @return Form builder
   */
  public PayWithTokenAndCvcParameters payWithTokenAndCvcParameters(String successUrl, String failureUrl, String cancelUrl,
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
   * @return Form builder
   */
  public MobilePayParametersBuilder mobilePayParametersBuilder(String successUrl, String failureUrl, String cancelUrl,
                                                               String amount, String currency, String orderId, String description) {
    return new MobilePayParametersBuilder(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl,
        failureUrl, cancelUrl, amount, currency, orderId, description);
  }

  /**
   * Get parameters for Pivo payment request.
   *
   * @param successUrl  The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl  The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl   The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param amount      The amount to pay in Euro cents.
   * @param orderId     A generated order ID, may for example be always unique or used multiple times for recurring transactions.
   * @param description Description of the payment shown in the form.
   * @return Form builder
   */
  public PivoParametersBuilder pivoParametersBuilder(
      String successUrl,
      String failureUrl,
      String cancelUrl,
      Long amount,
      String orderId,
      String description
  ) {
    return new PivoParametersBuilder(
        method,
        signatureKeyId,
        signatureSecret,
        account,
        merchant,
        baseUrl,
        successUrl,
        failureUrl,
        cancelUrl,
        amount,
        orderId,
        description
    );
  }

  /**
   * Get parameters for AfterPay form payment request.
   *
   * @param successUrl  The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl  The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl   The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param amount      The amount to pay in Euro cents.
   * @param orderId     Payment Highway order ID. Also used for AfterPay's parent transaction reference, which reduces the max length to 128.
   * @param description Description of the payment shown in the form.
   * @param orderDescription Description of the purchase. Will be shown on the customer's invoice. Max length 255.
   * @return Form builder
   */
  public AfterPayParametersBuilder afterPayParametersBuilder(
    String successUrl,
    String failureUrl,
    String cancelUrl,
    Long amount,
    String orderId,
    String description,
    String orderDescription
  ) {
    return new AfterPayParametersBuilder(
      method,
      signatureKeyId,
      signatureSecret,
      account,
      merchant,
      baseUrl,
      successUrl,
      failureUrl,
      cancelUrl,
      amount,
      orderId,
      description,
      orderDescription
    );
  }

  /**
   * Get parameters for Add Card request
   *
   * @param successUrl The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl  The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language   The language the form is displayed in.
   * @deprecated Use builder pattern with addCardParameters-method.
   * @return Form container
   */
  @Deprecated
  public FormContainer generateAddCardParameters(
    String successUrl,
    String failureUrl,
    String cancelUrl,
    String language
  ) {
    return addCardParameters(successUrl, failureUrl, cancelUrl)
      .language(language)
      .build();
  }

  /**
   * Get parameters for Add Card request with the possibility to accept cards that require CVC.
   *
   * @param successUrl        The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl        The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl         The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language          The language the form is displayed in.
   * @param acceptCvcRequired Accept a payment card token even if the card requires CVC for payments.
   * @deprecated Use builder pattern with addCardParameters-method.
   * @return Form container
   */
  @Deprecated
  public FormContainer generateAddCardParameters(String successUrl, String failureUrl,
                                                 String cancelUrl, String language, Boolean acceptCvcRequired) {

    return addCardParameters(successUrl, failureUrl, cancelUrl)
      .language(language)
      .acceptCvcRequired(acceptCvcRequired)
      .build();
  }

  /**
   * Get parameters for Add Card request with the possibility to
   * <ul>
   *  <li>accept cards that require CVC</li>
   *  <li>skip notifications displayed on the Payment Highway form</li>
   *  <li>exit from iframe after a result</li>
   *  <li>exit from iframe when redirecting the user to 3DS.</li>
   * </ul>
   *
   * @param successUrl            The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl            The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl             The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language              The language the form is displayed in.
   * @param acceptCvcRequired     Accept a payment card token even if the card requires CVC for payments. May be null.
   * @param skipFormNotifications Skip notifications displayed on the Payment Highway form. May be null.
   * @param exitIframeOnResult    Exit from iframe after a result. May be null.
   * @param exitIframeOn3ds       Exit from iframe when redirecting the user to 3DS. May be null.
   * @deprecated Use builder pattern with addCardParameters-method.
   * @return Form container
   */
  @Deprecated
  public FormContainer generateAddCardParameters(String successUrl, String failureUrl,
                                                 String cancelUrl, String language, Boolean acceptCvcRequired,
                                                 Boolean skipFormNotifications, Boolean exitIframeOnResult,
                                                 Boolean exitIframeOn3ds) {

    AddCardParameters builder = addCardParameters(successUrl, failureUrl, cancelUrl)
      .language(language);

    if (acceptCvcRequired != null) {
      builder.acceptCvcRequired(acceptCvcRequired);
    }
    if (skipFormNotifications != null) {
      builder.skipFormNotifications(skipFormNotifications);
    }
    if (exitIframeOnResult != null) {
      builder.exitIframeOnResult(exitIframeOnResult);
    }
    if (exitIframeOn3ds != null) {
      builder.exitIframeOn3ds(exitIframeOn3ds);
    }

    return builder.build();
  }

  /**
   * Get parameters for Add Card request with the possibility to
   * <ul>
   *  <li>accept cards that require CVC</li>
   *  <li>skip notifications displayed on the Payment Highway form</li>
   *  <li>exit from iframe after a result</li>
   *  <li>exit from iframe when redirecting the user to 3DS.</li>
   *  <li>force enable/disable 3ds</li>
   * </ul>
   * @param successUrl            The URL the user is redirected after the transaction is handled. The payment itself may still be rejected.
   * @param failureUrl            The URL the user is redirected after a failure such as an authentication or connectivity error.
   * @param cancelUrl             The URL the user is redirected after cancelling the transaction (clicking on the cancel button).
   * @param language              The language the form is displayed in.
   * @param acceptCvcRequired     Accept a payment card token even if the card requires CVC for payments. May be null.
   * @param skipFormNotifications Skip notifications displayed on the Payment Highway form. May be null.
   * @param exitIframeOnResult    Exit from iframe after a result. May be null.
   * @param exitIframeOn3ds       Exit from iframe when redirecting the user to 3DS. May be null.
   * @param use3ds                Force enable/disable 3ds. Null to use default configured parameter.
   * @deprecated Use builder pattern with addCardParameters-method.
   * @return Form container
   */
  @Deprecated
  public FormContainer generateAddCardParameters(String successUrl, String failureUrl,
                                                 String cancelUrl, String language, Boolean acceptCvcRequired,
                                                 Boolean skipFormNotifications, Boolean exitIframeOnResult,
                                                 Boolean exitIframeOn3ds, Boolean use3ds) {

    AddCardParameters builder = addCardParameters(successUrl, failureUrl, cancelUrl)
      .language(language);

    if (acceptCvcRequired != null) {
      builder.acceptCvcRequired(acceptCvcRequired);
    }
    if (skipFormNotifications != null) {
      builder.skipFormNotifications(skipFormNotifications);
    }
    if (exitIframeOnResult != null) {
      builder.exitIframeOnResult(exitIframeOnResult);
    }
    if (exitIframeOn3ds != null) {
      builder.exitIframeOn3ds(exitIframeOn3ds);
    }
    if (use3ds != null) {
      builder.use3ds(use3ds);
    }

    return builder.build();
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
   * @deprecated Use builder pattern with addCardParameters-method.
   * @return Form container
   */
  @Deprecated
  public FormContainer generatePaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                 String language, String amount, String currency, String orderId,
                                                 String description) {

    return paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .build();
  }

  /**
   * Get parameters for Payment request with the possibility to
   * <ul>
   *  <li>skip notifications displayed on the Payment Highway form</li>
   *  <li>exit from iframe after a result</li>
   *  <li>exit from iframe when redirecting the user to 3DS.</li>
   * </ul>
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
   * @deprecated Use builder pattern with addCardParameters-method.
   * @return Form container
   */
  @Deprecated
  public FormContainer generatePaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                 String language, String amount, String currency, String orderId,
                                                 String description, Boolean skipFormNotifications,
                                                 Boolean exitIframeOnResult, Boolean exitIframeOn3ds) {

    PaymentParameters builder =
      paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
        .language(language);

    if (skipFormNotifications != null) {
      builder.skipFormNotifications(skipFormNotifications);
    }
    if (exitIframeOnResult != null) {
      builder.exitIframeOnResult(exitIframeOnResult);
    }
    if (exitIframeOn3ds != null) {
      builder.exitIframeOn3ds(exitIframeOn3ds);
    }

    return builder.build();
  }

  /**
   * Get parameters for Payment request with the possibility to
   * <ul>
   *  <li>skip notifications displayed on the Payment Highway form</li>
   *  <li>exit from iframe after a result</li>
   *  <li>exit from iframe when redirecting the user to 3DS.</li>
   *  <li>force enable/disable 3ds</li>
   * </ul>
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
   * @deprecated Use builder pattern with addCardParameters-method.
   * @return Form container
   */
  @Deprecated
  public FormContainer generatePaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                 String language, String amount, String currency, String orderId,
                                                 String description, Boolean skipFormNotifications,
                                                 Boolean exitIframeOnResult, Boolean exitIframeOn3ds, Boolean use3ds) {

    PaymentParameters builder =
      paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
        .language(language);

    if (skipFormNotifications != null) {
      builder.skipFormNotifications(skipFormNotifications);
    }
    if (exitIframeOnResult != null) {
      builder.exitIframeOnResult(exitIframeOnResult);
    }
    if (exitIframeOn3ds != null) {
      builder.exitIframeOn3ds(exitIframeOn3ds);
    }
    if (use3ds != null) {
      builder.use3ds(use3ds);
    }

    return builder.build();
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
   * @deprecated Use builder pattern with addCardParameters-method.
   * @return Form container
   */
  @Deprecated
  public FormContainer generateAddCardAndPaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                           String language, String amount, String currency,
                                                           String orderId, String description) {

    return paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
      .language(language)
      .tokenize(true)
      .build();
  }

  /**
   * Get parameters for Add Card and Pay request with the possibility to
   * <ul>
   *  <li>skip notifications displayed on the Payment Highway form</li>
   *  <li>exit from iframe after a result</li>
   *  <li>exit from iframe when redirecting the user to 3DS.</li>
   * </ul>
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
   * @deprecated Use builder pattern with addCardParameters-method.
   * @return Form container
   */
  @Deprecated
  public FormContainer generateAddCardAndPaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                           String language, String amount, String currency,
                                                           String orderId, String description,
                                                           Boolean skipFormNotifications, Boolean exitIframeOnResult,
                                                           Boolean exitIframeOn3ds) {

    PaymentParameters builder =
      paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
        .language(language)
        .tokenize(true);

    if (skipFormNotifications != null) {
      builder.skipFormNotifications(skipFormNotifications);
    }
    if (exitIframeOnResult != null) {
      builder.exitIframeOnResult(exitIframeOnResult);
    }
    if (exitIframeOn3ds != null) {
      builder.exitIframeOn3ds(exitIframeOn3ds);
    }

    return builder.build();
  }

  /**
   * Get parameters for Add Card and Pay request with the possibility to
   * <ul>
   *  <li>skip notifications displayed on the Payment Highway form</li>
   *  <li>exit from iframe after a result</li>
   *  <li>exit from iframe when redirecting the user to 3DS.</li>
   *  <li>force enable/disable 3ds</li>
   * </ul>
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
   * @deprecated Use builder pattern with addCardParameters-method.
   * @return Form container
   */
  @Deprecated
  public FormContainer generateAddCardAndPaymentParameters(String successUrl, String failureUrl, String cancelUrl,
                                                           String language, String amount, String currency,
                                                           String orderId, String description,
                                                           Boolean skipFormNotifications, Boolean exitIframeOnResult,
                                                           Boolean exitIframeOn3ds, Boolean use3ds) {
    PaymentParameters builder =
      paymentParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
        .language(language)
        .tokenize(true);

    if (skipFormNotifications != null) {
      builder.skipFormNotifications(skipFormNotifications);
    }
    if (exitIframeOnResult != null) {
      builder.exitIframeOnResult(exitIframeOnResult);
    }
    if (exitIframeOn3ds != null) {
      builder.exitIframeOn3ds(exitIframeOn3ds);
    }
    if (use3ds != null) {
      builder.use3ds(use3ds);
    }

    return builder.build();
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
   * @deprecated Use builder pattern with payWithTokenAndCvcParameters-method.
   * @return FromContainer
   */
  @Deprecated
  public FormContainer generatePayWithTokenAndCvcParameters(UUID token, String successUrl, String failureUrl,
                                                            String cancelUrl, String language, String amount,
                                                            String currency, String orderId, String description) {

    PayWithTokenAndCvcParameters builder =
      payWithTokenAndCvcParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description, token)
        .language(language);

    return builder.build();
  }

  /**
   * Get parameters for Pay with Token and CVC request with the possibility to
   * <ul>
   *  <li>skip notifications displayed on the Payment Highway form</li>
   *  <li>exit from iframe after a result</li>
   *  <li>exit from iframe when redirecting the user to 3DS.</li>
   * </ul>
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
   * @deprecated Use builder pattern with payWithTokenAndCvcParameters-method.
   * @return Form container
   */
  @Deprecated
  public FormContainer generatePayWithTokenAndCvcParameters(UUID token, String successUrl, String failureUrl,
                                                            String cancelUrl, String language, String amount,
                                                            String currency, String orderId, String description,
                                                            Boolean skipFormNotifications, Boolean exitIframeOnResult,
                                                            Boolean exitIframeOn3ds) {

    PayWithTokenAndCvcParameters builder =
      payWithTokenAndCvcParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description, token)
        .language(language);

    if (skipFormNotifications != null) {
      builder.skipFormNotifications(skipFormNotifications);
    }
    if (exitIframeOnResult != null) {
      builder.exitIframeOnResult(exitIframeOnResult);
    }
    if (exitIframeOn3ds != null) {
      builder.exitIframeOn3ds(exitIframeOn3ds);
    }

    return builder.build();
  }

  /**
   * Get parameters for Pay with Token and CVC request with the possibility to
   * <ul>
   *  <li>skip notifications displayed on the Payment Highway form</li>
   *  <li>exit from iframe after a result</li>
   *  <li>exit from iframe when redirecting the user to 3DS.</li>
   *  <li>force enable/disable 3ds</li>
   * </ul>
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
   * @deprecated Use builder pattern with payWithTokenAndCvcParameters-method.
   * @return Form container
   */
  @Deprecated
  public FormContainer generatePayWithTokenAndCvcParameters(UUID token, String successUrl, String failureUrl,
                                                            String cancelUrl, String language, String amount,
                                                            String currency, String orderId, String description,
                                                            Boolean skipFormNotifications, Boolean exitIframeOnResult,
                                                            Boolean exitIframeOn3ds, Boolean use3ds) {

    PayWithTokenAndCvcParameters builder =
      payWithTokenAndCvcParameters(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description, token)
        .language(language);

    if (skipFormNotifications != null) {
      builder.skipFormNotifications(skipFormNotifications);
    }
    if (exitIframeOnResult != null) {
      builder.exitIframeOnResult(exitIframeOnResult);
    }
    if (exitIframeOn3ds != null) {
      builder.exitIframeOn3ds(exitIframeOn3ds);
    }
    if (use3ds != null) {
      builder.use3ds(use3ds);
    }

    return builder.build();
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
   * @deprecated Use builder pattern with mobilePayParametersBuilder-method.
   * @return Form container
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
   * @deprecated Use builder pattern with mobilePayParametersBuilder-method.
   * @return Form container
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
   * @deprecated Use builder pattern with mobilePayParametersBuilder-method.
   * @return Form container
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
    MobilePayParametersBuilder builder =
      mobilePayParametersBuilder(successUrl, failureUrl, cancelUrl, amount, currency, orderId, description)
        .language(language);

    if (exitIframeOnResult != null) {
      builder.exitIframeOnResult(exitIframeOnResult);
    }
    if (shopLogoUrl != null) {
      builder.shopLogoUrl(shopLogoUrl);
    }

    return builder.build();
  }
}
