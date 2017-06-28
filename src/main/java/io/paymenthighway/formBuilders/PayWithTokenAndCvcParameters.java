package io.paymenthighway.formBuilders;

import org.apache.http.message.BasicNameValuePair;

import java.util.UUID;

public class PayWithTokenAndCvcParameters
  extends GenericPaymentParametersBuilder<PayWithTokenAndCvcParameters>
  implements CardFormParametersInterface {

  public PayWithTokenAndCvcParameters(
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
    String description,
    UUID token
  ) {
    super(method, signatureKeyId, signatureSecret, account, merchant, baseUrl, successUrl, failureUrl, cancelUrl, amount, currency, orderId, description);
    nameValuePairs.add(new BasicNameValuePair(FormBuilderConstants.SPH_TOKEN, token.toString()));
    serviceUri = "/form/view/pay_with_token_and_cvc";
  }
}
