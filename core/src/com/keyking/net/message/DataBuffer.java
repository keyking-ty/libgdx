package com.keyking.net.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DataBuffer extends InputStream{

	public static Charset DEFAULT_CHARSET = Charset.UTF_8;
	
	ByteBuffer buf = null;
	
	int mark = -1;
	
	int save;//这个流有的数据长度
	
	public DataBuffer(int len){
		buf = ByteBuffer.allocate(len);
	}
	
	public static DataBuffer allocate(int len) {
		DataBuffer buffer = new DataBuffer(len);
		return buffer;
	}

	public static DataBuffer wrap(byte[] datas) {
		DataBuffer buffer = new DataBuffer(datas.length);
		buffer.put(datas);
		buffer.toHead();
		return buffer;
	}
	
	public void autoExpand(int start , int len){
		int end = start + len;
        int newCapacity = normalizeCapacity(end);
        if(newCapacity > buf.capacity())
            capacity(newCapacity);
        if(end > buf.limit()){
        	buf.limit(end);
        }	
	}
	
	public void autoExpand(int len){
		autoExpand(position(),len);
	}
	
	private int normalizeCapacity(int capacity) {
		int newCapacity;
		switch (capacity) {
		case 0:
		case 1:
		case 2:
		case 4:
		case 8:
		case 16:
		case 32:
		case 64: 
		case 128:
		case 256:
		case 512:
		case 1024:
		case 2048:
		case 4096:
		case 8192:
		case 16384:
		case 32768:
		case 65536:
		case 131072:
		case 262144:
		case 524288:
		case 2097152:
		case 4194304:
		case 8388608:
		case 16777216:
		case 33554432:
		case 67108864:
		case 134217728:
		case 268435456:
		case 536870912:
		case 1073741824:
		case 2147483647:
			return capacity;
		default:
			newCapacity = 1;
			break;
		}
		while (newCapacity < capacity) {
			newCapacity <<= 1;
			if (newCapacity < 0)
				return 2147483647;
		}
		return newCapacity;
	}

	public void capacity(int newCapacity){
       if(newCapacity > buf.capacity()){
           int pos = position();
           ByteOrder bo = buf.order();
           ByteBuffer newBuf = ByteBuffer.allocate(newCapacity);
           buf.clear();
           newBuf.put(buf);
           buf = newBuf;
           if(mark >= 0){
                buf.position(mark);
                buf.mark();
           }
           buf.position(pos);
           buf.order(bo);
        }
    }
	public void skip(int len){
		int newposition = buf.position() + len;
		buf.position(newposition);
	}
	
	public void mark(){
        buf.mark();
        mark = position();
	}
	
	public void reset(){
		buf.reset();
	}
	
	public int position() {
		return buf.position();
	}

	public void position(int position) {
		buf.position(position);
		if(mark > position){
			mark = -1;
		}  
	}

	public void toHead() {
		buf.clear();
	}
	
	public byte[] arrayToPosition() {
		int len = position();
		toHead();
		return getByteArray(len);
	}
	
	public byte get() {
		return buf.get();
	}

	public void put(byte data){
		autoExpand(1);
		buf.put(data);
		if (position() > save){
			save = position();
		}
	}
	
	public void put(int index , byte data){
		autoExpand(index,1);
		buf.put(index,data);
		if (position() > save){
			save = position();
		}
	}

	public byte get(int index) {
		return buf.get(index);
	}

	public void get(byte[] data) {
		buf.get(data);
	}
	
	public void get(byte[] data ,int start,int end) {
		buf.get(data,start,end);
	}
	
	public byte[] getByteArray(int len) {
		byte[] bytes = new byte[len];
		buf.get(bytes);
		return bytes;
	}

	public void put(byte[] data, int start , int end) {
		autoExpand(end);
		buf.put(data,start,end);
		if (position() > save){
			save = position();
		}
	}

	public void put(byte[] data) {
		autoExpand(data.length);
		buf.put(data);
		if (position() > save){
			save = position();
		}
	}
	
	public char getChar() {
		return buf.getChar();
	}

	public void putChar(char ch) {
		autoExpand(2);
		buf.putChar(ch);
		if (position() > save){
			save = position();
		}
	}

	public char getChar(int index) {
		return buf.getChar(index);
	}

	public void putChar(int index, char ch) {
		autoExpand(index,2);
		buf.putChar(index,ch);
		if (position() > save){
			save = position();
		}
	}

	public short getShort() {
		return buf.getShort();
	}

	public void putShort(short num) {
		autoExpand(2);
		buf.putShort(num);
		if (position() > save){
			save = position();
		}
	}

	public short getShort(int index) {
		return buf.getShort(index);
	}

	public void putShort(int index, short num) {
		autoExpand(index,2);
		buf.putShort(index,num);
		if (position() > save){
			save = position();
		}
	}

	public int getInt() {
		return buf.getInt();
	}

	public void putInt(int num) {
		autoExpand(4);
		buf.putInt(num);
		if (position() > save){
			save = position();
		}
	}

	public int getInt(int num) {
		return buf.getInt();
	}
	
	public void putInt(int index, int num) {
		buf.putInt(index, num);
		if (position() > save){
			save = position();
		}
	}
	
	public long getLong() {
		return buf.getLong();
	}

	public void putLong(long num) {
		autoExpand(8);
		buf.putLong(num);
		if (position() > save){
			save = position();
		}
	}

	public long getLong(int index) {
		return buf.getLong(index);
	}

	public void putLong(int index, long num) {
		autoExpand(index,8);
		buf.putLong(index,num);
		if (position() > save){
			save = position();
		}
	}
	
	public float getFloat() {
		return buf.getFloat();
	}

	public void putFloat(float num) {
		autoExpand(4);
		buf.putFloat(num);
		if (position() > save){
			save = position();
		}
	}

	public float getFloat(int index) {
		return buf.getFloat(index);
	}

	public void putFloat(int index, float num) {
		autoExpand(index,4);
		buf.putFloat(index, num);
		if (position() > save){
			save = position();
		}
	}
	
	public void putPrefixedString(String str) {
		byte[] bytes = _stringToByteArray(str,DEFAULT_CHARSET);
		putInt(bytes.length);
		put(bytes);
	}

	public String getPrefixedString() {
		return _byteArrayToString(getByteArray(getInt()),DEFAULT_CHARSET);
	}
	
	private String _byteArrayToString(byte[] data,Charset set) {
		try {
			return new String(data, set.charsetName());
		} catch (UnsupportedEncodingException exp) {
			exp.printStackTrace();
		}
		return "";
	}

	private byte[] _stringToByteArray(String str,Charset set) {
		try {
			return str.getBytes(set.charsetName());
		} catch (UnsupportedEncodingException exp) {
			exp.printStackTrace();
		}
		return new byte[0];
	}

	@Override
	public int read() throws IOException {
		return get();
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		int max = save - position();
		if (max <= 0){
			return -1;
		}else if (b.length > max){
			byte[] temp = new byte[max];
			get(temp);
			System.arraycopy(temp,0, b,0,temp.length);
		}else{
			get(b);
		}
		return 0;
	}

	public enum Charset{
		  UTF_8("UTF-8"),
		  UTF_16("UTF-16"),
		  UTF_16BE("UTF-16BE"),
		  UTF_16LE("UTF-16LE"), 
		  UTF_32("UTF-32"), 
		  UTF_32BE("UTF-32BE"), 
		  UTF_32LE("UTF-32LE"),
		  ISO_8859_1("ISO-8859-1"),
		  US_ASCII("US-ASCII"), 
		  GBK("GBK"), 
		  GB2312("GB2312");
		  private String charsetName;

		  private Charset(String paramString){
		    charsetName = paramString;
		  }

		  public String charsetName(){
		    return charsetName;
		  }
	}
}
 
 
