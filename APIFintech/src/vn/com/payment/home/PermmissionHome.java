package vn.com.payment.home;
// Generated 18-May-2021 10:34:34 by Hibernate Tools 3.5.0.Final

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
import vn.com.payment.entities.Permmission;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class Permmission.
 * @see vn.com.payment.entities.Permmission
 * @author Hibernate Tools
 */
@Stateless
public class PermmissionHome extends BaseSqlHomeDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	public Permmission getPermmission(int rowId) {
		List<Permmission> results = new ArrayList<>();
		Session session = null;
		Permmission permmission = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(Permmission.class);
			Criterion rowIdDB 				= Restrictions.eq("rowId", rowId);
			crtProduct.add(rowIdDB);
			results = crtProduct.list();
			if (results.size() > 0) {
				permmission = results.get(0);
			}
			return permmission;
		} catch (Exception e) {
			FileLogger.log(" getSubPermissionid Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
}
