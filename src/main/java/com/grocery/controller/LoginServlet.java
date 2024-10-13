package com.grocery.controller;
import com.grocery.dao.UserDao;
import com.grocery.model.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.grocery.model.User;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customerId = request.getParameter("customerId");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();
        User user = userDao.loginUser(customerId, password);  // Ensure this returns a User object

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user); // Use "currentUser" to match your profile.jsp

            // Check user role and redirect accordingly
            if ("admin".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect("adminHome.jsp"); // Redirect to admin home page
            } else {
                response.sendRedirect("ProductServlet"); // Redirect to customer home page
            }
        } else {
            // Handle login failure
            request.setAttribute("errorMessage", "Invalid Customer ID or Password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
	}

}
