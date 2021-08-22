package vn.com.payment.entities;
// Generated Jun 22, 2021 11:26:33 PM by Hibernate Tools 3.5.0.Final

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
 * TblBorrower generated by hbm2java
 */
@Entity
@Table(name = "tbl_borrower", catalog = "db_fintech")
public class TblBorrower implements java.io.Serializable {

	private Integer borrowerId;
	private String borrowerName;
	private String borrowerMobile;
	private String borrowerEmail;
	private String idNumber;
	private String idImgFront;
	private String idImgBack;
	private String licenseDriveNumber;
	private String borrowerCode;
	private String borrowerAddress;
	private Date borrowerBirthDay;
	private String idIssuedBy;
	private Date idIssuedDate;
	private String bankAccount;
	private String bankCode;
	private String bankName;
	private String bankBranch;
	private int borrowerType;
	private Integer branchId;

	public TblBorrower() {
	}

	public TblBorrower(String borrowerName, String borrowerMobile, String idNumber, Date idIssuedDate,
			int borrowerType) {
		this.borrowerName = borrowerName;
		this.borrowerMobile = borrowerMobile;
		this.idNumber = idNumber;
		this.idIssuedDate = idIssuedDate;
		this.borrowerType = borrowerType;
	}

	public TblBorrower(String borrowerName, String borrowerMobile, String borrowerEmail, String idNumber,
			String idImgFront, String idImgBack, String licenseDriveNumber, String borrowerCode, String borrowerAddress,
			Date borrowerBirthDay, String idIssuedBy, Date idIssuedDate, String bankAccount, String bankCode,
			String bankName, String bankBranch, int borrowerType, Integer branchId) {
		this.borrowerName = borrowerName;
		this.borrowerMobile = borrowerMobile;
		this.borrowerEmail = borrowerEmail;
		this.idNumber = idNumber;
		this.idImgFront = idImgFront;
		this.idImgBack = idImgBack;
		this.licenseDriveNumber = licenseDriveNumber;
		this.borrowerCode = borrowerCode;
		this.borrowerAddress = borrowerAddress;
		this.borrowerBirthDay = borrowerBirthDay;
		this.idIssuedBy = idIssuedBy;
		this.idIssuedDate = idIssuedDate;
		this.bankAccount = bankAccount;
		this.bankCode = bankCode;
		this.bankName = bankName;
		this.bankBranch = bankBranch;
		this.borrowerType = borrowerType;
		this.branchId = branchId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "borrower_id", unique = true, nullable = false)
	public Integer getBorrowerId() {
		return this.borrowerId;
	}

	public void setBorrowerId(Integer borrowerId) {
		this.borrowerId = borrowerId;
	}

	@Column(name = "borrower_name", nullable = false, length = 100)
	public String getBorrowerName() {
		return this.borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	@Column(name = "borrower_mobile", nullable = false, length = 500)
	public String getBorrowerMobile() {
		return this.borrowerMobile;
	}

	public void setBorrowerMobile(String borrowerMobile) {
		this.borrowerMobile = borrowerMobile;
	}

	@Column(name = "borrower_email", length = 100)
	public String getBorrowerEmail() {
		return this.borrowerEmail;
	}

	public void setBorrowerEmail(String borrowerEmail) {
		this.borrowerEmail = borrowerEmail;
	}

	@Column(name = "id_number", nullable = false, length = 20)
	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Column(name = "id_img_front", length = 200)
	public String getIdImgFront() {
		return this.idImgFront;
	}

	public void setIdImgFront(String idImgFront) {
		this.idImgFront = idImgFront;
	}

	@Column(name = "id_img_back", length = 200)
	public String getIdImgBack() {
		return this.idImgBack;
	}

	public void setIdImgBack(String idImgBack) {
		this.idImgBack = idImgBack;
	}

	@Column(name = "license_drive_number", length = 50)
	public String getLicenseDriveNumber() {
		return this.licenseDriveNumber;
	}

	public void setLicenseDriveNumber(String licenseDriveNumber) {
		this.licenseDriveNumber = licenseDriveNumber;
	}

	@Column(name = "borrower_code", length = 20)
	public String getBorrowerCode() {
		return this.borrowerCode;
	}

	public void setBorrowerCode(String borrowerCode) {
		this.borrowerCode = borrowerCode;
	}

	@Column(name = "borrower_address", length = 200)
	public String getBorrowerAddress() {
		return this.borrowerAddress;
	}

	public void setBorrowerAddress(String borrowerAddress) {
		this.borrowerAddress = borrowerAddress;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "borrower_birth_day", length = 10)
	public Date getBorrowerBirthDay() {
		return this.borrowerBirthDay;
	}

	public void setBorrowerBirthDay(Date borrowerBirthDay) {
		this.borrowerBirthDay = borrowerBirthDay;
	}

	@Column(name = "id_issued_by", length = 200)
	public String getIdIssuedBy() {
		return this.idIssuedBy;
	}

	public void setIdIssuedBy(String idIssuedBy) {
		this.idIssuedBy = idIssuedBy;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "id_issued_date", nullable = false, length = 10)
	public Date getIdIssuedDate() {
		return this.idIssuedDate;
	}

	public void setIdIssuedDate(Date idIssuedDate) {
		this.idIssuedDate = idIssuedDate;
	}

	@Column(name = "bank_account", length = 20)
	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(name = "bank_code", length = 20)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "bank_name", length = 200)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "bank_branch", length = 200)
	public String getBankBranch() {
		return this.bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	@Column(name = "borrower_type", nullable = false)
	public int getBorrowerType() {
		return this.borrowerType;
	}

	public void setBorrowerType(int borrowerType) {
		this.borrowerType = borrowerType;
	}

	@Column(name = "branch_id")
	public Integer getBranchId() {
		return this.branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

}