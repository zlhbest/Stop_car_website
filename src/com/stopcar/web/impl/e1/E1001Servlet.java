package com.stopcar.web.impl.e1;

import com.stopcar.web.impl.E10Support;

public class E1001Servlet extends E10Support{
	@Override
	public String execute() throws Exception {
		this.queryData();
		return "parkone";
	}
}
