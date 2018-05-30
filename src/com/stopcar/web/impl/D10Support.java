package com.stopcar.web.impl;

import com.stopcar.services.impl.D10ServicesImpl;
import com.stopcar.web.support.ControllerSupport;

public abstract class D10Support extends ControllerSupport{
	public D10Support()
	{
		this.services =new D10ServicesImpl();
	}
}
