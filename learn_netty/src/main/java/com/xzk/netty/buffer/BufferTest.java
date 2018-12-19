package com.xzk.netty.buffer;

import java.nio.IntBuffer;
import java.util.Random;

public class BufferTest {
	
	public static void main(String[] args) {
		IntBuffer intBuffer = IntBuffer.allocate(10);
		for (int i = 0; i < intBuffer.capacity(); i++) {
			intBuffer.put(new Random().nextInt(20));
		}
		
		intBuffer.flip();
		
		while(intBuffer.hasRemaining()) {
			System.out.println(intBuffer.get());
		}
		
	}

}
