package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;

public interface MobilePayParametersInterface {
    /**
     * Exit from iframe after a result
     *
     * @param exitIframeOnResult Exit from iframe after a result.
     * @return MobilePayParametersBuilder
     */
    MobilePayParametersInterface exitIframeOnResult(Boolean exitIframeOnResult);

    /**
     * @param shopLogoUrl The logo must be 250x250 pixel in .png format and must be hosted on a HTTPS (secure) server.
     * @return MobilePayParametersBuilder
     */
    MobilePayParametersInterface shopLogoUrl(String shopLogoUrl);

    /**
     * @param phoneNumber Customer phone number with country code e.q. +358449876543. Makes it easier for the customer to identify himself toward the MPO Website.
     * @return MobilePayParametersBuilder
     */
    MobilePayParametersInterface phoneNumber(String phoneNumber);

    /**
     * @param shopName Max 100 AN. Name of the shop/merchant. MobilePay app displays this under the shop logo.  If omitted, the merchant name from PH is used.
     * @return MobilePayParametersBuilder
     */
    MobilePayParametersInterface shopName(String shopName);

    /**
     * @param subMerchantId Max 15 AN. Should only be used by a Payment Facilitator customer
     * @return MobilePayParametersBuilder
     */
    MobilePayParametersInterface subMerchantId(String subMerchantId);

    /**
     * @param subMerchantName Max 21 AN. Should only be used by a Payment Facilitator customer
     * @return MobilePayParametersBuilder
     */
    MobilePayParametersInterface subMerchantName(String subMerchantName);
    MobilePayParametersInterface language(String language);
    FormContainer build();
}
