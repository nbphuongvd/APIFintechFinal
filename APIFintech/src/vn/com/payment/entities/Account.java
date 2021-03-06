package vn.com.payment.entities;
// Generated 13-May-2021 01:14:35 by Hibernate Tools 3.5.0.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Account generated by hbm2java
 */
@Entity
@Table(name = "account")
public class Account implements java.io.Serializable {

	private Integer rowId;
	private String UName;
	private String password;
	private String firstName;
	private String lastName;
	private String gender;
	private String phone;
	private String email;
	private int status;
	private Date createdDate;
	private String rolesId;
	private String branchId;
	private int type;
	private int requireChangePass;

	public Account() {
	}

	public Account(String UName, String password, String firstName, String lastName, String gender, String phone,
			String email, int status, Date createdDate, String rolesId, String branchId, int type,
			int requireChangePass) {
		this.UName = UName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.status = status;
		this.createdDate = createdDate;
		this.rolesId = rolesId;
		this.branchId = branchId;
		this.type = type;
		this.requireChangePass = requireChangePass;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "row_id", unique = true, nullable = false)
	public Integer getRowId() {
		return this.rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	@Column(name = "u_name", nullable = false, length = 100)
	public String getUName() {
		return this.UName;
	}

	public void setUName(String UName) {
		this.UName = UName;
	}

	@Column(name = "password", nullable = false, length = 500)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "first_name", nullable = false, length = 100)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", nullable = false, length = 100)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "gender", nullable = false, length = 20)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "phone", nullable = false, length = 15)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email", nullable = false, length = 500)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "roles_id", nullable = false, length = 50)
	public String getRolesId() {
		return this.rolesId;
	}

	public void setRolesId(String rolesId) {
		this.rolesId = rolesId;
	}

	@Column(name = "branch_id", nullable = false, length = 200)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "type", nullable = false)
	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "require_change_pass", nullable = false)
	public int getRequireChangePass() {
		return this.requireChangePass;
	}

	public void setRequireChangePass(int requireChangePass) {
		this.requireChangePass = requireChangePass;
	}

}
