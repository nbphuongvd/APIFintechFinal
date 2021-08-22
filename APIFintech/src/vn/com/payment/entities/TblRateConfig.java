package vn.com.payment.entities;
// Generated May 16, 2021 10:01:24 AM by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TblRateConfig generated by hbm2java
 */
@Entity
@Table(name = "tbl_rate_config", catalog = "db_fintech")
public class TblRateConfig implements java.io.Serializable {

	private int rateId;
	private String rateName;
	private String rateCode;
	private int rateType;
	private float rateValue;
	private Integer activeStatus;

	public TblRateConfig() {
	}

	public TblRateConfig(int rateId, String rateName, String rateCode, int rateType, float rateValue) {
		this.rateId = rateId;
		this.rateName = rateName;
		this.rateCode = rateCode;
		this.rateType = rateType;
		this.rateValue = rateValue;
	}

	public TblRateConfig(int rateId, String rateName, String rateCode, int rateType, float rateValue,
			Integer activeStatus) {
		this.rateId = rateId;
		this.rateName = rateName;
		this.rateCode = rateCode;
		this.rateType = rateType;
		this.rateValue = rateValue;
		this.activeStatus = activeStatus;
	}

	@Id

	@Column(name = "rate_id", unique = true, nullable = false)
	public int getRateId() {
		return this.rateId;
	}

	public void setRateId(int rateId) {
		this.rateId = rateId;
	}

	@Column(name = "rate_name", nullable = false)
	public String getRateName() {
		return this.rateName;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

	@Column(name = "rate_code", nullable = false)
	public String getRateCode() {
		return this.rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	@Column(name = "rate_type", nullable = false)
	public int getRateType() {
		return this.rateType;
	}

	public void setRateType(int rateType) {
		this.rateType = rateType;
	}

	@Column(name = "rate_value", nullable = false, precision = 8, scale = 0)
	public float getRateValue() {
		return this.rateValue;
	}

	public void setRateValue(float rateValue) {
		this.rateValue = rateValue;
	}

	@Column(name = "active_status")
	public Integer getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

}