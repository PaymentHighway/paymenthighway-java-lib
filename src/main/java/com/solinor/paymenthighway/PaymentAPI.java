/**
 * 
 */
package com.solinor.paymenthighway;

import java.io.IOException;
import java.util.UUID;

import org.apache.http.client.HttpResponseException;

import com.solinor.paymenthighway.connect.PaymentAPIConnection;
import com.solinor.paymenthighway.exception.AuthenticationException;
import com.solinor.paymenthighway.model.CommitTransactionRequest;
import com.solinor.paymenthighway.model.CommitTransactionResponse;
import com.solinor.paymenthighway.model.InitTransactionResponse;
import com.solinor.paymenthighway.model.ReportResponse;
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
	 * These need to be defined
	 */
	private String serviceUrl = null;
	private String signatureKeyId = null;
	private String signatureSecret = null;
	private String account = null;
	private String merchant = null;

	public PaymentAPI(String serviceUrl, String signatureKeyId,
			String signatureSecret, String account, String merchant) {
		this.serviceUrl = serviceUrl;
		this.signatureKeyId = signatureKeyId;
		this.signatureSecret = signatureSecret;
		this.account = account;
		this.merchant = merchant;
	}

	/**
	 * Payment Highway Init Transaction
	 * 
	 * @param nameValuePairs
	 * @return InitTransactionResponse from Payment Highway
	 * @throws HttpResponseException 
	 * @throws AuthenticationException 
	 * @throws IOException
	 */
	public InitTransactionResponse initTransaction()
			throws AuthenticationException, HttpResponseException, IOException {
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret,
				this.account, this.merchant);

		return paymentApi.initTransactionHandle();
	}

	/**
	 * Payment Highway Debit Transaction
	 * 
	 * @param nameValuePairs
	 * @param transactionId
	 * @param request
	 * @return TransactionResponse
	 * @throws HttpResponseException 
	 * @throws AuthenticationException 
	 * @throws IOException
	 */
	public TransactionResponse debitTransaction(UUID transactionId,
			TransactionRequest request) throws AuthenticationException,
			HttpResponseException, IOException {
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret,
				this.account, this.merchant);
		return paymentApi.debitTransaction(transactionId, request);
	}

	/**
	 * Payment Highway Revert Transaction
	 * 
	 * @param nameValuePairs
	 * @param transactionId
	 * @param request
	 * @return TransactionResponse
	 * @throws HttpResponseException 
	 * @throws AuthenticationException 
	 * @throws IOException
	 */
	public TransactionResponse revertTransaction(UUID transactionId,
			String amount) throws AuthenticationException,
			HttpResponseException, IOException {
		RevertTransactionRequest revertRequest = new RevertTransactionRequest(
				amount);
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret,
				this.account, this.merchant);
		return paymentApi.revertTransaction(transactionId, revertRequest);
	}

	/**
	 * Payment Highway Transaction Status Request
	 * 
	 * @param nameValuePairs
	 * @param transactionId
	 * @return TransactionStatusResponse
	 * @throws HttpResponseException 
	 * @throws AuthenticationException 
	 * @throws IOException
	 */
	public TransactionStatusResponse transactionStatus(UUID transactionId)
			throws AuthenticationException, HttpResponseException, IOException {
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret,
				this.account, this.merchant);
		return paymentApi.transactionStatus(transactionId);
	}

	/**
	 * Payment Highway Transaction Commit Request
	 * 
	 * @param nameValuePairs
	 * @param transactionId
	 * @return CommitTransactionResponse
	 * @throws HttpResponseException 
	 * @throws AuthenticationException 
	 * @throws IOException
	 */
	public CommitTransactionResponse commitTransaction(UUID transactionId,
			String amount, String currency) throws AuthenticationException,
			HttpResponseException, IOException {
		CommitTransactionRequest commitRequest = new CommitTransactionRequest(
				amount, currency);
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret,
				this.account, this.merchant);
		return paymentApi.commitTransaction(transactionId, commitRequest);
	}

	/**
	 * Payment Highway Tokenize Request
	 * 
	 * @param nameValuePairs
	 * @param transactionId
	 * @return TokenizationResponse
	 * @throws HttpResponseException 
	 * @throws AuthenticationException 
	 * @throws IOException
	 */
	public TokenizationResponse tokenize(String tokenizationId)
			throws AuthenticationException, HttpResponseException, IOException {
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret,
				this.account, this.merchant);
		return paymentApi.tokenization(tokenizationId);
	}
	
	/**
	 * Payment Highway Daily Report Request
	 *
	 * @param String format: yyyyMMdd
	 * @return ReportResponse
	 * @throws HttpResponseException 
	 * @throws AuthenticationException 
	 * @throws IOException
	 */
	public ReportResponse fetchDailyReport(String date)
			throws AuthenticationException, HttpResponseException, IOException {
		PaymentAPIConnection paymentApi = new PaymentAPIConnection(
				this.serviceUrl, this.signatureKeyId, this.signatureSecret,
				this.account, this.merchant);
		return paymentApi.fetchReport(date);
	}
}
