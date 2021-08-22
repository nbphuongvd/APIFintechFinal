package vn.com.payment.object;

import com.google.gson.Gson;

public class ObjInbox {
	public Integer inbox_id;
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
	public Integer getInbox_id() {
		return inbox_id;
	}
	public void setInbox_id(Integer inbox_id) {
		this.inbox_id = inbox_id;
	}
	
}
