package com.stopcar.web.impl.d1;

import java.util.Map;

import com.stopcar.web.impl.D10Support;

public class D1001Servlet extends D10Support{
	@Override
	public String execute() throws Exception {
		this.findById();
		if(this.getAttributes().get("ins")!=null)
		{
			return "admin";
		}
		else
		{
			this.addAttrobute("msg", "用户名或密码错误!");
			return "index";
		}
	}
}
