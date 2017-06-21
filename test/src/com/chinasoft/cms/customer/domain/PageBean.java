package com.chinasoft.cms.customer.domain;

import java.util.List;

/**
 * 处理分页的领域对象
 * @author philip
 *
 */
public class PageBean<T> {
	private int pageSize;//每页的记录数量
	private int pageCode;//当前页码
	private int totalRecord;//总记录数
	private List<T> beanList;//当前页面的具体数据
	private String url;//请求参数
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	//获取总页数
	public int getTotalPage(){
		int totalPage = totalRecord/pageSize;
		return totalRecord%pageSize==0?totalPage:totalPage+1;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCode() {
		return pageCode;
	}
	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	
	
}
