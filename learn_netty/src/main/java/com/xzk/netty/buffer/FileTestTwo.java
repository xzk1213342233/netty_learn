package com.xzk.netty.buffer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileTestTwo {
	
	public static void main(String[] args){
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("fileOutputStream.txt");
			FileChannel fileChannel = fileOutputStream.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			byte[] byteParams = "hello world".getBytes(); 
	        for (byte b : byteParams) {
	        	byteBuffer.put(b);
			}
	        
	        byteBuffer.flip();
	        
	        fileChannel.write(byteBuffer);
		} catch (Exception e) {
			e.fillInStackTrace();
		}finally {
			if(fileOutputStream!=null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
