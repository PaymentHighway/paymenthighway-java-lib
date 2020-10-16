package io.paymenthighway.test;

import io.paymenthighway.model.JsonTestHelper;
import io.paymenthighway.model.request.ContactInformation;
import io.paymenthighway.model.request.SubMerchant;

public class TestResources {
  public static SubMerchant TestSubMerchant = new SubMerchant(
      "123456789",
      "8999",
      new ContactInformation(
          "Super Neat Service",
          "Street 123",
          "Helsinki",
          "00100",
          "FI",
          "+3581231234"
      )
  );

  public static void assertTestSubMerchant(String json) {
    JsonTestHelper.keyExists(json, "sub_merchant");
    JsonTestHelper.testJson(json, "id", TestSubMerchant.getId());
    JsonTestHelper.testJson(json, "merchant_category_code", TestSubMerchant.getMerchantCategoryCode());

    ContactInformation contactInformation = TestSubMerchant.getContactInformation();

    JsonTestHelper.keyExists(json, "contact_information");
    JsonTestHelper.testJson(json, "name", contactInformation.getName());
    JsonTestHelper.testJson(json, "street_address", contactInformation.getStreetAddress());
    JsonTestHelper.testJson(json, "city", contactInformation.getCity());
    JsonTestHelper.testJson(json, "postal_code", contactInformation.getPostalCode());
    JsonTestHelper.testJson(json, "country_code", contactInformation.getCountryCode());
    JsonTestHelper.testJson(json, "telephone", contactInformation.getTelephone());
  }
}
