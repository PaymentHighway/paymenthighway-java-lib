package io.paymenthighway.formBuilders;


import org.apache.http.message.BasicNameValuePair;

public class GenericCardFormBuilder<T> extends GenericFormBuilder<T> {

    public GenericCardFormBuilder(String method, String signatureKeyId, String signatureSecret, String account,
                                  String merchant, String baseUrl, String successUrl, String failureUrl, String cancelUrl) {
        super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl);
    }

    /**
     * Skip notifications displayed on the Payment Highway form
     *
     * @param skipFormNotifications Skip notifications displayed on the Payment Highway form.
     * @return T
     */
    public T skipFormNotifications(Boolean skipFormNotifications) {
        nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_SKIP_FORM_NOTIFICATIONS, skipFormNotifications.toString()));
        return (T) this;
    }

    /**
     * Exit from iframe after a result
     *
     * @param exitIframeOnResult Exit from iframe after a result.
     * @return T
     */
    public T exitIframeOnResult(Boolean exitIframeOnResult) {
        nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_RESULT, exitIframeOnResult.toString()));
        return(T) this;
    }

    /**
     * Exit from iframe when redirecting the user to 3DS.
     *
     * @param exitIframeOn3ds Exit from iframe when redirecting the user to 3DS.
     * @return T
     */
    public T exitIframeOn3ds(Boolean exitIframeOn3ds) {
        nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_EXIT_IFRAME_ON_THREE_D_SECURE, exitIframeOn3ds.toString()));
        return (T) this;
    }

    /**
     * Force enable/disable 3ds
     *
     * @param use3ds Force enable/disable 3ds.
     * @return T
     */
    public T use3ds(Boolean use3ds) {
        nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_USE_THREE_D_SECURE, use3ds.toString()));
        return (T) this;
    }
}
