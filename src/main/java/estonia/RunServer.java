package estonia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import estonia.bussineslogic.monitoring.ReportGenerator;
import estonia.dao.HibernateSessionFactory;
import estonia.dao.entities.PlayerEntity;
import estonia.nio.NettyInit;

public class RunServer {

	/**
	 * TODO add this class to pom
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		Properties serverProp = loadDbProperties();
		
		// statistic log
		TimerTask reportTimer = ReportGenerator.getInstance();
		new Timer().schedule(reportTimer, 60*1000, 60*1000);
		// start netty
		NettyInit.init( Integer.parseInt( serverProp.getProperty("server.port", "7252") ) );
		// hibernate configuration
		AnnotationConfiguration config = new AnnotationConfiguration().
				setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
				setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver"). 
				setProperty("hibernate.connection.url", serverProp.getProperty("hsql.url", "jdbc:hsqldb:mem:test")). 
				setProperty("hibernate.connection.username", serverProp.getProperty("hsql.login", "SA")). 
				setProperty("hibernate.connection.password", serverProp.getProperty("hsql.pass", "")). 
				setProperty("hibernate.connection.pool_size", serverProp.getProperty("hsql.poolsize", "1")). 
				setProperty("hibernate.current_session_context_class", "thread"). 
				setProperty("hibernate.connection.autocommit", "true"). 
				setProperty("hibernate.hbm2ddl.auto", serverProp.getProperty("hbm2ddl", "create-drop")). 
				setProperty("hibernate.show_sql", serverProp.getProperty("logsql", "true")). 
				addAnnotatedClass(PlayerEntity.class); 
		SessionFactory sf = config.buildSessionFactory(); 
		HibernateSessionFactory.init(sf);
	}
	
	private static Properties loadDbProperties() throws IOException {
		 Properties props = new Properties();
		 try{
	        FileInputStream fis = new FileInputStream("server.conf");
	        props.load(fis);    
	        fis.close();
		 }catch(FileNotFoundException fnf) {
			 System.out.println("DB configuration file (db.conf) was not found");
		 }
	     return props;
	}

}
