package vn.com.payment.object;

import com.google.gson.Gson;

import vn.com.payment.entities.Account;

public class ResUserInfo {
	public long status;
	public String permission;
	public String message;
	public Account account;
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

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}
