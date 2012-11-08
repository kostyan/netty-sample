package estonia.model;

import estonia.bussineslogic.ErrorCodes;

public class PlayerUpdatedBalanceResult {

	private ErrorCodes errorCode;
	private long balanceVersion;
	private double balanceAfterChange;
	public ErrorCodes getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(ErrorCodes errorCode) {
		this.errorCode = errorCode;
	}
	public long getBalanceVersion() {
		return balanceVersion;
	}
	public void setBalanceVersion(long balanceVersion) {
		this.balanceVersion = balanceVersion;
	}
	public double getBalanceAfterChange() {
		return balanceAfterChange;
	}
	public void setBalanceAfterChange(double balanceAfterChange) {
		this.balanceAfterChange = balanceAfterChange;
	}
	
	
}
