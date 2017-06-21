package com.chinasoft.cms.customer.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasoft.cms.customer.domain.Customer;
import com.chinasoft.cms.customer.domain.PageBean;
import com.chinasoft.cms.customer.service.CustomerException;
import com.chinasoft.cms.customer.service.CustomerService;

@WebServlet(urlPatterns="/CustomerServlet")
public class CustomerServlet extends HttpServlet{
	CustomerService csService = new CustomerService();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		if(method.equals("add")){
			//生产UUID  长度：36
			String cid = UUID.randomUUID().toString();
			String cname = request.getParameter("cname");
			String gender = request.getParameter("gender");
			String birthday = request.getParameter("birthday");
			String cellphone =  request.getParameter("cellphone");
			String email = request.getParameter("email");
			String description =  request.getParameter("description");
			
			Customer form = new Customer();
			form.setBirthday(birthday);
			form.setCellphone(cellphone);
			form.setCid(cid);
			form.setCname(cname);
			form.setDescription(description);
			form.setEmail(email);
			form.setGender(gender);
			try {
				csService.add(form);
				request.setAttribute("msg", "恭喜您，注册成功");
				request.getRequestDispatcher("/msg.jsp").forward(request, response);
			} catch (CustomerException e) {
				request.setAttribute("msg", "对不起，注册失败");
				request.getRequestDispatcher("/msg.jsp").forward(request, response);
			}
			
		}else if(method.equals("edit")){
			//取出jsp传过来的所有参数
			String cid = request.getParameter("cid");
			String cname = request.getParameter("cname");
			String gender = request.getParameter("gender");
			String birthday = request.getParameter("birthday");
			String cellphone =  request.getParameter("cellphone");
			String email = request.getParameter("email");
			String description =  request.getParameter("description");
			//新建一个Customer对象，并把获取到的参数作为属性全部设置给该对象
			Customer form = new Customer();
			form.setBirthday(birthday);
			form.setCellphone(cellphone);
			form.setCid(cid);
			form.setCname(cname);
			form.setDescription(description);
			form.setEmail(email);
			form.setGender(gender);
			//调用业务层的update方法，更新该对象对应在数据库中的值
			csService.update(form);
			//向request存入一个key为msg 值为 更新成功的的  键值对值
			request.setAttribute("msg", "更新成功！");
			//转发到msg.jsp
			request.getRequestDispatcher("/msg.jsp").forward(request, response);
		}
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		if(method.equals("findAll")){
//			List<Customer> cstmList = csService.loalAll();
//			request.setAttribute("cstmList", cstmList);
//			request.getRequestDispatcher("/list.jsp").forward(request, response);
			/**
			 * 1.定义pageSize
			 * 2.获取pageCode
			 * 3.把pageSize和pageCode作为参数，调用service层的findAll方法，得到PageBean对象
			 * 4.把得到的PageBean对象通过转发发送给list.jsp
			 */
			int pageSize = 10;//定义每页的记录数
			int pageCode = getPageCode(request);//获取当前页码
			PageBean<Customer> pb = csService.findAll(pageSize,pageCode);//获取PageBean对象
			pb.setUrl(getUrl(request));
			request.setAttribute("pb", pb);//把得到的PageBean对象，存到request里面
			//转发到list.jsp（也就是把pb对象传给list.jsp）
			request.getRequestDispatcher("/list.jsp").forward(request, response);
		}else if(method.equals("preEdit")){
			//获取当前需要被编辑的对象
			String cid = request.getParameter("cid");
			Customer cs = csService.load(cid);
			//把该对象传给edit.jsp
			request.setAttribute("cstm", cs);
			request.getRequestDispatcher("/edit.jsp").forward(request, response);
		}else if(method.equals("delete")){
			//获取jsp传递过来的cid值
			String cid = request.getParameter("cid");
			//调用业务层的delete方法
			csService.delete(cid);
			//删除之后，查询所有数据。并把查询到的数据封装成的集合转发给list.jsp
			request.setAttribute("cstmList", csService.loalAll());
			request.getRequestDispatcher("/list.jsp").forward(request, response);
		}else if(method.equals("query")){
			String cname = request.getParameter("cname");
			String gender = request.getParameter("gender");
			String cellphone = request.getParameter("cellphone");
			String email = request.getParameter("email");
			
			Customer form = new Customer();
			form.setCname(cname);
			form.setGender(gender);
			form.setCellphone(cellphone);
			form.setEmail(email);
			/**
			 * 和findAll逻辑一样。
			 * 多传了一个参数:form   -->因为form封装了高级查询的条件
			 */
			int pageCode = getPageCode(request);
			int pageSize = 10;
			PageBean<Customer> pb = csService.query(form,pageCode,pageSize);
			request.setAttribute("pb", pb);
			String url = getUrl(request);
			pb.setUrl(url);
			request.getRequestDispatcher("/list.jsp").forward(request, response);
			
		}
	}
	/**
	 * 获取当前页码
	 * @param request
	 * @return
	 */
	private int getPageCode(HttpServletRequest request) {
		String pageCode = request.getParameter("pageCode");
		if(pageCode==null||pageCode.trim().isEmpty()){
			return 1;
		}
		return Integer.parseInt(pageCode);
	}
	
	/**
	 * 获取请求url
	 * @param request
	 * @return
	 */
	private String getUrl(HttpServletRequest request){
		String url = null;
		try {
			url =  URLDecoder.decode(request.getQueryString(),"UTF-8");//获取请求参数
			int index = url.lastIndexOf("&");
			url = url.substring(0,index);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
}
