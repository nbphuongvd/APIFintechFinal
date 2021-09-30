package vn.com.payment.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import com.google.gson.Gson;

import vn.com.payment.config.LogType;
import vn.com.payment.config.MainCfg;
import vn.com.payment.entities.Account;
import vn.com.payment.entities.GroupMapPer;
import vn.com.payment.entities.Permmission;
import vn.com.payment.entities.SubPermission;
import vn.com.payment.entities.TblRateConfig;
import vn.com.payment.entities.TblSystemActions;
import vn.com.payment.home.AccountHome;
import vn.com.payment.home.GroupMapPerHome;
import vn.com.payment.home.GroupRolesHome;
import vn.com.payment.home.PermmissionHome;
import vn.com.payment.home.SubPermissionHome;
import vn.com.payment.object.ObjectSubPer;
import vn.com.payment.object.RateConfigReq;
import vn.com.payment.object.RateConfigRes;
import vn.com.payment.object.ReqChangePass;
import vn.com.payment.object.ReqLogin;
import vn.com.payment.object.ResChangePass;
import vn.com.payment.object.ResLogin;
import vn.com.payment.object.ResUserInfo;
import vn.com.payment.object.TokenRedis;
import vn.com.payment.redis.RedisBusiness;
import vn.com.payment.thread.ThreadInsertActionLog;
import vn.com.payment.thread.ThreadInsertLogStep;
import vn.com.payment.ultities.Commons;
import vn.com.payment.ultities.FileLogger;
import vn.com.payment.ultities.MD5;
import vn.com.payment.ultities.Utils;
import vn.com.payment.ultities.ValidData;


public class UserInfo {
	GroupMapPerHome groupMapPerHome = new GroupMapPerHome();
	SubPermissionHome subPermissionHome = new SubPermissionHome();
	PermmissionHome permmissionHome = new PermmissionHome();
	GroupRolesHome groupRolesHome = new GroupRolesHome();
	AccountHome accountHome = new AccountHome();
	Utils utils = new Utils();
	public static String prefixKey = "APIFintech";
	Gson gson = new Gson();
	long statusSuccess = 100l;
	long statusFale = 111l;
	long statusFaleToken = 104l;
	public Response login(String dataLogin) {
		FileLogger.log("----------------Bat dau login--------------------------", LogType.USERINFO);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResLogin resLogin = new ResLogin();
		try {
			
			JSONObject jsonObject = new JSONObject();
			FileLogger.log("login dataLogin : " + dataLogin, LogType.USERINFO);
			ReqLogin reqLogin = gson.fromJson(dataLogin, ReqLogin.class);
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API LOGIN");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataLogin);
			if (ValidData.checkNull(reqLogin.getUsername()) == false
				|| ValidData.checkNull(reqLogin.getPwd()) == false
				|| ValidData.checkNullInt(reqLogin.getType()) == false){
				FileLogger.log("login invalid : ", LogType.USERINFO);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				resLogin.setStatus(statusFale);
				resLogin.setToken("");
				resLogin.setRequire_change_pass(0);
				resLogin.setMessage("Dữ liệu đăng nhập không đúng định dạng");
				resLogin.setFull_name("");
				resLogin.setRoles_id("");
				return response.header(Commons.ResponseTime, getTimeNow()).entity(resLogin.toJSON()).build();
			}
//			Account acc = accountHome.getAccountLogin(reqLogin.getUsername(), MD5.hash(reqLogin.getPwd()), reqLogin.getType());
			Account acc = accountHome.getAccountUsename(reqLogin.getUsername());
			if (acc != null){
				String fullName = acc.getUName();
				try {
					fullName = acc.getFirstName() + " " + acc.getLastName();
				} catch (Exception e) {
				}
				String pwdCheck = MD5.hash(reqLogin.getPwd());
				if(!pwdCheck.equals(acc.getPassword())){
					FileLogger.log("login sai pass : ", LogType.USERINFO);
					response = response.header(Commons.ReceiveTime, getTimeNow());
					resLogin.setStatus(statusFale);
					resLogin.setToken("");
					resLogin.setRequire_change_pass(0);
					resLogin.setMessage("Sai mật khẩu");
					resLogin.setFull_name("");
					resLogin.setRoles_id("");
					return response.header(Commons.ResponseTime, getTimeNow()).entity(resLogin.toJSON()).build();
				}
				if(reqLogin.getType() != acc.getType()){
					FileLogger.log("login sai pass : ", LogType.USERINFO);
					response = response.header(Commons.ReceiveTime, getTimeNow());
					resLogin.setStatus(statusFale);
					resLogin.setToken("");
					resLogin.setRequire_change_pass(0);
					resLogin.setMessage("Sai thông tin đăng nhập vui lòng kiểm tra lại với admin dịch vụ");
					resLogin.setFull_name("");
					resLogin.setRoles_id("");
					return response.header(Commons.ResponseTime, getTimeNow()).entity(resLogin.toJSON()).build();
				}
				
				FileLogger.log("login acc ! null : " + gson.toJson(acc), LogType.USERINFO);
				List<GroupMapPer> results = new ArrayList<>();
				
				ArrayList<Integer> subPer = new ArrayList<Integer>();
				try {
					results = groupMapPerHome.getGroupMapPer(Integer.parseInt(acc.getRolesId()));
					for (GroupMapPer groupMapPer : results) {
						if(!subPer.contains(groupMapPer.getSubPermissionId())){
							//Gan ArrayList sub_per
							//[2,3,4,5,6,7,8,9]
							subPer.add(groupMapPer.getSubPermissionId());
						}
					}
//					System.out.println(subPer);
//					System.out.println("fuck");
					//Gan ArrayList Permission
					ArrayList<Integer> permiss = new ArrayList<Integer>();
					for (int i : subPer) {
						SubPermission getSubPermission = subPermissionHome.getSubPermissionRowID(i);
						 if(!permiss.contains(getSubPermission.getPermissionId())){
							//Gan ArrayList permission
							//[2,3,4,5,6,7,8,9]
							 permiss.add(getSubPermission.getPermissionId());
						}
					 }
//					System.out.println(permiss);
//					System.out.println("fuck2");
					
					JSONArray array = new JSONArray();
					for (int i : permiss) {
						ObjectSubPer objectSubPer = new ObjectSubPer();
						Permmission permmission = new Permmission();
						permmission = permmissionHome.getPermmission(i);
						objectSubPer.setName(permmission.getName());
						objectSubPer.setIcon(permmission.getIcon());
						List<SubPermission> listSubPermission = new ArrayList<>();
						listSubPermission = subPermissionHome.getSubPermissionid(i, subPer);
						objectSubPer.setSub_permission(listSubPermission);				
						array.add(objectSubPer);					
					}
					jsonObject.put("permission", array);
				} catch (Exception e) {
					FileLogger.log("login lay permission false : " + e, LogType.ERROR);
				}
				
//				int branch_id = 0;
//				if (ValidData.checkNull(acc.getBranchId()) == true) {
//					JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
//					Iterator<String> keys = isJsonObject.keys();
//					while (keys.hasNext()) {
//						String keyNext = keys.next();
////						JSONArray msg = (JSONArray) isJsonObject.get(keyNext);
//						branch_id = Integer.parseInt(keyNext);
//					}
//				}

				String key = prefixKey + reqLogin.getUsername();
				String tokenResponse = RedisBusiness.getValue_fromCache(key);
				if(tokenResponse == null){				
					FileLogger.log("login sinh token va push len cache : ", LogType.USERINFO);
					String token = UUID.randomUUID().toString().replace("-", "");
					TokenRedis tokenRedis = new TokenRedis();
					tokenRedis.setToken(token);
					tokenRedis.setUsername(reqLogin.getUsername());
					tokenRedis.setExpire(getTimeEXP());
					boolean checkPush = RedisBusiness.setValue_toCacheTime(key, tokenRedis.toJSON(), MainCfg.timeExp);
					if(checkPush == true){
						FileLogger.log("login Gettoken setValue_toCacheTime success------", LogType.USERINFO);
//						resLogin.setStatus(statusSuccess);
//						resLogin.setToken(token);
//						resLogin.setRequire_change_pass(acc.getRequireChangePass());
//						resLogin.setPermission(array.toString());

						jsonObject.put("status", statusSuccess);
					    jsonObject.put("token", tokenRedis.getToken());
					    jsonObject.put("require_change_pass", acc.getRequireChangePass());	
					    jsonObject.put("message", "Đăng nhập thành công");
					    jsonObject.put("full_name", fullName);
					    jsonObject.put("branch_id", acc.getBranchId());
					    jsonObject.put("roles_id", acc.getRolesId());
						response = response.header(Commons.ReceiveTime, getTimeNow());	
						tblSystemActions.setResponseData(jsonObject.toString());
						Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
						t.start();
						return response.header(Commons.ResponseTime, getTimeNow()).entity(jsonObject.toString()).build();
					}else{
						FileLogger.log("login Gettoken setValue_toCacheTime false ------", LogType.USERINFO);
						resLogin.setStatus(statusFale);
						resLogin.setToken("");
						resLogin.setRequire_change_pass(0);
						resLogin.setMessage("Đã có lỗi xảy ra - vui lòng kiểm tra lại với admin dịch vụ");
						resLogin.setFull_name("");
						response = response.header(Commons.ReceiveTime, getTimeNow());
					}
				}else{
					TokenRedis tokenRedis = gson.fromJson(tokenResponse, TokenRedis.class);
					FileLogger.log("login Gettoken setValue_toCacheTime success------", LogType.USERINFO);
//					resLogin.setStatus(statusSuccess);
//					resLogin.setToken(tokenRedis.getToken());
//					resLogin.setRequire_change_pass(acc.getRequireChangePass());
//					resLogin.setPermission(array.toString());
					
					jsonObject.put("status", statusSuccess);
				    jsonObject.put("token", tokenRedis.getToken());
				    jsonObject.put("require_change_pass", acc.getRequireChangePass());
				    jsonObject.put("message", "Đăng nhập thành công");
				    jsonObject.put("full_name", fullName);
				    jsonObject.put("branch_id", acc.getBranchId());
				    jsonObject.put("roles_id", acc.getRolesId());
					response = response.header(Commons.ReceiveTime, getTimeNow());
					tblSystemActions.setResponseData(jsonObject.toString());
					Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
					t.start();
					return response.header(Commons.ResponseTime, getTimeNow()).entity(jsonObject.toString()).build();
				}
			}else{
				FileLogger.log("login Gettoken false username or pass", LogType.USERINFO);
				resLogin.setStatus(statusFaleToken);
				resLogin.setToken("");
				resLogin.setRequire_change_pass(0);
				resLogin.setMessage("Sai tên đăng nhập");
				resLogin.setFull_name("");
				resLogin.setRoles_id("");
				response = response.header(Commons.ReceiveTime, getTimeNow());
			}
			
			tblSystemActions.setResponseData(resLogin.toJSON());
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();	
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resLogin.toJSON()).build();
		} catch (Exception e) {
//			e.printStackTrace();
			FileLogger.log("----------------Ket thuc login Exception "+ e.getMessage(), LogType.ERROR);
			resLogin.setStatus(statusFale);
			resLogin.setToken("");
			resLogin.setRequire_change_pass(0);
			resLogin.setMessage("Đã có lỗi xảy ra - vui lòng kiểm tra lại với admin dịch vụ");
			resLogin.setFull_name("");
			resLogin.setRoles_id("");
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resLogin.toJSON()).build();
		}
	}
	
	public Response changePass(String dataChangePass) {
		FileLogger.log("----------------Bat dau changePass--------------------------", LogType.USERINFO);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResChangePass resChangePass = new ResChangePass();
		try {
			ReqChangePass reqChangePass = gson.fromJson(dataChangePass, ReqChangePass.class);
			if (ValidData.checkNull(reqChangePass.getUsername()) == false
					|| ValidData.checkNull(reqChangePass.getPwd()) == false
					|| ValidData.checkNull(reqChangePass.getToken()) == false
					|| ValidData.checkNull(reqChangePass.getNew_pwd()) == false){
				FileLogger.log("changePass invalid : ", LogType.USERINFO);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				resChangePass.setStatus(statusFale);
				resChangePass.setMessage("Dữ liệu đổi mật khẩu không đúng định dạng");
				return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
			}
			
			AccountHome accountHome = new AccountHome();
//			Account acc = accountHome.getAccount(reqChangePass.getUsername(), MD5.hash(reqChangePass.getPwd()));
			Account acc = accountHome.getAccountUsename(reqChangePass.getUsername());
			if (acc != null){
				String pwdCheck = MD5.hash(reqChangePass.getPwd());
				if(!pwdCheck.equals(acc.getPassword())){
					FileLogger.log("changePass pass : ", LogType.USERINFO);
					response = response.header(Commons.ReceiveTime, getTimeNow());
					resChangePass.setStatus(statusFale);
					resChangePass.setMessage("Sai mật khẩu");
					return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
				}				
				
				String key = prefixKey + reqChangePass.getUsername();
				String tokenResponse = RedisBusiness.getValue_fromCache(key);
				if(tokenResponse == null){				
					FileLogger.log("changePass sinh token va push len cache : ", LogType.USERINFO);
					String token = UUID.randomUUID().toString().replace("-", "");
					TokenRedis tokenRedis = new TokenRedis();
					tokenRedis.setToken(token);
					tokenRedis.setUsername(reqChangePass.getUsername());
					tokenRedis.setExpire(getTimeEXP());
					boolean checkPush = RedisBusiness.setValue_toCacheTime(key, tokenRedis.toJSON(), MainCfg.timeExp);
					if(checkPush == true){
						FileLogger.log("changePass Gettoken setValue_toCacheTime success------", LogType.USERINFO);						
						acc.setPassword(MD5.hash(reqChangePass.getNew_pwd()));
						acc.setRequireChangePass(1);
						boolean checkUPD = accountHome.updateAccount(acc);
						if(checkUPD){
							resChangePass.setStatus(statusSuccess);
							resChangePass.setMessage("Thanh cong");
						}else{
							resChangePass.setStatus(statusFale);
							resChangePass.setMessage("Đã có lỗi xảy ra - vui lòng kiểm tra lại với admin dịch vụ");
						}
					}else{
						FileLogger.log("changePass Gettoken setValue_toCacheTime false ------", LogType.USERINFO);
						resChangePass.setStatus(statusFale);
						resChangePass.setMessage("Đã có lỗi xảy ra - vui lòng kiểm tra lại với admin dịch vụ");
					}
				}else{
//					TokenRedis tokenRedis = gson.fromJson(tokenResponse, TokenRedis.class);
					FileLogger.log("changePass Gettoken setValue_toCacheTime success------", LogType.USERINFO);
					acc.setPassword(MD5.hash(reqChangePass.getNew_pwd()));
					acc.setRequireChangePass(1);
					boolean checkUPD = accountHome.updateAccount(acc);
					if(checkUPD){
						resChangePass.setStatus(statusSuccess);
						resChangePass.setMessage("Thanh cong");
					}else{
						resChangePass.setStatus(statusFale);
						resChangePass.setMessage("Đã có lỗi xảy ra - vui lòng kiểm tra lại với admin dịch vụ");
					}
				}
			}else{
				FileLogger.log("changePass pass : ", LogType.USERINFO);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				resChangePass.setStatus(statusFale);
				resChangePass.setMessage("Sai tên đăng nhập");
				return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
			}
			response = response.header(Commons.ReceiveTime, getTimeNow());
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API CHANGEPASS");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataChangePass);
			tblSystemActions.setResponseData(resChangePass.toJSON());
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
		} catch (Exception e) {
//			e.printStackTrace();
			FileLogger.log("----------------Ket thuc changePass Exception "+ e.getMessage(), LogType.ERROR);
			resChangePass.setStatus(statusFale);
			resChangePass.setMessage("Đã có lỗi xảy ra - vui lòng kiểm tra lại với admin dịch vụ");
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
		}
	}
	
	public Response resetPass(String dataResetPass) {
		FileLogger.log("----------------Bat dau resetPass--------------------------", LogType.USERINFO);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResChangePass resChangePass = new ResChangePass();
		try {
			ReqChangePass reqChangePass = gson.fromJson(dataResetPass, ReqChangePass.class);
			if (ValidData.checkNull(reqChangePass.getUsername()) == false){
				FileLogger.log("changePass invalid : ", LogType.USERINFO);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				resChangePass.setStatus(statusFale);
				resChangePass.setMessage("Dữ liệu đặt lại mật khẩu mặc định không đúng định dạng");
				return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
			}
			String newPass = getRandomStr(8);
//			System.out.println("newPass: "+ newPass);
			AccountHome accountHome = new AccountHome();
			Account acc = accountHome.getAccountUsename(reqChangePass.getUsername());
			if (acc != null){
				acc.setPassword(MD5.hash(MD5.hash(newPass)));
				boolean checkUPD = accountHome.updateAccount(acc);
				FileLogger.log("changePass username: " + reqChangePass.getUsername() + " checkUPD newPass:" + checkUPD, LogType.USERINFO);
				if(checkUPD){
					resChangePass.setStatus(statusSuccess);
					resChangePass.setMessage("Thanh cong");
					
					//Sent email
					String key = prefixKey + "_NOTIFY";
					String subject = "Thông báo thay đổi mật khẩu";
					String content = newPass;
					String message = "1";
					String isHtml  = "true";
					String receiveEmail = acc.getEmail();
					String receiveSMS = "";
					String receiveChat = "";
					String serviceCode = "API";
					String subService = "APIFintech";
					boolean sentNoti = utils.sentNotify(key, reqChangePass.getUsername(), subject, content, message, isHtml, receiveEmail, receiveSMS, receiveChat, serviceCode, subService);
					FileLogger.log("changePass sentNoti : " + sentNoti, LogType.USERINFO);
				}else{
					resChangePass.setStatus(statusFale);
					resChangePass.setMessage("Đã có lỗi xảy ra - vui lòng kiểm tra lại với admin dịch vụ");					
				}
			}else{
				FileLogger.log("changePass username: " + reqChangePass.getUsername() + " false getAccountUsename null:", LogType.USERINFO);
				resChangePass.setStatus(statusFale);
				resChangePass.setMessage("Sai tên đăng nhập");
			}
			response = response.header(Commons.ReceiveTime, getTimeNow());
			TblSystemActions tblSystemActions = new TblSystemActions();
			tblSystemActions.setActionType("API RESETPASS");
			tblSystemActions.setRegisterDate(new Date());
			tblSystemActions.setActionData(dataResetPass);
			tblSystemActions.setResponseData(resChangePass.toJSON());
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
		} catch (Exception e) {
//			e.printStackTrace();
			FileLogger.log("----------------Ket thuc resetPass Exception "+ e.getMessage(), LogType.ERROR);
			resChangePass.setStatus(statusFale);
			resChangePass.setMessage("Đã có lỗi xảy ra - vui lòng kiểm tra lại với admin dịch vụ");
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
		}
	}
	
	public Response getUserInfo(String dataGetInfo) {
		FileLogger.log("----------------Bat dau getInfo--------------------------", LogType.BUSSINESS);
		UserInfo userInfo = new UserInfo();
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResUserInfo resUserInfo = new ResUserInfo();
		JSONObject jsonObjectInfo = new JSONObject();
		TblSystemActions tblSystemActions = new TblSystemActions();
		tblSystemActions.setActionType("API get user info");
		tblSystemActions.setRegisterDate(new Date());
		tblSystemActions.setActionData(dataGetInfo);
		tblSystemActions.setResponseData(resUserInfo.toJSON());
		try {
			FileLogger.log("getUserInfo dataGetInfo: " + dataGetInfo, LogType.BUSSINESS);
			RateConfigReq rateConfigReq = gson.fromJson(dataGetInfo, RateConfigReq.class);
			if (ValidData.checkNull(rateConfigReq.getUsername()) == false || ValidData.checkNull(rateConfigReq.getToken()) == false) {
				FileLogger.log("getUserInfo: " + rateConfigReq.getUsername() + " invalid : ", LogType.BUSSINESS);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				resUserInfo.setStatus(statusFale);
				resUserInfo.setMessage("Lay thong tin that bai - Invalid message request");
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUserInfo.toJSON()).build();
			}
			
			boolean checkLG = userInfo.checkLogin(rateConfigReq.getUsername(), rateConfigReq.getToken());
			if (checkLG) {
				Account acc = accountHome.getAccountUsename(rateConfigReq.getUsername());
				acc.setPassword("");
				List<GroupMapPer> results = new ArrayList<>();
				ArrayList<Integer> subPer = new ArrayList<Integer>();
				try {
					results = groupMapPerHome.getGroupMapPer(Integer.parseInt(acc.getRolesId()));
					for (GroupMapPer groupMapPer : results) {
						if(!subPer.contains(groupMapPer.getSubPermissionId())){
							//Gan ArrayList sub_per
							//[2,3,4,5,6,7,8,9]
							subPer.add(groupMapPer.getSubPermissionId());
						}
					}
//					System.out.println(subPer);
//					System.out.println("fuck");
					//Gan ArrayList Permission
					ArrayList<Integer> permiss = new ArrayList<Integer>();
					for (int i : subPer) {
						SubPermission getSubPermission = subPermissionHome.getSubPermissionRowID(i);
						 if(!permiss.contains(getSubPermission.getPermissionId())){
							//Gan ArrayList permission
							//[2,3,4,5,6,7,8,9]
							 permiss.add(getSubPermission.getPermissionId());
						}
					 }
					JSONArray array = new JSONArray();
					for (int i : permiss) {
						ObjectSubPer objectSubPer = new ObjectSubPer();
						Permmission permmission = new Permmission();
						permmission = permmissionHome.getPermmission(i);
						objectSubPer.setName(permmission.getName());
						objectSubPer.setIcon(permmission.getIcon());
						List<SubPermission> listSubPermission = new ArrayList<>();
						listSubPermission = subPermissionHome.getSubPermissionid(i, subPer);
						objectSubPer.setSub_permission(listSubPermission);				
						array.add(objectSubPer);					
					}
//					resUserInfo.setStatus(statusSuccess);
//					resUserInfo.setMessage("Lay thong tin thanh cong");
//					resUserInfo.setPermission(array.toString());
//					resUserInfo.setAccount(acc);
					
					jsonObjectInfo.put("status", statusSuccess);
					jsonObjectInfo.put("message", "Lấy thông tin thành công");
					jsonObjectInfo.put("permission", array);
				    
					jsonObjectInfo.put("u_name", acc.getUName());
					jsonObjectInfo.put("first_name", acc.getFirstName());
					jsonObjectInfo.put("last_name", acc.getLastName());
					jsonObjectInfo.put("gender", acc.getGender());
					jsonObjectInfo.put("phone", acc.getPhone());
					jsonObjectInfo.put("email", acc.getEmail());
					jsonObjectInfo.put("status_Acc", acc.getStatus());
				    jsonObjectInfo.put("roles_id", acc.getRolesId());
				    jsonObjectInfo.put("branch_id", acc.getBranchId());
				    jsonObjectInfo.put("type", acc.getType());
				    jsonObjectInfo.put("branch_id", acc.getBranchId());
				    
				    tblSystemActions.setResponseData(jsonObjectInfo.toString());
					Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
					t.start();
					return response.header(Commons.ResponseTime, getTimeNow()).entity(jsonObjectInfo.toString()).build();
				} catch (Exception e) {
					FileLogger.log("getUserInfo lay permission false : " + e, LogType.ERROR);
					resUserInfo.setStatus(statusFale);
					resUserInfo.setMessage("Lay thong tin that bai - Khong lay duoc thong tin quyen cua user");
				}			
			} else {
				FileLogger.log("getUserInfo: " + rateConfigReq.getUsername() + " check login false:", LogType.BUSSINESS);
				resUserInfo.setStatus(statusFale);
				resUserInfo.setMessage("Lay thong tin that bai - Thong tin login sai");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log(
					"getUserInfo: " + rateConfigReq.getUsername() + " response to client:" + resUserInfo.toJSON(),
					LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getRateConfig: ", LogType.BUSSINESS);
			Thread t = new Thread(new ThreadInsertActionLog(tblSystemActions));
			t.start();
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUserInfo.toJSON()).build();
		} catch (Exception e) {
//			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getRateConfig Exception " + e.getMessage(), LogType.ERROR);
			resUserInfo.setStatus(statusFale);
			resUserInfo.setMessage("Lay thong tin that bai -  Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUserInfo.toJSON()).build();
		}
	}
	
	public boolean checkLogin(String userName, String token){
		boolean result = false;
		try {
			Account acc = accountHome.getAccountUsename(userName);
			if (acc != null){
				String key = UserInfo.prefixKey + userName;
				String tokenResponse = RedisBusiness.getValue_fromCache(key);
				if(tokenResponse != null){
					TokenRedis tokenRedis = gson.fromJson(tokenResponse, TokenRedis.class);
					if(token.equals(tokenRedis.getToken())){
						result = true;
					}else{
						FileLogger.log("checkLogin token_fromCache:" + tokenResponse + " # request_token: " + token, LogType.BUSSINESS);
					}
				}else{
					FileLogger.log("checkLogin token_fromCache null ", LogType.BUSSINESS);
				}
			}else{
				FileLogger.log("checkLogin getAccountUsename null ", LogType.BUSSINESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("checkLogin Exception "+ e, LogType.ERROR);
		}
		return result;
	}
	
	public static String getTimeNow() {
		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATETIME);
		return format.format(new Date());
	}
	
	public static String getTimeEXP() {
		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATE);
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		System.out.println(format.format(dt));
		return format.format(dt);
	}
	
	public static String getRandomStr(int length) {
		String stock = "0123456789abcdefghijklmnopqrstuvwxyz";
		String ran = "";
		for (int i = 0; i < length; i++) {
			ran += stock.charAt(new Random().nextInt(stock.length() - 1));
		}
		return ran;
	}
	
	
	public static void main(String[] args) {
		try {
//			UserInfo userInfo = new UserInfo();
//			String newPass = getRandomStr(8);
//			String key = prefixKey + "_NOTIFY";
//			String subject = "Thong bao thay doi mat khau";
//			String content = "Mat khau moi cua ban la: " + newPass;
//			String message = "1";
//			String isHtml  = "true";
//			String receiveEmail = "dinhphuong.v@gmail.com";
//			String receiveSMS = "";
//			String receiveChat = "";
//			String serviceCode = "API";
//			String subService = "APIFintech";
//			boolean sentNoti = userInfo.sentNotify(key, "userName" , subject, content, message, isHtml, receiveEmail, receiveSMS, receiveChat, serviceCode, subService);
//			System.out.println("sentNoti: " + sentNoti);
			System.out.println(MD5.hash("123456"));
			System.out.println(MD5.hash(MD5.hash("12345678")));
			// 123456 e10adc3949ba59abbe56e057f20f883e
			// 12345678 25d55ad283aa400af464c76d713c07ad
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
