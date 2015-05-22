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

  public InitTransactionResponse mapInitTransactionResponse(String json) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    InitTransactionResponse response = null;
    try {
      response = mapper.readValue(json, InitTransactionResponse.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return response;
  }

  public TransactionResponse mapTransactionResponse(String json) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    TransactionResponse response = null;
    try {
      response = mapper.readValue(json, TransactionResponse.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return response;
  }

  public TransactionStatusResponse mapTransactionStatusResponse(String json) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    TransactionStatusResponse response = null;
    try {
      response = mapper.readValue(json, TransactionStatusResponse.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return response;
  }

  public CommitTransactionResponse mapCommitTransactionResponse(String json) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    CommitTransactionResponse response = null;
    try {
      response = mapper.readValue(json, CommitTransactionResponse.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return response;
  }

  public TokenizationResponse mapTokenizationResponse(String json) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    TokenizationResponse response = null;
    try {
      response = mapper.readValue(json, TokenizationResponse.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return response;
  }

  public ReportResponse mapReportResponse(String json) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    ReportResponse response = null;
    try {
      response = mapper.readValue(json, ReportResponse.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return response;
  }

}
