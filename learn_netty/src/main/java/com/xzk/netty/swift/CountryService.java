package com.xzk.netty.swift;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

@ThriftService
public interface CountryService {

	@ThriftMethod
	public Country getCountryByName(String name);
	@ThriftMethod
	public void save(Country country);
	 
}
