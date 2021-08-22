package vn.com.payment.object;

import java.util.List;
import com.google.gson.Gson;

public class ReqInbox {
	public String username;
	public String token;	
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
	
}
