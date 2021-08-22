package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ReqPayment {
	public String username;
	public String token;
	public String loan_code;
	public String bill_index;
	public String real_payment_date;
	public long	  pay_amount;
	public String is_a_special_payment;
	public String memo;
	public String action;
	public long	  real_amt_to_decr_your_loan;
	public long	  real_monthly_interest;
	public long	  real_advisory_fee;
	public long	  real_manage_fee;
	public long	  real_over_due_fee;
	public long	  real_late_pay_fee;
//	"real_amt_to_decr_your_loan":"tien gốc",
//	"real_monthly_interest":"tien lãi",
//	"real_advisory_fee":"tien fee tu vấn",
//	"real_manage_fee":"tien fee dich vu",
//	"real_over_due_fee":"phi qua han",
//	"real_late_pay_fee":phi tra no truoc han
	
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
	public String getBill_index() {
		return bill_index;
	}
	public void setBill_index(String bill_index) {
		this.bill_index = bill_index;
	}
	public String getReal_payment_date() {
		return real_payment_date;
	}
	public void setReal_payment_date(String real_payment_date) {
		this.real_payment_date = real_payment_date;
	}
	public long getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(long pay_amount) {
		this.pay_amount = pay_amount;
	}
	public String getIs_a_special_payment() {
		return is_a_special_payment;
	}
	public void setIs_a_special_payment(String is_a_special_payment) {
		this.is_a_special_payment = is_a_special_payment;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<ObjImage> getImages() {
		return images;
	}
	public void setImages(List<ObjImage> images) {
		this.images = images;
	}
	public long getReal_amt_to_decr_your_loan() {
		return real_amt_to_decr_your_loan;
	}
	public void setReal_amt_to_decr_your_loan(long real_amt_to_decr_your_loan) {
		this.real_amt_to_decr_your_loan = real_amt_to_decr_your_loan;
	}
	public long getReal_monthly_interest() {
		return real_monthly_interest;
	}
	public void setReal_monthly_interest(long real_monthly_interest) {
		this.real_monthly_interest = real_monthly_interest;
	}
	public long getReal_advisory_fee() {
		return real_advisory_fee;
	}
	public void setReal_advisory_fee(long real_advisory_fee) {
		this.real_advisory_fee = real_advisory_fee;
	}
	public long getReal_manage_fee() {
		return real_manage_fee;
	}
	public void setReal_manage_fee(long real_manage_fee) {
		this.real_manage_fee = real_manage_fee;
	}
	public long getReal_over_due_fee() {
		return real_over_due_fee;
	}
	public void setReal_over_due_fee(long real_over_due_fee) {
		this.real_over_due_fee = real_over_due_fee;
	}
	public long getReal_late_pay_fee() {
		return real_late_pay_fee;
	}
	public void setReal_late_pay_fee(long real_late_pay_fee) {
		this.real_late_pay_fee = real_late_pay_fee;
	}
}

