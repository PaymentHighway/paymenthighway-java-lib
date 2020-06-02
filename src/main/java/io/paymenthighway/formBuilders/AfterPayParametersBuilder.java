package io.paymenthighway.formBuilders;

public class AfterPayParametersBuilder
    extends GenericPaymentParametersBuilder<AfterPayParametersBuilder>
    implements AfterPayParametersInterface {

    public AfterPayParametersBuilder(
        String method,
        String signatureKeyId,
        String signatureSecret,
        String account,
        String merchant,
        String baseUrl,
        String successUrl,
        String failureUrl,
        String cancelUrl,
        Long amount,
        String orderId,
        String description,
        String orderDescription
    ) {
        super(
            method,
            signatureKeyId,
            signatureSecret,
            account,
            merchant,
            baseUrl,
            successUrl,
            failureUrl,
            cancelUrl,
            amount.toString(),
            "EUR",
            orderId,
            description
        );

        serviceUri = "/form/view/afterpay";

        addNameValuePair(FormBuilderConstants.SPH_ORDER_DESCRIPTION, orderDescription);
    }

    /**
     * @param socialSecurityNumber The customer's social security number. If set, the value will be pre-filled on the form.
     * @return Form builder
     */
    public AfterPayParametersInterface socialSecurityNumber(String socialSecurityNumber) {
        addNameValuePair(FormBuilderConstants.SPH_SOCIAL_SECURITY_NUMBER, socialSecurityNumber);
        return this;
    }

    /**
     * @param emailAddress The customer's email address. If set, the value will be pre-filled on the form.
     * @return Form builder
     */
    public AfterPayParametersInterface emailAddress(String emailAddress) {
        addNameValuePair(FormBuilderConstants.SPH_EMAIL_ADDRESS, emailAddress);
        return this;
    }
}
