package io.paymenthighway;

import io.paymenthighway.connect.PaymentAPIConnection;
import io.paymenthighway.exception.AuthenticationException;
import io.paymenthighway.model.request.*;
import io.paymenthighway.model.response.*;
import io.paymenthighway.model.response.transaction.ChargeCitResponse;
import io.paymenthighway.model.response.transaction.ChargeMitResponse;
import io.paymenthighway.model.response.transaction.DebitTransactionResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.Closeable;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Payment Highway Payment API Service.
 */
public class PaymentAPI implements Closeable {

  private PaymentAPIConnection paymentApi = null;

  /**
   * Payment API with default HTTP client
   * @param serviceUrl Production or Sandbox base URL
   * @param signatureKeyId The signature key's ID or name
   * @param signatureSecret The secret signature key
   * @param account Payment Highway account name
   * @param merchant Payment Highway merchant name. One account might have multiple merchants.
   */
  public PaymentAPI(String serviceUrl, String signatureKeyId, String signatureSecret, String account, String merchant) {

    CloseableHttpClient httpClient = null;

    try {
      httpClient = PaymentAPIConnection.defaultHttpClient();
    } catch(NoSuchAlgorithmException | KeyManagementException exception) {
      // If TLSv1.2 is not supported. Hides exceptions for backwards compatibility.
      exception.printStackTrace();
    }

    paymentApi = new PaymentAPIConnection(serviceUrl, signatureKeyId, signatureSecret, account, merchant, httpClient);
  }

  /**
   * Payment API with customizable HTTP client
   * Pay attention to closing if sharing the http client between multiple instances!
   * @param serviceUrl Production or Sandbox base URL
   * @param signatureKeyId The signature key's ID or name
   * @param signatureSecret The secret signature key
   * @param account Payment Highway account name
   * @param merchant Payment Highway merchant name. One account might have multiple merchants.
   * @param httpClient The underlying HTTP client.
   */
  public PaymentAPI(
      String serviceUrl,
      String signatureKeyId,
      String signatureSecret,
      String account,
      String merchant,
      CloseableHttpClient httpClient
  ) {
    paymentApi = new PaymentAPIConnection(serviceUrl, signatureKeyId, signatureSecret, account, merchant, httpClient);
  }

  /**
   * @param httpClient The underlying HTTP client
   * @deprecated Use the constructor to inject httpClient instead.
   */
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
  @Deprecated
  public DebitTransactionResponse debitTransaction(UUID transactionId, TransactionRequest request) throws IOException {

    return paymentApi.debitTransaction(transactionId, request);
  }

  /**
   * Merchant Customer Initiated Transaction (MIT)
   * Used when a customer is not participating in the payment flow.
   * A contract and understanding between the merchant and the customer must be established, allowing this kind of payments.
   *
   * @param transactionId Transaction id
   * @param request Charge Mit Transaction Request
   * @return ChargeMitResponse response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public ChargeMitResponse chargeMerchantInitiatedTransaction(UUID transactionId, ChargeMitRequest request) throws IOException {

    return paymentApi.chargeMerchantInitiatedTransaction(transactionId, request);
  }

  /**
   * Charge Customer Initiated Transaction (CIT)
   * Used when a customer is participating in the payment flow.
   *
   * @param transactionId Transaction id
   * @param request Charge Cit Transaction Request
   * @return ChargeCitResponse response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public ChargeCitResponse chargeCustomerInitiatedTransaction(UUID transactionId, ChargeCitRequest request) throws IOException {

    return paymentApi.chargeCustomerInitiatedTransaction(transactionId, request);
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
  public DebitTransactionResponse debitApplePayTransaction(UUID transactionId, ApplePayTransactionRequest request) throws IOException {
    return paymentApi.debitApplePayTransaction(transactionId, request);
  }

  /**
   * MobilePay app switch request.
   *
   * @param request MobilePay init request
   * @return MobilePayInit response
   * @throws IOException Exception
   */
  public MobilePayInitResponse initMobilePaySession(MobilePayInitRequest request) throws IOException {
    return paymentApi.initMobilePaySession(request);
  }

  /**
   * MobilePay session status
   *
   * @param sessionToken Session token
   * @return MobilePayStatus response
   * @throws IOException Exception
   */
  public MobilePayStatusResponse mobilePaySessionStatus(String sessionToken) throws IOException {
    return paymentApi.mobilePaySessionStatus(sessionToken);
  }

  /**
   * Pivo app switch request
   *
   * @param request Pivo init request
   * @return Pivo init response
   * @throws IOException Exception
   */
  public PivoInitResponse initPivoTransaction(PivoInitRequest request) throws  IOException {
    return paymentApi.initPivoTransaction(request);
  }

  /**
   * Payment Highway Revert Transaction
   *
   * @param transactionId Transaction id
   * @return Transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public RevertResponse revertTransaction(UUID transactionId) throws IOException {

    RevertTransactionRequest revertRequest = new RevertTransactionRequest();

    return paymentApi.revertTransaction(transactionId, revertRequest);
  }

  /**
   * Payment Highway Revert Pivo Transaction without prefilled reference number
   *
   * @param transactionId Transaction id
   * @return Transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public RevertResponse revertPivoTransaction(UUID transactionId) throws IOException {

    RevertPivoTransactionRequest revertRequest = new RevertPivoTransactionRequest();

    return paymentApi.revertPivoTransaction(transactionId, revertRequest);
  }

  /**
   * Payment Highway Revert Pivo Transaction
   *
   * @param transactionId Transaction id
   * @param referenceNumber Reference number
   * @return Transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public RevertResponse revertPivoTransaction(UUID transactionId, String referenceNumber) throws IOException {

    RevertPivoTransactionRequest revertRequest = new RevertPivoTransactionRequest(referenceNumber);

    return paymentApi.revertPivoTransaction(transactionId, revertRequest);
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
  public RevertResponse revertTransaction(UUID transactionId, String amount) throws IOException {

    RevertTransactionRequest revertRequest = new RevertTransactionRequest(amount);

    return paymentApi.revertTransaction(transactionId, revertRequest);
  }

  /**
   * Payment Highway Revert Pivo Transaction with amount, but without prefilled reference number
   *
   * @param transactionId Transaction id
   * @param amount Amount to revert
   * @return Transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public RevertResponse revertPivoTransaction(UUID transactionId, Long amount) throws IOException {

    RevertPivoTransactionRequest revertRequest = new RevertPivoTransactionRequest(amount);

    return paymentApi.revertPivoTransaction(transactionId, revertRequest);
  }

  /**
   * Payment Highway Revert Pivo Transaction with amount
   *
   * @param transactionId Transaction id
   * @param referenceNumber Reference number
   * @param amount Amount to revert
   * @return Transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public RevertResponse revertPivoTransaction(UUID transactionId, String referenceNumber, Long amount) throws IOException {

    RevertPivoTransactionRequest revertRequest = new RevertPivoTransactionRequest(referenceNumber, amount);

    return paymentApi.revertPivoTransaction(transactionId, revertRequest);
  }

  /**
   * Revert AfterPay Transaction fully
   * Committed payments will be refunded, uncommitted ones voided.
   *
   * @param transactionId Transaction id
   * @return Transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public RevertResponse revertAfterPayTransaction(UUID transactionId) throws IOException {

    RevertAfterPayTransactionRequest revertRequest = new RevertAfterPayTransactionRequest();

    return paymentApi.revertAfterPayTransaction(transactionId, revertRequest);
  }

  /**
   * Revert AfterPay Transaction with the given amount
   * Committed payments will be refunded, uncommitted ones voided.
   *
   * @param transactionId Transaction id
   * @param amount Amount to revert
   * @return Transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public RevertResponse revertAfterPayTransaction(UUID transactionId, Long amount) throws IOException {

    RevertAfterPayTransactionRequest revertRequest = new RevertAfterPayTransactionRequest(amount);

    return paymentApi.revertAfterPayTransaction(transactionId, revertRequest);
  }

  /**
   * AfterPay Transaction Result Request
   * Used to find out whether or not an uncommitted transaction succeeded, without actually committing (capturing) it.
   *
   * @param transactionId Transaction id
   * @return AfterPay transaction result response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public AfterPayTransactionResultResponse afterPayTransactionResult(UUID transactionId) throws IOException {

    return paymentApi.afterPayTransactionResult(transactionId);
  }


  /**
   * AfterPay Transaction Commit Request
   * Used to commit (capture) the transaction.
   *
   * @param transactionId Transaction id
   * @param amount The amount to commit, must be less or equal than the initial transaction amount
   * @return Commit transaction response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public AfterPayTransactionCommitResponse commitAfterPayTransaction(UUID transactionId, Long amount) throws IOException {

    CommitAfterPayTransactionRequest commitRequest = new CommitAfterPayTransactionRequest(amount);

    return paymentApi.commitAfterPayTransaction(transactionId, commitRequest);
  }

  /**
   * AfterPay Transaction Status Request
   *
   * @param transactionId Transaction id
   * @return AfterPay transaction status response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public AfterPayTransactionStatusResponse afterPayTransactionStatus(UUID transactionId) throws IOException {

    return paymentApi.afterPayTransactionStatus(transactionId);
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
   * Payment Highway Pivo Transaction Status Request
   *
   * @param transactionId Transaction id
   * @return Transaction status response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public PivoTransactionStatusResponse pivoTransactionStatus(UUID transactionId) throws IOException {

    return paymentApi.pivoTransactionStatus(transactionId);
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
   * For now after masterpass this api isn't supported for any payment type
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
   * Payment Highway Pivo Transaction Result Request
   * Used to find out whether or not a Pivo transaction succeeded.
   *
   * @param transactionId Transaction id
   * @return Transaction result response
   * @throws HttpResponseException Exception
   * @throws AuthenticationException Exception
   * @throws IOException Exception
   */
  public PivoTransactionResultResponse pivoTransactionResult(UUID transactionId) throws IOException {

    return paymentApi.pivoTransactionResult(transactionId);
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

  /**
   * Closes the underlying connection instances. Be careful if using custom HTTP client on multiple instances!
   * @throws IOException Exception
   */
  @Override
  public void close() throws IOException {
    if (paymentApi != null) {
      paymentApi.close();
    }
  }
}
