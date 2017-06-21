package com.chinasoft.cms.test;

import java.util.UUID;

import javax.persistence.Temporal;

import org.junit.Test;

public class UUIDTest {
	@Test
	public void test(){
		System.out.println(UUID.randomUUID().toString());
	}
}
