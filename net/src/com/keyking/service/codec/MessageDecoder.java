package com.keyking.service.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.keyking.service.util.DataBuffer;

public class MessageDecoder extends CumulativeProtocolDecoder {
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer buffer , ProtocolDecoderOutput out) throws Exception {
		int len = buffer.getInt();
		if (len != buffer.remaining()) {
			buffer.position(buffer.position() + buffer.remaining());
			return false;
		}
		byte[] datas = new byte[buffer.remaining()];
		buffer.get(datas);
		DataBuffer data = DataBuffer.wrap(datas);
		out.write(data);
		return true;
	}
}
 
 
