package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

import vn.com.payment.entities.TblInbox;

public class ResInbox {
	public long status;
	public String message;
	public long totalRecord;
	public long totalUInread;
	public List<TblInbox> inbox;
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
	public List<TblInbox> getInbox() {
		return inbox;
	}
	public void setInbox(List<TblInbox> inbox) {
		this.inbox = inbox;
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public long getTotalUInread() {
		return totalUInread;
	}
	public void setTotalUInread(long totalUInread) {
		this.totalUInread = totalUInread;
	}
	
}
