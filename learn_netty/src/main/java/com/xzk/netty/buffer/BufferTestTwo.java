package com.xzk.netty.buffer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class BufferTestTwo {
	
	public static void main(String[] args) throws Exception{
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		InetSocketAddress inetSocketAddress = new InetSocketAddress(8112);
		serverSocketChannel.socket().bind(inetSocketAddress);
		
		int messageLength = 2 + 3 + 4;
		ByteBuffer[] byteBuffers = new ByteBuffer[3];
		
		byteBuffers[0] = ByteBuffer.allocate(2);
		byteBuffers[1] = ByteBuffer.allocate(3);
		byteBuffers[2] = ByteBuffer.allocate(4);
		
		SocketChannel socketChannel = serverSocketChannel.accept();
		
		while(true) {
			int bytesRead = 0;
			
			while(bytesRead < messageLength) {
				long r = socketChannel.read(byteBuffers);
				bytesRead += r;
				System.out.println("bytesRead = "+bytesRead);
				Arrays.asList(byteBuffers).stream().
				map(buffer -> "position="+buffer.position()+",limit="+buffer.limit()).
				forEach(System.out::println);
			}
			Arrays.asList(byteBuffers).forEach(buffer -> {
				buffer.flip();
			});
			
			long bytesWritten = 0;
			while(bytesWritten < messageLength) {
				long r = socketChannel.write(byteBuffers);
				bytesWritten += r;
			}
			Arrays.asList(byteBuffers).forEach(buffer -> {
				buffer.clear();
			});
			
			System.out.println("bytesRead = "+bytesRead+",bytesWritten = "+bytesWritten+",messageLength = "+messageLength);
		}
	}
}
