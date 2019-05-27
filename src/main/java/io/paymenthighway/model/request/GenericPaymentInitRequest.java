package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericPaymentInitRequest<T> extends Request {
    @JsonProperty("amount") private Long amount;
    @JsonProperty("currency") private String currency;
    @JsonProperty("order") private String order;
    @JsonProperty("language") private String language;
    @JsonProperty("description") private String description;
    @JsonProperty("sub_merchant_id") private String subMerchantId;
    @JsonProperty("sub_merchant_name") private String subMerchantName;
    @JsonProperty("is_estimated_amount") private boolean isEstimatedAmount;
    @JsonProperty("webhook_success_url") private String webhookSuccessUrl;
    @JsonProperty("webhook_cancel_url") private String webhookCancelUrl;
    @JsonProperty("webhook_failure_url") private String webhookFailureUrl;

    public GenericPaymentInitRequest(Long amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Merchant defined order identifier. Should be unique per transaction.
     *
     * @param order Order identifier
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T order(String order) {
        this.order = order;
        return (T) this;
    }

    /**
     * The URL the PH server makes request to after the transaction is handled. The payment itself may still be rejected.
     *
     * @param url Webhook url to call when request is successfully handled
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T webhookSuccessUrl(String url) {
        this.webhookSuccessUrl = url;
        return (T) this;
    }

    /**
     * The URL the PH server makes request to after cancelling the transaction (clicking on the cancel button).
     *
     * @param url Webhook url to call when user cancels request
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T webhookCancelUrl(String url) {
        this.webhookCancelUrl = url;
        return (T) this;
    }

    /**
     * The URL the PH server makes request to after a failure such as an authentication or connectivity error.
     *
     * @param url Webhook url to call when request failed
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T webhookFailureUrl(String url) {
        this.webhookFailureUrl = url;
        return (T) this;
    }

    /**
     * The order description shown to the user
     *
     * @param description Description
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T description(String description) {
        this.description = description;
        return (T) this;
    }

    /**
     * Set PaymentHighway form language
     *
     * @param language Two character language code e.g. FI or EN. If omitted, default language from user's browser's settings is used.
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T language(String language) {
        this.language = language;
        return (T) this;
    }

    /**
     * Sub merchant id
     *
     * @param id Sub merchant id
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T subMerchantId(String id) {
        this.subMerchantId = id;
        return (T) this;
    }

    /**
     * Sub merchant name
     * @param name Sub merchant name
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T subMerchantName(String name) {
        this.subMerchantName = name;
        return (T) this;
    }

}
