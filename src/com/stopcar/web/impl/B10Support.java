package com.stopcar.web.impl;


import com.stopcar.services.impl.B10ServicesImpl;
import com.stopcar.web.support.ControllerSupport;

public abstract class B10Support extends ControllerSupport {
	public B10Support() 
	{
		this.services = new B10ServicesImpl();
	}

}
