package com.chinasoft.cms.customer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.chinasoft.cms.customer.domain.Customer;
import com.chinasoft.cms.customer.domain.PageBean;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class CustomerDao {
	QueryRunner qr = new QueryRunner(new ComboPooledDataSource());
	public Customer findCustomerByCname(String cname) {
		//被返回的Customer对象
		Customer cs = null;
		List<Customer> list = new ArrayList<Customer>();
		try {
			
			//注册驱动
			Class.forName("com.mysql.jdbc.Driver");
			//获取连接
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zr_0330",
					"root", "123456");
			String sql = "SELECT * FROM customer WHERE cname=?";
			//预编译sql语句
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cname);
			//获取结果集
			ResultSet rs = pstmt.executeQuery();
			//解析结果集
			while(rs.next()){
				cs = new Customer();
				cs.setCid(rs.getString("cid"));
				cs.setBirthday(rs.getString("birthday"));
				cs.setCellphone(rs.getString("cellphone"));
				cs.setCname(rs.getString("cname"));
				cs.setDescription(rs.getString("description"));
				cs.setEmail(rs.getString("email"));
				cs.setGender(rs.getString("gender"));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cs;
	}

	public void addCustomer(Customer form) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			//获取连接
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zr_0330",
					"root", "123456");
			String sql = "INSERT customer VALUES(?,?,?,?,?,?,?);";
			//预编译sql语句
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, form.getCid());
			pstmt.setString(2, form.getCname());
			pstmt.setString(3, form.getGender());
			pstmt.setString(4, form.getBirthday());
			pstmt.setString(5, form.getCellphone());
			pstmt.setString(6, form.getEmail());
			pstmt.setString(7, form.getDescription());
			
			pstmt.executeUpdate();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	/**
	 * 查询所有数据
	 * @return
	 */
	public List<Customer> loadAll() {
		String sql = "SELECT * FROM customer";
		
		try {
			return qr.query(sql, new BeanListHandler<Customer>(Customer.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//通过id查找客户
	public Customer findCustomerByCid(String cid) {
		String sql = "SELECT * FROM customer WHERE cid=?";
		Object[] params = {cid};
		try {
			return qr.query(sql, params, new BeanHandler<Customer>(Customer.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 更新用户信息
	 * @param cs
	 */
	public void update(Customer cs) {
		String sql = "UPDATE customer SET cname=?,gender=?,birthday=?,"
				+ "cellphone=?,email=?,description=? WHERE cid=?";
		Object[] params = {cs.getCname(),cs.getGender(),cs.getBirthday(),
				cs.getCellphone(),cs.getEmail(),cs.getDescription(),cs.getCid()};
		try {
			qr.update(sql,params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	//根据id删除数据
	public void delete(String cid) {
		String sql = "DELETE FROM customer WHERE cid=?";
		Object[] params = {cid};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
//	/**
//	 * 多条件查询
//	 * @param form
//	 * @return
//	 */
//	public List<Customer> query(Customer form) {
//		StringBuilder sql = new StringBuilder("SELECT * FROM customer WHERE 1=1");
//		
//		
//		try {
//			System.out.println(sql.toString());
//			return qr.query(sql.toString(),params.toArray(),  new BeanListHandler<Customer>(Customer.class));
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}
	
	/**
	 * 带分页效果的查询所有
	 * 1.创建PageBean对象，先设置pageSize和pageCode属性
	 * 2.通过执行sql获取总记录数，作为属性赋值给PageBean对象
	 * 3.通过执行sql获取beanList,作为属性赋值给PageBean对象
	 * 4.返回PageBean对象
	 * @param pageSize
	 * @param pageCode
	 * @return
	 */
	public PageBean<Customer> findAll(int pageSize, int pageCode) {
		/*
		 * 1.创建PageBean对象，先设置pageSize和pageCode属性
		 */
		PageBean<Customer> pb = new PageBean<Customer>();
		pb.setPageSize(pageSize);
		pb.setPageCode(pageCode);
		/*
		 * 2.通过执行sql获取总记录数，作为属性赋值给PageBean对象
		 */
		try {
			String sql = "SELECT COUNT(*) FROM customer";
			Number totalRecord = (Number)qr.query(sql, new ScalarHandler());
			/*
			 * 3.通过执行sql获取beanList,作为属性赋值给PageBean对象
			 */
			sql = "SELECT * FROM customer LIMIT ?,?";
			Object[] params = {(pageCode-1)*pageSize,pageSize};
			List<Customer> beanList = 
					qr.query(sql, params, new BeanListHandler<Customer>(Customer.class));
			pb.setBeanList(beanList);
			pb.setTotalRecord(totalRecord.intValue());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return pb;
	}
	
	/**
	 * 逻辑和findAll 一样。
	 * 多了 查询条件
	 * @param form
	 * @param pageCode
	 * @param pageSize
	 * @return
	 */
	public PageBean<Customer> query(Customer form, int pageCode, int pageSize) {
		PageBean<Customer> pb = new PageBean<Customer>();
		try {
			pb.setPageCode(pageCode);
			pb.setPageSize(pageSize);
			StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM customer WHERE 1=1");
			List<Object> countParams = appendSql(countSql, form);
			Number totalRecord = (Number)qr.query(countSql.toString(),countParams.toArray(),new ScalarHandler());
			pb.setTotalRecord(totalRecord.intValue());
			StringBuilder beanSql = new StringBuilder("SELECT * FROM customer WHERE 1=1");
			List<Object> beanParams = appendSql(beanSql, form);
			beanSql.append(" LIMIT ?,?");
			beanParams.add((pageCode-1)*pageSize);
			beanParams.add(pageSize);
			List<Customer> beanList = qr.query(beanSql.toString(), beanParams.toArray(),
					new BeanListHandler<Customer>(Customer.class));
			pb.setBeanList(beanList);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return pb;
	}
	/**
	 * 给指定的sql语句添加条件
	 * @param sql
	 * @param form
	 * @return
	 */
	private List<Object> appendSql(StringBuilder sql,Customer form){
		List<Object> params = new ArrayList<Object>();
		String cname = form.getCname();
		String gender = form.getGender();
		String cellphone = form.getCellphone();
		String email = form.getEmail();
		
		if(cname!=null&&!cname.trim().isEmpty()){
			//sql.append(" AND cname like '%"+cname+"%'");
			sql.append(" AND cname like ?");
			params.add("%"+cname+"%");
		}
		if(gender!=null&&!gender.trim().isEmpty()){
			sql.append(" AND gender like ?");
			params.add("%"+gender+"%");
		}
		if(cellphone!=null&&!cellphone.trim().isEmpty()){
			sql.append(" AND cellphone like ?");
			params.add("%"+cellphone+"%");
		}
		if(email!=null&&!email.trim().isEmpty()){
			sql.append(" AND email like ?");
			params.add("%"+email+"%");
		}
		return params;
	}

}
