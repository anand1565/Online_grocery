package com.grocery.controller;
import com.grocery.dao.UserDao;
import com.grocery.model.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        String role = "customer"; // Default role for new users

        User user = new User(fullName, email, password, address, contact, role, null); // ID will be generated

        UserDao userDao = new UserDao();
        
//        if (userDao.emailExists(email)) {
//            response.sendRedirect("register.jsp?error=emailExists");
//            return;
//        }
        
        boolean success = userDao.registerUser(user);
        
        if (success) {
            response.sendRedirect("login.jsp"); // Redirect to login after successful registration
        } else {
            response.sendRedirect("register.jsp?message=Registration failed. Try again.");
        }
	}

}
