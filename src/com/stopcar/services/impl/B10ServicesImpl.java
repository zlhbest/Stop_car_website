package com.stopcar.services.impl;

import java.util.List;
import java.util.Map;

import com.stopcar.services.support.JDBCSupport;

public class B10ServicesImpl extends JDBCSupport
{
	public List<Map<String, String>> query()throws Exception
	{
		StringBuilder sql = new StringBuilder()
		.append("SELECT b.ba001,b.ba002,c.ca002,c.ca001")
		.append("  FROM ba01 b,ca01 c")
		.append(" WHERE c.ca001=b.ba004")
		;
		return this.queryForList(sql.toString());
	}
	public Map<String,String> login() throws Exception
	{
		//1°¢∂®“ÂSQL”Ôæ‰
		StringBuilder sql = new StringBuilder()
				.append("SELECT c.ca002")
				.append("  FROM ba01 a,ca01 c")
				.append(" WHERE a.ba002=?")
				.append("   AND a.ba003=?")
				.append("   AND c.ba001=a.ba001");
		
		Object[] vals = {
				this.getVal("username"),
				this.getVal("password")
		};
		return this.queryForMap(sql.toString(), vals);
	}
}
