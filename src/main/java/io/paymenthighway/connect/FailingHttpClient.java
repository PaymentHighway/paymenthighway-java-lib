package io.paymenthighway.connect;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Causes delayed error messages in order to avoid breaking changes.
 * This exists for backward compatibility purposes only and should be removed when breaking changes are acceptable!
 */
public class FailingHttpClient extends CloseableHttpClient {

  private Exception exception = null;

  public FailingHttpClient(Exception exception) {
    this.exception = exception;
  }

  @Override
  protected CloseableHttpResponse doExecute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
    throw new IOException(String.format("Failed initializing PaymentAPIConnection: %s", exception.getMessage()));
  }

  @Override
  public void close() throws IOException {

  }

  @Override
  public HttpParams getParams() {
    return null;
  }

  @Override
  public ClientConnectionManager getConnectionManager() {
    return null;
  }
}