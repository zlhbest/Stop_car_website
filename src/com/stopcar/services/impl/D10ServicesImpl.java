package com.stopcar.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.stopcar.services.support.JDBCSupport;
import com.stopcar.system.tools.Tools;

public class D10ServicesImpl extends JDBCSupport{
	public Map<String,String> findById() throws Exception
	{
		StringBuilder sql = new StringBuilder()
		.append("SELECT *")
		.append(" FROM admin d")
		.append(" WHERE d.password=?")
		.append("   AND d.username=?");
		Object args[] = {
				Tools.getMd5Code(this.getVal("pwd")),
				this.getVal("name")
		};
		return this.queryForMap(sql.toString(),args);
	}
	@Override
	public List<Map<String, String>> query() throws Exception {
		Object name=this.getVal("name");   //Í£³µ³¡Ãû³Æ
		StringBuilder sql = new StringBuilder()
		.append("SELECT builder.`name`")
		.append("  FROM builder")
		.append(" WHERE 1=1")
		;
		List<Object> params = new ArrayList<>();
		if(this.isNotNUll(name))
		{
			sql.append(" AND builder.`name` LIKE ?");
			params.add("%"+name+"%");
		}
		return this.queryForList(sql.toString(),params.toArray());
	}
}
