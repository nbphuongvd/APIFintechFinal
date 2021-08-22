package vn.com.payment.object;

import com.google.gson.Gson;

public class ResContractList {
	public long loan_id;
	public String loan_code;
	public String loan_name;
	public String id_number;
	public long borrower_phone;
	public String product_name;
	public long approved_amount; 
	public long final_status; 
	public String created_date; 
	public String borrower_fullname; 
	public String branch_id; 
	public String branch_name; 
	public String room_code; // phong giao dich
	public long extend_status; // trạng thái phu
	public long insurance_status; // trạng thái phu
	public long insurance_paid_amount; // trạng thái phu
	
	public long partner_insurance_code; // ma~ hop dong partner tra? ve
	public long partner_insurance_id; // id hop. dong do partner tra ve khi call api

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
	
	public long getExtend_status() {
		return extend_status;
	}

	public void setExtend_status(long extend_status) {
		this.extend_status = extend_status;
	}

	public long getLoan_id() {
		return loan_id;
	}

	public void setLoan_id(long loan_id) {
		this.loan_id = loan_id;
	}

	public String getLoan_code() {
		return loan_code;
	}

	public void setLoan_code(String loan_code) {
		this.loan_code = loan_code;
	}

	public String getLoan_name() {
		return loan_name;
	}

	public void setLoan_name(String loan_name) {
		this.loan_name = loan_name;
	}
	
	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public long getBorrower_phone() {
		return borrower_phone;
	}

	public void setBorrower_phone(long borrower_phone) {
		this.borrower_phone = borrower_phone;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public long getApproved_amount() {
		return approved_amount;
	}

	public void setApproved_amount(long approved_amount) {
		this.approved_amount = approved_amount;
	}

	public long getFinal_status() {
		return final_status;
	}

	public void setFinal_status(long final_status) {
		this.final_status = final_status;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getBorrower_fullname() {
		return borrower_fullname;
	}

	public void setBorrower_fullname(String borrower_fullname) {
		this.borrower_fullname = borrower_fullname;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public String getRoom_code() {
		return room_code;
	}

	public void setRoom_code(String room_code) {
		this.room_code = room_code;
	}

	public long getInsurance_status() {
		return insurance_status;
	}

	public void setInsurance_status(long insurance_status) {
		this.insurance_status = insurance_status;
	}

	public long getInsurance_paid_amount() {
		return insurance_paid_amount;
	}

	public void setInsurance_paid_amount(long insurance_paid_amount) {
		this.insurance_paid_amount = insurance_paid_amount;
	}

	public long getPartner_insurance_code() {
		return partner_insurance_code;
	}

	public void setPartner_insurance_code(long partner_insurance_code) {
		this.partner_insurance_code = partner_insurance_code;
	}

	public long getPartner_insurance_id() {
		return partner_insurance_id;
	}

	public void setPartner_insurance_id(long partner_insurance_id) {
		this.partner_insurance_id = partner_insurance_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	
}
//"loan_code":			tbl_loan_request
//"loan_code":			tbl_loan_request
//"id_number": 			tbl_borrower
//"borrower_phone":  	tbl_loan_req_detail
//"product_name":   -   	branch_id tbl_brand
//"approved_amount":		tbl_loan_req_detail
//"final_status":		tbl_loan_req_detail
//"created_date":		tbl_loan_req_detail
//"borrower_fullname":	tbl_loan_req_detail
//phong giao dichj nao