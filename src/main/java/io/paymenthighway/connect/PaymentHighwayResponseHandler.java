package io.paymenthighway.connect;

import io.paymenthighway.exception.AuthenticationException;
import io.paymenthighway.security.SecureSigner;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Custom Response Handler
 */
public class PaymentHighwayResponseHandler implements ResponseHandler<String> {

  private SecureSigner ss = null;
  private String method = null;
  private String uri = null;

  public PaymentHighwayResponseHandler(SecureSigner ss, String method, String uri) {
    this.ss = ss;
    this.method = method;
    this.uri = uri;
  }

  @Override
  public String handleResponse(final HttpResponse response) throws IOException {

    String content = EntityUtils.toString(response.getEntity());
    int status = response.getStatusLine().getStatusCode();

    if (status >= 200 && status < 300) {
      boolean authenticated = ss.authenticate(this.method, this.uri, response, content);
      if (!authenticated) {
        // signals a failure to authenticate incoming message signature
        System.err.println("Message authentication failed, status:" + status + ", reason:" +
            response.getStatusLine().getReasonPhrase() + ":" + content);
        throw new AuthenticationException(
            "Message authentication failed, status:" + status + ", reason:" +
                response.getStatusLine().getReasonPhrase() + ":" + content);
      }
      HttpEntity entity = response.getEntity();
      return entity != null ? content
          : null;
    } else if (status == 401) {
      // signals an authentication failure in Payment Highway
      // Payment Highway couldn't authenticate signature from the given parameters
      throw new HttpResponseException(status, " Authentication failure: " +
          response.getStatusLine().getReasonPhrase() + ":" + content);
    } else {
      // Signals a non 2xx HTTP response.
      System.err.println("status:" + status + ", reason:" +
          response.getStatusLine().getReasonPhrase() + ":" + content);
      throw new HttpResponseException(status, " reason:" +
          response.getStatusLine().getReasonPhrase() + ":" + content);
    }
  }
}
