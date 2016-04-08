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

  @Deprecated
  public InitTransactionResponse mapInitTransactionResponse(String json) {
    return mapResponse(json, InitTransactionResponse.class);
  }

  @Deprecated
  public TransactionResponse mapTransactionResponse(String json) {
    return mapResponse(json, TransactionResponse.class);
  }

  @Deprecated
  public TransactionStatusResponse mapTransactionStatusResponse(String json) {
    return mapResponse(json, TransactionStatusResponse.class);
  }

  @Deprecated
  public CommitTransactionResponse mapCommitTransactionResponse(String json) {
    return mapResponse(json, CommitTransactionResponse.class);
  }

  @Deprecated
  public TokenizationResponse mapTokenizationResponse(String json) {
    return mapResponse(json, TokenizationResponse.class);
  }

  @Deprecated
  public OrderSearchResponse mapOrderSearchResponse(String json) {
    return mapResponse(json, OrderSearchResponse.class);
  }

  @Deprecated
  public ReportResponse mapReportResponse(String json) {
    return mapResponse(json, ReportResponse.class);
  }

  @Deprecated
  public ReconciliationReportResponse mapReconciliationReportResponse(String json) {
    return mapResponse(json, ReconciliationReportResponse.class);
  }

}
