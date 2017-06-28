package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;

public interface MobilePayParametersInterface {
    /**
     * Exit from iframe after a result
     *
     * @param exitIframeOnResult Exit from iframe after a result.
     * @return Form builder
     */
    MobilePayParametersInterface exitIframeOnResult(Boolean exitIframeOnResult);

    /**
     * @param shopLogoUrl The logo must be 250x250 pixel in .png format and must be hosted on a HTTPS (secure) server.
     * @return Form builder
     */
    MobilePayParametersInterface shopLogoUrl(String shopLogoUrl);

    /**
     * @param phoneNumber Customer phone number with country code e.q. +358449876543. Makes it easier for the customer to identify himself toward the MPO Website.
     * @return Form builder
     */
    MobilePayParametersInterface phoneNumber(String phoneNumber);

    /**
     * @param shopName Max 100 AN. Name of the shop/merchant. MobilePay app displays this under the shop logo.  If omitted, the merchant name from PH is used.
     * @return Form builder
     */
    MobilePayParametersInterface shopName(String shopName);

    /**
     * @param subMerchantId Max 15 AN. Should only be used by a Payment Facilitator customer
     * @return Form builder
     */
    MobilePayParametersInterface subMerchantId(String subMerchantId);

    /**
     * @param subMerchantName Max 21 AN. Should only be used by a Payment Facilitator customer
     * @return Form builder
     */
    MobilePayParametersInterface subMerchantName(String subMerchantName);

    /**
     * Set PaymentHighway form language
     *
     * @param language Two character language code e.q. FI or EN. If omitted, default language from user's browser's settings is used.
     * @return Form builder
     */
    MobilePayParametersInterface language(String language);

    /**
     * The URL the PH server makes request after the transaction is handled. The payment itself may still be rejected.
     *
     * @param successUrl Webhook url to call when request is successfully handled
     * @return Form builder
     */
    MobilePayParametersInterface webhookSuccessUrl(String successUrl);

    /**
     * The URL the PH server makes request after a failure such as an authentication or connectivity error.
     *
     * @param failureUrl Webhook url to call when request failed
     * @return Form builder
     */
    MobilePayParametersInterface webhookFailureUrl(String failureUrl);

    /**
     * The URL the PH server makes request after cancelling the transaction (clicking on the cancel button).
     *
     * @param cancelUrl Webhook url to call when user cancels request
     * @return Form builder
     */
    MobilePayParametersInterface webhookCancelUrl(String cancelUrl);

    /**
     * Delay for webhook in seconds. Between 0-900
     *
     * @param delay Webhook triggering delay in seconds
     * @return Form builder
     */
    MobilePayParametersInterface webhookDelay(Integer delay);

    /**
     * Builds form parameters
     *
     * @return FormContainer
     */
    FormContainer build();
}
