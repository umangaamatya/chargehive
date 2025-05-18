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

/**
 * Servlet Filter for handling authentication checks.
 * Restricts access to protected pages unless the user is logged in.
 * Allows open access to public pages such as login, registration, and home.
 * 
 * URL Pattern: /*
 * 
 * @author Umanga Amatya
 */

@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class AuthenticationFilter implements Filter {

	private static final String LOGIN = "/login";
	private static final String REGISTER = "/registration";
	private static final String PROFILE = "/profile";
	private static final String HOME = "/index";
	private static final String ABOUT = "/about";
	private static final String ROOT = "/";
	private static final String DASHBOARD = "/admin";
	private LoginService loginService;



	/**
	 * Initializes the filter. Can be used to set up resources if needed.
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}
	
	/**
	 * Intercepts all incoming requests and filters access based on session state.
	 * 
	 * @param request  the incoming ServletRequest
	 * @param response the ServletResponse object
	 * @param chain    the FilterChain to continue execution
	 * @throws IOException if an input/output error occurs
	 * @throws ServletException if a servlet-specific error occurs
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Cast the request and response to HttpServletRequest and HttpServletResponse
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String username = req.getParameter("userName");
		String contextPath = req.getContextPath();
	
		String uri = req.getRequestURI();
		
		// Allow access to static resources and public pages
		if (uri.endsWith(".css") || uri.endsWith(HOME) || uri.endsWith(PROFILE) || uri.endsWith(ABOUT) || uri.endsWith(ROOT)) {
			chain.doFilter(request, response);
			return;
		}

		// Get the session and check if user is logged in
		boolean isLoggedIn = SessionUtil.getAttribute(req, "userId") != null;
		
		if (isLoggedIn) {
		    // Allow access to everything, including /login and /registration
		    chain.doFilter(request, response);
		} else {
		    // If not logged in, allow only public routes
		    if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER) ||
		        uri.endsWith(HOME) || uri.endsWith(ABOUT) || uri.endsWith(ROOT) || uri.endsWith("/aboutus.jsp") || uri.endsWith(".css") || uri.endsWith(".js")) {
		        chain.doFilter(request, response);
		    } else {
		        req.setAttribute("error", "You must log in first.");
		        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, res);
		    }
		}
	}
	
	/**
	 * Cleans up resources when the filter is destroyed.
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Filter.super.destroy();
	}

}
