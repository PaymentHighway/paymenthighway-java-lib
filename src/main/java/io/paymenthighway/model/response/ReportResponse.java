package io.paymenthighway.model.response;

/**
 * Report Response POJO
 */
public class ReportResponse extends Response {

  Settlement[] settlements;

  public Settlement[] getSettlements() {
    return this.settlements;
  }
}
