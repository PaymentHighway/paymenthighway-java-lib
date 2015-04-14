/**
 * 
 */
package com.solinor.paymenthighway;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.http.NameValuePair;

import com.solinor.paymenthighway.connect.PaymentAPIConnection;
import com.solinor.paymenthighway.model.CommitTransactionRequest;
import com.solinor.paymenthighway.model.CommitTransactionResponse;
import com.solinor.paymenthighway.model.InitTransactionResponse;
import com.solinor.paymenthighway.model.RevertTransactionRequest;
import com.solinor.paymenthighway.model.TokenizationResponse;
import com.solinor.paymenthighway.model.TransactionRequest;
import com.solinor.paymenthighway.model.TransactionResponse;
import com.solinor.paymenthighway.model.TransactionStatusResponse;

/**
 * Payment Highway Payment API Service.
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class PaymentAPI {

	/*
	 * These needs to be defined: either assign directly, via constructor or use
	 * setter methods.
	 */
	String serviceUrl = null;
	String signatureKeyId = null;
	String signatureSecret = null;

	/**
	 * Constructors
	 */
	public PaymentAPI() {
	}

	public PaymentAPI(String serviceUrl, String signatureKeyId,
			String signatureSecret) {
		this.serviceUrl = serviceUrl;
		this.signatureKeyId = signatureKeyId;
		this.signatureSecret = signatureSecret;
	}

	/**
	 * Payment Highway Init Transaction
	 * 
	 * @param nameValuePairs
	 * @return InitTransactionResponse from Payment Highway
	 * @throws IOException
	 */
	public InitTransactionResponse initTransaction(
			List<NameValuePair> nameValuePairs) throws IOException {
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		return paymentApi.initTransactionHandle(nameValuePairs);
	}

	/**
	 * Payment Highway Debit Transaction
	 * 
	 * @param nameValuePairs
	 * @param transactionId
	 * @param request
	 * @return TransactionResponse
	 * @throws IOException
	 */
	public TransactionResponse debitTransaction(
			List<NameValuePair> nameValuePairs, UUID transactionId,
			TransactionRequest request) throws IOException {
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		return paymentApi.debitTransaction(nameValuePairs, transactionId,
				request);
	}

	/**
	 * Payment Highway Revert Transaction
	 * 
	 * @param nameValuePairs
	 * @param transactionId
	 * @param request
	 * @return TransactionResponse
	 * @throws IOException
	 */
	public TransactionResponse revertTransaction(
			List<NameValuePair> nameValuePairs, UUID transactionId,
			RevertTransactionRequest request) throws IOException {
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		return paymentApi.revertTransaction(nameValuePairs, transactionId,
				request);
	}

	/**
	 * Payment Highway Transaction Status Request
	 * 
	 * @param nameValuePairs
	 * @param transactionId
	 * @return TransactionStatusResponse
	 * @throws IOException
	 */
	public TransactionStatusResponse transactionStatus(
			List<NameValuePair> nameValuePairs, UUID transactionId)
			throws IOException {
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		return paymentApi.transactionStatus(nameValuePairs, transactionId);
	}

	/**
	 * Payment Highway Transaction Commit Request
	 * 
	 * @param nameValuePairs
	 * @param transactionId
	 * @return CommitTransactionResponse
	 * @throws IOException
	 */
	public CommitTransactionResponse commitTransaction(
			List<NameValuePair> nameValuePairs, UUID transactionId,
			CommitTransactionRequest request) throws IOException {
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		return paymentApi.commitTransaction(nameValuePairs, transactionId,
				request);
	}

	/**
	 * Payment Highway Tokenize Request
	 * 
	 * @param nameValuePairs
	 * @param transactionId
	 * @return TokenizationResponse
	 * @throws IOException
	 */
	public TokenizationResponse tokenize(List<NameValuePair> nameValuePairs,
			String tokenizationId) throws IOException {
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		return paymentApi.tokenization(nameValuePairs, tokenizationId);
	}

	/*
	 * Setter methods
	 */
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public void setSignatureKeyId(String signatureKeyId) {
		this.signatureKeyId = signatureKeyId;
	}

	public void setSignatureSecret(String signatureSecret) {
		this.signatureSecret = signatureSecret;
	}

}
