package com.stopcar.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.stopcar.services.support.JDBCSupport;
import com.stopcar.system.tools.Tools;

public class E10ServicesImpl extends JDBCSupport{
	@Override
	public List<Map<String, String>> query() throws Exception {
		//1、还原页面数据
		String parkid=this.getVal("ca001").toString(); //通过前端传过来的停车场id查询属于这个停车场的停车位
		Object cb001=this.getVal("qcb001");   //停车场名称
		Object cb002=this.getVal("qcb002");   //停车场位置
		//2、完成啥sql主体的定义
		StringBuilder sql = new StringBuilder()
		.append("SELECT cb.cb001,ca.ca002,a.aa002,cb.cb002,cb.cb004")
		.append(" FROM cb01001 cb,aa01 a,ca01 ca")
		.append(" WHERE a.aa001=cb.aa001")
		.append("   AND ca.ca001=cb.ca001")
		;
		//3、定义装载动态参数的List
		List<Object> params = new ArrayList<>();
		//开始拼接查询条件
		if(this.isNotNUll(cb001))
		{
			sql.append(" and cb.cb001 like ?");
			params.add("%"+cb001+"%");
			}
		if(this.isNotNUll(cb002))
		{
			sql.append(" and cb.cb002 like ?");
			params.add("%"+cb002+"%");
		}
		return this.queryForList(sql.toString(), params.toArray());
	}
	@Override
	public Map<String, String> parkoneid() throws Exception {
		StringBuilder sql = new StringBuilder()
				.append("SELECT cb.cb001,ca.ca002,ca.ca007,a.aa002")
				.append("  FROM cb01001 cb,aa01 a,ca01 ca")
				.append(" WHERE a.aa001=cb.aa001")
				.append("   AND ca.ca001=cb.ca001")
				.append("   AND cb.cb001=?")
				;
		Object args[] = {
				this.getVal("parkid")
		};
		return this.queryForMap(sql.toString(), args);
	}
}
