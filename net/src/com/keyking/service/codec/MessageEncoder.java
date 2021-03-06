package com.keyking.service.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.keyking.service.exp.NotSenderClass;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class MessageEncoder extends ProtocolEncoderAdapter {

	@Override
	public void encode(IoSession session , Object obj , ProtocolEncoderOutput out)throws Exception {
		if (!(obj instanceof RespEntity)){
			throw new NotSenderClass(obj.getClass().getName());
		}
		RespEntity entity = (RespEntity)obj;
		DataBuffer buffer = DataBuffer.allocate(1024);
		entity.serialize(buffer);
		byte[] datas = buffer.arrayToPosition();
		out.write(IoBuffer.wrap(datas));
	}
}
 
 
