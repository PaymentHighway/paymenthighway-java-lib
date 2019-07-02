package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;

public interface AfterPayParametersInterface {

    /**
     * @param socialSecurityNumber The customer's social security number. If set, the value will be pre-filled on the form.
     * @return Form builder
     */
    AfterPayParametersInterface socialSecurityNumber(String socialSecurityNumber);

    /**
     * @param emailAddress The customer's email address. If set, the value will be pre-filled on the form.
     * @return Form builder
     */
    AfterPayParametersInterface emailAddress(String emailAddress);

    /**
     * Exit from iframe after a result
     *
     * @param exitIframeOnResult Exit from iframe after a result.
     * @return Form builder
     */
    AfterPayParametersInterface exitIframeOnResult(Boolean exitIframeOnResult);

    /**
     * Set PaymentHighway form language
     *
     * @param language Two character language code e.g. FI or EN. If omitted, default language from user's browser's settings is used.
     * @return Form builder
     */
    AfterPayParametersInterface language(String language);

    /**
     * The URL the PH server makes request to after the transaction is handled. The payment itself may still be rejected.
     *
     * @param successUrl Webhook url to call when request is successfully handled
     * @return Form builder
     */
    AfterPayParametersInterface webhookSuccessUrl(String successUrl);

    /**
     * The URL the PH server makes request to after a failure such as an authentication or connectivity error.
     *
     * @param failureUrl Webhook url to call when request failed
     * @return Form builder
     */
    AfterPayParametersInterface webhookFailureUrl(String failureUrl);

    /**
     * The URL the PH server makes request to after cancelling the transaction (clicking on the cancel button).
     *
     * @param cancelUrl Webhook url to call when user cancels request
     * @return Form builder
     */
    AfterPayParametersInterface webhookCancelUrl(String cancelUrl);


    /**
     * Builds form parameters
     *
     * @return FormContainer
     */
    FormContainer build();
}
