package com.xzk.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyChatServer {
	
	public static void main(String[] args)  throws Exception{
		
		EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
		EventLoopGroup workLoopGroup = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		try {
			serverBootstrap.group(bossLoopGroup, workLoopGroup).channel(NioServerSocketChannel.class)
			               .childHandler(new MyChatServerInitializer());
			ChannelFuture channelFuture = serverBootstrap.bind(8112).sync();
			channelFuture.channel().closeFuture().sync();
			
		} finally {
			bossLoopGroup.shutdownGracefully();
			workLoopGroup.shutdownGracefully();
		}
		
		
		
	}

}
