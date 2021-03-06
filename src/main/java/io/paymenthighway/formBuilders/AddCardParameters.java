package io.paymenthighway.formBuilders;

public class AddCardParameters extends GenericCardFormBuilder<AddCardParameters> implements AddCardParametersInterface {

    public AddCardParameters(
      String method,
      String signatureKeyId,
      String signatureSecret,
      String account,
      String merchant,
      String baseUrl,
      String successUrl,
      String failureUrl,
      String cancelUrl
    ) {
        super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl);
        serviceUri = "/form/view/add_card";
    }

    /**
     * Get parameters for Add Card request with the possibility to accept cards that require CVC.
     *
     * @param acceptCvcRequired Accept a payment card token even if the card requires CVC for payments.
     * @return AddCardParameters
     */
    public AddCardParameters acceptCvcRequired(Boolean acceptCvcRequired) {
        addNameValuePair(FormBuilderConstants.SPH_ACCEPT_CVC_REQUIRED, acceptCvcRequired);
        return this;
    }
}
