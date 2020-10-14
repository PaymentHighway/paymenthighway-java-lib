package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PivoInitRequest extends GenericPaymentInitRequest {
    @JsonProperty("reference_number") private String referenceNumber;
    @JsonProperty("phone_number") private String phoneNumber;
    @JsonProperty("app_url") private String appUrl;

    /**
     *
     * @param amount amount
     * @param currency currency
     * @return builder
     */
    public static Builder Builder(long amount, String currency) {
        return new Builder(amount, currency);
    }

    /**
     *
     * @return reference number
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     *
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @return app url
     */
    public String getAppUrl() {
        return appUrl;
    }

    public static class Builder extends GenericPaymentInitBuilder<Builder> {

        private String referenceNumber;
        private String phoneNumber;
        private String appUrl;

        public Builder(long amount, String currency) {
            super(amount,currency);
        }

        /**
         * Reference number. In RF-format or in Finnish reference number format.
         *
         * @param referenceNumber Reference number
         * @return builder
         */
        public Builder setReferenceNumber(String referenceNumber) {
            this.referenceNumber = referenceNumber;
            return this;
        }

        /**
         * When used, phone number will be prefilled to form. Use international format e.g. "+358441234567"
         * @param phoneNumber phone number
         * @return builder
         */
        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        /**
         * Pivo launches this application when returning
         *
         * @param appUrl App url
         * @return builder
         */
        public Builder setAppUrl(String appUrl) {
            this.appUrl = appUrl;
            return this;
        }

        public PivoInitRequest build() {
            return new PivoInitRequest(this);
        }

    }

    private PivoInitRequest(Builder builder) {
        super(builder);
        this.referenceNumber = builder.referenceNumber;
        this.phoneNumber = builder.phoneNumber;
        this.appUrl = builder.appUrl;
        this.isEstimatedAmount = builder.isEstimatedAmount;
    }
}
