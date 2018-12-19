package com.xzk.netty.buffer;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 
 *  ASCII        7 bit来表示一个字符，共计可以表示128种字符，老美的。。
 *  
 *  ISO-8859-1   8 bit(一个字节)表示一个字符，共计可以表示256种字符，欧洲的。。
 *  
 *  GB2312       16 bit(二个字节)表示一个中文，未考虑生僻字，中国的。。
 *  
 *  GBK          GB2312的扩展，加入生僻字，中国的。。
 *  
 *  GB18030      大于GBK所包含的中文，中国的。。
 *  
 *  Big5         中国台湾的。。
 *  
 *  Unicode      16 bit(二个字节)表示一个字符，存储空间受到挑战（之前一个字节就能表示，现在得填充到2个字节了），不适合存储，空间浪费，国际的。。
 *  
 *  Unicode是一种编码方式，UTF则是一种存储方式；UTF-8是Unicode的实现方式之一
 *  
 *  UTF-8        变长字节表示形式，是一种针对Unicode的可变长度字符编码，3个字节表示一个中文
 *  
 */
public class CharsetTest {
	
	public static void main(String[] args) {
		
		String inputFile = "CharsetTestInput.txt";
		String outputFile = "CharsetTestoutput.txt";
		
		RandomAccessFile inputRandomAccessFile = null;
		RandomAccessFile outputRandomAccessFile = null;
		
        try {
    		inputRandomAccessFile = new RandomAccessFile(inputFile, "r");
    		outputRandomAccessFile = new RandomAccessFile(outputFile, "rw");
    		
    		long inputFileLength = new File(inputFile).length();
    		
    		FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
    		FileChannel outputFileChannel = outputRandomAccessFile.getChannel();
    		
    		MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputFileLength);
    		
    		Charset charset = Charset.forName("GBK");
    		//解码
    		CharsetDecoder decoder = charset.newDecoder();
    		//编码
    		CharsetEncoder encoder = charset.newEncoder();
    		
    		CharBuffer charBuffer = decoder.decode(inputData);
    		
    		ByteBuffer byteBuffer = encoder.encode(charBuffer);
    		
    		outputFileChannel.write(byteBuffer);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(inputRandomAccessFile != null) {
					inputRandomAccessFile.close();
				}
				if(outputRandomAccessFile != null) {
					outputRandomAccessFile.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
