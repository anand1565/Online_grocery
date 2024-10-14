<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.grocery.model.CartItem" %>

<%
    // Retrieve cart items from session
    List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
    double totalAmount = 0;

    // Calculate total amount only if cartItems is not null or empty
    if (cartItems != null) {
        for (CartItem item : cartItems) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Cart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .cart-item { border: 1px solid #ddd; border-radius: 5px; padding: 10px; margin-bottom: 10px; }
        .cart-image { height: 100px; object-fit: cover; }
    </style>
</head>
<body>
    <jsp:include page="userNav.jsp" />
    <div class="container mt-5">
        <h1 class="text-center mb-4">Shopping Cart</h1>

        <div class="row">
            <div class="col-md-8">
                <% if (cartItems != null && !cartItems.isEmpty()) { %>
                    <% for (CartItem item : cartItems) { %>
                        <div class="cart-item d-flex justify-content-between align-items-center">
                            <div class="d-flex align-items-center">
                                <img src="<%= item.getProductImage() %>" class="cart-image me-3" alt="<%= item.getProductName() %>">
                                <div>
                                    <h5><%= item.getProductName() %></h5>
                                    <p>Price: ₹<%= item.getPrice() %></p>
                                    <form action="CartServlet" method="post" class="d-inline">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="productId" value="<%= item.getProductId() %>">
                                        <button type="submit" class="btn btn-danger btn-sm">Remove</button>
                                    </form>
                                </div>
                            </div>
                            <div>
                                <form action="CartServlet" method="post" class="d-flex align-items-center">
                                    <input type="hidden" name="productId" value="<%= item.getProductId() %>">
                                    <button type="submit" class="btn btn-secondary btn-sm" name="action" value="decrement">-</button>
                                    <input type="text" class="form-control mx-2" style="width: 50px; text-align: center;" name="quantity" value="<%= item.getQuantity() %>" readonly>
                                    <button type="submit" class="btn btn-secondary btn-sm" name="action" value="increment">+</button>
                                </form>
                            </div>
                        </div>
                    <% } %>
                <% } else { %>
                    <p class="text-center text-danger">Your cart is empty.</p>
                <% } %>
            </div>

            <div class="col-md-4">
                <h4>Order Summary</h4>
                <ul class="list-group mb-3">
                    <% if (cartItems != null && !cartItems.isEmpty()) { 
                        for (CartItem item : cartItems) { %>
                            <li class="list-group-item d-flex justify-content-between">
                                <span><%= item.getProductName() %></span>
                                <span>₹<%= item.getPrice() * item.getQuantity() %></span>
                            </li>
                    <% } } else { %>
                        <li class="list-group-item">No products in the cart.</li>
                    <% } %>
                </ul>

                <div class="d-flex justify-content-between">
                    <h5>Total Amount:</h5>
                    <h5>₹<%= totalAmount %></h5>
                </div>

                <form action="payment.jsp" method="post" class="mt-3">
                    <input type="hidden" name="totalAmount" value="<%= totalAmount %>">
                    <%
                        if (cartItems != null) {
                            StringBuilder cartItemsData = new StringBuilder();
                            for (CartItem item : cartItems) {
                                cartItemsData.append(item.getProductId()).append(":")
                                             .append(item.getProductName()).append(":")
                                             .append(item.getPrice()).append(":")
                                             .append(item.getQuantity()).append(":")
                                             .append(item.getProductImage()).append(";");
                            }
                    %>
                        <input type="hidden" name="cartItems" value="<%= cartItemsData.toString() %>">
                    <% } %>
                    <button type="submit" class="btn btn-success w-100">Proceed to Checkout</button>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
