package com.stopcar.web.impl.c1;

import com.stopcar.web.impl.C10Support;

public class C1002Servlet extends C10Support{
	@Override
	public String execute() throws Exception {
		//将状态从0变为1
		this.updatestate01();
		return null;
	}

}
