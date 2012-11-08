package estonia.dao;

import estonia.dao.entities.PlayerEntity;
import estonia.model.PlayerBalanceAction;
import estonia.model.PlayerUpdatedBalanceResult;

// declare methods for process Player persistence actions
public interface IPlayerDao {

	public PlayerUpdatedBalanceResult createOrUpdatePlayerBalance(PlayerBalanceAction player);
	public PlayerEntity getPlayerByUsername(String userName);
	
}
