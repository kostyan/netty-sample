package estonia.dao;

import org.hibernate.SessionFactory;

public final class HibernateSessionFactory {

	private static SessionFactory sessionFactory;

	private HibernateSessionFactory(SessionFactory sf) { 
	}
	
	
	public static synchronized void init(SessionFactory sf) {
		if(sessionFactory == null){
			sessionFactory = sf;
		}else{
			throw new IllegalArgumentException("SessionFactory already specified");
		}
	}
	
	public static synchronized SessionFactory getFactory() {
		return sessionFactory;
	}
	
	
}
