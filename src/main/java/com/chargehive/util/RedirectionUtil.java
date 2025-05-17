package com.chargehive.util;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Utility class for handling redirection and forwarding with optional message attributes.
 * 
 * Provides predefined URLs for internal views and methods to:
 * - Set request-scoped messages
 * - Forward requests to JSP pages
 * - Combine both actions in a single call
 * 
 * @author Umanga Amatya
 */
public class RedirectionUtil {

	private static final String baseUrl = "/WEB-INF/pages/";
	public static final String registrationUrl = baseUrl + "registration.jsp";
	public static final String loginUrl = baseUrl + "login.jsp";
	public static final String indexUrl = baseUrl + "index.jsp";
	public static final String adminUrl = baseUrl + "admin.jsp";
	
	/**
	 * Sets a message attribute in the request scope.
	 *
	 * @param req     the HttpServletRequest object
	 * @param msgType attribute name (e.g., "error", "success")
	 * @param msg     message content to be displayed
	 */
	public void setMsgAttribute(HttpServletRequest req, String msgType, String msg) {
		req.setAttribute(msgType, msg);
	}

	/**
	 * Forwards the request to the specified internal page (usually a JSP).
	 *
	 * @param req  the HttpServletRequest
	 * @param resp the HttpServletResponse
	 * @param page full path to the internal JSP page
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs during dispatch
	 */	
	public void redirectToPage(HttpServletRequest req, HttpServletResponse resp, String page)
			throws ServletException, IOException {
		req.getRequestDispatcher(page).forward(req, resp);
	}
	
	/**
	 * Sets a message and then forwards the request to the given page.
	 *
	 * @param req     the HttpServletRequest
	 * @param resp    the HttpServletResponse
	 * @param msgType the attribute name (e.g., "error", "success")
	 * @param msg     the message to attach
	 * @param page    the target JSP page to forward to
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	public void setMsgAndRedirect(HttpServletRequest req, HttpServletResponse resp, String msgType, String msg,
			String page) throws ServletException, IOException {
		setMsgAttribute(req, msgType, msg);
		redirectToPage(req, resp, page);
	}

}
