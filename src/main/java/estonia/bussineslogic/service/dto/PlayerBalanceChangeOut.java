package estonia.bussineslogic.service.dto;

/** Packet structure: 
 * transactionId_lenght:byte,transactionId:string,errorCode:int,balanceVersion:long,
 * balanceChange:double,balanceAfterChange:double
 */
public class PlayerBalanceChangeOut {

	private String transactionId;
	private int errorCode;
	private long balanceVersion;
	private double balanceChanges;
	private double balanceAfterChange;
	
	public int size() {
		return 1 + transactionId.length() + 4 + 8 + 8 + 8;
	}
	
	public PlayerBalanceChangeOut() {
		
	}
	
	public PlayerBalanceChangeOut(String transactionId, int errorCode,
			long balanceVersion, double balanceChanges,
			double balanceAfterChange) {
		this.transactionId = transactionId;
		this.errorCode = errorCode;
		this.balanceVersion = balanceVersion;
		this.balanceChanges = balanceChanges;
		this.balanceAfterChange = balanceAfterChange;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public long getBalanceVersion() {
		return balanceVersion;
	}
	public void setBalanceVersion(long balanceVersion) {
		this.balanceVersion = balanceVersion;
	}
	public double getBalanceChanges() {
		return balanceChanges;
	}
	public void setBalanceChanges(double balanceChanges) {
		this.balanceChanges = balanceChanges;
	}
	public double getBalanceAfterChange() {
		return balanceAfterChange;
	}
	public void setBalanceAfterChange(double balanceAfterChange) {
		this.balanceAfterChange = balanceAfterChange;
	}
	
	
}
