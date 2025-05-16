package com.chargehive.util;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * @author Umanga Amatya
 */
public class RedirectionUtil {

	private static final String baseUrl = "/WEB-INF/pages/";
	public static final String registrationUrl = baseUrl + "registration.jsp";
	public static final String loginUrl = baseUrl + "login.jsp";
	public static final String indexUrl = baseUrl + "index.jsp";
	public static final String adminUrl = baseUrl + "admin.jsp";

	public void setMsgAttribute(HttpServletRequest req, String msgType, String msg) {
		req.setAttribute(msgType, msg);
	}

	public void redirectToPage(HttpServletRequest req, HttpServletResponse resp, String page)
			throws ServletException, IOException {
		req.getRequestDispatcher(page).forward(req, resp);
	}

	public void setMsgAndRedirect(HttpServletRequest req, HttpServletResponse resp, String msgType, String msg,
			String page) throws ServletException, IOException {
		setMsgAttribute(req, msgType, msg);
		redirectToPage(req, resp, page);
	}

}
