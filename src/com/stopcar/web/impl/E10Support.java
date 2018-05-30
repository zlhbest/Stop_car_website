package com.stopcar.web.impl;


import com.stopcar.services.impl.E10ServicesImpl;
import com.stopcar.web.support.ControllerSupport;

public abstract class E10Support extends ControllerSupport{
	public E10Support()
	{
		this.services =new E10ServicesImpl();
	}
}
