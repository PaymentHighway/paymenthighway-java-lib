package io.paymenthighway.formBuilders;

import org.apache.http.message.BasicNameValuePair;

public class AddCardParameters extends GenericCardFormBuilder {

    public AddCardParameters(String method, String signatureKeyId, String signatureSecret, String account, String merchant,
                             String baseUrl, String successUrl, String failureUrl, String cancelUrl, String language) {
        super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl, language);
        serviceUri = "/form/view/add_card";
    }

    /**
     * Get parameters for Add Card request with the possibility to accept cards that require CVC.
     *
     * @param acceptCvcRequired Accept a payment card token even if the card requires CVC for payments.
     * @return AddCardParameters
     */
    public GenericCardFormBuilder acceptCvcRequired(Boolean acceptCvcRequired) {
        nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_ACCEPT_CVC_REQUIRED, acceptCvcRequired.toString()));
        return this;
    }
}
