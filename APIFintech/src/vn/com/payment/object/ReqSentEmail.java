package vn.com.payment.object;

import com.google.gson.Gson;

public class ReqSentEmail {
	public String username;
	public String token;
	public String loan_code;				// ma hop dong
	public String bill_index;

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
	
}
