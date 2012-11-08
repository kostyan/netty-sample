package estonia.nio;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import estonia.bussineslogic.service.dto.PlayerBalanceChangeOut;
import estonia.nio.packets.PlayerBalanceChangePacketProcessor;

public class PacketEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext chc, Channel chanel,
			Object obj) throws Exception {
		
		PlayerBalanceChangeOut out = (PlayerBalanceChangeOut) obj;
		ChannelBuffer buffer = ChannelBuffers.buffer(out.size());
		PlayerBalanceChangePacketProcessor.write(out, buffer);
		
		return buffer;
	}

}
