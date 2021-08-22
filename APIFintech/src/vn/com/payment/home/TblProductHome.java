package vn.com.payment.home;
// Generated 15-May-2021 09:07:14 by Hibernate Tools 3.5.0.Final

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class TblProduct.
 * @see vn.com.payment.entities.TblProduct
 * @author Hibernate Tools
 */
@Stateless
public class TblProductHome extends BaseSqlHomeDao{

//	private static final Log log = LogFactory.getLog(TblProductHome.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	public TblProduct getProduct(String product_type, String product_brand, String product_modal) {
		TblProduct tblProduct = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 	= session.createCriteria(TblProduct.class);
			Criterion type 			= Restrictions.eq("productType", new Integer(product_type));
			Criterion brand 		= Restrictions.eq("productName", product_brand);
			Criterion modal			= Restrictions.eq("productCode", product_modal);
//			Criterion total 		= Restrictions.eq("total_run", total_run);
//			Criterion condition 	= Restrictions.eq("product_condition", product_condition);
//			Criterion borrower 		= Restrictions.eq("product_own_by_borrower", product_own_by_borrower);
//			Criterion serial 		= Restrictions.eq("product_serial_no", product_serial_no);
			crtProduct.add(type);
			crtProduct.add(brand);
			crtProduct.add(modal);
//			crtProduct.add(total);
//			crtProduct.add(condition);
//			crtProduct.add(borrower);
//			crtProduct.add(serial);
			// listTrans.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<TblProduct> listProduct = crtProduct.list();
			if (listProduct.size() > 0) {
				tblProduct = listProduct.get(0);
			}
		} catch (Exception e) {
			FileLogger.log(" getProduct Exception "+ e, LogType.ERROR);
			throw e;
		} finally {
			releaseSession(session);
		}
		return tblProduct;
	}
	
	public static void main(String[] args) {
		TblProductHome tblProductHome = new TblProductHome();
		TblProduct tblProduct = tblProductHome.getProduct("2", "Jupiter", "JU 2015");
		System.out.println(tblProduct.getProductCode());
	}
}
