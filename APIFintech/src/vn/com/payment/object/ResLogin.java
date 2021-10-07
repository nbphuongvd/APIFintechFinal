package vn.com.payment.object;

import com.google.gson.Gson;

public class ResLogin {
	public long status;
	public String token;
	public int require_change_pass;
	public String permission;
	public String message;
	public String full_name;
	public String branch_id;
	public String roles_id;
	public String row_id;
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

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public int getRequire_change_pass() {
		return require_change_pass;
	}

	public void setRequire_change_pass(int require_change_pass) {
		this.require_change_pass = require_change_pass;
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

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public String getRoles_id() {
		return roles_id;
	}

	public void setRoles_id(String roles_id) {
		this.roles_id = roles_id;
	}

	public String getRow_id() {
		return row_id;
	}

	public void setRow_id(String row_id) {
		this.row_id = row_id;
	}
	
}
