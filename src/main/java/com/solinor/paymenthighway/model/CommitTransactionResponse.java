package com.solinor.paymenthighway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Commit Transaction request POJO
 */
public class CommitTransactionResponse {
	
	@JsonProperty("card_token")
	UUID cardToken;
	CommitTransactionResponse.Card card;
	CommitTransactionResponse.Result result;
	
	public UUID getCardToken() {
		return cardToken;
	}
	public CommitTransactionResponse.Card getCard() {
		return this.card;
	}
	public CommitTransactionResponse.Result getResult() {
		return this.result;
	}
	
	public static class Card {
		String type;
		@JsonProperty("partial_pan")
		String partialPan;
		@JsonProperty("expire_year")
		String expireYear;
		@JsonProperty("expire_month")
		String expireMonth;
		
		public String getType() {
			return type;
		}
		public String getPartialPan() {
			return partialPan;
		}
		public String getExpireMonth() {
			return expireMonth;
		}
		public String getExpireYear() {
			return expireYear;
		}
		
	}

	public static class Result {
		String code;
		String message;
		public String getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		
	}
}
