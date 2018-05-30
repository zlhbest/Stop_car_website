package com.stopcar.services.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stopcar.system.db.DBUtils;


public class JDBCSupport implements BaseServices {

	/************************************************************************
	 *   DTO相关方法
	 ************************************************************************/
    //参数的引用传递和值传递：参数传递就是说不管有多少的对象，
	//所有的值都是在内存中只有一个，开辟多少对象只是多了一个对这个内存地址的引用多了一个
	//但是值引用是在传递中不管有几个都会开辟空间。
	//值传递是八大类型 int float之类的，这种在方法中修改不会改变本身的值，改变仅限于方法中
	//引用传递是指对象，方法中修改也会对参数修改
	private Map<String,Object> dto = null;
	/**
	 * 接收DTO
	 */
	public void setMapDto(Map<String,Object> dto)
	{
		this.dto = dto;
	}
	/**
	 * 获取单一数据
	 * @param val
	 * @return
	 */
	protected final Object getVal(String key)
	{
		return this.dto.get(key);
	}
	/**
	 * 向DTO写入数据
	 * @param key
	 * @param value
	 */
	protected final void addEntry(String key,Object value)
	{
		this.dto.put(key, value);
	}
	/************************************************************************
	 *   辅助方法
	 ************************************************************************/
	/**
	 * 此方法是将不管是字符串还是字符串数组都变成字符串数组
	 * @param key
	 * @return
	 */
	protected final String[] getArray(String key)
	{
		Object val=this.dto.get(key);
		if(val==null||val.equals(""))
		{
			return new String[] {};//匿名字符串数组
		}
		if(val instanceof java.lang.String[])
		{
			return (String[])val;
		}
		else
		{
			return new String[] {val.toString()};
		}
	}
	/**
	 * 判断字符串是不是空值或者是null
	 * @param val
	 * @return
	 */
	protected final boolean isNotNUll(Object val)
	{
		return val!=null&&!val.equals("");
	}
	/************************************************************************
	 *   查询处理
	 ************************************************************************/
	/**
	 * 数据批量查询
	 * <
	 * 1.还原页面条件为Object类型
	 * 2.拼接SQL语句,保证:
	 *    2.1 ?与?值,同生共死
	 *    2.1 ?产生的顺序,与?值进入数组的顺序,严格一一匹配
	 * 3.编译后,读数组为?赋值   
	 * >
	 * @return
	 * @throws Exception
	 */
	protected final List<Map<String,String>> queryForList(final String sql,final Object...val)throws Exception
	{
		//1、创建JDBC接口变量
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try
		{
			//获取连接资源
			conn=DBUtils.getConnection();
			//编译SQL语句
			pstm = conn.prepareStatement(sql);
			//读取参数数组，为pstm赋值
			int index = 1;
			for(Object param:val)
			{
				pstm.setObject(index++, param);
			}
			//执行SQL语句:通过语句对象执行SQL，返回结果集对象
		    rs=pstm.executeQuery();
		    //获取结果集描述对象
		    ResultSetMetaData rsmd = rs.getMetaData();
		    //计算查询结果中的列数
		    int count = rsmd.getColumnCount();
		    //计算初始容量
		    int initsize=((int)(count/0.75))+1;
		    
		    //定义List装载全部查询结果
		    List<Map<String,String>> rows = new ArrayList<>();
		    //定义Map装载当前的数据
		    Map<String,String> ins = null;
		    //while控制循环的rs向下循环
		    while(rs.next())
		    {
		    	//实例化当前的HashMap
		    	ins = new HashMap<>(initsize);
		    	//控制每行中个列的读取
		    	for(int i=1;i<=count;i++)
		    	{
		    		//将当前行的列，返给Map，列标题做key，列数据做value
		    		ins.put(rsmd.getColumnLabel(i).toLowerCase(), rs.getString(i));
		    	}
		    	rows.add(ins);
		    }
		    return rows;
		    
		}
		finally
		{
			DBUtils.close(rs);
			DBUtils.close(pstm);
			DBUtils.close(conn);  
			}
		}
	/**
	 * 单一实例查询  对位开放
	 * @param sql --  执行的sql语句 
	 * @param val --  参数值数组
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> queryForMapOpen(final String sql,final Object...val)throws Exception
	{
		//1.定义JDBC接口
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		try
		{
			//2.创建连接
			conn=DBUtils.getConnection();
			//3.编译SQL
			pstm=conn.prepareStatement(sql);
			//4.参数赋值
			int index=1;
			for(Object param:val)
			{
				pstm.setObject(index++, param);
			}
			//5.执行SQL
			rs=pstm.executeQuery();
			
			//6.定义Map变量,装载当前行数据
			Map<String,String> ins=null;
			//7.判断是否存在查询结果
			if(rs.next())
			{
				//8.获取结果集描述对象
				ResultSetMetaData rsmd=rs.getMetaData();
				//9.计算查询结果中的列数
				int count=rsmd.getColumnCount();
				//10.计算安全的初始容量
				int initSize=((int)(count/0.75))+1;
				//11.实例化HashMap,用以装载当前行数据
				ins=new HashMap<>(initSize);
				//12.循环解析当前行每列
				for(int i=1;i<=count;i++)
				{
					ins.put(rsmd.getColumnLabel(i).toLowerCase(), rs.getString(i));
				}
				
			}
			
			return ins;
		}
		finally
		{
			DBUtils.close(rs);
			DBUtils.close(pstm);
			DBUtils.close(conn);
		}
	}
	/**
	 * 单一实例查询
	 * @param sql --  执行的sql语句 
	 * @param val --  参数值数组
	 * @return
	 * @throws Exception
	 */
	protected final Map<String,String> queryForMap(final String sql,final Object...val)throws Exception
	{
		//1.定义JDBC接口
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		try
		{
			//2.创建连接
			conn=DBUtils.getConnection();
			//3.编译SQL
			pstm=conn.prepareStatement(sql);
			//4.参数赋值
			int index=1;
			for(Object param:val)
			{
				pstm.setObject(index++, param);
			}
			//5.执行SQL
			rs=pstm.executeQuery();
			
			//6.定义Map变量,装载当前行数据
			Map<String,String> ins=null;
			//7.判断是否存在查询结果
			if(rs.next())
			{
				//8.获取结果集描述对象
				ResultSetMetaData rsmd=rs.getMetaData();
				//9.计算查询结果中的列数
				int count=rsmd.getColumnCount();
				//10.计算安全的初始容量
				int initSize=((int)(count/0.75))+1;
				//11.实例化HashMap,用以装载当前行数据
				ins=new HashMap<>(initSize);
				//12.循环解析当前行每列
				for(int i=1;i<=count;i++)
				{
					ins.put(rsmd.getColumnLabel(i).toLowerCase(), rs.getString(i));
				}
				
			}
		
			return ins;
		}
		finally
		{
			DBUtils.close(rs);
			DBUtils.close(pstm);
			DBUtils.close(conn);
		}
	}
	/************************************************************************
	 *   多表事务处理
	 ************************************************************************/
	private final List<PstmManagerBean> pstmList = new ArrayList<>();
	/**
	 * 向事务中添加非批处理语句
	 * @param sql
	 * @param args
	 * @throws Exception
	 */
	protected void appendSql(String sql,Object...args)throws Exception
	{
		//创建语句对象并编译赋值
		PreparedStatement pstm = DBUtils.preparedStatement(sql);
		int index=1;
		for(Object param:args)
		{
			pstm.setObject(index++,param);
		}
		//为语句对象提供管理器
		PstmManagerBean pm = new PstmManagerBean(pstm, false);
		//将pstmbean放入pstmList
		this.pstmList.add(pm);
	}
	/**
	 * 为事务添加批处理语句
	 * <
	 *   语句格式如下:
	 *   delete from table where id=?
	 * >
	 * @param sql
	 * @param args
	 * @throws Exception
	 */
	protected void appendSqlForBatch(final String sql,final Object...args)throws Exception
	{
		//1.编译SQL语句
		PreparedStatement pstm=DBUtils.preparedStatement(sql);
		for(Object param:args)
		{
			pstm.setObject(1, param);
			pstm.addBatch();
		}
		
		//构造Pstm管理bean
		PstmManagerBean pm=new PstmManagerBean(pstm, true);
		//放入pstmlist
		this.pstmList.add(pm);
	}
	
	/**
	 * 为事务添加SQL语句
	 * 
	 * <
	 *   单一状态修改语句
	 *   语句格式如下:
	 *   update table 
	 *      set columns=?
	 *    where id=?  
	 * >
	 * @param sql        ---- SQL语句
	 * @param newState   ---- 单一状态值(set列表中字段的目标值)
	 * @param args       ---- 主键数组
	 * @throws Exception
	 */
	protected void appendSqlForBatch(final String sql,final Object newState,final Object...args)throws Exception
	{
		PreparedStatement pstm=DBUtils.preparedStatement(sql);
		//先为第一个参数赋值
		pstm.setObject(1, newState);
		//循环为第二个参数赋值,并放入缓冲区
		for(Object param:args)
		{ 
		   pstm.setObject(2, param);	
		   pstm.addBatch();
		}
		//获取pstm管理器
		PstmManagerBean pm=new PstmManagerBean(pstm, true);
		//放入pstmList
		this.pstmList.add(pm);
	}
	/**
	 * 为事务添加SQL语句
	 * <
	 *   多状态修改语句
	 *   语句格式如下:
	 *   update table 
	 *      set col1=?,col2=?,col3=?.....coln=? 
	 *    where id=?
	 * >
	 * @param sql        ---- SQL语句
	 * @param states     ---- set列表中各?的对应值
	 * @param args       ---- 主键数组
	 * @throws Exception
	 */
	protected void appendSqlForBatch(final String sql,final Object[] states ,final Object...args)throws Exception
	{
		PreparedStatement pstm=DBUtils.preparedStatement(sql);
		//1.先完成set列表的赋值
		int index=1;
		for(Object s:states)
		{
			pstm.setObject(index++, s);
		}
		for(Object e:args)
		{
			pstm.setObject(index, e);
			pstm.addBatch();
		}
		//获取pstm管理器
		PstmManagerBean pm=new PstmManagerBean(pstm, true);
		//放入pstmList
		this.pstmList.add(pm);
	}

	
	/**
	 * 事务执行方法
	 * @return
	 * @throws Exception
	 */
	protected boolean executeTransaction()throws Exception
	{
		//1.定义事务返回值
		boolean tag=false;
		//2.开启事务
		DBUtils.beginTransaction();
		try
		{
			//以事务方式执行SQL语句
			for(PstmManagerBean pm:pstmList)
			{
				pm.executePreparedStatement();
			}
			//提交事务
			DBUtils.commit();
			tag=true;
			
		}
		catch(Exception ex)
		{
			//事务回滚
			DBUtils.rollback();
		    ex.printStackTrace();
		    
		}
		finally
		{
			//结束事务
			DBUtils.endTransaction();
			//关闭所有语句对象
			this.closePreparedStatement();
			//清除语句对象管理器
			this.pstmList.clear();
		}
		return tag;
	}
	
	/**
	 * 关闭事务期间的所有语句对象
	 */
	private void closePreparedStatement()
	{
		for(PstmManagerBean pm:pstmList)
		{
			pm.close();
		}
	}

	
	
	
	/************************************************************************
	 *   单一表数据更新 
	 ************************************************************************/
	
	/**
	 * 单一表批处理
	 * <
	 *   适合的SQL语句格式如下:
	 *   DELETE FROM TABLE WHERE ID=?
	 * >
	 * @param sql  --- 需要执行的SQL语句
	 * @param val  --- checkbox数组
	 * @return
	 * @throws Exception
	 */
	protected final boolean batchUpdate(final String sql,final Object...val)throws Exception
	{
		//1.定义JDBC接口
		Connection conn=null;
		PreparedStatement pstm=null;
		try
		{
			//2.创建连接
			conn=DBUtils.getConnection();
			//3.编译SQL
			pstm=conn.prepareStatement(sql);
			//4.参数赋值
			for(Object param:val)
			{
				pstm.setObject(1, param);
				//添加缓冲区
				pstm.addBatch();
			}
			
			//定义事务返回值
			boolean tag=false;
			//开启事务
			conn.setAutoCommit(false);
			try
			{
				//在事务中执行缓冲区的语句
				pstm.executeBatch();
				//提交事务
				conn.commit();
				
				//修改返回值
				tag=true;
			}
			catch(Exception ex)
			{
				conn.rollback();
				ex.printStackTrace();
			}
			finally
			{
			    conn.setAutoCommit(true);
			}
			return tag;
		}
		finally
		{
			DBUtils.close(pstm);
			DBUtils.close(conn);
		}
	} 
	/**
	 * 单一表数据更新方法  对外开放
	 * @param sql
	 * @param val
	 * @return
	 * @throws Exception
	 */
	public  boolean executeUpdateOpen( String sql,  Object...val)throws Exception
	{
		//1.定义JDBC接口
		Connection conn=null;
		PreparedStatement pstm=null;
		try
		{
			//2.创建连接
			conn=DBUtils.getConnection();
			//3.编译SQL语句
			pstm=conn.prepareStatement(sql);
			//4.参数赋值
			int index=1;
			for(Object param:val)
			{
				pstm.setObject(index++, param);
			}
			//5.执行
			return pstm.executeUpdate()>0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			DBUtils.close(pstm);
			DBUtils.close(conn);
		}
		return false;
	}
	
	/**
	 * 单一表数据更新方法
	 * @param sql
	 * @param val
	 * @return
	 * @throws Exception
	 */
	protected final boolean executeUpdate(final String sql, final Object...val)throws Exception
	{
		//1.定义JDBC接口
		Connection conn=null;
		PreparedStatement pstm=null;
		try
		{
			//2.创建连接
			conn=DBUtils.getConnection();
			//3.编译SQL语句
			pstm=conn.prepareStatement(sql);
			//4.参数赋值
			int index=1;
			for(Object param:val)
			{
				pstm.setObject(index++, param);
			}
			//5.执行
			return pstm.executeUpdate()>0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			DBUtils.close(pstm);
			DBUtils.close(conn);
		}
		return false;
	}
	
}
