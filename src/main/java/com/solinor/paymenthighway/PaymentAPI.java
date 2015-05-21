package com.solinor.paymenthighway;

import com.solinor.paymenthighway.connect.PaymentAPIConnection;
import com.solinor.paymenthighway.exception.AuthenticationException;
import com.solinor.paymenthighway.model.*;
import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.util.UUID;

/**
 * Payment Highway Payment API Service.
 */
public class PaymentAPI {

	/*
	 * These need to be defined
	 */
	private PaymentAPIConnection paymentApi = null;

	public PaymentAPI(String serviceUrl, String signatureKeyId, String signatureSecret, String account, String merchant) {

		paymentApi = new PaymentAPIConnection(serviceUrl, signatureKeyId, signatureSecret, account, merchant);
	}

	/**
	 * Payment Highway Init Transaction
	 *
	 * @return InitTransactionResponse from Payment Highway
	 * @throws HttpResponseException
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	public InitTransactionResponse initTransaction() throws IOException {

		return paymentApi.initTransactionHandle();
	}

	/**
	 * Payment Highway Debit Transaction
	 *
	 * @param transactionId
	 * @param request
	 * @return TransactionResponse
	 * @throws HttpResponseException
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	public TransactionResponse debitTransaction(UUID transactionId, TransactionRequest request) throws IOException {

		return paymentApi.debitTransaction(transactionId, request);
	}

	/**
	 * Payment Highway Revert Transaction with amount
	 *
	 * @param transactionId
	 * @return TransactionResponse
	 * @throws HttpResponseException
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	public TransactionResponse revertTransaction(UUID transactionId) throws IOException {

		RevertTransactionRequest revertRequest = new RevertTransactionRequest();

		return paymentApi.revertTransaction(transactionId, revertRequest);
	}

	/**
	 * Payment Highway Revert Transaction with amount
	 *
	 * @param transactionId
	 * @param amount
	 * @return TransactionResponse
	 * @throws HttpResponseException
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	public TransactionResponse revertTransaction(UUID transactionId, String amount) throws IOException {

		RevertTransactionRequest revertRequest = new RevertTransactionRequest(amount);

		return paymentApi.revertTransaction(transactionId, revertRequest);
	}

	/**
	 * Payment Highway Transaction Status Request
	 *
	 * @param transactionId
	 * @return TransactionStatusResponse
	 * @throws HttpResponseException
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	public TransactionStatusResponse transactionStatus(UUID transactionId) throws IOException {

		return paymentApi.transactionStatus(transactionId);
	}

	/**
	 * Payment Highway Transaction Commit Request
	 *
	 * @param transactionId
	 * @return CommitTransactionResponse
	 * @throws HttpResponseException
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	public CommitTransactionResponse commitTransaction(UUID transactionId, String amount, String currency) throws IOException {

		CommitTransactionRequest commitRequest = new CommitTransactionRequest(amount, currency);

		return paymentApi.commitTransaction(transactionId, commitRequest);
	}

	/**
	 * Payment Highway Tokenize Request
	 *
	 * @return TokenizationResponse
	 * @throws HttpResponseException
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	public TokenizationResponse tokenize(String tokenizationId) throws IOException {

		return paymentApi.tokenization(tokenizationId);
	}

	/**
	 * Payment Highway Daily Report Request
	 *
	 * @return ReportResponse
	 * @throws HttpResponseException
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	public ReportResponse fetchDailyReport(String date) throws IOException {

		return paymentApi.fetchReport(date);
	}
}
