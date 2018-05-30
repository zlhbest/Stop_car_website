package com.stopcar.web.impl.c1;

import com.stopcar.web.impl.C10Support;

public class C1011Servlet extends C10Support{
	@Override
	public String execute() throws Exception 
	{
		this.update("add");
		return "addpark";
	}
}
