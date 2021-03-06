package vn.com.payment.services;

import vn.com.payment.config.MainCfg;
import vn.com.payment.entities.Account;
import vn.com.payment.entities.TblBanks;
import vn.com.payment.entities.TblBlackList;
import vn.com.payment.entities.TblBorrower;
import vn.com.payment.entities.TblDebtRemindHistory;
import vn.com.payment.entities.TblImages;
import vn.com.payment.entities.TblInbox;
import vn.com.payment.entities.TblInsuranceBanks;
import vn.com.payment.entities.TblLoanBill;
import vn.com.payment.entities.TblLoanExpertiseSteps;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.entities.TblLoanRequestAskAns;
import vn.com.payment.entities.TblLoanRequestAskAnsGen;
import vn.com.payment.entities.TblLoanSponsorMapp;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.entities.TblRateConfig;
import vn.com.payment.entities.TblSponsor;
import vn.com.payment.entities.TblSystemActions;
import vn.com.payment.home.AccountHome;
import vn.com.payment.home.BaseMongoDB;
import vn.com.payment.home.DBFintechHome;
import vn.com.payment.home.TblBanksHome;
import vn.com.payment.home.TblLoanBillHome;
//import vn.com.payment.home.TblLoanReqDetailHome;
import vn.com.payment.home.TblLoanRequestHome;
import vn.com.payment.home.TblProductHome;
import vn.com.payment.home.TblRateConfigHome;
import vn.com.payment.object.BankReq;
import vn.com.payment.object.BankRes;
import vn.com.payment.object.ContractObj;
import vn.com.payment.object.ContractObjRes;
import vn.com.payment.object.Fees;
import vn.com.payment.object.InsuranceProviders;
//import vn.com.payment.object.NotifyObject;
import vn.com.payment.object.ObjBillRes;
import vn.com.payment.object.ObjDebtReminderDetail;
import vn.com.payment.object.ObjDebtReminderRedis;
import vn.com.payment.object.ObjImage;
import vn.com.payment.object.ObjInbox;
//import vn.com.payment.object.ObjMinhhoa;
import vn.com.payment.object.ObjQuestions;
import vn.com.payment.object.ObjReqFee;
import vn.com.payment.object.ProducResAll;
import vn.com.payment.object.ProductReq;
import vn.com.payment.object.ProductRes;
import vn.com.payment.object.RateConfigReq;
import vn.com.payment.object.RateConfigRes;
import vn.com.payment.object.ReqAllotment;
import vn.com.payment.object.ReqAppraisal;
import vn.com.payment.object.ReqContractList;
import vn.com.payment.object.ReqContractListSponsor;
import vn.com.payment.object.ReqCreaterLoan;
import vn.com.payment.object.ReqDebtReminder;
import vn.com.payment.object.ReqDisbursement;
import vn.com.payment.object.ReqInbox;
import vn.com.payment.object.ReqPayment;
import vn.com.payment.object.ReqSentEmail;
import vn.com.payment.object.ReqSettlement;
import vn.com.payment.object.ReqStepLog;
import vn.com.payment.object.ReqUDExtendStatus;
import vn.com.payment.object.ReqUPDInbox;
import vn.com.payment.object.ReqUpdateInsurance;
import vn.com.payment.object.ReqUpdateStatus;
import vn.com.payment.object.ResAllContractList;
import vn.com.payment.object.ResAllotment;
import vn.com.payment.object.ResAppraisal;
import vn.com.payment.object.ResContractDetail;
import vn.com.payment.object.ResContractList;
import vn.com.payment.object.ResContractListSponsor;
import vn.com.payment.object.ResCreaterLoan;
import vn.com.payment.object.ResDebtReminder;
import vn.com.payment.object.ResDisbursement;
import vn.com.payment.object.ResInbox;
import vn.com.payment.object.ResPayment;
import vn.com.payment.object.ResSentEmail;
import vn.com.payment.object.ResSettlement;
import vn.com.payment.object.ResStepLog;
import vn.com.payment.object.ResUDExtendStatus;
import vn.com.payment.object.ResUPDInbox;
import vn.com.payment.object.ResUpdateInsurance;
import vn.com.payment.object.ResUpdateStatus;
import vn.com.payment.redis.RedisBusiness;
import vn.com.payment.thread.ThreadInsertActionLog;
import vn.com.payment.thread.ThreadInsertLogStep;
import vn.com.payment.ultities.Commons;
import vn.com.payment.ultities.FileLogger;
import vn.com.payment.ultities.GsonUltilities;
import vn.com.payment.ultities.Utils;
import vn.com.payment.ultities.ValidData;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.math.util.MathUtils;
import org.bson.Document;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.json.JSONArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Bussiness {
	AccountHome accountHome = new AccountHome();
	TblProductHome tblProductHome = new TblProductHome();
	TblRateConfigHome tblRateConfigHome = new TblRateConfigHome();
	TblLoanRequestHome tbLoanRequestHome = new TblLoanRequestHome();
	TblLoanBillHome tblLoanBillHome = new TblLoanBillHome();
	TblBanksHome tblBanksHome = new TblBanksHome();
	BaseMongoDB mongoDB = new BaseMongoDB();
	Caculator caculator = new Caculator();
	UserInfo userInfo = new UserInfo();
	ValidData validData = new ValidData();
	DBFintechHome dbFintechHome = new DBFintechHome();
	Gson gson = new Gson();
	long statusSuccess = 100l;
	long statusFale = 111l;
	long statusFaleToken = 104l;
	int statusPending = 107;
	int statusReject = 110;
	int statusDisbursement = 116;
	SimpleDateFormat sm = new SimpleDateFormat("yyyyMMdd 00:00:00");
	private FileLogger log = new FileLogger(Caculator.class);

	public Response getContractNumber(String dataContract) {
		log.info("----------------Bat dau getContractNumber--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ContractObjRes contractObjRes = new ContractObjRes();
		try {
			log.info("getContractNumber dataContract: " + dataContract);
			ContractObj contractObj = gson.fromJson(dataContract, ContractObj.class);
			if (ValidData.checkNull(contractObj.getUsername()) == false
					|| ValidData.checkNull(contractObj.getToken()) == false) {
				log.info("getContractNumber: " + contractObj.getUsername() + " invalid : ");
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				contractObjRes.setStatus(statusFale);
				contractObjRes.setMessage("Lay thong tin that bai - Invalid message request");
				contractObjRes.setContract_number("");
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(contractObjRes.toJSON())
						.build();
			}

			boolean checkLG = userInfo.checkLogin(contractObj.getUsername(), contractObj.getToken());
			if (checkLG) {
				// Check thong tin hop dong co trong DB chua
				BigDecimal getSeq = tbLoanRequestHome.getSeqContract();
				log.info("getContractNumber: " + contractObj.getUsername() + " getSeq : " + getSeq);
				if (getSeq != null) {
					Account acc = accountHome.getAccountUsename(contractObj.getUsername());
					if (ValidData.checkNullBranch(acc.getBranchId()) == true) {
						JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
						Iterator<String> keys = isJsonObject.keys();
						String branchID = "";
						while (keys.hasNext()) {
							branchID = keys.next();
						}
						String genContract = MainCfg.prefixContract + "." + branchID + "." + getSeq;
						log.info("getContractNumber: " + contractObj.getUsername() + " genContract : " + genContract);
						boolean checkContract = tbLoanRequestHome.checktblLoanRequest(genContract);
						if (checkContract) {
							log.info(
									"getContractNumber: " + contractObj.getUsername() + " genContract : " + genContract);
							contractObjRes.setStatus(statusSuccess);
							contractObjRes.setMessage("Lay thong tin thanh cong");
							contractObjRes.setContract_number(genContract);
						} else {
							log.info("contractObj: " + contractObj.getUsername() + " check login false:");
							contractObjRes.setStatus(statusFale);
							contractObjRes.setMessage("Lay thong tin that bai - Co the trung ma hop dong");
							contractObjRes.setContract_number("");
						}
					} else {
						log.info("contractObj: " + contractObj.getUsername() + " check login false:");
						contractObjRes.setStatus(statusFale);
						contractObjRes.setMessage("Lay thong tin that bai - User chua thuoc chi nhanh nao");
						contractObjRes.setContract_number("");
					}
				} else {
					log.info("contractObj: " + contractObj.getUsername() + " getSeq false:");
					contractObjRes.setStatus(statusFale);
					contractObjRes.setMessage("Lay thong tin that bai - Co loi xay ra");
					contractObjRes.setContract_number("");
				}
			} else {
				log.info("contractObj: " + contractObj.getUsername() + " check login false:");
				contractObjRes.setStatus(statusFale);
				contractObjRes.setMessage("Lay thong tin that bai - Login false");
				contractObjRes.setContract_number("");
			}
			log.info("contractObj: " + contractObj.getUsername() + " response to client:" + contractObjRes.toJSON());
			log.info("----------------Ket thuc contractObj ");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Lay ma hop dong");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataContract);
			tblSystemActions.setResponseData(contractObjRes.toJSON());
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(contractObjRes.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc contractObj Exception ", e);
			contractObjRes.setStatus(statusFale);
			contractObjRes.setMessage("Lay thong tin that bai - Co loi xay ra");
			contractObjRes.setContract_number("");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(contractObjRes.toJSON()).build();
		}
	}

	public Response getProduct(String dataProducReq) {
		log.info("----------------Bat dau getProduct--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ProducResAll producResAll = new ProducResAll();
		ProductRes productRes = new ProductRes();
		try {
			log.info("getProduct dataProducReq: " + dataProducReq);
			ProductReq productReq = gson.fromJson(dataProducReq, ProductReq.class);
			if (ValidData.checkNull(productReq.getUsername()) == false
					|| ValidData.checkNull(productReq.getToken()) == false
					|| ValidData.checkNullLong(productReq.getProduct_type()) == false
					|| ValidData.checkNull(productReq.getProduct_brand()) == false
					|| ValidData.checkNull(productReq.getProduct_modal()) == false) {
				log.info("getProduct: " + productReq.getUsername() + " invalid : ");
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				producResAll.setStatus(statusFale);
				producResAll.setSuggest_info(productRes);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(producResAll.toJSON()).build();
			}
			boolean checkLG = userInfo.checkLogin(productReq.getUsername(), productReq.getToken());
			if (checkLG) {
				TblProduct tblProduct = tblProductHome.getProduct(String.valueOf(productReq.getProduct_type()),
						productReq.getProduct_brand(), productReq.getProduct_modal());

				if (tblProduct != null) {
					producResAll.setStatus(statusSuccess);

					productRes.setProduct_type(tblProduct.getProductType());
					productRes.setProduct_brand(tblProduct.getProductName());
					productRes.setProduct_modal(tblProduct.getProductCode());
					productRes.setTotal_run(productReq.getTotal_run());
					productRes.setProduct_condition(productReq.getProduct_condition());
					productRes.setProduct_own_by_borrower(productReq.getProduct_own_by_borrower());
					productRes.setBuy_a_new_price(tblProduct.getBrandnewPrice());
					productRes.setLoan_price(tblProduct.getLoanPrice());
					// ?????nh gi?? = [ Gi?? vay ]x [ t??? l??? t??nh tr???ng s???n ph???m ] x [
					// t??? l??? KM ???? ??i ] x [ t??? l??? ch??nh ch??? ]
					// accept_loan_price: loan_price * product_condition *
					// total_run * product_own_by_borrower
					long accept_loan_price = productRes.getLoan_price() * productRes.getProduct_condition()
							* productRes.getTotal_run() * productRes.getProduct_own_by_borrower();
					productRes.setAccept_loan_price(accept_loan_price);

					producResAll.setSuggest_info(productRes);
				} else {
					log.info("getProduct: " + productReq.getUsername() + " tblProduct null:");
					producResAll.setStatus(statusFale);
					producResAll.setSuggest_info(productRes);
				}
			} else {
				log.info("getProduct: " + productReq.getUsername() + " check login false:");
				producResAll.setStatus(statusFale);
				producResAll.setSuggest_info(productRes);
			}
			log.info("getProduct: " + productReq.getUsername() + " response to client:" + producResAll.toJSON());
			log.info("----------------Ket thuc getProduct ");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Lay thong tin san pham");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataProducReq);
			tblSystemActions.setResponseData(producResAll.toJSON());
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(producResAll.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc getProduct Exception ", e);
			producResAll.setStatus(statusFale);
			producResAll.setSuggest_info(productRes);
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(producResAll.toJSON()).build();
		}
	}

	public Response getRateConfig(String dataRateConfig) {
		log.info("----------------Bat dau getRateConfig--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		RateConfigRes rateConfigRes = new RateConfigRes();
		List<TblRateConfig> arrRateCfg = new ArrayList<>();
		try {
			log.info("getRateConfig dataRateConfig: " + dataRateConfig);
			RateConfigReq rateConfigReq = gson.fromJson(dataRateConfig, RateConfigReq.class);
			if (ValidData.checkNull(rateConfigReq.getUsername()) == false
					|| ValidData.checkNull(rateConfigReq.getToken()) == false
					|| ValidData.checkNullInt(rateConfigReq.getType()) == false) {
				log.info("getRateConfig: " + rateConfigReq.getUsername() + " invalid : ");
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				rateConfigRes.setStatus(statusFale);
				rateConfigRes.setMessage("Lay thong tin that bai - Invalid message request");
				rateConfigRes.setRate_config(arrRateCfg);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(rateConfigRes.toJSON()).build();
			}
			boolean checkLG = userInfo.checkLogin(rateConfigReq.getUsername(), rateConfigReq.getToken());
			if (checkLG) {
				List<TblRateConfig> results = tblRateConfigHome.getRateConfig(rateConfigReq.getType());
				if (results != null) {
					rateConfigRes.setStatus(statusSuccess);
					rateConfigRes.setMessage("Lay thong tin thanh cong");
					rateConfigRes.setRate_config(results);
				} else {
					log.info("getProduct: " + rateConfigReq.getUsername() + " tblProduct null:");
					rateConfigRes.setStatus(statusFale);
					rateConfigRes.setMessage("Lay thong tin that bai - Khong tim thay thong tin RateConfig");
					rateConfigRes.setRate_config(arrRateCfg);
				}
			} else {
				log.info("getRateConfig: " + rateConfigReq.getUsername() + " check login false:");
				rateConfigRes.setStatus(statusFale);
				rateConfigRes.setMessage("Lay thong tin that bai - Thong tin login sai");
				rateConfigRes.setRate_config(arrRateCfg);
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("getRateConfig: " + rateConfigReq.getUsername() + " response to client:" + rateConfigRes.toJSON());
			log.info("----------------Ket thuc getRateConfig: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Lay thong tin ty le");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataRateConfig);
			tblSystemActions.setResponseData(rateConfigRes.toJSON());
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(rateConfigRes.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc getRateConfig Exception ", e);
			rateConfigRes.setStatus(statusFale);
			rateConfigRes.setMessage("Lay thong tin that bai -  Da co loi xay ra");
			rateConfigRes.setRate_config(arrRateCfg);
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(rateConfigRes.toJSON()).build();
		}
	}

	public Response createrLoan(String datacreaterLoan) {
		log.info("----------------Bat dau createrLoan--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResCreaterLoan resCreaterLoan = new ResCreaterLoan();
		try {
			log.info("createrLoan datacreaterLoan: " + datacreaterLoan);
			ReqCreaterLoan reqCreaterLoan = gson.fromJson(datacreaterLoan, ReqCreaterLoan.class);
			ResCreaterLoan resCreaterLoanValid = validData.validCreaterLoan(reqCreaterLoan);
			if (resCreaterLoanValid != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoanValid.toJSON())
						.build();
			}

			Account acc = accountHome.getAccountUsename(reqCreaterLoan.getUsername());
			String fullName = reqCreaterLoan.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
			int branch_id = 0;
			int room_id = 0;
			boolean checkRoom = false;
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					// System.out.println(msg);
					// System.out.println(msg.get(0));
					branch_id = Integer.parseInt(key);
					// if(ValidData.checkNull(msg.get(0).toString())){
					// room_id = Integer.parseInt(msg.get(0).toString());
					//
					// }
					for (int i = 0; i < msg.length(); i++) {
						System.out.println("aa: " + msg.get(i).toString());
						if (ValidData.checkNull(msg.get(i).toString()) == true) {
							room_id = Integer.parseInt(msg.get(i).toString());
							checkRoom = true;
							break;
						}
					}
				}
			}

			if (ValidData.checkNull(reqCreaterLoan.getBorrower_id_number()) == true) {
				// boolean checkTblBorrower =
				// dbFintechHome.checkTblBorrower(reqCreaterLoan.getBorrower_id_number());
				TblBorrower getTblBorrower = dbFintechHome.getTblBorrower(reqCreaterLoan.getBorrower_id_number());
				if (getTblBorrower == null) {
					int countTblBorrower = (int) dbFintechHome.countTblBorrower(branch_id);
					int countBR = countTblBorrower + 1;
					String borrowerCode = "";
					if (countBR < 10) {
						borrowerCode = branch_id + "." + "KH" + ".0" + countBR;
					} else {
						borrowerCode = branch_id + "." + "KH" + "." + countBR;
					}

					log.info("createrLoan checkTblBorrower null -> insert TblBorrower: ");
					TblBorrower tblBorrower = new TblBorrower();
					tblBorrower.setBorrowerName(reqCreaterLoan.getBorrower_name());
					tblBorrower.setBorrowerMobile(reqCreaterLoan.getBorrower_phone());
					tblBorrower.setBorrowerEmail(reqCreaterLoan.getBorrower_email());
					tblBorrower.setIdNumber(reqCreaterLoan.getBorrower_id_number());
					tblBorrower.setBorrowerCode(borrowerCode);
					tblBorrower.setBorrowerAddress(reqCreaterLoan.getBorrower_address());
					tblBorrower.setBorrowerBirthDay(
							new SimpleDateFormat("yyyyMMdd").parse(reqCreaterLoan.getBorrower_birthday()));
					tblBorrower.setIdIssuedBy(reqCreaterLoan.getId_issue_at());
					tblBorrower
							.setIdIssuedDate(new SimpleDateFormat("yyyyMMdd").parse(reqCreaterLoan.getId_issue_date()));
					tblBorrower.setBankBranch(reqCreaterLoan.getBank_branch());
					tblBorrower.setBorrowerType(Integer.parseInt(reqCreaterLoan.getBorrower_type()));
					tblBorrower.setBranchId(branch_id);
					boolean checkINSBorrower = dbFintechHome.createTblBorrower(tblBorrower);
					log.info("createrLoan checkINSBorrower : " + checkINSBorrower);
				} else {
					TblBlackList getTblBlackList = dbFintechHome.getTblBlackList(getTblBorrower.getBorrowerId());
					if (getTblBlackList != null) {
						resCreaterLoan.setStatus(statusFale);
						resCreaterLoan.setMessage("Yeu cau that bai - User nam trong danh sach blackList group: "
								+ getTblBlackList.getGroupDebtId());
						response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
						return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoan.toJSON())
								.build();
					}
				}
			}
			TblLoanRequestHome tblLoanReqDetailHome = new TblLoanRequestHome();
			BigInteger loanID = tblLoanReqDetailHome.getIDAutoIncrement();
			System.out.println(loanID);

			// 1 Nh???n ti???n qua - disburse_to - tbl_loan_req_detail
			// 2 Lo???i sa??n ph????m - borrower_type - tbl_loan_req_detail
			// 3 Th????ng hi????u - product_brand - tbl_loan_req_detail
			// 4 C??ch t??nh l??i - calculate_profit_type - tbl_loan_request
			// 5 S??? th??ng vay - loan_for_month - tbl_loan_request
			// 6 Serial H?? - product_serial_no - tbl_loan_req_detail
			// 7 Ng??y vay - created_date - tbl_loan_request
			// 8 Ng??y tr??? - disbursement_date - tbl_loan_req_detail

			TblLoanRequest tblLoanRequest = new TblLoanRequest();
			TblLoanReqDetail tblLoanReqDetail = new TblLoanReqDetail();
			tblLoanRequest = dbFintechHome.getTblLoanRequest(reqCreaterLoan.getLoan_code());
			List<Fees> feesListSet = reqCreaterLoan.getFees();
			String billID = Utils.getTimeNowDate() + "_" + Utils.getBillid();
			List<TblLoanBill> illustrationNewLoanBill = null;
			int insOrUpd = 0; // 0 insert, 1 update
			BigDecimal total_amount_must_pay = new BigDecimal(0);
			BigDecimal total_insterest_must_pay = new BigDecimal(0);

			BigDecimal total_monthly_interest = new BigDecimal(0); // tieefn
																	// laxi
			BigDecimal total_advisory_fee = new BigDecimal(0); // phi tu van
			BigDecimal total_service_fee = new BigDecimal(0); // phi dich vu

			if (tblLoanRequest.getLoanId() != null) {
				insOrUpd = 1;
				tblLoanReqDetail = dbFintechHome.getLoanDetail(tblLoanRequest.getLoanId());

				if ((reqCreaterLoan.getQuestion_and_answears()) != null
						&& !reqCreaterLoan.getQuestion_and_answears().isEmpty()) {
					boolean checkDeleteAskAns = dbFintechHome.deleteAskAns(tblLoanReqDetail.getLoanId());
					log.info("createrLoan deleteAskAns checkDeleteAskAns: " + checkDeleteAskAns);
				}
				if ((reqCreaterLoan.getImages()) != null && !reqCreaterLoan.getImages().isEmpty()) {
					boolean checkDelete = dbFintechHome.deleteTblImages(tblLoanReqDetail.getLoanId());
					log.info("createrLoan checkDelete IMG: " + checkDelete);
				}
				if (ValidData.checkNull(reqCreaterLoan.getLoan_amount()) == true
						&& ValidData.checkNull(reqCreaterLoan.getLoan_for_month()) == true
						&& ValidData.checkNull(reqCreaterLoan.getCalculate_profit_type()) == true) {
					System.out.println("Update khoan vay");
					boolean checkDeleteLoanBill = dbFintechHome.deleteTblLoanBill(tblLoanReqDetail.getLoanId());
					log.info("createrLoan deleteBill checkDeleteLoanBill: " + checkDeleteLoanBill);
					double sotienvay = Double.valueOf(reqCreaterLoan.getLoan_amount());
					double sothangvay = Double.valueOf(reqCreaterLoan.getLoan_for_month());
					double loaitrano = Double.valueOf(reqCreaterLoan.getCalculate_profit_type());
					illustrationNewLoanBill = caculator.illustrationNewLoanBill(reqCreaterLoan.getUsername(), billID,
							sotienvay, sothangvay, reqCreaterLoan.getLoan_expect_date(), loaitrano, feesListSet,
							tblLoanRequest.getLoanId());
				}
			} else {
				tblLoanRequest = new TblLoanRequest();
				tblLoanReqDetail = new TblLoanReqDetail();
				tblLoanRequest.setLoanId(loanID.intValue());
				tblLoanReqDetail.setLoanId(loanID.intValue());
				tblLoanReqDetail.setCreatedDate(new Date());
				tblLoanRequest.setFinalStatus(statusPending);
				tblLoanRequest.setPreviousStatus(statusPending);
				tblLoanRequest.setCreatedDate(new Date());
				tblLoanRequest.setCreatedBy(reqCreaterLoan.getUsername());
				double sotienvay = Double.valueOf(reqCreaterLoan.getLoan_amount());
				double sothangvay = Double.valueOf(reqCreaterLoan.getLoan_for_month());
				double loaitrano = Double.valueOf(reqCreaterLoan.getCalculate_profit_type());
				illustrationNewLoanBill = caculator.illustrationNewLoanBill(reqCreaterLoan.getUsername(), billID,
						sotienvay, sothangvay, reqCreaterLoan.getLoan_expect_date(), loaitrano, feesListSet,
						tblLoanRequest.getLoanId());
			}

			try {
				if (illustrationNewLoanBill != null) {
					for (TblLoanBill tblLoanBill : illustrationNewLoanBill) {
						// setAmtToDecrYourLoan So tien con lai
						total_amount_must_pay = total_amount_must_pay.add(tblLoanBill.getAmtToDecrYourLoan());
						// setMonthlyInterest
						total_insterest_must_pay = total_insterest_must_pay.add(tblLoanBill.getMonthlyInterest());

						total_monthly_interest = total_monthly_interest.add(tblLoanBill.getMonthlyInterest());
						total_advisory_fee = total_advisory_fee.add(tblLoanBill.getAdvisoryFee());
						total_service_fee = total_service_fee.add(tblLoanBill.getServiceFee());
						try {
							tblLoanRequest.setTotalMonthlyInterest(total_monthly_interest);
							tblLoanRequest.setTotalAdvisoryFee(total_advisory_fee);
							tblLoanRequest.setTotalServiceFee(total_service_fee);
						} catch (Exception e) {
							log.info("createrLoan: " + reqCreaterLoan.getUsername()
									+ " ex setTotalMonthlyInterest, setTotalAdvisoryFee, setTotalAdvisoryFee :" + e);
						}
					}
				}
			} catch (Exception e) {
				log.info("createrLoan: " + reqCreaterLoan.getUsername() + " ex sum So tien con lai vao :" + e);
			}

			tblLoanRequest.setEditedDate(new Date());
			tblLoanRequest.setExpireDate(new Date());
			tblLoanRequest.setApprovedDate(new Date());
			tblLoanRequest.setApprovedBy(reqCreaterLoan.getUsername());
			tblLoanRequest.setLatestUpdate(new Date());

			tblLoanRequest.setTotalAmountMustPay(total_amount_must_pay);
			tblLoanRequest.setTotalInsterestMustPay(total_insterest_must_pay);

			try {
				if (ValidData.checkNull(reqCreaterLoan.getLoan_code()) == true) {
					tblLoanRequest.setLoanCode(reqCreaterLoan.getLoan_code());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getLoan_name()) == true) {
					tblLoanRequest.setLoanName(reqCreaterLoan.getLoan_name());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getContract_serial_num()) == true) {
					tblLoanRequest.setContractSerialNum(reqCreaterLoan.getContract_serial_num());
				}
			} catch (Exception e) {
			}
			tblLoanRequest.setBranchId(branch_id);
			if (checkRoom) {
				tblLoanRequest.setRoomId(room_id);
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getCalculate_profit_type()) == true) {
					tblLoanRequest.setCalculateProfitType(Integer.parseInt(reqCreaterLoan.getCalculate_profit_type()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getLoan_for_month()) == true) {
					tblLoanRequest.setLoanForMonth(Integer.parseInt(reqCreaterLoan.getLoan_for_month()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getDisburse_to()) == true) {
					tblLoanReqDetail.setDisburseTo(Integer.parseInt(reqCreaterLoan.getDisburse_to()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getProduct_modal()) == true) {
					tblLoanReqDetail.setProductModal(reqCreaterLoan.getProduct_modal());
					tblLoanReqDetail.setProductId(Integer.parseInt(reqCreaterLoan.getProduct_modal()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getProduct_brand()) == true) {
					tblLoanReqDetail.setProductBrand(Integer.parseInt(reqCreaterLoan.getProduct_brand()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getTotal_run()) == true) {
					tblLoanReqDetail.setTotalRun(Integer.parseInt(reqCreaterLoan.getTotal_run()));
				}
			} catch (Exception e) {
			}
			// try {
			// if (ValidData.checkNull(reqCreaterLoan.getProduct_id()) == true)
			// {
			// tblLoanReqDetail.setProductId(Integer.parseInt(reqCreaterLoan.getProduct_id()));
			// }
			// } catch (Exception e) {
			// }
			try {
				if (ValidData.checkNull(reqCreaterLoan.getProduct_serial_no()) == true) {
					tblLoanReqDetail.setProductSerialNo(reqCreaterLoan.getProduct_serial_no());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getProduct_color()) == true) {
					tblLoanReqDetail.setProductColor(reqCreaterLoan.getProduct_color());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getProduct_machine_number()) == true) {
					tblLoanReqDetail.setProductMachineNumber(reqCreaterLoan.getProduct_machine_number());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getProduct_own_by_borrower()) == true) {
					tblLoanReqDetail
							.setProductOwnByBorrower(Integer.parseInt(reqCreaterLoan.getProduct_own_by_borrower()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getProduct_condition()) == true) {
					tblLoanReqDetail.setProductCondition(Integer.parseInt(reqCreaterLoan.getProduct_condition()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getBorrower_phone()) == true) {
					tblLoanReqDetail.setBorrowerPhone(reqCreaterLoan.getBorrower_phone());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getBorrower_email()) == true) {
					tblLoanReqDetail.setBorrowerEmail(reqCreaterLoan.getBorrower_email());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getBorrower_type()) == true) {
					tblLoanReqDetail.setBorrowerType(Integer.parseInt(reqCreaterLoan.getBorrower_type()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getLoan_expect_date()) == true) {
					tblLoanReqDetail.setDisbursementDate(Integer.parseInt(reqCreaterLoan.getLoan_expect_date()));
				}
			} catch (Exception e) {
			}

			try {
				int soNgay = Integer.parseInt(reqCreaterLoan.getLoan_for_month()) * 30;
				String ngayDaohan = Utils.getDaohan(reqCreaterLoan.getLoan_expect_date(), soNgay);
				tblLoanReqDetail.setSettlementDate(Integer.parseInt(ngayDaohan));
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getDisburse_to_bank_no()) == true) {
					tblLoanReqDetail.setDisburseToBankNo(reqCreaterLoan.getDisburse_to_bank_no());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getBorrower_id_number()) == true) {
					tblLoanReqDetail.setBorrowerId(reqCreaterLoan.getBorrower_id_number());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getProduct_type()) == true) {
					tblLoanReqDetail.setProductType(Integer.parseInt(reqCreaterLoan.getProduct_type()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getDisburse_to_bank_name()) == true) {
					tblLoanReqDetail.setDisburseToBankName(reqCreaterLoan.getDisburse_to_bank_name());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getDisburse_to_bank_code()) == true) {
					tblLoanReqDetail.setDisburseToBankCode(reqCreaterLoan.getDisburse_to_bank_code());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getLoan_amount()) == true) {
					tblLoanReqDetail.setExpectAmount(Long.parseLong(reqCreaterLoan.getLoan_amount()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getLoan_amount()) == true) {
					tblLoanReqDetail.setApprovedAmount(Long.parseLong(reqCreaterLoan.getLoan_amount()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getProduct_valuation()) == true) {
					tblLoanReqDetail.setProductValuation(Long.parseLong(reqCreaterLoan.getProduct_valuation()));
				}
			} catch (Exception e) {
			}

			tblLoanReqDetail.setEditedDate(new Date());
			try {
				if (ValidData.checkNull(reqCreaterLoan.getBorrower_address()) == true) {
					tblLoanReqDetail.setBorrowerAddress(reqCreaterLoan.getBorrower_address());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getBorrower_birthday()) == true) {
					tblLoanReqDetail.setBorrowerBirthday(Integer.parseInt(reqCreaterLoan.getBorrower_birthday()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getBorrower_income()) == true) {
					tblLoanReqDetail.setBorrowerIncome(Long.parseLong(reqCreaterLoan.getBorrower_income()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getBorrower_fullname()) == true) {
					tblLoanReqDetail.setBorrowerFullname(reqCreaterLoan.getBorrower_fullname());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getId_issue_at()) == true) {
					tblLoanReqDetail.setIdIssueAt(reqCreaterLoan.getId_issue_at());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getId_issue_date()) == true) {
					tblLoanReqDetail.setIdIssueDate(Integer.parseInt(reqCreaterLoan.getId_issue_date()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getProduct_desc()) == true) {
					tblLoanReqDetail.setProductDesc(reqCreaterLoan.getProduct_desc());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getBank_branch()) == true) {
					tblLoanReqDetail.setBankBranch(reqCreaterLoan.getBank_branch());
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getDisburse_to()) == true) {
					tblLoanReqDetail.setDisburseTo(Integer.parseInt(reqCreaterLoan.getDisburse_to()));
				}
			} catch (Exception e) {
			}
			try {
				if (ValidData.checkNull(reqCreaterLoan.getChange_fee()) == true) {
					tblLoanReqDetail.setChangeFee(Integer.parseInt(reqCreaterLoan.getChange_fee()));
				}
			} catch (Exception e) {
			}
			try {
				if ((reqCreaterLoan.getFees()) != null && !reqCreaterLoan.getFees().isEmpty()) {
					tblLoanReqDetail.setFees(gson.toJson(feesListSet));
				}
			} catch (Exception e) {
			}
			List<TblImages> imagesListSet = new ArrayList<>();
			if ((reqCreaterLoan.getImages()) != null && !reqCreaterLoan.getImages().isEmpty()) {
				List<ObjImage> imagesList = reqCreaterLoan.getImages();
				for (ObjImage objImage : imagesList) {
					TblImages tblImages = new TblImages();
					tblImages.setLoanRequestDetailId(tblLoanRequest.getLoanId());
					tblImages.setImageName(objImage.getImage_name());
					tblImages.setImageInputName(objImage.getImage_input_name());
					tblImages.setPartnerImageId(objImage.getPartner_image_id());
					tblImages.setImageType((int) objImage.getImage_type());
					tblImages.setImageByte(objImage.getImage_byte());
					tblImages.setImageUrl(objImage.getImage_url());
					tblImages.setImageIsFront((int) objImage.getImage_is_front());
					tblImages.setUploadBy(reqCreaterLoan.getUsername());
					tblImages.setCreatedDate(new Date());
					tblImages.setEditedDate(new Date());
					imagesListSet.add(tblImages);
				}
			} else {
				imagesListSet = null;
			}

			int percentAns = 0;
			TblLoanRequestAskAns tblLoanRequestAskAns = new TblLoanRequestAskAns();
			if ((reqCreaterLoan.getQuestion_and_answears()) != null
					&& !reqCreaterLoan.getQuestion_and_answears().isEmpty()) {
				List<ObjQuestions> questionsList = reqCreaterLoan.getQuestion_and_answears();
				tblLoanRequestAskAns.setLoanId(tblLoanRequest.getLoanId());
				tblLoanRequestAskAns.setQAThamDinh1(gson.toJson(questionsList));
			} else {
				tblLoanRequestAskAns = null;
			}

			log.info("createrLoan: " + reqCreaterLoan.getUsername() + " percentAns: " + percentAns);
			System.out.println("createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanReqDetail: "
					+ gson.toJson(tblLoanReqDetail));
			log.info("createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanReqDetail: "
					+ gson.toJson(tblLoanReqDetail));
			log.info("createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanRequest: " + gson.toJson(tblLoanRequest));
			log.info("createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanRequestAskAns: "
					+ gson.toJson(tblLoanRequestAskAns));

			boolean checkINS = tblLoanReqDetailHome.createLoanTrans(insOrUpd, tblLoanRequest, tblLoanReqDetail,
					imagesListSet, illustrationNewLoanBill, tblLoanRequestAskAns);
			if (checkINS) {
				log.info("createrLoan: " + reqCreaterLoan.getUsername() + " thanh cong:");
				log.info("createrLoan: " + reqCreaterLoan.getUsername() + " percentAns:");
				// if(percentAns <= 50){
				// resCreaterLoan.setStatus(tblLoanRequest.getFinalStatus());
				// resCreaterLoan.setMessage("Khoan vay bi tu choi do thieu
				// thong tin");
				// resCreaterLoan.setRequest_code(loanID.longValue());
				// }else{
				log.info("createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanRequest.getLoanId():"
						+ tblLoanRequest.getLoanId());
				resCreaterLoan.setStatus(statusSuccess);
				resCreaterLoan.setMessage("Yeu cau dang duoc xu ly");
				resCreaterLoan.setRequest_code(tblLoanRequest.getLoanId());
				// }
				TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
				tblLoanExpertiseSteps.setLoanId(tblLoanRequest.getLoanId());
				tblLoanExpertiseSteps.setExpertiseUser(fullName);
				tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
				tblLoanExpertiseSteps.setExpertiseStatus(tblLoanRequest.getFinalStatus());
				tblLoanExpertiseSteps.setExpertiseStep(1);
				tblLoanExpertiseSteps.setExpertiseComment("");
				tblLoanExpertiseSteps.setLoanCode(tblLoanRequest.getLoanCode());
				tblLoanExpertiseSteps.setAction(reqCreaterLoan.getAction());

				// log.info("createrLoan ThreadInsertLogStep",
				// LogType.BUSSINESS);
				// boolean checkINSExpertiseSteps =
				// dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
				// log.info("createrLoan ThreadInsertLogStep checkINS: " +
				// checkINSExpertiseSteps);
				Thread t = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
				t.start();

			} else {
				log.info("createrLoan: " + reqCreaterLoan.getUsername() + " that bai:");
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage("Yeu cau that bai");
				// resCreaterLoan.setRequest_code();
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("createrLoan: " + reqCreaterLoan.getUsername() + " response to client:" + resCreaterLoan.toJSON());
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Tao khoan vay");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(datacreaterLoan);
			tblSystemActions.setResponseData(resCreaterLoan.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			log.info("----------------Ket thuc createrLoan: ");
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoan.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc createrLoan Exception ", e);
			resCreaterLoan.setStatus(statusFale);
			resCreaterLoan.setMessage("Yeu cau that bai - Da co loi xay ra");
			// resCreaterLoan.setRequest_code("");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoan.toJSON()).build();
		}
	}

	public Response getContractList(String dataGetContractList) {
		log.info("----------------Bat dau getContractList--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResAllContractList resAllContractList = new ResAllContractList();
		List<ResContractList> resContractList = new ArrayList<>();
		try {
			log.info("getContractList dataGetContractList: " + dataGetContractList);
			ReqContractList reqContractList = gson.fromJson(dataGetContractList, ReqContractList.class);
			ResAllContractList resCreaterLoanValid = validData.validGetContractList(reqContractList);
			if (resCreaterLoanValid != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoanValid.toJSON())
						.build();
			}
			Account acc = accountHome.getAccountUsename(reqContractList.getUsername());
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();

			if (ValidData.checkNull(reqContractList.getBranch_id()) == true) {
				branchID.add(Integer.parseInt(reqContractList.getBranch_id()));
				if (ValidData.checkNull(reqContractList.getRoom_id()) == true) {
					roomID.add(Integer.parseInt(reqContractList.getRoom_id()));
				}
			} else {
				if (ValidData.checkNull(acc.getBranchId()) == true) {
					JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
					Iterator<String> keys = isJsonObject.keys();
					while (keys.hasNext()) {
						String key = keys.next();
						System.out.println(key);
						JSONArray msg = (JSONArray) isJsonObject.get(key);
						System.out.println(msg.length());
						System.out.println("aaaaa");

						branchID.add(new Integer(key.toString()));
						for (int i = 0; i < msg.length(); i++) {
							System.out.println("aa: " + msg.get(i).toString());
							if (ValidData.checkNull(msg.get(i).toString()) == true) {
								roomID.add(Integer.parseInt(msg.get(i).toString()));
							}
						}
					}
				}
			}
			// System.out.println("branchID: " + branchID);
			// System.out.println("roomID: " + roomID);
			String loan_code = "";
			try {
				loan_code = reqContractList.getLoan_code();
			} catch (Exception e) {
			}
			List<Integer> final_statusAR = new ArrayList<>();
			try {
				List<String> final_status = reqContractList.getFinal_status();
				for (String string : final_status) {
					final_statusAR.add(Integer.parseInt(string));
				}
			} catch (Exception e) {
			}
			List<Integer> insurance_statusAR = new ArrayList<>();
			try {
				System.out.println(reqContractList.getInsurance_status().size());
				if (reqContractList.getInsurance_status().size() > 0) {
					List<String> insurance_status = reqContractList.getInsurance_status();
					for (String string : insurance_status) {
						insurance_statusAR.add(Integer.parseInt(string));
					}
				}
			} catch (Exception e) {
			}
			String id_number = reqContractList.getId_number();
			String borrower_name = reqContractList.getBorrower_name();
			String from_date = reqContractList.getFrom_date();
			String to_date = reqContractList.getTo_date();
			String calculate_profit_type = reqContractList.getCalculate_profit_type();
			List<ResContractList> lisResContract = dbFintechHome.listResContractList(branchID, roomID, loan_code,
					final_statusAR, insurance_statusAR, id_number, borrower_name, from_date, to_date,
					calculate_profit_type, reqContractList.getLimit(), reqContractList.getOffSet());
			long total = dbFintechHome.listCountContractList(branchID, roomID, loan_code, final_statusAR,
					insurance_statusAR, id_number, borrower_name, from_date, to_date, calculate_profit_type,
					reqContractList.getLimit(), reqContractList.getOffSet());
			if (lisResContract != null) {
				resAllContractList.setStatus(statusSuccess);
				resAllContractList.setMessage("Yeu cau thanh cong");
				resAllContractList.setContract_list(lisResContract);
				resAllContractList.setTotalRecord(total);
			} else {
				resAllContractList.setStatus(statusFale);
				resAllContractList.setMessage("Yeu cau that bai - Da co loi xay ra");
				resAllContractList.setContract_list(resContractList);
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("getContractList: " + reqContractList.getUsername() + " response to client:"
					+ resAllContractList.toJSON());
			log.info("----------------Ket thuc getContractList: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Lay danh sach khoan vay");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataGetContractList);
			tblSystemActions.setResponseData(resAllContractList.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllContractList.toJSON())
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("----------------Ket thuc getContractList Exception ", e);
			resAllContractList.setStatus(statusFale);
			resAllContractList.setMessage("Yeu cau that bai - Da co loi xay ra");
			resAllContractList.setContract_list(resContractList);
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllContractList.toJSON())
					.build();
		}
	}

	public Response getLogStepsList(String dataLogStepsList) {
		log.info("----------------Bat dau getLogStepsList--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResStepLog resStepLog = new ResStepLog();
		try {
			log.info("getLogStepsList dataLogStepsList: " + dataLogStepsList);
			ReqStepLog reqStepLog = gson.fromJson(dataLogStepsList, ReqStepLog.class);
			ResStepLog resStepLog2 = validData.validGetLogStepsList(reqStepLog);
			if (resStepLog2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resStepLog2.toJSON()).build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqStepLog.getUsername());
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					branchID.add(new Integer(key.toString()));
					for (int i = 0; i < msg.length(); i++) {
						if (ValidData.checkNull(msg.get(i).toString()) == true) {
							roomID.add(Integer.parseInt(msg.get(i).toString()));
						}
					}
				}
			}
			// boolean checkLoan = dbFintechHome.checkLoan(branchID, roomID,
			// reqStepLog.getLoan_id());
			TblLoanRequest tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqStepLog.getLoan_id());
			if (tblLoanRequest != null) {
				List<TblLoanExpertiseSteps> getLoanExpertiseSteps = dbFintechHome
						.getLoanExpertiseSteps(tblLoanRequest.getLoanId());
				if (getLoanExpertiseSteps != null) {
					resStepLog.setStatus(statusSuccess);
					resStepLog.setMessage("Yeu cau thanh cong");
					resStepLog.setLoan_id(reqStepLog.getLoan_id());
					resStepLog.setLoan_logs(getLoanExpertiseSteps);
				} else {
					resStepLog.setStatus(statusSuccess);
					resStepLog.setMessage("Yeu cau thanh cong - Khong co log cua hop dong nay");
					resStepLog.setLoan_id(reqStepLog.getLoan_id());
				}
			} else {
				resStepLog.setStatus(statusFale);
				resStepLog.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("getLogStepsList: " + reqStepLog.getUsername() + " response to client:" + reqStepLog.toJSON());
			log.info("----------------Ket thuc getContractList: ");
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resStepLog.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc getLogStepsList Exception ", e);
			resStepLog.setStatus(statusFale);
			resStepLog.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resStepLog.toJSON()).build();
		}
	}

	public Response getContractDetail(String dataContractDetail) {
		log.info("----------------Bat dau getContractDetail--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResContractDetail resContractDetail = new ResContractDetail();
		try {
			log.info("getContractDetail datacreaterContractList: " + dataContractDetail);
			ReqStepLog reqStepLog = gson.fromJson(dataContractDetail, ReqStepLog.class);
			ResContractDetail resContractDetail2 = validData.validgetContractDetail(reqStepLog);
			if (resContractDetail2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resContractDetail2.toJSON())
						.build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqStepLog.getUsername());
			TblLoanRequest tblLoanRequest = null;
			// System.out.println(acc.getBranchId());
			TblSponsor getSponsoID = dbFintechHome.getSponsoID(acc.getRowId());
			// TblInsuranceProviders tblInsuranceProviders = null;
			// System.out.println(acc.getRowId());
			InsuranceProviders getInsurancePro = null;
			if (getSponsoID != null) {
				// acc la nha dau tu
				tblLoanRequest = dbFintechHome.getLoanRoleNDT(reqStepLog.getLoan_id());
				TblLoanSponsorMapp loanSponsorMapp = new TblLoanSponsorMapp();
				loanSponsorMapp = dbFintechHome.getLoanSponsorMapp(tblLoanRequest.getLoanId(),
						getSponsoID.getSponsorAccountId());
				if (loanSponsorMapp != null) {
					try {
						getInsurancePro = dbFintechHome.getInsurancePro(loanSponsorMapp.getInsuranceProviderId());
						try {
							List<TblInsuranceBanks> getInsuranceBanks = dbFintechHome
									.getInsuranceBanks(loanSponsorMapp.getInsuranceProviderId());
							getInsurancePro.setInsuranceProviderId(loanSponsorMapp.getInsuranceProviderId());
							getInsurancePro.setInsuranceBanks(getInsuranceBanks);
						} catch (Exception e) {
						}
					} catch (Exception e) {
					}
				} else {
					tblLoanRequest = null;
				}
			} else {
				// acc khong phai nha dau tu
				if (ValidData.checkNull(acc.getBranchId()) == true && (!acc.getBranchId().equals("0"))) {
					JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
					Iterator<String> keys = isJsonObject.keys();
					while (keys.hasNext()) {
						String key = keys.next();
						System.out.println(key);
						JSONArray msg = (JSONArray) isJsonObject.get(key);
						branchID.add(new Integer(key.toString()));
						for (int i = 0; i < msg.length(); i++) {
							if (ValidData.checkNull(msg.get(i).toString()) == true) {
								roomID.add(Integer.parseInt(msg.get(i).toString()));
							}
						}
					}
				}
				if (acc.getRolesId().equals("4")) {
					// Tai khoan la giao dich vien
					tblLoanRequest = dbFintechHome.getLoanCreaterBy(acc.getEmail(), branchID, roomID,
							reqStepLog.getLoan_id());
				} else {
					// Tai khoan khong phai giao dich vien
					tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqStepLog.getLoan_id());
				}
				if (tblLoanRequest != null) {
					TblLoanSponsorMapp loanSponsorMapp = new TblLoanSponsorMapp();
					loanSponsorMapp = dbFintechHome.getLoanSponsorMappLoanID(tblLoanRequest.getLoanId());
					if (loanSponsorMapp != null) {
						try {
							getInsurancePro = dbFintechHome.getInsurancePro(loanSponsorMapp.getInsuranceProviderId());
							try {
								List<TblInsuranceBanks> getInsuranceBanks = dbFintechHome
										.getInsuranceBanks(loanSponsorMapp.getInsuranceProviderId());
								getInsurancePro.setInsuranceProviderId(loanSponsorMapp.getInsuranceProviderId());
								getInsurancePro.setInsuranceBanks(getInsuranceBanks);
							} catch (Exception e) {
							}
						} catch (Exception e) {
						}
					}
				}
			}

			// boolean checkLoan = dbFintechHome.checkLoan(branchID, roomID,
			// Integer.parseInt(reqStepLog.getLoan_id()));
			if (tblLoanRequest != null) {
				resContractDetail.setStatus(statusSuccess);
				resContractDetail.setMessage("Yeu cau thanh cong");
				resContractDetail.setCreated_by(tblLoanRequest.getCreatedBy());
				resContractDetail.setCreated_date(
						Utils.convertDateToString("yyyy/MM/dd HH:mm:ss", tblLoanRequest.getCreatedDate()));
				resContractDetail.setApproved_by(tblLoanRequest.getApprovedBy());
				resContractDetail.setApproved_date(
						Utils.convertDateToString("yyyy/MM/dd HH:mm:ss", tblLoanRequest.getApprovedDate()));
				resContractDetail.setFinal_status(tblLoanRequest.getFinalStatus().toString());
				resContractDetail.setLoan_code(tblLoanRequest.getLoanCode());
				resContractDetail.setLoan_name(tblLoanRequest.getLoanName());
				resContractDetail.setCalculate_profit_type(tblLoanRequest.getCalculateProfitType().toString());
				resContractDetail.setLoan_for_month(tblLoanRequest.getLoanForMonth().toString());

				try {
					resContractDetail.setInsurance_status(tblLoanRequest.getInsuranceStatus().toString());
				} catch (Exception e) {
					resContractDetail.setInsurance_status("");
				}
				try {
					resContractDetail.setInsurance_fee_amt(tblLoanRequest.getInsuranceFeeAmt().toString());
				} catch (Exception e) {
					resContractDetail.setInsurance_fee_amt("");
				}
				try {
					resContractDetail.setInsurance_paid_amount(tblLoanRequest.getInsurancePaidAmount().toString());
				} catch (Exception e) {
					resContractDetail.setInsurance_paid_amount("");
				}
				try {
					resContractDetail.setInsurance_fee_fr_api(tblLoanRequest.getInsuranceFeeFrApi().toString());
				} catch (Exception e) {
					resContractDetail.setInsurance_fee_fr_api("");
				}
				try {
					resContractDetail
							.setInsurance_paid_amt_fr_api(tblLoanRequest.getInsurancePaidAmtFrApi().toString());
				} catch (Exception e) {
					resContractDetail.setInsurance_paid_amt_fr_api("");
				}
				try {
					resContractDetail.setPartner_insurance_code(tblLoanRequest.getPartnerInsuranceCode().toString());
				} catch (Exception e) {
					resContractDetail.setPartner_insurance_code("");
				}
				try {
					resContractDetail.setPartner_insurance_id(tblLoanRequest.getPartnerInsuranceId().toString());
				} catch (Exception e) {
					resContractDetail.setPartner_insurance_id("");
				}
				// Nh???n ti???n qua - disburse_to - tbl_loan_req_detail
				// Lo???i sa??n ph????m - borrower_type - tbl_loan_req_detail
				// Th????ng hi????u - product_brand - tbl_loan_req_detail
				// C??ch t??nh l??i - calculate_profit_type - tbl_loan_request
				// S??? th??ng vay - loan_for_month - tbl_loan_request
				// Serial H?? - product_serial_no - tbl_loan_req_detail
				// Ng??y vay - created_date - tbl_loan_request

				TblLoanReqDetail tblLoanReqDetail = dbFintechHome.getLoanDetail(tblLoanRequest.getLoanId());
				try {
					ResContractList getBranchRoom = dbFintechHome.getBranchRoom(tblLoanRequest.getBranchId(),
							tblLoanRequest.getRoomId(), tblLoanRequest.getLoanId());
					resContractDetail.setBranch_id(getBranchRoom.getBranch_id());
					resContractDetail.setBranch(getBranchRoom.getBranch_name());
					resContractDetail.setRoom(getBranchRoom.getRoom_code());
				} catch (Exception e) {
				}
				try {
					List<TblImages> tblImages = dbFintechHome.getTblImagesJoin(tblLoanRequest.getLoanId());
					resContractDetail.setImages(tblImages);
				} catch (Exception e) {
				}
				try {
					List<TblLoanRequestAskAns> tblLoanRequestAskAns = dbFintechHome
							.getRequestAskAns(tblLoanRequest.getLoanId());

					List<TblLoanRequestAskAnsGen> resListLoanRequestAskAns = new ArrayList<>();
					Type listTypeAskAns1 = new TypeToken<List<Object>>() {
					}.getType();
					Type listTypeAskAns2 = new TypeToken<List<Object>>() {
					}.getType();
					for (TblLoanRequestAskAns tblAskAns : tblLoanRequestAskAns) {
						TblLoanRequestAskAnsGen tblLoanRequestAskAnsGen = new TblLoanRequestAskAnsGen();
						ArrayList<Object> lstObj1 = GsonUltilities.fromJson(tblAskAns.getQAThamDinh1(),
								listTypeAskAns1);
						ArrayList<Object> lstObj2 = GsonUltilities.fromJson(tblAskAns.getQAThamDinh2(),
								listTypeAskAns2);
						tblLoanRequestAskAnsGen.setQAId(tblAskAns.getQAId());
						tblLoanRequestAskAnsGen.setLoanId(tblAskAns.getLoanId());
						tblLoanRequestAskAnsGen.setThamDinh1Rate(tblAskAns.getThamDinh1Rate());
						tblLoanRequestAskAnsGen.setThamDinh2Rate(tblAskAns.getThamDinh2Rate());
						tblLoanRequestAskAnsGen.setQAThamDinh1(lstObj1);
						tblLoanRequestAskAnsGen.setQAThamDinh2(lstObj2);
						resListLoanRequestAskAns.add(tblLoanRequestAskAnsGen);
					}
					resContractDetail.setQuestion_and_answears(resListLoanRequestAskAns);
				} catch (Exception e) {
				}
				Type listType = new TypeToken<List<Object>>() {
				}.getType();
				ArrayList<Object> lstObj = GsonUltilities.fromJson(tblLoanReqDetail.getFees(), listType);
				resContractDetail.setFees(lstObj);
				tblLoanReqDetail.setFees("");
				resContractDetail.setLoan_req_detail(tblLoanReqDetail);
				try {
					List<TblLoanBill> listLoanBill = tblLoanBillHome.getTblLoanBill(tblLoanRequest.getLoanId());
					resContractDetail.setLoanBill(listLoanBill);
				} catch (Exception e) {
				}
				if (getInsurancePro != null) {
					resContractDetail.setInsurance_providers(getInsurancePro);
				}

			} else {
				resContractDetail.setStatus(statusFale);
				resContractDetail.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("getContractDetail: " + reqStepLog.getUsername() + " response to client:"
					+ resContractDetail.toJSON().replace("'\'", ""));
			log.info("----------------Ket thuc getContractDetail: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Lay chi tiet khoan vay");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataContractDetail);
			tblSystemActions.setResponseData(resContractDetail.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resContractDetail.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("----------------Ket thuc getContractDetail Exception ", e);
			resContractDetail.setStatus(statusFale);
			resContractDetail.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resContractDetail.toJSON()).build();
		}
	}

	// Update tr???ng th??i giao d???ch
	public Response updateStatus(String dataUpdateStatus) {
		log.info("----------------Bat dau updateStatus--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResUpdateStatus resUpdateStatus = new ResUpdateStatus();
		try {
			log.info("updateStatus dataUpdateStatus: " + dataUpdateStatus);
			ReqUpdateStatus reqUpdateStatus = gson.fromJson(dataUpdateStatus, ReqUpdateStatus.class);
			ResUpdateStatus resUpdateStatus2 = validData.validUpdateStatus(reqUpdateStatus);
			if (resUpdateStatus2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUpdateStatus2.toJSON())
						.build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqUpdateStatus.getUsername());
			String fullName = reqUpdateStatus.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					// System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					branchID.add(new Integer(key.toString()));
					for (int i = 0; i < msg.length(); i++) {
						if (ValidData.checkNull(msg.get(i).toString()) == true) {
							roomID.add(Integer.parseInt(msg.get(i).toString()));
						}
					}
				}
			}
			System.out.println(gson.toJson(reqUpdateStatus));

			TblLoanRequest tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqUpdateStatus.getLoan_code());

			if (tblLoanRequest != null) {
				log.info("updateStatus tblLoanRequest: " + gson.toJson(tblLoanRequest));
				log.info("updateStatus tblLoanRequest.getLoanId: " + tblLoanRequest.getLoanId());
				tblLoanRequest.setPreviousStatus(tblLoanRequest.getFinalStatus());
				tblLoanRequest.setFinalStatus(Integer.parseInt(reqUpdateStatus.getFinal_status()));
				tblLoanRequest.setLatestUpdate(new Date());

				boolean checkUPD = tbLoanRequestHome.updateLoanRequest(tblLoanRequest);

				if (checkUPD) {
					resUpdateStatus.setStatus(statusSuccess);
					resUpdateStatus.setMessage("Yeu cau thanh cong");
					resUpdateStatus.setLoan_code(reqUpdateStatus.getLoan_code());
					TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
					tblLoanExpertiseSteps.setLoanId(tblLoanRequest.getLoanId());
					tblLoanExpertiseSteps.setExpertiseUser(fullName);
					tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
					tblLoanExpertiseSteps.setExpertiseStatus(tblLoanRequest.getFinalStatus());
					tblLoanExpertiseSteps.setExpertiseStep(2);
					tblLoanExpertiseSteps.setExpertiseComment(reqUpdateStatus.getMemo());
					tblLoanExpertiseSteps.setLoanCode(tblLoanRequest.getLoanCode());
					try {
						tblLoanExpertiseSteps.setAction(reqUpdateStatus.getAction());
					} catch (Exception e) {
					}
					// log.info("updateStatus ThreadInsertLogStep",
					// LogType.BUSSINESS);
					// boolean checkINSExpertiseSteps =
					// dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
					// log.info("updateStatus ThreadInsertLogStep
					// checkINS: " + checkINSExpertiseSteps);
					Thread t = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
					t.start();

				} else {
					resUpdateStatus.setStatus(statusFale);
					resUpdateStatus.setMessage("Yeu cau that bai -  Da co loi xay ra");
					resUpdateStatus.setLoan_code(reqUpdateStatus.getLoan_code());
				}
			} else {
				log.info("updateStatus tblLoanRequest null: ");
				resUpdateStatus.setStatus(statusFale);
				resUpdateStatus.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("updateStatus: " + reqUpdateStatus.getUsername() + " response to client:"
					+ resUpdateStatus.toJSON().replace("'\'", ""));
			log.info("----------------Ket thuc updateStatus: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API update status");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataUpdateStatus);
			tblSystemActions.setResponseData(resUpdateStatus.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUpdateStatus.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc updateStatus Exception ", e);
			resUpdateStatus.setStatus(statusFale);
			resUpdateStatus.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUpdateStatus.toJSON()).build();
		}
	}

	// Ph??n b??? nh?? ?????u t??
	public Response setAllotment(String dataAllotment) {
		log.info("----------------Bat dau setAllotment--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResAllotment resAllotment = new ResAllotment();
		try {
			log.info("setAllotment dataUpdateStatus: " + dataAllotment);
			ReqAllotment reqAllotment = gson.fromJson(dataAllotment, ReqAllotment.class);
			ResAllotment resAllotment2 = validData.validSetAllotment(reqAllotment);
			if (resAllotment2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllotment2.toJSON()).build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqAllotment.getUsername());
			String fullName = reqAllotment.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					branchID.add(new Integer(key.toString()));
					for (int i = 0; i < msg.length(); i++) {
						if (ValidData.checkNull(msg.get(i).toString()) == true) {
							roomID.add(Integer.parseInt(msg.get(i).toString()));
						}
					}
				}
			}
			System.out.println(gson.toJson(reqAllotment));

			TblLoanRequest tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqAllotment.getLoan_code());
			// System.out.println(tblLoanRequest);
			// System.out.println(tblLoanRequest.getLoanId());
			if (tblLoanRequest != null) {
				// TblLoanReqDetail tblLoanReqDetail =
				// dbFintechHome.getLoanDetail(tblLoanRequest.getLoanId());
				log.info("setAllotment tblLoanRequest: " + gson.toJson(tblLoanRequest));
				log.info("setAllotment tblLoanRequest.getLoanId: " + tblLoanRequest.getLoanId());
				tblLoanRequest.setPreviousStatus(tblLoanRequest.getFinalStatus());
				tblLoanRequest.setFinalStatus(113); // 113 ???? ph??n b???
				tblLoanRequest.setLatestUpdate(new Date());

				boolean checkUPD = tbLoanRequestHome.updateLoanRequest(tblLoanRequest);

				if (checkUPD) {

					// List<Integer> sponsorAR = new ArrayList<>();
					try {
						List<String> sponsor = reqAllotment.getSponsor();
						for (String string : sponsor) {

							TblLoanSponsorMapp tblLoanSponsorMapp = new TblLoanSponsorMapp();
							tblLoanSponsorMapp.setLoanId(tblLoanRequest.getLoanId());
							tblLoanSponsorMapp.setSponsorId(Integer.parseInt(string));
							tblLoanSponsorMapp.setCreatedDate(Utils.getTimeStampNow());
							// tblLoanSponsorMapp.setDisbursementDate(Utils.stringToTimestamp(tblLoanReqDetail.getDisbursementDate().toString()));
							tblLoanSponsorMapp.setDisbursementStatus(0);
							tblLoanSponsorMapp.setEntryExpireTime(Utils.getMin15());
							try {
								tblLoanSponsorMapp.setInsuranceProviderId(
										Integer.parseInt(reqAllotment.getInsurance_provider_id()));
							} catch (Exception e) {
							}
							boolean checkINS = dbFintechHome.createTblLoanSponsorMapp(tblLoanSponsorMapp);
							log.info("setAllotment tblLoanRequest.getLoanId: " + tblLoanRequest.getLoanId()
									+ " createTblLoanSponsorMapp: " + checkINS);
						}
					} catch (Exception e) {
					}
					resAllotment.setStatus(statusSuccess);
					resAllotment.setMessage("Yeu cau thanh cong");
					resAllotment.setLoan_code(reqAllotment.getLoan_code());

					TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
					tblLoanExpertiseSteps.setLoanId(tblLoanRequest.getLoanId());
					tblLoanExpertiseSteps.setExpertiseUser(fullName);
					tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
					tblLoanExpertiseSteps.setExpertiseStatus(tblLoanRequest.getFinalStatus());
					tblLoanExpertiseSteps.setExpertiseStep(2);
					tblLoanExpertiseSteps.setExpertiseComment(reqAllotment.getExpertise_comment());
					tblLoanExpertiseSteps.setLoanCode(tblLoanRequest.getLoanCode());
					try {
						tblLoanExpertiseSteps.setAction(reqAllotment.getAction());
					} catch (Exception e) {
					}
					// log.info("setAllotment ThreadInsertLogStep",
					// LogType.BUSSINESS);
					// boolean checkINSExpertiseSteps =
					// dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
					// log.info("setAllotment ThreadInsertLogStep
					// checkINS: " + checkINSExpertiseSteps);
					Thread t = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
					t.start();

				} else {
					resAllotment.setStatus(statusFale);
					resAllotment.setMessage("Yeu cau that bai -  Da co loi xay ra");
				}
			} else {
				log.info("setAllotment tblLoanRequest null: ");
				resAllotment.setStatus(statusFale);
				resAllotment.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("setAllotment: " + reqAllotment.getUsername() + " response to client:"
					+ resAllotment.toJSON().replace("'\'", ""));
			log.info("----------------Ket thuc setAllotment: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Phan bo nha dau tu");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataAllotment);
			tblSystemActions.setResponseData(resAllotment.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllotment.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc setAllotment Exception ", e);
			resAllotment.setStatus(statusFale);
			resAllotment.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllotment.toJSON()).build();
		}
	}

	public Response getBank(String dataGetbank) {
		log.info("----------------Bat dau getBank--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		BankRes bankRes = new BankRes();
		try {
			log.info("getBank dataGetbank: " + dataGetbank);
			BankReq reqBankReq = gson.fromJson(dataGetbank, BankReq.class);

			List<TblBanks> getTblBanks = tblBanksHome.getTblBanks(1, reqBankReq.getBank_support_function());
			if (getTblBanks != null) {
				log.info("getBank: " + reqBankReq.getUsername() + " thanh cong:");
				bankRes.setStatus(statusSuccess);
				bankRes.setMessage("Yeu cau thanh cong");
				bankRes.setBanks(getTblBanks);
			} else {
				log.info("getBank: " + reqBankReq.getUsername() + " that bai getTblBanks null");
				bankRes.setStatus(statusFale);
				bankRes.setMessage("Yeu cau that bai - Da co loi xay ra");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("getBank: " + reqBankReq.getUsername() + " response to client:" + bankRes.toJSON());
			log.info("----------------Ket thuc getBank: ");
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(bankRes.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc getBank Exception ", e);
			bankRes.setStatus(statusFale);
			bankRes.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(bankRes.toJSON()).build();
		}
	}

	// Minh hoa khoan vay
	public Response getIllustration(String dataIllustration) {
		log.info("----------------Bat dau getIllustration--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ObjBillRes objBillRes = new ObjBillRes();
		String billID = Utils.getTimeNowDate() + "_" + Utils.getBillid();
		try {
			log.info(" dataIllustration: " + dataIllustration);
			ObjReqFee objReqFee = gson.fromJson(dataIllustration, ObjReqFee.class);
			if (ValidData.checkNull(objReqFee.getUsername()) == false
					|| ValidData.checkNull(objReqFee.getToken()) == false) {
				log.info("getIllustration: " + objReqFee.getUsername() + " invalid : ");
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				objBillRes.setStatus(statusFale);
				objBillRes.setMessage("Yeu cau that bai - Invalid message request");
				objBillRes.setBilling_tmp_code("");
				objBillRes.setCollection("");
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(objBillRes.toJSON()).build();
			}
			boolean checkLG = userInfo.checkLogin(objReqFee.getUsername(), objReqFee.getToken());
			if (checkLG) {
				log.info("getIllustration: " + objReqFee.getUsername() + " checkLG:" + checkLG);
				// String billID = getTimeNowDate() + "_" + getBillid();
				double sotienvay = (double) objReqFee.getLoan_amount();
				double sothangvay = (double) objReqFee.getLoan_for_month();
				double loaitrano = (double) objReqFee.getCalculate_profit_type();
				List<Fees> listFee = objReqFee.getFees();

				// '1:Lai suat, 2:Phi tu van, 3:phi dich vu,4:phitra no tr????c
				// han,5:phi tat toan truoc han',
				System.out.println("aaaa");
				// Bussiness bussiness = new Bussiness();
				String loanID = "";
				ArrayList<Document> illustrationIns = caculator.illustrationNew(objReqFee.getUsername(), billID,
						sotienvay, sothangvay, objReqFee.getLoan_expect_date(), loaitrano, listFee, loanID);
				log.info("getIllustration: " + objReqFee.getUsername() + " illustrationIns:" + illustrationIns);
				boolean checkInsMongo = mongoDB.insertDocument(illustrationIns, "tbl_minhhoa");
				log.info("getIllustration: " + objReqFee.getUsername() + " checkInsMongo: " + checkInsMongo);
				objBillRes.setStatus(statusSuccess);
				objBillRes.setMessage("Yeu cau thanh cong");
				objBillRes.setBilling_tmp_code(billID);
				objBillRes.setCollection("tbl_minhhoa");
			} else {
				log.info("getIllustration: " + objReqFee.getUsername() + " check login false:");
				objBillRes.setStatus(statusFale);
				objBillRes.setMessage("Yeu cau that bai - Invalid message request");
				objBillRes.setBilling_tmp_code("");
				objBillRes.setCollection("");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("getIllustration: " + objReqFee.getUsername() + " response to client:" + objBillRes.toJSON());
			log.info("----------------Ket thuc getIllustration: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Minh hoa khoan vay");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataIllustration);
			tblSystemActions.setResponseData(objBillRes.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(objBillRes.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc getIllustration Exception ", e);
			objBillRes.setStatus(statusFale);
			objBillRes.setMessage("Yeu cau that bai - Invalid message request");
			objBillRes.setBilling_tmp_code("");
			objBillRes.setCollection("");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(objBillRes.toJSON()).build();
		}
	}

	// Th???m ?????nh l???n 2
	public Response updateAppraisal(String dataAppraisal) {
		log.info("----------------Bat dau updateAppraisal--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResAppraisal resAppraisal = new ResAppraisal();
		try {
			log.info("updateAppraisal dataAppraisal: " + dataAppraisal);
			ReqAppraisal reqAppraisal = gson.fromJson(dataAppraisal, ReqAppraisal.class);
			ResAppraisal resAppraisal2 = validData.validUpdateAppraisal(reqAppraisal);
			if (resAppraisal2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAppraisal2.toJSON()).build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqAppraisal.getUsername());
			String fullName = reqAppraisal.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					branchID.add(new Integer(key.toString()));
					for (int i = 0; i < msg.length(); i++) {
						if (ValidData.checkNull(msg.get(i).toString()) == true) {
							roomID.add(Integer.parseInt(msg.get(i).toString()));
						}
					}
				}
			}
			TblLoanRequest tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqAppraisal.getLoan_code());
			if (tblLoanRequest != null) {
				TblLoanReqDetail tblLoanReqDetail = dbFintechHome.getLoanDetail(tblLoanRequest.getLoanId());
				// try {
				// boolean checkDelete =
				// dbFintechHome.deleteTblImages(tblLoanReqDetail.getLoanId());
				// log.info("updateAppraisal checkDelete IMG: " + checkDelete);
				// // boolean checkDeleteAskAns =
				// // dbFintechHome.deleteAskAns(tblLoanReqDetail.getLoanId());
				// // log.info("updateAppraisal deleteAskAns
				// // checkDeleteAskAns: " + checkDeleteAskAns,
				// // LogType.BUSSINESS);
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				List<TblImages> imagesListSet = new ArrayList<>();
				if (reqAppraisal.getImages() != null) {
					List<ObjImage> imagesList = reqAppraisal.getImages();
					for (ObjImage objImage : imagesList) {
						TblImages tblImages = new TblImages();
						tblImages.setLoanRequestDetailId(tblLoanReqDetail.getLoanId());
						tblImages.setImageName(objImage.getImage_name());
						tblImages.setImageInputName(objImage.getImage_input_name());
						tblImages.setPartnerImageId(objImage.getPartner_image_id());
						tblImages.setImageType((int) objImage.getImage_type());
						tblImages.setImageByte(objImage.getImage_byte());
						tblImages.setImageUrl(objImage.getImage_url());
						tblImages.setImageIsFront((int) objImage.getImage_is_front());
						tblImages.setUploadBy(reqAppraisal.getUsername());
						tblImages.setCreatedDate(new Date());
						tblImages.setEditedDate(new Date());
						imagesListSet.add(tblImages);
					}
				}
				TblLoanRequestAskAns tblLoanRequestAskAns = new TblLoanRequestAskAns();
				if ((reqAppraisal.getQuestion_and_answears()) != null) {
					List<ObjQuestions> questionsList = reqAppraisal.getQuestion_and_answears();
					tblLoanRequestAskAns.setLoanId(tblLoanReqDetail.getLoanId());
					// tblLoanRequestAskAns.setQAThamDinh1(gson.toJson(questionsList));
					tblLoanRequestAskAns.setQAThamDinh2(gson.toJson(questionsList));
				}
				// int insOrUpd = 0; // 0 insert, 1 update
				tblLoanRequest.setPreviousStatus(tblLoanRequest.getFinalStatus());
				tblLoanRequest.setFinalStatus(110);
				boolean checkINS = tbLoanRequestHome.createLoanTransTD(1, tblLoanRequest, tblLoanReqDetail,
						imagesListSet, tblLoanRequestAskAns);
				if (checkINS) {
					resAppraisal.setStatus(statusSuccess);
					resAppraisal.setMessage("Yeu cau thanh cong");
					resAppraisal.setLoan_code(tblLoanRequest.getLoanCode());

					TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
					tblLoanExpertiseSteps.setLoanId(tblLoanRequest.getLoanId());
					tblLoanExpertiseSteps.setExpertiseUser(fullName);
					tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
					tblLoanExpertiseSteps.setExpertiseStatus(tblLoanRequest.getFinalStatus());
					tblLoanExpertiseSteps.setExpertiseStep(1);
					tblLoanExpertiseSteps.setExpertiseComment(reqAppraisal.getMemo());
					tblLoanExpertiseSteps.setLoanCode(tblLoanRequest.getLoanCode());
					tblLoanExpertiseSteps.setAction(reqAppraisal.getAction());

					// log.info("Bat dau ThreadInsertLogStep",
					// LogType.BUSSINESS);
					// boolean checkINSExpertiseSteps =
					// dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
					// log.info("ThreadInsertLogStep checkINS: " +
					// checkINSExpertiseSteps);
					Thread t = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
					t.start();
				} else {
					resAppraisal.setStatus(statusFale);
					resAppraisal.setMessage("Yeu cau that bai");
				}
			} else {
				resAppraisal.setStatus(statusFale);
				resAppraisal.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("updateAppraisal: " + reqAppraisal.getUsername() + " response to client:" + resAppraisal.toJSON());
			log.info("----------------Ket thuc updateAppraisal: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Tham dinh khoan vay");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataAppraisal);
			tblSystemActions.setResponseData(resAppraisal.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAppraisal.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc updateAppraisal Exception ", e);
			resAppraisal.setStatus(statusFale);
			resAppraisal.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAppraisal.toJSON()).build();
		}
	}

	// L???y danh sach khoan vay phan bo cho nha dau tu
	public Response getContractListSponsor(String dataGetContractListSpon) {
		log.info("----------------Bat dau getContractListSponsor--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResAllContractList resAllContractList = new ResAllContractList();
		List<ResContractListSponsor> resContractListSponsors = new ArrayList<>();
		try {
			log.info("getContractListSponsor dataGetContractListSpon: " + dataGetContractListSpon);
			ReqContractListSponsor reqContractListSponsor = gson.fromJson(dataGetContractListSpon,
					ReqContractListSponsor.class);
			ResAllContractList resContractListSponsor2 = validData.validListSponsor(reqContractListSponsor);
			if (resContractListSponsor2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow())
						.entity(resContractListSponsor2.toJSON()).build();
			}
			Account acc = accountHome.getAccountUsename(reqContractListSponsor.getUsername());
			String loan_code = "";
			try {
				loan_code = reqContractListSponsor.getLoan_code();
			} catch (Exception e) {
			}
			List<Integer> final_statusAR = new ArrayList<>();
			try {
				List<String> final_status = reqContractListSponsor.getFinal_status();
				for (String string : final_status) {
					final_statusAR.add(Integer.parseInt(string));
				}
			} catch (Exception e) {
			}
			String borrower_name = reqContractListSponsor.getBorrower_name();
			String from_date = reqContractListSponsor.getFrom_date();
			String to_date = reqContractListSponsor.getTo_date();
			String calculate_profit_type = reqContractListSponsor.getCalculate_profit_type();
			List<ResContractListSponsor> listListSponsor = dbFintechHome.listListSponsor(acc.getRowId(), loan_code,
					final_statusAR, borrower_name, from_date, to_date, calculate_profit_type,
					reqContractListSponsor.getLimit(), reqContractListSponsor.getOffSet());
			long total = dbFintechHome.listCounListSponsor(acc.getRowId(), loan_code, final_statusAR, borrower_name,
					from_date, to_date, calculate_profit_type, reqContractListSponsor.getLimit(),
					reqContractListSponsor.getOffSet());
			if (listListSponsor != null) {
				resAllContractList.setStatus(statusSuccess);
				resAllContractList.setMessage("Yeu cau thanh cong");
				resAllContractList.setContract_list_sponsor(listListSponsor);
				resAllContractList.setTotalRecord(total);
			} else {
				resAllContractList.setStatus(statusFale);
				resAllContractList.setMessage("Yeu cau that bai - Da co loi xay ra");
				resAllContractList.setContract_list_sponsor(resContractListSponsors);
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("getContractListSponsor: " + reqContractListSponsor.getUsername() + " response to client:"
					+ resAllContractList.toJSON());
			log.info("----------------Ket thuc getContractListSponsor: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Lay danh sach khoan vay cho nha dau tu");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataGetContractListSpon);
			tblSystemActions.setResponseData(resAllContractList.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllContractList.toJSON())
					.build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc getContractListSponsor Exception ", e);
			resAllContractList.setStatus(statusFale);
			resAllContractList.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllContractList.toJSON())
					.build();
		}
	}

	// Giai ngan
	public Response disbursement(String dataDisbursement) {
		log.info("----------------Bat dau disbursement--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		// List<ResContractListSponsor> resContractListSponsors = new
		// ArrayList<>();
		ResDisbursement resDisbursement = new ResDisbursement();
		try {
			log.info("disbursement dataDisbursement: " + dataDisbursement);
			ReqDisbursement reqDisbursement = gson.fromJson(dataDisbursement, ReqDisbursement.class);
			ResDisbursement resDisbursement2 = validData.validDisbursement(reqDisbursement);
			if (resDisbursement2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resDisbursement2.toJSON())
						.build();
			}
			Account acc = accountHome.getAccountUsename(reqDisbursement.getUsername());
			String fullName = reqDisbursement.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
			int accountID = acc.getRowId();
			String loan_code = "";
			try {
				loan_code = reqDisbursement.getLoan_code();
			} catch (Exception e) {
			}
			TblLoanRequest tblLoanRequest = dbFintechHome.getTblLoanRequest(loan_code);
			if (tblLoanRequest != null) {
				TblLoanSponsorMapp tblLoanSponsorMapp = dbFintechHome.getLoanRequet(tblLoanRequest.getLoanId(),
						accountID);
				if (tblLoanSponsorMapp != null) {
					// int checkDisbursementDate =
					// tblLoanSponsorMapp.getDisbursementDate().compareTo(new
					// Date());
					int checkDisbursementDate = tblLoanSponsorMapp.getEntryExpireTime()
							.compareTo(sm.parse(sm.format(new Date())));
					log.info("disbursement checkDisbursementDate: " + checkDisbursementDate);
					if (checkDisbursementDate > 0) {
						// Khoan vay chua den han thanh toan
						log.info("disbursement checkDisbursementDate: " + checkDisbursementDate);
						if (reqDisbursement.getExpertise_status() == 1) {
							// C???p nh???t tr???ng th??i final_status b???ng
							// tbl_loan_request v???: ???? gi???i ng??n(116).
							// disbursement_date = current
							tblLoanRequest.setPreviousStatus(tblLoanRequest.getFinalStatus());
							tblLoanRequest.setFinalStatus(statusDisbursement);
							tblLoanSponsorMapp.setDisbursementDate(new Date());
							tblLoanSponsorMapp.setDisbursementStatus(1);
						} else {
							// disbursement_status = 0, disbursement_date =
							// current
							tblLoanSponsorMapp.setDisbursementDate(new Date());
							tblLoanSponsorMapp.setDisbursementStatus(2);
						}
						TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
						tblLoanExpertiseSteps.setLoanId(tblLoanRequest.getLoanId());
						tblLoanExpertiseSteps.setExpertiseUser(fullName);
						tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
						tblLoanExpertiseSteps.setExpertiseStatus(tblLoanRequest.getFinalStatus());
						tblLoanExpertiseSteps.setExpertiseStep(1);
						// tblLoanExpertiseSteps.setExpertiseComment(reqAppraisal.getMemo());
						tblLoanExpertiseSteps.setLoanCode(tblLoanRequest.getLoanCode());
						tblLoanExpertiseSteps.setAction(reqDisbursement.getAction());

						resDisbursement.setStatus(statusSuccess);
						resDisbursement.setMessage("Yeu cau thanh cong");
						resDisbursement.setLoan_code(loan_code);
						TblLoanReqDetail tblLoanReqDetail = dbFintechHome.getLoanDetail(tblLoanRequest.getLoanId());
						if (reqDisbursement.getImages() != null) {
							List<ObjImage> imagesList = reqDisbursement.getImages();
							for (ObjImage objImage : imagesList) {
								TblImages tblImages = new TblImages();
								tblImages.setLoanRequestDetailId(tblLoanReqDetail.getLoanId());
								tblImages.setImageName(objImage.getImage_name());
								tblImages.setImageInputName(objImage.getImage_input_name());
								tblImages.setPartnerImageId(objImage.getPartner_image_id());
								tblImages.setImageType((int) objImage.getImage_type());
								tblImages.setImageByte(objImage.getImage_byte());
								tblImages.setImageUrl(objImage.getImage_url());
								tblImages.setImageIsFront((int) objImage.getImage_is_front());
								tblImages.setUploadBy(reqDisbursement.getUsername());
								tblImages.setCreatedDate(new Date());
								tblImages.setEditedDate(new Date());
								boolean checkINSEImage = dbFintechHome.createTblImages(tblImages);
								log.info("disbursement checkINSEImage: " + checkINSEImage);
							}
						}

						// boolean checkINSExpertiseSteps =
						// dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
						// log.info("disbursement checkINSExpertiseSteps:
						// " + checkINSExpertiseSteps);

						boolean checkUPDSpon = dbFintechHome.updateTblLoanSponsorMapp(tblLoanSponsorMapp);
						log.info("disbursement checkUPDSpon: " + checkUPDSpon);

						boolean checkUPDLoan = dbFintechHome.updateTblLoanRequest(tblLoanRequest);
						log.info("disbursement checkINS: " + checkUPDLoan);

						Thread t = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
						t.start();

					} else {
						// Khoan vay qua han
						tblLoanRequest.setPreviousStatus(tblLoanRequest.getFinalStatus());
						tblLoanRequest.setFinalStatus(123);
						tblLoanRequest.setLatestUpdate(new Date());
						boolean checkUPDLoan = dbFintechHome.updateTblLoanRequest(tblLoanRequest);
						log.info("disbursement checkUPDLoan: " + checkUPDLoan);
						resDisbursement.setStatus(statusFale);
						resDisbursement.setMessage("Yeu cau that bai - Khoan vay da qua thoi gian cho phep giai ngan");
					}
				} else {
					resDisbursement.setStatus(statusFale);
					resDisbursement.setMessage("Yeu cau that bai - Khoan vay nay khong duoc phep dau tu");
				}
			} else {
				resDisbursement.setStatus(statusFale);
				resDisbursement.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("disbursement: " + reqDisbursement.getUsername() + " response to client:"
					+ resDisbursement.toJSON());
			log.info("----------------Ket thuc disbursement: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Giai ngan");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataDisbursement);
			tblSystemActions.setResponseData(resDisbursement.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resDisbursement.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc disbursement Exception ", e);
			resDisbursement.setStatus(statusFale);
			resDisbursement.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resDisbursement.toJSON()).build();
		}
	}

	// Danh sach nhac no
	public Response listDebtReminder(String dataGetdebtReminder) {
		log.info("----------------Bat dau getdebtReminder--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResDebtReminder resDebtReminder = new ResDebtReminder();
		List<ObjDebtReminderDetail> getDebtReminder = new ArrayList<>();
		try {
			log.info("getdebtReminder dataGetdebtReminder: " + dataGetdebtReminder);
			ReqDebtReminder reqDebtReminder = gson.fromJson(dataGetdebtReminder, ReqDebtReminder.class);
			ResDebtReminder resDebtReminder2 = validData.validGetdebtReminder(reqDebtReminder);
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			if (resDebtReminder2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resDebtReminder2.toJSON())
						.build();
			}
			String loan_code = "";
			try {
				loan_code = reqDebtReminder.getLoan_code();
			} catch (Exception e) {
			}
			List<Integer> final_statusAR = new ArrayList<>();
			try {
				List<String> final_status = reqDebtReminder.getFinal_status();
				if (final_status.size() > 0) {
					for (String string : final_status) {
						final_statusAR.add(Integer.parseInt(string));
					}
				} else {
					for (int i = 116; i < 122; i++) {
						final_statusAR.add(i);
					}
				}
			} catch (Exception e) {
			}
			System.out.println("final_statusARaaaaa" + final_statusAR);
			String borrower_name = reqDebtReminder.getBorrower_name();
			String from_date = reqDebtReminder.getFrom_date();
			String to_date = reqDebtReminder.getTo_date();
			String bill_from_date = reqDebtReminder.getBill_from_date();
			String bill_to_date = reqDebtReminder.getBill_to_date();
			String id_number = reqDebtReminder.getId_number();
			String bill_payment_status = "";
			if (ValidData.checkNull(reqDebtReminder.getBill_payment_status()) == true) {
				bill_payment_status = reqDebtReminder.getBill_payment_status();
			}
			Account acc = accountHome.getAccountUsename(reqDebtReminder.getUsername());
			String creater_by = "";
			long total = 0;
			List<ObjDebtReminderDetail> listDebtReminderDetail = null;
			if (acc.getRolesId().equals("4")) {
				// acc la giao dich vien chi lay HD cua giao dich vien do
				creater_by = acc.getEmail();
				total = dbFintechHome.countDebtReminderDetail(creater_by, branchID, roomID, loan_code, final_statusAR,
						bill_payment_status, borrower_name, from_date, to_date, bill_from_date, bill_to_date,
						id_number);
				listDebtReminderDetail = dbFintechHome.listDebtReminderDetail(creater_by, branchID, roomID, loan_code,
						final_statusAR, bill_payment_status, borrower_name, from_date, to_date, bill_from_date,
						bill_to_date, id_number, reqDebtReminder.getLimit(), reqDebtReminder.getOffSet());
			} else {
				// acc khong phai giao dich vien lay HD cung chi nhanh
				// if (ValidData.checkNull(acc.getBranchId()) == true) {
				// JSONObject isJsonObject = (JSONObject) new
				// JSONObject(acc.getBranchId());
				// Iterator<String> keys = isJsonObject.keys();
				// while (keys.hasNext()) {
				// String key = keys.next();
				// System.out.println(key);
				// JSONArray msg = (JSONArray) isJsonObject.get(key);
				// branchID.add(new Integer(key.toString()));
				// }
				// }

				if (reqDebtReminder.getBranch_id().size() > 0) {

					try {
						List<String> branch = reqDebtReminder.getBranch_id();
						for (String string : branch) {
							branchID.add(Integer.parseInt(string));
						}
					} catch (Exception e) {
					}
					if (reqDebtReminder.getRoom_id().size() > 0) {
						try {
							List<String> room = reqDebtReminder.getRoom_id();
							for (String string : room) {
								roomID.add(Integer.parseInt(string));
							}
						} catch (Exception e) {
						}
					}
					// branchID.add(Integer.parseInt(reqDebtReminder.getBranch_id()));
					// if (ValidData.checkNull(reqDebtReminder.getRoom_id()) ==
					// true) {
					// roomID.add(Integer.parseInt(reqDebtReminder.getRoom_id()));
					// }
				} else {
					if (ValidData.checkNull(acc.getBranchId()) == true) {
						JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
						Iterator<String> keys = isJsonObject.keys();
						while (keys.hasNext()) {
							String key = keys.next();
							System.out.println(key);
							JSONArray msg = (JSONArray) isJsonObject.get(key);
							branchID.add(new Integer(key.toString()));
							for (int i = 0; i < msg.length(); i++) {
								if (ValidData.checkNull(msg.get(i).toString()) == true) {
									roomID.add(Integer.parseInt(msg.get(i).toString()));
								}
							}
						}
					}
				}
				total = dbFintechHome.countDebtReminderDetail(creater_by, branchID, roomID, loan_code, final_statusAR,
						bill_payment_status, borrower_name, from_date, to_date, bill_from_date, bill_to_date,
						id_number);
				listDebtReminderDetail = dbFintechHome.listDebtReminderDetail(creater_by, branchID, roomID, loan_code,
						final_statusAR, bill_payment_status, borrower_name, from_date, to_date, bill_from_date,
						bill_to_date, id_number, reqDebtReminder.getLimit(), reqDebtReminder.getOffSet());
			}

			if (listDebtReminderDetail != null) {
				resDebtReminder.setStatus(statusSuccess);
				resDebtReminder.setMessage("Yeu cau thanh cong");
				resDebtReminder.setLoan_request_details(listDebtReminderDetail);
				resDebtReminder.setTotalRecord(total);
			} else {
				resDebtReminder.setStatus(statusFale);
				resDebtReminder.setMessage("Yeu cau that bai - Da co loi xay ra");
				resDebtReminder.setLoan_request_details(getDebtReminder);
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("getdebtReminder: " + reqDebtReminder.getUsername() + " response to client:"
					+ resDebtReminder.toJSON());
			log.info("----------------Ket thuc getdebtReminder: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Giai ngan");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataGetdebtReminder);
			tblSystemActions.setResponseData(resDebtReminder.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resDebtReminder.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("----------------Ket thuc getdebtReminder Exception ", e);
			resDebtReminder.setStatus(statusFale);
			resDebtReminder.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resDebtReminder.toJSON()).build();
		}
	}

	// Update tr???ng th??i giao d???ch
	public Response updateExtendStatus(String dataExtendStatus) {
		log.info("----------------Bat dau updateExtendStatus--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResUDExtendStatus resUDExtendStatus = new ResUDExtendStatus();
		try {
			log.info("updateExtendStatus dataUpdateStatus: " + dataExtendStatus);
			ReqUDExtendStatus reqUDExtendStatus = gson.fromJson(dataExtendStatus, ReqUDExtendStatus.class);
			ResUDExtendStatus resUDExtendStatus2 = validData.updateExtendStatus(reqUDExtendStatus);
			if (resUDExtendStatus2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUDExtendStatus2.toJSON())
						.build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqUDExtendStatus.getUsername());
			String fullName = reqUDExtendStatus.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					branchID.add(new Integer(key.toString()));
					for (int i = 0; i < msg.length(); i++) {
						if (ValidData.checkNull(msg.get(i).toString()) == true) {
							roomID.add(Integer.parseInt(msg.get(i).toString()));
						}
					}
				}
			}
			System.out.println(gson.toJson(reqUDExtendStatus));

			TblLoanRequest tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqUDExtendStatus.getLoan_code());
			boolean checkUPDAll = false;
			if (tblLoanRequest != null) {
				log.info("updateExtendStatus tblLoanRequest: " + gson.toJson(tblLoanRequest));
				log.info("updateExtendStatus tblLoanRequest.getLoanId: " + tblLoanRequest.getLoanId());

				Integer last_day_accept_pay = null;
				String memo = "";
				if (reqUDExtendStatus.getExtend_status().equals("1163")) {
					// Nhac no thanh cong
					try {
						last_day_accept_pay = Integer.parseInt(reqUDExtendStatus.getLast_day_accept_pay());
					} catch (Exception e) {
					}
					try {
						if (reqUDExtendStatus.getLast_day_accept_pay() != null) {
							memo = reqUDExtendStatus.getLast_day_accept_pay() + " - " + reqUDExtendStatus.getMemo();
						} else {
							memo = reqUDExtendStatus.getMemo();
						}
					} catch (Exception e) {
					}

					boolean checkUPDBill = dbFintechHome.updateLoanBill(tblLoanRequest.getLoanId(),
							Integer.parseInt(reqUDExtendStatus.getBill_index()), last_day_accept_pay);
					log.info("updateExtendStatus checkUPDBill: " + checkUPDBill);
					Integer extenStt = null;
					boolean checkUPDLoan = tbLoanRequestHome.updateTblLoanRequest(tblLoanRequest.getLoanId(), extenStt);
					log.info("updateExtendStatus checkUPDLoan: " + checkUPDLoan);

					if (checkUPDBill && checkUPDLoan) {
						checkUPDAll = true;
					}

				} else {
					// Nhac no that bai
					try {
						memo = reqUDExtendStatus.getMemo();
					} catch (Exception e) {
					}
					checkUPDAll = true;
				}

				if (checkUPDAll) {
					resUDExtendStatus.setStatus(statusSuccess);
					resUDExtendStatus.setMessage("Yeu cau thanh cong");
					resUDExtendStatus.setLoan_code(reqUDExtendStatus.getLoan_code());
					TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
					tblLoanExpertiseSteps.setLoanId(tblLoanRequest.getLoanId());
					tblLoanExpertiseSteps.setExpertiseUser(fullName);
					tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
					tblLoanExpertiseSteps.setExpertiseStatus(tblLoanRequest.getFinalStatus());
					tblLoanExpertiseSteps.setExpertiseStep(5);
					tblLoanExpertiseSteps.setExpertiseComment(memo);
					tblLoanExpertiseSteps.setLoanCode(tblLoanRequest.getLoanCode());
					try {
						tblLoanExpertiseSteps.setAction(reqUDExtendStatus.getAction());
					} catch (Exception e) {
					}
					// boolean checkINSExpertiseSteps =
					// dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
					// log.info("updateExtendStatus checkINS: " +
					// checkINSExpertiseSteps);

					Thread t = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
					t.start();

					TblDebtRemindHistory tblDebtRemindHistory = new TblDebtRemindHistory();
					tblDebtRemindHistory.setLoanId(tblLoanRequest.getLoanId());
					tblDebtRemindHistory.setLoanCode(tblLoanRequest.getLoanCode());
					tblDebtRemindHistory.setCreatedDate(new Date());
					tblDebtRemindHistory.setCreatedBy(fullName);
					try {
						tblDebtRemindHistory.setAdminRemark(reqUDExtendStatus.getAdmin_remark());
						tblDebtRemindHistory.setBorrowerRemark(reqUDExtendStatus.getBorrower_reply_remark());
						tblDebtRemindHistory.setRemindType(Integer.parseInt(reqUDExtendStatus.getRemind_type()));
						tblDebtRemindHistory.setBillIndex(Integer.parseInt(reqUDExtendStatus.getBill_index()));
					} catch (Exception e) {
					}
					boolean checkInsResm = dbFintechHome.createTblDebtRemindHistory(tblDebtRemindHistory);
					log.info("updateExtendStatus checkInsResm: " + checkInsResm);
					// TblLoanBill tblLoanBill =
					// dbFintechHome.getTblLoanBill(Integer.parseInt(reqUDExtendStatus.getBill_index()),
					// tblLoanRequest.getLoanId());
					// ObjDebtReminderRedis objDebtReminderRedis = new
					// ObjDebtReminderRedis();
					// objDebtReminderRedis.setLoan_id(tblLoanRequest.getLoanId().toString());
					// objDebtReminderRedis.setLoan_code(tblLoanRequest.getLoanCode());
					// objDebtReminderRedis.setBill_id(tblLoanBill.getBillId().toString());
					// String key = "QUEUE_INDEBT_REMIND";
					// log.info("updateExtendStatus key : " + key,
					// LogType.BUSSINESS);
					// log.info("updateExtendStatus ObjDebtReminderRedis :
					// " + objDebtReminderRedis.toJSON());
					// RedisBusiness redisBusiness = new RedisBusiness();
					// boolean checkPush = redisBusiness.enQueueToRedis(key,
					// objDebtReminderRedis.toJSON());
					// log.info("updateExtendStatus checkPush : " +
					// checkPush);

				} else {
					resUDExtendStatus.setStatus(statusFale);
					resUDExtendStatus.setMessage("Yeu cau that bai -  Da co loi xay ra");
					resUDExtendStatus.setLoan_code(reqUDExtendStatus.getLoan_code());
				}
			} else {
				log.info("updateExtendStatus tblLoanRequest null: ");
				resUDExtendStatus.setStatus(statusFale);
				resUDExtendStatus.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("updateExtendStatus: " + reqUDExtendStatus.getUsername() + " response to client:"
					+ resUDExtendStatus.toJSON().replace("'\'", ""));
			log.info("----------------Ket thuc updateExtendStatus: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Update trang thai giao dich");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataExtendStatus);
			tblSystemActions.setResponseData(resUDExtendStatus.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUDExtendStatus.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc updateExtendStatus Exception ", e);
			resUDExtendStatus.setStatus(statusFale);
			resUDExtendStatus.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUDExtendStatus.toJSON()).build();
		}
	}

	// Tat toan
	public Response settlement(String dataSettlement) {
		log.info("----------------Bat dau settlement--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResSettlement resSettlement = new ResSettlement();
		try {
			log.info("settlement dataSettlement: " + dataSettlement);
			ReqSettlement reqSettlement = gson.fromJson(dataSettlement, ReqSettlement.class);
			ResSettlement resSettlement2 = validData.validSettlement(reqSettlement);
			if (resSettlement2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resSettlement2.toJSON())
						.build();
			}
			Account acc = accountHome.getAccountUsename(reqSettlement.getUsername());
			String fullName = reqSettlement.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
			String loan_code = "";
			try {
				loan_code = reqSettlement.getLoan_code();
			} catch (Exception e) {
			}

			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					branchID.add(new Integer(key.toString()));
					for (int i = 0; i < msg.length(); i++) {
						if (ValidData.checkNull(msg.get(i).toString()) == true) {
							roomID.add(Integer.parseInt(msg.get(i).toString()));
						}
					}
				}
			}

			TblLoanRequest getLoanBranchID = dbFintechHome.getLoanBranchID(branchID, loan_code);
			if (getLoanBranchID != null) {
				if (getLoanBranchID.getFinalStatus().toString().equals("122") == false) {
					System.out.println(getLoanBranchID.getFinalStatus());
					List<TblLoanBill> getListTblLoanBill = tblLoanBillHome
							.getListTblLoanBill(getLoanBranchID.getLoanId(), 0);
					BigDecimal real_amt_to_decr_your_loan = new BigDecimal(
							reqSettlement.getLatest_amt_to_decr_your_loan());
					int maxBillIndex = dbFintechHome.maxBillIndex(getLoanBranchID.getLoanId());
					BigDecimal realAdvisoryFee = new BigDecimal(0);
					BigDecimal realManageFee = new BigDecimal(0);
					int bill_id_setter = 0;
					int bill_index_setter = 0;
					boolean checkINS = true;
					for (TblLoanBill tblLoanBill : getListTblLoanBill) {
						// if(tblLoanBill.getBillIndex() < maxBillIndex){
						// if(payAmount.compareTo(tblLoanBill.getTotalOnAMonth())
						// > 0){
						// tblLoanBill.setPaymentAmt(tblLoanBill.getTotalOnAMonth().longValue());
						// payAmount =
						// payAmount.subtract(tblLoanBill.getTotalOnAMonth());
						// }else{
						// tblLoanBill.setPaymentAmt(payAmount.longValue());
						// payAmount = new BigDecimal(0);
						// }
						// }else{
						// tblLoanBill.setPaymentAmt(payAmount.longValue());
						// }
						if (tblLoanBill.getBillIndex() < maxBillIndex) {
							if (real_amt_to_decr_your_loan.compareTo(tblLoanBill.getAmtToDecrYourLoan()) > 0) {
								tblLoanBill.setRealAmtToDecrYourLoan(tblLoanBill.getAmtToDecrYourLoan());
								real_amt_to_decr_your_loan = real_amt_to_decr_your_loan
										.subtract(tblLoanBill.getAmtToDecrYourLoan());
							} else {
								tblLoanBill.setRealAmtToDecrYourLoan(real_amt_to_decr_your_loan);
								real_amt_to_decr_your_loan = new BigDecimal(0);
							}
						} else {
							tblLoanBill.setRealAmtToDecrYourLoan(real_amt_to_decr_your_loan);
						}
						tblLoanBill.setBillPaymentStatus(1);
						tblLoanBill.setPaymentDate(new Date());

						try {
							tblLoanBill.setRealPaymentDate(
									new SimpleDateFormat("yyyyMMdd").parse(reqSettlement.getReal_payment_date()));
						} catch (Exception e) {
						}

						tblLoanBill.setBillStatus(122);
						tblLoanBill.setBillCollectBy(fullName);

						if (checkINS) {

							bill_id_setter = tblLoanBill.getBillId();
							bill_index_setter = tblLoanBill.getBillIndex();
							realAdvisoryFee = new BigDecimal(reqSettlement.getReal_advisory_fee());
							realManageFee = new BigDecimal(reqSettlement.getReal_manage_fee());

							tblLoanBill.setPaymentAmt(reqSettlement.getPay_amount());
							tblLoanBill.setRealAdvisoryFee(realAdvisoryFee); // Phi
																				// tu
																				// van
																				// thuc
																				// thu
							tblLoanBill.setRealManageFee(realManageFee); // Phi
																			// quan
																			// ly
																			// thuc
																			// thu
						} else {
							tblLoanBill.setRealAdvisoryFee(new BigDecimal(0)); // Phi
																				// tu
																				// van
																				// thuc
																				// thu
							tblLoanBill.setRealManageFee(new BigDecimal(0)); // Phi
																				// quan
																				// ly
																				// thuc
																				// thu
						}
						boolean updLoanBill = dbFintechHome.updateTblLoanBill(tblLoanBill);
						checkINS = false;
						log.info("settlement updLoanBill BillIndex: " + tblLoanBill.getBillIndex() + " " + updLoanBill);
					}

					// fsdfsd

					if (reqSettlement.getImages() != null) {
						List<ObjImage> imagesList = reqSettlement.getImages();
						for (ObjImage objImage : imagesList) {
							TblImages tblImages = new TblImages();
							tblImages.setLoanRequestDetailId(getLoanBranchID.getLoanId());
							tblImages.setImageName(objImage.getImage_name());
							tblImages.setImageInputName(objImage.getImage_input_name());
							tblImages.setPartnerImageId(objImage.getPartner_image_id());
							tblImages.setImageType((int) objImage.getImage_type());
							tblImages.setImageByte(objImage.getImage_byte());
							tblImages.setImageUrl(objImage.getImage_url());
							tblImages.setImageIsFront((int) objImage.getImage_is_front());
							tblImages.setUploadBy(reqSettlement.getUsername());
							tblImages.setCreatedDate(new Date());
							tblImages.setEditedDate(new Date());
							tblImages.setBillId(bill_id_setter);
							boolean checkINSEImage = dbFintechHome.createTblImages(tblImages);
							log.info("settlement checkINSEImage: " + checkINSEImage);
						}
					}

					log.info("settlement tblLoanBill tat toan: ");
					getLoanBranchID.setPreviousStatus(getLoanBranchID.getFinalStatus());
					getLoanBranchID.setFinalStatus(122); // 122 = tat toan
					getLoanBranchID.setSettleDate(Utils.getDateNow());

					getLoanBranchID.setLatestUpdate(new Date());
					getLoanBranchID.setBillIndexSettlement(bill_index_setter);
					try {
						getLoanBranchID.setSettleAmount(new BigDecimal(reqSettlement.getPay_amount()));
					} catch (Exception e) {
					}
					try {
						getLoanBranchID.setLatestAmtToDecrYourLoan(
								new BigDecimal(reqSettlement.getLatest_amt_to_decr_your_loan()));
					} catch (Exception e) {
					}
					try {
						getLoanBranchID.setSettleFee(new BigDecimal(reqSettlement.getSettle_fee()));
					} catch (Exception e) {
					}
					try {
						getLoanBranchID.setInterestTillNow(new BigDecimal(reqSettlement.getInterest_till_now()));
					} catch (Exception e) {
					}
					boolean checkUPDLoan = dbFintechHome.updateTblLoanRequest(getLoanBranchID);
					log.info("settlement tat toan checkUPDLoan: " + checkUPDLoan);

					TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
					tblLoanExpertiseSteps.setLoanId(getLoanBranchID.getLoanId());
					tblLoanExpertiseSteps.setExpertiseUser(fullName);
					tblLoanExpertiseSteps.setExpertiseDate(new Date());
					tblLoanExpertiseSteps.setExpertiseStatus(getLoanBranchID.getFinalStatus());
					tblLoanExpertiseSteps.setExpertiseStep(6);
					tblLoanExpertiseSteps.setExpertiseComment(reqSettlement.getMemo());
					tblLoanExpertiseSteps.setLoanCode(getLoanBranchID.getLoanCode());
					tblLoanExpertiseSteps.setAction(reqSettlement.getAction());

					boolean checkINSExpertiseSteps = dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
					log.info("settlement checkINSExpertiseSteps: " + checkINSExpertiseSteps);
					TblLoanBill getTblLoanBillIndex = tblLoanBillHome.getTblLoanBillIndex(getLoanBranchID.getLoanId(),
							maxBillIndex);
					getTblLoanBillIndex.setBillPaymentStatus(1);
					boolean updLoanBillMax = dbFintechHome.updateTblLoanBill(getTblLoanBillIndex);
					log.info("settlement updLoanBill update PaymentStatus = 1 : " + getTblLoanBillIndex.getBillIndex()
							+ " " + updLoanBillMax);
					if (checkUPDLoan && checkINSExpertiseSteps) {
						resSettlement.setStatus(statusSuccess);
						resSettlement.setMessage("Yeu cau thanh cong");
						resSettlement.setLoan_code(loan_code);
					} else {
						resSettlement.setStatus(statusFale);
						resSettlement.setMessage("Yeu cau that bai");
					}
				} else {
					log.info("settlement tblLoanRequest status = tat toan: ");
					resSettlement.setStatus(statusFale);
					resSettlement.setMessage("Yeu cau that bai - Khoan vay da duoc tat toan truoc do");
				}
			} else {
				log.info("settlement tblLoanRequest null: ");
				resSettlement.setStatus(statusFale);
				resSettlement.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("settlement: " + reqSettlement.getUsername() + " response to client:" + resSettlement.toJSON());
			log.info("----------------Ket thuc settlement: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Tat toan");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataSettlement);
			tblSystemActions.setResponseData(resSettlement.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resSettlement.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc settlement Exception ", e);
			resSettlement.setStatus(statusFale);
			resSettlement.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resSettlement.toJSON()).build();
		}
	}

	// Dong no
	public Response paymentLoan(String dataPaymentLoan) {
		log.info("----------------Bat dau paymentLoan--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResPayment resPayment = new ResPayment();
		try {
			log.info("paymentLoan dataPaymentLoan: " + dataPaymentLoan);
			ReqPayment reqPayment = gson.fromJson(dataPaymentLoan, ReqPayment.class);
			ResPayment resPayment2 = validData.validDataPaymentLoan(reqPayment);
			if (resPayment2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resPayment2.toJSON()).build();
			}
			Account acc = accountHome.getAccountUsename(reqPayment.getUsername());
			String fullName = reqPayment.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
			String loan_code = "";
			try {
				loan_code = reqPayment.getLoan_code();
			} catch (Exception e) {
			}

			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					branchID.add(new Integer(key.toString()));
					for (int i = 0; i < msg.length(); i++) {
						if (ValidData.checkNull(msg.get(i).toString()) == true) {
							roomID.add(Integer.parseInt(msg.get(i).toString()));
						}
					}
				}
			}

			TblLoanRequest getLoanBranchID = dbFintechHome.getLoanBranchID(branchID, loan_code);
			if (getLoanBranchID != null) {
				if (getLoanBranchID.getFinalStatus().toString().equals("122") == false) {
					TblLoanBill tblLoanBill = tblLoanBillHome.getTblLoanBillIndex(getLoanBranchID.getLoanId(),
							Integer.parseInt(reqPayment.getBill_index()));
					if (tblLoanBill != null) {
						System.out.println(tblLoanBill.getBillId());
						int maxBillIndex = dbFintechHome.maxBillIndex(getLoanBranchID.getLoanId());

						TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
						tblLoanExpertiseSteps.setLoanId(getLoanBranchID.getLoanId());
						tblLoanExpertiseSteps.setExpertiseUser(fullName);
						tblLoanExpertiseSteps.setExpertiseDate(new Date());
						tblLoanExpertiseSteps.setExpertiseStatus(getLoanBranchID.getFinalStatus());
						tblLoanExpertiseSteps.setExpertiseStep(6);
						tblLoanExpertiseSteps.setExpertiseComment(reqPayment.getMemo());
						tblLoanExpertiseSteps.setLoanCode(getLoanBranchID.getLoanCode());
						tblLoanExpertiseSteps.setAction(reqPayment.getAction());
						BigDecimal tieTT = new BigDecimal(reqPayment.getPay_amount());
						if (reqPayment.getIs_a_special_payment().equals("0")) {
							// ????ng l??i th?????ng
							BigDecimal tiencanTT = tblLoanBill.getTotalOnAMonth();
							// if (tieTT.compareTo(tiencanTT) >= 0) {
							// Chap nhan thanh toan
							tblLoanBill.setBillPaymentStatus(1);
							tblLoanBill.setPaymentDate(new Date());
							tblLoanBill.setPaymentAmt(tieTT.longValue());
							tblLoanBill.setBillCollectBy(fullName);
							tblLoanBill.setIsASpecialPayment(0);
							try {
								tblLoanBill.setRealPaymentDate(
										new SimpleDateFormat("yyyyMMdd").parse(reqPayment.getReal_payment_date()));
							} catch (Exception e) {
							}
							try {
								tblLoanBill.setRealAmtToDecrYourLoan(
										new BigDecimal(reqPayment.getReal_amt_to_decr_your_loan()));
							} catch (Exception e) {
							}
							try {
								tblLoanBill
										.setRealMonthlyInterest(new BigDecimal(reqPayment.getReal_monthly_interest()));
							} catch (Exception e) {
							}
							try {
								tblLoanBill.setRealAdvisoryFee(new BigDecimal(reqPayment.getReal_advisory_fee()));
							} catch (Exception e) {
							}
							try {
								tblLoanBill.setRealManageFee(new BigDecimal(reqPayment.getReal_manage_fee()));
							} catch (Exception e) {
							}
							try {
								tblLoanBill.setRealOverDueFee(new BigDecimal(reqPayment.getReal_over_due_fee()));
							} catch (Exception e) {
							}
							try {
								tblLoanBill.setRealLatePayFee(new BigDecimal(reqPayment.getReal_late_pay_fee()));
							} catch (Exception e) {
							}
							log.info("paymentLoan tblLoanBill success dong lai thuong: ");

							// Check qua han thanh toan cong tong tien vs phi
							DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
							String dateNow = new SimpleDateFormat("yyyyMMdd").format(new Date());
							LocalDate dayMustPay = formatter
									.parseLocalDate(String.valueOf(tblLoanBill.getDayMustPay()));
							LocalDate dayNow = formatter.parseLocalDate(dateNow);
							boolean isBefore = dayMustPay.isBefore(dayNow);
							if (isBefore) {
								BigDecimal totalAmt = tblLoanBill.getTotalOnAMonth()
										.add(new BigDecimal(tblLoanBill.getOverDueFee()));
								// tblLoanBill.setTotalOnAMonth(totalAmt);
								tblLoanBill.setPaymentAmt(totalAmt.longValue());
							}
							if (Integer.parseInt(reqPayment.getBill_index()) < maxBillIndex) {
								BigDecimal previousPeriodDebtFee = new BigDecimal(0);
								BigDecimal totalAmt = tblLoanBill.getTotalOnAMonth()
										.add(new BigDecimal(tblLoanBill.getOverDueFee()));
								if (tblLoanBill.getPreviousPeriodDebtFee() != null) {
									previousPeriodDebtFee = totalAmt.add(tblLoanBill.getPreviousPeriodDebtFee())
											.subtract(tieTT);
								} else {
									previousPeriodDebtFee = totalAmt.add(new BigDecimal(0)).subtract(tieTT);
								}
								int indexBill = tblLoanBill.getBillIndex() + 1;
								log.info("paymentLoan updLoanBill billindex: " + indexBill
										+ " _ previousPeriodDebtFee: " + previousPeriodDebtFee);
								boolean checkUPDBillPre = dbFintechHome.updateLoanBillPhitruocno(
										tblLoanBill.getLoanId(), indexBill, previousPeriodDebtFee);
								log.info("paymentLoan updLoanBill checkUPDBillPre: " + checkUPDBillPre);

							}
							boolean updLoanBill = dbFintechHome.updateTblLoanBill(tblLoanBill);
							log.info("paymentLoan updLoanBill: " + updLoanBill);
							// boolean updLoanBill = true;
							boolean checkINSExpertiseSteps = dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
							log.info("paymentLoan checkINSExpertiseSteps: " + checkINSExpertiseSteps);
							if (updLoanBill && checkINSExpertiseSteps) {
								resPayment.setStatus(statusSuccess);
								resPayment.setMessage("Yeu cau thanh cong");
								resPayment.setLoan_code(loan_code);
							} else {
								resPayment.setStatus(statusFale);
								resPayment.setMessage("Yeu cau that bai");
							}
							// } else {
							// // Khong chap nhan thanh toan
							// log.info("paymentLoan tblLoanBill false so tien
							// dong no < tien can thanh toan: ",
							// LogType.BUSSINESS);
							// resPayment.setStatus(statusFale);
							// resPayment.setMessage("Yeu cau that bai - so tien
							// dong no < tien can thanh toan");
							// }
						} else if (reqPayment.getIs_a_special_payment().equals("1")) {
							// ????ng l??i ?????c bi???t
							tblLoanBill.setBillPaymentStatus(1);
							tblLoanBill.setPaymentDate(new Date());
							tblLoanBill.setPaymentAmt(tieTT.longValue());
							tblLoanBill.setBillCollectBy(fullName);
							tblLoanBill.setIsASpecialPayment(1);
							try {
								tblLoanBill.setRealPaymentDate(
										new SimpleDateFormat("yyyyMMdd").parse(reqPayment.getReal_payment_date()));
							} catch (Exception e) {
							}
							try {
								tblLoanBill.setRealAmtToDecrYourLoan(
										new BigDecimal(reqPayment.getReal_amt_to_decr_your_loan()));
							} catch (Exception e) {
							}
							try {
								tblLoanBill
										.setRealMonthlyInterest(new BigDecimal(reqPayment.getReal_monthly_interest()));
							} catch (Exception e) {
							}
							try {
								tblLoanBill.setRealAdvisoryFee(new BigDecimal(reqPayment.getReal_advisory_fee()));
							} catch (Exception e) {
							}
							try {
								tblLoanBill.setRealManageFee(new BigDecimal(reqPayment.getReal_manage_fee()));
							} catch (Exception e) {
							}
							try {
								tblLoanBill.setRealOverDueFee(new BigDecimal(reqPayment.getReal_over_due_fee()));
							} catch (Exception e) {
							}
							try {
								tblLoanBill.setRealLatePayFee(new BigDecimal(reqPayment.getReal_late_pay_fee()));
							} catch (Exception e) {
							}
							if (Integer.parseInt(reqPayment.getBill_index()) < maxBillIndex) {
								BigDecimal previousPeriodDebtFee = new BigDecimal(0);
								BigDecimal totalAmt = tblLoanBill.getTotalOnAMonth()
										.add(new BigDecimal(tblLoanBill.getOverDueFee()));
								if (tblLoanBill.getPreviousPeriodDebtFee() != null) {
									previousPeriodDebtFee = totalAmt.add(tblLoanBill.getPreviousPeriodDebtFee())
											.subtract(tieTT);
								} else {
									previousPeriodDebtFee = totalAmt.add(new BigDecimal(0)).subtract(tieTT);
								}
								int indexBill = tblLoanBill.getBillIndex() + 1;
								log.info("paymentLoan updLoanBill billindex: " + indexBill
										+ " _ previousPeriodDebtFee: " + previousPeriodDebtFee);
								boolean checkUPDBillPre = dbFintechHome.updateLoanBillPhitruocno(
										tblLoanBill.getLoanId(), indexBill, previousPeriodDebtFee);
								log.info("paymentLoan updLoanBill checkUPDBillPre: " + checkUPDBillPre);
							}

							boolean updLoanBill = dbFintechHome.updateTblLoanBill(tblLoanBill);
							log.info("paymentLoan updLoanBill: " + updLoanBill);

							boolean checkINSExpertiseSteps = dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
							log.info("paymentLoan checkINSExpertiseSteps: " + checkINSExpertiseSteps);

							log.info("paymentLoan tblLoanBill success dong lai dac biet: ");
							if (updLoanBill && checkINSExpertiseSteps) {
								resPayment.setStatus(statusSuccess);
								resPayment.setMessage("Yeu cau thanh cong");
								resPayment.setLoan_code(loan_code);
							} else {
								resPayment.setStatus(statusFale);
								resPayment.setMessage("Yeu cau that bai");
							}
						} else {
							log.info("paymentLoan tblLoanBill null: ");
							resPayment.setStatus(statusFale);
							resPayment.setMessage("Yeu cau that bai - type ??ong lai Is_a_special_payment khong dung");
						}

						if (reqPayment.getImages() != null) {
							List<ObjImage> imagesList = reqPayment.getImages();
							for (ObjImage objImage : imagesList) {
								TblImages tblImages = new TblImages();
								tblImages.setLoanRequestDetailId(getLoanBranchID.getLoanId());
								tblImages.setImageName(objImage.getImage_name());
								tblImages.setImageInputName(objImage.getImage_input_name());
								tblImages.setPartnerImageId(objImage.getPartner_image_id());
								tblImages.setImageType((int) objImage.getImage_type());
								tblImages.setImageByte(objImage.getImage_byte());
								tblImages.setImageUrl(objImage.getImage_url());
								tblImages.setImageIsFront((int) objImage.getImage_is_front());
								tblImages.setUploadBy(reqPayment.getUsername());
								tblImages.setCreatedDate(new Date());
								tblImages.setEditedDate(new Date());
								tblImages.setBillId(tblLoanBill.getBillId());
								boolean checkINSEImage = dbFintechHome.createTblImages(tblImages);
								log.info("paymentLoan checkINSEImage: " + checkINSEImage);
							}
						}
						if (Integer.parseInt(reqPayment.getBill_index()) == maxBillIndex) {
							// Tat toan
							log.info("paymentLoan tblLoanBill tat toan: ");
							getLoanBranchID.setPreviousStatus(getLoanBranchID.getFinalStatus());
							getLoanBranchID.setFinalStatus(122); // 122 = tat
																	// toan
							getLoanBranchID.setSettleDate(Utils.getDateNow());
							getLoanBranchID.setSettleAmount(new BigDecimal(0));
							getLoanBranchID.setLatestUpdate(new Date());
							boolean checkUPDLoan = dbFintechHome.updateTblLoanRequest(getLoanBranchID);
							log.info("paymentLoan tat toan checkUPDLoan: " + checkUPDLoan);
						}
					} else {
						log.info("paymentLoan tblLoanBill null: ");
						resPayment.setStatus(statusFale);
						resPayment.setMessage(
								"Yeu cau that bai - Khong tim thay thong tin ky vay truyen len ung voi hop dong nay");
					}
				} else {
					log.info("paymentLoan tblLoanRequest status = tat toan: ");
					resPayment.setStatus(statusFale);
					resPayment.setMessage("Yeu cau that bai - Khoan vay da duoc tat toan truoc do");
				}
			} else {
				log.info("paymentLoan tblLoanRequest null: ");
				resPayment.setStatus(statusFale);
				resPayment.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("paymentLoan: " + reqPayment.getUsername() + " response to client:" + resPayment.toJSON());
			log.info("----------------Ket thuc paymentLoan: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Dong no");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataPaymentLoan);
			tblSystemActions.setResponseData(resPayment.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resPayment.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc paymentLoan Exception ", e);
			resPayment.setStatus(statusFale);
			resPayment.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resPayment.toJSON()).build();
		}
	}

	public Response getListInbox(String dataGetListInbox) {
		log.info("----------------Bat dau getListInbox--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResInbox resInbox = new ResInbox();
		try {
			log.info("getListInbox dataGetListInbox: " + dataGetListInbox);
			ReqInbox reqInbox = gson.fromJson(dataGetListInbox, ReqInbox.class);
			boolean checkLG = userInfo.checkLogin(reqInbox.getUsername(), reqInbox.getToken());
			if (!checkLG) {
				log.info("getListInbox : " + reqInbox.getUsername() + " check login false:");
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				log.info(messageErr);
				resInbox.setStatus(statusFale);
				resInbox.setMessage(messageErr);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resInbox.toJSON()).build();
			}
			long allIB = dbFintechHome.countAllTblInbox(reqInbox.getUsername());
			long ibUnread = dbFintechHome.countTblInboxUnread(reqInbox.getUsername());
			List<TblInbox> getInboxs = dbFintechHome.getInboxs(reqInbox.getUsername(),
					Integer.parseInt(reqInbox.getLimit()), Integer.parseInt(reqInbox.getOffSet()));
			if (getInboxs != null) {
				log.info("getListInbox: " + reqInbox.getUsername() + " thanh cong:");
				resInbox.setStatus(statusSuccess);
				resInbox.setMessage("Yeu cau thanh cong");
				resInbox.setTotalRecord(allIB);
				resInbox.setTotalUInread(ibUnread);
				resInbox.setInbox(getInboxs);
			} else {
				log.info("getListInbox: " + reqInbox.getUsername() + " that bai getListInbox null");
				resInbox.setStatus(statusFale);
				resInbox.setMessage("Yeu cau that bai - Da co loi xay ra");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("getListInbox: " + reqInbox.getUsername() + " response to client:" + resInbox.toJSON());
			log.info("----------------Ket thuc getListInbox: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Lay danh sach inbox");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataGetListInbox);
			tblSystemActions.setResponseData(resInbox.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resInbox.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc getListInbox Exception ", e);
			resInbox.setStatus(statusFale);
			resInbox.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resInbox.toJSON()).build();
		}
	}

	public Response updateInbox(String dataUpdateInbox) {
		log.info("----------------Bat dau updateInbox--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResUPDInbox resUPDInbox = new ResUPDInbox();
		try {
			log.info("updateInbox dataUpdateInbox: " + dataUpdateInbox);
			ReqUPDInbox reqUPDInbox = gson.fromJson(dataUpdateInbox, ReqUPDInbox.class);
			boolean checkLG = userInfo.checkLogin(reqUPDInbox.getUsername(), reqUPDInbox.getToken());
			if (!checkLG) {
				log.info("updateInbox : " + reqUPDInbox.getUsername() + " check login false:");
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				log.info(messageErr);
				resUPDInbox.setStatus(statusFale);
				resUPDInbox.setMessage(messageErr);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUPDInbox.toJSON()).build();
			}

			try {
				List<ObjInbox> inbox_id = reqUPDInbox.getInbox();
				for (ObjInbox string : inbox_id) {
					log.info("updateInbox inbox_id: " + string.getInbox_id());
					TblInbox getInboxs = dbFintechHome.getInbox(reqUPDInbox.getUsername(), string.getInbox_id());
					getInboxs.setInboxRead(1);
					boolean checkUPD = dbFintechHome.updateTblInbox(getInboxs);
					log.info("updateInbox inbox_id checkUPD: " + checkUPD);
				}
				resUPDInbox.setStatus(statusSuccess);
				resUPDInbox.setMessage("Yeu cau thanh cong");
			} catch (Exception e) {
				log.info("updateInbox Exception inbox: ", e);
				resUPDInbox.setStatus(statusFale);
				resUPDInbox.setMessage("Yeu cau that bai - Da co loi xay ra");
			}

			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("updateInbox: " + reqUPDInbox.getUsername() + " response to client:" + resUPDInbox.toJSON());
			log.info("----------------Ket thuc getListInbox: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Update inbox");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataUpdateInbox);
			tblSystemActions.setResponseData(resUPDInbox.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUPDInbox.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc updateInbox Exception ", e);
			resUPDInbox.setStatus(statusFale);
			resUPDInbox.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUPDInbox.toJSON()).build();
		}
	}

	public Response updateInsurance(String dataUpdateInsurance) {
		log.info("----------------Bat dau updateInsurance--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResUpdateInsurance resUpdateInsurance = new ResUpdateInsurance();
		try {
			log.info("updateInsurance dataUpdateInsurance: " + dataUpdateInsurance);
			ReqUpdateInsurance reqUpdateInsurance = gson.fromJson(dataUpdateInsurance, ReqUpdateInsurance.class);
			ResUpdateInsurance resUpdateInsurance2 = validData.validDataUpdateInsurance(reqUpdateInsurance);
			if (resUpdateInsurance2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUpdateInsurance2.toJSON())
						.build();
			}

			Account acc = accountHome.getAccountUsename(reqUpdateInsurance.getUsername());
			String fullName = reqUpdateInsurance.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
			String loan_code = "";
			try {
				loan_code = reqUpdateInsurance.getLoan_code();
			} catch (Exception e) {
			}

			// List<Integer> branchID = new ArrayList<>();
			// List<Integer> roomID = new ArrayList<>();
			// if (ValidData.checkNull(acc.getBranchId()) == true) {
			// JSONObject isJsonObject = (JSONObject) new
			// JSONObject(acc.getBranchId());
			// Iterator<String> keys = isJsonObject.keys();
			// while (keys.hasNext()) {
			// String key = keys.next();
			// System.out.println(key);
			// JSONArray msg = (JSONArray) isJsonObject.get(key);
			// branchID.add(new Integer(key.toString()));
			// for (int i = 0; i < msg.length(); i++) {
			// roomID.add(Integer.parseInt(msg.get(i).toString()));
			// }
			// }
			// }

			// TblLoanRequest getLoanBranchID =
			// dbFintechHome.getLoanBranchID(branchID, loan_code);
			TblLoanRequest getLoanBranchID = dbFintechHome.getLoanRoleNDT(loan_code);

			if (getLoanBranchID != null) {
				List<TblImages> imagesListSet = new ArrayList<>();
				if (reqUpdateInsurance.getImages() != null) {
					List<ObjImage> imagesList = reqUpdateInsurance.getImages();
					for (ObjImage objImage : imagesList) {
						TblImages tblImages = new TblImages();
						tblImages.setLoanRequestDetailId(getLoanBranchID.getLoanId());
						tblImages.setImageName(objImage.getImage_name());
						tblImages.setImageInputName(objImage.getImage_input_name());
						tblImages.setPartnerImageId(objImage.getPartner_image_id());
						tblImages.setImageType((int) objImage.getImage_type());
						tblImages.setImageByte(objImage.getImage_byte());
						tblImages.setImageUrl(objImage.getImage_url());
						tblImages.setImageIsFront((int) objImage.getImage_is_front());
						tblImages.setUploadBy(reqUpdateInsurance.getUsername());
						tblImages.setCreatedDate(new Date());
						tblImages.setEditedDate(new Date());
						imagesListSet.add(tblImages);
					}
				}
				getLoanBranchID.setInsuranceStatus(Integer.parseInt(reqUpdateInsurance.getInsurance_status()));
				try {
					if (ValidData.checkNull(reqUpdateInsurance.getInsurance_paid_amount()) == true) {
						getLoanBranchID
								.setInsurancePaidAmount(new BigDecimal(reqUpdateInsurance.getInsurance_paid_amount()));
					}
				} catch (Exception e) {
				}
				try {
					if (ValidData.checkNull(reqUpdateInsurance.getInsurance_fee_amt()) == true) {
						getLoanBranchID.setInsuranceFeeAmt(new BigDecimal(reqUpdateInsurance.getInsurance_fee_amt()));
					}
				} catch (Exception e) {
				}
				try {
					if (ValidData.checkNull(reqUpdateInsurance.getInsurance_paid_amt_fr_api()) == true) {
						getLoanBranchID.setInsurancePaidAmtFrApi(
								new BigDecimal(reqUpdateInsurance.getInsurance_paid_amt_fr_api()));
					}
				} catch (Exception e) {
				}
				try {
					if (ValidData.checkNull(reqUpdateInsurance.getInsurance_fee_fr_api()) == true) {
						getLoanBranchID
								.setInsuranceFeeFrApi(new BigDecimal(reqUpdateInsurance.getInsurance_fee_fr_api()));
					}
				} catch (Exception e) {
				}
				try {
					if (ValidData.checkNull(reqUpdateInsurance.getPartner_insurance_code()) == true) {
						getLoanBranchID.setPartnerInsuranceCode(reqUpdateInsurance.getPartner_insurance_code());
					}
				} catch (Exception e) {
				}
				try {
					if (ValidData.checkNull(reqUpdateInsurance.getPartner_insurance_id()) == true) {
						getLoanBranchID.setPartnerInsuranceId(reqUpdateInsurance.getPartner_insurance_id());
					}
				} catch (Exception e) {
				}
				boolean checkINS = tbLoanRequestHome.createLoanTransIMG(getLoanBranchID, imagesListSet);
				if (checkINS) {
					resUpdateInsurance.setStatus(statusSuccess);
					resUpdateInsurance.setMessage("Yeu cau thanh cong");
					resUpdateInsurance.setLoan_code(getLoanBranchID.getLoanCode());

					TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
					tblLoanExpertiseSteps.setLoanId(getLoanBranchID.getLoanId());
					tblLoanExpertiseSteps.setExpertiseUser(fullName);
					tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
					tblLoanExpertiseSteps.setExpertiseStatus(getLoanBranchID.getFinalStatus());
					tblLoanExpertiseSteps.setExpertiseStep(1);
					tblLoanExpertiseSteps.setExpertiseComment(reqUpdateInsurance.getMemo());
					tblLoanExpertiseSteps.setLoanCode(getLoanBranchID.getLoanCode());
					tblLoanExpertiseSteps.setAction(reqUpdateInsurance.getAction());
					Thread t = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
					t.start();
				} else {
					log.info("updateInsurance checkINS false: ");
					resUpdateInsurance.setStatus(statusFale);
					resUpdateInsurance.setMessage("Yeu cau that bai");
				}
			} else {
				log.info("updateInsurance tblLoanRequest null: ");
				resUpdateInsurance.setStatus(statusFale);
				resUpdateInsurance.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("updateInsurance: " + reqUpdateInsurance.getUsername() + " response to client:"
					+ reqUpdateInsurance.toJSON());
			log.info("----------------Ket thuc getListInbox: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Update Insurance");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataUpdateInsurance);
			tblSystemActions.setResponseData(resUpdateInsurance.toJSON());
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUpdateInsurance.toJSON())
					.build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc updateInsurance Exception ", e);
			resUpdateInsurance.setStatus(statusFale);
			resUpdateInsurance.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUpdateInsurance.toJSON())
					.build();
		}
	}

	// Sent Email nhac no
	public Response sentEmail(String dataSentEmail) {
		log.info("----------------Bat dau sentEmail--------------------------");
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResSentEmail resSentEmail = new ResSentEmail();
		try {
			log.info("sentEmail dataSentEmail: " + dataSentEmail);
			ReqSentEmail reqSentEmail = gson.fromJson(dataSentEmail, ReqSentEmail.class);
			ResSentEmail resSentEmail2 = validData.validSentEmail(reqSentEmail);
			if (resSentEmail2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resSentEmail2.toJSON()).build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqSentEmail.getUsername());
			String fullName = reqSentEmail.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					branchID.add(new Integer(key.toString()));
					for (int i = 0; i < msg.length(); i++) {
						if (ValidData.checkNull(msg.get(i).toString()) == true) {
							roomID.add(Integer.parseInt(msg.get(i).toString()));
						}
					}
				}
			}
			System.out.println(gson.toJson(reqSentEmail));

			TblLoanRequest tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqSentEmail.getLoan_code());
			if (tblLoanRequest != null) {
				log.info("sentEmail tblLoanRequest: " + gson.toJson(tblLoanRequest));
				log.info("sentEmail tblLoanRequest.getLoanId: " + tblLoanRequest.getLoanId());

				TblLoanBill tblLoanBill = dbFintechHome.getTblLoanBill(Integer.parseInt(reqSentEmail.getBill_index()),
						tblLoanRequest.getLoanId());
				ObjDebtReminderRedis objDebtReminderRedis = new ObjDebtReminderRedis();
				objDebtReminderRedis.setLoan_id(tblLoanRequest.getLoanId().toString());
				objDebtReminderRedis.setLoan_code(tblLoanRequest.getLoanCode());
				objDebtReminderRedis.setBill_id(tblLoanBill.getBillId().toString());
				String key = "QUEUE_INDEBT_REMIND";
				log.info("updateExtendStatus key : " + key);
				log.info("updateExtendStatus ObjDebtReminderRedis : " + objDebtReminderRedis.toJSON());
				RedisBusiness redisBusiness = new RedisBusiness();
				boolean checkPush = redisBusiness.enQueueToRedis(key, objDebtReminderRedis.toJSON());
				log.info("updateExtendStatus checkPush : " + checkPush);
				if (checkPush) {
					resSentEmail.setStatus(statusSuccess);
					resSentEmail.setMessage("Yeu cau thanh cong");
					resSentEmail.setLoan_code(reqSentEmail.getLoan_code());
				} else {
					resSentEmail.setStatus(statusFale);
					resSentEmail.setMessage("Yeu cau that bai");
				}
			} else {
				log.info("updateExtendStatus tblLoanRequest null: ");
				resSentEmail.setStatus(statusFale);
				resSentEmail.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}

			TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
			tblLoanExpertiseSteps.setLoanId(tblLoanRequest.getLoanId());
			tblLoanExpertiseSteps.setExpertiseUser(fullName);
			tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
			tblLoanExpertiseSteps.setExpertiseStatus(tblLoanRequest.getFinalStatus());
			tblLoanExpertiseSteps.setExpertiseStep(5);
			tblLoanExpertiseSteps.setExpertiseComment("");
			tblLoanExpertiseSteps.setLoanCode(tblLoanRequest.getLoanCode());
			tblLoanExpertiseSteps.setAction("G???i email nh???c n???");
			Thread tEmail = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
			tEmail.start();

			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			log.info("updateExtendStatus: " + reqSentEmail.getUsername() + " response to client:"
					+ resSentEmail.toJSON().replace("'\'", ""));
			log.info("----------------Ket thuc updateExtendStatus: ");
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API Update trang thai giao dich");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataSentEmail);
			tblSystemActions.setResponseData(resSentEmail.toJSON());
			// tblSystemActions.setRootId(rootId);
			// tblSystemActions.setActionId(actionId);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resSentEmail.toJSON()).build();
		} catch (Exception e) {
			// e.printStackTrace();
			log.fatal("----------------Ket thuc updateExtendStatus Exception ", e);
			resSentEmail.setStatus(statusFale);
			resSentEmail.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resSentEmail.toJSON()).build();
		}
	}

	public static void main(String[] args) {
		try {
			// Bussiness bussiness = new Bussiness();
			// AccountHome accountHome = new AccountHome();
			// Account acc =
			// accountHome.getAccountUsename("dinhphuong.v@gmail.com");
			// List<Integer> branchID = new ArrayList<>();
			// List<Integer> roomID = new ArrayList<>();
			// if (ValidData.checkNull(acc.getBranchId()) == true) {
			// JSONObject isJsonObject = (JSONObject) new
			// JSONObject(acc.getBranchId());
			// Iterator<String> keys = isJsonObject.keys();
			// while (keys.hasNext()) {
			// String key = keys.next();
			// System.out.println(key);
			// JSONArray msg = (JSONArray) isJsonObject.get(key);
			// branchID.add(new Integer(key.toString()));
			// for (int i = 0; i < msg.length(); i++) {
			// roomID.add(Integer.parseInt(msg.get(i).toString()));
			// }
			// }
			// } else {
			// System.out.println("null");
			// }
			// System.out.println("aa: " + branchID);
			// System.out.println("aa: " + roomID);
			// String aaa = "";
			// String dateaa = "20210608";
			// DBFintechHome dbFintechHome = new DBFintechHome();
			// TblLoanBillHome tblLoanBillHome = new TblLoanBillHome();
			// TblLoanBill tblLoanBill = tblLoanBillHome.getTblLoanBillIndex(1,
			// 1);
			// System.out.println(tblLoanBill.getLoanId());
			// tblLoanBill.setRealPaymentDate(new
			// SimpleDateFormat("yyyyMMdd").parse(dateaa));
			// boolean updLoanBill =
			// dbFintechHome.updateTblLoanBill(tblLoanBill);

			double rate = 418750;
			double rate1 = 451250;
			double rate2 = 483750;
			double rate3 = 516250;
			double rate4 = 548750;
			double rate5 = 581250;
			// System.out.println((double) Math.ulp(rate));

			System.out.println(Math.round(MathUtils.round(rate, -3)));
			System.out.println(Math.round(MathUtils.round(rate1, -3)));
			System.out.println(Math.round(MathUtils.round(rate2, -3)));
			System.out.println(Math.round(MathUtils.round(rate3, -3)));
			System.out.println(Math.round(MathUtils.round(rate4, -3)));
			System.out.println(Math.round(MathUtils.round(rate5, -3)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static double roundUp(double src) {
		double len = String.valueOf(src).length() - 1;
		if (len == 0)
			len = 1;
		double d = (double) Math.pow((double) 10, (double) len);
		return (src + (d - 1)) / d * d;
	}
}
