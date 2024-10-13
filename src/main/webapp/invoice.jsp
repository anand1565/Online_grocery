<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.grocery.model.CartItem" %>

<%
    String paymentMethod = request.getParameter("paymentMethod");
    double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));

    String[] cartItemsArray = request.getParameterValues("cartItems");
    List<CartItem> cartItems = new ArrayList<>();

    for (String item : cartItemsArray) {
        String[] details = item.split(":");
        String productId = details[0];
        String productName = details[1];
        double price = Double.parseDouble(details[2]);
        int quantity = Integer.parseInt(details[3]);
        cartItems.add(new CartItem(productId, productName, price, quantity, "imagePath")); // Update with actual image path if needed
    }

    // Optionally clear the cart here (if needed, based on your logic)
    // session.removeAttribute("cartItems"); // Uncomment if you're storing cart in session
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Invoice</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>Invoice</h1>
        <h4>Payment Method: <%= paymentMethod %></h4>
        <h5>Total Amount: ₹<%= totalAmount %></h5>
        <table class="table">
            <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (CartItem item : cartItems) {
                %>
                    <tr>
                        <td><%= item.getProductName() %></td>
                        <td><%= item.getQuantity() %></td>
                        <td>₹<%= item.getPrice() * item.getQuantity() %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
