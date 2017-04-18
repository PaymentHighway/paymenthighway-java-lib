package io.paymenthighway.connect;

import io.paymenthighway.security.SecureSigner;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * PaymentHighway Form API Connections
 */
public class FormAPIConnection {

  private final static String USER_AGENT = "PaymentHighway Java Lib";
  private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
  private final static String METHOD_POST = "POST";

  private String serviceUrl = null;
  private String signatureKeyId = null;
  private String signatureSecret = null;

  /**
   * Constructor
   */
  public FormAPIConnection(String serviceUrl, String signatureKeyId, String signatureSecret) {

    this.serviceUrl = serviceUrl;
    this.signatureKeyId = signatureKeyId;
    this.signatureSecret = signatureSecret;
  }

  /**
   * Form API call to add card
   *
   * @return Response body
   */
  public String addCardRequest(List<NameValuePair> nameValuePairs) throws IOException {
    return this.addCardRequest(nameValuePairs, true);
  }

  /**
   * Form API call to add card
   *
   * @return Response body
   */
  public String addCardRequest(List<NameValuePair> nameValuePairs, Boolean calculateSignature) throws IOException {
    final String formUri = "/form/view/add_card";
    return makeRequest(formUri, nameValuePairs, calculateSignature);
  }

  /**
   * Form API call to make a payment
   *
   * @return Response body
   */
  public String paymentRequest(List<NameValuePair> nameValuePairs) throws IOException {
    return this.paymentRequest(nameValuePairs, true);
  }

  /**
   * Form API call to make a payment
   *
   * @return Response body
   */
  public String paymentRequest(List<NameValuePair> nameValuePairs, Boolean calculateSignature) throws IOException {
    final String formPaymentUri = "/form/view/pay_with_card";
    return makeRequest(formPaymentUri, nameValuePairs, calculateSignature);
  }

  /**
   * Form API call to add card and make a payment
   *
   * @return Response body
   */
  public String addCardAndPayRequest(List<NameValuePair> nameValuePairs) throws IOException {
    return addCardAndPayRequest(nameValuePairs, true);
  }

  /**
   * Form API call to add card and make a payment
   *
   * @return Response body
   */
  public String addCardAndPayRequest(List<NameValuePair> nameValuePairs, Boolean calculateSignature) throws IOException {
    final String formPaymentUri = "/form/view/add_and_pay_with_card";
    return makeRequest(formPaymentUri, nameValuePairs, calculateSignature);
  }

  /**
   * Form API call to pay with a tokenized card and a CVC
   *
   * @param nameValuePairs The Form API http parameters
   * @return Response body
   */
  public String payWithTokenAndCvcRequest(List<NameValuePair> nameValuePairs) throws IOException {
    return this.payWithTokenAndCvcRequest(nameValuePairs, true);
  }

  /**
   * Form API call to pay with a tokenized card and a CVC
   *
   * @param nameValuePairs The Form API http parameters
   * @param calculateSignature Calculate form signature
   * @return Response body
   */
  public String payWithTokenAndCvcRequest(List<NameValuePair> nameValuePairs, Boolean calculateSignature) throws IOException {
    final String formPaymentUri = "/form/view/pay_with_token_and_cvc";
    return makeRequest(formPaymentUri, nameValuePairs, calculateSignature);
  }

  /**
   *
   * @param formUri
   * @param nameValuePairs
   * @return
   */
  private String makeRequest(String formUri, List<NameValuePair> nameValuePairs) throws IOException {
    return this.makeRequest(formUri, nameValuePairs, true);
  }

  /**
   *
   * @param formUri
   * @param nameValuePairs
   * @return
   */
  private String makeRequest(String formUri, List<NameValuePair> nameValuePairs, Boolean calculateSignature) throws  IOException {
    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpPost httpPost = new HttpPost(this.serviceUrl + formUri);

      if(calculateSignature) {
        String signature = this.createSignature(METHOD_POST, formUri, nameValuePairs);
        nameValuePairs.add(new BasicNameValuePair("signature", signature));
      }
      this.addHeaders(httpPost);

      httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

      return httpclient.execute(httpPost, bodyResponseHandler());
    }
  }

  // TODO: Move this code to some test helper class
  static ResponseHandler<String> bodyResponseHandler() {
    return new ResponseHandler<String>() {
      public String handleResponse(final HttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
          HttpEntity entity = response.getEntity();
          return entity != null ? EntityUtils.toString(entity) : null;
        } else {
          throw new ClientProtocolException("Unexpected response status: " + status);
        }
      }
    };
  }

  /**
   * Create a secure signature
   *
   * @param method
   * @param uri
   * @return String signature
   */
  private String createSignature(String method, String uri, List<NameValuePair> nameValuePairs) {
    SecureSigner ss = new SecureSigner(this.signatureKeyId, this.signatureSecret);
    return ss.createSignature(method, uri, nameValuePairs, "");
  }

  /**
   * Add headers to request
   *
   * @param httpPost
   */
  void addHeaders(HttpPost httpPost) {
    httpPost.addHeader("User-Agent", USER_AGENT);
    httpPost.addHeader("Content-Type", CONTENT_TYPE);
  }
}
