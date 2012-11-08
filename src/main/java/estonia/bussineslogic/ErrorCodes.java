package estonia.bussineslogic;

public enum ErrorCodes {
	SUCCESS(0),
	OPTIMISTIC_LOCK(1),
	PERSISTENCE_ERROR(2);
	
	ErrorCodes(int code) {
		errorCode = code;
	}
	
	private int errorCode;
	
	public int getErrorCode() {
		return errorCode;
	}
	
}
