package vn.com.payment.home;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.mongodb.DB;

import ch.qos.logback.core.net.SyslogOutputStream;
import vn.com.payment.config.LogType;
import vn.com.payment.entities.TblBlackList;
import vn.com.payment.entities.TblBorrower;
import vn.com.payment.entities.TblDebtRemindHistory;
import vn.com.payment.entities.TblImages;
import vn.com.payment.entities.TblInbox;
import vn.com.payment.entities.TblInsuranceBanks;
import vn.com.payment.entities.TblInsuranceProviders;
import vn.com.payment.entities.TblLoanBill;
import vn.com.payment.entities.TblLoanExpertiseSteps;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.entities.TblLoanRequestAskAns;
import vn.com.payment.entities.TblLoanSponsorMapp;
import vn.com.payment.entities.TblSponsor;
import vn.com.payment.entities.TblSystemActions;
import vn.com.payment.object.InsuranceProviders;
import vn.com.payment.object.ObjDebtReminderDetail;
import vn.com.payment.object.ResContractList;
import vn.com.payment.object.ResContractListSponsor;
import vn.com.payment.ultities.FileLogger;
import vn.com.payment.ultities.ValidData;

public class DBFintechHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(DBFintechHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	Gson gson = new Gson();
	
	public boolean createTblDebtRemindHistory(TblDebtRemindHistory TblDebtRemindHistory) {
		try {
			FileLogger.log("createTblDebtRemindHistory "+ gson.toJson(TblDebtRemindHistory), LogType.BUSSINESS);
			save(TblDebtRemindHistory);
			FileLogger.log("createTblDebtRemindHistory id: "+ TblDebtRemindHistory.getRowId(), LogType.BUSSINESS);
			System.out.println("id: " + TblDebtRemindHistory.getRowId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createTblDebtRemindHistory Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	
	public boolean createSystemActions(TblSystemActions tblSystemActions) {
		try {
			FileLogger.log("createSystemActions "+ gson.toJson(tblSystemActions), LogType.BUSSINESS);
			save(tblSystemActions);
			FileLogger.log("createSystemActions id: "+ tblSystemActions.getActionId(), LogType.BUSSINESS);
			System.out.println("id: " + tblSystemActions.getActionId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createSystemActions Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean createExpertiseSteps(TblLoanExpertiseSteps tblLoanExpertiseSteps) {
		try {
			FileLogger.log("createExpertiseSteps "+ gson.toJson(tblLoanExpertiseSteps), LogType.BUSSINESS);
			save(tblLoanExpertiseSteps);
			FileLogger.log("createExpertiseSteps id: "+ tblLoanExpertiseSteps.getLoanExpertiseId(), LogType.BUSSINESS);
			System.out.println("id: " + tblLoanExpertiseSteps.getLoanExpertiseId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean createTblLoanSponsorMapp(TblLoanSponsorMapp tblLoanSponsorMapp) {
		try {
			FileLogger.log("createTblLoanSponsorMapp "+ gson.toJson(tblLoanSponsorMapp), LogType.BUSSINESS);
			save(tblLoanSponsorMapp);
			FileLogger.log("createTblLoanSponsorMapp id: "+ tblLoanSponsorMapp.getSponsorId(), LogType.BUSSINESS);
			System.out.println("id: " + tblLoanSponsorMapp.getSponsorId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createTblLoanSponsorMapp Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean createTblBorrower(TblBorrower tblBorrower) {
		try {
			FileLogger.log("createTblBorrower "+ gson.toJson(tblBorrower), LogType.BUSSINESS);
			save(tblBorrower);
			FileLogger.log("createTblBorrower id: "+ tblBorrower.getBorrowerId(), LogType.BUSSINESS);
			System.out.println("id: " + tblBorrower.getBorrowerId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createTblBorrower Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean updateExpertiseSteps(TblLoanExpertiseSteps TblLoanExpertiseSteps) {
		try {
			updateObj(TblLoanExpertiseSteps, TblLoanExpertiseSteps.getLoanExpertiseId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("updateExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}

	public boolean updateTblLoanBill(TblLoanBill tblLoanBill) {
		try {
			updateObj(tblLoanBill, tblLoanBill.getBillId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("updateExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean updateTblLoanSponsorMapp(TblLoanSponsorMapp tblLoanSponsorMapp) {
		try {
			updateObj(tblLoanSponsorMapp, tblLoanSponsorMapp.getLoanSponsorMappId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("updateExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean updateTblLoanRequest(TblLoanRequest tblLoanRequest) {
		try {
			updateObj(tblLoanRequest, tblLoanRequest.getLoanId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("updateExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}

	public List<ResContractList> listResContractList(List<Integer> branchID, List<Integer> roomID,
			String loan_code, List<Integer> final_status, List<Integer> insurance_status, String id_number, 
			String borrower_name, String from_date, String to_date, String calculate_profit_type, String limit, String offSet) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ResContractList> lisResContractList = new ArrayList<>();		
		String time = String.valueOf(10);
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT lr.loanCode, "
								+ "lr.loanName, ld.borrowerId, "
								+ "ld.borrowerPhone, ld.productBrand, "
								+ "ld.approvedAmount, lr.finalStatus, "
								+ "lr.createdDate, ld.borrowerFullname, "
								+ "tr.roomName, br.branchName, lr.loanId, lr.extendStatus, lr.insuranceStatus, lr.insurancePaidAmount, "
								+ "lr.partnerInsuranceCode, lr.partnerInsuranceId "
								+ "FROM TblLoanReqDetail ld "
								+ "LEFT JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "LEFT JOIN Branch br ON br.rowId in lr.branchId "
								+ "LEFT JOIN TransasctionRoom tr ON tr.rowId in lr.roomId "
								+ "Where "
								+ "lr.branchId in :listbranchId ";
			if(ValidData.checkNull(loan_code) == true){
				sql = sql + "and lr.loanCode =:loan_code ";
			}
			if(final_status.size() > 0){
				sql = sql + "and lr.finalStatus in :final_status ";
			}
			if(insurance_status.size() > 0){
				sql = sql + "and lr.insuranceStatus in :insurance_status ";
			}
			if(ValidData.checkNull(borrower_name) == true){
				sql = sql + "and ld.borrowerFullname =:borrower_name ";
			}
			if(ValidData.checkNull(from_date) == true){
				sql = sql + "and lr.createdDate >= :from_date ";
			}
			if(ValidData.checkNull(to_date) == true){
				sql = sql + "and lr.createdDate <= :to_date ";
			}
			if(ValidData.checkNull(calculate_profit_type) == true){
				sql = sql + "and lr.calculateProfitType =:calculate_profit_type ";
			}
			if(ValidData.checkNull(id_number) == true){
				sql = sql + "and ld.borrowerId =:id_number ";
			}
			if(roomID.size() > 0){
				sql = sql + "and lr.roomId in :listRoom ";
			}
			sql = sql + "ORDER BY lr.createdDate DESC";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);

			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
			if(final_status.size() > 0){
				query.setParameter("final_status", final_status);
			}	
			if(insurance_status.size() > 0){
				query.setParameter("insurance_status", insurance_status);
			}	
			if(ValidData.checkNull(borrower_name) == true){
				query.setParameter("borrower_name", borrower_name);
			}
			if(ValidData.checkNull(from_date) == true){
				Date fromDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(from_date + " 00:00:00");  
				query.setParameter("from_date", fromDate);
			}
			if(ValidData.checkNull(to_date) == true){
				Date toDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(to_date + " 23:59:59");  
				query.setParameter("to_date", toDate);
			}
			if(ValidData.checkNull(calculate_profit_type) == true){
				query.setParameter("calculate_profit_type", Integer.parseInt(calculate_profit_type));
			}
			if(ValidData.checkNull(id_number) == true){
				query.setParameter("id_number", id_number);
			}
			if(roomID.size() > 0){
				query.setParameter("listRoom", roomID);
			}
			query.setParameter("listbranchId", branchID);
			if(ValidData.checkNull(offSet) == true){
				query.setFirstResult(Integer.parseInt(offSet));
			}
			if(ValidData.checkNull(limit) == true){
				query.setMaxResults(Integer.parseInt(limit));
			}
			list = query.getResultList();
			
			System.out.println(list.size());
			for (int i = 0; i<  list.size(); i++){
				Object[] row = (Object[]) list.get(i);		
				ResContractList reContractList = new ResContractList();
				for (int j = 0; j < row.length; j++) {		
					try {
						reContractList.setLoan_code(row[0]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setLoan_name(row[1]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setId_number(row[2]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setBorrower_phone(Long.parseLong(row[3]+""));
					} catch (Exception e) {
					}
					try {
						reContractList.setProduct_name(row[4]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setApproved_amount(Long.parseLong(row[5]+""));
					} catch (Exception e) {
					}
					try {
						reContractList.setFinal_status(Long.parseLong(row[6]+""));
					} catch (Exception e) {
					}
					try {
						reContractList.setCreated_date(row[7]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setBorrower_fullname(row[8]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setRoom_code(row[9]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setBranch_id(row[10]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setLoan_id(Long.parseLong(row[11]+""));
					} catch (Exception e) {
					}
					try {
						reContractList.setExtend_status(Long.parseLong(row[12]+""));
					} catch (Exception e) {
					}
					try {
						reContractList.setInsurance_status(Long.parseLong(row[13]+""));
					} catch (Exception e) {
					}
					try {
						reContractList.setInsurance_paid_amount(Long.parseLong(row[14]+""));
					} catch (Exception e) {
					}
					try {
						reContractList.setPartner_insurance_code(Long.parseLong(row[15]+""));
					} catch (Exception e) {
					}
					try {
						reContractList.setPartner_insurance_id(Long.parseLong(row[16]+""));
					} catch (Exception e) {
					}
				}
				System.out.println("----------------------------------------------");
				lisResContractList.add(reContractList);
			}
			System.out.println(list.size());
			return lisResContractList;
		} catch (Exception e) {
			FileLogger.log(">> listResContractList err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public long listCountContractList(List<Integer> branchID, List<Integer> roomID,
			String loan_code, List<Integer> final_status, List<Integer> insurance_status, String id_number, 
			String borrower_name, String from_date, String to_date, String calculate_profit_type, String limit, String offSet) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ResContractList> lisResContractList = new ArrayList<>();		
		String time = String.valueOf(10);
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT count(lr.loanCode)"
								+ "FROM TblLoanReqDetail ld "
								+ "LEFT JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "LEFT JOIN Branch br ON br.rowId in lr.branchId "
								+ "LEFT JOIN TransasctionRoom tr ON tr.rowId in lr.roomId "
								+ "Where "
								+ "lr.branchId in :listbranchId ";
			if(ValidData.checkNull(loan_code) == true){
				sql = sql + "and lr.loanCode =:loan_code ";
			}
			if(final_status.size() > 0){
				sql = sql + "and lr.finalStatus in :final_status ";
			}
			if(insurance_status.size() > 0){
				sql = sql + "and lr.insuranceStatus in :insurance_status ";
			}
			if(ValidData.checkNull(borrower_name) == true){
				sql = sql + "and ld.borrowerFullname =:borrower_name ";
			}
			if(ValidData.checkNull(from_date) == true){
				sql = sql + "and lr.createdDate >= :from_date ";
			}
			if(ValidData.checkNull(to_date) == true){
				sql = sql + "and lr.createdDate <= :to_date ";
			}
			if(ValidData.checkNull(calculate_profit_type) == true){
				sql = sql + "and lr.calculateProfitType =:calculate_profit_type ";
			}
			if(ValidData.checkNull(id_number) == true){
				sql = sql + "and ld.borrowerId =:id_number ";
			}
			if(roomID.size() > 0){
				sql = sql + "and lr.roomId in :listRoom ";
			}
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);

			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
			if(final_status.size() > 0){
				query.setParameter("final_status", final_status);
			}
			if(insurance_status.size() > 0){
				query.setParameter("insurance_status", insurance_status);
			}
			if(ValidData.checkNull(borrower_name) == true){
				query.setParameter("borrower_name", borrower_name);
			}
			if(ValidData.checkNull(from_date) == true){
				Date fromDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(from_date + " 00:00:00");   
				query.setParameter("from_date", fromDate);
			}
			if(ValidData.checkNull(to_date) == true){
				Date toDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(to_date + " 23:59:59");
				query.setParameter("to_date", toDate);
			}
			if(ValidData.checkNull(calculate_profit_type) == true){
				query.setParameter("calculate_profit_type", Integer.parseInt(calculate_profit_type));
			}
			if(ValidData.checkNull(id_number) == true){
				query.setParameter("id_number", id_number);
			}
			if(roomID.size() > 0){
				query.setParameter("listRoom", roomID);
			}
			query.setParameter("listbranchId", branchID);
			list = query.getResultList();
			
			System.out.println(list.get(0));
			System.out.println(list.size());
			return (long)list.get(0);
		} catch (Exception e) {
			FileLogger.log(">> listResContractList err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return 0l;
	}
	
	public List<TblLoanExpertiseSteps> getLoanExpertiseSteps(int loan_id) {
		List<TblLoanExpertiseSteps> results = new ArrayList<>();
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanExpertiseSteps.class);
			Criterion getLoan 		= Restrictions.eq("loanId", loan_id);
			crtProduct.add(getLoan);
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
	
//	public boolean checkLoan11(List<Integer> branchID, List<Integer> roomID, String loanID) {
//		Session session = null;
//		Transaction tx = null;
//		List<Object> list = null;
//		List<ResContractList> lisResContractList = new ArrayList<>();		
//		String time = String.valueOf(10);
//		boolean result = false;
//		try {
//			session = HibernateUtil.getSessionFactory().openSession();
//			String sql = "SELECT loanCode FROM TblLoanRequest Where "
//								+ "loanId =:loanID and "
//								+ "roomId in :listRoom and "
//								+ "branchId in :listbranchId";
//			System.out.println("sql: "+ sql);
//			session = HibernateUtil.getSessionFactory().openSession();
//			Query query = session.createQuery(sql);
//			query.setParameter("loanCode", loanID);
//			query.setParameter("listRoom", roomID);
//			query.setParameter("listbranchId", branchID);
//			list = query.getResultList();
//			if(list.size() <= 0){
//				result = false;
//			}else{
//				result = true;
//			}
//			System.out.println(list.size());
//			return result;
//		} catch (Exception e) {
//			FileLogger.log(">> checkLoan err " + e.getMessage(),LogType.ERROR);
//			e.printStackTrace();
//		} finally {
//			releaseSession(session);
//		}
//		return false;
//	}
	
	
	public TblLoanRequest getLoan(List<Integer> branchID, List<Integer> roomID, String loanCode) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		TblLoanRequest tblLoanRequest = new TblLoanRequest();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "FROM TblLoanRequest Where "
								+ "loanCode =:loanCode "
								+ "and branchId in :listbranchId ";
			if(roomID.size() > 0){
				sql = sql + "and roomId in :listRoom  ";
			}
			System.out.println("sql: "+ sql);
			sql = sql + " ORDER BY createdDate DESC";
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanCode", loanCode);
			if(roomID.size() > 0){
				query.setParameter("listRoom", roomID);
			}
			query.setParameter("listbranchId", branchID);
			list = query.getResultList();
			if(list.size() > 0){
				tblLoanRequest = (TblLoanRequest) list.get(0);
				return tblLoanRequest;
			}
			System.out.println(list.size());
			
		} catch (Exception e) {
			FileLogger.log(">> checkLoan err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public TblLoanRequest getLoanCreaterBy(String createdBy, List<Integer> branchID, List<Integer> roomID, String loanCode) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		TblLoanRequest tblLoanRequest = new TblLoanRequest();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "FROM TblLoanRequest Where "
								+ "loanCode =:loanCode "
								+ "and createdBy =:createdBy "
								+ "and branchId in :listbranchId ";
			System.out.println("sql: "+ sql);
			if(roomID.size() > 0){
				sql = sql + "and roomId in :listRoom  ";
			}
			sql = sql + " ORDER BY createdDate DESC";
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanCode", loanCode);
			query.setParameter("createdBy", createdBy);
			if(roomID.size() > 0){
				query.setParameter("listRoom", roomID);
			}
			query.setParameter("listbranchId", branchID);
			list = query.getResultList();
			if(list.size() > 0){
				tblLoanRequest = (TblLoanRequest) list.get(0);
				return tblLoanRequest;
			}
			System.out.println(list.size());
			
		} catch (Exception e) {
			FileLogger.log(">> checkLoan err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	
	public TblLoanRequest getLoanBranchID(List<Integer> branchID, String loanCode) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		TblLoanRequest tblLoanRequest = new TblLoanRequest();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "FROM TblLoanRequest Where "
								+ "loanCode =:loanCode and "
								+ "branchId in :listbranchId";
			System.out.println("sql: "+ sql);
			sql = sql + " ORDER BY createdDate DESC";
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanCode", loanCode);
			query.setParameter("listbranchId", branchID);
			list = query.getResultList();
			if(list.size() > 0){
				tblLoanRequest = (TblLoanRequest) list.get(0);
				return tblLoanRequest;
			}
			System.out.println(list.size());
			
		} catch (Exception e) {
			FileLogger.log(">> checkLoan err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	public TblLoanRequest getLoanRoleNDT(String loanCode) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		TblLoanRequest tblLoanRequest = new TblLoanRequest();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "FROM TblLoanRequest Where "
								+ "loanCode =:loanCode";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			sql = sql + " ORDER BY createdDate DESC";
			Query query = session.createQuery(sql);
			query.setParameter("loanCode", loanCode);
//			query.setParameter("listRoom", roomID);
//			query.setParameter("listbranchId", branchID);
			list = query.getResultList();
			if(list.size() > 0){
				tblLoanRequest = (TblLoanRequest) list.get(0);
				return tblLoanRequest;
			}
			System.out.println(list.size());
			
		} catch (Exception e) {
			FileLogger.log(">> checkLoan err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	public boolean createTblLoanExpertiseSteps(TblLoanExpertiseSteps tblLoanExpertiseSteps) {
		try {
			save(tblLoanExpertiseSteps);
			System.out.println("id: " + tblLoanExpertiseSteps.getLoanExpertiseId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createTblLoanExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean createTblImages(TblImages tblImages) {
		try {
			save(tblImages);
			System.out.println("id: " + tblImages.getImageId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createTblLoanExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public TblLoanReqDetail getLoanDetail(int loan_id) {
		List<TblLoanReqDetail> results = new ArrayList<>();
		Session session = null;
		TblLoanReqDetail tblLoanReqDetail = new TblLoanReqDetail();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanReqDetail.class);
			Criterion getLoan 				= Restrictions.eq("loanId", loan_id);
			crtProduct.add(getLoan);
			results = crtProduct.list();
			if(results.size() > 0){
				tblLoanReqDetail = (TblLoanReqDetail) results.get(0);
				return tblLoanReqDetail;
			}
		} catch (Exception e) {
			FileLogger.log(" getLoanDetail Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	public List<TblImages> getTblImages11(int loan_request_detail_id) {
		List<TblImages> results = new ArrayList<>();
		Session session = null;
		TblImages tblImages = new TblImages();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblImages.class);
			Criterion getLoan 				= Restrictions.eq("loanRequestDetailId", loan_request_detail_id);
			crtProduct.add(getLoan);
			results = crtProduct.list();
//			if(results.size() > 0){
//				tblImages = (TblImages) results.get(0);
//			}
			return results;
		} catch (Exception e) {
			FileLogger.log(" getTblImages Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public List<TblImages> getTblImagesJoin(int loan_request_detail_id) {
		Session session = null;
		Transaction tx = null;
		List<TblImages> results = new ArrayList<>();		
		List<Object> list = null;
		try {
			String sql = "SELECT img.imageId, img.loanRequestDetailId, "
									+ "img.imageType, img.imageByte, img.imageUrl, img.imageIsFront, "
									+ "img.imageName, img.partnerImageId, img.imageStatus, img.deleteDate, img.imageInputName, "
									+ "acc.firstName, acc.lastName, img.createdDate, img.editedDate "											
									+ "FROM TblImages img "
									+ "INNER JOIN Account acc ON acc.email = img.uploadBy "
									+ "Where img.loanRequestDetailId =:loanReqID  ";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanReqID", loan_request_detail_id);
			list = query.getResultList();
			if(list.size() > 0){
				for (int i = 0; i<  list.size(); i++){
					Object[] row = (Object[]) list.get(i);		
					TblImages tblImages = new TblImages();	
					for (int j = 0; j < row.length; j++) {		
						try {
							tblImages.setImageId(Long.parseLong(row[0]+""));
						} catch (Exception e) {
						}
						try {
							tblImages.setLoanRequestDetailId(Integer.parseInt(row[1]+""));
						} catch (Exception e) {
						}
						try {
							tblImages.setImageType(Integer.parseInt(row[2]+""));
						} catch (Exception e) {
						}
						try {
							tblImages.setImageByte(row[3]+"");
						} catch (Exception e) {
						}
						try {
							tblImages.setImageUrl(row[4]+"");
						} catch (Exception e) {
						}
						try {
							tblImages.setImageIsFront(Integer.parseInt(row[5]+""));
						} catch (Exception e) {
						}
						try {
							tblImages.setImageName(row[6]+"");
						} catch (Exception e) {
						}
						try {
							tblImages.setPartnerImageId(row[7]+"");
						} catch (Exception e) {
						}
						try {
							tblImages.setImageStatus(Integer.parseInt(row[8]+""));
						} catch (Exception e) {
						}
						try {
							tblImages.setDeleteDate(new SimpleDateFormat("dd/MM/yyyy").parse(row[9]+""));
						} catch (Exception e) {
						}
						try {
							tblImages.setImageInputName(row[10]+"");
						} catch (Exception e) {
						}
						try {
							tblImages.setUploadBy(row[11]+" "+row[12]);
						} catch (Exception e) {
						}
						try {
//							System.out.println(row[13]);
							String date1 = row[13].toString().replace(".0", "");
							System.out.println("aa: " + date1);
							tblImages.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date1));
						} catch (Exception e) {
//							e.printStackTrace();
							tblImages.setCreatedDate(new Date());
						}
						try {
							String date2 = row[14].toString().replace(".0", "");
							tblImages.setEditedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date2));
						} catch (Exception e) {
//							e.printStackTrace();
							tblImages.setEditedDate(new Date());
						}
					}
					results.add(tblImages);
				}
				System.out.println("----------------------------------------------");
			}
			System.out.println(list.size());
			return results;
		} catch (Exception e) {
			FileLogger.log(">> getTblImagesJoin err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	
	
	public List<TblLoanRequestAskAns> getRequestAskAns(int loanID) {
		List<TblLoanRequestAskAns> results = new ArrayList<>();
		Session session = null;
		TblLoanRequestAskAns tblLoanRequestAskAns = new TblLoanRequestAskAns();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanRequestAskAns.class);
			Criterion getLoan 				= Restrictions.eq("loanId", loanID);
			crtProduct.add(getLoan);
			results = crtProduct.list();
//			if(results.size() > 0){
//				tblLoanRequestAskAns = (TblLoanRequestAskAns) results.get(0);
//			}
			return results;
		} catch (Exception e) {
			FileLogger.log(" getRequestAskAns Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public TblLoanRequest getTblLoanRequest(String loanCode) {
		List<TblLoanRequest> results = new ArrayList<>();
		Session session = null;
		TblLoanRequest tblLoanRequest = new TblLoanRequest();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanRequest.class);
			Criterion getLoan 				= Restrictions.eq("loanCode", loanCode);
			crtProduct.add(getLoan);
			results = crtProduct.list();
			if(results.size() > 0){
				tblLoanRequest = (TblLoanRequest) results.get(0);
			}
			return tblLoanRequest;
		} catch (Exception e) {
			FileLogger.log(" getTblLoanRequest Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	public List<ResContractListSponsor> listListSponsor(int sponsorID, String loan_code, List<Integer> final_status, String borrower_name, String from_date, String to_date, String calculate_profit_type, String limit, String offSet) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ResContractListSponsor> lisResContractListSponsor = new ArrayList<>();		
		String time = String.valueOf(10);
		int disStatus = 1;
		try {
//			String s = new SimpleDateFormat("yyyyMMdd").format(new Date());
//			int dateNow = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			Date dateNowInt = new Date();
			session = HibernateUtil.getSessionFactory().openSession();
			int loan = 1;
			String sql = "SELECT lr.loanCode, "
								+ "lr.loanName, ld.productType, "
								+ "ld.approvedAmount, lr.finalStatus, "
								+ "ld.disbursementDate, ld.borrowerFullname, "
								+ "lr.loanId, lr.calculateProfitType, lr.loanForMonth, ls.disbursementStatus "
								+ "FROM TblLoanReqDetail ld "
								+ "INNER JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "INNER JOIN TblLoanSponsorMapp ls ON ls.loanId = ld.loanId "
								+ "Where ls.sponsorId =:sponsorID "
								+ "and (ls.entryExpireTime >= :dateNowInt or  ls.disbursementStatus =:disStatus) ";
			if(ValidData.checkNull(loan_code) == true){
				sql = sql + "and lr.loanCode =:loan_code ";
			}
			if(final_status.size() > 0){
				sql = sql + "and lr.finalStatus in :final_status ";
			}
			if(ValidData.checkNull(borrower_name) == true){
				sql = sql + "and ld.borrowerFullname =:borrower_name ";
			}
			if(ValidData.checkNull(from_date) == true){
				sql = sql + "and lr.createdDate >= :from_date ";
			}
			if(ValidData.checkNull(to_date) == true){
				sql = sql + "and lr.createdDate <= :to_date ";
			}
			if(ValidData.checkNull(calculate_profit_type) == true){
				sql = sql + "and lr.calculateProfitType =:calculate_profit_type ";
			}
			sql = sql + "ORDER BY lr.createdDate DESC";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("disStatus", disStatus);
			query.setParameter("sponsorID", sponsorID);
			query.setParameter("dateNowInt", dateNowInt);
			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
			if(final_status.size() > 0){
				query.setParameter("final_status", final_status);
			}	
			if(ValidData.checkNull(borrower_name) == true){
				query.setParameter("borrower_name", borrower_name);
			}
			if(ValidData.checkNull(from_date) == true){
				Date fromDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(from_date + " 00:00:00");  
				query.setParameter("from_date", fromDate);
			}
			if(ValidData.checkNull(to_date) == true){
				Date toDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(to_date + " 23:59:59");  
				query.setParameter("to_date", toDate);
			}
			if(ValidData.checkNull(calculate_profit_type) == true){
				query.setParameter("calculate_profit_type", Integer.parseInt(calculate_profit_type));
			}
			query.setFirstResult(Integer.parseInt(offSet));
			query.setMaxResults(Integer.parseInt(limit));
			list = query.getResultList();
			
			System.out.println(list.size());
			for (int i = 0; i<  list.size(); i++){
				Object[] row = (Object[]) list.get(i);		
				ResContractListSponsor resContractListSponsor = new ResContractListSponsor();
				for (int j = 0; j < row.length; j++) {		
					try {
						resContractListSponsor.setLoan_code(row[0]+"");
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setLoan_name(row[1]+"");
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setProduct_name(row[2]+"");
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setApproved_amount(Long.parseLong(row[3]+""));
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setFinal_status(Long.parseLong(row[4]+""));
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setDisbursement_date(row[5]+"");
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setBorrower_fullname(row[6]+"");
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setLoan_id(Long.parseLong(row[7]+""));
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setCalculateProfitType(Long.parseLong(row[8]+""));
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setLoan_for_month(Long.parseLong(row[9]+""));
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setDisbursementStatus(Long.parseLong(row[10]+""));
					} catch (Exception e) {
					}
				}
				System.out.println("----------------------------------------------");
				lisResContractListSponsor.add(resContractListSponsor);
			}
			System.out.println(list.size());
			return lisResContractListSponsor;
		} catch (Exception e) {
			FileLogger.log(">> lisResContractListSponsor err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public long listCounListSponsor(int sponsorID, String loan_code, List<Integer> final_status, String borrower_name, String from_date, String to_date, String calculate_profit_type, String limit, String offSet) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ResContractList> lisResContractList = new ArrayList<>();		
		String time = String.valueOf(10);
		int loan = 1;
//		int dateNow = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		Date dateNowInt = new Date();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT count(lr.loanCode) "
								+ "FROM TblLoanReqDetail ld "
								+ "INNER JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "INNER JOIN TblLoanSponsorMapp ls ON ls.loanId = ld.loanId "
								+ "Where ls.sponsorId =:sponsorID "
								+ "and ls.entryExpireTime >= :dateNowInt ";
			
			if(ValidData.checkNull(loan_code) == true){
				sql = sql + "and lr.loanCode =:loan_code ";
			}
			if(final_status.size() > 0){
				sql = sql + "and lr.finalStatus in :final_status ";
			}
			if(ValidData.checkNull(borrower_name) == true){
				sql = sql + "and ld.borrowerFullname =:borrower_name ";
			}
			if(ValidData.checkNull(from_date) == true){
				sql = sql + "and lr.createdDate >= :from_date ";
			}
			if(ValidData.checkNull(to_date) == true){
				sql = sql + "and lr.createdDate <= :to_date ";
			}
			if(ValidData.checkNull(calculate_profit_type) == true){
				sql = sql + "and lr.calculateProfitType =:calculate_profit_type ";
			}
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("sponsorID", sponsorID);
			query.setParameter("dateNowInt", dateNowInt);
			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
			if(final_status.size() > 0){
				query.setParameter("final_status", final_status);
			}	
			if(ValidData.checkNull(borrower_name) == true){
				query.setParameter("borrower_name", borrower_name);
			}
			if(ValidData.checkNull(from_date) == true){
				Date fromDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(from_date + " 00:00:00");  
				query.setParameter("from_date", fromDate);
			}
			if(ValidData.checkNull(to_date) == true){
				Date toDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(to_date + " 23:59:59");  
				query.setParameter("to_date", toDate);
			}
			if(ValidData.checkNull(calculate_profit_type) == true){
				query.setParameter("calculate_profit_type", Integer.parseInt(calculate_profit_type));
			}
			list = query.getResultList();
			
			System.out.println(list.get(0));
			System.out.println(list.size());
			return (long)list.get(0);
		} catch (Exception e) {
			FileLogger.log(">> listResContractList err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return 0l;
	}
	
	public ResContractList getBranchRoom(int branchID, int roomID, int loanID) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		ResContractList lisResContractList = new ResContractList();		
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT tr.roomName, br.branchName "											
									+ "FROM TblLoanRequest lr "
									+ "INNER JOIN Branch br ON br.rowId in lr.branchId "
									+ "INNER JOIN TransasctionRoom tr ON tr.rowId in lr.roomId "
									+ "Where "
									+ "lr.loanId =:loanID and "
									+ "lr.roomId =:listRoom and "
									+ "lr.branchId =:listbranchId ";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanID", loanID);
			query.setParameter("listRoom", roomID);
			query.setParameter("listbranchId", branchID);
			list = query.getResultList();
			if(list.size() > 0){
				Object[] row = (Object[]) list.get(0);		
				for (int j = 0; j < row.length; j++) {				
					lisResContractList.setBranch_id(branchID+"");
					lisResContractList.setRoom_code(row[0]+"");
					lisResContractList.setBranch_name(row[1]+"");
				}
				System.out.println("----------------------------------------------");
			}
			System.out.println(list.size());
			return lisResContractList;
		} catch (Exception e) {
			FileLogger.log(">> checkLoan err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public boolean deleteTblImages(int loan_request_detail_id) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "delete from TblImages where loanRequestDetailId =:detailId ";	
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setParameter("detailId", loan_request_detail_id);
			int check = query.executeUpdate();
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log(">> deleteTblImages err " + e,LogType.ERROR);
		}finally {
			releaseSession(session);
		}
		return result;
    }
	
	public boolean deleteTblLoanBill(int loanID) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "delete from TblLoanBill where loanId =:loanID ";	
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setParameter("loanID", loanID);
			int check = query.executeUpdate();
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log(">> deleteTblImages err " + e,LogType.ERROR);
		}finally {
			releaseSession(session);
		}
		return result;
    }
	
	public boolean deleteAskAns(int loan_id) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "delete from TblLoanRequestAskAns where loanId =:loan_id ";	
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setParameter("loan_id", loan_id);
			int check = query.executeUpdate();
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log(">> deleteAskAns err " + e,LogType.ERROR);
		}finally {
			releaseSession(session);
		}
		return result;
    }
	
	public TblLoanSponsorMapp getLoanRequet(int loanID, int sponsorID) {
		Session session = null;
		BigDecimal sumAmount = null;
		List<Object> list = null;
		try {
			int statusDis = 5;
			int statusDisCreater = 0;
			session = HibernateUtil.getSessionFactory().openSession();
			String sqlUpd = "update TblLoanSponsorMapp set disbursementStatus =:statusDis where loanId =:loanIDUPD and sponsorId =:sponsorIDUPD and disbursementStatus =:statusDisCreater";
			String sqlSel = "from TblLoanSponsorMapp where loanId =:loanID and sponsorId =:sponsorID and disbursementStatus =:statusDisCreater";
			Query updateQ = session.createQuery(sqlUpd);
			Query query = session.createQuery(sqlSel);
			updateQ.setParameter("statusDis", statusDis);
			updateQ.setParameter("loanIDUPD", loanID);
			updateQ.setParameter("sponsorIDUPD", sponsorID);
			updateQ.setParameter("statusDisCreater", statusDisCreater);
			
			query.setParameter("statusDisCreater", statusDis);
			query.setParameter("loanID", loanID);
			query.setParameter("sponsorID", sponsorID);
			Transaction transaction = session.beginTransaction();
			try {
				int result = updateQ.executeUpdate();
				if(result == 1){					
					TblLoanSponsorMapp tbLoanSponsorMapp = null;
					list = query.getResultList();
					System.out.println(list.size());
					tbLoanSponsorMapp = (TblLoanSponsorMapp) list.get(0);
					transaction.commit();
					return tbLoanSponsorMapp;
				}				
			} catch (Exception e) {
				transaction.rollback();
				e.printStackTrace();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("getListVaMap = " +e);
		}finally {
			releaseSession(session);
		}
		return null;
	}
	
	
	public List<ObjDebtReminderDetail> listDebtReminderDetail(String createrBy, List<Integer> branchID, List<Integer> roomID, String loan_code, List<Integer> final_status, String billPStatus, String borrower_name, String from_date, String to_date, String bill_from_date, String bill_to_date, String id_number, String limit, String offSet) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ObjDebtReminderDetail> lisResDebtReminderDetail = new ArrayList<>();		
		String time = String.valueOf(10);
		int finalSTT = 116;
		int billSTTDH = 1161;
		int billSTTQH = 1162;
		try {
//			String s = new SimpleDateFormat("yyyyMMdd").format(new Date());
//			int dateNow = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			Date dateNowInt = new Date();
			session = HibernateUtil.getSessionFactory().openSession();
			int loan = 1;
			
			String sql = "SELECT lr.loanId, lr.loanCode, lr.createdBy, lr.finalStatus, ld.productDesc, "
								+ "lr.createdDate, ld.borrowerFullname, ld.borrowerId, ld.borrowerPhone, "
								+ "ld.approvedAmount, tr.roomName, br.branchName, lb.paymentAmt, "
								+ "lb.paymentDate, lb.billIndex, ld.productType, lb.dayMustPay, lb.totalOnAMonth, lb.billStatus, lb.billPaymentStatus "
								+ "FROM TblLoanReqDetail ld "
								+ "INNER JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "INNER JOIN TblLoanBill lb ON lb.loanId = ld.loanId "
								+ "INNER JOIN Branch br ON br.rowId in lr.branchId "
								+ "INNER JOIN TransasctionRoom tr ON tr.rowId in lr.roomId "
								+ "Where lr.loanId >=:loanID ";
//								+ "and lr.finalStatus =:finalStt "
//								+ "and (lb.billStatus =:billSTTDH or lb.billStatus =:billSTTQH) ";
			if(ValidData.checkNull(loan_code) == true){
				sql = sql + "and lr.loanCode =:loan_code ";
			}
			if(final_status.size() > 0){
				if(ValidData.checkNull(billPStatus) == true){
					sql = sql + "and ((lb.billStatus in :listFinalStatus and lb.billPaymentStatus =:billPStatus) or lr.finalStatus in :listFinalStatus) ";
				}else{
					sql = sql + "and (lb.billStatus in :listFinalStatus or lr.finalStatus in :listFinalStatus) ";
				}
			}
			if(branchID.size() > 0){
				sql = sql + "and br.rowId in :branchID ";
			}
			if(roomID.size() > 0){
				sql = sql + "and tr.rowId in :roomID ";
			}
			if(ValidData.checkNull(createrBy) == true){
				sql = sql + "and lr.createdBy =:createrBy ";
			}
			if(ValidData.checkNull(borrower_name) == true){
				sql = sql + "and ld.borrowerFullname =:borrower_name ";
			}
			if(ValidData.checkNull(from_date) == true){
				sql = sql + "and lr.createdDate >= :from_date ";
			}
			if(ValidData.checkNull(to_date) == true){
				sql = sql + "and lr.createdDate <= :to_date ";
			}
			if(ValidData.checkNull(bill_from_date) == true){
				sql = sql + "and lb.dayMustPay >= :bill_from_date ";
			}
			if(ValidData.checkNull(bill_to_date) == true){
				sql = sql + "and lb.dayMustPay <= :bill_to_date ";
			}
			if(ValidData.checkNull(id_number) == true){
				sql = sql + "and ld.borrowerId =:id_number ";
			}
			sql = sql + "ORDER BY lr.createdDate DESC, lb.billIndex DESC";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanID", loan);
			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
//			if(final_status.size() > 0){
//			query.setParameter("finalStt", finalSTT);
//			query.setParameter("billSTTDH", billSTTDH);
//			query.setParameter("billSTTQH", billSTTQH);
//			}	
			if(final_status.size() > 0){
				if(ValidData.checkNull(billPStatus) == true){
					int billStt = Integer.parseInt(billPStatus);
					query.setParameter("listFinalStatus", final_status);
					query.setParameter("billPStatus", billStt);
				}else{
					query.setParameter("listFinalStatus", final_status);
				}
			}
			if(final_status.size() > 0){
				query.setParameter("listFinalStatus", final_status);
			}
			if(branchID.size() > 0){
				query.setParameter("branchID", branchID);
			}
			if(roomID.size() > 0){
				query.setParameter("roomID", roomID);
			}
			if(ValidData.checkNull(borrower_name) == true){
				query.setParameter("borrower_name", borrower_name);
			}
			if(ValidData.checkNull(createrBy) == true){
				query.setParameter("createrBy", createrBy);
			}
			if(ValidData.checkNull(from_date) == true){
				Date fromDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(from_date + " 00:00:00");  
				query.setParameter("from_date", fromDate);
			}
			if(ValidData.checkNull(to_date) == true){
				Date toDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(to_date + " 23:59:59");  
				query.setParameter("to_date", toDate);
			}
			if(ValidData.checkNull(bill_from_date) == true){
				query.setParameter("bill_from_date", Integer.parseInt(bill_from_date));
			}
			if(ValidData.checkNull(bill_to_date) == true){
				query.setParameter("bill_to_date", Integer.parseInt(bill_to_date));
			}
			if(ValidData.checkNull(id_number) == true){
				query.setParameter("id_number", id_number);
			}
			if(ValidData.checkNull(offSet) == true){
				query.setFirstResult(Integer.parseInt(offSet));
			}
			if(ValidData.checkNull(limit) == true){
				query.setMaxResults(Integer.parseInt(limit));
			}
		
			list = query.getResultList();
			
			System.out.println(list.size());
			for (int i = 0; i<  list.size(); i++){
				Object[] row = (Object[]) list.get(i);		
				ObjDebtReminderDetail objDebtReminderDetail = new ObjDebtReminderDetail();
				for (int j = 0; j < row.length; j++) {	
					try {
						objDebtReminderDetail.setLoanId(Integer.parseInt(row[0]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setLoan_code(row[1]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setCreated_by(row[2]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setFinal_status(Integer.parseInt(row[3]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setProduct_desc(row[4]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setCreatedDate(row[5]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBorrowerFullname(row[6]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBorrower_id(row[7]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBorrowerPhone(row[8]+"");
					} catch (Exception e) {
					}

					try {
						objDebtReminderDetail.setLoan_amount(Long.parseLong(row[9]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setRoom_name(row[10]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBranch(row[11]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setPayment_amount(Long.parseLong(row[12]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setPayment_date(row[13]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBill_index(Integer.parseInt(row[14]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setProduct_id(Integer.parseInt(row[15]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setDay_must_pay(Integer.parseInt(row[16]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setTotal_on_a_month(Long.parseLong(row[17]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBill_status(Integer.parseInt(row[18]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBill_payment_status(Integer.parseInt(row[19]+""));
					} catch (Exception e) {
					}
				}
				System.out.println("----------------------------------------------");
				lisResDebtReminderDetail.add(objDebtReminderDetail);
			}
			System.out.println(list.size());
			return lisResDebtReminderDetail;
		} catch (Exception e) {
			FileLogger.log(">> lisResDebtReminderDetail err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	
	public long countDebtReminderDetail(String createrBy, List<Integer> branchID, List<Integer> roomID, String loan_code, List<Integer> final_status, String billPStatus, String borrower_name, String from_date, String to_date, String bill_from_date, String bill_to_date, String id_number) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ObjDebtReminderDetail> lisResDebtReminderDetail = new ArrayList<>();		
		String time = String.valueOf(10);
		int finalSTT = 116;
		int billSTTDH = 1161;
		int billSTTQH = 1162;
		try {
//			String s = new SimpleDateFormat("yyyyMMdd").format(new Date());
//			int dateNow = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			Date dateNowInt = new Date();
			session = HibernateUtil.getSessionFactory().openSession();
			int loan = 1;
			
			String sql = "SELECT count(lr.loanCode) "
								+ "FROM TblLoanReqDetail ld "
								+ "INNER JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "INNER JOIN TblLoanBill lb ON lb.loanId = ld.loanId "
								+ "INNER JOIN Branch br ON br.rowId in lr.branchId "
								+ "INNER JOIN TransasctionRoom tr ON tr.rowId in lr.roomId "
								+ "Where lr.loanId >=:loanID ";
								
//								+ "and lr.finalStatus =:finalStt "
//								+ "and (lb.billStatus =:billSTTDH or  lb.billStatus =:billSTTQH) ";
			if(ValidData.checkNull(loan_code) == true){
				sql = sql + "and lr.loanCode =:loan_code ";
			}
//			if(final_status.size() > 0){
//				sql = sql + "and (lb.billStatus in :listFinalStatus or lr.finalStatus in :listFinalStatus) ";
//			}
			if(final_status.size() > 0){
				if(ValidData.checkNull(billPStatus) == true){
					sql = sql + "and ((lb.billStatus in :listFinalStatus and lb.billPaymentStatus =:billPStatus) or lr.finalStatus in :listFinalStatus) ";
				}else{
					sql = sql + "and (lb.billStatus in :listFinalStatus or lr.finalStatus in :listFinalStatus) ";
				}
			}
//			if(final_status.size() > 0){
//				sql = sql + "and lr.finalStatus in :final_status ";
//			}
			if(branchID.size() > 0){
				sql = sql + "and br.rowId in :branchID ";
			}
			if(roomID.size() > 0){
				sql = sql + "and tr.rowId in :roomID ";
			}
			if(ValidData.checkNull(createrBy) == true){
				sql = sql + "and lr.createdBy =:createrBy ";
			}
			if(ValidData.checkNull(borrower_name) == true){
				sql = sql + "and ld.borrowerFullname =:borrower_name ";
			}
			if(ValidData.checkNull(from_date) == true){
				sql = sql + "and lr.createdDate >= :from_date ";
			}
			if(ValidData.checkNull(to_date) == true){
				sql = sql + "and lr.createdDate <= :to_date ";
			}
			if(ValidData.checkNull(bill_from_date) == true){
				sql = sql + "and lb.dayMustPay >= :bill_from_date ";
			}
			if(ValidData.checkNull(bill_to_date) == true){
				sql = sql + "and lb.dayMustPay <= :bill_to_date ";
			}
			if(ValidData.checkNull(id_number) == true){
				sql = sql + "and ld.borrowerId =:id_number ";
			}
			sql = sql + "ORDER BY lr.createdDate DESC";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanID", loan);
			if(final_status.size() > 0){
				if(ValidData.checkNull(billPStatus) == true){
					int billStt = Integer.parseInt(billPStatus);
					query.setParameter("listFinalStatus", final_status);
					query.setParameter("billPStatus", billStt);
				}else{
					query.setParameter("listFinalStatus", final_status);
				}
			}
			if(final_status.size() > 0){
				query.setParameter("listFinalStatus", final_status);
			}
			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
//			if(final_status.size() > 0){
			if(branchID.size() > 0){
				query.setParameter("branchID", branchID);
			}
			if(roomID.size() > 0){
				query.setParameter("roomID", roomID);
			}
//			query.setParameter("finalStt", finalSTT);
//			query.setParameter("billSTTDH", billSTTDH);
//			query.setParameter("billSTTQH", billSTTQH);
//			}	
			if(ValidData.checkNull(borrower_name) == true){
				query.setParameter("borrower_name", borrower_name);
			}
			if(ValidData.checkNull(createrBy) == true){
				query.setParameter("createrBy", createrBy);
			}
			if(ValidData.checkNull(from_date) == true){
				Date fromDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(from_date + " 00:00:00");  
				query.setParameter("from_date", fromDate);
			}
			if(ValidData.checkNull(to_date) == true){
				Date toDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(to_date + " 23:59:59");  
				query.setParameter("to_date", toDate);
			}
			if(ValidData.checkNull(bill_from_date) == true){
				query.setParameter("bill_from_date", Integer.parseInt(bill_from_date));
			}
			if(ValidData.checkNull(bill_to_date) == true){
				query.setParameter("bill_to_date", Integer.parseInt(bill_to_date));
			}
			if(ValidData.checkNull(id_number) == true){
				query.setParameter("id_number", id_number);
			}
//			query.setFirstResult(Integer.parseInt(offSet));
//			query.setMaxResults(Integer.parseInt(limit));
			list = query.getResultList();

			System.out.println(list.size());
			return (long)list.get(0);
		} catch (Exception e) {
			FileLogger.log(">> lisResDebtReminderDetail err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return 0l;
	}
	
	public boolean updateLoanBill(int loan_id, int bill_index, Integer last_day_accept_pay) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "update TblLoanBill set lastDayAcceptPay =:lastDayUPD where loanId =:loanIDUPD and billIndex =:billIndexUPD";	
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setParameter("lastDayUPD", last_day_accept_pay);
			query.setParameter("loanIDUPD", loan_id);
			query.setParameter("billIndexUPD", bill_index);
			int check = query.executeUpdate();
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log(">> updateLoanBill err " + e,LogType.ERROR);
		}finally {
			releaseSession(session);
		}
		return result;
    }
	public boolean updateLoanBillPhitruocno(int loan_id, int bill_index, BigDecimal previousPeriodDebtFee) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "update TblLoanBill set previousPeriodDebtFee =:previousPeriodDebtFee where loanId =:loanIDUPD and billIndex =:billIndexUPD";	
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setParameter("previousPeriodDebtFee", previousPeriodDebtFee);
			query.setParameter("loanIDUPD", loan_id);
			query.setParameter("billIndexUPD", bill_index);
			int check = query.executeUpdate();
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log(">> updateLoanBill err " + e,LogType.ERROR);
		}finally {
			releaseSession(session);
		}
		return result;
    }
	
	public int maxBillIndex(int loanID) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ObjDebtReminderDetail> lisResDebtReminderDetail = new ArrayList<>();		
		String time = String.valueOf(10);		
		try {
			Date dateNowInt = new Date();
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT max(billIndex) FROM TblLoanBill ld where loanId =:loanID";
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanID", loanID);
			list = query.getResultList();
			System.out.println(list.size());
			return (int)list.get(0);
		} catch (Exception e) {
			FileLogger.log(">> lisResDebtReminderDetail err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return 0;
	}
	public List<TblInbox> getInboxs(String userName, int limit, int offset) {
		List<TblInbox> results = new ArrayList<>();
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblInbox.class);
			Criterion getUserName 			= Restrictions.eq("username", userName);
//			Criterion getinboxRead 			= Restrictions.eq("inboxRead", 0);
			crtProduct.add(getUserName);
//			crtProduct.add(getinboxRead);
			crtProduct.setFirstResult(offset);
			crtProduct.setMaxResults(limit);
			crtProduct.addOrder(Order.desc("createdDate"));
			results = crtProduct.list();
			return results;
		} catch (Exception e) {
			FileLogger.log(" getInboxs Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public long countTblInboxUnread(String userName) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ObjDebtReminderDetail> lisResDebtReminderDetail = new ArrayList<>();	
		int ibread = 0;
		try {
			Date dateNowInt = new Date();
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT count(inboxId) FROM TblInbox where username =:userName and inboxRead=:ibread";
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("userName", userName);
			query.setParameter("ibread", ibread);
			list = query.getResultList();
			System.out.println(list.size());
			return (long)list.get(0);
		} catch (Exception e) {
			FileLogger.log(">> lisResDebtReminderDetail err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return 0;
	}
	
	public long countAllTblInbox(String userName) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ObjDebtReminderDetail> lisResDebtReminderDetail = new ArrayList<>();	
		try {
			Date dateNowInt = new Date();
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT count(inboxId) FROM TblInbox where username =:userName";
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("userName", userName);
			list = query.getResultList();
			System.out.println(list.size());
			return (long)list.get(0);
		} catch (Exception e) {
			FileLogger.log(">> countAllTblInbox err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return 0;
	}
	
	
	
	public TblInbox getInbox(String userName, int inboxID) {
		List<TblInbox> results = new ArrayList<>();
		Session session = null;
		TblInbox tblInbox = new TblInbox();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblInbox.class);
			Criterion getUserName 			= Restrictions.eq("username", userName);
			Criterion getInboxID 			= Restrictions.eq("inboxId", inboxID);
			crtProduct.add(getUserName);
			crtProduct.add(getInboxID);
			results = crtProduct.list();
			if(results.size() > 0){
				tblInbox = (TblInbox) results.get(0);
			}
			return tblInbox;
		} catch (Exception e) {
			FileLogger.log(" getInboxs Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public TblLoanBill getTblLoanBill(int billIndex, int loanID) {
		List<TblLoanBill> results = new ArrayList<>();
		Session session = null;
		TblLoanBill tblLoanBill = new TblLoanBill();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanBill.class);
			Criterion getUserName 			= Restrictions.eq("billIndex", billIndex);
			Criterion getInboxID 			= Restrictions.eq("loanId", loanID);
			crtProduct.add(getUserName);
			crtProduct.add(getInboxID);
			results = crtProduct.list();
			if(results.size() > 0){
				tblLoanBill = (TblLoanBill) results.get(0);
			}
			return tblLoanBill;
		} catch (Exception e) {
			FileLogger.log(" getTblLoanBill Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public boolean updateTblInbox(TblInbox tblInbox) {
		try {
			updateObj(tblInbox, tblInbox.getInboxId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("updateTblInbox Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean checkSponso(int accountID) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT sponsorId FROM TblSponsor Where sponsorAccountId =:sponsoID";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("sponsoID", accountID);
			list = query.getResultList();
			if(list.size() <= 0){
				result = false;
			}else{
				result = true;
			}
			System.out.println(list.size());
			return result;
		} catch (Exception e) {
			FileLogger.log(">> checkSponso err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return false;
	}
	
	public TblLoanSponsorMapp getLoanSponsorMapp(int loan_id, int sponsoID) {
		List<TblLoanSponsorMapp> results = new ArrayList<>();
		Session session = null;
		TblLoanSponsorMapp tblLoanSponsorMapp = new TblLoanSponsorMapp();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanSponsorMapp.class);
			Criterion getLoan 				= Restrictions.eq("loanId", loan_id);
			Criterion getSponso				= Restrictions.eq("sponsorId", sponsoID);
			crtProduct.add(getLoan);
			crtProduct.add(getSponso);
			results = crtProduct.list();
			if(results.size() > 0){
				tblLoanSponsorMapp = (TblLoanSponsorMapp) results.get(0);
				return tblLoanSponsorMapp;
			}
		} catch (Exception e) {
			FileLogger.log(" getLoanSponsorMapp Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public TblLoanSponsorMapp getLoanSponsorMappLoanID(int loan_id) {
		List<TblLoanSponsorMapp> results = new ArrayList<>();
		Session session = null;
		TblLoanSponsorMapp tblLoanSponsorMapp = new TblLoanSponsorMapp();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanSponsorMapp.class);
			Criterion getLoan 				= Restrictions.eq("loanId", loan_id);
			crtProduct.add(getLoan);
			results = crtProduct.list();
			if(results.size() > 0){
				tblLoanSponsorMapp = (TblLoanSponsorMapp) results.get(0);
				return tblLoanSponsorMapp;
			}
		} catch (Exception e) {
			FileLogger.log(" getLoanSponsorMappLoanID Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public TblSponsor getSponsor(int sponsoID) {
		List<TblSponsor> results = new ArrayList<>();
		Session session = null;
		TblSponsor tblSponsor = new TblSponsor();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblSponsor.class);
			Criterion getSponso				= Restrictions.eq("sponsorAccountId", sponsoID);
			crtProduct.add(getSponso);
			results = crtProduct.list();
			if(results.size() > 0){
				System.out.println(results.size());
				tblSponsor = (TblSponsor) results.get(0);
				return tblSponsor;
			}
		} catch (Exception e) {
			FileLogger.log(" getSponsor Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public TblSponsor getSponsoID(int sponsoID) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		boolean result = false;
		TblSponsor tblSponsor = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "from TblSponsor Where sponsorAccountId =:sponsoID";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("sponsoID", sponsoID);
			list = query.getResultList();
			if(list.size() > 0){
				tblSponsor = (TblSponsor) list.get(0);
			}
			System.out.println(list.size());
			return tblSponsor;
		} catch (Exception e) {
			FileLogger.log(">> checkSponso err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public TblInsuranceProviders getInsuranceProviders111(long insuranceProviderId) {
		List<TblInsuranceProviders> results = new ArrayList<>();
		Session session = null;
		TblInsuranceProviders tblInsuranceProviders = new TblInsuranceProviders();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblInsuranceProviders.class);
			Criterion getLoan 				= Restrictions.eq("insuranceProviderId", insuranceProviderId);
			crtProduct.add(getLoan);
			results = crtProduct.list();
			if(results.size() > 0){
				tblInsuranceProviders = (TblInsuranceProviders) results.get(0);
				return tblInsuranceProviders;
			}
		} catch (Exception e) {
			FileLogger.log(" getLoanSponsorMapp Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}

	public InsuranceProviders getInsurancePro(long insuranceProviderId) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		InsuranceProviders insuranceProviders = new InsuranceProviders();		
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT ip.insuranceProviderId, ip.insuranceCode , ip.insuranceName , ip.activeStt , ip.createdDate , ip.editedDate , ip.fixFee , ip.fixRate, "
					+ "ip.insuranceApi, ip.authToken, ip.apiAcc, ip.apiPass, ip.authTokenExpires, ip.insuranceDefault, "
					+ "ib.bankName , ib.accBankNo , ib.accBankName "											
									+ "FROM TblInsuranceProviders ip "
									+ "INNER JOIN TblInsuranceBanks ib ON ip.insuranceProviderId = ib.insuranceProviderId "
									+ "Where "
									+ "ip.insuranceProviderId =:ipID ";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("ipID", insuranceProviderId);
			list = query.getResultList();
			if(list.size() > 0){
				Object[] row = (Object[]) list.get(0);		
				for (int j = 0; j < row.length; j++) {				
					insuranceProviders.setInsuranceProviderId(Long.parseLong(row[0]+""));
					insuranceProviders.setInsuranceCode(row[1]+"");
					insuranceProviders.setInsuranceName(row[2]+"");
					insuranceProviders.setActiveStt(Integer.parseInt(row[3]+""));
					try {
						insuranceProviders.setCreatedDate(new SimpleDateFormat("dd/MM/yyyy").parse(row[4]+""));
					} catch (Exception e) {
					}
					try {
						insuranceProviders.setEditedDate(new SimpleDateFormat("dd/MM/yyyy").parse(row[5]+""));
					} catch (Exception e) {
					}
					try {
						insuranceProviders.setFixFee(Integer.parseInt(row[6]+""));
					} catch (Exception e) {
					}
					try {
						insuranceProviders.setFixRate(Float.parseFloat(row[7]+""));
					} catch (Exception e) {
					}
					try {
						insuranceProviders.setInsuranceApi(row[8]+"");
					} catch (Exception e) {
					}
					try {
						insuranceProviders.setAuthToken(row[9]+"");
					} catch (Exception e) {
					}
					try {
						insuranceProviders.setApiAcc(row[10]+"");
					} catch (Exception e) {
					}
					try {
						insuranceProviders.setApiPass(row[11]+"");
					} catch (Exception e) {
					}
					try {
						insuranceProviders.setAuthTokenExpires(row[12]+"");
					} catch (Exception e) {
					}
					try {
						insuranceProviders.setInsurancedefault(Integer.parseInt(row[13]+""));
					} catch (Exception e) {
					}
//					insuranceProviders.setBankName(row[8]+"");
//					insuranceProviders.setAccBankNo(row[9]+"");
//					insuranceProviders.setAccBankName(row[10]+"");
				}
				System.out.println("----------------------------------------------");
			}
			System.out.println(list.size());
			return insuranceProviders;
		} catch (Exception e) {
			FileLogger.log(">> checkLoan err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public List<TblInsuranceBanks> getInsuranceBanks(int insuranceProviderId) {
		List<TblInsuranceBanks> results = new ArrayList<>();
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblInsuranceBanks.class);
			Criterion getUserName 			= Restrictions.eq("insuranceProviderId", insuranceProviderId);
			crtProduct.add(getUserName);
			crtProduct.addOrder(Order.desc("mappingId"));
			results = crtProduct.list();
			return results;
		} catch (Exception e) {
			FileLogger.log(" getInboxs Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public boolean checkTblBorrower(String idNumber) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT idNumber FROM TblBorrower Where idNumber =:idNB";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("idNB", idNumber);
			list = query.getResultList();
			if(list.size() <= 0){
				result = false;
			}else{
				result = true;
			}
			System.out.println(list.size());
			return result;
		} catch (Exception e) {
			FileLogger.log(">> checkTblBorrower err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return false;
	}
	
	public TblBorrower getTblBorrower(String idNumber) {
		List<TblBorrower> results = new ArrayList<>();
		Session session = null;
		TblBorrower tblBorrower = new TblBorrower();
		try {
			session					= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 	= session.createCriteria(TblBorrower.class);
			Criterion borrower		= Restrictions.eq("idNumber", idNumber);
			crtProduct.add(borrower);
			results = crtProduct.list();
			if(results.size() > 0){
				System.out.println(results.size());
				tblBorrower = (TblBorrower) results.get(0);
				return tblBorrower;
			}
		} catch (Exception e) {
			FileLogger.log(" getTblBorrower Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public TblBlackList getTblBlackList(int borrowerID) {
		List<TblBlackList> results = new ArrayList<>();
		Session session = null;
		TblBlackList tblBlackList = new TblBlackList();
		try {
			session					= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 	= session.createCriteria(TblBlackList.class);
			Criterion borrower		= Restrictions.eq("borrowerId", borrowerID);
			crtProduct.add(Restrictions.gt("effectiveDateTo", new Date())); //lt < , gt > 
			crtProduct.add(borrower);
			results = crtProduct.list();
			if(results.size() > 0){
				System.out.println(results.size());
				tblBlackList = (TblBlackList) results.get(0);
				return tblBlackList;
			}
		} catch (Exception e) {
			FileLogger.log(" getTblBlackList Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public long countTblBorrower(int branch_id) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ObjDebtReminderDetail> lisResDebtReminderDetail = new ArrayList<>();	
		try {
			Date dateNowInt = new Date();
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT count(borrowerId) FROM TblBorrower where branchId =:branchID";
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("branchID", branch_id);
			list = query.getResultList();
			System.out.println(list.size());
			return (long)list.get(0);
		} catch (Exception e) {
			FileLogger.log(">> countTblBorrower err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return 0;
	}
	
	public static void main(String[] args) {
		
		DBFintechHome dbFintechHome = new DBFintechHome();
//		TblBorrower getTblBorrower = dbFintechHome.getTblBorrower("131231231");
//		System.out.println(getTblBorrower.getBorrowerId());
		
//		long countTblBorrower = dbFintechHome.countTblBorrower(18);
//		System.out.println(countTblBorrower);
//		TblSponsor getSponsor = dbFintechHome.getSponsoID(11);
//		System.out.println(getSponsor.getSponsorId());
		
//		InsuranceProviders getLoanSponsorMapp = dbFintechHome.getInsurancePro(1);
//		System.out.println(getLoanSponsorMapp.getInsuranceName());
//		
//		List<TblInsuranceBanks> getInsuranceBanks1 = dbFintechHome.getInsuranceBanks(1);
//		for (TblInsuranceBanks tblInsuranceBanks : getInsuranceBanks1) {
//			System.out.println(tblInsuranceBanks.getAccBankNo());
//		}
//		
//		boolean check = dbFintechHome.checkS1ponso(19);
//		System.out.println(check);
//		
		TblLoanBillHome tblLoanBillHome = new TblLoanBillHome();
		TblLoanBill tblLoanBill = tblLoanBillHome.getTblLoanBillIndex(201,1);
		int aa = tblLoanBill.getDayMustPay();
		
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
		String dateNow =  new SimpleDateFormat("yyyyMMdd").format(new Date());
		LocalDate localDate1 = formatter.parseLocalDate(String.valueOf(aa));
		LocalDate localDate2 = formatter.parseLocalDate(dateNow);
		
		System.out.println(localDate1);
		System.out.println(localDate2);
		System.out.println("---------");
		boolean is1After2 = localDate1.isBefore(localDate2);
		
		System.out.println(is1After2);

//		List<TblImages> getTblImagesJoin = dbFintechHome.getTblImagesJoin(174);
//		for (TblImages tblImages : getTblImagesJoin) {
//			System.out.println(tblImages.getUploadBy());
//			System.out.println(tblImages.getCreatedDate());
//		}
//		
//		int maxBillIndex = dbFintechHome.maxBillIndex(160);
//		TblInbox getInboxs = dbFintechHome.getInbox("supercub@mailinator.com", 1);
//		System.out.println(getInboxs.getContent());
//		System.out.println(getInboxs.getCreatedDate());
//		System.out.println("-----------------------");
//		
//		long maxx = dbFintechHome.countAllTblInbox("supercub@mailinator.com");
//		System.out.println(maxx);
//		long maxx1 = dbFintechHome.countTblInboxUnread("supercub@mailinator.com");
//		System.out.println(maxx1);
//		SimpleDateFormat sm = new SimpleDateFormat("yyyyMMdd 00:00:00");
//		try {
//			AccountHome accountHome = new AccountHome();
//			DBFintechHome dbFintechHome = new DBFintechHome();
//			Account acc = accountHome.getAccountUsename("dinhphuong.v@gmail.com");
//			List<Integer> branchID = new ArrayList<>();
//			List<Integer> roomID = new ArrayList<>();
//			if (ValidData.checkNull(acc.getBranchId()) == true) {
//				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
//				Iterator<String> keys = isJsonObject.keys();
//				while (keys.hasNext()) {
//					String key = keys.next();
//					System.out.println(key);
//					JSONArray msg = (JSONArray) isJsonObject.get(key);
//					branchID.add(new Integer(key.toString()));
//					for (int i = 0; i < msg.length(); i++) {
//						roomID.add(Integer.parseInt(msg.get(i).toString()));
//					}
//				}
//			}
//			TblLoanSponsorMapp lisResContractList = dbFintechHome.getLoanRequet(139, 11);
//			System.out.println(lisResContractList.getLoanId());
//			System.out.println(lisResContractList.getDisbursementDate().compareTo(sm.parse(sm.format(new Date()))));
//			int aaa = lisResContractList.getDisbursementDate().compareTo(sm.parse(sm.format(new Date())));
//			if(aaa < 0){
//				System.out.println("a");
//			}else{
//				System.out.println("b");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		 List<Integer> list1 = new ArrayList<>();
//	      list1.add(new Integer(41));
//	      list1.add(new Integer(42));
//	      
//	      List<Integer> list = new ArrayList<>();
//	      list.add(new Integer(18));
//	      list.add(new Integer(19));
		
//		for (ResContractList resContractList : lisResContractList) {
//			System.out.println(resContractList.getLoan_code());
//		}
//		
//	      System.out.println(list);
//		boolean aaaa = dbFintechHome.deleteTblImages(48);
//		System.out.println("a: "+ aaaa);
	}
}
