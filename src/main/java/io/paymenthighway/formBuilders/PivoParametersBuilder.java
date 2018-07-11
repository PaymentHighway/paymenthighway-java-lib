package io.paymenthighway.formBuilders;

public class PivoParametersBuilder
    extends GenericPaymentParametersBuilder<PivoParametersBuilder>
    implements PivoParametersInterface {

    public PivoParametersBuilder(
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
        String description
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
        serviceUri = "/form/view/pivo";
    }

    /**
     * @param phoneNumber User’s phone number. Will be suggested (pre-filled) for User on desktop browser payments. The phone number needs to be in international format (e.g. +358401234567).
     * @return PivoParametersInterface
     */
    public PivoParametersBuilder phoneNumber(String phoneNumber) {
        addNameValuePair(FormBuilderConstants.SPH_PHONE_NUMBER, phoneNumber);
        return this;
    }

    /**
     * Reference number
     *
     * @param referenceNumber Reference number
     * @return PivoParametersInterface
     */
    public PivoParametersBuilder referenceNumber(String referenceNumber) {
        addNameValuePair(FormBuilderConstants.SPH_REFERENCE_NUMBER, referenceNumber);
        return this;
    }

    /**
     * When used, Pivo tries to open application with this url
     *
     * @param appUrl App url
     * @return Form builder
     */
    public PivoParametersInterface appUrl(String appUrl) {
        addNameValuePair(FormBuilderConstants.SPH_APP_URL, appUrl);
        return this;
    }
}
