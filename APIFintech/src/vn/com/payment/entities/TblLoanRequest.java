package vn.com.payment.entities;
// Generated Jul 1, 2021 12:50:47 AM by Hibernate Tools 3.5.0.Final

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TblLoanRequest generated by hbm2java
 */
@Entity
@Table(name = "tbl_loan_request", catalog = "db_fintech")
public class TblLoanRequest implements java.io.Serializable {

	private Integer loanId;
	private Date createdDate;
	private Date editedDate;
	private Date expireDate;
	private Date approvedDate;
	private String createdBy;
	private String approvedBy;
	private Integer finalStatus;
	private Integer previousStatus;
	private Integer sponsorId;
	private Date latestUpdate;
	private String approveRejectedReason;
	private Integer calculateProfitType;
	private Integer loanForMonth;
	private String loanCode;
	private String loanName;
	private String contractSerialNum;
	private Integer branchId;
	private Integer roomId;
	private Integer extendStatus;
	private Date settleDate;
	private BigDecimal settleAmount;
	private BigDecimal settleFee;
	private BigDecimal latestAmtToDecrYourLoan;
	private BigDecimal interestTillNow;
	private Integer insuranceStatus;
	private BigDecimal insurancePaidAmount;
	private BigDecimal insuranceFeeAmt;
	private String partnerInsuranceCode;
	private String partnerInsuranceId;
	private BigDecimal totalPaidOnLoan;
	private BigDecimal totalPaidForInterest;
	private BigDecimal totalAmountOverdue;
	private BigDecimal totalInterestOverdue;
	private BigDecimal totalAmountMustPay;       // Goc con` phai thu
	private BigDecimal totalInsterestMustPay; 	 // 'Lai con` phai? thu
	private BigDecimal settlementFee;
	private BigDecimal insurancePaidAmtFrApi;
	private BigDecimal insuranceFeeFrApi;

	public TblLoanRequest() {
	}

	public TblLoanRequest(Date createdDate, String createdBy, String loanCode) {
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.loanCode = loanCode;
	}

	public TblLoanRequest(Date createdDate, Date editedDate, Date expireDate, Date approvedDate, String createdBy,
			String approvedBy, Integer finalStatus, Integer previousStatus, Integer sponsorId, Date latestUpdate,
			String approveRejectedReason, Integer calculateProfitType, Integer loanForMonth, String loanCode,
			String loanName, String contractSerialNum, Integer branchId, Integer roomId, Integer extendStatus,
			Date settleDate, BigDecimal settleAmount, BigDecimal settleFee, BigDecimal latestAmtToDecrYourLoan,
			BigDecimal interestTillNow, Integer insuranceStatus, BigDecimal insurancePaidAmount,
			BigDecimal insuranceFeeAmt, String partnerInsuranceCode, String partnerInsuranceId,
			BigDecimal totalPaidOnLoan, BigDecimal totalPaidForInterest, BigDecimal totalAmountOverdue,
			BigDecimal totalInterestOverdue, BigDecimal totalAmountMustPay, BigDecimal totalInsterestMustPay,
			BigDecimal settlementFee, BigDecimal insurancePaidAmtFrApi, BigDecimal insuranceFeeFrApi) {
		this.createdDate = createdDate;
		this.editedDate = editedDate;
		this.expireDate = expireDate;
		this.approvedDate = approvedDate;
		this.createdBy = createdBy;
		this.approvedBy = approvedBy;
		this.finalStatus = finalStatus;
		this.previousStatus = previousStatus;
		this.sponsorId = sponsorId;
		this.latestUpdate = latestUpdate;
		this.approveRejectedReason = approveRejectedReason;
		this.calculateProfitType = calculateProfitType;
		this.loanForMonth = loanForMonth;
		this.loanCode = loanCode;
		this.loanName = loanName;
		this.contractSerialNum = contractSerialNum;
		this.branchId = branchId;
		this.roomId = roomId;
		this.extendStatus = extendStatus;
		this.settleDate = settleDate;
		this.settleAmount = settleAmount;
		this.settleFee = settleFee;
		this.latestAmtToDecrYourLoan = latestAmtToDecrYourLoan;
		this.interestTillNow = interestTillNow;
		this.insuranceStatus = insuranceStatus;
		this.insurancePaidAmount = insurancePaidAmount;
		this.insuranceFeeAmt = insuranceFeeAmt;
		this.partnerInsuranceCode = partnerInsuranceCode;
		this.partnerInsuranceId = partnerInsuranceId;
		this.totalPaidOnLoan = totalPaidOnLoan;
		this.totalPaidForInterest = totalPaidForInterest;
		this.totalAmountOverdue = totalAmountOverdue;
		this.totalInterestOverdue = totalInterestOverdue;
		this.totalAmountMustPay = totalAmountMustPay;
		this.totalInsterestMustPay = totalInsterestMustPay;
		this.settlementFee = settlementFee;
		this.insurancePaidAmtFrApi = insurancePaidAmtFrApi;
		this.insuranceFeeFrApi = insuranceFeeFrApi;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "loan_id", unique = true, nullable = false)
	public Integer getLoanId() {
		return this.loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edited_date", length = 19)
	public Date getEditedDate() {
		return this.editedDate;
	}

	public void setEditedDate(Date editedDate) {
		this.editedDate = editedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expire_date", length = 19)
	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approved_date", length = 19)
	public Date getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name = "created_by", nullable = false, length = 100)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "approved_by", length = 100)
	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name = "final_status")
	public Integer getFinalStatus() {
		return this.finalStatus;
	}

	public void setFinalStatus(Integer finalStatus) {
		this.finalStatus = finalStatus;
	}

	@Column(name = "previous_status")
	public Integer getPreviousStatus() {
		return this.previousStatus;
	}

	public void setPreviousStatus(Integer previousStatus) {
		this.previousStatus = previousStatus;
	}

	@Column(name = "sponsor_id")
	public Integer getSponsorId() {
		return this.sponsorId;
	}

	public void setSponsorId(Integer sponsorId) {
		this.sponsorId = sponsorId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "latest_update", length = 19)
	public Date getLatestUpdate() {
		return this.latestUpdate;
	}

	public void setLatestUpdate(Date latestUpdate) {
		this.latestUpdate = latestUpdate;
	}

	@Column(name = "approve_rejected_reason")
	public String getApproveRejectedReason() {
		return this.approveRejectedReason;
	}

	public void setApproveRejectedReason(String approveRejectedReason) {
		this.approveRejectedReason = approveRejectedReason;
	}

	@Column(name = "calculate_profit_type")
	public Integer getCalculateProfitType() {
		return this.calculateProfitType;
	}

	public void setCalculateProfitType(Integer calculateProfitType) {
		this.calculateProfitType = calculateProfitType;
	}

	@Column(name = "loan_for_month")
	public Integer getLoanForMonth() {
		return this.loanForMonth;
	}

	public void setLoanForMonth(Integer loanForMonth) {
		this.loanForMonth = loanForMonth;
	}

	@Column(name = "loan_code", nullable = false)
	public String getLoanCode() {
		return this.loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	@Column(name = "loan_name")
	public String getLoanName() {
		return this.loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	@Column(name = "contract_serial_num", length = 30)
	public String getContractSerialNum() {
		return this.contractSerialNum;
	}

	public void setContractSerialNum(String contractSerialNum) {
		this.contractSerialNum = contractSerialNum;
	}

	@Column(name = "branch_id")
	public Integer getBranchId() {
		return this.branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	@Column(name = "room_id")
	public Integer getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	@Column(name = "extend_status")
	public Integer getExtendStatus() {
		return this.extendStatus;
	}

	public void setExtendStatus(Integer extendStatus) {
		this.extendStatus = extendStatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "settle_date", length = 10)
	public Date getSettleDate() {
		return this.settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	@Column(name = "settle_amount", precision = 20, scale = 0)
	public BigDecimal getSettleAmount() {
		return this.settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	@Column(name = "settle_fee", precision = 20, scale = 0)
	public BigDecimal getSettleFee() {
		return this.settleFee;
	}

	public void setSettleFee(BigDecimal settleFee) {
		this.settleFee = settleFee;
	}

	@Column(name = "latest_amt_to_decr_your_loan", precision = 20, scale = 0)
	public BigDecimal getLatestAmtToDecrYourLoan() {
		return this.latestAmtToDecrYourLoan;
	}

	public void setLatestAmtToDecrYourLoan(BigDecimal latestAmtToDecrYourLoan) {
		this.latestAmtToDecrYourLoan = latestAmtToDecrYourLoan;
	}

	@Column(name = "interest_till_now", precision = 20, scale = 0)
	public BigDecimal getInterestTillNow() {
		return this.interestTillNow;
	}

	public void setInterestTillNow(BigDecimal interestTillNow) {
		this.interestTillNow = interestTillNow;
	}

	@Column(name = "insurance_status")
	public Integer getInsuranceStatus() {
		return this.insuranceStatus;
	}

	public void setInsuranceStatus(Integer insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}

	@Column(name = "insurance_paid_amount", precision = 20, scale = 0)
	public BigDecimal getInsurancePaidAmount() {
		return this.insurancePaidAmount;
	}

	public void setInsurancePaidAmount(BigDecimal insurancePaidAmount) {
		this.insurancePaidAmount = insurancePaidAmount;
	}

	@Column(name = "insurance_fee_amt", precision = 20, scale = 0)
	public BigDecimal getInsuranceFeeAmt() {
		return this.insuranceFeeAmt;
	}

	public void setInsuranceFeeAmt(BigDecimal insuranceFeeAmt) {
		this.insuranceFeeAmt = insuranceFeeAmt;
	}

	@Column(name = "partner_insurance_code", length = 200)
	public String getPartnerInsuranceCode() {
		return this.partnerInsuranceCode;
	}

	public void setPartnerInsuranceCode(String partnerInsuranceCode) {
		this.partnerInsuranceCode = partnerInsuranceCode;
	}

	@Column(name = "partner_insurance_id", length = 500)
	public String getPartnerInsuranceId() {
		return this.partnerInsuranceId;
	}

	public void setPartnerInsuranceId(String partnerInsuranceId) {
		this.partnerInsuranceId = partnerInsuranceId;
	}

	@Column(name = "total_paid_on_loan", precision = 20, scale = 0)
	public BigDecimal getTotalPaidOnLoan() {
		return this.totalPaidOnLoan;
	}

	public void setTotalPaidOnLoan(BigDecimal totalPaidOnLoan) {
		this.totalPaidOnLoan = totalPaidOnLoan;
	}

	@Column(name = "total_paid_for_interest", precision = 20, scale = 0)
	public BigDecimal getTotalPaidForInterest() {
		return this.totalPaidForInterest;
	}

	public void setTotalPaidForInterest(BigDecimal totalPaidForInterest) {
		this.totalPaidForInterest = totalPaidForInterest;
	}

	@Column(name = "total_amount_overdue", precision = 20, scale = 0)
	public BigDecimal getTotalAmountOverdue() {
		return this.totalAmountOverdue;
	}

	public void setTotalAmountOverdue(BigDecimal totalAmountOverdue) {
		this.totalAmountOverdue = totalAmountOverdue;
	}

	@Column(name = "total_interest_overdue", precision = 20, scale = 0)
	public BigDecimal getTotalInterestOverdue() {
		return this.totalInterestOverdue;
	}

	public void setTotalInterestOverdue(BigDecimal totalInterestOverdue) {
		this.totalInterestOverdue = totalInterestOverdue;
	}

	@Column(name = "total_amount_must_pay", precision = 20, scale = 0)
	public BigDecimal getTotalAmountMustPay() {
		return this.totalAmountMustPay;
	}

	public void setTotalAmountMustPay(BigDecimal totalAmountMustPay) {
		this.totalAmountMustPay = totalAmountMustPay;
	}

	@Column(name = "total_insterest_must_pay", precision = 20, scale = 0)
	public BigDecimal getTotalInsterestMustPay() {
		return this.totalInsterestMustPay;
	}

	public void setTotalInsterestMustPay(BigDecimal totalInsterestMustPay) {
		this.totalInsterestMustPay = totalInsterestMustPay;
	}

	@Column(name = "settlement_fee", precision = 20, scale = 0)
	public BigDecimal getSettlementFee() {
		return this.settlementFee;
	}

	public void setSettlementFee(BigDecimal settlementFee) {
		this.settlementFee = settlementFee;
	}

	@Column(name = "insurance_paid_amt_fr_api", precision = 20, scale = 0)
	public BigDecimal getInsurancePaidAmtFrApi() {
		return this.insurancePaidAmtFrApi;
	}

	public void setInsurancePaidAmtFrApi(BigDecimal insurancePaidAmtFrApi) {
		this.insurancePaidAmtFrApi = insurancePaidAmtFrApi;
	}

	@Column(name = "insurance_fee_fr_api", precision = 20, scale = 0)
	public BigDecimal getInsuranceFeeFrApi() {
		return this.insuranceFeeFrApi;
	}

	public void setInsuranceFeeFrApi(BigDecimal insuranceFeeFrApi) {
		this.insuranceFeeFrApi = insuranceFeeFrApi;
	}

}