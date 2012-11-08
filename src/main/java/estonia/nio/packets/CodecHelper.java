package estonia.nio.packets;

import org.jboss.netty.buffer.ChannelBuffer;

public final class CodecHelper {

	private CodecHelper() { }
	
	public static String readString(byte len, ChannelBuffer buffer) {
		StringBuilder builder = new StringBuilder();
		for(byte i = 0; i < len; i++) {
			builder.append( buffer.readChar() );
		}
		return builder.toString();
	}
	
	public static void writeString(String str, ChannelBuffer buffer) {
		for(int i = 0; i < str.length(); i++) {
			buffer.writeChar( str.charAt(i) );
		}
	}
}
