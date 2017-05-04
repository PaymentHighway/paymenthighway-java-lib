package io.paymenthighway.exception;

import org.apache.http.client.ClientProtocolException;

/**
 * Payment Highway Authentication Exception
 * Raised when message signature from Payment Highway doesn't match
 */
public class AuthenticationException extends ClientProtocolException {

  private static final long serialVersionUID = 8686884614878272601L;

  /**
   *
   */
  public AuthenticationException() {

  }

  /**
   * @param s Exception string
   */
  public AuthenticationException(String s) {
    super(s);
  }

  /**
   * @param cause Throwable exception
   */
  public AuthenticationException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message Exception message
   * @param cause Throwable exception
   */
  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }

}
