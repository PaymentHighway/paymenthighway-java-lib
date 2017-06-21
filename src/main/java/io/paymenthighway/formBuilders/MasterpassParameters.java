package io.paymenthighway.formBuilders;

public class MasterpassParameters
  extends GenericPaymentParametersBuilder<MasterpassParameters>
  implements CardFormParametersInterface {

    public MasterpassParameters(
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
        serviceUri = "/form/view/masterpass";
    }

    /**
     * Request shipping address from masterpass
     *
     * @param requestShippingAddress Request shipping address from masterpass.
     * @return Form builder
     */
    public MasterpassParameters requestShippingAddress(Boolean requestShippingAddress) {
        addNameValuePair(FormBuilderConstants.SPH_REQUEST_SHIPPING_ADDRESS, requestShippingAddress.toString());
        return this;
    }
}
