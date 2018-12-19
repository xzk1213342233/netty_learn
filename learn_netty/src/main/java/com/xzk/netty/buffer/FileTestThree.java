package com.xzk.netty.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileTestThree {
	
	public static void main(String[] args) {
		
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		
		try {
			fileInputStream =  new FileInputStream("fileInputStream.txt");
			FileChannel fileInputChannel = fileInputStream.getChannel();

			fileOutputStream = new FileOutputStream("fileOutputStream.txt");
			FileChannel fileOutputChannel = fileOutputStream.getChannel();
			
			while(true) {
 			    byteBuffer.clear();	
				
				int read = fileInputChannel.read(byteBuffer);
				
				System.out.println("read="+read);
				
				if(-1 == read) {
					break;
				}
				
				byteBuffer.flip();
				
				fileOutputChannel.write(byteBuffer);
			 }
		} catch (Exception e) {
			e.fillInStackTrace();
		}finally {
			if(fileInputStream!=null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
