<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.grocery.model.User" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    // Fetch the current session
    HttpSession session1 = request.getSession(false);
    User currentUser = null;

    // Check if the session is valid and retrieve user details
    if (session1 != null) {
        currentUser = (User) session1.getAttribute("currentUser");
    }

    // Redirect to login if user is not logged in
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- Include Navbar -->
    <jsp:include page="userNav.jsp" />

    <div class="container mt-5">
        <h1 class="text-center mb-4">User Profile</h1>
        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">User Details</h5>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4 text-center">
                        <img src="<%= currentUser.getProfileImage() != null ? currentUser.getProfileImage() : "https://via.placeholder.com/150" %>" alt="User Image" class="img-fluid rounded-circle mb-3" style="width: 100px; height: 100px;" />
                        <h6><strong><%= currentUser.getFullName() %></strong></h6>
                    </div>
                    <div class="col-md-8">
                        <ul class="list-group">
                            <li class="list-group-item"><strong>Email:</strong> <%= currentUser.getEmail() %></li>
                            <li class="list-group-item"><strong>Address:</strong> <%= currentUser.getAddress() %></li>
                            <li class="list-group-item"><strong>Contact:</strong> <%= currentUser.getContact() %></li>
                            <li class="list-group-item"><strong>Role:</strong> <%= currentUser.getRole() %></li>
                            <li class="list-group-item"><strong>Customer ID:</strong> <%= currentUser.getCustomerId() %></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="card-footer text-center">
                <a href="editProfile.jsp" class="btn btn-warning">Edit Profile</a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>
