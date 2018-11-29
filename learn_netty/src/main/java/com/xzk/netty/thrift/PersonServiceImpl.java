package com.xzk.netty.thrift;

import org.apache.thrift.TException;

public class PersonServiceImpl implements PersonService.Iface{

	@Override
	public Person getPersonByUsername(String username) throws DataException, TException {
		System.out.println("getPersonByUsername 方法被调用");
		Person person  = new Person();
		person.setUsername(username);
		person.setAge(18);
		person.setMarried(false);
		return person;
	}

	@Override
	public void savePerson(Person person) throws DataException, TException {
		System.out.println("savePerson 方法被调用");
		System.out.println(person.getUsername());
		System.out.println(person.getAge());
		System.out.println(person.isMarried());
	}

}
