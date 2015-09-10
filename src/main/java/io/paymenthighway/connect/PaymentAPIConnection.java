package io.paymenthighway.connect;

import io.paymenthighway.PaymentHighwayUtility;
import io.paymenthighway.exception.AuthenticationException;
import io.paymenthighway.json.JsonGenerator;
import io.paymenthighway.json.JsonParser;
import io.paymenthighway.model.request.CommitTransactionRequest;
import io.paymenthighway.model.request.Request;
import io.paymenthighway.model.request.RevertTransactionRequest;
import io.paymenthighway.model.request.TransactionRequest;
import io.paymenthighway.model.response.*;
import io.paymenthighway.security.SecureSigner;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * PaymentHighway Payment API Connections
 */
public class PaymentAPIConnection implements Closeable {

  /* Payment API headers */
  private static final String USER_AGENT = "PaymentHighway Java Lib";
  private static final String METHOD_POST = "POST";
  private static final String METHOD_GET = "GET";

  private String serviceUrl = "";
  private String signatureKeyId = null;
  private String signatureSecret = null;
  private String account = null;
  private String merchant = null;

  private CloseableHttpClient httpclient;

  /**
   * Constructor
   *
   * @param serviceUrl
   * @param account
   * @param merchant
   * @param signatureKeyId
   * @param signatureSecret
   */
  public PaymentAPIConnection(String serviceUrl, String signatureKeyId, String signatureSecret, String account, String merchant) {

    this.serviceUrl = serviceUrl;
    this.signatureKeyId = signatureKeyId;
    this.signatureSecret = signatureSecret;
    this.account = account;
    this.merchant = merchant;
  }

  public void setHttpClient(CloseableHttpClient httpClient) {
    this.httpclient = httpClient;
  }

  public InitTransactionResponse initTransactionHandle() throws IOException {

    final String paymentUri = "/transaction";

    String response = executePost(paymentUri, createNameValuePairs());

    JsonParser jpar = new JsonParser();
    return jpar.mapInitTransactionResponse(response);
  }

  public TransactionResponse debitTransaction(UUID transactionId, TransactionRequest request) throws IOException {

    final String paymentUri = "/transaction/";
    final String actionUri = "/debit";
    String debitUri = paymentUri + transactionId + actionUri;

    String response = executePost(debitUri, createNameValuePairs(), request);

    JsonParser jpar = new JsonParser();
    return jpar.mapTransactionResponse(response);
  }

  public TransactionResponse creditTransaction(UUID transactionId, TransactionRequest request) throws IOException {

    final String paymentUri = "/transaction/";
    final String actionUri = "/credit";
    String creditUri = paymentUri + transactionId + actionUri;

    String response = executePost(creditUri, createNameValuePairs(), request);

    JsonParser jpar = new JsonParser();
    return jpar.mapTransactionResponse(response);
  }

  public TransactionResponse revertTransaction(UUID transactionId, RevertTransactionRequest request) throws IOException {

    final String paymentUri = "/transaction/";
    final String actionUri = "/revert";
    String revertUri = paymentUri + transactionId + actionUri;

    String response = executePost(revertUri, createNameValuePairs(), request);

    JsonParser jpar = new JsonParser();
    return jpar.mapTransactionResponse(response);
  }

  public CommitTransactionResponse commitTransaction(UUID transactionId, CommitTransactionRequest request) throws IOException {

    final String paymentUri = "/transaction/";
    final String actionUri = "/commit";
    String commitUri = paymentUri + transactionId + actionUri;

    String response = executePost(commitUri, createNameValuePairs(), request);

    JsonParser jpar = new JsonParser();
    return jpar.mapCommitTransactionResponse(response);
  }

  public TransactionStatusResponse transactionStatus(UUID transactionId) throws IOException {

    final String paymentUri = "/transaction/";

    String statusUri = paymentUri + transactionId;

    String response = executeGet(statusUri, createNameValuePairs());

    JsonParser jpar = new JsonParser();
    return jpar.mapTransactionStatusResponse(response);
  }

  public TokenizationResponse tokenization(UUID tokenizationId) throws IOException {

    final String paymentUri = "/tokenization/";

    String tokenUri = paymentUri + tokenizationId;

    String response = executeGet(tokenUri, createNameValuePairs());

    JsonParser jpar = new JsonParser();
    return jpar.mapTokenizationResponse(response);
  }

  public ReportResponse fetchReport(String date) throws IOException {

    final String reportUri = "/report/batch/";

    String fetchUri = reportUri + date;

    String response = executeGet(fetchUri, createNameValuePairs());

    JsonParser jpar = new JsonParser();
    return jpar.mapReportResponse(response);
  }

  private String executeGet(String requestUri, List<NameValuePair> nameValuePairs) throws IOException {
    CloseableHttpClient httpclient = returnHttpClients();

    SecureSigner ss = new SecureSigner(this.signatureKeyId, this.signatureSecret);

    HttpRequestBase httpRequest = new HttpGet(this.serviceUrl + requestUri);

    String signature = this.createSignature(ss, METHOD_GET, requestUri, nameValuePairs, null);
    nameValuePairs.add(new BasicNameValuePair("signature", signature));

    this.addHeaders(httpRequest, nameValuePairs);

    ResponseHandler<String> responseHandler = new PaymentHighwayResponseHandler(ss, METHOD_GET, requestUri);

    return httpclient.execute(httpRequest, responseHandler);
  }

  private String executePost(String requestUri, List<NameValuePair> nameValuePairs, Request requestBody) throws IOException {
    CloseableHttpClient httpclient = returnHttpClients();

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

  /**
   * Create name value pairs
   *
   * @return
   */
  private List<NameValuePair> createNameValuePairs() {
    List<NameValuePair> nameValuePairs = new ArrayList<>();
    nameValuePairs.add(new BasicNameValuePair("sph-account", this.account));
    nameValuePairs.add(new BasicNameValuePair("sph-merchant", this.merchant));
    nameValuePairs.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
    nameValuePairs.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
    return nameValuePairs;
  }

  private CloseableHttpClient returnHttpClients() {
    if (httpclient == null) {
      httpclient = HttpClients.createDefault();
    }
    return httpclient;
  }

  @Override
  public void close() throws IOException {
    if (httpclient != null) {
      httpclient.close();
    }
  }
}
