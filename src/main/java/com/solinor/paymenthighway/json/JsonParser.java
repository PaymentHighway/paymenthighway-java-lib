/**
 * 
 */
package com.solinor.paymenthighway.json;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solinor.paymenthighway.model.CommitTransactionResponse;
import com.solinor.paymenthighway.model.InitTransactionResponse;
import com.solinor.paymenthighway.model.ReportResponse;
import com.solinor.paymenthighway.model.TokenizationResponse;
import com.solinor.paymenthighway.model.TransactionResponse;
import com.solinor.paymenthighway.model.TransactionStatusResponse;

/**
  * Generates Objects from JSON
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
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
