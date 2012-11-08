package estonia.nio;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class WalletPipelineFactory implements ChannelPipelineFactory {

	@Override
    public ChannelPipeline getPipeline() throws Exception {
        PacketDecoder decoder = new PacketDecoder();
        PacketEncoder encoder = new PacketEncoder();
        return Channels.pipeline(decoder, encoder, new PlayerBalanceChangeHandler());
    }
	
}
