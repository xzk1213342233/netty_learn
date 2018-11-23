package com.xzk.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

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
			
			serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
			                childHandler(new TestServerInitalizer());
			
			
			ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
	
}
