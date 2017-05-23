package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;

public interface CardFormParametersInterface {
    /**
     * Skip notifications displayed on the Payment Highway form
     *
     * @param skipFormNotifications Skip notifications displayed on the Payment Highway form.
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface skipFormNotifications(Boolean skipFormNotifications);

    /**
     * Exit from iframe after a result
     *
     * @param exitIframeOnResult Exit from iframe after a result.
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface exitIframeOnResult(Boolean exitIframeOnResult);

    /**
     * Exit from iframe when redirecting the user to 3DS.
     *
     * @param exitIframeOn3ds Exit from iframe when redirecting the user to 3DS.
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface exitIframeOn3ds(Boolean exitIframeOn3ds);

    /**
     * Force enable/disable 3ds
     *
     * @param use3ds Force enable/disable 3ds.
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface use3ds(Boolean use3ds);

    /**
     * Set PaymentHighway form language
     *
     * @param language Two character language code e.q. FI or EN. If omitted, default language from user's browser's settings is used.
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface language(String language);


    /**
     * Show payment method selection page.
     *
     * @param show Show payment method selection page.
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface showPaymentMethodSelectionPage(Boolean show);

    /**
     * Tokenize card or mobile wallet.
     *
     * @param tokenize Tokenize card or mobile wallet.
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface tokenize(Boolean tokenize);

    /**
     * The URL the PH server makes request after the transaction is handled. The payment itself may still be rejected.
     * @param successUrl
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface webhookSuccessUrl(String successUrl);

    /**
     * The URL the PH server makes request after a failure such as an authentication or connectivity error.
     * @param failureUrl
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface webhookFailureUrl(String failureUrl);

    /**
     * The URL the PH server makes request after cancelling the transaction (clicking on the cancel button).
     * @param cancelUrl
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface webhookCancelUrl(String cancelUrl);

    /**
     * Delay for webhook in seconds. Between 0-900
     * @param delay
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface webhookDelay(Integer delay);

    /**
     * Show payment method selection page.
     *
     * @param show Show payment method selection page.
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface showPaymentMethodSelectionPage(Boolean show);

    /**
     * Tokenize card or mobile wallet.
     *
     * @param tokenize Tokenize card or mobile wallet.
     * @return CardFormParametersInterface
     */
    CardFormParametersInterface tokenize(Boolean tokenize);

    /**
     * Builds form parameters
     *
     * @return FormContainer
     */
    FormContainer build();
}
