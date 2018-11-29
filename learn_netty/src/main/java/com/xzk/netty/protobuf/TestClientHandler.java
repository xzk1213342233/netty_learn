package com.xzk.netty.protobuf;

import java.util.Random;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestClientHandler extends SimpleChannelInboundHandler<DataInfo.MyMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DataInfo.MyMessage msg) throws Exception {
		
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		int randomInt  = new Random().nextInt(3);
		System.out.println("随机数="+randomInt);
		
		DataInfo.MyMessage myMessage = null;
		
		if(randomInt == 0) {
			DataInfo.Student student = DataInfo.Student.newBuilder().setName("张三").setAge(18).setAddress("北京").build();
			DataInfo.MyMessage.DataType dataType =  DataInfo.MyMessage.DataType.StudentType;
			myMessage = DataInfo.MyMessage.newBuilder().setDataType(dataType).setStudent(student).build();
		}else if(randomInt == 1) {
			DataInfo.Dog dog = DataInfo.Dog.newBuilder().setName("哈士奇").setAge(2).build();
			DataInfo.MyMessage.DataType dataType =  DataInfo.MyMessage.DataType.DogType;
			myMessage = DataInfo.MyMessage.newBuilder().setDataType(dataType).setDog(dog).build();
		}else if(randomInt == 2) {
			DataInfo.Cat cat = DataInfo.Cat.newBuilder().setName("加菲猫").setAge(3).build();
			DataInfo.MyMessage.DataType dataType =  DataInfo.MyMessage.DataType.CatType;
			myMessage = DataInfo.MyMessage.newBuilder().setDataType(dataType).setCat(cat).build();
		}
		
		ctx.channel().writeAndFlush(myMessage);
	}

}
