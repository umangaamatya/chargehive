package com.chargehive.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.chargehive.util.RedirectionUtil;

/**
 * @author Umanga Amatya
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/admin"})
public class AdminDashboard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public AdminDashboard() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.print('0');
		request.getRequestDispatcher(RedirectionUtil.adminUrl).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
