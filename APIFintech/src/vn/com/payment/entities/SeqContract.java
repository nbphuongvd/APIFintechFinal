package vn.com.payment.entities;
// Generated May 23, 2021 7:04:28 PM by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SeqContract generated by hbm2java
 */
@Entity
@Table(name = "seq_contract", catalog = "db_fintech")
public class SeqContract implements java.io.Serializable {

	private Integer rowId;

	public SeqContract() {
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

}
