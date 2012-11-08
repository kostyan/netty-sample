package estonia.model;

public class BalanceAction {

	private double balanceChange;
	private BalanceState balanceState;
	
	public enum BalanceState {
		IN, OUT;
	}

	public BalanceAction(double balanceChange) {
		super();
		setBalanceChange( balanceChange );
	}

	public double getBalanceChange() {
		return balanceChange;
	}

	public void setBalanceChange(double balance) {
		this.balanceChange = balance;
		this.balanceState = balance < 0 ? BalanceState.OUT : BalanceState.IN;
	}

	public BalanceState getBalanceState() {
		return balanceState;
	}

	
}
