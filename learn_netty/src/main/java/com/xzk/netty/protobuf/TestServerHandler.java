package com.xzk.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestServerHandler extends SimpleChannelInboundHandler<DataInfo.MyMessage>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DataInfo.MyMessage msg) throws Exception {
		DataInfo.MyMessage.DataType dataType =  msg.getDataType(); 
		
		if(dataType == DataInfo.MyMessage.DataType.StudentType) {
			System.out.println(msg.getStudent().getName());
			System.out.println(msg.getStudent().getAge());
			System.out.println(msg.getStudent().getAddress());
		}else if(dataType == DataInfo.MyMessage.DataType.DogType) {
			System.out.println(msg.getDog().getName());
			System.out.println(msg.getDog().getAge());
		}else if(dataType == DataInfo.MyMessage.DataType.CatType) {
			System.out.println(msg.getCat().getName());
			System.out.println(msg.getCat().getAge());
		}
	}

}
