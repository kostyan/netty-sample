package estonia.bussineslogic.service.dto;

/** Packet structure: 
 * username_lenght:byte,username:string,transactionId_lenght:byte,transactionId:string,balanceChange:double
 */
public class PlayerBalanceChangeIn {

	private String userName;
	private String transactionId;
	private double balanceChange;
	
	public int size() {
		return 1 + userName.length() + 1 + transactionId.length() + 8;
	}
	
	public PlayerBalanceChangeIn(){
	}
	
	public PlayerBalanceChangeIn(String userName, String transactionId,
			double balanceChange) {
		this.userName = userName;
		this.transactionId = transactionId;
		this.balanceChange = balanceChange;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public double getBalanceChange() {
		return balanceChange;
	}
	public void setBalanceChange(double balanceChange) {
		this.balanceChange = balanceChange;
	}
	
	
	
}
