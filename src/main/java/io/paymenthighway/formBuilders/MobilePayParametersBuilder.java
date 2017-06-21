package io.paymenthighway.formBuilders;

public class MobilePayParametersBuilder
  extends GenericFormBuilder<MobilePayParametersBuilder>
  implements MobilePayParametersInterface {

    public MobilePayParametersBuilder(String method, String signatureKeyId, String signatureSecret, String account, String merchant,
                                      String baseUrl, String successUrl, String failureUrl, String cancelUrl,
                                      String amount, String currency, String orderId, String description) {
        super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl);
        addNameValuePair(FormBuilderConstants.SPH_AMOUNT, amount);
        addNameValuePair(FormBuilderConstants.SPH_CURRENCY, currency);
        addNameValuePair(FormBuilderConstants.SPH_ORDER, orderId);
        addNameValuePair(FormBuilderConstants.DESCRIPTION, description);
        serviceUri = "/form/view/mobilepay";
    }

    /**
     * Exit from iframe after a result
     *
     * @param exitIframeOnResult Exit from iframe after a result.
     * @return MobilePayParametersInterface
     */
    public MobilePayParametersBuilder exitIframeOnResult(Boolean exitIframeOnResult) {
        addNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, exitIframeOnResult.toString());
        return this;
    }

    /**
     * @param shopLogoUrl The logo must be 250x250 pixel in .png format and must be hosted on a HTTPS (secure) server.
     * @return MobilePayParametersInterface
     */
    public MobilePayParametersBuilder shopLogoUrl(String shopLogoUrl) {
        addNameValuePair(FormBuilderConstants.SPH_SHOP_LOGO_URL, shopLogoUrl);
        return this;
    }

    /**
     * @param phoneNumber Customer phone number with country code e.q. +358449876543. Makes it easier for the customer to identify himself toward the MPO Website.
     * @return MobilePayParametersInterface
     */
    public MobilePayParametersBuilder phoneNumber(String phoneNumber) {
        addNameValuePair(FormBuilderConstants.SPH_MOBILEPAY_PHONE_NUMBER, phoneNumber);
        return this;
    }

    /**
     * @param shopName Max 100 AN. Name of the shop/merchant. MobilePay app displays this under the shop logo.  If omitted, the merchant name from PH is used.
     * @return MobilePayParametersInterface
     */
    public MobilePayParametersBuilder shopName(String shopName) {
        addNameValuePair(FormBuilderConstants.SPH_MOBILEPAY_SHOP_NAME, shopName);
        return this;
    }

    /**
     * @param subMerchantId Max 15 AN. Should only be used by a Payment Facilitator customer
     * @return MobilePayParametersInterface
     */
    public MobilePayParametersBuilder subMerchantId(String subMerchantId) {
        addNameValuePair(FormBuilderConstants.SPH_SUB_MERCHANT_ID, subMerchantId);
        return this;
    }

    /**
     * @param subMerchantName Max 21 AN. Should only be used by a Payment Facilitator customer
     * @return MobilePayParametersInterface
     */
    public MobilePayParametersBuilder subMerchantName(String subMerchantName) {
        addNameValuePair(FormBuilderConstants.SPH_SUB_MERCHANT_NAME, subMerchantName);
        return this;
    }
}
