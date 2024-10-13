<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.grocery.model.Product" %>

<%
    List<Product> productList = (List<Product>) request.getAttribute("productList");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - Products</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="userNav.jsp" />
    <div class="container mt-5">
        <h1 class="text-center mb-4">Available Products</h1>

        <% if (productList != null && !productList.isEmpty()) { %>
            <div class="row">
                <% for (Product product : productList) { %>
                    <div class="col-md-4 mb-4">
                        <div class="card h-100">
                            <img src="<%= product.getProductImage() %>" class="card-img-top" alt="Product Image" 
                                 style="height: 200px; object-fit: cover;">
                            <div class="card-body">
                                <h5 class="card-title"><%= product.getProductName() %></h5>
                                <p class="card-text">Price: ₹<%= product.getPrice() %></p>
                                <p class="card-text"><%= product.getDescription() %></p>
                            </div>
                            <div class="card-footer text-center">
                                <form action="CartServlet" method="post">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                                    <input type="hidden" name="productName" value="<%= product.getProductName() %>">
                                    <input type="hidden" name="price" value="<%= product.getPrice() %>">
                                    <input type="hidden" name="productImage" value="<%= product.getProductImage() %>">
                                    <button type="submit" class="btn btn-primary">Add to Cart</button>
                                </form>
                            </div>
                        </div>
                    </div>
                <% } %>
            </div>
        <% } else { %>
            <p class="text-center text-danger">No products found.</p>
        <% } %>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
