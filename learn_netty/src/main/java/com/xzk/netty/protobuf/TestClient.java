package com.xzk.netty.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TestClient {
	
	public static void main(String[] args) throws Exception{
		
		EventLoopGroup loopGroup  = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		try {
			bootstrap.group(loopGroup).channel(NioSocketChannel.class)
			         .handler(new TestClientInitializer());
			ChannelFuture channelFuture = bootstrap.connect("localhost", 8112).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			loopGroup.shutdownGracefully();
		}
	}

}
