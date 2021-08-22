package vn.com.payment.home;
// Generated May 16, 2021 10:01:24 AM by Hibernate Tools 3.5.0.Final

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.TblRateConfig;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class TblRateConfig.
 * @see vn.com.payment.entities.TblRateConfig
 * @author Hibernate Tools
 */
@Stateless
public class TblRateConfigHome extends BaseSqlHomeDao{

	@PersistenceContext
	private EntityManager entityManager;
	public List<TblRateConfig> getRateConfig(int type) {
		List<TblRateConfig> results = new ArrayList<>();
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblRateConfig.class);
			Criterion active_status 		= Restrictions.eq("activeStatus", new Integer(1));
			Criterion rateType 				= Restrictions.eq("rateType", type);
			crtProduct.add(active_status);
			crtProduct.add(rateType);
			results = crtProduct.list();
			return results;
		} catch (Exception e) {
			FileLogger.log(" getProduct Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public static void main(String[] args) {
		TblRateConfigHome tblRateConfigHome = new TblRateConfigHome();
		List<TblRateConfig> results = tblRateConfigHome.getRateConfig(2);
		System.out.println(results);
		for (TblRateConfig tblRateConfig : results) {
			System.out.println(tblRateConfig.getRateName());
		}
		
//		List<AllAppModel> lstExtrextData = new ArrayList<>();
	}
}
