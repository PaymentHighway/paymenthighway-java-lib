package io.paymenthighway.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.paymenthighway.model.response.*;

import java.io.IOException;

/**
 * Generates Objects from JSON
 */
public class JsonParser {

  /**
   * Constructor
   */
  public JsonParser() {
  }

  public <T> T mapResponse(String json, Class<T> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    T response = null;
    try {
      response = mapper.readValue(json, clazz);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return response;
  }
}
