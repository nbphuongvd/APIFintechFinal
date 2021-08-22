package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ReqUpdateInsurance {

	public String username;
	public String token;
	public String loan_code;
	public String action;
	public String memo;
	public String insurance_status; //một trong các trạng thái: 117	Đã giải ngân bảo hiểm/Chờ lấy chứng thư bảo hiểm(Đang vay), 118	Đã gửi hồ sơ sang bảo hiểm/Chờ lấy chứng thư bảo hiểm(Đang vay), 119	Đã lấy chứng thư bảo hiểm(Đang vay)
	public String insurance_paid_amount; 
	public String insurance_fee_amt; 
	public String insurance_paid_amt_fr_api; 
	public String insurance_fee_fr_api; 
	public String partner_insurance_code; 
	public String partner_insurance_id; 
	public List<ObjImage> images;
	public String toJSON(){
		String json	=	"";
		try {
			Gson gson = new Gson();
			json	=	gson.toJson(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLoan_code() {
		return loan_code;
	}
	public void setLoan_code(String loan_code) {
		this.loan_code = loan_code;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getInsurance_status() {
		return insurance_status;
	}
	public void setInsurance_status(String insurance_status) {
		this.insurance_status = insurance_status;
	}
	public List<ObjImage> getImages() {
		return images;
	}
	public void setImages(List<ObjImage> images) {
		this.images = images;
	}
	public String getInsurance_paid_amount() {
		return insurance_paid_amount;
	}
	public void setInsurance_paid_amount(String insurance_paid_amount) {
		this.insurance_paid_amount = insurance_paid_amount;
	}
	public String getInsurance_fee_amt() {
		return insurance_fee_amt;
	}
	public void setInsurance_fee_amt(String insurance_fee_amt) {
		this.insurance_fee_amt = insurance_fee_amt;
	}
	public String getInsurance_paid_amt_fr_api() {
		return insurance_paid_amt_fr_api;
	}
	public void setInsurance_paid_amt_fr_api(String insurance_paid_amt_fr_api) {
		this.insurance_paid_amt_fr_api = insurance_paid_amt_fr_api;
	}
	public String getInsurance_fee_fr_api() {
		return insurance_fee_fr_api;
	}
	public void setInsurance_fee_fr_api(String insurance_fee_fr_api) {
		this.insurance_fee_fr_api = insurance_fee_fr_api;
	}
	public String getPartner_insurance_code() {
		return partner_insurance_code;
	}
	public void setPartner_insurance_code(String partner_insurance_code) {
		this.partner_insurance_code = partner_insurance_code;
	}
	public String getPartner_insurance_id() {
		return partner_insurance_id;
	}
	public void setPartner_insurance_id(String partner_insurance_id) {
		this.partner_insurance_id = partner_insurance_id;
	}
	
}
