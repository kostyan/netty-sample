package estonia.bussineslogic.service;

import org.apache.log4j.Logger;

import estonia.bussineslogic.monitoring.SimpleMonitor;
import estonia.bussineslogic.service.dto.PlayerBalanceChangeIn;
import estonia.bussineslogic.service.dto.PlayerBalanceChangeOut;
import estonia.dao.HibernateSessionFactory;
import estonia.dao.IPlayerDao;
import estonia.dao.impl.PlayerDaoImpl;
import estonia.model.BalanceAction;
import estonia.model.PlayerBalanceAction;
import estonia.model.PlayerUpdatedBalanceResult;

public class PlayerServiceImpl {

	private static Logger log = Logger.getLogger(PlayerServiceImpl.class);
	private static PlayerServiceImpl playerService = new PlayerServiceImpl(); 
	// DI must be used here
	private IPlayerDao playerDao = new PlayerDaoImpl(HibernateSessionFactory.getFactory());
	
	private PlayerServiceImpl() { }
	
	public PlayerBalanceChangeOut performBalanceChanges(PlayerBalanceChangeIn in) {
		log.info( String.format("<INCOME_MSG> Request balance change on %f for username=%s, transactionId=%s", 
				in.getBalanceChange(), in.getUserName(), in.getTransactionId()) );
		PlayerBalanceChangeOut out = new PlayerBalanceChangeOut();
		
		SimpleMonitor mon = new SimpleMonitor();
		try {
			PlayerUpdatedBalanceResult createOrUpdatePlayerBalance = playerDao.createOrUpdatePlayerBalance( 
					new PlayerBalanceAction(in.getUserName(), new BalanceAction(in.getBalanceChange())) );
				
			out.setTransactionId( in.getTransactionId() );
			out.setBalanceChanges( in.getBalanceChange() );
			out.setBalanceVersion( createOrUpdatePlayerBalance.getBalanceVersion() );
			out.setBalanceAfterChange( createOrUpdatePlayerBalance.getBalanceAfterChange() );
			out.setErrorCode( createOrUpdatePlayerBalance.getErrorCode().getErrorCode() );
		} finally {
			mon.stop();
		}
		
		log.info( String.format("<OUTCOME_MSG> Balance changed with errorCode=%d on %f for username=%s, transactionId=%s, balance after change=%f, balance version=%d",
				out.getErrorCode(), out.getBalanceChanges(), in.getUserName(), out.getTransactionId(), out.getBalanceAfterChange(),
				out.getBalanceVersion()) );
		
		return out;
	}
	
	public static PlayerServiceImpl getPlayerService() {
		return playerService;
	}
	
}
