package estonia.nio;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

public final class NettyInit {

	private static boolean isInit = false;
	
	private NettyInit() {
		
	}
	
	private static Channel bindChannel;
	private static ServerBootstrap networkServer;
	
	public static synchronized void init(int port) {
		if( !isInit ) {
			ExecutorService bossExec = new OrderedMemoryAwareThreadPoolExecutor(1, 400000000, 2000000000, 60, TimeUnit.SECONDS);
			ExecutorService ioExec = new OrderedMemoryAwareThreadPoolExecutor(4, 400000000, 2000000000, 60, TimeUnit.SECONDS);
			 networkServer = new ServerBootstrap(new NioServerSocketChannelFactory(bossExec, ioExec, 4));
			networkServer.setOption("backlog", 500);
			networkServer.setOption("connectTimeoutMillis", 10000);
			networkServer.setPipelineFactory(new WalletPipelineFactory());
			bindChannel = networkServer.bind(new InetSocketAddress(port));
			isInit = true;
		}
	}
	
	public static synchronized void stop() {
		bindChannel.close().awaitUninterruptibly(1000);
		networkServer.getFactory().releaseExternalResources();
	}
	
}
