package com.stopcar.web.impl.b1;

import com.stopcar.web.impl.B10Support;

public class B1001Servlet extends B10Support{
	@Override
	public String execute() throws Exception {
		this.queryData();
		return "allmanager";
	}
}
