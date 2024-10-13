package com.grocery.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.grocery.dao.UserDao;
import com.grocery.model.User;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
@WebServlet("/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao = new UserDao();

    public EditProfileServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        String customerId = ((User) request.getSession().getAttribute("currentUser")).getCustomerId();
        
        // Get existing user details from session
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        String currentProfileImage = currentUser.getProfileImage(); // Keep the current profile image

        String profileImagePath = currentProfileImage; // Default to current profile image

        // Handle file upload (if a new file is uploaded)
        Part filePart = request.getPart("profileImage");
        if (filePart != null && filePart.getSize() > 0) { // Check if a file was uploaded
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + "uploads" + File.separator + fileName;

            // Create uploads directory if it doesn't exist
            File uploadDir = new File(getServletContext().getRealPath("") + "uploads");
            if (!uploadDir.exists()) uploadDir.mkdir();

            // Save the uploaded file to the directory
            Files.copy(filePart.getInputStream(), Paths.get(uploadPath));

            // Store the new file path as a URL (relative path)
            profileImagePath = "uploads/" + fileName;
        }

        // Create updated User object with existing customerId
        User updatedUser = new User(fullName, email, password, address, contact, currentUser.getRole(), customerId, profileImagePath);

        // Update user in the database
        boolean isUpdated = userDao.updateUser(updatedUser);

        if (isUpdated) {
            // Update session with new user details
            request.getSession().setAttribute("currentUser", updatedUser);
            response.sendRedirect("profile.jsp");  // Redirect to profile page
        } else {
            response.sendRedirect("editProfile.jsp?error=UpdateFailed");  // Handle failure
        }
    }
}
