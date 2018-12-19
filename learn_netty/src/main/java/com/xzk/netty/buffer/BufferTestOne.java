package com.xzk.netty.buffer;

import java.nio.ByteBuffer;

public class BufferTestOne {
	
	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		for (int i = 0; i < buffer.capacity(); i++) {
			buffer.put((byte) i);
		}
		
		buffer.position(4);
		buffer.limit(7);
		
		ByteBuffer sliceBuffer = buffer.slice();
		for (int i = 0; i < sliceBuffer.capacity(); i++) {
			sliceBuffer.put((byte) (sliceBuffer.get(i)*2));
		}
		
		buffer.position(0);
		buffer.limit(buffer.capacity());
		
		while (buffer.hasRemaining()) {
			System.out.println(buffer.get());
		}
	}

}
