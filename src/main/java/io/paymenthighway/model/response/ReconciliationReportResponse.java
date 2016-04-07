package io.paymenthighway.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Reconciliation Report Response POJO
 */
public class ReconciliationReportResponse extends Response {

  @JsonProperty("settlements")
  ReconciliationSettlement[] reconciliationSettlements;
  @JsonProperty("commission_settlements")
  CommissionSettlement[] commissionSettlements;

  public ReconciliationSettlement[] getReconciliationSettlements() {
    return this.reconciliationSettlements;
  }

  public CommissionSettlement[] getCommissionSettlements() {
    return commissionSettlements;
  }
}
