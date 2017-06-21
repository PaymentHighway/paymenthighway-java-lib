package io.paymenthighway.model.response;

public class UserProfileResponse extends Response {

  private UserProfileTransaction transaction;

  /**
   * @return Return transaction containing pre authorization profile information
   */
  public UserProfileTransaction getTransaction() {
    return transaction;
  }
}
