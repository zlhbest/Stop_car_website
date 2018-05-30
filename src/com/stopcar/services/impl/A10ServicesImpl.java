package com.stopcar.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.stopcar.services.support.JDBCSupport;

public class A10ServicesImpl extends JDBCSupport
{
	public boolean update(String type)throws Exception
	{
		if(type.contentEquals("add"))
		{
			return this.add();
		}
		else
		{
			throw new Exception("Service 实现类["+this.getClass().getName()+"]存在未定义方法类别......");
		}
	}
	private boolean add()throws Exception
	{
		StringBuilder sql = new StringBuilder()
		.append("INSERT INTO aa01(aa002,aa003,aa004,")
		.append("              aa005,aa006,aa007,aa008)")
		.append("     VALUES(?,?,?,?,?,?,?) ");
		Object[] vals = {
				this.getVal("name"),
				this.getVal("password"),
				"1",
				"0",
				"0",
				this.getVal("email"),
				this.getVal("mobile")
		};
		return this.executeUpdate(sql.toString(), vals);
	}
	public Map<String,String> login() throws Exception
	{
		//1、定义SQL语句
		StringBuilder sql = new StringBuilder()
				.append("SELECT a.aa001,a.aa004,a.aa005,a.aa006,a.aa007,a.aa008,a.aa009,a.aa010")
				.append("  FROM aa01 a")
				.append(" WHERE a.aa002=?")
				.append("   AND a.aa003=?");
		
		Object[] vals = {
				this.getVal("username"),
				this.getVal("password")
		};
		return this.queryForMap(sql.toString(), vals);
		}
	/**
	 * 数据查询
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> query()throws Exception
	{
		StringBuilder sql = new StringBuilder()
		.append("SELECT a.aa001,a.aa002,a.aa004,a.aa005,a.aa005,a.aa006,a.aa006,a.aa007,a.aa008,a.aa009")
		.append("   FROM aa01 a")
		;
		return this.queryForList(sql.toString());
	}
	@Override
	public Map<String, String> selectparkbyuserid() throws Exception {
		StringBuilder sql = new StringBuilder()
		.append("SELECT cb.cb001,cb.cb002,cb.cb004,c.ca002,c.ca004,c.ca006")
		.append("  FROM cb01001 cb,ca01 c")
		.append(" WHERE c.ca001=cb.ca001")
		.append("   AND cb.aa001=?")
		;
		Object[] vals = {
				this.getVal("userid"),
		};
		return this.queryForMap(sql.toString(),vals);
	}
}

