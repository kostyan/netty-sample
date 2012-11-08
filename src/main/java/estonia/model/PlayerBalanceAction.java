package estonia.model;

public class PlayerBalanceAction {

	private String userName;
	private BalanceAction balance;
	
		
	public PlayerBalanceAction(String userName, BalanceAction balance) {
		super();
		this.userName = userName;
		this.balance = balance;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BalanceAction getBalance() {
		return balance;
	}
	public void setBalance(BalanceAction balance) {
		this.balance = balance;
	}
	
	
}
