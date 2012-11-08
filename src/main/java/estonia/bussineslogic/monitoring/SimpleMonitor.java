package estonia.bussineslogic.monitoring;

public class SimpleMonitor {

	private static ReportGenerator reportGenerator = ReportGenerator.getInstance();
	private long startTime = 0;
	
	public SimpleMonitor() {
		startTime = System.currentTimeMillis();
	}
	
	public void stop() {
		long usedTime = System.currentTimeMillis() - startTime;
		reportGenerator.addTime(usedTime);
	}
}
