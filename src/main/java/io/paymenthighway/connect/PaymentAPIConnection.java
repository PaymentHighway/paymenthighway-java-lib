package io.paymenthighway.connect;

import io.paymenthighway.Constants;
import io.paymenthighway.PaymentHighwayUtility;
import io.paymenthighway.json.JsonGenerator;
import io.paymenthighway.json.JsonParser;
import io.paymenthighway.model.request.*;
import io.paymenthighway.model.response.*;
import io.paymenthighway.model.response.transaction.ChargeCitResponse;
import io.paymenthighway.model.response.transaction.ChargeMitResponse;
import io.paymenthighway.model.response.transaction.DebitTransactionResponse;
import io.paymenthighway.security.SecureSigner;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.Closeable;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * PaymentHighway Payment API Connections
 */
public class PaymentAPIConnection implements Closeable {

  private static String JAVA_VERSION = "-";

  static {
    try {
      JAVA_VERSION = System.getProperty("java.version", JAVA_VERSION);
    } catch (SecurityException ignored) {
    }
  }

  /* Payment API headers */
  private static final String USER_AGENT = "PaymentHighway Java Lib/" + Constants.API_VERSION + " (Java/" + JAVA_VERSION + ")";
  private static final String METHOD_POST = "POST";
  private static final String METHOD_GET = "GET";
  private static final String SPH_API_VERSION = Constants.API_VERSION;

  private String serviceUrl = "";
  private String signatureKeyId = null;
  private String signatureSecret = null;
  private String account = null;
  private String merchant = null;

  private CloseableHttpClient httpclient;

  private JsonParser jsonParser = new JsonParser();

  public static CloseableHttpClient defaultHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
    SSLContext sslContext = SSLContexts.custom().setProtocol("TLSv1.2").build();
    return HttpClients.custom().setSSLContext(sslContext).build();
  }

  /**
   * Payment API Connection
   * @param serviceUrl Production or Sandbox base URL
   * @param signatureKeyId The signature key's ID or name
   * @param signatureSecret The secret signature key
   * @param account Payment Highway account name
   * @param merchant Payment Highway merchant name. One account might have multiple merchants.
   * @param httpClient The underlying HTTP client. Will be closed if the method close() is called.
   */
  public PaymentAPIConnection(
      String serviceUrl,
      String signatureKeyId,
      String signatureSecret,
      String account,
      String merchant,
      CloseableHttpClient httpClient
  ) {

    this.serviceUrl = serviceUrl;
    this.signatureKeyId = signatureKeyId;
    this.signatureSecret = signatureSecret;
    this.account = account;
    this.merchant = merchant;
    this.httpclient = httpClient;
  }

  /**
   * @param httpClient http client
   * @deprecated Use the constructor to inject the httpClient instead.
   */
  public void setHttpClient(CloseableHttpClient httpClient) {
    this.httpclient = httpClient;
  }


  public InitTransactionResponse initTransactionHandle() throws IOException {

    final String paymentUri = "/transaction";

    String response = executePost(paymentUri, createNameValuePairs());

    return jsonParser.mapResponse(response, InitTransactionResponse.class);
  }

  public DebitTransactionResponse debitTransaction(UUID transactionId, TransactionRequest request) throws IOException {

    String requestUri = String.format("/transaction/%s/debit", transactionId);
    String response = executePost(requestUri, createNameValuePairs(request.getRequestId()), request);
    return jsonParser.mapResponse(response, DebitTransactionResponse.class);
  }

  public ChargeCitResponse chargeCustomerInitiatedTransaction(UUID transactionId, ChargeCitRequest request) throws IOException {

    String requestUri = String.format("/transaction/%s/card/charge/customer_initiated", transactionId);
    String response = executePost(requestUri, createNameValuePairs(request.getRequestId()), request);
    return jsonParser.mapResponse(response, ChargeCitResponse.class);
  }

  public ChargeMitResponse chargeMerchantInitiatedTransaction(UUID transactionId, ChargeMitRequest request) throws IOException {

    String requestUri = String.format("/transaction/%s/card/charge/merchant_initiated", transactionId);
    String response = executePost(requestUri, createNameValuePairs(request.getRequestId()), request);
    return jsonParser.mapResponse(response, ChargeMitResponse.class);
  }


  public DebitTransactionResponse debitApplePayTransaction(UUID transactionId, ApplePayTransactionRequest request)
      throws IOException {

    String requestUri = String.format("/transaction/%s/debit_applepay", transactionId);
    String response = executePost(requestUri, createNameValuePairs(request.getRequestId()), request);
    return jsonParser.mapResponse(response, DebitTransactionResponse.class);
  }

  public MobilePayInitResponse initMobilePaySession(MobilePayInitRequest request) throws IOException {
    String requestUri = "/app/mobilepay";
    String response = executePost(requestUri, createNameValuePairs(request.getRequestId()), request);
    return jsonParser.mapResponse(response, MobilePayInitResponse.class);
  }

  public MobilePayStatusResponse mobilePaySessionStatus(String sessionToken) throws IOException {
    String requestUri = String.format("/app/mobilepay/%s", sessionToken);
    String response = executeGet(requestUri, createNameValuePairs());
    return jsonParser.mapResponse(response, MobilePayStatusResponse.class);
  }

  public PivoInitResponse initPivoTransaction(PivoInitRequest request) throws IOException {
    String requestUri = "/app/pivo";
    String response = executePost(requestUri, createNameValuePairs(), request);
    return jsonParser.mapResponse(response, PivoInitResponse.class);
  }

  public RevertResponse revertTransaction(UUID transactionId, RevertTransactionRequest request) throws IOException {
    final String actionUri = "/revert";
    return transactionPost(transactionId, actionUri, request, RevertResponse.class);
  }

  public RevertResponse revertPivoTransaction(UUID transactionId, RevertPivoTransactionRequest request) throws IOException {
    final String actionUri = "/pivo/revert";
    return transactionPost(transactionId, actionUri, request, RevertResponse.class);
  }

  public RevertResponse revertAfterPayTransaction(UUID transactionId, RevertAfterPayTransactionRequest request) throws IOException {
    final String actionUri = "/afterpay/revert";
    return transactionPost(transactionId, actionUri, request, RevertResponse.class);
  }

  public AfterPayTransactionResultResponse afterPayTransactionResult(UUID transactionId) throws IOException {
    final String actionUri = "/afterpay/result";
    return transactionGet(transactionId, actionUri, AfterPayTransactionResultResponse.class);
  }

  public AfterPayTransactionCommitResponse commitAfterPayTransaction(UUID transactionId, CommitAfterPayTransactionRequest request) throws IOException {
    final String actionUri = "/afterpay/commit";
    return transactionPost(transactionId, actionUri, request, AfterPayTransactionCommitResponse.class);
  }

  public AfterPayTransactionStatusResponse afterPayTransactionStatus(UUID transactionId) throws IOException {
    String response = executeGet("/transaction/" + transactionId + "/afterpay", createNameValuePairs());
    return jsonParser.mapResponse(response, AfterPayTransactionStatusResponse.class);
  }

  public CommitTransactionResponse commitTransaction(UUID transactionId, CommitTransactionRequest request) throws IOException {
    final String actionUri = "/commit";
    return transactionPost(transactionId, actionUri, request, CommitTransactionResponse.class);
  }

  public UserProfileResponse userProfile(UUID transactionId) throws IOException {
    String actionUri = "/user_profile";
    return transactionGet(transactionId, actionUri, UserProfileResponse.class);
  }

  public TransactionResultResponse transactionResult(UUID transactionId) throws IOException {
    final String actionUri = "/result";
    return transactionGet(transactionId, actionUri, TransactionResultResponse.class);
  }

  public PivoTransactionResultResponse pivoTransactionResult(UUID transactionId) throws IOException {
    final String actionUri = "/pivo/result";
    return transactionGet(transactionId, actionUri, PivoTransactionResultResponse.class);
  }

  public TransactionStatusResponse transactionStatus(UUID transactionId) throws IOException {
    return transactionGet(transactionId, "",  TransactionStatusResponse.class);
  }

  public PivoTransactionStatusResponse pivoTransactionStatus(UUID transactionId) throws IOException {
    String response = executeGet("/transaction/pivo/" + transactionId, createNameValuePairs());
    return jsonParser.mapResponse(response, PivoTransactionStatusResponse.class);
  }

  private <T> T transactionPost(UUID transactionId, String actionUri, Request request, Class<T> clazz) throws IOException {
    final String paymentUri = "/transaction/";
    String uri = paymentUri + transactionId + actionUri;
    String response = executePost(uri, createNameValuePairs(request.getRequestId()), request);

    return jsonParser.mapResponse(response, clazz);
  }

  private <T> T transactionGet(UUID transactionId, String actionUri, Class<T> clazz) throws IOException {
    final String paymentUri = "/transaction/";
    String uri = paymentUri + transactionId + actionUri;
    String response = executeGet(uri, createNameValuePairs());

    return jsonParser.mapResponse(response, clazz);
  }

  public OrderSearchResponse searchOrders(String order) throws IOException {

    final String paymentUri = "/transactions/?order=";

    String searchUri = paymentUri + order;

    String response = executeGet(searchUri, createNameValuePairs());

    return jsonParser.mapResponse(response, OrderSearchResponse.class);
  }

  public TokenizationResponse tokenization(UUID tokenizationId) throws IOException {

    final String paymentUri = "/tokenization/";

    String tokenUri = paymentUri + tokenizationId;

    String response = executeGet(tokenUri, createNameValuePairs());

    return jsonParser.mapResponse(response, TokenizationResponse.class);
  }

  public ReportResponse fetchReport(String date) throws IOException {

    final String reportUri = "/report/batch/";

    String fetchUri = reportUri + date;

    String response = executeGet(fetchUri, createNameValuePairs());

    return jsonParser.mapResponse(response, ReportResponse.class);
  }

  public ReconciliationReportResponse fetchReconciliationReport(String date) throws IOException {
    return fetchReconciliationReport(date, false);
  }

  public ReconciliationReportResponse fetchReconciliationReport(String date, Boolean useDateProcessed) throws IOException {
    final String reportUri = "/report/reconciliation/";

    String queryString = String.format("?use-date-processed=%s", useDateProcessed);

    String fetchUri = reportUri + date + queryString;

    String response = executeGet(fetchUri, createNameValuePairs());

    return jsonParser.mapResponse(response, ReconciliationReportResponse.class);
  }

  protected String executeGet(String requestUri, List<NameValuePair> nameValuePairs) throws IOException {
    SecureSigner ss = new SecureSigner(this.signatureKeyId, this.signatureSecret);

    HttpRequestBase httpRequest = new HttpGet(this.serviceUrl + requestUri);

    String signature = this.createSignature(ss, METHOD_GET, requestUri, nameValuePairs, null);
    nameValuePairs.add(new BasicNameValuePair("signature", signature));

    this.addHeaders(httpRequest, nameValuePairs);

    ResponseHandler<String> responseHandler = new PaymentHighwayResponseHandler(ss, METHOD_GET, requestUri);

    return httpclient.execute(httpRequest, responseHandler);
  }

  protected String executePost(String requestUri, List<NameValuePair> nameValuePairs, Request requestBody) throws IOException {
    SecureSigner ss = new SecureSigner(this.signatureKeyId, this.signatureSecret);

    HttpPost httpRequest = new HttpPost(this.serviceUrl + requestUri);

    String signature = this.createSignature(ss, METHOD_POST, requestUri, nameValuePairs, requestBody);
    nameValuePairs.add(new BasicNameValuePair("signature", signature));

    this.addHeaders(httpRequest, nameValuePairs);

    if (requestBody != null) {
      this.addBody(httpRequest, requestBody);
    }

    ResponseHandler<String> responseHandler = new PaymentHighwayResponseHandler(ss, METHOD_POST, requestUri);

    return httpclient.execute(httpRequest, responseHandler);
  }

  private String executePost(String requestUri, List<NameValuePair> nameValuePairs) throws IOException {
    return executePost(requestUri, nameValuePairs, null);
  }

  protected void addHeaders(HttpRequestBase httpPost, List<NameValuePair> nameValuePairs) {

    httpPost.addHeader(HTTP.USER_AGENT, USER_AGENT);
    httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");

    for (NameValuePair param : nameValuePairs) {
      httpPost.addHeader(param.getName(), param.getValue());
    }
  }

  private void addBody(HttpPost httpPost, Request request) {
    JsonGenerator jsonGen = new JsonGenerator();
    String requestBody = jsonGen.createTransactionJson(request);
    StringEntity requestEntity = new StringEntity(requestBody, "utf-8");

    httpPost.setEntity(requestEntity);
  }

  private String createSignature(SecureSigner ss, String method, String uri, List<NameValuePair> nameValuePairs, Request request) {

    String json = "";
    if (request != null) {
      JsonGenerator jsonGenerator = new JsonGenerator();
      json = jsonGenerator.createTransactionJson(request);
    }
    return ss.createSignature(method, uri, nameValuePairs, json);
  }

  private List<NameValuePair> createNameValuePairs() {
    return createNameValuePairs(PaymentHighwayUtility.createRequestId());
  }

  /**
   * Create name value pairs
   *
   * @return
   */
  private List<NameValuePair> createNameValuePairs(String requestId) {
    List<NameValuePair> nameValuePairs = new ArrayList<>();
    nameValuePairs.add(new BasicNameValuePair("sph-api-version", SPH_API_VERSION));
    nameValuePairs.add(new BasicNameValuePair("sph-account", this.account));
    nameValuePairs.add(new BasicNameValuePair("sph-merchant", this.merchant));
    nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
    nameValuePairs.add(new BasicNameValuePair("sph-request-id", requestId));
    return nameValuePairs;
  }

  @Override
  public void close() throws IOException {
    if (httpclient != null) {
      httpclient.close();
    }
  }
}
