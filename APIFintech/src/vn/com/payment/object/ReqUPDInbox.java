package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ReqUPDInbox {
	public String username;
	public String token;
	public List<ObjInbox> inbox;
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
	public List<ObjInbox> getInbox() {
		return inbox;
	}
	public void setInbox(List<ObjInbox> inbox) {
		this.inbox = inbox;
	}
	
}
