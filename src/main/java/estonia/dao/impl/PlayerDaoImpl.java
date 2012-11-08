package estonia.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;

import estonia.bussineslogic.ErrorCodes;
import estonia.dao.IPlayerDao;
import estonia.dao.entities.PlayerEntity;
import estonia.model.PlayerBalanceAction;
import estonia.model.PlayerUpdatedBalanceResult;

public class PlayerDaoImpl implements IPlayerDao {

	private static Logger log = Logger.getLogger(PlayerDaoImpl.class);
	private final SessionFactory factory;
	
	public PlayerDaoImpl(SessionFactory factory) {
		this.factory = factory; 
	}
	
	@Override
	public PlayerUpdatedBalanceResult createOrUpdatePlayerBalance(PlayerBalanceAction player) {
		PlayerUpdatedBalanceResult res = new PlayerUpdatedBalanceResult();
		ErrorCodes returnErrorCode = ErrorCodes.SUCCESS;
		
		Session session = factory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			PlayerEntity playerEntity = getPlayerByUsernameInSession(session, player.getUserName());
			if(playerEntity != null) {
				res.setBalanceAfterChange( playerEntity.getBalance() );
				res.setBalanceVersion( playerEntity.getVersion() );
			} else {
				playerEntity = new PlayerEntity();
				playerEntity.setUsername(player.getUserName());
			}
			playerEntity.setBalance( playerEntity.getBalance() + player.getBalance().getBalanceChange() );
			session.saveOrUpdate(playerEntity);
			tx.commit();
			
			res.setBalanceVersion(res.getBalanceVersion() + 1);
			res.setBalanceAfterChange(res.getBalanceAfterChange() + player.getBalance().getBalanceChange());
		} catch(StaleObjectStateException optLock) {
			returnErrorCode = ErrorCodes.OPTIMISTIC_LOCK;
			log.debug("Optimistic lock exception", optLock);
		} catch(HibernateException he) {
			returnErrorCode = ErrorCodes.PERSISTENCE_ERROR;
			log.error("Persisiting error", he);
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
		
		res.setErrorCode( returnErrorCode );
		return res;
	}

	@Override
	public PlayerEntity getPlayerByUsername(String userName) {
		Session session = factory.getCurrentSession();
		try {
			return getPlayerByUsernameInSession(session, userName);
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}

	private PlayerEntity getPlayerByUsernameInSession(Session session, String userName) {
		return (PlayerEntity) session.get(PlayerEntity.class, userName);
	}
	
}
