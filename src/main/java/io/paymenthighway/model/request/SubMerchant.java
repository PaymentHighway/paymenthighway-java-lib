package io.paymenthighway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubMerchant {

  String id;
  @JsonProperty("merchant_category_code")
  String merchantCategoryCode;
  @JsonProperty("contact_information")
  ContactInformation contactInformation;
  @JsonProperty("vat_id")
  String vatId;

  /**
   * Sub-merchant details, only to be used if initiated by a Payment Facilitator
   * @param id Payment Facilitator assigned numeric sub-merchant identifier
   * @param merchantCategoryCode Four digit merchant category code
   * @param contactInformation Sub-merchant's contact details
   */
  public SubMerchant(String id, String merchantCategoryCode, ContactInformation contactInformation) {
    this.id = id;
    this.merchantCategoryCode = merchantCategoryCode;
    this.contactInformation = contactInformation;
  }

  public SubMerchant(String id, String merchantCategoryCode, ContactInformation contactInformation, String vatId) {
    this.id = id;
    this.merchantCategoryCode = merchantCategoryCode;
    this.contactInformation = contactInformation;
    this.vatId = vatId;
  }

  public String getId() {
    return id;
  }

  public String getMerchantCategoryCode() {
    return merchantCategoryCode;
  }

  public ContactInformation getContactInformation() {
    return contactInformation;
  }

  public String getVatId() {
    return vatId;
  }
}
