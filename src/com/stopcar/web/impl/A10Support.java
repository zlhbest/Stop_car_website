package com.stopcar.web.impl;

import com.stopcar.services.impl.A10ServicesImpl;
import com.stopcar.web.support.ControllerSupport;

public abstract class A10Support extends ControllerSupport {

	public  A10Support() 
	{
		//为控制器提供Services实现类---保证控制器只要被实例化,内部就会自动包含对应的Services,
    	//但是Services中没有包含页面数据,为了应用Services必须另行寻找办法,传递页面数据
		this.services = new A10ServicesImpl();
	}

}
