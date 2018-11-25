package com.xzk.netty.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyChatClient {
	
	public static void main(String[] args) throws Exception{
		
		EventLoopGroup loopGroup  = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		try {
			bootstrap.group(loopGroup).channel(NioSocketChannel.class)
			         .handler(new MyChatClientInitializer());
			Channel channel = bootstrap.connect("localhost", 8112).sync().channel();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			for(;;) {
				channel.writeAndFlush(reader.readLine()+"\r\n");
			}
		} finally {
			loopGroup.shutdownGracefully();
		}
	}

}
