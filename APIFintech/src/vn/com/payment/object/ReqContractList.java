package vn.com.payment.object;

import java.util.List;

import org.json.JSONArray;

import com.google.gson.Gson;

import vn.com.payment.entities.TblRateConfig;

public class ReqContractList {
	
	public String username;
	public String token;
	public String from_date;
	public String to_date;
	public String calculate_profit_type; 	// [1: du no. giam dan;2:tat toan cuoi ky]
	public List<String> final_status;		// trạng thái các hồ sơ muốn tìm kiếm(=-1 nếu muốn search all status)
	public List<String> insurance_status;	// trạng thái bảo hiểm
	public String loan_code;				// ma hop dong
	public String id_number;				// cmt/ho chieu
	public String borrower_name;			// tên khách hàng
	public String branch_id;				// id chi nhánh	
	public String room_id;					// id phong
	public String limit;
	public String offSet;

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

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	
	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public String getCalculate_profit_type() {
		return calculate_profit_type;
	}

	public void setCalculate_profit_type(String calculate_profit_type) {
		this.calculate_profit_type = calculate_profit_type;
	}
//
//	public String getFinal_status() {
//		return final_status;
//	}
//
//	public void setFinal_status(String final_status) {
//		this.final_status = final_status;
//	}
	
	

	public String getLoan_code() {
		return loan_code;
	}

//	public JSONArray getFinal_status() {
//		return final_status;
//	}
//
//	public void setFinal_status(JSONArray final_status) {
//		this.final_status = final_status;
//	}

	
	
	public void setLoan_code(String loan_code) {
		this.loan_code = loan_code;
	}

	public List<String> getFinal_status() {
		return final_status;
	}

	public void setFinal_status(List<String> final_status) {
		this.final_status = final_status;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public String getBorrower_name() {
		return borrower_name;
	}

	public void setBorrower_name(String borrower_name) {
		this.borrower_name = borrower_name;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getOffSet() {
		return offSet;
	}

	public void setOffSet(String offSet) {
		this.offSet = offSet;
	}

	public List<String> getInsurance_status() {
		return insurance_status;
	}

	public void setInsurance_status(List<String> insurance_status) {
		this.insurance_status = insurance_status;
	}

	public String getRoom_id() {
		return room_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	
}
