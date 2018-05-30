package com.stopcar.web.support;

import java.util.Map;

public interface BaseController 
{
	/**
	 * DTO注入方法
	 * <
	 *   数据的输入
	 *   BaseServlet通过该方法，为业务控制器传递页面输入数据
	 * >
	 * @param dto
	 */
	void setDto(Map<String,Object> dto);
	/**
	 * 业务控制器中进行流程控制的方法
	 * @return 用于业务处理结束跳转页面
	 * @throws Exception
	 */
	String execute()throws Exception;
	/**
	 * 属性读入
	 * <
	 *    数据输入
	 *    Baseservlet通过该方法，读取到servlet传递到页面的输出数据
	 * >
	 * @return
	 */
	Map<String,Object> getAttributes();
	
	
}
