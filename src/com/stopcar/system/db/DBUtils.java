package com.stopcar.system.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


import sun.font.BidiUtils;


public class DBUtils 
{
	//1、定义驱动串---整个驱动jar包中，核心类的路径
	private static String driver = null;
	//2、定义链接串---数据库的位置及名称
	private static String url = null;
	private static String userName = null;
	private static String password = null;
	
	//为当前线程创建线程副本常量
	private final static ThreadLocal<Connection> threadlocal = new ThreadLocal<>();
	
	/**
	 * 静态块
	 * 在类被第一次加载入内存时候，执行，以后不再执行
	 */
	static
	{
		try
		{
			//获取资源文件解析器实例
			ResourceBundle  bundle=ResourceBundle.getBundle("DBOPtions");
			//从资源文件中获取数据
			driver = bundle.getString("DRIVER");
			url = bundle.getString("URL");
			userName = bundle.getString("USERNAME");
			password = bundle.getString("PASSWORD");
			
			//3、加载驱动
			Class.forName(driver);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 当一个类中所有的成员(属性和方法)都是static，那么此时构造器应该是私有的
	 */
	private DBUtils() {};
	
	public static Connection getConnection()
		throws Exception
	{
		//1.到当前线程的线程副本中获取数据库连接
		Connection conn  =threadlocal.get();
		//2.判断连接是否存在
		if(conn==null||conn.isClosed())
		{
			//3、创建数据库连接
		     conn = DriverManager.getConnection(url,userName,password);
		     
		     //4.连接与当前的线程绑定，就相当于将钱存进银行卡
		     threadlocal.set(conn);
		}
		return conn;
	}
	public static void close()
	{
		try
		{
			//获取当前线程绑定的连接对象
			Connection conn  = threadlocal.get();
			//判断连接是否存在
			if(conn != null || !conn.isClosed())
			{
				//解除与当前线程的绑定
				threadlocal.remove();
				//关闭连接
				conn.close();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static void close(ResultSet rs)
	{
		try
		{
			if(rs!=null)
			{
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	
	
	public static void close(PreparedStatement pstm)
	{
		try
		{
			if(pstm!=null)
			{
				pstm.close();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void close(Connection conn)
	{
		try
		{
			if(conn!=null && !conn.isClosed())
			{
				conn.close();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	/*****************************************************
	 *     以下SQL编译方法
	 *****************************************************/
	/**
	 * 编译SQL语句
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static PreparedStatement preparedStatement(String sql)throws Exception
	{
		return DBUtils.getConnection().prepareStatement(sql);
	}
	/*****************************************************
	 *     以下为事务相关方法
	 *****************************************************/
	/**
	 * 开启事务
	 * @throws Exception
	 */
	public static void beginTransaction()throws Exception
	{
		DBUtils.getConnection().setAutoCommit(false);
	}
	/**
	 * 提交事务
	 * @throws Exception
	 */
	public static void commit()throws Exception
	{
		DBUtils.getConnection().commit();
	}
	/**
	 * 事务回滚
	 */
	public static void rollback()
	{
		try
		{
			DBUtils.getConnection().rollback();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	/**
	 * 结束事务
	 */
	public static void endTransaction()
	{
		try
		{
			DBUtils.getConnection().setAutoCommit(true);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
