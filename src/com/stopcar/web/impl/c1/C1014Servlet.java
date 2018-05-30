package com.stopcar.web.impl.c1;

import com.stopcar.web.impl.C10Support;

public class C1014Servlet extends C10Support{
	@Override
	public String execute() throws Exception {
		//删除数据
		this.update("delete");
		//重新检索
		this.deleteLastQuery();
		return "allpark";
	}
}
