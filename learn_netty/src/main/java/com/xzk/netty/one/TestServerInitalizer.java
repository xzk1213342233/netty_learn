package com.xzk.netty.one;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitalizer extends  ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("httpServerCodec",new HttpServerCodec());//编解码
		pipeline.addLast("testHttpServerHandler",new TestHttpServerHandler());
	}
}
