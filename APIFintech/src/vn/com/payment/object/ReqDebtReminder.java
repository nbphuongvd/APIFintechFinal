package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ReqDebtReminder {
	public String username;
	public String token;
	public String from_date;
	public String to_date;
	public String bill_from_date;
	public String bill_to_date;
	public List<String> final_status;		// trạng thái các hồ sơ muốn tìm kiếm(=-1 nếu muốn search all status)
	public String loan_code;				// ma hop dong
	public String id_number;				// cmt/ho chieu
	public String borrower_name;			// tên khách hàng
	public List<String> branch_id;				// id chi nhánh	
	public List<String> room_id;					// id phong
	public String bill_payment_status;
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

	public List<String> getFinal_status() {
		return final_status;
	}

	public void setFinal_status(List<String> final_status) {
		this.final_status = final_status;
	}

	public String getLoan_code() {
		return loan_code;
	}

	public void setLoan_code(String loan_code) {
		this.loan_code = loan_code;
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

//	public String getBranch_id() {
//		return branch_id;
//	}
//
//	public void setBranch_id(String branch_id) {
//		this.branch_id = branch_id;
//	}
//
//	public String getRoom_id() {
//		return room_id;
//	}
//
//	public void setRoom_id(String room_id) {
//		this.room_id = room_id;
//	}
	

	public String getBill_payment_status() {
		return bill_payment_status;
	}

	public List<String> getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(List<String> branch_id) {
		this.branch_id = branch_id;
	}

	public List<String> getRoom_id() {
		return room_id;
	}

	public void setRoom_id(List<String> room_id) {
		this.room_id = room_id;
	}

	public void setBill_payment_status(String bill_payment_status) {
		this.bill_payment_status = bill_payment_status;
	}

	public String getBill_from_date() {
		return bill_from_date;
	}

	public void setBill_from_date(String bill_from_date) {
		this.bill_from_date = bill_from_date;
	}

	public String getBill_to_date() {
		return bill_to_date;
	}

	public void setBill_to_date(String bill_to_date) {
		this.bill_to_date = bill_to_date;
	}
	
}
