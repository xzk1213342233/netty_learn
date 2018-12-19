package com.xzk.netty.swift;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct
public class Country {

	private String name;

	
	@ThriftField(1)
	public String getName() {
		return name;
	}

	@ThriftField
	public void setName(String name) {
		this.name = name;
	}
	
	
}
