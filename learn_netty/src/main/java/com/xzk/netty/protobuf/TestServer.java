package com.xzk.netty.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestServer {

	public static void main(String[] args) throws InterruptedException {
		/**
		    *    线程组
		  * bossGroup     接收链接
		  * workerGroup   处理链接
		  *    
		 */
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			//启动服务端
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			
			serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
			               .handler(new LoggingHandler(LogLevel.INFO)) 
			               .childHandler(new TestServerInitalizer());
			ChannelFuture channelFuture = serverBootstrap.bind(8112).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
	
}
