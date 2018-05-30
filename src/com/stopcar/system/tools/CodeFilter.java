package com.stopcar.system.tools;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;

/**
 * Servlet Filter implementation class CodeFilter
 */
@WebFilter("/CodeFilter")
public class CodeFilter extends HttpServlet implements Filter
{

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException 
	{
		//将服务器编码格式转换为GBK
		request.setCharacterEncoding("GBK");
		//response.setCharacterEncoding("GBK");
		//让请求到达目标的servlet
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
