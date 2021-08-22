package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ReqSettlement {
	public String username;
	public String token;
	public String loan_code;
	public String real_payment_date;
	public long	  pay_amount;
	public String memo;
	public String action;
	public long	  latest_amt_to_decr_your_loan; //Gốc kỳ gần nhất
	public long	  settle_fee;					//Phí settle
	public long	  interest_till_now;			//Lãi phát sinh đến ngày thanh toán

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
	public long getLatest_amt_to_decr_your_loan() {
		return latest_amt_to_decr_your_loan;
	}
	public void setLatest_amt_to_decr_your_loan(long latest_amt_to_decr_your_loan) {
		this.latest_amt_to_decr_your_loan = latest_amt_to_decr_your_loan;
	}
	public long getSettle_fee() {
		return settle_fee;
	}
	public void setSettle_fee(long settle_fee) {
		this.settle_fee = settle_fee;
	}
	public long getInterest_till_now() {
		return interest_till_now;
	}
	public void setInterest_till_now(long interest_till_now) {
		this.interest_till_now = interest_till_now;
	}
	
}

