package com.chargehive.controller;

import com.chargehive.model.UserModel;
import com.chargehive.service.LoginService;
import com.chargehive.util.RedirectionUtil;
import com.chargehive.util.SessionUtil;
import com.chargehive.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ValidationUtil validationUtil;
	private RedirectionUtil redirectionUtil;
	private LoginService loginService;

	@Override
	public void init() throws ServletException {
		this.validationUtil = new ValidationUtil();
		this.redirectionUtil = new RedirectionUtil();
		this.loginService = new LoginService();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(RedirectionUtil.loginUrl).forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("user_email");
		String password = req.getParameter("user_password");

		if (email != null) email = email.trim();
		if (password != null) password = password.trim();

		if (!validationUtil.IsEmpty(email) && !validationUtil.IsEmpty(password)) {
			UserModel user = loginService.authenticateUser(email, password);

			if (user != null) {
				HttpSession session = req.getSession();
				session.setAttribute("userId", user.getUserId());
				session.setAttribute("userEmail", user.getUserEmail());
				session.setAttribute("userRole", user.getUserRole());

				if ("admin".equalsIgnoreCase(user.getUserRole())) {
					resp.sendRedirect(req.getContextPath() + "/admin");
				} else {
					resp.sendRedirect(req.getContextPath() + "/user");
				}
			} else {
				handleLoginFailure(req, resp);
			}
		} else {
			redirectionUtil.setMsgAndRedirect(req, resp, "error", "Please fill all the fields!",
					RedirectionUtil.loginUrl);
		}
	}

	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String errorMessage = "Invalid credentials. Please try again!";
		req.setAttribute("error", errorMessage);
		req.getRequestDispatcher(RedirectionUtil.loginUrl).forward(req, resp);
	}
}