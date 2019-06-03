package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PivoInitRequest extends GenericPaymentInitRequest<PivoInitRequest> {
    @JsonProperty("reference_number") private String referenceNumber;
    @JsonProperty("phone_number") private String phoneNumber;
    @JsonProperty("app_url") private String appUrl;


    public PivoInitRequest(long amount, String currency) {
        super(amount, currency);
    }

    /**
     * Reference number. In RF-format or in Finnish reference number format.
     *
     * @param referenceNumber Reference number
     * @return builder
     */
    public PivoInitRequest setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    /**
     * When used, phone number will be prefilled to form. Use international format e.g. "+358441234567"
     * @param phoneNumber phone number
     * @return builder
     */
    public PivoInitRequest setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Pivo launches this application when returning
     *
     * @param appUrl App url
     * @return builder
     */
    public PivoInitRequest setAppUrl(String appUrl) {
        this.appUrl = appUrl;
        return this;
    }
}
