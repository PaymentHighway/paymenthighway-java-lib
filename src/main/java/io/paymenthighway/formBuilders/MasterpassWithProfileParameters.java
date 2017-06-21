package io.paymenthighway.formBuilders;

public class MasterpassWithProfileParameters
  extends GenericPaymentParametersBuilder<MasterpassWithProfileParameters>
  implements CardFormParametersInterface {

    public MasterpassWithProfileParameters(
      String method,
      String signatureKeyId,
      String signatureSecret,
      String account,
      String merchant,
      String baseUrl,
      String successUrl,
      String failureUrl,
      String cancelUrl,
      long amount,
      String currency,
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
          Long.valueOf(amount).toString(),
          currency,
          orderId,
          description
        );
        serviceUri = "/form/view/masterpass_with_profile";
    }

    /**
     * Request shipping address from masterpass
     *
     * @param requestShippingAddress Request shipping address from masterpass.
     * @return Form builder
     */
    public MasterpassWithProfileParameters requestShippingAddress(Boolean requestShippingAddress) {
        addNameValuePair(FormBuilderConstants.SPH_REQUEST_SHIPPING_ADDRESS, requestShippingAddress.toString());
        return this;
    }
}
