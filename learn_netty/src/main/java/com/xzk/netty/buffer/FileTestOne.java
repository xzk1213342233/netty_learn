package com.xzk.netty.buffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileTestOne {
	
	public static void main(String[] args) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream =  new FileInputStream("fileInputStream.txt");
			FileChannel fileChannel = fileInputStream.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			fileChannel.read(byteBuffer);
			
			byteBuffer.flip();
			
			while(byteBuffer.remaining()>0) {
			  System.out.println((char)byteBuffer.get());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(fileInputStream!=null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
