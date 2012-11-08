package estonia.nio;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import estonia.bussineslogic.service.PlayerServiceImpl;
import estonia.bussineslogic.service.dto.PlayerBalanceChangeIn;
import estonia.bussineslogic.service.dto.PlayerBalanceChangeOut;

public class PlayerBalanceChangeHandler extends SimpleChannelHandler {

	private static Logger log = Logger.getLogger(PlayerBalanceChangeHandler.class);
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		Channel ch = e.getChannel();
		
		PlayerBalanceChangeIn in = (PlayerBalanceChangeIn) e.getMessage();
		PlayerBalanceChangeOut out = PlayerServiceImpl.getPlayerService().performBalanceChanges(in);
		
	    ch.write(out);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		log.error("Exception on message", e.getCause());
		
	    Channel ch = e.getChannel();
	    ch.close();
	}
	
}
