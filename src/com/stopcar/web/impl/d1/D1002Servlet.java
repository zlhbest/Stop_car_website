package com.stopcar.web.impl.d1;

import com.stopcar.web.impl.D10Support;

public class D1002Servlet extends D10Support{
	@Override
	public String execute() throws Exception {
		this.selectparkname();
		return null;
	}

}
