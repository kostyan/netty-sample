package estonia.nio;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;
import org.jboss.netty.handler.codec.replay.VoidEnum;

import estonia.nio.packets.PlayerBalanceChangePacketProcessor;

public class PacketDecoder extends ReplayingDecoder<VoidEnum> {

	@Override
	protected Object decode(ChannelHandlerContext chc, Channel channel,
			ChannelBuffer buffer, VoidEnum arg) throws Exception {
		return PlayerBalanceChangePacketProcessor.read(buffer);
	}

}
