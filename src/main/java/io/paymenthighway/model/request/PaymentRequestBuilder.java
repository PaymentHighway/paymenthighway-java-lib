package io.paymenthighway.model.request;

public class PaymentRequestBuilder extends Request {

    public PaymentRequestBuilder(){}

    public static PivoInitRequest pivoInitRequest(long amount, String currency) {
        return new PivoInitRequest(amount, currency);
    }


}
