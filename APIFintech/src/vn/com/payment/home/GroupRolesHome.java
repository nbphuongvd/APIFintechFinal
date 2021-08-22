package vn.com.payment.home;
// Generated May 18, 2021 12:51:34 AM by Hibernate Tools 3.5.0.Final

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.GroupRoles;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class GroupRoles.
 * @see vn.com.payment.entities.GroupRoles
 * @author Hibernate Tools
 */
@Stateless
public class GroupRolesHome extends BaseSqlHomeDao{

	@PersistenceContext
	private EntityManager entityManager;

	public GroupRoles getGroupRoles(int roleID) {
//		List<GroupMapPer> results = new ArrayList<>();
		GroupRoles groupRoles = null;
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(GroupRoles.class);
			Criterion rowId 				= Restrictions.eq("rowId", roleID);
			crtProduct.add(rowId);
			@SuppressWarnings("unchecked")
			List<GroupRoles> listGroupRoles = crtProduct.list();
			if (listGroupRoles.size() > 0) {
				groupRoles = listGroupRoles.get(0);
			}
			return groupRoles;
		} catch (Exception e) {
			FileLogger.log(" getGroupMapPer Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	public static void main(String[] args) {
		GroupRolesHome groupRolesHome = new GroupRolesHome();
		GroupRoles getGroupRoles = groupRolesHome.getGroupRoles(9);
		System.out.println(getGroupRoles.getName());
	}
}
