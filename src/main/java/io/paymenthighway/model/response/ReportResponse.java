package io.paymenthighway.model.response;

/**
 * Report Response POJO
 */
public class ReportResponse {

  Result result;

  Settlement[] settlements;

  public Result getResult() {
    return this.result;
  }

  public Settlement[] getSettlements() {
    return this.settlements;
  }
}
