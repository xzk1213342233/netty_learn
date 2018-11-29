package com.xzk.netty.heart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MyHeartServer {
	
	public static void main(String[] args) throws  Exception{
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
		
		try {
			serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
			               .handler(new LoggingHandler(LogLevel.INFO))
			               .childHandler(new MyHeartSeverInitializer());
			ChannelFuture channelFuture = serverBootstrap.bind(8112).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
		
	}

}
