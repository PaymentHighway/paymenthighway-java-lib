package io.paymenthighway;

import io.paymenthighway.connect.PaymentAPIConnection;
import io.paymenthighway.exception.AuthenticationException;
import io.paymenthighway.model.request.*;
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
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public InitTransactionResponse initTransaction() throws IOException {

    return paymentApi.initTransactionHandle();
  }

  /**
   * Payment Highway Debit Transaction
   *
   * @param transactionId Transaction id
   * @param request Transaction request
   * @return Transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public TransactionResponse debitTransaction(UUID transactionId, TransactionRequest request) throws IOException {

    return paymentApi.debitTransaction(transactionId, request);
  }

  /**
   * Payment Highway Masterpass Debit Transaction
   *
   * @param transactionId Transaction id
   * @param request Masterpass Debit Transaction request
   * @return Transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public TransactionResponse debitMasterpassTransaction(UUID transactionId, MasterpassTransactionRequest request)
    throws IOException {

    return paymentApi.debitMasterpassTransaction(transactionId, request);
  }

  /**
   * Payment Highway Apple Pay Transaction
   *
   * @param transactionId Transaction id
   * @param request Apple Pay Transaction request
   * @return Transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public TransactionResponse debitApplePayTransaction(UUID transactionId, ApplePayTransactionRequest request) throws IOException {
    return paymentApi.debitApplePayTransaction(transactionId, request);
  }

  /**
   * Payment Highway Revert Transaction with amount
   *
   * @param transactionId Transaction id
   * @return Transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public TransactionResponse revertTransaction(UUID transactionId) throws IOException {

    RevertTransactionRequest revertRequest = new RevertTransactionRequest();

    return paymentApi.revertTransaction(transactionId, revertRequest);
  }

  /**
   * Payment Highway Revert Transaction with amount
   *
   * @param transactionId Transaction id
   * @param amount Amount to revert
   * @return Transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public TransactionResponse revertTransaction(UUID transactionId, String amount) throws IOException {

    RevertTransactionRequest revertRequest = new RevertTransactionRequest(amount);

    return paymentApi.revertTransaction(transactionId, revertRequest);
  }

  /**
   * Payment Highway Transaction Status Request
   *
   * @param transactionId Transaction id
   * @return Transaction status response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public TransactionStatusResponse transactionStatus(UUID transactionId) throws IOException {

    return paymentApi.transactionStatus(transactionId);
  }

  /**
   * Payment Highway Order Status Request
   *
   * @param order The ID of the order whose transactions should be searched for
   * @return Order search response
   * @throws IOException Exception
   */
  public OrderSearchResponse searchOrders(String order) throws IOException {

    return paymentApi.searchOrders(order);
  }

  /**
   * Payment Highway Transaction Commit Request
   * Used to commit (capture) the transaction.
   * In order to find out the result of the transaction without committing it, use Transaction Result request instead.
   *
   * @param transactionId Transaction id
   * @param amount The amount to commit, must be less or equal than the initial transaction amount
   * @param currency The original transaction currency
   * @return Commit transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public CommitTransactionResponse commitTransaction(UUID transactionId, String amount, String currency) throws IOException {

    CommitTransactionRequest commitRequest = new CommitTransactionRequest(amount, currency);

    return paymentApi.commitTransaction(transactionId, commitRequest);
  }

  /**
   * Payment Highway User profile information
   *
   * For now used to find user information from masterpass, example shipping address
   *
   * @param transactionId Transaction id
   * @return User profile response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public UserProfileResponse userProfile(UUID transactionId) throws IOException {

    return paymentApi.userProfile(transactionId);
  }

  /**
   * Payment Highway Transaction Result Request
   * Used to find out whether or not an uncommitted transaction succeeded, without actually committing (capturing) it.
   *
   * @param transactionId Transaction id
   * @return Transaction result response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public TransactionResultResponse transactionResult(UUID transactionId) throws IOException {

    return paymentApi.transactionResult(transactionId);
  }

  /**
   * Payment Highway Tokenize Request
   *
   * @param tokenizationId Tokenization id
   * @return Tokenization response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public TokenizationResponse tokenize(UUID tokenizationId) throws IOException {

    return paymentApi.tokenization(tokenizationId);
  }

  /**
   * Payment Highway Daily Report Request
   *
   * @param date Date
   * @return Report response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public ReportResponse fetchDailyReport(String date) throws IOException {

    return paymentApi.fetchReport(date);
  }

  /**
   * Payment Highway Reconciliation Report Request
   *
   * @param date The date to fetch the reconciliation report for.
   * @return Reconciliation report response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public ReconciliationReportResponse fetchReconciliationReport(String date) throws IOException {

    return paymentApi.fetchReconciliationReport(date);
  }

  /**
   * Payment Highway Reconciliation Report Request
   *
   * Deprecated: use of the default behaviour (useDateProcessed=false) is encouraged to be used instead
   *
   * @param date The date to fetch the reconciliation report for.
   * @param useDateProcessed True for using the Euroline processing date (legacy style), instead of the report's fetching date. May result in changes in the past.
   * @return Reconciliation report response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
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
