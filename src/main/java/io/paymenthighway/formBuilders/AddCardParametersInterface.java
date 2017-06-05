package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;

public interface AddCardParametersInterface {
    /**
     * Skip notifications displayed on the Payment Highway form
     *
     * @param skipFormNotifications Skip notifications displayed on the Payment Highway form.
     * @return AddCardParametersInterface
     */
    AddCardParametersInterface skipFormNotifications(Boolean skipFormNotifications);

    /**
     * Exit from iframe after a result
     *
     * @param exitIframeOnResult Exit from iframe after a result.
     * @return AddCardParametersInterface
     */
    AddCardParametersInterface exitIframeOnResult(Boolean exitIframeOnResult);

    /**
     * Exit from iframe when redirecting the user to 3DS.
     *
     * @param exitIframeOn3ds Exit from iframe when redirecting the user to 3DS.
     * @return AddCardParametersInterface
     */
    AddCardParametersInterface exitIframeOn3ds(Boolean exitIframeOn3ds);

    /**
     * Force enable/disable 3ds
     *
     * @param use3ds Force enable/disable 3ds.
     * @return AddCardParametersInterface
     */
    AddCardParametersInterface use3ds(Boolean use3ds);

    /**
     * Set PaymentHighway form language
     *
     * @param language Two character language code e.q. FI or EN. If omitted, default language from user's browser's settings is used.
     * @return AddCardParametersInterface
     */
    AddCardParametersInterface language(String language);

    /**
     * Get parameters for Add Card request with the possibility to accept cards that require CVC.
     *
     * @param acceptCvcRequired Accept a payment card token even if the card requires CVC for payments.
     * @return AddCardParametersInterface
     */
    AddCardParametersInterface acceptCvcRequired(Boolean acceptCvcRequired);

    /**
     * The URL the PH server makes request after the transaction is handled. The payment itself may still be rejected.
     * @param successUrl
     * @return CardFormParametersInterface
     */
    AddCardParametersInterface webhookSuccessUrl(String successUrl);

    /**
     * The URL the PH server makes request after a failure such as an authentication or connectivity error.
     * @param failureUrl
     * @return CardFormParametersInterface
     */
    AddCardParametersInterface webhookFailureUrl(String failureUrl);

    /**
     * The URL the PH server makes request after cancelling the transaction (clicking on the cancel button).
     * @param cancelUrl
     * @return CardFormParametersInterface
     */
    AddCardParametersInterface webhookCancelUrl(String cancelUrl);

    /**
     * Delay for webhook in seconds. Between 0-900
     * @param delay
     * @return CardFormParametersInterface
     */
    AddCardParametersInterface webhookDelay(Integer delay);

    /**
     * Builds form parameters
     *
     * @return FormContainer
     */
    FormContainer build();
}
