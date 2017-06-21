package com.chinasoft.cms.test;

import java.util.UUID;

import org.junit.Test;

import com.chinasoft.cms.customer.dao.CustomerDao;
import com.chinasoft.cms.customer.domain.Customer;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddTest {
	@Test
	public void test(){
		CustomerDao csDao = new CustomerDao();
		//ComboPooledDataSource ds = new ComboPooledDataSource();
		for(int i=0;i<300;i++){
			Customer cs = new Customer();
			cs.setCid(UUID.randomUUID().toString());
			cs.setBirthday("2017-02-31");
			cs.setCellphone("150"+i);
			cs.setCname("周杰伦"+i);
			cs.setDescription("这是一条很严肃的描述"+i);
			cs.setEmail(i+"@qq.com");
			cs.setGender(i%2==0?"男":"女");
			csDao.addCustomer(cs);
		}
	}
}
