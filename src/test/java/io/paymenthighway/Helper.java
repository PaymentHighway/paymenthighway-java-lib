package io.paymenthighway;

import org.apache.http.NameValuePair;
import org.junit.Assert;

import java.util.List;

public class Helper {

  public static NameValuePair assertFieldExists(List<NameValuePair> fields, String key) throws AssertionError {
    NameValuePair found = null;
    for (NameValuePair field: fields) {
      if (field.getName().equals(key)) {
        found = field;
      }
    }

    if (found == null) {
      Assert.fail(String.format("Could not find key '%s' from field list", key));
    }

    return found;
  }

}
