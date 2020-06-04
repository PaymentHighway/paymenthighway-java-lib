package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.paymenthighway.model.Splitting;

public class GenericPaymentInitRequest extends Request {
    @JsonProperty("amount") protected Long amount;
    @JsonProperty("currency") protected String currency;
    @JsonProperty("order") protected String order;
    @JsonProperty("language") protected String language;
    @JsonProperty("description") protected String description;
    @JsonProperty("sub_merchant_id") protected String subMerchantId;
    @JsonProperty("sub_merchant_name") protected String subMerchantName;
    @JsonProperty("is_estimated_amount") protected boolean isEstimatedAmount;
    @JsonProperty("webhook_success_url") protected String webhookSuccessUrl;
    @JsonProperty("webhook_cancel_url") protected String webhookCancelUrl;
    @JsonProperty("webhook_failure_url") protected String webhookFailureUrl;
    @JsonProperty protected Splitting splitting;

    /**
     *
     * @return amount
     */
    public Long getAmount() {
        return amount;
    }

    /**
     *
     * @return currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @return order
     */
    public String getOrder() {
        return order;
    }

    /**
     *
     * @return language code
     */
    public String getLanguage() {
        return language;
    }

    /**
     *
     * @return webhook success url
     */
    public String getWebhookSuccessUrl() {
        return webhookSuccessUrl;
    }

    /**
     *
     * @return webhook cancel url
     */
    public String getWebhookCancelUrl() {
        return webhookCancelUrl;
    }

    /**
     *
     * @return webhook failure url
     */
    public String getWebhookFailureUrl() {
        return webhookFailureUrl;
    }

    /**
     *
     * @return sub merchant id
     */
    public String getSubMerchantId() {
        return subMerchantId;
    }

    /**
     *
     * @return sub merchant name
     */
    public String getSubMerchantName() {
        return subMerchantName;
    }

    /**
     *
     * @return is estimated amount
     */
    public boolean getIsEstimatedAmount() {
        return isEstimatedAmount;
    }

    /**
     *
     * @return splitting
     */
    public Splitting getSplitting() {
        return splitting;
    }
}
