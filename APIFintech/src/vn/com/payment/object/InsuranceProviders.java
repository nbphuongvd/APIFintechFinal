package vn.com.payment.object;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import com.google.gson.Gson;

import vn.com.payment.entities.SubPermission;
import vn.com.payment.entities.TblInsuranceBanks;

public class InsuranceProviders {

	public long insuranceProviderId;
	public String insuranceCode;
	public String insuranceName;
	public Integer activeStt;
	public Date createdDate;
	public Date editedDate;
	public Integer fixFee;
	public Float fixRate;
	public String insuranceApi;
	public String authToken;
	public String apiAcc;
	public String apiPass;
	public String authTokenExpires;
	public Integer insurancedefault;
	
	List<TblInsuranceBanks> insuranceBanks;
//	
//	private String accBankName;
//	private String accBankNo;
//	private String bankName;

	public String toJSON() {
		String json = "";
		try {
			Gson gson = new Gson();
			json = gson.toJson(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	

	public long getInsuranceProviderId() {
		return insuranceProviderId;
	}



	public void setInsuranceProviderId(long insuranceProviderId) {
		this.insuranceProviderId = insuranceProviderId;
	}



	public String getInsuranceCode() {
		return insuranceCode;
	}

	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public Integer getActiveStt() {
		return activeStt;
	}

	public void setActiveStt(Integer activeStt) {
		this.activeStt = activeStt;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getEditedDate() {
		return editedDate;
	}

	public void setEditedDate(Date editedDate) {
		this.editedDate = editedDate;
	}

	public Integer getFixFee() {
		return fixFee;
	}

	public void setFixFee(Integer fixFee) {
		this.fixFee = fixFee;
	}

	public Float getFixRate() {
		return fixRate;
	}

	public void setFixRate(Float fixRate) {
		this.fixRate = fixRate;
	}



	public List<TblInsuranceBanks> getInsuranceBanks() {
		return insuranceBanks;
	}



	public void setInsuranceBanks(List<TblInsuranceBanks> insuranceBanks) {
		this.insuranceBanks = insuranceBanks;
	}



	public String getInsuranceApi() {
		return insuranceApi;
	}



	public void setInsuranceApi(String insuranceApi) {
		this.insuranceApi = insuranceApi;
	}



	public String getAuthToken() {
		return authToken;
	}



	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}



	public String getApiAcc() {
		return apiAcc;
	}



	public void setApiAcc(String apiAcc) {
		this.apiAcc = apiAcc;
	}



	public String getApiPass() {
		return apiPass;
	}



	public void setApiPass(String apiPass) {
		this.apiPass = apiPass;
	}



	public String getAuthTokenExpires() {
		return authTokenExpires;
	}



	public void setAuthTokenExpires(String authTokenExpires) {
		this.authTokenExpires = authTokenExpires;
	}



	public Integer getInsurancedefault() {
		return insurancedefault;
	}



	public void setInsurancedefault(Integer insurancedefault) {
		this.insurancedefault = insurancedefault;
	}

	
}
