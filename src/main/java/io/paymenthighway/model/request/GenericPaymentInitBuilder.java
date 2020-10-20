package io.paymenthighway.model.request;

import java.net.MalformedURLException;
import java.net.URL;

public class GenericPaymentInitBuilder<T> extends GenericPaymentBuilder<T> {
    protected String language;
    protected String description;
    protected String subMerchantId;
    protected String subMerchantName;
    protected boolean isEstimatedAmount;
    protected URL webhookSuccessUrl;
    protected URL webhookCancelUrl;
    protected URL webhookFailureUrl;

    public GenericPaymentInitBuilder(Long amount, String currency) {
        super(amount, currency);
    }

    /**
     * The URL the PH server makes request to after the transaction is handled. The payment itself may still be rejected.
     *
     * @param webhookSuccessUrl Webhook url to call when request is successfully handled
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T setWebhookSuccessUrl(String webhookSuccessUrl) throws MalformedURLException {
        return setWebhookSuccessUrl(new URL(webhookSuccessUrl));
    }

    /**
     * The URL the PH server makes request to after the transaction is handled. The payment itself may still be rejected.
     *
     * @param webhookSuccessUrl Webhook url to call when request is successfully handled
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T setWebhookSuccessUrl(URL webhookSuccessUrl) {
        this.webhookSuccessUrl = webhookSuccessUrl;
        return (T) this;
    }

    /**
     * The URL the PH server makes request to after cancelling the transaction (clicking on the cancel button).
     *
     * @param webhookCancelUrl Webhook url to call when user cancels request
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T setWebhookCancelUrl(String webhookCancelUrl) throws MalformedURLException {
        return setWebhookCancelUrl(new URL(webhookCancelUrl));
    }

    /**
     * The URL the PH server makes request to after cancelling the transaction (clicking on the cancel button).
     *
     * @param webhookCancelUrl Webhook url to call when user cancels request
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T setWebhookCancelUrl(URL webhookCancelUrl) {
        this.webhookCancelUrl = webhookCancelUrl;
        return (T) this;
    }

    /**
     * The URL the PH server makes request to after a failure such as an authentication or connectivity error.
     *
     * @param webhookFailureUrl Webhook url to call when request failed
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T setWebhookFailureUrl(String webhookFailureUrl) throws MalformedURLException {
        return setWebhookFailureUrl(new URL(webhookFailureUrl));
    }

    /**
     * The URL the PH server makes request to after a failure such as an authentication or connectivity error.
     *
     * @param webhookFailureUrl Webhook url to call when request failed
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T setWebhookFailureUrl(URL webhookFailureUrl) {
        this.webhookFailureUrl = webhookFailureUrl;
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
