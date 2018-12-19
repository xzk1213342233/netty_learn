package com.xzk.netty.buffer;


import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class SelectorTestOne {
	
	public static void main(String[] args)  throws Exception{
		int[] ports = new int[5];
		
		ports[0] = 5000;
		ports[1] = 5001;
		ports[2] = 5002;
		ports[3] = 5003;
		ports[4] = 5004;
		
		Selector selector = Selector.open();
		try {
			for (int i = 0; i < ports.length; i++) {
				//开启一个ServerSocketChannel
				ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
				//是否阻塞(fasle 非阻塞，true 阻塞)，默认true
				serverSocketChannel.configureBlocking(false);
				//获取和这个channel 相关联的serverSocket
				ServerSocket serverSocket = serverSocketChannel.socket();
				//端口号
				InetSocketAddress inetSocketAddress = new InetSocketAddress(ports[i]);
				//绑定端口号
				serverSocket.bind(inetSocketAddress);
				
				//开始注册
				serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
				
				System.out.println("监听端口号 : " + ports[i]);
			}
			
			while(true) {
				int numbers = selector.select();
				System.out.println("numbers = " + numbers);
				
				//获取Set<SelectionKey>
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				System.out.println("selectionKeys = " + selectionKeys);
				
				Iterator<SelectionKey> iterator =  selectionKeys.iterator();
				while(iterator.hasNext()) {
					SelectionKey  selectionKey =  iterator.next();
					if(selectionKey.isAcceptable()) {
						ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
						SocketChannel socketChannel = serverSocketChannel.accept();
						socketChannel.configureBlocking(false);
						
						//读事件
						socketChannel.register(selector, SelectionKey.OP_READ);
						
						iterator.remove();
						System.out.println("获得客户端连接 :" + socketChannel);
					}else if(selectionKey.isReadable()) {
						SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
						ByteBuffer byteBuffer = ByteBuffer.allocate(512);
						int byteRead = 0;
						while(true) {
							byteBuffer.clear();
							int read  = socketChannel.read(byteBuffer);
							if(read <= 0) {
								break;
							}
							byteBuffer.flip();
							socketChannel.write(byteBuffer);
							byteRead += read;
						}
						System.out.println("读取: "+ byteRead +",来自于: " + socketChannel);
						
						iterator.remove();
					}
				}
			}		
		} finally {
			selector.close();
		}
	}
}
