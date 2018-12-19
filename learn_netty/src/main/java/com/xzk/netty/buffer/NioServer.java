package com.xzk.netty.buffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NioServer {
	
	private final static  ConcurrentHashMap<String,SocketChannel> clientMap = new ConcurrentHashMap<String,SocketChannel>();
	
	public static void main(String[] args) throws IOException{
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocket.bind(new InetSocketAddress(8112));
		System.out.println("已绑定端口号为 :" + 8112);
		
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(true) {
			try {
				int numbers  = selector.select();
				System.out.println("numbers = "+ numbers);
				
				Set<SelectionKey>  selectionKeys =  selector.selectedKeys();

				selectionKeys.forEach(selectionKey -> {
					final SocketChannel client;
					try {
						if(selectionKey.isAcceptable()) {
							ServerSocketChannel server =  (ServerSocketChannel)selectionKey.channel();
							client = server.accept();
							client.configureBlocking(false);
							client.register(selector, SelectionKey.OP_READ);
							clientMap.put(UUID.randomUUID().toString(), client);
						}else if(selectionKey.isReadable()) {
							client =  (SocketChannel)selectionKey.channel();
							ByteBuffer readBuffer = ByteBuffer.allocate(1024);
							int count = client.read(readBuffer);
							if(count > 0) {
								readBuffer.flip();
								Charset charset = Charset.forName("UTF-8");
								String receivedMessage = String.valueOf(charset.decode(readBuffer).array());
								System.out.println("receivedMessage = " + receivedMessage);
								
								String senderKey = null;
								for(Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
									if(client == entry.getValue()) {
										senderKey = entry.getKey();
										break;
									}
								}
								
								for(Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
									SocketChannel value = entry.getValue();
									ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
									writeBuffer.put((senderKey + ":" + receivedMessage).getBytes());
									writeBuffer.flip();
									value.write(writeBuffer);
								}
							}
						}
						System.out.println("size = " + selectionKeys.size());
					} catch (Exception e) {
						e.printStackTrace();
						try {
							selectionKey.channel().close();
							for(Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
								if(selectionKey.channel() == entry.getValue()) {
									clientMap.remove(entry.getKey());
								}
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				selectionKeys.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
