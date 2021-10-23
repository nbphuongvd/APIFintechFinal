package vn.com.payment.thread;

import vn.com.payment.entities.TblLoanExpertiseSteps;
import vn.com.payment.home.DBFintechHome;
import vn.com.payment.ultities.FileLogger;

public class ThreadInsertLogStep implements Runnable {
	TblLoanExpertiseSteps tblLoanExpertiseSteps;
	DBFintechHome dbFintechHome = new DBFintechHome();
	private FileLogger log = new FileLogger(ThreadInsertActionLog.class);
	public ThreadInsertLogStep(TblLoanExpertiseSteps tblLoanExpertiseSteps) {
		this.tblLoanExpertiseSteps = tblLoanExpertiseSteps;
	}

	public void run() {
		log.info("Bat dau ThreadInsertLogStep");
		boolean checkINS = dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
		log.info("ThreadInsertLogStep checkINS: " + checkINS);
		log.info("Ket thuc ThreadInsertLogStep");
	}
}
