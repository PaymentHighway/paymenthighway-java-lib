package io.paymenthighway.model.request;

public class GenericPaymentInitBuilder<T> {
    protected Long amount;
    protected String currency;
    protected String order;
    protected String language;
    protected String description;
    protected String subMerchantId;
    protected String subMerchantName;
    protected boolean isEstimatedAmount;
    protected String webhookSuccessUrl;
    protected String webhookCancelUrl;
    protected String webhookFailureUrl;

    public GenericPaymentInitBuilder(Long amount, String currency) {
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
    public T setOrder(String order) {
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
    public T setWebhookSuccessUrl(String url) {
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
    public T setWebhookCancelUrl(String url) {
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
    public T setWebhookFailureUrl(String url) {
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
    public T setDescription(String description) {
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
    public T setLanguage(String language) {
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
    public T setSubMerchantId(String id) {
        this.subMerchantId = id;
        return (T) this;
    }

    /**
     * Sub merchant name
     * @param name Sub merchant name
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T setSubMerchantName(String name) {
        this.subMerchantName = name;
        return (T) this;
    }

}
