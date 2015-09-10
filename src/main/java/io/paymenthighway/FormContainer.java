package io.paymenthighway;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Everything you need for a form
 */
public class FormContainer {

  private String method;
  private String baseUrl;
  private String actionUrl;
  private List<NameValuePair> nameValuePairs;
  private String requestId;

  public FormContainer(String method, String baseUrl, String actionUrl, List<NameValuePair> nameValuePairs, String requestId) {
    this.method = method;
    this.baseUrl = baseUrl;
    this.actionUrl = actionUrl;
    this.nameValuePairs = nameValuePairs;
    this.requestId = requestId;
  }

  public String getMethod() {
    return this.method;
  }

  public String getAction() {
    return this.baseUrl + this.actionUrl;
  }

  public List<NameValuePair> getFields() {
    return nameValuePairs;
  }

  public String getRequestId() {
    return requestId;
  }
}
