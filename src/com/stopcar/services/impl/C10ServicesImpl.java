package com.stopcar.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.Query;

import com.stopcar.services.support.JDBCSupport;

public class C10ServicesImpl extends JDBCSupport{
	
	public List<Map<String,String>> getstate() throws Exception 
	{
		//1、定义SQL语句
		StringBuilder sql = new StringBuilder()
		.append("SELECT  a.cb001,a.cb002")
		.append(" FROM cb01001 a")
		.append(" WHERE a.ca001=?");
		Object[] vals = {
			this.getVal("parkname")
		};//这里是暂时吧所有的数据放进了一个表里面，再拆分，等项目稳定再改
		List<Map<String,String>> result = this.queryForList(sql.toString(), vals);
		return result;
	}
	//这里是想state转变为1的
	public boolean updatestate01() throws Exception
	{
		//更新单个停车位分以下几个步骤
		//1、获取前端要修改的id，通过parkid获取到停车场的编号和车位数，这里的设定是一个人可以有多个车位
		//2、车位数减一然后挺车子的数加一
		//3、显示在前端
		//通过id获取到当前停车位的停车场,首先要判断一下用户是否有停车位状态
		StringBuilder sqlforuserid = new StringBuilder()
		.append("SELECT cb.aa001,cb.cb002")
		.append("  FROM cb01001 cb")
		.append(" WHERE cb.aa001=?")
		;
		Object[] valsforuserid = {
				this.getVal("userid")
		};
		Map<String,String> resultforuserid=this.queryForMap(sqlforuserid.toString(),valsforuserid);
		//有数据并且状态为1的时候是不能改数据的
		if(!resultforuserid.isEmpty()&&(!resultforuserid.get("cb002").equals("2")))
		{
			return false;
		}
		else if(!resultforuserid.isEmpty()&&(resultforuserid.get("cb002").equals("2")))
		{
			StringBuilder sqlforpark = new StringBuilder()
					.append("SELECT ca.ca004,ca.ca006,ca.ca001")
					.append("  FROM ca01 ca,cb01001 cb")
					.append(" WHERE ca.ca001=cb.ca001")
					.append("   AND cb.cb001=?")
					;
			Object[] valssqlforpark = {
					this.getVal("parkid")
			};
			Map<String,String> result = this.queryForMap(sqlforpark.toString(),valssqlforpark);
			//reslut获取到了车位所在的停车场的停车位和已经预定的数目
			//定义SQL语句
			boolean sqlpark=false;
			boolean sqlueser =false;
			StringBuilder sql = new StringBuilder()
					.append("UPDATE cb01001")
					.append("  SET  cb002 = ?")
					.append("  WHERE cb001=?");
			Object[] vals = {
					this.getVal("state"),
					this.getVal("parkid")
			};
			sqlpark=this.executeUpdate(sql.toString(), vals);
			StringBuilder sql2 = new StringBuilder()
					.append("UPDATE aa01 a")
					.append("  SET a.aa005=1")
					.append(" WHERE a.aa001=?")
					;
					Object[] vals2= {
							this.getVal("userid"),
					};
			sqlueser = this.executeUpdate(sql2.toString(),vals2);
			if((sqlpark&&sqlueser)&&(Integer.parseInt(result.get("ca004"))>0))
			{
				StringBuilder sqlforparkone = new StringBuilder()
						.append("UPDATE ca01 ca")
						.append("  SET ca.ca004=?,ca.ca006=?")
						.append(" WHERE ca.ca001=?")
						;
				Object[] valsforparkone = {
						Integer.parseInt(result.get("ca004"))-1,
						Integer.parseInt(result.get("ca006"))+1,
						result.get("ca001"),
				};
				return this.executeUpdate(sqlforparkone.toString(),valsforparkone);
			}
		}
		return false;
	}
	public boolean updatestate02()throws Exception
	{
		//如果user有已经预定的车位或者有再用的车位，
		StringBuilder sqlforuserid = new StringBuilder()
		.append("SELECT cb.aa001,cb.cb002")
		.append("  FROM cb01001 cb")
		.append(" WHERE cb.aa001=?")
		;
		Object[] valsforuserid = {
				this.getVal("userid")
		};
		Map<String,String> resultforuserid=this.queryForMap(sqlforuserid.toString(),valsforuserid);
		if(!(resultforuserid==null))
		{
			return false;
		}
		else if(resultforuserid==null)
		{
			boolean sqlpark=false;
			boolean sqlueser =false;
			StringBuilder sql = new StringBuilder()
					.append("UPDATE cb01001")
					.append("  SET  cb002 = ?,aa001=?")
					.append("  WHERE cb001=?");
			Object[] vals = {
					this.getVal("state"),
					this.getVal("userid"),
					this.getVal("parkid")
			};
			sqlpark= this.executeUpdate(sql.toString(), vals);
			StringBuilder sql2 = new StringBuilder()
			.append("UPDATE aa01 a")
			.append("  SET a.aa005=2")
			.append(" WHERE a.aa001=?")
			;
			Object[] vals2= {
					this.getVal("userid"),
			};
			sqlueser = this.executeUpdate(sql2.toString(),vals2);
			if(sqlpark&&sqlueser)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	/**
	 * 当用户预定后取消预定时，将用户名变为-1，并且将状态该为0
	 */
	@Override
	public boolean guiling2() throws Exception {
		boolean sqlpark=false;
		boolean sqlueser =false;
		StringBuilder sql = new StringBuilder()
		.append("UPDATE cb01001 cb")
		.append("   SET cb.aa001=-1,cb.cb002=0")
		.append(" WHERE cb.aa001=?")
		;
		Object[] vals= {
				this.getVal("userid"),
		};
		sqlpark = this.executeUpdate(sql.toString(),vals);
		StringBuilder sql2 = new StringBuilder()
		.append("UPDATE aa01 a")
		.append("  SET a.aa005=0")
		.append(" WHERE a.aa001=?")
		;
		Object[] vals2= {
				this.getVal("userid"),
		};
		sqlueser = this.executeUpdate(sql2.toString(),vals2);
		
		if(sqlpark&&sqlueser)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	@Override
	public boolean guiling1() throws Exception {
		boolean sqlpark=false;
		boolean sqlueser =false;
		boolean sqlparkchange = false;
		StringBuilder sql2 = new StringBuilder()
		.append("UPDATE aa01 a")
		.append("  SET a.aa005=0")
		.append(" WHERE a.aa001=?")
		;
		Object[] vals2= {
				this.getVal("userid"),
		};
		sqlueser = this.executeUpdate(sql2.toString(),vals2);
		StringBuilder sql3 = new StringBuilder()
		.append("UPDATE cb01001 cb,ca01 ca")
		.append("   SET ca.ca004=ca.ca004+1,ca.ca006=ca.ca006-1")
		.append(" WHERE ca.ca001=cb.ca001")
		.append("  AND cb.aa001=?")
		;
		Object[] vals3= {
				this.getVal("userid"),
		};
		sqlparkchange = this.executeUpdate(sql3.toString(),vals3);
		StringBuilder sql = new StringBuilder()
		.append("UPDATE cb01001 cb")
		.append("   SET cb.aa001=-1,cb.cb002=0")
		.append(" WHERE cb.aa001=?")
		;
		Object[] vals= {
				this.getVal("userid"),
		};
		sqlpark = this.executeUpdate(sql.toString(),vals);
		if(sqlpark&&sqlueser&&sqlparkchange)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 数据查询
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> query()throws Exception
	{
		//1、还原页面数据
		Object ca002=this.getVal("qca002");   //停车场名称
		Object ca003=this.getVal("qca003");   //停车场位置
		//2、完成啥sql主体的定义
		StringBuilder sql = new StringBuilder()
		.append("SELECT c.ca001,c.ca002,c.ca003,c.ca004,c.ca004,c.ca005,c.ca006,b.ba002")
		.append(" FROM ca01 c,ba01 b")
		.append(" WHERE b.ba001=c.ba001");
		//3、定义装载动态参数的List
		List<Object> params = new ArrayList<>();
		//开始拼接查询条件
		if(this.isNotNUll(ca002))
		{
			sql.append(" and c.ca002 like ?");
			params.add("%"+ca002+"%");
		}
		if(this.isNotNUll(ca003))
		{
			sql.append(" and c.ca003 like ?");
			params.add("%"+ca003+"%");
		}
		return this.queryForList(sql.toString(), params.toArray());
	}
	@Override
	public boolean update(String utype) throws Exception {
		if(utype.equalsIgnoreCase("add"))
		{
			return this.add();
		}
		else if(utype.equalsIgnoreCase("modify"))
		{
			return this.modify();
		}
		else if(utype.equalsIgnoreCase("delete"))
		{
			return this.delete();
		}
		else if(utype.equalsIgnoreCase("batchDelete"))
		{
			return this.batchDelete();
		}
		return super.update(utype);
	}
	/**
	 * 数据批量删除
	 * @return
	 * @throws Exception
	 */
	public boolean batchDelete()throws Exception
	{
		//1.定义SQL语句
		String sql="delete from ca01 where ca001=?";
		//2.还原checkbox数组
		String[] idlist=this.getArray("idlist");
		//3.执行
		return this.batchUpdate(sql, idlist);
	}
	/**
	 * 单一实例删除
	 * @return
	 * @throws Exception
	 */
	public boolean delete()throws Exception
	{
		//1.定义SQL语句
		String sql="delete from ca01 where ca001=?";
		//2.执行SQL
		return this.executeUpdate(sql, this.getVal("ca001"));
	}
	
	/**
	 * 单一实例修改
	 * @return
	 * @throws Exception
	 */
	public boolean modify()throws Exception
	{
		//1.定义SQL语句
		StringBuilder sql=new StringBuilder()
		.append("UPDATE ca01 c")
		.append(" SET c.ba001=?,c.ca002=?,")
		.append("		 c.ca003=?,c.ca004=?,")
		.append("		 c.ca005=?,c.ca006=?")
		.append(" WHERE c.ca001=?")		
		;
		//2.编写参数数组
		Object[] val={
			this.getVal("ba001"),
			this.getVal("ca002"),
			this.getVal("ca003"),
			this.getVal("ca004"),
			this.getVal("ca005"),
			"0",
			this.getVal("ca001"),
		};
		//3.执行SQL语句
		return this.executeUpdate(sql.toString(), val);
	}  
	public boolean add()throws Exception
	{
		//1.定义SQL
		StringBuilder sql=new StringBuilder()
		.append("INSERT INTO ca01(ba001,ca002,ca003,ca004,ca005,ca006)")
		.append(" VALUES(?,?,?,?,?,?)")
        ;
		//2.编写参数数组
		Object[] val={
				this.getVal("ba001"),
				this.getVal("ca002"),
				this.getVal("ca003"),
				this.getVal("ca004"),
				this.getVal("ca005"),
				"0"
		};
		//3.执行SQL
		return this.executeUpdate(sql.toString(), val);
	}
	@Override
	public Map<String, String> findById() throws Exception 
	{
		//1.定义SQL语句
		StringBuilder sql=new StringBuilder()
		.append("SELECT b.ba002,c.ca002,c.ca003,c.ca004,c.ca005,c.ca006")
		.append(" FROM ca01 c,ba01 b")
		.append(" WHERE  b.ba001=c.ba001")
		.append("   AND c.ca001=?")
		;
		//2.执行查询
		return this.queryForMap(sql.toString(), this.getVal("ca001"));
	}
	@Override
	public Map<String, String> parkid() throws Exception {
		StringBuilder sql = new StringBuilder()
		.append("SELECT ca001,ca002,ca003,ca004,ca005,ca006,ca007,ba002")
		.append("  FROM ca01,ba01")
		.append(" WHERE ca01.ca001=?")
		.append("   AND ba01.ba001=ca01.ba001")
		;
		//2.编写参数数组
		Object[] val={
				this.getVal("parkid")
		};
		return this.queryForMap(sql.toString(), val);
	}
}
