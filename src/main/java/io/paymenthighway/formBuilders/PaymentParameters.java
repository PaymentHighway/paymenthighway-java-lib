package io.paymenthighway.formBuilders;

public class PaymentParameters
  extends GenericPaymentParametersBuilder<PaymentParameters>
  implements CardFormParametersInterface {

    public PaymentParameters(
      String method,
      String signatureKeyId,
      String signatureSecret,
      String account,
      String merchant,
      String baseUrl,
      String successUrl,
      String failureUrl,
      String cancelUrl,
      String amount,
      String currency,
      String orderId,
      String description
    ) {
        super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl, amount, currency, orderId, description);
        serviceUri = "/form/view/pay_with_card";
    }
}
