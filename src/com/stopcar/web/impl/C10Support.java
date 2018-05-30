package com.stopcar.web.impl;

import com.stopcar.services.impl.C10ServicesImpl;
import com.stopcar.web.support.ControllerSupport;

public abstract class C10Support extends ControllerSupport{
	public C10Support()
	{
		this.services = new C10ServicesImpl();
	}
}
