package estonia;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.Executors;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.util.CharsetUtil;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import estonia.dao.HibernateSessionFactory;
import estonia.dao.entities.PlayerEntity;
import estonia.nio.NettyInit;
import estonia.nio.packets.CodecHelper;

@Test
public class IntegrationTest {

	private SessionFactory sf;
	
	@BeforeSuite
	public void init() {
		
		// start netty
		NettyInit.init( Integer.parseInt( "7252" ) );
		// hibernate configuration
		@SuppressWarnings("deprecation")
		AnnotationConfiguration config = new AnnotationConfiguration().
				setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
				setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver"). 
				setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:test"). 
				setProperty("hibernate.connection.username", "SA"). 
				setProperty("hibernate.connection.password", ""). 
				setProperty("hibernate.connection.pool_size",  "1"). 
				setProperty("hibernate.current_session_context_class", "thread"). 
				setProperty("hibernate.connection.autocommit", "true"). 
				setProperty("hibernate.hbm2ddl.auto", "create-drop"). 
				setProperty("hibernate.show_sql", "true"). 
				addAnnotatedClass(PlayerEntity.class); 
		sf = config.buildSessionFactory(); 
		HibernateSessionFactory.init(sf);
		
	}
	
	@AfterSuite
	public void stop() {
		NettyInit.stop();
	}
	
	@Test
	public void test() {
		ChannelFactory factory =
                new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        ClientBootstrap cb = new ClientBootstrap(factory);
        ChannelFuture future = cb.connect(new InetSocketAddress("localhost", 7252));
        Channel channel = future.awaitUninterruptibly().getChannel();
        // Encoder
        channel.getPipeline().addLast("stringEncoder", new Encoder());
        if (future.isSuccess()) {
            String msg = getMessage("den", 520.);
            System.out.println("Message: " + msg);
            future = channel.write( msg );
            System.out.println(future.isSuccess());
            System.out.println(future.isDone());
            System.out.println(future.getCause());
        }
        
        try{
        	Thread.sleep(15000);
	        PlayerEntity p = (PlayerEntity) sf.openSession().get(PlayerEntity.class, "den");
	        Assert.assertNotNull(p);
	        Assert.assertEquals(p.getBalance().doubleValue(), 520.);
	        System.out.println(p.getUsername() + " has " +p.getBalance());
        }catch(Exception e){
        	Assert.fail(e.getMessage());
        	e.printStackTrace();
        }
	}
	
	private String getMessage(String username, double balanceChange) {
		StringBuilder msg = new StringBuilder();
		
		msg.append(username+";");
		
		Random r = new Random();
		String transaction = r.nextLong() + "T;";
		msg.append(transaction);
		
		msg.append(balanceChange);
		return msg.toString();
	}
	
	private class Encoder extends OneToOneEncoder {

		@Override
		protected Object encode(ChannelHandlerContext ctx, Channel channel,
				Object msg1) throws Exception {
			String msg =((String) msg1); 
			String[] out = msg.split(";");
			ChannelBuffer buffer = ChannelBuffers.buffer(1 + out[0].length() + 1 + out[1].length() + 8 + 1000);
			
			buffer.writeByte( out[0].length() );
			CodecHelper.writeString(out[0], buffer);
			
			buffer.writeByte( out[1].length() );
			CodecHelper.writeString(out[1], buffer);
			
			buffer.writeDouble( Double.parseDouble(out[2]) );
			
			return buffer;
		}
		
	}
	
}
