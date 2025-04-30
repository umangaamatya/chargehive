package com.chargehive.filter;


import java.io.IOException;

import com.chargehive.service.LoginService;
import com.chargehive.util.SessionUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(asyncSupported = true, urlPatterns = { "/" })
public class AuthenticationFilter implements Filter {

	private static final String LOGIN = "/login";
	private static final String REGISTER = "/registration";
	private static final String PROFILE = "/profile";
	private static final String HOME = "/index";
	private static final String ABOUT = "/about";
	private static final String ROOT = "/";
	private static final String DASHBOARD = "/admin";
	private LoginService loginService;




	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Cast the request and response to HttpServletRequest and HttpServletResponse
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String username = req.getParameter("userName");
		String contextPath = req.getContextPath();
//		String userRole = loginService.getUserRole(username);
		// Get the requested URI
		String uri = req.getRequestURI();

		if (uri.endsWith(".css") || uri.endsWith(HOME) || uri.endsWith(PROFILE) || uri.endsWith(ABOUT) || uri.endsWith(ROOT)) {
			chain.doFilter(request, response);
			return;
		}

		// Get the session and check if user is logged in
		boolean isLoggedIn = SessionUtil.getAttribute(req, "userName") != null;

//		if(userRole.equals("admin")) {
//			res.sendRedirect(req.getContextPath() + DASHBOARD);
//		}else {
//			res.sendRedirect(req.getContextPath() + LOGIN);
//		}
//		
		if (!isLoggedIn) {
			if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(req.getContextPath() + LOGIN);
			}
		} else {
			if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
				res.sendRedirect(req.getContextPath() + HOME);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Filter.super.destroy();
	}

}
