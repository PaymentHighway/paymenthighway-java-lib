package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;

public interface PivoParametersInterface {
    /**
     * Exit from iframe after a result
     *
     * @param exitIframeOnResult Exit from iframe after a result.
     * @return Form builder
     */
    PivoParametersInterface exitIframeOnResult(Boolean exitIframeOnResult);
    
    /**
     * @param phoneNumber User’s phone number. Will be suggested (pre-filled). The phone number needs to be in international format (e.g. +358401234567).
     * @return Form builder
     */
    PivoParametersInterface phoneNumber(String phoneNumber);

    /**
     * Set PaymentHighway form language
     *
     * @param language Two character language code e.g. FI or EN. If omitted, default language from user's browser's settings is used.
     * @return Form builder
     */
    PivoParametersInterface language(String language);

    /**
     * The URL the PH server makes request to after the transaction is handled. The payment itself may still be rejected.
     *
     * @param successUrl Webhook url to call when request is successfully handled
     * @return Form builder
     */
    PivoParametersInterface webhookSuccessUrl(String successUrl);

    /**
     * The URL the PH server makes request to after a failure such as an authentication or connectivity error.
     *
     * @param failureUrl Webhook url to call when request failed
     * @return Form builder
     */
    PivoParametersInterface webhookFailureUrl(String failureUrl);

    /**
     * The URL the PH server makes request to after cancelling the transaction (clicking on the cancel button).
     *
     * @param cancelUrl Webhook url to call when user cancels request
     * @return Form builder
     */
    PivoParametersInterface webhookCancelUrl(String cancelUrl);

    /**
     * Delay for webhook in seconds. Between 0-900
     *
     * @param delay Webhook triggering delay in seconds
     * @return Form builder
     */
    PivoParametersInterface webhookDelay(Integer delay);

    /**
     * Reference number
     *
     * @param referenceNumber Reference number
     * @return Form builder
     */
    PivoParametersInterface referenceNumber(String referenceNumber);

    /**
     * When used, Pivo tries to open application with this url
     *
     * @param appUrl App url
     * @return Form builder
     */
    PivoParametersInterface appUrl(String appUrl);

    /**
     * Builds form parameters
     *
     * @return FormContainer
     */
    FormContainer build();
}
