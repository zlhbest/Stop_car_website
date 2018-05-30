package com.stopcar.web.impl.c1;

import com.stopcar.web.impl.C10Support;

public class C1013Servlet extends C10Support{
	@Override
	public String execute() throws Exception {
		this.update("modify");
		return "addpark";
	}

}
