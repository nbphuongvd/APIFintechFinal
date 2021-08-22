package vn.com.payment.entities;
// Generated May 17, 2021 11:02:56 PM by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GroupMapPer generated by hbm2java
 */
@Entity
@Table(name = "group_map_per", catalog = "db_fintech")
public class GroupMapPer implements java.io.Serializable {

	private Integer rowId;
	private int groupId;
	private int subPermissionId;
	private int viewPermission;
	private int editPermission;
	private int createPermission;
	private int deletePermission;

	public GroupMapPer() {
	}

	public GroupMapPer(int groupId, int subPermissionId, int viewPermission, int editPermission, int createPermission,
			int deletePermission) {
		this.groupId = groupId;
		this.subPermissionId = subPermissionId;
		this.viewPermission = viewPermission;
		this.editPermission = editPermission;
		this.createPermission = createPermission;
		this.deletePermission = deletePermission;
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

	@Column(name = "group_id", nullable = false)
	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	@Column(name = "sub_permission_id", nullable = false)
	public int getSubPermissionId() {
		return this.subPermissionId;
	}

	public void setSubPermissionId(int subPermissionId) {
		this.subPermissionId = subPermissionId;
	}

	@Column(name = "view_permission", nullable = false)
	public int getViewPermission() {
		return this.viewPermission;
	}

	public void setViewPermission(int viewPermission) {
		this.viewPermission = viewPermission;
	}

	@Column(name = "edit_permission", nullable = false)
	public int getEditPermission() {
		return this.editPermission;
	}

	public void setEditPermission(int editPermission) {
		this.editPermission = editPermission;
	}

	@Column(name = "create_permission", nullable = false)
	public int getCreatePermission() {
		return this.createPermission;
	}

	public void setCreatePermission(int createPermission) {
		this.createPermission = createPermission;
	}

	@Column(name = "delete_permission", nullable = false)
	public int getDeletePermission() {
		return this.deletePermission;
	}

	public void setDeletePermission(int deletePermission) {
		this.deletePermission = deletePermission;
	}

}
