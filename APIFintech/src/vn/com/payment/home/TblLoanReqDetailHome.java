package vn.com.payment.home;
// Generated May 16, 2021 2:41:23 PM by Hibernate Tools 3.5.0.Final
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class TblLoanReqDetail.
 * @see vn.com.payment.entities.TblLoanReqDetail
 * @author Hibernate Tools
 */
@Stateless
public class TblLoanReqDetailHome extends BaseSqlHomeDao{

	@PersistenceContext
	private EntityManager entityManager;

	public boolean createLoanReqDetail(TblLoanReqDetail tblLoanReqDetail) {
		try {
			save(tblLoanReqDetail);
			System.out.println("id: " + tblLoanReqDetail.getLoanId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createLoanReqDetail Exception "+ e, LogType.ERROR);
		}
		return false;
	}

	public boolean updateLoanReqDetail(TblLoanReqDetail tblLoanReqDetail) {
		try {
			updateObj(tblLoanReqDetail, tblLoanReqDetail.getLoanId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("updateLoanReqDetail Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	
	
	public static void main(String[] args) {
//		TblLoanReqDetailHome tblLoanReqDetailHome = new TblLoanReqDetailHome();
//		BigInteger aa = tblLoanReqDetailHome.getCountVA();
//		System.out.println(aa);
	}
}
