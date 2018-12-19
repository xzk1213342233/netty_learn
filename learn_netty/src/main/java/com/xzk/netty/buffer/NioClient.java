package com.xzk.netty.buffer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioClient {
	
	private static final ExecutorService es = Executors.newSingleThreadExecutor();
	
	public static void main(String[] args) {
		try {
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			
			Selector selector = Selector.open();
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			socketChannel.connect(new InetSocketAddress("localhost", 8112));
			
			while(true) {
				selector.select();
				Set<SelectionKey> keySet = selector.selectedKeys();
				keySet.forEach(selectionKey -> {
					try {
						if(selectionKey.isConnectable()) {
							SocketChannel client = (SocketChannel)selectionKey.channel();
							//连接状态 进行中。。。
							if(client.isConnectionPending()) {
									//连接
									client.finishConnect();
									ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
									writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
									writeBuffer.flip();
									client.write(writeBuffer);
									
									//键盘数据输入
									es.submit(() -> {
											while (true) {
												try {
													writeBuffer.clear();
													InputStreamReader input = new InputStreamReader(System.in);
													BufferedReader br = new BufferedReader(input);
													String sendMessage = br.readLine();
													writeBuffer.put(sendMessage.getBytes());
													writeBuffer.flip();
													client.write(writeBuffer);
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
									});
							}
							client.register(selector, SelectionKey.OP_READ);
						}else if(selectionKey.isReadable()) {
							SocketChannel client = (SocketChannel)selectionKey.channel();
							ByteBuffer readBuffer = ByteBuffer.allocate(1024);
							int count = client.read(readBuffer);
							if(count > 0) {
								String receivedMessage = new String(readBuffer.array(),0,count);
								System.out.println(receivedMessage);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				keySet.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
