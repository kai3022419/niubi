package com.chinasoft.cms.customer.service;

import java.util.List;

import com.chinasoft.cms.customer.dao.CustomerDao;
import com.chinasoft.cms.customer.domain.Customer;
import com.chinasoft.cms.customer.domain.PageBean;

public class CustomerService {
	CustomerDao csDao = new CustomerDao();
	public void add(Customer form) throws CustomerException {
		String cname = form.getCname();
		Customer cs = csDao.findCustomerByCname(cname);
		if(cs!=null){
			throw new CustomerException("用户名已存在");
		}
		csDao.addCustomer(form);
	}
	public List<Customer> loalAll() {
		// TODO Auto-generated method stub
		return csDao.loadAll();
	}
	//通过id查找客户
	public Customer load(String cid) {
		// TODO Auto-generated method stub
		return csDao.findCustomerByCid(cid);
	}
	public void update(Customer cs) {
		csDao.update(cs);
		
	}
	public void delete(String cid) {
		csDao.delete(cid);
	}
//	public List<Customer> query(Customer form) {
//		return csDao.query(form);
//		
//	}
	/**
	 * 带分页效果的查询所有数据
	 * @param pageSize
	 * @param pageCode
	 * @return
	 */
	public PageBean<Customer> findAll(int pageSize, int pageCode) {
		return csDao.findAll(pageSize,pageCode);
	}
	
	/**
	 * 高级查询的分页查询
	 * @param form
	 * @param pageCode
	 * @param pageSize
	 * @return
	 */
	public PageBean<Customer> query(Customer form, int pageCode, int pageSize) {
		return csDao.query(form,pageCode,pageSize);
	}

}
