package com.xzk.netty.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
/**
  *  传输格式:
 * TBinaryProtocol     二进制格式
 * TCompacProticol     压缩格式
 * TJSONProtocol       JSON格式
 * TSimpleJSONProtocol 提供JSON只写协议，生成的文件很容易通过脚本语言解析
 * TDebugProtocol      使用易懂的可读的文本格式，以便于debug
 * 
  *  传输方式:
 * TSocket 阻塞式socket
 * TFramedTransport 以frame为单位进行传输，非阻塞式服务中使用。
 * TFileTransport 以文件形式进行传输
 * TMemoryTransport 将内存用于I/O,java实现时内部实际使用了简单的ByteArraryOutputStream
 * TZlibTransport 使用 zlib进行压缩，与其他传输方式联合使用，当前无java实现。
 * 
 * 
  *  服务模型:
 * TSimpleServer      简单的单线程服务模型，常用于测试
 * TThreadPoolServer  多线程服务模型，使用标准的阻塞式IO
 * TNonblockingServer 多线程服务模型，使用非阻塞式IO(需要使用TFramedTransport数据传输方式)
 * THsHaServer        THsHa引入了线程池去处理，其他模型把读写任务放到线程池去处理
 *
 */
public class ThriftServer {

	public static void main(String[] args) throws Exception{
		
		TNonblockingServerSocket socket = new TNonblockingServerSocket(8112);
		THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
		PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<PersonServiceImpl>(new PersonServiceImpl());
		//协议层（压缩协议）
		arg.protocolFactory(new TCompactProtocol.Factory());
		//传输层
		arg.transportFactory(new TFramedTransport.Factory());
		arg.processorFactory(new TProcessorFactory(processor));
		
		TServer server = new THsHaServer(arg);
		System.out.println("ThriftServer start!");
		server.serve();
		
	}
	
}
