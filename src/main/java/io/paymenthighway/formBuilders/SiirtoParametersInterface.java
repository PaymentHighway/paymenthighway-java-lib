package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;

public interface SiirtoParametersInterface {
    /**
     * Exit from iframe after a result
     *
     * @param exitIframeOnResult Exit from iframe after a result.
     * @return Form builder
     */
    SiirtoParametersInterface exitIframeOnResult(Boolean exitIframeOnResult);

    /**
     * @param phoneNumber Customer phone number with country code e.q. +358449876543. Makes it easier for the customer to identify himself.
     * @return Form builder
     */
    SiirtoParametersInterface phoneNumber(String phoneNumber);

    /**
     * Set PaymentHighway form language
     *
     * @param language Two character language code e.q. FI or EN. If omitted, default language from user's browser's settings is used.
     * @return Form builder
     */
    SiirtoParametersInterface language(String language);

    /**
     * The URL the PH server makes request after the transaction is handled. The payment itself may still be rejected.
     *
     * @param successUrl Webhook url to call when request is successfully handled
     * @return Form builder
     */
    SiirtoParametersInterface webhookSuccessUrl(String successUrl);

    /**
     * The URL the PH server makes request after a failure such as an authentication or connectivity error.
     *
     * @param failureUrl Webhook url to call when request failed
     * @return Form builder
     */
    SiirtoParametersInterface webhookFailureUrl(String failureUrl);

    /**
     * The URL the PH server makes request after cancelling the transaction (clicking on the cancel button).
     *
     * @param cancelUrl Webhook url to call when user cancels request
     * @return Form builder
     */
    SiirtoParametersInterface webhookCancelUrl(String cancelUrl);

    /**
     * Delay for webhook in seconds. Between 0-900
     *
     * @param delay Webhook triggering delay in seconds
     * @return Form builder
     */
    SiirtoParametersInterface webhookDelay(Integer delay);

    /**
     * Reference number
     *
     * @param referenceNumber Reference number
     * @return Form builder
     */
    SiirtoParametersInterface referenceNumber(String referenceNumber);


    /**
     * Builds form parameters
     *
     * @return FormContainer
     */
    FormContainer build();
}
