package io.paymenthighway.model.request.sca;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerAccount {
  @JsonProperty("account_age_indicator")
  private AccountAgeIndicator accountAgeIndicator;
  @JsonProperty("account_date")
  private String accountDate;
  @JsonProperty("change_indicator")
  private ChangeIndicator changeIndicator;
  @JsonProperty("change_date")
  private String changeDate;
  @JsonProperty("password_change_indicator")
  private PasswordChangeIndicator passwordChangeIndicator;
  @JsonProperty("password_change_date")
  private String passwordChangeDate;
  @JsonProperty("number_of_recent_purchases")
  private Integer numberOfRecentPurchases;
  @JsonProperty("number_of_add_card_attempts_day")
  private Integer numberOfAddCardAttemptsDay;
  @JsonProperty("number_of_transaction_activity_day")
  private Integer numberOfTransactionActivityDay;
  @JsonProperty("number_of_transaction_activity_year")
  private Integer numberOfTransactionActivityYear;
  @JsonProperty("shipping_address_indicator")
  private ShippingAddressIndicator shippingAddressIndicator;
  @JsonProperty("shipping_address_usage_date")
  private String shippingAddressUsageDate;
  @JsonProperty("suspicious_activity")
  private SuspiciousActivity suspiciousActivity;

  private CustomerAccount(Builder builder) {
    accountAgeIndicator = builder.accountAgeIndicator;
    accountDate = builder.accountDate;
    changeIndicator = builder.changeIndicator;
    changeDate = builder.changeDate;
    passwordChangeIndicator = builder.passwordChangeIndicator;
    passwordChangeDate = builder.passwordChangeDate;
    numberOfRecentPurchases = builder.numberOfRecentPurchases;
    numberOfAddCardAttemptsDay = builder.numberOfAddCardAttemptsDay;
    numberOfTransactionActivityDay = builder.numberOfTransactionActivityDay;
    numberOfTransactionActivityYear = builder.numberOfTransactionActivityYear;
    shippingAddressIndicator = builder.shippingAddressIndicator;
    shippingAddressUsageDate = builder.shippingAddressUsageDate;
    suspiciousActivity = builder.suspiciousActivity;
  }

  public static Builder Builder() {
    return new Builder();
  }

  public static class Builder {
    private AccountAgeIndicator accountAgeIndicator;
    private String accountDate;
    private ChangeIndicator changeIndicator;
    private String changeDate;
    private PasswordChangeIndicator passwordChangeIndicator;
    private String passwordChangeDate;
    private Integer numberOfRecentPurchases;
    private Integer numberOfAddCardAttemptsDay;
    private Integer numberOfTransactionActivityDay;
    private Integer numberOfTransactionActivityYear;
    private ShippingAddressIndicator shippingAddressIndicator;
    private String shippingAddressUsageDate;
    private SuspiciousActivity suspiciousActivity;

    public Builder() { }

    /**
     * Length of time that the cardholder has had the account with the 3DS Requestor.
     * @param accountAgeIndicator 01 = No account (guest check-out), 02 = Created during this transaction, 03 = Less than 30 days, 04 = 30−60 days, 05 = More than 60 days
     * @return Builder
     */
    public Builder setAccountAgeIndicator(AccountAgeIndicator accountAgeIndicator) {
      this.accountAgeIndicator = accountAgeIndicator;
      return this;
    }

    /**
     * Date that the cardholder opened the account at merchant
     * @param accountDate Date format: yyyy-MM-dd
     * @return Builder
     */
    public Builder setAccountDate(String accountDate) {
      this.accountDate = accountDate;
      return this;
    }

    /**
     * Length of time since the cardholder’s account information with the 3DS Requestor was last changed. Including Billing or Shipping address, new payment account, or new user(s) added.
     * @param changeIndicator 01 = Changed during this transaction, 02 = Less than 30 days, 03 = 30−60 days, 04 = More than 60 days
     * @return Builder
     */
    public Builder setChangeIndicator(ChangeIndicator changeIndicator) {
      this.changeIndicator = changeIndicator;
      return this;
    }

    /**
     * Date that the cardholder’s account with the 3DS Requestor was last changed. Including Billing or Shipping address
     * @param changeDate Date format: yyyy-MM-dd
     * @return Builder
     */
    public Builder setChangeDate(String changeDate) {
      this.changeDate = changeDate;
      return this;
    }

    /**
     * Length of time since the cardholder’s account with the 3DS Requestor had a password change or account reset.
     * @param passwordChangeIndicator 01 = No change, 02 = Changed during this transaction, 03 = Less than 30 days, 04 = 30−60 days, 05 = More than 60 days
     * @return Builder
     */
    public Builder setPasswordChangeIndicator(PasswordChangeIndicator passwordChangeIndicator) {
      this.passwordChangeIndicator = passwordChangeIndicator;
      return this;
    }

    /**
     * Date that cardholder’s account with the 3DS Requestor had a password change or account reset.
     * @param passwordChangeDate Date format: yyyy-MM-dd
     * @return Builder
     */
    public Builder setPasswordChangeDate(String passwordChangeDate) {
      this.passwordChangeDate = passwordChangeDate;
      return this;
    }

    /**
     * Number of purchases with this cardholder account during the previous six months.
     * @param numberOfRecentPurchases Max value: 9999
     * @return Builder
     */
    public Builder setNumberOfRecentPurchases(Integer numberOfRecentPurchases) {
      this.numberOfRecentPurchases = numberOfRecentPurchases;
      return this;
    }

    /**
     * Number of Add Card attempts in the last 24 hours.
     * @param numberOfAddCardAttemptsDay Max value: 999
     * @return Builder
     */
    public Builder setNumberOfAddCardAttemptsDay(Integer numberOfAddCardAttemptsDay) {
      this.numberOfAddCardAttemptsDay = numberOfAddCardAttemptsDay;
      return this;
    }

    /**
     * Number of transactions (successful and abandoned) for this cardholder account with the 3DS Requestor across all payment accounts in the previous 24 hours.
     * @param numberOfTransactionActivityDay Max value: 999
     * @return Builder
     */
    public Builder setNumberOfTransactionActivityDay(Integer numberOfTransactionActivityDay) {
      this.numberOfTransactionActivityDay = numberOfTransactionActivityDay;
      return this;
    }

    /**
     * Number of transactions (successful and abandoned) for this cardholder account with the 3DS Requestor across all payment accounts in the previous year.
     * @param numberOfTransactionActivityYear Max value: 999
     * @return Builder
     */
    public Builder setNumberOfTransactionActivityYear(Integer numberOfTransactionActivityYear) {
      this.numberOfTransactionActivityYear = numberOfTransactionActivityYear;
      return this;
    }

    /**
     * Indicates when the shipping address used for this transaction was first used with the 3DS Requestor.
     * @param shippingAddressIndicator 01 = This transaction, 02 = Less than 30 days, 03 = 30−60 days, 04 = More than 60 days
     * @return Builder
     */
    public Builder setShippingAddressIndicator(ShippingAddressIndicator shippingAddressIndicator) {
      this.shippingAddressIndicator = shippingAddressIndicator;
      return this;
    }

    /**
     * Date when the shipping address used for this transaction was first used with the 3DS Requestor
     * @param shippingAddressUsageDate Date format: yyyy-MM-dd
     * @return Builder
     */
    public Builder setShippingAddressUsageDate(String shippingAddressUsageDate) {
      this.shippingAddressUsageDate = shippingAddressUsageDate;
      return this;
    }

    /** Indicates whether the 3DS Requestor has experienced suspicious activity (including previous fraud) on the cardholder account.
     * @param suspiciousActivity  01 = No suspicious activity has been observed, 02 = Suspicious activity has been observed
     * @return Builder
     */
    public Builder setSuspiciousActivity(SuspiciousActivity suspiciousActivity) {
      this.suspiciousActivity = suspiciousActivity;
      return this;
    }

    public CustomerAccount build() {
      return new CustomerAccount(this);
    }
  }

  /**
   * Length of time that the cardholder has had the account.
   * <li>{@link #NoAccount}</li>
   * <li>{@link #CreatedDuringTransaction}</li>
   * <li>{@link #LessThan30Days}</li>
   * <li>{@link #Between30And60Days}</li>
   * <li>{@link #MoreThan60Days}</li>
   */
  public enum AccountAgeIndicator {

    /**
     * 01 = No account (guest check-out)
     */
    @JsonProperty("01")
    NoAccount,

    /**
     * 02 = Created during this transaction
     */
    @JsonProperty("02")
    CreatedDuringTransaction,

    /**
     * 03 = Less than 30 days
     */
    @JsonProperty("03")
    LessThan30Days,

    /**
     * 04 = 30−60 days
     */
    @JsonProperty("04")
    Between30And60Days,

    /**
     * 05 = More than 60 days
     */
    @JsonProperty("05")
    MoreThan60Days
  }

  /**
   * Length of time since the cardholder’s account information was last changed.
   * Including Billing or Shipping address, new payment account, or new user(s) added.
   * <li>{@link #ChangedDuringTransaction}</li>
   * <li>{@link #LessThan30Days}</li>
   * <li>{@link #Between30And60Days}</li>
   * <li>{@link #Between30And60Days}</li>
   * <li>{@link #MoreThan60Days}</li>
   */
  public enum ChangeIndicator {

    /**
     * 01 = Changed during this transaction
     */
    @JsonProperty("01")
    ChangedDuringTransaction,

    /**
     * 02 = Less than 30 days
     */
    @JsonProperty("02")
    LessThan30Days,

    /**
     * 03 = 30−60 days
     */
    @JsonProperty("03")
    Between30And60Days,

    /**
     * 04 = More than 60 days
     */
    @JsonProperty("04")
    MoreThan60Days
  }

  /**
   * Length of time since the cardholder’s account had a password change or account reset.
   * <li>{@link #NoChange}</li>
   * <li>{@link #ChangedDuringTransaction}</li>
   * <li>{@link #LessThan30Days}</li>
   * <li>{@link #Between30And60Days}</li>
   * <li>{@link #MoreThan60Days}</li>
   */
  public enum PasswordChangeIndicator {

    /**
     * 01 = No change
     */
    @JsonProperty("01")
    NoChange,

    /**
     * 02 = Changed during this transaction
     */
    @JsonProperty("02")
    ChangedDuringTransaction,

    /**
     * 03 = Less than 30 days
     */
    @JsonProperty("03")
    LessThan30Days,

    /**
     * 04 = 30−60 days
     */
    @JsonProperty("04")
    Between30And60Days,

    /**
     * 05 = More than 60 days
     */
    @JsonProperty("05")
    MoreThan60Days
  }

  /**
   * Indicates when the shipping address used for this transaction was first used.
   * <li>{@link #ThisTransaction}</li>
   * <li>{@link #LessThan30Days}</li>
   * <li>{@link #Between30And60Days}</li>
   * <li>{@link #Between30And60Days}</li>
   * <li>{@link #MoreThan60Days}</li>
   */
  public enum ShippingAddressIndicator {

    /**
     * 01 = This transaction
     */
    @JsonProperty("01")
    ThisTransaction,

    /**
     * 02 = Less than 30 days
     */
    @JsonProperty("02")
    LessThan30Days,

    /**
     * 03 = 30−60 days
     */
    @JsonProperty("03")
    Between30And60Days,

    /**
     * 04 = More than 60 days
     */
    @JsonProperty("04")
    MoreThan60Days
  }

  /**
   * Indicates whether suspicious activity has been experienced (including previous fraud) on the cardholder account.
   * <li>{@link #NoSuspiciousActivity}</li>
   * <li>{@link #SuspiciousActivityObserved}</li>
   */
  public enum SuspiciousActivity {

    /**
     * 01 = No suspicious activity has been observed
     */
    @JsonProperty("01")
    NoSuspiciousActivity,

    /**
     * 02 = Suspicious activity has been observed
     */
    @JsonProperty("02")
    SuspiciousActivityObserved
  }
}
