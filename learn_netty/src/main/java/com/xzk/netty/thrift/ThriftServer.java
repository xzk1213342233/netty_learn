package com.xzk.netty.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

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
