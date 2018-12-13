package com.software.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.software.entity.User;

/**
 * 访问教务员用户下的所有页面的过滤器
 * 当session中的user丢失，即需要重新登录
 * @author hua
 *
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE
		}
					, urlPatterns = { "/acdemic_dean/*" })
public class AcdemicDeanJspFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		User user = (User) request.getSession().getAttribute("user");
		
		if(user == null){
			String message = "账号已过期，请重新登录";
			request.getSession().setAttribute("message", message);	
			//重定向回到登录页面
			response.sendRedirect(request.getContextPath() + "/login/login.jsp");
		}
		
		filterChain.doFilter(request, response);
	}

    
}
