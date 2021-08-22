package vn.com.payment.thread;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.TblSystemActions;
import vn.com.payment.home.DBFintechHome;
import vn.com.payment.ultities.FileLogger;

public class ThreadInsertActionLog implements Runnable {
	TblSystemActions tblSystemActions;
	DBFintechHome dbFintechHome = new DBFintechHome();

	public ThreadInsertActionLog(TblSystemActions tblSystemActions) {
		this.tblSystemActions = tblSystemActions;
	}

	public void run() {
		FileLogger.log("Bat dau ThreadInsertActionLog", LogType.BUSSINESS);
		boolean checkINS = dbFintechHome.createSystemActions(tblSystemActions);
		FileLogger.log("ThreadInsertActionLog checkINS: " + checkINS, LogType.BUSSINESS);
		FileLogger.log("Ket thuc ThreadInsertActionLog", LogType.BUSSINESS);
	}
}
