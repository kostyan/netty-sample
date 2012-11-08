package estonia.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import estonia.bussineslogic.ErrorCodes;
import estonia.dao.entities.PlayerEntity;
import estonia.dao.impl.PlayerDaoImpl;
import estonia.model.BalanceAction;
import estonia.model.PlayerBalanceAction;
import estonia.model.PlayerUpdatedBalanceResult;

@Test
public class PlayerDaoTest {

	private static final String USER_NAME = "first";
	
	private SessionFactory buildSessionFactory;
	private IPlayerDao dao;

	@SuppressWarnings("deprecation")
	@BeforeSuite
	public void runInMemoryDbms() {
		AnnotationConfiguration config = new AnnotationConfiguration().
				setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
				setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver"). 
				setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:test"). 
				setProperty("hibernate.connection.username", "SA"). 
				setProperty("hibernate.connection.password", ""). 
				setProperty("hibernate.connection.pool_size", "1"). 
				setProperty("hibernate.current_session_context_class", "thread"). 
				setProperty("hibernate.connection.autocommit", "true"). 
				setProperty("hibernate.hbm2ddl.auto", "create-drop"). 
				setProperty("hibernate.show_sql", "true"). 
				addAnnotatedClass(PlayerEntity.class); 
		buildSessionFactory = config.buildSessionFactory(); 
		HibernateSessionFactory.init(buildSessionFactory);
		dao = new PlayerDaoImpl(HibernateSessionFactory.getFactory());
	}
	
	@BeforeTest
	public void afterTest(){
		Session session = buildSessionFactory.openSession(); 
		try { 
			session.createSQLQuery("delete from player").executeUpdate(); 
		} finally {
			session.close();
		}
	}
	
	@Test(description="Try to change balance for user")
	public void testBalanceChanges() {
		Session session = buildSessionFactory.openSession();
		try {
			PlayerBalanceAction player = new PlayerBalanceAction(USER_NAME, new BalanceAction(+12));
			PlayerUpdatedBalanceResult createOrUpdatePlayerBalance = dao.createOrUpdatePlayerBalance(player);
			Assert.assertTrue( createOrUpdatePlayerBalance.getErrorCode() == ErrorCodes.SUCCESS );
			
			PlayerUpdatedBalanceResult opRes = dao.createOrUpdatePlayerBalance( new PlayerBalanceAction(USER_NAME, new BalanceAction(-5)) );
			Assert.assertTrue( opRes.getErrorCode() == ErrorCodes.SUCCESS );
			Assert.assertEquals( opRes.getBalanceAfterChange(), 7.d );
			Assert.assertEquals( opRes.getBalanceVersion(), 2L );
			
			PlayerEntity e = (PlayerEntity) session.get(PlayerEntity.class, USER_NAME);
			Assert.assertEquals(e.getBalance(), 7d);
			Assert.assertEquals( opRes.getBalanceVersion(), 2L );
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
		
	}
	
}
