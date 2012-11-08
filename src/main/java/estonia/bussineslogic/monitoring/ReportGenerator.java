package estonia.bussineslogic.monitoring;

import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class ReportGenerator extends TimerTask {

	private static final Logger log = Logger.getLogger(ReportGenerator.class);
	private static ReportGenerator instance;
	private List<Long> statisticQueue = new LinkedList<>(); 
	private long min, max, sum;
	
	private ReportGenerator() { 
		reset();
	}
	
	public static synchronized ReportGenerator getInstance() {
		if(instance == null) {
			instance = new ReportGenerator();
		}
		return instance;
	}
	
	@Override
	public void run() {
		synchronized(this) {
			int invocations = statisticQueue.size();
			log.info(String.format("Processed request: %d", invocations));
			log.info(String.format("Min time on request %fs, max time %fs, average time %f sec", 
					invocations == 0 ? 0 : min/1000, max/1000, sum/invocations/1000));
			reset();
		}
	}
	
	public synchronized void addTime(Long usedTime) {
		statisticQueue.add(usedTime);
		min = min < usedTime ? min : usedTime;
		max = max > usedTime ? max : usedTime;
		sum += usedTime;
	}
	
	private void reset() {
		statisticQueue.clear();
		min = max = sum = 0;
	}

}
