package io.paymenthighway.formBuilders;


import org.apache.http.message.BasicNameValuePair;

public class GenericCardFormBuilder extends GenericFormBuilder {

    public GenericCardFormBuilder(String method, String signatureKeyId, String signatureSecret, String account,
                                  String merchant, String baseUrl, String successUrl, String failureUrl, String cancelUrl,
                                  String language) {
        super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl, language);
    }

    /**
     * Skip notifications displayed on the Payment Highway form
     *
     * @param skipFormNotifications Skip notifications displayed on the Payment Highway form.
     * @return GenericCardFormBuilder
     */
    public GenericCardFormBuilder skipFormNotifications(Boolean skipFormNotifications) {
        nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SKIP_FORM_NOTIFICATIONS, skipFormNotifications.toString()));
        return this;
    }

    /**
     * Exit from iframe after a result
     *
     * @param exitIframeOnResult Exit from iframe after a result.
     * @return GenericCardFormBuilder
     */
    public GenericCardFormBuilder exitIframeOnResult(Boolean exitIframeOnResult) {
        nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, exitIframeOnResult.toString()));
        return this;
    }

    /**
     * Exit from iframe when redirecting the user to 3DS.
     *
     * @param exitIframeOn3ds Exit from iframe when redirecting the user to 3DS.
     * @return GenericCardFormBuilder
     */
    public GenericCardFormBuilder exitIframeOn3ds(Boolean exitIframeOn3ds) {
        nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_THREE_D_SECURE, exitIframeOn3ds.toString()));
        return this;
    }

    /**
     * Force enable/disable 3ds
     *
     * @param use3ds Force enable/disable 3ds.
     * @return GenericCardFormBuilder
     */
    public GenericCardFormBuilder use3ds(Boolean use3ds) {
        nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_USE_THREE_D_SECURE, use3ds.toString()));
        return this;
    }
}
