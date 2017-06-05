package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;

public interface MobilePayParametersInterface {
    /**
     * Exit from iframe after a result
     *
     * @param exitIframeOnResult Exit from iframe after a result.
     * @return MobilePayParametersInterface
     */
    MobilePayParametersInterface exitIframeOnResult(Boolean exitIframeOnResult);

    /**
     * @param shopLogoUrl The logo must be 250x250 pixel in .png format and must be hosted on a HTTPS (secure) server.
     * @return MobilePayParametersInterface
     */
    MobilePayParametersInterface shopLogoUrl(String shopLogoUrl);

    /**
     * @param phoneNumber Customer phone number with country code e.q. +358449876543. Makes it easier for the customer to identify himself toward the MPO Website.
     * @return MobilePayParametersInterface
     */
    MobilePayParametersInterface phoneNumber(String phoneNumber);

    /**
     * @param shopName Max 100 AN. Name of the shop/merchant. MobilePay app displays this under the shop logo.  If omitted, the merchant name from PH is used.
     * @return MobilePayParametersInterface
     */
    MobilePayParametersInterface shopName(String shopName);

    /**
     * @param subMerchantId Max 15 AN. Should only be used by a Payment Facilitator customer
     * @return MobilePayParametersInterface
     */
    MobilePayParametersInterface subMerchantId(String subMerchantId);

    /**
     * @param subMerchantName Max 21 AN. Should only be used by a Payment Facilitator customer
     * @return MobilePayParametersInterface
     */
    MobilePayParametersInterface subMerchantName(String subMerchantName);

    /**
     * Set PaymentHighway form language
     *
     * @param language Two character language code e.q. FI or EN. If omitted, default language from user's browser's settings is used.
     * @return MobilePayParametersInterface
     */
    MobilePayParametersInterface language(String language);

    /**
     * The URL the PH server makes request after the transaction is handled. The payment itself may still be rejected.
     * @param successUrl
     * @return CardFormParametersInterface
     */
    MobilePayParametersInterface webhookSuccessUrl(String successUrl);

    /**
     * The URL the PH server makes request after a failure such as an authentication or connectivity error.
     * @param failureUrl
     * @return CardFormParametersInterface
     */
    MobilePayParametersInterface webhookFailureUrl(String failureUrl);

    /**
     * The URL the PH server makes request after cancelling the transaction (clicking on the cancel button).
     * @param cancelUrl
     * @return CardFormParametersInterface
     */
    MobilePayParametersInterface webhookCancelUrl(String cancelUrl);

    /**
     * Delay for webhook in seconds. Between 0-900
     * @param delay
     * @return CardFormParametersInterface
     */
    MobilePayParametersInterface webhookDelay(Integer delay);


    /**
     * Builds form parameters
     *
     * @return FormContainer
     */
    FormContainer build();
}
