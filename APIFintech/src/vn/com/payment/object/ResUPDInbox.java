package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

import vn.com.payment.entities.TblInbox;

public class ResUPDInbox {
	public long status;
	public String message;
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
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
