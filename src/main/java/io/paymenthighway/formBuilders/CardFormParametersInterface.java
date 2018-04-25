package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;

public interface CardFormParametersInterface {
    /**
     * Skip notifications displayed on the Payment Highway form
     *
     * @param skipFormNotifications Skip notifications displayed on the Payment Highway form.
     * @return Form builder
     */
    CardFormParametersInterface skipFormNotifications(Boolean skipFormNotifications);

    /**
     * Exit from iframe after a result
     *
     * @param exitIframeOnResult Exit from iframe after a result.
     * @return Form builder
     */
    CardFormParametersInterface exitIframeOnResult(Boolean exitIframeOnResult);

    /**
     * Exit from iframe when redirecting the user to 3DS.
     *
     * @param exitIframeOn3ds Exit from iframe when redirecting the user to 3DS.
     * @return Form builder
     */
    CardFormParametersInterface exitIframeOn3ds(Boolean exitIframeOn3ds);

    /**
     * Force enable/disable 3ds
     *
     * @param use3ds Force enable/disable 3ds.
     * @return Form builder
     */
    CardFormParametersInterface use3ds(Boolean use3ds);

    /**
     * Set PaymentHighway form language
     *
     * @param language Two character language code e.g. FI or EN. If omitted, default language from user's browser's settings is used.
     * @return Form builder
     */
    CardFormParametersInterface language(String language);


    /**
     * Show payment method selection page.
     *
     * @param show Show payment method selection page.
     * @return Form builder
     */
    CardFormParametersInterface showPaymentMethodSelectionPage(Boolean show);

    /**
     * Tokenize card or mobile wallet.
     *
     * @param tokenize Tokenize card or mobile wallet.
     * @return Form builder
     */
    CardFormParametersInterface tokenize(Boolean tokenize);

    /**
     * The URL the PH server makes request to after the transaction is handled. The payment itself may still be rejected.
     *
     * @param successUrl Webhook url to call when request is successfully handled
     * @return Form builder
     */
    CardFormParametersInterface webhookSuccessUrl(String successUrl);

    /**
     * The URL the PH server makes request to after a failure such as an authentication or connectivity error.
     *
     * @param failureUrl Webhook url to call when request failed
     * @return Form builder
     */
    CardFormParametersInterface webhookFailureUrl(String failureUrl);

    /**
     * The URL the PH server makes request to after cancelling the transaction (clicking on the cancel button).
     *
     * @param cancelUrl Webhook url to call when user cancels request
     * @return Form builder
     */
    CardFormParametersInterface webhookCancelUrl(String cancelUrl);

    /**
     * Delay for webhook in seconds. Between 0-900
     *
     * @param delay Webhook triggering delay in seconds
     * @return Form builder
     */
    CardFormParametersInterface webhookDelay(Integer delay);

    /**
     * Builds form parameters
     *
     * @return FormContainer
     */
    FormContainer build();
}
