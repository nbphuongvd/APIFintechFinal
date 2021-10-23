package vn.com.payment.thread;

import vn.com.payment.entities.TblSystemActions;
import vn.com.payment.home.DBFintechHome;
import vn.com.payment.ultities.FileLogger;

public class ThreadInsertActionLog implements Runnable {
	TblSystemActions tblSystemActions;
	DBFintechHome dbFintechHome = new DBFintechHome();
	private FileLogger log = new FileLogger(ThreadInsertActionLog.class);

	public ThreadInsertActionLog(TblSystemActions tblSystemActions) {
		this.tblSystemActions = tblSystemActions;
	}

	public void run() {
		log.info("Bat dau ThreadInsertActionLog");
		boolean checkINS = dbFintechHome.createSystemActions(tblSystemActions);
		log.info("ThreadInsertActionLog checkINS: " + checkINS);
		log.info("Ket thuc ThreadInsertActionLog");
	}
}
