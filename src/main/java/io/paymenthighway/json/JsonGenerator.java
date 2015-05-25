package io.paymenthighway.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Generates JSON from objects
 */
public class JsonGenerator {

  /**
   * Constructor
   */
  public JsonGenerator() {
  }

  public String createTransactionJson(Object request) {

    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    String json = null;
    try {
      json = mapper.writeValueAsString(request);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }

}
