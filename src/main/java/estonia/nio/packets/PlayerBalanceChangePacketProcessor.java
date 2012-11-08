package estonia.nio.packets;

import org.jboss.netty.buffer.ChannelBuffer;

import estonia.bussineslogic.service.dto.PlayerBalanceChangeIn;
import estonia.bussineslogic.service.dto.PlayerBalanceChangeOut;

public final class PlayerBalanceChangePacketProcessor {

	private PlayerBalanceChangePacketProcessor() { }
	
	public static PlayerBalanceChangeIn read(ChannelBuffer buffer) {
		PlayerBalanceChangeIn in = new PlayerBalanceChangeIn();
		
		byte userNameLen = buffer.readByte();
		in.setUserName( CodecHelper.readString(userNameLen, buffer) );
		
		byte transactionIdLen = buffer.readByte();
		in.setTransactionId( CodecHelper.readString(transactionIdLen, buffer) );
		
		in.setBalanceChange( buffer.readDouble() );
		
		return in;
	}
	
	 public static void write(PlayerBalanceChangeOut out, ChannelBuffer buffer) {
		 buffer.writeByte( out.getTransactionId().length() );
		 CodecHelper.writeString(out.getTransactionId(), buffer);
		 
		 buffer.writeInt( out.getErrorCode() );
		 
		 buffer.writeLong( out.getBalanceVersion() );
		 
		 buffer.writeDouble( out.getBalanceChanges() );
		 
		 buffer.writeDouble( out.getBalanceAfterChange() );
	 }
	 	
}
