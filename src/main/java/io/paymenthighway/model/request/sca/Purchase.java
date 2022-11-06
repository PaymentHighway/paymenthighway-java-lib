package io.paymenthighway.model.request.sca;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Purchase {

  @JsonProperty("shipping_indicator")
  private ShippingIndicator shippingIndicator;

  @JsonProperty("delivery_time_frame")
  private DeliveryTimeFrame deliveryTimeFrame;

  @JsonProperty("delivery_email")
  private String deliveryEmail;

  @JsonProperty("reorder_items_indicator")
  private ReorderItemsIndicator reorderItemsIndicator;

  @JsonProperty("pre_order_purchase_indicator")
  private PreOrderItemsIndicator preOrderPurchaseIndicator;

  @JsonProperty("pre_order_date")
  private String preOrderDate;

  @JsonProperty("shipping_name_indicator")
  private ShippingNameIndicator shippingNameIndicator;

  @JsonProperty("gift_card_amount")
  private Long giftCardAmount;

  @JsonProperty("gift_card_count")
  private Integer giftCardCount;

  private Purchase(Builder builder) {
    shippingIndicator = builder.shippingIndicator;
    deliveryTimeFrame = builder.deliveryTimeFrame;
    deliveryEmail = builder.deliveryEmail;
    reorderItemsIndicator = builder.reorderItemsIndicator;
    preOrderPurchaseIndicator = builder.preOrderPurchaseIndicator;
    preOrderDate = builder.preOrderDate;
    shippingNameIndicator = builder.shippingNameIndicator;
    giftCardAmount = builder.giftCardAmount;
    giftCardCount = builder.giftCardCount;
  }

  public static Builder Builder() {
    return new Builder();
  }

  public ShippingIndicator getShippingIndicator() {
    return shippingIndicator;
  }

  public DeliveryTimeFrame getDeliveryTimeFrame() {
    return deliveryTimeFrame;
  }

  public String getDeliveryEmail() {
    return deliveryEmail;
  }

  public ReorderItemsIndicator getReorderItemsIndicator() {
    return reorderItemsIndicator;
  }

  public PreOrderItemsIndicator getPreOrderPurchaseIndicator() {
    return preOrderPurchaseIndicator;
  }

  public String getPreOrderDate() {
    return preOrderDate;
  }

  public ShippingNameIndicator getShippingNameIndicator() {
    return shippingNameIndicator;
  }

  public Long getGiftCardAmount() {
    return giftCardAmount;
  }

  public Integer getGiftCardCount() {
    return giftCardCount;
  }

  public static final class Builder {
    private ShippingIndicator shippingIndicator;
    private DeliveryTimeFrame deliveryTimeFrame;
    private String deliveryEmail;
    private ReorderItemsIndicator reorderItemsIndicator;
    private PreOrderItemsIndicator preOrderPurchaseIndicator;
    private String preOrderDate;
    private ShippingNameIndicator shippingNameIndicator;
    private Long giftCardAmount;
    private Integer giftCardCount;

    public Builder() { }

    /**
     * Merchants must choose the Shipping Indicator code that most accurately describes the cardholder’s specific transaction, not their general business.
     * If one or more items are included in the sale, use the Shipping Indicator code for the physical goods, or if all digital goods, use the Shipping Indicator code that describes the most expensive item.
     * @param shippingIndicator Indicates shipping method chosen for the transaction.
     * @return Builder
     */
    public Builder setShippingIndicator(ShippingIndicator shippingIndicator) {
      this.shippingIndicator = shippingIndicator;
      return this;
    }

    /**
     * @param deliveryTimeFrame Indicates the merchandise delivery timeframe.
     * @return Builder
     */
    public Builder setDeliveryTimeFrame(DeliveryTimeFrame deliveryTimeFrame) {
      this.deliveryTimeFrame = deliveryTimeFrame;
      return this;
    }

    /**
     * @param deliveryEmail For Electronic delivery, the email address to which the merchandise was delivered. Max length: 254
     * @return Builder
     */
    public Builder setDeliveryEmail(String deliveryEmail) {
      this.deliveryEmail = deliveryEmail;
      return this;
    }

    /**
     * @param reorderItemsIndicator Indicates whether the cardholder is reordering previously purchased merchandise.
     * @return Builder
     */
    public Builder setReorderItemsIndicator(ReorderItemsIndicator reorderItemsIndicator) {
      this.reorderItemsIndicator = reorderItemsIndicator;
      return this;
    }

    /**
     * @param preOrderPurchaseIndicator Indicates whether Cardholder is placing an order for merchandise with a future availability or release date.
     * @return Builder
     */
    public Builder setPreOrderPurchaseIndicator(PreOrderItemsIndicator preOrderPurchaseIndicator) {
      this.preOrderPurchaseIndicator = preOrderPurchaseIndicator;
      return this;
    }

    /**
     * For a pre-ordered purchase, the expected date that the merchandise will be available
     * @param preOrderDate Date format: yyyy-MM-dd
     * @return Builder
     */
    public Builder setPreOrderDate(String preOrderDate) {
      this.preOrderDate = preOrderDate;
      return this;
    }

    /**
     * @param shippingNameIndicator Indicates if the Cardholder Name on the account is identical to the shipping Name used for this transaction.
     * @return Builder
     */
    public Builder setShippingNameIndicator(ShippingNameIndicator shippingNameIndicator) {
      this.shippingNameIndicator = shippingNameIndicator;
      return this;
    }

    /**
     * For prepaid or gift card purchase, the purchase amount total of prepaid or gift card(s)
     * @param giftCardAmount Amount in the minor currency units. Max value: 999999999999999
     * @return Builder
     */
    public Builder setGiftCardAmount(Long giftCardAmount) {
      this.giftCardAmount = giftCardAmount;
      return this;
    }

    /**
     * For prepaid or gift card purchase, total count of individual prepaid or gift cards/codes purchased.
     * @param giftCardCount Max value: 99
     * @return Builder
     */
    public Builder setGiftCardCount(Integer giftCardCount) {
      this.giftCardCount = giftCardCount;
      return this;
    }



    public Purchase build() {
      return new Purchase(this);
    }
  }

  /**
   * Indicates shipping method chosen for the transaction.
   * Merchants must choose the Shipping Indicator code that most accurately describes the cardholder’s specific transaction, not their general business.
   * If one or more items are included in the sale, use the Shipping Indicator code for the physical goods, or if all digital goods, use the Shipping Indicator code that describes the most expensive item.
   * {@link #ShipToCardholdersAddress}
   * {@link #ShipToVerifiedAddress}
   * {@link #ShipToDifferentAddress}
   * {@link #ShipToStore}
   * {@link #DigitalGoods}
   * {@link #TravelAndEventTickets}
   * {@link #Other}
   */
  public enum ShippingIndicator
  {
    /**
     * 01 = Ship to cardholder’s billing address
     */
    @JsonProperty("01")
    ShipToCardholdersAddress,

    /**
     * 02 = Ship to another verified address on file with merchant
     */
    @JsonProperty("02")
    ShipToVerifiedAddress,

    /**
     * 03 = Ship to address that is different than the cardholder’s billing address
     */
    @JsonProperty("03")
    ShipToDifferentAddress,

    /**
     * 04 = “Ship to Store” / Pick-up at local store (Store address shall be populated in shipping address fields)
     */
    @JsonProperty("04")
    ShipToStore,

    /**
     * 05 = Digital goods (includes online services, electronic gift cards and redemption codes)
     */
    @JsonProperty("05")
    DigitalGoods,

    /**
     * 06 = Travel and Event tickets, not shipped
     */
    @JsonProperty("06")
    TravelAndEventTickets,

    /**
     * 07 = Other (for example, Gaming, digital services not shipped, emedia subscriptions, etc.)
     */
    @JsonProperty("07")
    Other
  }

  /**
   * Indicates the merchandise delivery timeframe.
   * {@link #ElectronicDelivery}
   * {@link #SameDayShipping}
   * {@link #OvernightShipping}
   * {@link #TwoDarOrMoreShipping}
   */
  public enum DeliveryTimeFrame {

    /**
     * 01 = Electronic Delivery
     */
    @JsonProperty("01")
    ElectronicDelivery,

    /**
     * 02 = Same day shipping
     */
    @JsonProperty("02")
    SameDayShipping,

    /**
     * 03 = Overnight shipping
     */
    @JsonProperty("03")
    OvernightShipping,

    /**
     * 04 = Two-day or more shipping
     */
    @JsonProperty("04")
    TwoDarOrMoreShipping
  }

  /**
   * Indicates whether the cardholder is reordering previously purchased merchandise.
   * {@link #FirstTimeOrdered}
   * {@link #Reorder}
   */
  public enum ReorderItemsIndicator {

    /**
     * 01 = First time ordered
     */
    @JsonProperty("01")
    FirstTimeOrdered,

    /**
     * 02 = Reorder
     */
    @JsonProperty("02")
    Reorder
  }

  /**
   * Indicates whether Cardholder is placing an order for merchandise with a future availability or release date.
   * {@link #MerchandiseAvailable}
   * {@link #FutureAvailability}
   */
  public enum PreOrderItemsIndicator {

    /**
     * 01 = Merchandise available
     */
    @JsonProperty("01")
    MerchandiseAvailable,

    /**
     * 02 = Future availability
     */
    @JsonProperty("02")
    FutureAvailability
  }

  /**
   * Indicates if the Cardholder Name on the account is identical to the shipping Name used for this transaction.
   * {@link #AccountNameMatchesShippingName}
   * {@link #AccountNameDifferentThanShippingName}
   */
  public enum ShippingNameIndicator {

    /**
     * 01 = Account Name identical to shipping Name
     */
    @JsonProperty("01")
    AccountNameMatchesShippingName,

    /**
     * 02 = Account Name different than shipping Name
     */
    @JsonProperty("02")
    AccountNameDifferentThanShippingName
  }
}
