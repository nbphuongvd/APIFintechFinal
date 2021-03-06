package vn.com.payment.home;
// Generated May 17, 2021 11:02:56 PM by Hibernate Tools 3.5.0.Final

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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import vn.com.payment.entities.GroupMapPer;
import vn.com.payment.entities.TblRateConfig;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class GroupMapPer.
 * @see vn.com.payment.entities.GroupMapPer
 * @author Hibernate Tools
 */
@Stateless
public class GroupMapPerHome extends BaseSqlHomeDao{

	private FileLogger log = new FileLogger(GroupMapPerHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public List<GroupMapPer> getGroupMapPer(int roleID) {
		List<GroupMapPer> results = new ArrayList<>();
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(GroupMapPer.class);
			Criterion groupId 				= Restrictions.eq("groupId", roleID);
			crtProduct.add(groupId);
			crtProduct.addOrder(Order.asc("rowId"));
			results = crtProduct.list();
			return results;
		} catch (Exception e) {
			log.fatal(" getGroupMapPer Exception ", e);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		GroupMapPerHome groupMapPerHome = new GroupMapPerHome();
		List<GroupMapPer> results = groupMapPerHome.getGroupMapPer(4);
		for (GroupMapPer groupMapPer : results) {
			System.out.println(groupMapPer.getSubPermissionId());
		}
	}
}
