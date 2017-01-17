package io.paymenthighway;

import io.paymenthighway.connect.PaymentAPIConnection;
import io.paymenthighway.exception.AuthenticationException;
import io.paymenthighway.model.request.CommitTransactionRequest;
import io.paymenthighway.model.request.RevertTransactionRequest;
import io.paymenthighway.model.request.TransactionRequest;
import io.paymenthighway.model.response.*;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;

/**
 * Payment Highway Payment API Service.
 */
public class PaymentAPI implements Closeable {

  /*
   * These need to be defined
   */
  private PaymentAPIConnection paymentApi = null;

  public PaymentAPI(String serviceUrl, String signatureKeyId, String signatureSecret, String account, String merchant) {

    paymentApi = new PaymentAPIConnection(serviceUrl, signatureKeyId, signatureSecret, account, merchant);
  }

  public void setHttpClient(CloseableHttpClient httpClient) {
    this.paymentApi.setHttpClient(httpClient);
  }

  /**
   * Payment Highway Init Transaction
   *
   * @return InitTransactionResponse from Payment Highway
   * @throws HttpResponseException Something went south with the Http Request / Response
   * @throws AuthenticationException Authentication error, e.g. invalid signature
   * @throws IOException Other IO Exceptions
   */
  public InitTransactionResponse initTransaction() throws IOException {

    return paymentApi.initTransactionHandle();
  }

  /**
   * Payment Highway Debit Transaction
   *
   * @param transactionId Payment Highway transaction ID
   * @param request Transaction Request
   * @return TransactionResponse
   * @throws HttpResponseException Something went south with the Http Request / Response
   * @throws AuthenticationException Authentication error, e.g. invalid signature
   * @throws IOException Other IO Exceptions
   */
  public TransactionResponse debitTransaction(UUID transactionId, TransactionRequest request) throws IOException {

    return paymentApi.debitTransaction(transactionId, request);
  }

  /**
   * Payment Highway Revert Transaction with amount
   *
   * @param transactionId Payment Highway transaction ID
   * @return TransactionResponse
   * @throws HttpResponseException Something went south with the Http Request / Response
   * @throws AuthenticationException Authentication error, e.g. invalid signature
   * @throws IOException Other IO Exceptions
   */
  public TransactionResponse revertTransaction(UUID transactionId) throws IOException {

    RevertTransactionRequest revertRequest = new RevertTransactionRequest();

    return paymentApi.revertTransaction(transactionId, revertRequest);
  }

  /**
   * Payment Highway Revert Transaction with amount
   *
   * @param transactionId Payment Highway transaction ID
   * @param amount The amount to revert, may be full or partial
   * @return TransactionResponse
   * @throws HttpResponseException Something went south with the Http Request / Response
   * @throws AuthenticationException Authentication error, e.g. invalid signature
   * @throws IOException Other IO Exceptions
   */
  public TransactionResponse revertTransaction(UUID transactionId, String amount) throws IOException {

    RevertTransactionRequest revertRequest = new RevertTransactionRequest(amount);

    return paymentApi.revertTransaction(transactionId, revertRequest);
  }

  /**
   * Payment Highway Transaction Status Request
   *
   * @param transactionId Payment Highway transaction ID
   * @return TransactionStatusResponse
   * @throws HttpResponseException Something went south with the Http Request / Response
   * @throws AuthenticationException Authentication error, e.g. invalid signature
   * @throws IOException Other IO Exceptions
   */
  public TransactionStatusResponse transactionStatus(UUID transactionId) throws IOException {

    return paymentApi.transactionStatus(transactionId);
  }

  /**
   * Payment Highway Order Status Request
   *
   * @param order The ID of the order whose transactions should be searched for
   * @return OrderSearchResponse
   * @throws IOException Other IO Exceptions
   */
  public OrderSearchResponse searchOrders(String order) throws IOException {

    return paymentApi.searchOrders(order);
  }

  /**
   * Payment Highway Transaction Commit Request
   * Used to commit (capture) the transaction.
   * In order to find out the result of the transaction without committing it, use Transaction Result request instead.
   *
   * @param transactionId Payment Highway transaction ID
   * @param amount The amount to commit, must be less or equal than the initial transaction amount
   * @param currency The original transaction currency
   * @return CommitTransactionResponse
   * @throws HttpResponseException Something went south with the Http Request / Response
   * @throws AuthenticationException Authentication error, e.g. invalid signature
   * @throws IOException Other IO Exceptions
   */
  public CommitTransactionResponse commitTransaction(UUID transactionId, String amount, String currency) throws IOException {

    CommitTransactionRequest commitRequest = new CommitTransactionRequest(amount, currency);

    return paymentApi.commitTransaction(transactionId, commitRequest);
  }

  /**
   * Payment Highway Transaction Result Request
   * Used to find out whether or not an uncommitted transaction succeeded, without actually committing (capturing) it.
   *
   * @param transactionId Payment Highway transaction ID
   * @return TransactionResultResponse
   * @throws HttpResponseException Something went south with the Http Request / Response
   * @throws AuthenticationException Authentication error, e.g. invalid signature
   * @throws IOException Other IO Exceptions
   */
  public TransactionResultResponse transactionResult(UUID transactionId) throws IOException {

    return paymentApi.transactionResult(transactionId);
  }

  /**
   * Payment Highway Tokenize Request
   *
   * @param tokenizationId Payment Highway tokenization ID
   * @return TokenizationResponse
   * @throws HttpResponseException Something went south with the Http Request / Response
   * @throws AuthenticationException Authentication error, e.g. invalid signature
   * @throws IOException Other IO Exceptions
   */
  public TokenizationResponse tokenize(UUID tokenizationId) throws IOException {

    return paymentApi.tokenization(tokenizationId);
  }

  /**
   * Payment Highway Daily Report Request
   *
   * @param date the date to fetch batch report for, format: yyyyMMdd
   * @return ReportResponse
   * @throws HttpResponseException Something went south with the Http Request / Response
   * @throws AuthenticationException Authentication error, e.g. invalid signature
   * @throws IOException Other IO Exceptions
   */
  public ReportResponse fetchDailyReport(String date) throws IOException {

    return paymentApi.fetchReport(date);
  }

  /**
   * Payment Highway Reconciliation Report Request
   *
   * @param date The date to fetch the reconciliation report for, format: yyyyMMdd
   * @return ReconciliationReportResponse
   * @throws HttpResponseException Something went south with the Http Request / Response
   * @throws AuthenticationException Authentication error, e.g. invalid signature
   * @throws IOException Other IO Exceptions
   */
  public ReconciliationReportResponse fetchReconciliationReport(String date) throws IOException {

    return paymentApi.fetchReconciliationReport(date);
  }

  /**
   * Payment Highway Reconciliation Report Request
   *
   * Deprecated: use of the default behaviour (useDateProcessed=false) is encouraged to be used instead
   *
   * @param date The date to fetch the reconciliation report for, format: format: yyyyMMdd
   * @param useDateProcessed True for using the Euroline processing date (legacy style), instead of the report's fetching date. May result in changes in the past.
   * @return ReconciliationReportResponse
   * @throws HttpResponseException Something went south with the Http Request / Response
   * @throws AuthenticationException Authentication error, e.g. invalid signature
   * @throws IOException Other IO Exceptions
   */
  @Deprecated
  public ReconciliationReportResponse fetchReconciliationReport(String date, Boolean useDateProcessed) throws IOException {
    return paymentApi.fetchReconciliationReport(date, useDateProcessed);
  }

  @Override
  public void close() throws IOException {
    if (paymentApi != null) {
      paymentApi.close();
    }
  }
}
