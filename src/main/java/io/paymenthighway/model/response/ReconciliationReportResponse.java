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
  @JsonProperty("financial_adjustments")
  FinancialAdjustment[] financialAdjustments;
  @JsonProperty("rejections")
  Rejection[] rejections;

  public ReconciliationSettlement[] getReconciliationSettlements() {
    return this.reconciliationSettlements;
  }

  public CommissionSettlement[] getCommissionSettlements() {
    return commissionSettlements;
  }

  public FinancialAdjustment[] getFinancialAdjustments() {
    return financialAdjustments;
  }

  public Rejection[] getRejections() {
    return rejections;
  }
}
