package vn.com.payment.home;
// Generated May 19, 2021 11:20:56 PM by Hibernate Tools 3.5.0.Final

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
//import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import vn.com.payment.config.LogType;
//import vn.com.payment.entities.SubPermission;
import vn.com.payment.entities.TblBanks;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class TblBanks.
 * @see vn.com.payment.entities.TblBanks
 * @author Hibernate Tools
 */
@Stateless
public class TblBanksHome extends BaseSqlHomeDao{

	@PersistenceContext
	private EntityManager entityManager;

	public List<TblBanks> getTblBanks(int status, int bankSupport) {
		List<TblBanks> results = new ArrayList<>();
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblBanks.class);
			Criterion statusActive 			= Restrictions.eq("status", status);
			Criterion bankSupportFunction 	= Restrictions.in("bankSupportFunction", bankSupport);
			crtProduct.add(statusActive);
			crtProduct.add(bankSupportFunction);
//			crtProduct.addOrder(Order.asc("permissionId"));
			results = crtProduct.list();
			return results;
		} catch (Exception e) {
			FileLogger.log(" getTblBanks Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
}
